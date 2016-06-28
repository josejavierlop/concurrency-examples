package es.jjlop.examples.bankaccount;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class BankAccountApp {

	public static void main(String[] args) throws InterruptedException, IOException{
		BankAccount ba = new BankAccount();
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(new Thread(new Deposit(ba)));
		es.execute(new Thread(new Withdrawn(ba)));
		es.shutdown();
		while(!es.isTerminated()){
			Thread.sleep(1000);
		}
		StringWriter sw = new StringWriter();
		ba.report(sw);
		System.out.println(sw.toString());
	}
	
}
class Deposit implements Runnable{
	private BankAccount ba;
	public Deposit(BankAccount ba){
		this.ba = ba;
	}
	
	public void run(){
		for(int i = 0; i < 100 ; i++){
			ba.deposit(ThreadLocalRandom .current().nextLong(1000));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Withdrawn implements Runnable{
	private BankAccount ba;
	public Withdrawn(BankAccount ba){
		this.ba = ba;
	}
	
	public void run(){
		for(int i = 0; i < 100 ; i++){
			try {
				ba.withdrawn(ThreadLocalRandom .current().nextLong(1000));
				Thread.sleep(100);
			}catch(InterruptedException e){
				//
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}