package chapter1;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/*
 * Creating and Running Daemon thread
 */
class Event {

	private LocalDateTime date;
	private String event;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}

class WriterTask implements Runnable {
	private Deque<Event> deque;

	public WriterTask(Deque<Event> deque) {
		this.deque = deque;
	}

	@Override
	public void run() {
		for(int i = 0; i < 100; i++) {
			Event event = new Event();
			event.setDate(LocalDateTime.now());
			event.setEvent("Thread: " + Thread.currentThread().getName() + " has generated an event with index id: " + i + ".");
			deque.addFirst(event);

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}

class CleanerTask extends Thread {
	private Deque<Event> deque;

	public CleanerTask(Deque<Event> deque) {
		this.deque = deque;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			LocalDateTime date = LocalDateTime.now();
			clean(date);
		}
	}

	private void clean(LocalDateTime date) {
		long difference;
		boolean delete;

		if(deque.size() == 0) {
			return;
		}

		delete = false;

		do {
			Event e = deque.getLast();
			difference = e.getDate().until(date, ChronoUnit.MILLIS);
			if(difference > 10000) {
				System.out.println("Cleaner: " + e.getEvent());
				deque.removeLast();
				delete = true;
			}
		} while (difference > 10000);
		
		if(delete) {
			System.out.println("Size of queue: " + deque.size());
		}
	}
}

public class Main_8 {
	public static void main(String[] args) throws InterruptedException {
		Deque<Event> deque = new ArrayDeque<>();
		
		WriterTask writer = new WriterTask(deque);
		for(int i = 0; i < 3; i++) {
			Thread thread = new Thread(writer);
			thread.start();
		}
		
		CleanerTask cleaner = new CleanerTask(deque);
		cleaner.start();
		
		
	}
}