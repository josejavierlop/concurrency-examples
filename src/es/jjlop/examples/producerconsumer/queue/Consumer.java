package es.jjlop.examples.producerconsumer.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable {
	private BlockingQueue<String> bq;

    public Consumer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    public void run() {
    	try{
	        for (String message = bq.take();
	             ! message.equals("DONE");
	             message = bq.take()) {
	            System.out.format("MESSAGE RECEIVED: %s%n", message);
	            try {
	                Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
	            } catch (InterruptedException e) {}
	        }
    	}catch(InterruptedException ie){
    		
    	}
    }
}
