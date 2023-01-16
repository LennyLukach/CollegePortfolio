from tkinter import *

import tkmacosx
from tkmacosx import Button
from PyDictionary import PyDictionary

bgColor = "grey37"

root = Tk()
root.title("English Dictionary")
root.resizable(0, 0)
root.eval('tk::PlaceWindow . center')
root.config(bg=bgColor)


def get_definitions(self) :
    word = self.get()
    for x in word :
        if not x.isalpha() :
            return "Must be one word and contain only letters"
    dictionary = PyDictionary()
    try :
        definitions = dictionary.meaning(word)
        word = (list(definitions.values())[0])
        return word
    except :
        return "Word not found"


e = Entry(root, width=50, bg="white", fg="black", justify="center")
e.grid(row=0, column=0, pady=(7, 0), sticky="ew", padx=7)
e.insert(0, "Click here to enter a word")
e.bind("<Button-1>", lambda event : e.delete(0, END))
e.bind("<Return>", lambda event : f.config(text=get_definitions(e)))
f = Label(root, wraplength=400, fg="black", justify="center", bg="white", width=50, highlightcolor="black", highlightthickness=2.5)
f.grid(row=1, column=0, pady=7, padx=7)
g = Button(root, height=21, fg="black", text="Get Definition", bg="white", relief="flat", highlightthickness=2.5, command=lambda : f.config(text=get_definitions(e)))
g.grid(row=0, column=1, pady=(7, 0), padx=7, sticky="ew")
h = Label(root, text="Press enter or click Get Definition", wraplength="100", font=("Helvetica", 12, "italic"), fg="white", bg=bgColor)
h.grid(row=1, column=1, pady=7, padx=7, sticky="ew")

root.mainloop()
