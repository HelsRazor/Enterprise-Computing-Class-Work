import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Random;


public class SyncTransactionBuffer implements Buffer {
	
	private Random r = new Random();
	
	private Lock accessLock = new ReentrantLock();
	
	//private Condition canDeposit = accessLock.newCondition();
	private Condition canWithdraw = accessLock.newCondition();
	
	private int balance = 0;
	
	public int getBalance()
	{
		return balance;
	}
	
	@Override
	public void deposit(int value)
	{
		accessLock.lock();
		
		balance += value;
		
		System.out.printf("Thread %s deposits $%d",Thread.currentThread().getName(),value);
		System.out.printf("\t\t\t\t\t\t\t(+)  Balance is %d\n", balance);	
		canWithdraw.signalAll();

		accessLock.unlock();

	}
	
	@Override
	public void withdrawal(int value)
	{
		accessLock.lock();
		
		try
		{
			
			while(balance < value)
			{
				System.out.printf("\t\t\t     Thread %s withdraws $%d",Thread.currentThread().getName(),value);
				System.out.printf("\t\t\t(******) WITHDRAWAL BLOCKED - INSUFFICIENT FUNDS!!!\n");
				canWithdraw.await();
			}
			
			balance -= value;
			
			System.out.printf("\t\t\t     Thread %s withdraws $%d",Thread.currentThread().getName(),value);
			System.out.printf("\t\t\t(-)  Balance is %d\n", balance);
			
		
	
			
		}
		catch(InterruptedException e)
		{
			
			e.printStackTrace();
		}
		finally
		{
			accessLock.unlock();
		}
	}


}


