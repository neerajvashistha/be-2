def binarySearch(alist, item):
	'''
	binartsearch using divide and conqure(non-recursive).

	    @param: alist, unsorted list
	    @param: item, an element to be searched in alist

	    returns: bool, position, the presence of item in alist and position.
	'''
	pos = 0
	first = 0
	found = False
	alist1=list()
	atup = tuple() #temp
	adict = dict() #{sortedlist:originalposition}
	if isinstance(alist,list):
		atup = tuple(alist)
		alist1 = list(atup)
		alist.sort()
		#to store the position of each item in alist into adict
		for i in range(len(alist)):
			adict[alist[i]] = alist1.index(alist[i])+1
		#actual program begins here--..
		last = len(alist)-1
    	while first<=last and not found:
	        midpoint = (first + last)//2
	        if alist[midpoint] == item:
	            found = True
	            pos = adict[item]
	        else:
	            if item < alist[midpoint]:
	                last = midpoint-1
	            else:
	                first = midpoint+1
	#print adict
	return found,pos

#testlist = [42,72, 2, 11, 55, 32, 76]
#print(binarySearch(testlist, 2))
#print(binarySearch(testlist, 13))

s = raw_input("enter few elements ")
alist = s.split(",")

alist = map(int, alist)

print alist

findEle = raw_input("enter element to be searched ")

print(binarySearch(alist,int(findEle)))

