package bank.rest;

public class TransferRequest {

	public int fromAccount;
	public int toAccount;
	public double amount; 
	
	public TransferRequest() {}

	public TransferRequest(int fromAccount, int toAccount, double amount) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}
	
	@Override 
	public String toString() {
		return "TransferRequest: " + fromAccount + " -> " + toAccount + ": " + amount; 
	}
	
}
