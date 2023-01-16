# Lenny Lukach
# Data Structures and Algorithms
# Flight Plan UI
# Date Made: 2/26/22
# Date Modified: 5/18/22
from audioop import add
import operator
import tkinter as tk
from turtle import back
from typing import Text
from PIL import ImageTk, Image
import pickle
import collections

debugMode = True

savePath = "Python\FlightApp\FlightPlanMap.txt"

root = tk.Tk()
root.title("Flight Plan")
root.geometry("800x800")
root.configure(bg="lightgray")
ws = root.winfo_screenwidth()
hs = root.winfo_screenheight()
h = 800
w = 800
x = (ws/2) - (w/2)
y = (hs/2) - (h/2)

root.geometry('%dx%d+%d+%d' % (w, h, x, y))

#Vars
destList = ["Start"]
destListNums = []
destListEntries = []


#Classes
class Location:
    def __init__(self, name, pos):
        self.name = name
        self.pos = pos

#Functions
def temp_text(e):
    txt_addDest.delete(0, "end")

def addDestination():
    destList.append(txt_addDest.get())
    txt_addDest.delete(0, "end")
    if debugMode:
        print(destList)

def genList():
    btn_getVals = tk.Button(root, text="Save Order", command=getVals)
    btn_createPlan = tk.Button(root, text="Create Plan", command=createPlanFunc)
    btn_getVals.place(x=50, y=120)
    btn_createPlan.place(x=125, y=120)
    destListEntries.clear()
    addY = 0
    for i in range(len(destList)):
        exec('tk.Label%d=tk.Label(root,text="%s")\ntk.Label%d.place(x=80, y=150+addY)' % (i,destList[i],i))
        if i > 0:
            new_entry = tk.Entry(root, width=2)
            new_entry.place(x=50, y=150+addY)
            destListEntries.append(new_entry)
        addY += 27   

def getVals():
    destListNums.clear()
    for entry in destListEntries:
        destListNums.append(entry.get())
    if debugMode:
        print(destListNums)

def createPlanFunc():
    odestList = destList[1:]
    newDestList = "["
    newDestListNums = "["
    a123 = len(odestList) - 1
    b123 = len(destListNums) - 1
    magHeading = txt_magHeading.get()
    directionCardinal = txt_directionCardinal.get()
    distance = txt_distance.get()
    with open(savePath, "w") as f:
        for dest in odestList:
            if odestList[a123] != dest:
                newDestList += dest + ", "
            else:
                newDestList += dest + "]"

        for dest in destListNums:
            if destListNums[b123] != dest:
                newDestListNums += dest + ", "
            else:
                newDestListNums += dest + "]"
        f.write(f"newDestList = {newDestList}")
        f.write(f"\nnewDestListNums = {newDestListNums}")

        f.write("\n" * 4)
        f.write(f"Magnetic Heading: {magHeading}\n")
        f.write(f"Cardinal Direction: {directionCardinal}\n")
        f.write(f"Distance to Airport: {distance}")

        destDict = {}
        for count in range(len(odestList)):
            destDict.update({odestList[count - 1]: destListNums[count - 1]})
        f.write("\n" * 6)
        sorted_x = sorted(destDict.items(), key=operator.itemgetter(1))
        f.write(str(sorted_x))
        f.close()

#?Images
try:
    background_image = ImageTk.PhotoImage(Image.open("/Users/lluakch/Development/Pycharm Project/vscode-workspace/Python/FlightApp/planeLanding.jpg.png"))
except FileNotFoundError:
    background_image = ImageTk.PhotoImage(Image.open("Python\FlightApp\planeLanding.jpg.png"))
    
background_lbl = tk.Label(root, image=background_image).place(x=0, y=0)

#?Create Widgets
txt_addDest = tk.Entry(root, text="asdf", bd=4)
txt_addDest.insert(0, "Enter Destination")
txt_addDest.bind("<FocusIn>", temp_text)

txt_directionCardinal = tk.Entry(root, bd=4)
txt_magHeading = tk.Entry(root, bd=4)
txt_distance = tk.Entry(root, bd=4)

lbl_enterDes = tk.Label(root, text="Enter Desired Destination")
lbl_directionCardinal = tk.Label(root, text="Enter Direction(CARDINAL)")
lbl_magHeading = tk.Label(root, text="Enter magnetic heading")
lbl_distance = tk.Label(root, text="    Distance to Airport    ")

btn_addDes = tk.Button(root, text="Add", command=addDestination)
btn_genList = tk.Button(root, text="Generate List", command=genList)

#?Add Widgets
txt_addDest.place(relx=0.052, rely=0.075)

txt_directionCardinal.place(relx=0.6, rely=0.075)
txt_magHeading.place(relx=0.4, rely=0.075)
txt_distance.place(relx=0.8, rely=0.075)

lbl_directionCardinal.place(relx=0.6, rely=0.045)
lbl_magHeading.place(relx=0.4, rely=0.045)
lbl_distance.place(relx=0.8, rely=0.045)

lbl_enterDes.place(relx=0.05, rely=0.045)

btn_addDes.place(relx=0.23, rely=0.045)
btn_genList.place(relx=0.23, rely=0.075)










root.mainloop()
