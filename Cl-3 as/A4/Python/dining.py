
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


'''
cipher@blackfury-HP-eNVy:~/Desktop/be-2/Cl-3 as/A4/Python$ python dining.py 
Russel is hungry
Russel starts eating
Russel finishes eating and leaves to think.
Aristotle is hungry
Aristotle starts eating
Marx is hungry
Marx starts eating
Kant is hungry
Buddha is hungry
Buddha swaps forks
Marx finishes eating and leaves to think.
Buddha starts eating
Aristotle finishes eating and leaves to think.
Kant swaps forks
Russel is hungry
Russel starts eating
Buddha finishes eating and leaves to think.
Kant starts eating
Marx is hungry
Marx swaps forks
Kant finishes eating and leaves to think.
Russel finishes eating and leaves to think.
Marx starts eating
Buddha is hungry
Buddha swaps forks
Aristotle is hungry
Aristotle starts eating
Marx finishes eating and leaves to think.
Buddha starts eating
Kant is hungry
Aristotle finishes eating and leaves to think.
Kant swaps forks
Buddha finishes eating and leaves to think.
Kant starts eating
Russel is hungry
Russel starts eating
Kant finishes eating and leaves to think.
Aristotle is hungry
Marx is hungry
Marx swaps forks
Buddha is hungry
Buddha starts eating
Russel finishes eating and leaves to think.
Marx swaps forks
Aristotle starts eating
Buddha finishes eating and leaves to think.
Marx starts eating
Kant is hungry
Aristotle finishes eating and leaves to think.
Kant starts eating
Buddha is hungry
Marx finishes eating and leaves to think.
Russel is hungry
Russel starts eating
Kant finishes eating and leaves to think.
Buddha starts eating
Buddha finishes eating and leaves to think.
Marx is hungry
Marx swaps forks
Aristotle is hungry
Kant is hungry
Kant starts eating
Russel finishes eating and leaves to think.
Marx starts eating
Aristotle swaps forks
Kant finishes eating and leaves to think.
Aristotle starts eating
Buddha is hungry
Buddha swaps forks
Marx finishes eating and leaves to think.
Buddha starts eating
Aristotle finishes eating and leaves to think.
Buddha finishes eating and leaves to think.
Kant is hungry
Kant starts eating
Russel is hungry
Russel starts eating
Aristotle is hungry
Marx is hungry
Marx swaps forks
Kant finishes eating and leaves to think.
Russel finishes eating and leaves to think.
Marx starts eating
Aristotle starts eating
Buddha is hungry
Buddha swaps forks
Marx finishes eating and leaves to think.
Buddha starts eating
Aristotle finishes eating and leaves to think.
Buddha finishes eating and leaves to think.
Russel is hungry
Russel starts eating
Aristotle is hungry
Kant is hungry
Kant starts eating
Kant finishes eating and leaves to think.
Marx is hungry
Marx swaps forks
Russel finishes eating and leaves to think.
Aristotle starts eating
Marx starts eating
Buddha is hungry
Buddha swaps forks
Kant is hungry
Aristotle finishes eating and leaves to think.
Kant starts eating
Marx finishes eating and leaves to think.
Buddha swaps forks
Exit Now we are finishing.

'''