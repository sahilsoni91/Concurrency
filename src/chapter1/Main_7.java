package chapter1;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/*
 * Waiting for the finalization of a thread
 */
class DataSourceLoader implements Runnable {

	@Override
	public void run() {
		System.out.println("Begin data source loading at: " + LocalDateTime.now());
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished data source loading at: " + LocalDateTime.now());
	}
}

class NetworkConnectionsLoader implements Runnable {

	@Override
	public void run() {
		System.out.println("Begin network connections loading at: " + LocalDateTime.now());
		
		try {
			TimeUnit.SECONDS.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished network connections loading at: " + LocalDateTime.now());
	}
}

public class Main_7 {
	public static void main(String[] args) {
		DataSourceLoader dataSourceLoader = new DataSourceLoader();
		NetworkConnectionsLoader networkConnectionLoader = new NetworkConnectionsLoader();
		
		Thread thread1 = new Thread(dataSourceLoader, "DataSourceThread");
		Thread thread2 = new Thread(networkConnectionLoader, "NetworkConnectionThread");
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Configurations has been loaded successfully.");
	}
}