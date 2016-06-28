package es.jjlop.examples.producerconsumer.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueExample {
	 public static void main(String[] args) {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(1);
        (new Thread(new Producer(bq))).start();
        (new Thread(new Consumer(bq))).start();
    }
}
