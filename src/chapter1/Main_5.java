package chapter1;

import java.io.File;
import java.util.concurrent.TimeUnit;

/*
 * Controlling interruption of a thread
 */
class FileSearch implements Runnable {

	private String initPath;
	private String fileName;
	
	public FileSearch(String initPath, String fileName) {
		this.initPath = initPath;
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		File file = new File(initPath);
		if(file.isDirectory()) {
			try {
				directoryProcess(file);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + " search has been interrupted.");
			}
		}
	}

	private void directoryProcess(File file) throws InterruptedException {
		File[] list = file.listFiles();
		if(list != null) {
			for(int i = 0; i < list.length; i++) {
				if(list[i].isDirectory()) {
					directoryProcess(list[i]);
				} else {
					fileProcess(list[i]);
				}
			}
		}
		
		if(Thread.interrupted()) {		//interrupted method reset value of interrupted flag to false
			throw new InterruptedException();
		}
	}

	private void fileProcess(File file) throws InterruptedException {
		if(file.getName().equals(fileName)) {
			System.out.println(Thread.currentThread().getName() + " found file " + file.getAbsolutePath());
		}
		
		if(Thread.interrupted()) {
			throw new InterruptedException();
		}
	}
}

public class Main_5 {
	public static void main(String[] args) {
		FileSearch searcher = new FileSearch("C:\\", "autoexec.bat");
		Thread thread = new Thread(searcher);
		thread.start();
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thread.interrupt();
	}
}
