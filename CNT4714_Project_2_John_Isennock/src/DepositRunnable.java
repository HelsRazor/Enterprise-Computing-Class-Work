import java.util.Random;

public class DepositRunnable extends Main implements Runnable  {
	private Random r = new Random();
	private Buffer sharedLocation;
	private String threadNameLocal;
	
	public DepositRunnable(Buffer shared, String threadName)
	{
		sharedLocation = shared;
		threadNameLocal = threadName;
	}
	
	@Override
	public void run()
	{
		Thread.currentThread().setName(threadNameLocal);
		try {
			Thread.sleep(r.nextInt(30));
			sharedLocation.deposit(generateAmount());
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	//Generates amount to deposit from 1 to 250
	private int generateAmount()
	{
		return r.nextInt(250)+1;
	}

}
