package com.suny.test;

public class ThreadTest {
    public static  void main(String[] args){
        ThreadDemo threadDemo1 = new ThreadDemo("thread1");
        threadDemo1.start();
        ThreadDemo threadDemo2 = new ThreadDemo("thread2");
        threadDemo2.start();
    }
}

class ThreadDemo extends Thread{

    private  String threadName;
    public ThreadDemo(String threadName){
        this.threadName = threadName;
        System.out.println("create Thread " + threadName);
    }
    @Override
    public void run() {
        for(int i=0; i<=10; i++){
            System.out.println("run Thread " + threadName  + " " + i);
        }
        System.out.println("Thread " + threadName + " exiting");
    }

    @Override
    public synchronized void start() {
        super.start();
        System.out.println("Thread " + threadName + " start");
    }
}