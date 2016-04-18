import random
def binarySearch(alist, searchelement):
	'''
	binartsearch using divide and conqure(non-recursive).

	    @param: alist, unsorted list
	    @param: item, an element to be searched in alist

	    returns: bool, position, the presence of item in alist and position.
	'''
	first = 0
	found = False
	adict={}
	pos=0
	for i in range(len(alist)):
		adict[alist[i]]=alist.index(alist[i])+1
	alist.sort()

	last = len(alist)-1
	while first<=last and not found:
		midpoint = (first+last)//2
		if alist[midpoint]==searchelement:
			found = True
			pos=adict[searchelement]			
		else:
			if searchelement<alist[midpoint]:
				last=midpoint-1
			else:
				first=midpoint+1
	return found,pos

alist = [random.randint(0,99) for i in range(100)]
print alist
searchelement = raw_input("Enter element to be Searched: ")
print(binarySearch(alist,int(searchelement)))


s = raw_input("enter few elements ")
alist = s.split(",")
findEle = raw_input("enter element to be searched ")

print(binarySearch(alist,findEle))

