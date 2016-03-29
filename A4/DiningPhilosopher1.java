import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class DiningPhilosopher1 {

    public static void main(String[] args) {
        // TODO code application logic here
        final int num=6;    //number of philosophers
        Lock fork[]=new ReentrantLock[num];
        Philosopher P[]=new Philosopher[num];
        Thread T[]=new Thread[num];
        for(int i=0;i<num;i++){
            fork[i]=new ReentrantLock();
        }

        for(int i=0;i<num;i++){
            P[i]=new Philosopher(fork[i==0?num-1:i-1], fork[i], " "+i);
            System.out.println("P: "+i+" got L: "+(i==0?num-1:i-1)+" R: "+i);
            T[i]=new Thread(P[i]);
        }

        try{
            MongoClient mongoclient=new MongoClient("localhost");
            System.out.println("Connection to mongodb sucessful");
            DB db=mongoclient.getDB("mydb");
            System.out.println("database mydb created");
            DBCollection coll=db.createCollection("mycol",null);
            System.out.println("collection mycol created");
        }catch(Exception e){
            
        }
        
        for(int i=0;i<num;i++){
            T[i].start();
        }
    }
}

class Philosopher implements Runnable{
    Lock leftFork,rightFork;
    String name;

    public Philosopher(Lock leftFork, Lock rightFork, String name) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.name = name;
    }
    
    public void eat() throws InterruptedException{
        try {
            leftFork.lock();
            rightFork.lock();
            MongoClient mongoclient=new MongoClient("localhost");
            DB db=mongoclient.getDB("mydb");
            DBCollection coll=db.getCollection("mycol");
            System.out.println(name +"eating.....");
            BasicDBObject doc1=new BasicDBObject(name , "eating...");
            coll.insert(doc1);
            System.out.print("Philosopher "+name+": Eating...getting ma booze!.... feeling sleepy...");
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            MongoClient mongoclient=new MongoClient("localhost");
            DB db=mongoclient.getDB("mydb");
            DBCollection coll=db.getCollection("mycol");
            BasicDBObject doc2=new BasicDBObject(name ,"done eating now thinking...");
            coll.insert(doc2);
            leftFork.unlock();
            rightFork.unlock();
        }
    }
    
    public void think() throws InterruptedException{

        MongoClient mongoclient=new MongoClient("localhost");
        DB db=mongoclient.getDB("mydb");
        DBCollection coll=db.getCollection("mycol");
            System.out.println(name +"thinking...");
            BasicDBObject doc=new BasicDBObject(name ,"thinking...");
            coll.insert(doc);
        System.out.println("Philosopher "+name+": What is life???... what is existance???...such thoughts make me feel hungry..");
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        try {
            eat();
            System.out.println("Philosopher "+name+" cha j.one jhala...");
            think();
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
/*
P: 0 got L: 5 R: 0
P: 1 got L: 0 R: 1
P: 2 got L: 1 R: 2
P: 3 got L: 2 R: 3
P: 4 got L: 3 R: 4
P: 5 got L: 4 R: 5
Connection to mongodb sucessful
database mydb created
collection mycol created
 0eating.....
 3eating.....
Philosopher  0: Eating...getting ma booze!.... feeling sleepy...Philosopher  3: Eating...getting ma booze!.... feeling sleepy... 0thinking...
 3thinking...
 5eating.....
Philosopher  3: What is life???... what is existance???...such thoughts make me feel hungry..
Philosopher  0: What is life???... what is existance???...such thoughts make me feel hungry..
Philosopher  5: Eating...getting ma booze!.... feeling sleepy... 2eating.....
Philosopher  2: Eating...getting ma booze!.... feeling sleepy... 4eating.....
 5thinking...
Philosopher  4: Eating...getting ma booze!.... feeling sleepy...Philosopher  5: What is life???... what is existance???...such thoughts make me feel hungry..
 2thinking...
 1eating.....
Philosopher  2: What is life???... what is existance???...such thoughts make me feel hungry..
Philosopher  1: Eating...getting ma booze!.... feeling sleepy... 4thinking...
Philosopher  4: What is life???... what is existance???...such thoughts make me feel hungry..
 1thinking...
Philosopher  1: What is life???... what is existance???...such thoughts make me feel hungry..
*/