package es.jjlop.examples.producerconsumer.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable {
	private BlockingQueue<String> bq;

	public Producer(BlockingQueue<String> bq) {
		this.bq = bq;
	}

	public void run() {
		String importantInfo[] = { "Mares eat oats", "Does eat oats", "Little lambs eat ivy",
				"A kid will eat ivy too" };
		try {
			for (int i = 0; i < importantInfo.length; i++) {
				bq.put(importantInfo[i]);
				Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
			}

			bq.put("DONE");
		} catch (InterruptedException e) {
		}
	}
}
