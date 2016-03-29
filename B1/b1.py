"""
Changes are in place function only:: Line Number:: 28,29, thats it

"""
import json
inf=open("8q.json")
board=json.loads(inf.read())
board=board["matrix"]
for i in board:
    print(i)

def issafe(row,col):
    for i in range(8):
        for j in range(8):
            if(board[i][j]==1): #if a queen exists here, then check if it attacks our queen
                if(row==i):
                    return False
                if(col==j):
                    return False
                if(abs(row-i)==abs(col-j)):
                    return False
    return True
def place(col):
    if(col>=8):     #if all 8 queens are placed, then finish
        print("\t\tCompleted...")
        return True
    for i in range(8):  #checking for all rows in that column
        if(board[i][col]==1):   #if a queen is already placed here,
            return place(col+1) #then simply place for next column
        if(issafe(i,col)):  #is it safe?
            board[i][col]=1 #queen is placed here
            if(place(col+1)==True): #recursive call to place next queen
                return True
            board[i][col]=0     #if not placed, then backtrack, i.e it sets to zero and the loop iterates to check for next position
    return False
if(place(0)==True):
    print("solution found")
else:
    print("Solution not possible")
for i in board:
    print(i)
