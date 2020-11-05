package Lab3.ex2;

public class InterferenceChecker implements Runnable {

	public static int shared;

	public int id;

	//static lock object for synchronization: all instances share lock
	public final static Object LOCK = new Object();

	InterferenceChecker(int id) {
		this.id = id;
	}

	static final int REPETITIONS = 1000 * 1000;

	public void run() throws RuntimeException {
		int i = 0;
		while (i < REPETITIONS) {

			synchronized (LOCK) {
				shared = id;
				if (shared != id) {
					break;
				}
			}

			i++;
		}
		System.out.printf("Thread %d: i=%d%n", id, i);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new InterferenceChecker(1));
		Thread t2 = new Thread(new InterferenceChecker(2));
		t1.start();
		t2.start();
	}
}
