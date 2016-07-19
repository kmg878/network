package kr.ac.sungkyul.thread;

public class ThreadExample {

	public static void main(String[] args) {
		DigitTread thread1 = new DigitTread();
		DigitTread thread2 = new DigitTread();
		AlphabetThread thread3 = new AlphabetThread();
		Thread thread4 = new Thread(new UpperCaseAlphabetRunnableImpl());
		
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		
		

	}

}
