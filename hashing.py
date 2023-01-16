import os
import math
import sys
import time
from usefulCustomImports import *

#----------!!! Explains useful feautures !!!----------#
#-----------------------------------------------------#
#----------- Print Statements to uncomment -----------#
#-----------------------------------------------------#
#-------------- Hashed Constants:LN 384 --------------#
#--------------- Input Padding: LN 395 ---------------#
#------------- Length of Padding: LN 403 -------------#
#-------------- Message Schedule:LN 415 --------------#
#--------------- Binary Hashes: LN 423 ---------------#
#-----------------------------------------------------#
# Might need to change cls to clear to run:LN 23 & 88 #






clearScr()

#---------------------------- METHODS ----------------------------#
#---------------------------- METHODS ----------------------------#
#---------------------------- METHODS ----------------------------#
#---------------------------- METHODS ----------------------------#


#Uses Sieve of Eratosthenes to get all necessary prime numbers with high efficiency
def getPrimes(n):
    primeList = list()
    sieve = [True] * (n+1)
    for p in range(2, n+1):
        if (sieve[p]):
            primeList.append(p)
            for i in range(p, n+1, p):
                sieve[i] = False
    return primeList

#This preps the creation of the hash and then calls the conversion method
def createHash(num, root):
    multiplier = 16 ** 8
    num = num ** (1. / root)
    num *= multiplier
    num = math.trunc(num)
    return decToHex(num)

#Converst decimals to hexadecimal values
def decToHex(num):
    hexaList = []
    hexLetters = ["a", "b", "c", "d", "e", "f"]
    while num != 0:
        result = num / 16
        result = math.trunc(result)
        remainder = num % 16
        num = result
        if remainder > 9:
            #Im very happy with this line below, it uses the difference of the value minus 10 to get the index rather than using a bunch of if statements
            indexCount = remainder - 10
            remainder = hexLetters[indexCount]
        else:
            remainder = str(remainder)
        hexaList.append(remainder)
    
    hexaList.reverse()
    hexaList.pop(0)
    hexaList.insert(0, "0x")
    return hexaList

#This converts strings to binary values
def strToBinary(word):
    tempStr = []
    doCheck = True
    for letter in word:
        if doCheck == True:
            #Warns user about flaw in program as the program uses ord() to get the ascii value which is slightly flawed in itself
            if letter.isalpha() == False:
                print("This program seems to incorrectly handle non-alphabetic characters(space / ~ , ! 0-9)\nAre you sure you want to use this input?\nInputting a-z values only has no incorrect outputs.\n(1) Re-enter\n(2) Use this ")
                choice = int(input())
                if choice == 1:
                    if letter == " ":
                        sys.exit("A space was found in input.")
                    sys.exit(f"{letter} was found in input.")
                elif choice == 2:
                    doCheck = False
                    clearScr()
                    print(f"Enter a word: {word}")
                    continue

        num = ord(letter)
        tempStr.append("0" + bin(num)[2:])
    return tempStr

#Converts binary values to decimal values
def binaryToDecimal(binary):
    decimal = 0
    for digit in binary:
        decimal = decimal*2 + int(digit)
    return decimal

#Converts decimal values to binary values
def decimalToBinary(n):
    test = "0"
    asdf = bin(n).replace("0b", "")
    test += asdf
    return test

#Method to create padding for the message block
def createPadding(tempStr):
    toLen = 0
    for item in tempStr:
        toLen += len(item)
        #Program only handles a single message block, if input is too large, then a second message block would be needed causing the length to be 512 * (amt of message blocks)
    if toLen > 512:
        print("Input too large, try again")
        exit()
    tempStr.append("1")
    padTo = 1
    binLen = 0
    orgLen = 0
    for item in tempStr:
        orgLen += len(item)
    orgLen -= 1
    for item in tempStr:
        binLen += len(item)
    pad0 = 448 - binLen
    #Appends zero's to get length of message block to be 512
    if pad0 > 0:
        tempStr.append("0" * pad0)
    pad0 = (bin(orgLen)[2:])
    tempStr.append(pad0)
    binLen = 0
    for item in tempStr:
        binLen += len(item)
    pad0 = (512) - binLen
    if pad0 > 0:
        tempStr.insert(len(tempStr) - 1, "0" * pad0)
    return tempStr

#Method to make the message schedule
def createMSchedule(msgBlock):
    bigStr = ""
    msgSchedule = []
    count = 0
    for item in msgBlock:
        bigStr += item
    for bit in bigStr:
        count += 1
        #Forces each new word in the message schedule to be 32 bits long exactly
        if count == 32:
            strand = bigStr[:32]
            bigStr = bigStr[32:]
            msgSchedule.append(strand)
            count = 0
    return msgSchedule

#Method to perform a Right shift; ex: 010110(3) >>> 000010 
def SHR(word, amt):
    tempWord = []
    wordOut = ""
    ogA = amt
    amt = len(word) - amt
    for let in word:
        tempWord.append(let)
    tempWord = tempWord[:amt]
    for x in range(ogA):
        tempWord.insert(0, "0")
    for x in tempWord:
        wordOut += x
    return wordOut

