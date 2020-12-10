package bank.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
	public static final String SERVER = "localhost";
	public Scanner in;
	public PrintWriter out;
	public Socket socket;

	public BankClient(String customer) {
		try {
			socket = new Socket(SERVER, BankServer.PORT);
			InputStream instream = socket.getInputStream();
			OutputStream outstream = socket.getOutputStream();
			in = new Scanner(instream);
			// set auto-flush to true
			out = new PrintWriter(outstream, true);
			sendCommand(customer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendCommand(String command) {
		StringBuffer result = new StringBuffer(); 
		out.println(command);
		while (true) {
			String line = in.nextLine().trim();
			if (line.isEmpty())
				break;
			result.append(line); 
		}
		return result.toString(); 
	}

	public int[] getAccounts() {
		String response = sendCommand("accounts");
		response = response.substring(1, response.length() - 1);
		String[] accStrings = response.split(",(\\s*)");
		int[] accounts = new int[accStrings.length];
		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = Integer.parseInt(accStrings[i]);
		}
		return accounts;
	}

	public double getBalance(int accountNumber) {
		String response = sendCommand("balance " + accountNumber);
		return Double.parseDouble(response);
	}

	public String transfer(int from, int target, double amount) {
		return sendCommand("transfer " + from + " " + target + " " + amount); 
	}

	public static void main(String[] args) throws IOException {
		String[] commands = new String[] { "accounts", "balance 101", "transfer 101 104 200", "balance 101",
				"balance 104", "logout" };
		String customer = "cust0"; 
		BankClient cl = new BankClient(customer);
		System.out.println("Client connection: " + customer);
		for (String command : commands) {
			System.out.println("> " + command);
			System.out.println(cl.sendCommand(command));
		}
		cl.socket.close();
	}

}
