# A1 binary search
## code
```
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

```
## Explanation
```
binary search runs in at worst logarithmic time, making O(log n) comparisons, where n is the number of elements in the array

Algorithm

Binary search only works on sorted arrays. A binary search begins by comparing the middle element of the array with the target value. If the target value matches the middle element, its position in the array is returned. If the target value is less or more than the middle element, the search continues the lower or upper half of the array respectively with a new middle element, eliminating the other half from consideration. This method can be described recursively or iteratively.

Procedure[edit]
Given an array A of n elements with values or records A0 ... An−1 and target value T, the following subroutine uses binary search to find the index of T in A.[6]

1. Set L to 0 and R to n−1.
2. If L > R, the search terminates as unsuccessful. Set m (the position of the middle element) to the floor of (L + R) / 2.
3. If Am = T, the search is done; return m.
4. If Am < T, set L to m + 1 and go to step 2.
5. If Am > T, set R to m – 1 and go to step 2.
6. This iterative procedure keeps track of the search boundaries via two variables; a recursive version would keep its boundaries in its recursive calls. Some implementations may place the comparison for equality at the end, resulting in a faster comparison loop but costing one more iteration on average.[7]
```
# A2 quick sort
## code
```
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
```
## Explanation
```
Mathematical analysis of quicksort shows that, on average, the algorithm takes O(n log n) comparisons to sort n items. In the worst case, it makes O(n2) comparisons, though this behavior is rare.

Quicksort is a divide and conquer algorithm. Quicksort first divides a large array into two smaller sub-arrays: the low elements and the high elements. Quicksort can then recursively sort the sub-arrays.

The steps are:

1. Pick an element, called a pivot, from the array.
2. Partitioning: reorder the array so that all elements with values less than the pivot come before the pivot, while all elements with values greater than the pivot come after it (equal values can go either way). After this partitioning, the pivot is in its final position. This is called the partition operation.
3. Recursively apply the above steps to the sub-array of elements with smaller values and separately to the sub-array of elements with greater values.

The base case of the recursion is arrays of size zero or one, which never need to be sorted.

The pivot selection and partitioning steps can be done in several different ways; the choice of specific implementation schemes greatly affects the algorithm's performance.
```

# A3 booth 
## code
```
import bitstring
from bitstring import BitArray
from flask import Flask, request, render_template

def booths(m,r):
	x=len(bin(m))
	y=len(bin(r))
	totallength = x+y+1

	if m<0 and r<0 or r<0:
		bugbit = 1
	else:
		bugbit = 0

	A = BitArray(int = m,length = totallength) << (y+1)
	compliment = BitArray(int = -m,length = totallength) << (y+1)
	P = BitArray(int = r, length = totallength) 
	P = P<<1

	for i in range(1,y+1):
		if P[-2:]=='0b01':
			P = BitArray(int = P.int + A.int, length = totallength)
		elif P[-2:]=='0b10':
			P = BitArray(int = P.int + compliment.int, length = totallength)
		P = BitArray(int = P.int>>1, length = totallength)

	P = P[:-1]

	P.int = P.int + bugbit
	steps =""
	return '<h1>RESULT</h1><br>'+steps+'<br><h3>decimal value: '+str(P.int)+'</br><br> binary value: '+str(P.bin)+"</h3>"


#print(booths(12,13))

app = Flask(__name__)

@app.route('/')
def rend():
	return render_template("booths.html")

@app.route('/',methods=['POST'])
def post_method():
	m = request.form['multiplier']
	r = request.form['multiplicand']
	try:
		multiplier = int(m)
		multiplicand = int(r)
	except:
		return "<br><h1>Error</h1><br> One of the item found"

	print multiplier, multiplicand
	return booths(multiplier,multiplicand)

if __name__ == '__main__':
 	app.run(debug=True)

#html file: templates/booths.html
<!DOCTYPE html>
<html>
<body>
<h1>Booth's Multiplier</h1>
<h2>Enter two numbers </h2>
<form action="." method = "POST">
<input type="number" name="multiplier">*
<input type="number" name="multiplicand">
<input type="submit" name="myform" value="=">
</form>
<body>
<html>
```
## Explanation
```
Booth's algorithm can be implemented by repeatedly adding (with ordinary unsigned binary addition) one of two predetermined values A and S to a product P, then performing a rightward arithmetic shift on P. Let m and r be the multiplicand and multiplier, respectively; and let x and y represent the number of bits in m and r.

1. Determine the values of A and S, and the initial value of P. All of these numbers should have a length equal to (x + y + 1).
1.1 A: Fill the most significant (leftmost) bits with the value of m. Fill the remaining (y + 1) bits with zeros.
1.2 S: Fill the most significant bits with the value of (−m) in two's complement notation. Fill the remaining (y + 1) bits with zeros.
1.3. P: Fill the most significant x bits with zeros. To the right of this, append the value of r. Fill the least significant (rightmost) bit with a zero.
2. Determine the two least significant (rightmost) bits of P.
2.1 If they are 01, find the value of P + A. Ignore any overflow.
2.2 If they are 10, find the value of P + S. Ignore any overflow.
2.3 If they are 00, do nothing. Use P directly in the next step.
2.4 If they are 11, do nothing. Use P directly in the next step.
3. Arithmetically shift the value obtained in the 2nd step by a single place to the right. Let P now equal this new value.
4. Repeat steps 2 and 3 until they have been done y times.
5. Drop the least significant (rightmost) bit from P. This is the product of m and r.

multiplying −8 by 2 using 4 bits for the multiplicand and the multiplier:

A = 1 1000 0000 0
S = 0 1000 0000 0
P = 0 0000 0010 0
Perform the loop four times:
P = 0 0000 0010 0. The last two bits are 00.
	P = 0 0000 0001 0. Right shift.
P = 0 0000 0001 0. The last two bits are 10.
	P = 0 1000 0001 0. P = P + S.
	P = 0 0100 0000 1. Right shift.
P = 0 0100 0000 1. The last two bits are 01.
	P = 1 1100 0000 1. P = P + A.
	P = 1 1110 0000 0. Right shift.
P = 1 1110 0000 0. The last two bits are 00.
	P = 1 1111 0000 0. Right shift.

The product is 11110000 (after discarding the first and the last bit) which is −16.
```