#Method to perfom a Right rotation = 010110(3) >>> 110010
def ROT(word, amt):
    tempWord = []
    newWord = ""
    for dd in word:
        tempWord += dd
    for count in range(amt):
        bit = tempWord.pop()
        tempWord.insert(0, bit)
    for aa in tempWord:
        newWord += aa
    return newWord

#Performs an XOR calculation on three variables; does one pair, and then the other pair
def XOR(word0, word1, word2):
    tempWord = ""
    wordOut = ""
    #First pair
    for x in range(max(len(word0), len(word1))):
        num0 = str(word0[x])
        num0 = int(num0)
        num1 = str(word1[x])
        num1 = int(num1)
        total = num0 + num1
        if total == 1:
            tempWord += "1"
        else:
            tempWord += "0"
    #Second pair
    for y in range(max(len(tempWord), len(word2))):
        num1 = str(tempWord[y])
        num1 = int(num1)     
        num2 = str(word2[y])
        num2 = int(num2)
        total = num1 + num2
        if total == 1:
            wordOut += "1"
        else:
            wordOut += "0"
    return wordOut

#Adds four binary "words" that are inputted;
def add(word0, word1, word2, word3):
    temp = bin(int(word0, 2) + int(word1, 2) + int(word2, 2) + int(word3, 2))[2:]
    if len(temp) < 32:
        temp = temp.zfill(32)
    elif len(temp) > 32:
        diff = len(temp) - 32
        temp = temp[diff:]
    return temp

#Same as original add but supports 5 words
def addBut5(word0, word1, word2, word3, word4):
    temp = bin(int(word0, 2) + int(word1, 2) + int(word2, 2) + int(word3, 2) + int(word4, 2))[2:]
    if len(temp) < 32:
        temp = temp.zfill(32)
    elif len(temp) > 32:
        diff = len(temp) - 32
        temp = temp[diff:]
    return temp

#Same as original add but supports 2 words
def addBut2(word0, word1):
    temp = bin(int(word0, 2) + int(word1, 2))[2:]
    if len(temp) < 32:
        temp = temp.zfill(32)
    elif len(temp) > 32:
        diff = len(temp) - 32
        temp = temp[diff:]
    return temp

#Method to perform a lowercase sigma0 calculation which calls a ROT(7) >>> ROT(18) >>> SHR(3) and XOR's all three values
def ls0(word0):
    word1 = ROT(word0, 7)
    word2 = ROT(word0, 18)
    word3 = SHR(word0, 3)
    wordOut = XOR(word1, word2, word3)
    return wordOut

#Method to perform a lowercase sigma1 calculation which calls a ROT(17) >>> ROT(19) >>> SHR(10) and XOR's all three values
def ls1(word0):
    word1 = ROT(word0, 17)
    word2 = ROT(word0, 19)
    word3 = SHR(word0, 10)
    wordOut = XOR(word1, word2, word3)
    return wordOut

#Method to perform an uppercase sigma0 calculation which calls a ROT(2) >>> ROT(13) >>> ROT(22) and XOR's all three values
def us0(word0):
    word1 = ROT(word0, 2)
    word2 = ROT(word0, 13)
    word3 = ROT(word0, 22)
    wordOut = XOR(word1, word2, word3)
    return wordOut

#Method to perform an uppercase sigma1 calculation which calls a ROT(6) >>> ROT(11) >>> ROT(25) and XOR's all three values
def us1(word0):
    word1 = ROT(word0, 6)
    word2 = ROT(word0, 11)
    word3 = ROT(word0, 25)
    wordOut = XOR(word1, word2, word3)
    return wordOut

#Method that performs a choice using three binary "words" inputted. X determines if the bit from y or z is used
def ch(x, y, z):
    tempWord = ""
    count = 0
    for digit in x:
        digit = int(digit)
        if digit == 1:
            tempWord += y[count]
        else:
            tempWord += z[count]
        count += 1
    return tempWord

#Method to perfom a majority selection; Compares the bits in x, y, and z and then chooses the majority amount(more zeros or more ones) to constitute final value
def maj(x, y, z):
    tempWord = ""
    count = 0
    for val in range(32):
        amt = int(x[count]) + int(y[count]) + int(z[count])
        if amt >= 2:
            tempWord += "1"
        else:
            tempWord += "0"
        count += 1
    return tempWord

