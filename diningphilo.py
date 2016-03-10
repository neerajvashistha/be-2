import threading
import random
import time
 
# Dining philosophers, 5 Phillies with 5 forks. Must have two forks to eat.
#
# Deadlock is avoided by never waiting for a fork while holding a fork (locked)
# Procedure is to do block while waiting to get first fork, and a nonblocking
# acquire of second fork.  If failed to get second fork, release first fork,
# swap which fork is first and which is second and retry until getting both.
#  
# See discussion page note about 'live lock'.
 
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
            print '%s is hungry.' % self.name
            self.dine()
 
    def dine(self):
        fork1, fork2 = self.forkOnLeft, self.forkOnRight
 
        while self.running:
            fork1.acquire(True)
            locked = fork2.acquire(False)
            if locked: break
            fork1.release()
            print '%s swaps forks' % self.name
            fork1, fork2 = fork2, fork1
        else:
            return
 
        self.dining()
        fork2.release()
        fork1.release()
 
    def dining(self):			
        print '%s starts eating '% self.name
        time.sleep(random.uniform(1,10))
        print '%s finishes eating and leaves to think.' % self.name
 
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
    print ("Now we're finishing.")
    exit()
DiningPhilosophers()
'''
cipher@blackfury-HP-eNVy:~/be-2$ python diningphilo.py 
Russel is hungry.
Russel starts eating 
Aristotle is hungry.
Marx is hungry.
Marx swaps forks
Kant is hungry.
Kant starts eating 
Buddha is hungry.
Russel finishes eating and leaves to think.
Marx starts eating 
 Aristotle swaps forks
Kant finishes eating and leaves to think.
Buddha swaps forks
 Aristotle starts eating 
Marx finishes eating and leaves to think.
Buddha starts eating 
Russel is hungry.
Russel swaps forks
Aristotle finishes eating and leaves to think.
Russel starts eating 
Buddha finishes eating and leaves to think.
Marx is hungry.
Marx swaps forks
Russel finishes eating and leaves to think.
Marx starts eating 
Aristotle is hungry.
Aristotle starts eating 
Kant is hungry.
Marx finishes eating and leaves to think.
Buddha is hungry.
Buddha starts eating 
Russel is hungry.
Russel swaps forks
Buddha finishes eating and leaves to think.
Aristotle finishes eating and leaves to think.
Russel starts eating 
 Kant starts eating 
Buddha is hungry.
Marx is hungry.
Marx swaps forks
Aristotle is hungry.
Kant finishes eating and leaves to think.
Buddha starts eating 
Russel finishes eating and leaves to think.
Marx swaps forks
 Aristotle starts eating 
Buddha finishes eating and leaves to think.
Marx starts eating 
Russel is hungry.
Aristotle finishes eating and leaves to think.
Marx finishes eating and leaves to think.
Russel starts eating 
Kant is hungry.
Kant starts eating 
Buddha is hungry.
Aristotle is hungry.
Marx is hungry.
Marx swaps forks
Kant finishes eating and leaves to think.
Buddha starts eating 
Russel finishes eating and leaves to think.
Aristotle starts eating 
Marx swaps forks
Buddha finishes eating and leaves to think.
Marx starts eating 
Russel is hungry.
Kant is hungry.
Buddha is hungry.
Buddha swaps forks
Marx finishes eating and leaves to think.
Russel swaps forks
 Buddha starts eating 
Aristotle finishes eating and leaves to think.
Kant swaps forks
 Russel starts eating 
Buddha finishes eating and leaves to think.
Kant starts eating 
Marx is hungry.
Marx swaps forks
Russel finishes eating and leaves to think.
Marx starts eating 
Kant finishes eating and leaves to think.
Aristotle is hungry.
Aristotle starts eating 
Buddha is hungry.
Buddha swaps forks
Marx finishes eating and leaves to think.
Buddha starts eating 
Kant is hungry.
Buddha finishes eating and leaves to think.
Aristotle finishes eating and leaves to think.
Kant starts eating 
Russel is hungry.
Russel starts eating 
Marx is hungry.
Marx swaps forks
Russel finishes eating and leaves to think.
Marx starts eating 
Kant finishes eating and leaves to think.
Buddha is hungry.
Buddha swaps forks
Russel is hungry.
Marx finishes eating and leaves to think.
Buddha starts eating 
Russel starts eating 
Kant is hungry.
Kant swaps forks
Aristotle is hungry.
Russel finishes eating and leaves to think.
Aristotle starts eating 
Buddha finishes eating and leaves to think.
Kant swaps forks
Now we're finishing.
Aristotle finishes eating and leaves to think.
Kant starts eating 
Marx is hungry.
Buddha is hungry.
Kant finishes eating and leaves to think.
Russel is hungry.
'''