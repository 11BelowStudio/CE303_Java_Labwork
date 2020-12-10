package bank.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bank.Bank;


// Banking server using sockets 

public class BankServer {

	public static final int PORT = 8888;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Bank bank = new Bank(); 
		ServerSocket server = new ServerSocket(PORT);
		System.out.println("Started BankingServer at port " + PORT);
		System.out.println("Waiting for clients to connect...");

		while (true) {
			Socket socket = server.accept();
			System.out.println("Client connected.");
			BankService service = new BankService(bank, socket);
			new Thread(service).start();
		}
	}
}
