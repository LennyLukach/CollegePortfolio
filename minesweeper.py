from usefulCustomImports import *
import random

clearScr()
bSize = 5
bombAmt = 6
coverIcon = "-"
bombIcon = "B"

class Cell:
    def __init__(self, pos, value, shown, icon, isBomb):
        self.pos = pos
        self.value = value
        self.shown = shown
        self.icon = icon
        self.isBomb = isBomb

board = [[None for row in range(bSize)]for col in range(bSize)]

for row in range(bSize):
    for col in range(bSize):
        cell = Cell((row, col), 0, False, coverIcon, False)
        board[row][col] = cell

def printBoard():
    line = " "
    for j in range(bSize):
        line += " " + str(j + 1)
    print(line)
    count = 0
    for row in board:
        count+= 1
        line = str(count)
        for cell in row:
            #cell.shown = True
            if cell.shown:
                if cell.isBomb:
                    cell.icon = bombIcon
                else:
                    cell.icon = str(cell.value)
            line += " " + cell.icon
        print(line)

def createBomb():
    numBombs = random.randint(bombAmt/2, bombAmt)
    for x in range(numBombs):
        while True:
            bombPos = (random.randint(0, bSize - 1), random.randint(0, bSize - 1))
            if board[bombPos[1]][bombPos[0]].isBomb == False:
                board[bombPos[1]][bombPos[0]].isBomb = True
                break;
    return board
        
def digCell():
    userRow = int(input("\nPick x coord:\n"))
    userCol = int(input("\nPick y coord:\n"))
    chosenCell = (userCol - 1, userRow - 1)
    for row in board:
        for cell in row:
            if cell.pos == chosenCell:
                cell.shown = True
    return board

def updateValue():
    for row in board:
        for cell in row:
            tempPos = [cell.pos[0], cell.pos[1]]
            bombsFound = 0

            #! LEFT
            tempPos[1] -= 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! RIGHT
            tempPos[1] += 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! UP
            tempPos[0] -= 1
            if tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! DOWN
            tempPos[0] += 1
            if tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! TOP LEFT
            tempPos[1] -= 1
            tempPos[0] -= 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1 and tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! TOP RIGHT
            tempPos[1] += 1
            tempPos[0] -= 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1 and tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! BOTTOM LEFT
            tempPos[1] -= 1
            tempPos[0] += 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1 and tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]

            #! BOTTOM RIGHT
            tempPos[1] += 1
            tempPos[0] += 1
            if tempPos[1] >= 0 and tempPos[1] <= bSize - 1 and tempPos[0] >= 0 and tempPos[0] <= bSize - 1:
                if board[tempPos[0]][tempPos[1]].isBomb == True:
                    bombsFound += 1
            tempPos = [cell.pos[0], cell.pos[1]]


            cell.value = bombsFound

def checkWin():
    revealedBomb = False
    wonGame = True;
    for row in board:
        for cell in row:
            if cell.shown:
                if cell.isBomb:
                    revealedBomb = True
                    wonGame = False
            elif cell.shown == False and cell.isBomb == False:
                wonGame = False

    if revealedBomb:
        for row in board:
            for cell in row:
                cell.shown = True
        print("\n")
        printBoard()
        print("\nYou found a bomb. Game over.")
    elif wonGame:
        for row in board:
            for cell in row:
                cell.shown = True
        print("\n")
        printBoard()
        print("\nYou win!")
    return [wonGame, revealedBomb]


createBomb()
updateValue()
while True:
    printBoard()
    digCell()
    potential = checkWin()
    if potential[0] or potential[1]:
        break