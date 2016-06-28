package es.jjlop.examples.bankaccount;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {
	
	private AtomicLong account;
	
	private List<Movement> movements;
	
	public BankAccount(){
		account = new AtomicLong();
		movements = Collections.synchronizedList(new ArrayList<Movement>());
	}
	
	public void deposit(long l){
		long expect;
		long update;
		do{
			expect = account.get();
			update = expect + l;
		}while(!account.compareAndSet(expect, update));
		synchronized(this){
			addMovement(update, l, 0);
		}
	}
	
	public void withdrawn(long l) throws Exception{
		long expect;
		long update;
		do{
			expect = account.get();
			update = expect - l;
			if(update < 0){
				throw new Exception("not enough money");
			}
		}while(!account.compareAndSet(expect, update));
		synchronized(this){
			addMovement(update, 0,l);
		}
	}
	
	public void addMovement(long amount, long deposit, long withdrawn){
		movements.add(new Movement(amount,deposit,withdrawn));
	}
	
	public synchronized void report(Writer w) throws IOException{
		w.write("Date\t\tTime\tAmount\tDeposited\tWithdrawn\n");
		for(Movement m : movements){
			w.write(m.getDate()+"\t"+m.getTime()+"\t"+m.getAmount()+"\t"+m.getDeposited()+"\t\t"+m.getWithdrawn()+"\n");
		}
	}
	
	
	class Movement{
		private Date date;
		private long amount;
		private long deposited;
		private long withdrawn;
		public Movement(long amount, long deposited, long withdrawn){
			this.date = new Date();
			this.amount = amount;
			this.deposited = deposited;
			this.withdrawn = withdrawn;
		}
		
		public String getDate() {
			return new SimpleDateFormat("dd/MM/yyyy").format(date);
		}
		public String getTime(){
			return new SimpleDateFormat("HH:mm").format(date);
		}
		public long getAmount() {
			return amount;
		}
		public long getDeposited() {
			return deposited;
		}
		public long getWithdrawn() {
			return withdrawn;
		}
		
	}
}