# A4 dining philosophers
## Code
```
from pymongo import MongoClient
import threading
import random
import time
 

class Philosopher(threading.Thread):
 
    running = True
 
    def __init__(self, xname, forkOnLeft, forkOnRight):
        threading.Thread.__init__(self)
        self.name = xname
        self.forkOnLeft = forkOnLeft
        self.forkOnRight = forkOnRight
 
    def run(self):
        while(self.running):
            #  Philosopher is thinking (but really is sleeping).
            time.sleep( random.uniform(3,13))
            insertIntoMongo( self.name, 'is hungry')
            self.dine()
 
    def dine(self):
        fork1, fork2 = self.forkOnLeft, self.forkOnRight
 
        while self.running:
            fork1.acquire(True)
            locked = fork2.acquire(False)
            if locked: break
            fork1.release()
            insertIntoMongo( self.name, 'swaps forks')
            fork1, fork2 = fork2, fork1
        else:
            return
 
        self.dining()
        fork2.release()
        fork1.release()
 
    def dining(self):
        insertIntoMongo( self.name, 'starts eating' )        
        time.sleep(random.uniform(1,10))
        insertIntoMongo( self.name, 'finishes eating and leaves to think.')
        
 
def DiningPhilosophers():
    forks = [threading.Lock() for n in range(5)]
    philosopherNames = ('Aristotle','Kant','Buddha','Marx', 'Russel')
 
    philosophers= [Philosopher(philosopherNames[i], forks[i%5], forks[(i+1)%5]) \
            for i in range(5)]
 
    random.seed(507129)
    Philosopher.running = True
    for p in philosophers: p.start()
    time.sleep(100)
    Philosopher.running = False
    insertIntoMongo( 'Exit', 'Now we are finishing.')

def insertIntoMongo(key,value):
    result = db.diningCol.insert_one(
        {
            key: value
        } )

client = MongoClient("mongodb://127.0.0.1:27027")
db = client.test 
db.drop_collection("diningCol")
DiningPhilosophers()

cursor = db.diningCol.find()
for document in cursor:
    document.pop(u'_id')
    for key,value in document.items():
        print key + " " +value

```
## Explanation

start the server

```
mongod --dbpath /home/cipher/mongodb --port 27027 --bind_ip 127.0.0.1 --noprealloc
```

#Dining philosophers

The dining philosophers problem illustrates non-composability of low-level synchronization primitives like semaphores. It is a modification of a problem posed by Edsger Dijkstra.

Five philosophers, Aristotle, Kant, Spinoza, Marx, and Russell (the tasks) spend their time thinking and eating spaghetti. They eat at a round table with five individual seats. For eating each philosopher needs two forks (the resources). There are five forks on the table, one left and one right of each seat. When a philosopher cannot grab both forks it sits and waits. Eating takes random time, then the philosopher puts the forks down and leaves the dining room. After spending some random time thinking about the nature of the universe, he again becomes hungry, and the circle repeats itself.

It can be observed that a straightforward solution, when forks are implemented by semaphores, is exposed to deadlock. There exist two deadlock states when all five philosophers are sitting at the table holding one fork each. One deadlock state is when each philosopher has grabbed the fork left of him, and another is when each has the fork on his right.

```

# This solution avoids deadlock by never waiting for a fork while having one in hand. 
# If a philosopher acquires one fork but can't acquire the second, 
# he releases the first fork before waiting to acquire the other (which then becomes the first fork acquired).
# Dining philosophers, 5 Phillies with 5 forks. Must have two forks to eat.
#
# Deadlock is avoided by never waiting for a fork while holding a fork (locked)
# Procedure is to do block while waiting to get first fork, and a nonblocking
# acquire of second fork.  If failed to get second fork, release first fork,
# swap which fork is first and which is second and retry until getting both.
#

```


# A5 calculator
Create a new Android Application Project.  Let's say your Application Name is "Calculator", your Project Name is "Calculator", and your Package Name is "com.example.calculator".  Setup the project with the default settings.  Now replace MainActivity.java, activity_main.xml (in res/layout), & strings.xml  (in res/values) with the code below.  Next, create a new class "CalculatorBrain" and replace CalculatorBrain.java with the code below.  

compileSdkVersion 23
buildToolsVersion "21.1.2"

