import java.util.Random;

public class WithdrawalRunnable implements Runnable {
	
	private Random r = new Random();
	
	private Buffer sharedLocation;
	
	private String localThreadName;
	
	public WithdrawalRunnable(Buffer shared, String threadName)
	{
		sharedLocation = shared;
		localThreadName = threadName;
	}
	
	@Override
	public void run()
	{
		Thread.currentThread().setName(localThreadName);
		//System.out.printf("Withdrawal Thread Called %s\n", Thread.currentThread().getName());
		try {
			Thread.sleep(r.nextInt(3));
			sharedLocation.withdrawal(generateAmount());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//Generates amount to withdraw from 1 to 50
	private int generateAmount()
	{
		return r.nextInt(50)+1;
	}

}
