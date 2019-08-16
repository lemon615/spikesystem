package com.suny.test;

public class ManyThread {
    public static void main(String[] args){
        RunnableDemo runnable1 = new RunnableDemo("thread1");
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        //runnable1.start();
        RunnableDemo runnable2 = new RunnableDemo("thread2");
        //runnable2.start();
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
}

class RunnableDemo implements Runnable{

    //private Thread thread;
    private String threadName;

    public RunnableDemo(String threadName){
        this.threadName = threadName;
        System.out.println("creating thread " + threadName);
    }

    @Override
    public void run() {
        for (int i=0; i<=10; i++){
            try {
                System.out.println("Thread " + threadName + " is " + i);
                //Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Thread " + threadName + " interrupted");
            }

        }
        System.out.println("Thread " + threadName + "exiting");

    }

/*    public void start(){
        System.out.println("Thread " + threadName + " start!!!");
        if (null == thread) {
            thread = new Thread(this, threadName);
            thread.start();
        }
    }*/
}