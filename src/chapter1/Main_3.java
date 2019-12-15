package chapter1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.State;

/*
 * Getting and Setting thread information
 */
class Calculator_3 implements Runnable {

	private int number;

	public Calculator_3(int number) {
		this.number = number;
	}
	@Override
	public void run() {
		for(int i = 1; i <= 10; i++) {
			System.out.println(Thread.currentThread().getName() + ": " + number + " * " + i + " = " + i * number);
		}
	}
}

public class Main_3 {

	public static void main(String[] args) {
		Thread[] threads = new Thread[10];
		Thread.State[] status = new Thread.State[10];
		for(int i = 0; i < 10; i++) {
			threads[i] = new Thread(new Calculator_3(i + 1));
			if(i % 2 == 0) {
				threads[i].setPriority(Thread.MIN_PRIORITY);
			} else {
				threads[i].setPriority(Thread.MAX_PRIORITY);
			}
			threads[i].setName("Thread - " + i);
		}

		try (FileWriter file = new FileWriter(".\\log.txt"); PrintWriter pw = new PrintWriter(file);) {
			for (int i = 0; i < 10; i++) {
				pw.println("Main : Status of Thread " + threads[i].getName() + " : " + threads[i].getState());
				status[i] = threads[i].getState();
			}

			for (int i = 0; i < 10; i++) {
				threads[i].start();
			}

			boolean isFinished = false;
			while (!isFinished) {
				for (int i = 0; i < 10; i++) {
					if (threads[i].getState() != status[i]) {
						writeThreadInfo(pw, threads[i],status[i]);
						status[i] = threads[i].getState();
					}
				}
				isFinished = true;
				for (int i = 0; i < 10; i++) {
					isFinished = isFinished && (threads[i].getState() == State.TERMINATED);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeThreadInfo(PrintWriter pw, Thread thread, State state) {
		pw.println("Main : Id " +  thread.getId() + " - " + thread.getName());
		pw.println("Main : Priority: " + thread.getPriority());
		pw.println("Main : Old State: " + state);
		pw.println("Main : New State: " + thread.getState());
		pw.println("Main : ************************************");
	}
}