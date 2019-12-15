package chapter1;

/*
 * Creating and Running a thread
 */
class Calculator_1 implements Runnable {
	
	private int number;
	
	public Calculator_1(int number) {
		this.number = number;
	}
	
	@Override
	public void run() {
		for(int i = 1; i <= 10; i++) {
			System.out.println(Thread.currentThread().getName() + ": " + number + " * " + i + " = " + i * number);
		}
	}
}

public class Main_1 {
	public static void main(String[] args) {
		for(int i = 1; i <= 10; i++) {
			Calculator_1 calc = new Calculator_1(i);
			Thread thread = new Thread(calc);
			thread.start();
		}
	}
}