package bank.socket;

// Socket-based bank service 
// Reads and writes ONE line at a time
// Print writer output is set to auto-flush

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import bank.Bank;

public class BankService implements Runnable {
	private Scanner in;
	private PrintWriter out;
	private String customer;
	private boolean login;
	private Bank bank;

	public BankService(Bank bank, Socket socket) {
		this.bank = bank;
		customer = null;
		login = false;
		try {
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override 
	public void run() {
		login();
		while (login) {
			try {
				Request request = Request.parse(in.nextLine());
				String response = execute(bank, request);
				// note use of \r\n for CRLF 
				out.println(response + "\r\n");
			} catch (NoSuchElementException e) {
				login = false;
			}
		}
		logout();
	}

	public void login() {
		out.println("Please enter your customer id");
		try {
			String input = in.nextLine().trim();
			if (bank.getCustomers().contains(input)) {
				customer = input;
				out.println("Welcome " + customer + "!");
				System.out.println("Login: " + customer);
				login = true;
			} else {
				out.println("Invalid login attempt!");
			}
			out.println(); // don't forget empty line terminator!
		} catch (NoSuchElementException e) {
		}
	}

	public void logout() {
		if (customer != null) {
			System.out.println("Logout: " + customer);
		}
		try {
			Thread.sleep(2000);
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String execute(Bank bank, Request request) {
		// System.out.println(request);
		try {
			switch (request.type) {
			case ACCOUNTS:
				return Arrays.toString(bank.getAccounts(customer));
			case BALANCE:
				int accountId = Integer.parseInt(request.params[0]);
				return "" + bank.getBalance(customer, accountId);
			case INVALID:
				return "Command invalid or failed!";
			case LOGOUT:
				login = false;
				return "Goodbye!";
			case TRANSFER:
				int acc1 = Integer.parseInt(request.params[0]);
				int acc2 = Integer.parseInt(request.params[1]);
				double amount = Double.parseDouble(request.params[2]);
				return bank.transfer(customer, acc1, acc2, amount);
			default:
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
