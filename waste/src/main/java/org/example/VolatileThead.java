package org.example;

public class VolatileThead extends Thread {
    
    private boolean stop=false;
    public void stopMe(){
        stop=true;
    }
    public void run(){
        int i=0;
        while(!stop){
            i++;
            System.out.println("Thread Stop Fail!");
        }
        System.out.println("Thread Stop Success!");
    }
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        VolatileThead v = new VolatileThead();
        v.start();
        //Thread.sleep(1000);
        v.stopMe();
        Thread.sleep(1000);

    }

}