package chapter1;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/*
 * Sleeping and Resuming a Thread
 */
class FileClock implements Runnable {

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			System.out.println(LocalDateTime.now());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("FileClock has been interrupted");
			}
		}
	}
}

public class Main_6 {
	public static void main(String[] args) {
		FileClock fileCock = new FileClock();
		Thread thread = new Thread(fileCock);
		thread.start();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thread.interrupt();
	}
}