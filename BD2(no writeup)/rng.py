"""
This is code for a very simple linear congruential random number generator.
                     Xn=a*Xn-1 mod m
                     Xo= seed (usually system time)

Author     : Subramanian Venkatesan
Language   : Python
"""
import time
class RandomNumberGenerator:
    #constructor
    def __init__(self,seed=None):
        self.randomints=[];
        self.count=0; # no of random numbers generated in the sequence.
        self.a=1664525
        self.m=4294967296 # 2^32
        if(seed is None):
            self.seed=time.mktime(time.gmtime()) # get epoch
        else:
            self.seed=seed
    # rand function
    def rand(self,a=None,b=None): 
        if self.count==0:
           # append X1=a*seed mod m
           self.randomints.append((self.a*self.seed)%self.m)
        else:
	   # append Xn=a*Xn-1 mod m
           self.randomints.append((self.a*self.randomints[-1])%self.m)
	#increment the count
	self.count+=1
        if a is None and b is None: 
            return self.randomints[-1]/self.m
        else:
            return a + (self.randomints[-1]/self.m) * (b-a)     
def main():
    x=RandomNumberGenerator()
    for i in range(0,5):
         print x.rand(5,10)
if __name__=="__main__":
    main()

