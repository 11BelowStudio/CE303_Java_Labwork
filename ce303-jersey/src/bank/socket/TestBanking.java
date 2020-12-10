package bank.socket;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import bank.Constants;

public class TestBanking {

	private Random random = new Random();

	public BankClient client = new BankClient(Constants.CUSTOMERS[random.nextInt(Constants.CUSTOMERS.length)]);
	public int[] accounts = client.getAccounts();

	public TestBanking() {
	};

	@Test
	public void testTransfer() {
		int acc1 = accounts[2];
		int acc2 = accounts[0];
		double bal1 = client.getBalance(acc1);
		double bal2 = client.getBalance(acc2);
		double amount = Constants.MIN_INIT_BALANCE / 10;
		client.transfer(acc1, acc2, amount);
		double bal1after = client.getBalance(acc1);
		double bal2after = client.getBalance(acc2);
		assertEquals(bal1after, bal1 - amount, Constants.EPSILON);
		assertEquals(bal2after, bal2 + amount, Constants.EPSILON);
		// transfer money back...
		client.transfer(acc2, acc1, amount);
	}

}
