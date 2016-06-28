package es.jjlop.examples.pingpong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PingPong {
	 
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		PingPongMonitor monitor = new PingPongMonitor();
		while (true) {
			executorService.execute(new Ping(monitor));
			executorService.execute(new Pong(monitor));
		}
		/*executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);*/
	}
}

class PingPongMonitor{
	private boolean ping = true;

	public synchronized void ping() throws InterruptedException{
		while(!ping){
			wait();
		}
		System.out.print("ping-");
		ping=false;
		notifyAll();
	}
	
	public synchronized void pong() throws InterruptedException{
		while(ping){
			wait();
		}
		System.out.print("pong-");
		ping=true;
		notifyAll();
	}
	
}

class Ping implements Runnable{
	
	private PingPongMonitor monitor;
	
	public Ping(PingPongMonitor monitor){
		this.monitor = monitor;
	}
	
	public void run() {
		try {
			monitor.ping();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 
class Pong implements Runnable{
	
	private PingPongMonitor monitor;
	
	public Pong(PingPongMonitor monitor){
		this.monitor = monitor;
	}
	
	public void run() {
		try {
			monitor.pong();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
