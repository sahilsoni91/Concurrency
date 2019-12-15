package chapter1;

/*
 * Creating and Running a thread
 */
class Calculator_2 extends Thread {
	
	private int number;
	
	public Calculator_2(int number) {
		this.number = number;
	}
	
	@Override
	public void run() {
		for(int i = 1; i <= 10; i++) {
			System.out.println(Thread.currentThread().getName() + ": " + number + " * " + i + " = " + i * number);
		}
	}
}

public class Main_2 {
	
	public static void main(String[] args) {
		for(int i = 1; i <= 10; i++) {
			Calculator_2 calc = new Calculator_2(i);
			calc.start();
		}
	}
}