#Calls the functions above to extend the message schedule from the 16 original words from the message block to being 64 words long using the original 16 words
def extendSchedule(msgBlock):
    t0 = 0
    t1 = 1
    t9 = 9
    t14 = 14
    tempBlock = ""
    for full in range(48):
        #Gets the specific words
        word0 = msgBlock[t0]
        word1 = msgBlock[t1]
        word2 = msgBlock[t9]
        word3 = msgBlock[t14]
        
        #Perfoms lowercase sigma0 and lowercase sigma1 on respective words
        lowSig0 = ls0(word1)
        lowSig1 = ls1(word3)

        #Creates a new word by adding all four values
        tempBlock = add(word0, lowSig0, word2, lowSig1)
        msgBlock.append(tempBlock)
        t0 += 1
        t1 += 1
        t9 += 1
        t14 += 1
    return msgBlock

#Converts hexadecimal to binary
def hexToBin(hexVal):
    newBin = bin(int(hexVal, 16))[2:].zfill(32)
    return newBin

#Compresses schedule to create the final state registers which make up the SHA-256 output
def compressSchedule(msgBlock, hashlist, roundlist):
    for counter in range(len(msgBlock)):
        #Gets all the required values using choice, uppercase sigma1, and majority as well as calls the constant values that have been defined using getPrimes() and createHash()
        efgCh = ch(hashlist[4], hashlist[5], hashlist[6])
        us1E = us1(hashlist[4])
        h = hashlist[7]
        k0 = roundlist[0]
        w0 = msgBlock[0]

        majABC= maj(hashlist[0], hashlist[1], hashlist[2])
        sigA = us0(hashlist[0])

        #Creates temporary words using values initialized above; these words are used to change the state registers
        tempWord1 = addBut5(efgCh, us1E, hashlist[7], roundlist[counter], msgBlock[counter])
        tempWord2 = addBut2(majABC, sigA)
        
        #Slides the state registers down one and adds the temporary words to current state registers to change the values
        hashlist.pop()
        hashlist.insert(0, addBut2(tempWord1, tempWord2))
        hashlist[4] = addBut2(hashlist[4], tempWord1)
    return hashlist

#---------------------------- DRIVER CODE ----------------------------- #
#---------------------------- DRIVER CODE ----------------------------- #
#---------------------------- DRIVER CODE ----------------------------- #
#---------------------------- DRIVER CODE ----------------------------- #

#Initializes constant values that will be used later 
shortPrime = getPrimes(19)
longPrime = getPrimes(311)
hashlist = []
roundlist = []

#Creates the hashes of the values
for prime in shortPrime:
    hash = createHash(prime, 2)
    tempHash = ""
    for bit in hash:
        tempHash += bit
    hashlist.append(tempHash)

for prime in longPrime:
    round = createHash(prime, 3)
    tempRound = ""
    for bit in round:
        tempRound += bit
    roundlist.append(tempRound)

#Remove comments on print statements to see the hexadecimal values of the first 8 and 64 prime numbers that will be used during compression
#------------------------------------------------------------------------------------------------------------------------------#
#print(hashlist)
#print(roundlist)

#Gets the input to convert to SHA-256
inputString = input("Enter a word: ")
#Converts the string to binary
padStr = strToBinary(inputString)
#Creates the padding and sets up the input for use
padStr = createPadding(padStr)
#Remove the comment on the print statement print the fully padded binary version of the users input
#--------------------------------------------------------------------------------------------------#
#print(padStr)

#Counts length of the padding
len0 = 0
for item in padStr:
    len0 += len(item)
#Remove the comment on the print statement to get length of the padded binary word
#---------------------------------------------------------------------------------#
#print(f"Length: {len0}")


#Creates the messageSchedule using the prepared binary input
msgBlock = createMSchedule(padStr)

#Extends the message schedule to be 64 words long
msgBlock = extendSchedule(msgBlock)

#Prints each word of the message schedule, looks really cool
#Remove comment on the print statement to see the message schedule and remove the comment on time.sleep for it to look cool
count = 0
for line in msgBlock:
    #print(f"W[{count}]" + line)
    #time.sleep(0.05)
    count += 1

#Converts each hexadecimal constant to binary so that it can be used
#Remove comments on print statements to see the binary version of the initialized constants
count = 0
for hexData in hashlist:
    hashlist[count] = hexToBin(hexData)
    #print(hashlist(count))
    count += 1
count = 0
for roundData in roundlist:
    roundlist[count] = hexToBin(roundData)
    #print(roundlist(count))
    count += 1

#Copies the original binary values of the hashlist since the original will be modified but a copy will needed as well
inithashlist = hashlist.copy()

#Calls the compression method and uses the message schedule and both constant lists
compressSchedule(msgBlock, hashlist, roundlist)

#Adds the modified hashlist and the copy of the original for a bit more hashing
finalstateregister = []
for counter in range(len(hashlist)):
    finalHash = addBut2(hashlist[counter], inithashlist[counter])
    finalstateregister.append(finalHash)

#Concatenates the final state registers to be easily readable
for register in finalstateregister:
    countreg = finalstateregister.index(register)
    register = hex(int(register, 2))[2:]
    finalstateregister[countreg] = register

#Prints out the final SHA-256 value
sha256hash = ""
for register in finalstateregister:
    sha256hash += register

print("\n" + sha256hash + "\n")