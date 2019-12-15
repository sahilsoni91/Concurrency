package chapter1;

import java.lang.Thread.UncaughtExceptionHandler;

/*
 * Processing unchecked exception in a thread
 */
class ExceptionHandler implements UncaughtExceptionHandler {
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("An Exception has been captured");
//		System.out.println("Thread: " + t.getName());
		System.out.println("Exception in thread \"" + t.getName() + "\" " + e.getClass().getName() + ": " + e.getMessage());
//		System.out.println("Stack Trace:");
		e.printStackTrace(System.out);
//		System.out.println("Thread Status: " + t.getState());
	}
}

class Task implements Runnable {

	@Override
	public void run() {
		int num = Integer.parseInt("TTT");
		System.out.println(num);
	}
}

public class Main_9 {
	public static void main(String[] args) {
		Task task = new Task();
		Thread thread = new Thread(task);
		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		/*thread.setUncaughtExceptionHandler((t, e) -> {
			System.out.println("An Exception has been captured");
			System.out.println("Exception in thread \"" + t.getName() + "\" " + e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace(System.out);
		});*/
		thread.start();
	}
}