The calculator should run without errors! 
MainActivity.java
``` java 
package example.com.calculator;
import java.io.IOException;
import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by neeraj on 19-03-2016.
 */
public class MainActivity extends Activity implements OnClickListener {
    private TextView mCalculatorDisplay;
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS = "0123456789.";
    DecimalFormat df = new DecimalFormat("@###########");
    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalculatorBrain = new CalculatorBrain();
        mCalculatorDisplay = (TextView) findViewById(R.id.textView1);
        
        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonClearMemory).setOnClickListener(this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);

        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        if (findViewById(R.id.buttonSquareRoot) != null) {
            findViewById(R.id.buttonSquareRoot).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSquared) != null) {
            findViewById(R.id.buttonSquared).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonInvert) != null) {
            findViewById(R.id.buttonInvert).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSine) != null) {
            findViewById(R.id.buttonSine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonCosine) != null) {
            findViewById(R.id.buttonCosine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonTangent) != null) {
            findViewById(R.id.buttonTangent).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String buttonPressed = ((Button) v).getText().toString();
        if (DIGITS.contains(buttonPressed)) {
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }
            } else {
                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                    mCalculatorDisplay.setText(buttonPressed);
                }
                userIsInTheMiddleOfTypingANumber = true;
            }
        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }
            try {
                mCalculatorBrain.performOperation(buttonPressed);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
    }

}
```
CalculatorBrain.java
``` java
package example.com.calculator;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * Created by neeraj on 19-03-2016.
 */
public class CalculatorBrain {
    // 3 + 6 = 9
    // 3 & 6 are called the operand.
    // The + is called the operator.
    // 9 is the result of the operation.
    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;
    private double mCalculatorMemory;

    // operator types
    public static final String ADD = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

    public static final String CLEAR = "C" ;
    public static final String CLEARMEMORY = "MC";
    public static final String ADDTOMEMORY = "M+";
    public static final String SUBTRACTFROMMEMORY = "M-";
    public static final String RECALLMEMORY = "MR";
    public static final String SQUAREROOT = "√";
    public static final String SQUARED = "x²";
    public static final String INVERT = "1/x";
    public static final String TOGGLESIGN = "+/-";
    public static final String SINE = "sin";
    public static final String COSINE = "cos";
    public static final String TANGENT = "tan";

    // public static final String EQUALS = "=";

    // constructor
    public CalculatorBrain() {
        // initialize variables upon start
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
        mCalculatorMemory = 0;
    }
    public void setOperand(double operand) {
        mOperand = operand;
    }
    public double getResult() {
        return mOperand;
    }
    // used on screen orientation change
    public void setMemory(double calculatorMemory) {
        mCalculatorMemory = calculatorMemory;
    }
    // used on screen orientation change
    public double getMemory() {
        return mCalculatorMemory;
    }
    public String toString() {
        return Double.toString(mOperand);
    }
    protected double performOperation(String operator) throws IOException {
        if (operator.equals(CLEAR)) {
            mOperand = 0;
            mWaitingOperator = "";
            mWaitingOperand = 0;
            // mCalculatorMemory = 0;
        } else if (operator.equals(CLEARMEMORY)) {
            mCalculatorMemory = 0;
        } else if (operator.equals(ADDTOMEMORY)) {
            mCalculatorMemory = mCalculatorMemory + mOperand;
        } else if (operator.equals(SUBTRACTFROMMEMORY)) {
            mCalculatorMemory = mCalculatorMemory - mOperand;
        } else if (operator.equals(RECALLMEMORY)) {
            mOperand = mCalculatorMemory;
        } else if (operator.equals(SQUAREROOT)) {
            mOperand = Math.sqrt(mOperand);
        } else if (operator.equals(SQUARED)) {
            mOperand = mOperand * mOperand;
        } else if (operator.equals(INVERT)) {
            if (mOperand != 0) {
                mOperand = 1 / mOperand;
            }
        } else if (operator.equals(TOGGLESIGN)) {
            mOperand = -mOperand;
        } else if (operator.equals(SINE)) {
            mOperand = Math.sin(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else if (operator.equals(COSINE)) {
            mOperand = Math.cos(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else if (operator.equals(TANGENT)) {
            mOperand = Math.tan(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else {
            performWaitingOperation();
            mWaitingOperator = operator;
            mWaitingOperand = mOperand;
        }
        return mOperand;
    }

    protected void performWaitingOperation() {
        if (mWaitingOperator.equals(ADD)) {
            mOperand = mWaitingOperand + mOperand;
        } else if (mWaitingOperator.equals(SUBTRACT)) {
            mOperand = mWaitingOperand - mOperand;
        } else if (mWaitingOperator.equals(MULTIPLY)) {
            mOperand = mWaitingOperand * mOperand;
        } else if (mWaitingOperator.equals(DIVIDE)) {
            if (mOperand != 0) {
                mOperand = mWaitingOperand / mOperand;
            }
        }
    }
}
```
activity_main.xml  (in res/layout)
``` xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/functionPad"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin" >

    <LinearLayout
        android:id="@+id/row1"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <TextView
            android:id="@+id/textView1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="right"
            android:maxLines="1"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="0"
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:textSize="40sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row2"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonClearMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonClearMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonAddToMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonAddToMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSubtractFromMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSubtractFromMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonRecallMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonRecallMemory"
            android:textSize="25sp" />
    </LinearLayout>
    <LinearLayout
        android:id="@+id/row7"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonSquareRoot"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSquareRoot"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSquared"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSquared"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonInvert"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonInvert"
            android:textSize="17sp" />

        <Button
            android:id="@+id/buttonSine"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSine"
            android:textSize="17sp" />
        <Button
            android:id="@+id/buttonCosine"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonCosine"
            android:textSize="17sp" />
        <Button
            android:id="@+id/buttonTangent"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonTangent"
            android:textSize="17sp" />
    </LinearLayout>
    <LinearLayout
        android:id="@+id/row3"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonClear"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonClear"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonToggleSign"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonToggleSign"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonDivide"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonDivide"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonMultiply"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonMultiply"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row4"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/button7"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button7"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button8"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button8"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button9"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button9"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSubtract"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSubtract"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row5"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/button4"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button4"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button5"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button5"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button6"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button6"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonAdd"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonAdd"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row6"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".24"
        android:baselineAligned="false" >

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".75"
            android:orientation="vertical" >

            <LinearLayout
                android:id="@+id/linearLayout1"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight=".50"
                android:textSize="25sp" >

                <Button
                    android:id="@+id/button1"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".33"
                    android:text="@string/button1"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/button2"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".33"
                    android:text="@string/button2"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/button3"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".34"
                    android:text="@string/button3"
                    android:textSize="25sp" />
            </LinearLayout>

            <LinearLayout
                android:id="@+id/linearLayout2"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight=".50" >

                <Button
                    android:id="@+id/button0"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".66"
                    android:text="@string/button0"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/buttonDecimalPoint"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".34"
                    android:text="@string/buttonDecimalPoint"
                    android:textSize="25sp" />
            </LinearLayout>
        </LinearLayout>

        <Button
            android:id="@+id/buttonEquals"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonEquals"
            android:textSize="25sp" />
    </LinearLayout>


</LinearLayout>
```
strings.xml  (in res/values)
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Calculator</string>
    <string name="menu_settings">Settings</string>
    <string name="action_settings">Settings</string>
    <string name="button0">0</string>
    <string name="button1">1</string>
    <string name="button2">2</string>
    <string name="button3">3</string>
    <string name="button4">4</string>
    <string name="button5">5</string>
    <string name="button6">6</string>
    <string name="button7">7</string>
    <string name="button8">8</string>
    <string name="button9">9</string>
    <string name="buttonAdd">+</string>
    <string name="buttonSubtract">-</string>
    <string name="buttonMultiply">*</string>
    <string name="buttonDivide">/</string>
    <string name="buttonToggleSign">+/-</string>
    <string name="buttonDecimalPoint">.</string>
    <string name="buttonEquals">=</string>
    <string name="buttonClear">C</string>
    <string name="buttonClearMemory">MC</string>
    <string name="buttonAddToMemory">M+</string>
    <string name="buttonSubtractFromMemory">M-</string>
    <string name="buttonRecallMemory">MR</string>
    <string name="buttonSquareRoot">√</string>
    <string name="buttonSquared">x²</string>
    <string name="buttonInvert">1/x</string>
    <string name="buttonSine">sin</string>
    <string name="buttonCosine">cos</string>
    <string name="buttonTangent">tan</string>

