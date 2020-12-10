package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bank {

	private List<String> customers;
	private final Map<Integer, BankAccount> accounts;
	private final Map<String, List<BankAccount>> customerAccounts;

	public Bank() {
		customers = new ArrayList<>();
		accounts = new TreeMap<>();
		customerAccounts = new TreeMap<>();
		int accountNumber = 100;
		for (String customer : Constants.CUSTOMERS){
			customers.add(customer);
			customerAccounts.put(customer, new ArrayList<BankAccount>());
			for (int j = 0; j < Constants.ACCOUNTS; j++) {
				accountNumber++;
				BankAccount account = new BankAccount(accountNumber, customer);
				accounts.put(accountNumber, account);
				customerAccounts.get(customer).add(account);
			}
		}
		System.out.println("Bank Setup");
		for (String customerId : customerAccounts.keySet()) {
			System.out.println("### Customer " + customerId);
			for (BankAccount bacc : customerAccounts.get(customerId)) {
				System.out.println(bacc);
			}
		}
	}

	public List<String> getCustomers() {
		return Collections.unmodifiableList(customers);
	}

	public int[] getAccounts(String customer) {
		if (!existsCustomer(customer))
			return new int[] {};
		int[] result = new int[customerAccounts.get(customer).size()];
		int i = 0;
		for (BankAccount acc : customerAccounts.get(customer)) {
			result[i++] = acc.id;
		}
		return result;
	}

	public boolean existsCustomer(String custId) {
		return customerAccounts.containsKey(custId);
	}

	public boolean hasAccount(String custId, int accountNumber) {
		for (BankAccount acc : customerAccounts.get(custId))
			if (acc.id == accountNumber)
				return true;
		return false;
	}

	public boolean existsAccount(int accountNumber) {
		return accounts.containsKey(accountNumber);
	}

	public Double getBalance(String custId, int accountNumber) {
		if (!existsCustomer(custId) || !hasAccount(custId, accountNumber))
			return null;
		else
			return accounts.get(accountNumber).getBalance();
	}

	public String transfer(String custId, int fromId, int toId, double amount) {
		if (!existsCustomer(custId) || !hasAccount(custId, fromId) || !existsAccount(toId) || fromId == toId  || amount == 0)
			return "Transfer failed: ("+custId+","+fromId+","+toId+","+amount+")"; 
		BankAccount toAccount = accounts.get(toId);
		BankAccount fromAccount = accounts.get(fromId);
		synchronized(fromId < toId? fromAccount : toAccount) {
			synchronized(fromId < toId? toAccount : fromAccount) {
					fromAccount.transfer(toAccount, amount);
					return "Transfer successful";
			}
		}
	}
}
