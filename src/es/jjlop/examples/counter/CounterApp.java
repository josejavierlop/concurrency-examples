package es.jjlop.examples.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterApp {

	public static void main(String[] args) throws InterruptedException{
		Counter c = new Counter();
		/*for(int i = 0; i < 10; i++){
			new Thread(new Visitor(c)).start();
		}
		
		Thread.sleep(100);
		System.exit(0);
		*/
		
		ExecutorService es = Executors.newFixedThreadPool(5);
		for(int i = 0; i < 10; i++){
			es.execute(new Thread(new Visitor(c)));
		}
		es.shutdown();
		es.awaitTermination(1, TimeUnit.HOURS);
	}
}

class Counter {
	private static AtomicInteger counter = new AtomicInteger();
	
	public int increment(){
		return counter.incrementAndGet();
	}
	
	public int getCurrent(){
		return counter.get();
	}
}

class Visitor implements Runnable{
	private Counter counter;
	public Visitor(Counter c){
		counter = c;
	}
	public void run(){
		for(int i = 0; i < 100; i++){
			System.out.println(counter.increment());
		}
	}
}