</resources>
```

# A6 encryption
## code
```
#encryption.py
import hashlib
import sys
import os
import binascii

try:
    hash_name = sys.argv[1]
except IndexError:
    print('1st arg\n'+ str(hashlib.algorithms_guaranteed)+'\n2nd arg username','\n3rd arg password')
else:
    try:
        username = sys.argv[2] 
        passwd = sys.argv[3]
        password = bytes(passwd,'UTF-8')      
    except IndexError:    
        print('using default password: password')
        password = b'password'



    salt = os.urandom(32)
    chef_salt = binascii.hexlify(salt)
    filename = username+"_salt.key"
    salT_file = open(username+"_salt.key","w")
    print("Salt\n"+chef_salt.decode('utf-8'))
    salT_file.write(chef_salt.decode('utf-8'))
    salT_file.close()
    dk = hashlib.pbkdf2_hmac(hash_name, password, chef_salt, 100000,128)
    store_hash = binascii.hexlify(dk)
    filename = username+"_hash.hash"
    hash_file = open(username+"_hash.hash","w")
    print("hash\n"+store_hash.decode('utf-8'))
    hash_file.write(store_hash.decode('utf-8'))
    hash_file.close()

# verification.py
import hashlib
import sys
import os
import binascii

try:
    hash_name = sys.argv[1]
except IndexError:
    print('Specify the hash name as the first argument.')
else:
    try:
        username = sys.argv[2] 
        passwd = sys.argv[3]
        password = bytes(passwd,'UTF-8')      
    except IndexError:    
        print('using default password: password')
        password = b'password'

    
    try:#password = bytes(passwd,'UTF-8')
        salT_file = open(username+"_salt.key")
        chef_salt = salT_file.read()
        print("Salt\n"+chef_salt)
        dk = hashlib.pbkdf2_hmac(hash_name, password, bytes(chef_salt,'UTF-8'), 100000,128)
        hashk = binascii.hexlify(dk)
        new_hash = hashk.decode('utf-8')
        hash_file = open(username+"_hash.hash")
        old_hash  = hash_file.read()
        print("hash\n"+old_hash)
        if new_hash == old_hash:
            print("The user "+username+" has used valid password")
        else:
            print("Incorrect username or password")
    except OSError as e:
        print("Incorrect username or password")
    

```
## Explanation
```
Algo

To Store a Password

	1.Generate a long random salt using a CSPRNG.
	2.generate hash using standard cryptographic hash function such as SHA256
	3.key stretching using  PBKDF2 inpust name = standard cryptographic hash function such as SHA256, password = hash generated, salt = salt, round = 10000 aleast, dklen = 64 atleast length of derived key
	4.Save both the salt and the hash in the user's database record.
	
To Validate a Password

	1.Retrieve the user's salt and hash from the database.
	2.Prepend the salt to the given password and hash it using the same hash function.
	3.Compare the hash of the given password with the hash from the database. If they match, the password is correct. Otherwise, the password is incorrect.

```

# B1 8q
## code
```
#8q.py
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

#8q.json
{"matrix": [

			[0, 0, 1, 0, 0, 0, 0, 0], 
			[0, 0, 0, 0, 0, 1, 0, 0], 
			[0, 0, 0, 0, 0, 0, 0, 0], 
			[0, 0, 0, 0, 0, 0, 0, 1], 
			[0, 0, 0, 0, 0, 0, 0, 0], 
			[1, 0, 0, 0, 0, 0, 0, 0], 
			[0, 0, 0, 0, 0, 0, 0, 0], 
			[0, 0, 0, 0, 0, 1, 0, 0]]}

```
## Explanation
```
1. 8 QUEENS PROBLEM USING BACK TRACKING
2. BACK TRACKING
 Backtracking is a general algorithm for finding all (or some) solutions to some computational problem, that incrementally builds candidates to the solutions, and abandons each partial candidate ‘c’ ("backtracks") as soon as it determines that ‘c’ cannot possibly be completed to a valid solution.
 Backtracking is an important tool for solving constraint satisfaction problems, such as crosswords, verbal arithmetic, Sudoku, and many other puzzles.
3. 
 It is also the basis of the so-called logic programming languages such as Planner and Prolog.
 The term "backtrack" was coined by American mathematician D. H. Lehmer in the 1950s.
 The pioneer string-processing language SNOBOL (1962) may have been the first to provide a built-in general backtracking facility.
4. 
 The good example of the use of backtracking is the eight queens puzzle, that asks for all arrangements of eight queens on a standard chessboard so that no queen attacks any other.
 In the common backtracking approach, the partial candidates are arrangements of k queens in the first k rows of the board, all in different rows and columns.
 Any partial solution that contains two mutually attacking queens can be abandoned, since it cannot possibly be completed to a valid solution
5. WHAT IS 8 QUEEN PROBLEM?
 The eight queens puzzle is the problem of placing eight chess queens on an 8 8 chessboard so that no two queens attack each other.
 Thus, a solution requires that no two queens share the same row, column, or diagonal.
 The eight queens puzzle is an example of the more general n-queens problem of placing n queens on an n n chessboard, where solutions exist for all natural numbers n with the exception of 1, 2 and 3.
 The solution possibilities are discovered only up to 23 queen.
