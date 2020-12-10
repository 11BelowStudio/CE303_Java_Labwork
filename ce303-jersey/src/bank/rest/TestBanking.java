package bank.rest;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

import bank.Constants;
import webserver.TomcatServer;

public class TestBanking {

	private Random random = new Random();

	public BankClient client = new BankClient(TomcatServer.BANK_URL,
			Constants.CUSTOMERS[random.nextInt(Constants.CUSTOMERS.length)]);
	public List<Integer> accounts = client.getAccounts();

	public TestBanking() {
	};

	@Test
	public void testTransfer() {
		int acc1 = accounts.get(2);
		int acc2 = accounts.get(1);
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

	@Test
	public void testTransferForm() {
		int acc1 = accounts.get(2);
		int acc2 = accounts.get(1);
		double bal1 = client.getBalance(acc1);
		double bal2 = client.getBalance(acc2);
		double amount = Constants.MIN_INIT_BALANCE / 10;
		client.transferForm(acc1, acc2, amount);
		double bal1after = client.getBalance(acc1);
		double bal2after = client.getBalance(acc2);
		assertEquals(bal1after, bal1 - amount, Constants.EPSILON);
		assertEquals(bal2after, bal2 + amount, Constants.EPSILON);
		// transfer money back...
		client.transfer(acc2, acc1, amount);
	}


	@Test
	public void testCustomers() {
		Set<String> customers1 = new TreeSet<>(Arrays.asList(Constants.CUSTOMERS));
		Set<String> customers2 = new TreeSet<>(client.getCustomers());
		assertEquals(customers1, customers2);
	}

}
