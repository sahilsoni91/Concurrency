package chapter1;

/*
 * Interrupting a thread
 */
class PrimeGenerator extends Thread {
	
	@Override
	public void run() {
		long number = 1L;
		while (true) {
			if(isPrime(number)) {
				System.out.println("Number " + number + " is Prime.");
			}
			
			try {
				Thread.sleep(100);
			} catch (Exception e) {
//				return;
			}
			
			if(interrupted()) { 
				System.out.println("Prime Generator is interrupted.");
				return;
			}
			 
			
			number++;
		}
	}

	private boolean isPrime(long number) {
		if(number <= 2) {
			return true;
		}
		
		for(long i = 2; i < number; i++) {
			if(number % i == 0) {
				return false;
			}
		}
		return true;
	}
}

public class Main_4 {
	public static void main(String[] args) {
		Thread task = new PrimeGenerator();
		task.start();
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		task.interrupt();
	}
}