6. PROBLEM INVENTOR
 The puzzle was originally proposed in 1848 by the chess player Max Bezzel, and over the years, many mathematicians, including Gauss, have worked on this puzzle and its generalized n-queens problem.
7. SOLUTION INVENTOR
 The first solution for 8 queens were provided by Franz Nauck in 1850. Nauck also extended the puzzle to n-queens problem (on an n n board—a chessboard of arbitrary size).
 In 1874, S. Günther proposed a method of finding solutions by using determinants, and J.W.L. Glaisher refined this approach.
 Edsger Dijkstra used this problem in 1972 to illustrate the power of what he called structured programming.
 He published a highly detailed description of the development of a depth-first backtracking algorithm.
8. Formulation : States: any arrangementof 0 to 8 queens on theboard Initial state: 0 queens onthe board Successor function: adda queen in any squareGoal test: 8 queens onthe board, none attacked
9. BACKTRACKING CONCEPT
 Each recursive call attempts to place a queen in a specificcolumn.
 For a given call, the state of the board from previousplacements is known (i.e. where are the other queens?)
 Current step backtracking: If a placement within thecolumn does not lead to a solution, the queen is removed andmoved "down" the column
 Previous step backtracking: When all rows in a columnhave been tried, the call terminates and backtracks to theprevious call (in the previous column)
