/*  Name: John Isennock
 * Course: CNT 4714Spring 2021
 * Assignment title: Project2–Synchronized, Cooperating Threads Under Locking
 * Due Date: February 14, 2021*/


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	
	public static void main(String[] args) {
		
		ExecutorService application = Executors.newFixedThreadPool(14);
		
		Buffer sharedLocation = new SyncTransactionBuffer();
		System.out.printf("Deposit Threads\t\t\tWithdrawal Threads\t\t\t\tBalance\n");
		System.out.printf("---------------\t\t\t------------------\t\t\t-----------------------\n");
		try
		{
			while(true){
				
				
				application.execute(new WithdrawalRunnable(sharedLocation,"W1"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W2"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W3"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W4"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W5"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W6"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W7"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W8"));
				application.execute(new WithdrawalRunnable(sharedLocation,"W9"));
				
				application.execute(new DepositRunnable(sharedLocation,"D1"));
				
				application.execute(new DepositRunnable(sharedLocation,"D2"));
				
				application.execute(new DepositRunnable(sharedLocation,"D3"));
				
				application.execute(new DepositRunnable(sharedLocation,"D4"));
				
				application.execute(new DepositRunnable(sharedLocation,"D5"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		application.shutdown();
		
	}

}