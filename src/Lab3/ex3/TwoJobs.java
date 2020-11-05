package Lab3.ex3;

// TODO
// Add program code so that the threads wait for each other after completing first job
// No thread should starts second job until all threads have completed first job 

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TwoJobs implements Runnable {

	private static final int N = 10;
	private int id;

	public static CyclicBarrier barrier = new CyclicBarrier(
			N,
			() -> System.out.println("all threads finished first job")
	);

	public TwoJobs(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		firstJob();
		waitForOthers();
		secondJob();
	}

	public static void main(String[] args) {
		for (int i = 0; i < N; i++) {
			new Thread(new TwoJobs(i)).start();
		}

	}

	public void firstJob() {
		System.out.println("Thread " + id + ", first job done");
	}

	public void secondJob() {
		System.out.println("Thread " + id + ", second job done");
	}

	public void waitForOthers() {
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e){
			e.printStackTrace();
		}
	}

}