10. CONTINU..Pruning: If a queen cannot be placed into column i, do noteven try to place one onto column i+1 – rather, backtrack to column i-1 and move the queen that had beenplaced there.Using this approach we can reduce the number of potentialsolutions even more
11. BACKTRACKING DEMO FOR 4 QUEENS
12. STEPS REVISITED - BACKTRACKING1. Place the first queen in the left upper corner of the table.2. Save the attacked positions.3. Move to the next queen (which can only be placed to the next line).4. Search for a valid position. If there is one go to step 8.5. There is not a valid position for the queen. Delete it (the x coordinate is 0).6. Move to the previous queen.7. Go to step 4.8. Place it to the first valid position.9. Save the attacked positions.10. If the queen processed is the last stop otherwise go to step 3.
13. EIGHT QUEEN PROBLEM: ALGORITHMputQueen(row){ for every position col on the same row if position col is available place the next queen in position col if (row<8) putQueen(row+1); else success; remove the queen from position col}
14. THE PUTQUEEN RECURSIVE METHODvoid putQueen(int row) { for (int col=0;col<squares;col++) if (column[col]==available && leftDiagonal[row+col]==available && rightDiagonal[row-col]== available) { positionInRow[row]=col; column[col]=!available; leftDiagonal[row+col]=!available;
15. rightDiagonal[row-col]=!available; if (row< squares-1) putQueen(row+1); else print(" solution found”); column[col]=available; leftDiagonal[row+col]=available; rightDiagonal[row-col]= available; }}
16. SOLUTIONS• The eight queens puzzle has 92 distinct solutions.• If solutions that differ only by symmetry operations(rotations and reflections) of the board are counted as one the puzzle has 12 unique (or fundamental) solutions
17. COUNTING SOLUTIONS
 The following table gives the number of solutions for placing n queens on an n n board, both unique and distinct for n=1–26.
 Note that the six queens puzzle has fewer solutions than the five queens puzzle.
 There is currently no known formula for the exact number of solutions.
18. Order(“N”) Total Solutions Unique Solutions Exec time---------------------------------------------------------1 1 1 < 0 seconds2 0 0 < 0 seconds3 0 0 < 0 seconds4 2 1 < 0 seconds5 10 2 < 0 seconds6 4 1 < 0 seconds7 40 6 < 0 seconds8 92 12 < 0 seconds9 352 46 < 0 seconds10 724 92 < 0 seconds11 2,680 341 < 0 seconds12 14,200 1,787 < 0 seconds13 73,712 9,233 < 0 seconds14 365,596 45,752 0.2s
19. 15 2,279,184 285,053 1.9 s16 14,772,512 1,846,955 11.2 s17 95,815,104 11,977,939 77.2 s18 666,090,624 83,263,591 9.6 m19 4,968,057,848 621,012,754 75.0 m20 39,029,188,884 4,878,666,808 10.2 h21 314,666,222,712 39,333,324,973 87.2 h22 2,691,008,701,644 336,376,244,042 31.923 24,233,937,684,440 3,029,242,658,210 296 d24 227,514,171,973,736 28,439,272,956,934 ?25 2,207,893,435,808,352 275,986,683,743,434 ?26 22,317,699,616,364,044 2,789,712,466,510,289 ? (s = seconds m = minutes h = hours d = days)
20. JEFF SOMER’S ALGORITHM
 His algorithm for the N-Queen problem is considered as the fastest algorithm. He uses the concept of back tracking to solve this
 Previously the World’s fastest algorithm for the N-Queen problem was given by Sylvain Pion and Joel-Yann Fourre.
 His algorithm finds solutions up to 23 queens and uses bit field manipulation in BACKTRACKING.
 According to his program the maximum time taken to find all the solutions for a 18 queens problem is 00:19:26 where as in the normal back tracking algorithm it was 00:75:00.
21. USING NESTED LOOPS FOR SOLUTIONFor a 4x4 board, we could find the solutions like this: for(i0 = 0; i0 < 4; ++i0) { if(isSafe(board, 0, i0)) { board[0][i0] = true; for(i1 = 0; i1 < 4; ++i1) { if(isSafe(board, 1, i1)) { board[1][i1] = true; for(i2 = 0; i2 < 4; ++i2) { if(isSafe(board 2, i2)) { board[2][i2] = true; for(i3 = 0; i3 < 4; ++i3) { if(isSafe(board 3, i3)) { board[3][i3] = true;
22. { printBoard(board, 4);} board[3][i3] = false; } } board[2][i2] = false; } } board[1][i1] = false; } } board[0][i0] = false; } }
23. WHY NOT NESTED LOOP
 The nested loops are not so preferred because . It Does not scale to different sized boards
 You must duplicate identical code (place and remove). and error in one spot is hard to find
 The problem with this is that its not very programmer- friendly. We cant vary at runtime the size of the board were searching
24. 
 The major advantage of the backtracking algorithm is the abillity to find and count all the possible solutions rather than just one while offering decent speed.
 If we go through the algorithm for 8 queens 981 queen moves (876 position tests plus 105 backtracks) are required for the first solution alone. 16,704 moves (14,852 tests and 1852 backtracks) are needed to find all 92 solutions.
 Given those figures, its easy to see why the solution is best left to computers.
```

# B5 plagiarism
## code
```
//index.jsp
<%-- 
    Document   : index
    Created on : 9 Mar, 2016, 11:06:22 AM
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="Plagarism">
     
       Enter the first input <textarea name="File1" rows="5" cols="10">
       </textarea><br>
       Enter the second input<textarea name="File2" rows="5" cols="10">
       </textarea><br>
       <input type="submit" value="Check Plagarism" name="btn"/>
          </form>
    </body>
</html>

//Plagarism.java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author
 */
@WebServlet(urlPatterns = {"/Plagarism"})
public class Plagarism extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Plagarism</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String fileOne = request.getParameter("File1");
            String fileTwo = request.getParameter("File2");
            out.println("Comparing the 2 files......");out.println("<br>");
            String[] str1 = fileOne.split("\\s");
            String[] str2 = fileTwo.split("\\s");
            
            for (int j = 0; j < str1.length; j++) { //alphabetical order sorting of str1
                for (int i = j + 1; i < str1.length; i++) {
                    if (str1[i].compareTo(str1[j]) < 0) {
                      String t = str1[j];
                      str1[j] = str1[i];
                      str1[i] = t;
                    }
                }              
            }
            
            int results; double plag=0;
            for (int key = 0; key < str2.length; key++){
                results = searchString(str1, str2[key]);
                if (results == 1) {
                    plag = plag+1;
                }
            
            }
            double plagPercent = (plag/str2.length)*100;
            
            out.println("Similarity between two text is "+ plagPercent);
            out.println("<br>");
            out.println("Plagrism "+((plagPercent>50.0) ? "exist" : "does not exist"));
            
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    //simple binary search
    public static int searchString(String[] str1, String key) {
        int first = 0;
        int last  = str1.length;

        while (first < last) {
            int mid = (first + last) / 2;
            if (key.compareTo(str1[mid]) < 0) {
                last = mid;
            } else if (key.compareTo(str1[mid]) > 0) {
                first = mid + 1;
            } else {
                return 1;
            }
        }
        return -1;
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
//test.java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class mytest3 
{
    

    public static void main(String[] args) {
       

        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8084/plagraism/";
        driver.get(baseUrl);
        String expected = "JSP Page";
        String actual = "";
        driver.manage().window().maximize();
        actual = driver.getTitle();
        if (actual.equals(expected)) {
            System.out.println("Title test passed");
        } else {
            System.out.println("Title test failed");}
            WebElement text=driver.findElement(By.name("File1"));
            text.sendKeys("hello");
            WebElement text1=driver.findElement(By.name("File2"));
            text1.sendKeys("hiee");
           
            WebElement btn=driver.findElement(By.name("btn"));
            btn.click();
            System.out.println(" test script sucessful");
            driver.close();
    
    }
}
```

# BD1 sha
## code
```
#sha1.py
def sha1(data):
    bytes = ""

    h0 = 0x67452301		# Initialize variables
    h1 = 0xEFCDAB89
    h2 = 0x98BADCFE
    h3 = 0x10325476
    h4 = 0xC3D2E1F0

    for n in range(len(data)):
        bytes+='{0:08b}'.format(ord(data[n]))
    bits = bytes+"1"
    pBits = bits
    #pad until length equals 448 mod 512
    while len(pBits)%512 != 448:
        pBits+="0"
    #append the original length
    pBits+='{0:064b}'.format(len(bits)-1)

    
    def chunks(l, n):
        return [l[i:i+n] for i in range(0, len(l), n)]

    def rol(n, b):
        return ((n << b) | (n >> (32 - b))) & 0xffffffff

    for c in chunks(pBits, 512): 
        words = chunks(c, 32)
        w = [0]*80
        for n in range(0, 16):
            w[n] = int(words[n], 2)
        for i in range(16, 80):
            w[i] = rol((w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16]), 1)  

        a = h0
        b = h1
        c = h2
        d = h3
        e = h4

        #Main loop

        for i in range(0, 80):
            if 0 <= i <= 19:
                #f = (b & c) | ((~b) & d)
				f = b ^ c ^ d
				k = 0x5A827999
            elif 20 <= i <= 39:
                f = b ^ c ^ d
                k = 0x6ED9EBA1
            elif 40 <= i <= 59:
                #f = (b & c) | (b & d) | (c & d)
				f = b ^ c ^ d
				k = 0x8F1BBCDC
            elif 60 <= i <= 79:
                #f = b ^ c ^ d
				f = b ^ c ^ d
				k = 0xCA62C1D6

            temp = rol(a, 5) + f + e + k + w[i] & 0xffffffff
            e = d
            d = c
            c = rol(b, 30)
            b = a
            a = temp

        h0 = h0 + a & 0xffffffff
        h1 = h1 + b & 0xffffffff
        h2 = h2 + c & 0xffffffff
        h3 = h3 + d & 0xffffffff
        h4 = h4 + e & 0xffffffff

    return '%08x%08x%08x%08x%08x' % (h0, h1, h2, h3, h4)
#print(sha1("hello"))

#server.py
import socket
import sha1
ss = socket.socket(socket.AF_INET, socket.SOCK_STREAM,0)
ss.bind(("", 5008))
ss.listen(5)

print "TCPServer1 Waiting for client on port 7998"

while 1:
        client_socket, address = ss.accept()
        
        print "I got a connection from ", address
        
        stri = client_socket.recv(1024)
        
        alist = stri.split('\n')
        print alist[0]
        checkHash=sha1.sha1(str(alist[0]))
        print checkHash
        if checkHash == alist[1]:
                print "Message is valid"
                client_socket.send("Message is Verified")
        else:
                client_socket.send("Message could not be verified")
        client_socket.close()
        ss.close()
        print "Data sent!"

        break;


'''
cipher@blackfury-HP-eNVy:~/be-2/BE1$ python server.py 
TCPServer1 Waiting for client on port 7998
I got a connection from  ('127.0.0.1', 44144)
Hello World!
2b72790bf888c4427287b3d79a8c8a3320c61986
Message is valid
Data sent!

'''
#client.py
# TCP client example
import socket
import sha1
TCP_IP = '127.0.0.1'
TCP_PORT = 5008
BUFFER_SIZE = 1024

MESSAGE = raw_input("Enter a message: ")
digest = sha1.sha1(MESSAGE)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(MESSAGE+"\n")#could directly use s.send(MESSAGE+"\n"+digest) i dont know why but someone told me...
s.send(digest)#could comment this.
data = s.recv(BUFFER_SIZE)
s.close()

print "received data:", data

```
## Explanation
```
Note 1: All variables are unsigned 32-bit quantities and wrap modulo 232 when calculating, except for
        ml, the message length, which is a 64-bit quantity, and
        hh, the message digest, which is a 160-bit quantity.
Note 2: All constants in this pseudo code are in big endian.
        Within each word, the most significant byte is stored in the leftmost byte position

Initialize variables:

h0 = 0x67452301
h1 = 0xEFCDAB89
h2 = 0x98BADCFE
h3 = 0x10325476
h4 = 0xC3D2E1F0

ml = message length in bits (always a multiple of the number of bits in a character).

Pre-processing:
append the bit '1' to the message e.g. by adding 0x80 if message length is a multiple of 8 bits.
append 0 ≤ k < 512 bits '0', such that the resulting message length in bits
   is congruent to −64 ≡ 448 (mod 512)
append ml, in a 64-bit big-endian integer. Thus, the total length is a multiple of 512 bits.

Process the message in successive 512-bit chunks:
break message into 512-bit chunks
for each chunk
    break chunk into sixteen 32-bit big-endian words w[i], 0 ≤ i ≤ 15

    Extend the sixteen 32-bit words into eighty 32-bit words:
    for i from 16 to 79
        w[i] = (w[i-3] xor w[i-8] xor w[i-14] xor w[i-16]) leftrotate 1

    Initialize hash value for this chunk:
    a = h0
    b = h1
    c = h2
    d = h3
    e = h4

    Main loop:[44][2]
    for i from 0 to 79
        if 0 ≤ i ≤ 19 then
            f = (b and c) or ((not b) and d)
            k = 0x5A827999
        else if 20 ≤ i ≤ 39
            f = b xor c xor d
            k = 0x6ED9EBA1
        else if 40 ≤ i ≤ 59
            f = (b and c) or (b and d) or (c and d) 
            k = 0x8F1BBCDC
        else if 60 ≤ i ≤ 79
            f = b xor c xor d
            k = 0xCA62C1D6

        temp = (a leftrotate 5) + f + e + k + w[i]
        e = d
        d = c
        c = b leftrotate 30
        b = a
        a = temp

    Add this chunk's hash to result so far:
    h0 = h0 + a
    h1 = h1 + b 
    h2 = h2 + c
    h3 = h3 + d
    h4 = h4 + e

Produce the final hash value (big-endian) as a 160 bit number:
hh = (h0 leftshift 128) or (h1 leftshift 96) or (h2 leftshift 64) or (h3 leftshift 32) or h4
The number hh is the message digest, which can be written in hexadecimal (base 16), but is often written using Base64 binary to ASCII text encoding.

The constant values used are chosen to be nothing up my sleeve numbers: the four round constants k are 230 times the square roots of 2, 3, 5 and 10. The first four starting values for h0 through h3 are the same with the MD5 algorithm, and the fifth (for h4) is similar.
```

# BD2 random number
## code
```
#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Based on the pseudocode in https://en.wikipedia.org/wiki/Mersenne_Twister.
Generates uniformly distributed 32-bit integers in the range [0, 232 − 1] with the MT19937 algorithm


"""
# Create a length 624 list to store the state of the generator
MT = [0 for i in xrange(624)]
index = 0

# To get last 32 bits
bitmask_1 = (2 ** 32) - 1

# To get 32. bit
bitmask_2 = 2 ** 31

# To get last 31 bits
bitmask_3 = (2 ** 31) - 1

def initialize_generator(seed):
    "Initialize the generator from a seed"
    global MT
    global bitmask_1
    MT[0] = seed
    for i in xrange(1,624):
        MT[i] = ((1812433253 * MT[i-1]) ^ ((MT[i-1] >> 30) + i)) & bitmask_1


def extract_number():
    """
    Extract a tempered pseudorandom number based on the index-th value,
    calling generate_numbers() every 624 numbers
    """
    global index
    global MT
    if index == 0:
        generate_numbers()
    y = MT[index]
    y ^= y >> 11
    y ^= (y << 7) & 2636928640
    y ^= (y << 15) & 4022730752
    y ^= y >> 18

    index = (index + 1) % 624
    return y

def generate_numbers():
    "Generate an array of 624 untempered numbers"
    global MT
    for i in xrange(624):
        y = (MT[i] & bitmask_2) + (MT[(i + 1 ) % 624] & bitmask_3)
        MT[i] = MT[(i + 397) % 624] ^ (y >> 1)
        if y % 2 != 0:
            MT[i] ^= 2567483615

if __name__ == "__main__":
    from datetime import datetime
    now = datetime.now()
    initialize_generator(now.microsecond)
    for i in xrange(5):
        "Print 5 random numbers as an example"
        print extract_number()
        
```
## Explanation
```
 // Create a length n array to store the state of the generator
 int[0..n-1] MT
 int index := n+1
 const int lower_mask = (1 << r) - 1 // That is, the binary number of r 1's
 const int upper_mask = lowest w bits of (not lower_mask)
 
 // Initialize the generator from a seed
 function seed_mt(int seed) {
     index := n
     MT[0] := seed
     for i from 1 to (n - 1) { // loop over each element
         MT[i] := lowest w bits of (f * (MT[i-1] xor (MT[i-1] >> (w-2))) + i)
     }
 }
 
 // Extract a tempered value based on MT[index]
 // calling twist() every n numbers
 function extract_number() {
     if index >= n {
         if index > n {
           error "Generator was never seeded"
           // Alternatively, seed with constant value; 5489 is used in reference C code[44]
         }
         twist()
     }
 
     int y := MT[index]
     y := y xor ((y >> u) and d)
     y := y xor ((y << s) and b)
     y := y xor ((y << t) and c)
     y := y xor (y >> l)
 
     index := index + 1
     return lowest w bits of (y)
 }
 
 // Generate the next n values from the series x_i 
 function twist() {
     for i from 0 to (n-1) {
         int x := (MT[i] and upper_mask)
                   + (MT[(i+1) mod n] and lower_mask)
         int xA := x >> 1
         if (x mod 2) != 0 { // lowest bit of x is 1
             xA := xA xor a
         }
         MT[i] := MT[(i + m) mod n] xor xA
     }
     index := 0
 }

```

# BD5 IDS
## code

```
import java.awt.Container;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class IDS1 {
	
	public static String line;
	public static JFrame fr;
	public static Container c;
	public static JTextArea tx;
	public static JScrollPane js;
	public static JButton ids_on,ids_off,disp_blocked_ip,disp_rules,unblock_ip;
	public static JTextField ip;
	
	
	public IDS1(){
		fr=new JFrame();
		c=fr.getContentPane();
		c.setLayout(null);
		
		fr.setTitle("Intrusion Detection System Config");
		
		fr.setBounds(0, 0, 920, 550);
		
		//components on the frame
		 tx=new JTextArea();
		js=new JScrollPane(tx,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		ids_on=new JButton("IDS ON");
		ids_off=new JButton("IDS OFF");
		disp_blocked_ip=new JButton("Blocked IPs");
		disp_rules=new JButton("Firewall Rules");
		ip=new JTextField("Enter Ip address");
		unblock_ip=new JButton("Unblock Ip");
		
		
		//setting bounds
		js.setBounds(5, 20, 900, 400);
		ids_on.setBounds(10, 470, 120, 50);
		ids_off.setBounds(10, 470, 120, 50);
		disp_blocked_ip.setBounds(140, 470, 120, 50);
		disp_rules.setBounds(270, 470, 120, 50);
		unblock_ip.setBounds(410, 470, 120, 50);
		ip.setBounds(410, 520, 220, 50);
		
		ip.setVisible(false);
		
		//adding components on the frame container
		c.add(js);
		c.add(ids_on);
		c.add(ids_off);
		c.add(disp_blocked_ip);
		c.add(unblock_ip);
		c.add(ip);
		c.add(disp_rules);
		
		
		//all button's action listeners
		
		ids_on.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ids_on.setVisible(false);
				ids_off.setVisible(true);
				exec_commands("sudo service psad start");
				exec_commands("sudo service psad status");
			}
		});

		ids_off.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ids_on.setVisible(true);
				ids_off.setVisible(false);
				
				exec_commands("sudo service psad stop");
				exec_commands("sudo service psad status");
			}
		});
				
		disp_blocked_ip.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exec_commands("sudo iptables -L INPUT -v -n --line-numbers");
				
			}
		});
				
		disp_rules.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//exec_commands("sudo iptables -N TRAFFIC_ACCT");//own traffic chain in order to avoid changes in firewall rules
				//exec_commands("sudo iptables -I FORWARD -j TRAFFIC_ACCT");//forwarding all traffic to my created chain
				//exec_commands("iptables -A TRAFFIC_ACCT -p tcp && iptables -A TRAFFIC_ACCT -p ip && iptables -A TRAFFIC_ACCT -p icmp");
				exec_commands("sudo iptables -L");
				
			}
		});

		unblock_ip.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String response = JOptionPane.showInputDialog(null,"Enter IP address",
						  JOptionPane.QUESTION_MESSAGE);
				exec_commands("sudo iptables -D INPUT -s "+response+" -j DROP");
				
			}
		});
				
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Main method
public static void main(String arr[]) throws Exception{
	IDS1 ids=new IDS1();
}

//method for execute the commands
public void exec_commands(String cmd){
	try {
        Runtime rt = Runtime.getRuntime();
        //Process pr = rt.exec("cmd /c dir");
        Process pr = rt.exec(cmd);

        BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

        String line=null;
        tx.setText("");
        while((line=input.readLine()) != null) {
            System.out.println(line);
           
            //display cmd output on textarea tx
            
            tx.append(line+"\n");
        }
        //int exitVal = pr.waitFor();
       // System.out.println("Exited with error code "+exitVal);
       
    	} catch(Exception e) {
        System.out.println(e.toString());
        e.printStackTrace();
    	}
	}
}
```

## Explantion
```
https://www.digitalocean.com/community/tutorials/how-to-use-psad-to-detect-network-intrusion-attempts-on-an-ubuntu-vps


sudo /sbin/iptables -A INPUT -s 65.55.44.102 -j DROP ---------------add ip address to be blocked

sudo apt-get install psad

sudo iptables -A INPUT -j LOG ---------------------------For logging purpose(optional)
sudo iptables -A FORWARD -j LOG--------------------------

sudo iptables -F ---------------Flush rules
sudo iptables -L ---------------List all rules

sudo iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT --------------------- explicitly allow all traffic related to
an existing connection.

sudo iptables -A INPUT -p tcp --dport 22 -j ACCEPT ---------------------services that we wish to keep open to the public

sudo iptables -A INPUT -j LOG
sudo iptables -A FORWARD -j LOG

sudo iptables -P INPUT DROP ------------------DROP all extraneous messages

sudo apt-get install iptables-persistent ------------------- makes these rules persistent(optional)
sudo service iptables-persistent start(opyional)

sudo gedit /etc/psad/psad.conf -----open and change e-mail address to your email or any other and can also change the domain as per your need

sudo psad --sig-update -------------------Update the signatures of the definitions of the known attacks

sudo service psad restart --------------Restart the psad IDS


-----------end-------------
```
