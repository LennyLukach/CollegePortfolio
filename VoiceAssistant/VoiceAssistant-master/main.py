import sys
import threading
import tkinter as tk
import speech_recognition as sr
import pyttsx3 as tts
from neuralintents import GenericAssistant
import warnings
import datetime

botName = "Activate"
botWakeWord = botName.lower()

warnings.filterwarnings('ignore', category=DeprecationWarning)


class Assistant:

    def __init__(self):
        self.recognizer = sr.Recognizer()
        self.speaker = tts.init()
        self.speaker.setProperty('rate', 150)

        self.assistant = GenericAssistant('intents.json', intent_methods={"time" : self.time})
        self.assistant.train_model()

        self.root = tk.Tk()
        self.root.geometry('400x400')
        self.label = tk.Label(self.root, text="Say '" + botName + "' to activate bot")
        self.label.pack()

        threading.Thread(target=self.run_assistant).start()

        self.root.mainloop()

    def time(self):
        now = datetime.datetime.now()
        print("The time is " + now.strftime("%H:%M:%S"))

    def run_assistant(self):
        while True:
            try:
                #may need to add #device_index=1 in sr.Microphone() if its not working
                with sr.Microphone() as source2:
                    self.recognizer.adjust_for_ambient_noise(source2, 0.2)
                    audio = self.recognizer.listen(source2)
                    message = self.recognizer.recognize_google(audio)
                    message = message.lower()

                    if botWakeWord in message:
                        self.label.config(text='Listening...', fg="red")
                        self.root.update()
                        print("------im listening")
                        audio = self.recognizer.listen(source2)
                        message = self.recognizer.recognize_google(audio)
                        message = message.lower()
                        print(message)
                        if message == 'stop':
                            self.speaker.say("Goodbye")
                            self.speaker.runAndWait()
                            self.speaker.stop()
                            self.root.destroy()
                            sys.exit()
                        else:
                            if message is not None:
                                response = self.assistant.request(message)
                                if response is not None:
                                    self.speaker.say(response)
                                    self.speaker.runAndWait()
                            self.label.config(text="Say '" + botName + "' to activate bot", fg="black")
                            self.root.update()


            except Exception as e:
                print(e)
                self.label.config(text="Say '" + botName + "' to activate bot", fg="black")
                self.root.update()
                print("crash")
                continue

Assistant.time(Assistant)
Assistant()
