from tkinter import *
from PyDictionary import PyDictionary

root = Tk()
root.title("English Dictionary")
root.resizable(0, 0)
root.eval('tk::PlaceWindow . center')
root.config(bg="black")
root.geometry("")


def get_definitions(self):
    word = self.get()
    for x in word:
        if not x.isalpha():
            return "Must be one word and contain only letters"
    dictionary = PyDictionary()
    try:
        definitions = dictionary.meaning(word)
        word = (list(definitions.values())[0])
        return word
    except:
        return "Word not found"


e = Entry(root, width=50, bg="white", fg="black", border=2, justify="center")
e.grid(row=0, column=0, pady=(7, 0), sticky="ew")
e.insert(0, "Enter a word")
e.bind("<Button-1>", lambda event: e.delete(0, END))
f = Label(root, fg="black", wraplength=400, bg="black", border=2)
f.grid(row=1, column=0, pady=7)

myButton = Button(root, width=50, border=2, text="Define Word", command=lambda: f.config(text=get_definitions(e), bg="white"))
myButton.grid(row=2, column=0, pady=(0, 7), sticky="ew")

root.mainloop()
