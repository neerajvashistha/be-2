from xml.dom.minidom import parse
import xml.dom.minidom
import random
import sys
from threading import Thread, current_thread

def quickSort(alist):
    quickSortHelper(alist,0,len(alist)-1)

def quickSortHelper(alist,first,last):
    if first<last:
        splitpoint = partition(alist,first,last)
        t =Thread(target = quickSortHelper, args = (alist,first,splitpoint-1))
        t.start()
        t.join()
        t1=Thread(target = quickSortHelper, args = (alist,splitpoint+1,last))
        t1.start()
        t1.join()
    return alist

def partition(alist,first,last):
    pivotvalue = alist[first]

    leftmark = first+1
    rightmark = last

    done = False
    while not done:
        while leftmark <= rightmark and alist[leftmark] <= pivotvalue:
            leftmark = leftmark + 1
        while alist[rightmark] >= pivotvalue and rightmark >= leftmark:
            rightmark = rightmark -1
        if rightmark < leftmark:
            done = True
        else:
            swap(alist,leftmark,rightmark)
            
    swap(alist,first,rightmark)

    return rightmark

def swap(alist,left,right):
    temp = alist[left]
    alist[left] = alist[right]
    alist[right] = temp

def genXML(filename):
    alist = [random.randint(1,100) for i in range(50)]
    f = open(filename,"w")
    f.write("<Number>")
    for i in range(len(alist)):
        f.write("\n\t<integer>"+str(alist[i])+"</integer>")
    f.write("\n</Number>")
    f.close()

if __name__ == '__main__':
    
    filename = raw_input("Enter filename: ")
    filename = filename if filename.find("xml")!=-1 else filename+".xml"
    genXML(filename)

    DOMTree = xml.dom.minidom.parse(filename)
    collection = DOMTree.documentElement
    integers = collection.getElementsByTagName("integer")

    alist=[]
    for i in range(len(integers)):
        val = collection.getElementsByTagName("integer")[i]
        alist.append(int(val.childNodes[0].data))

    quickSort(alist)
    print(alist)