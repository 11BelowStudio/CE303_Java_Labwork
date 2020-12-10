package bank;

public class BankAccount {

	public final int id;
	private double balance;
	public final String accountHolder;

	public BankAccount(int id, double initialBalance, String accountHolder) {
		this.id = id;
		balance = initialBalance;
		this.accountHolder = accountHolder;
	}

	public BankAccount(int id, String accountHolder) {
		this(id,0,accountHolder); 
		double initialBalance = Constants.MIN_INIT_BALANCE + 
				Math.random() * (Constants.MAX_INIT_BALANCE -  Constants.MIN_INIT_BALANCE); 
		this.balance = Math.round(initialBalance); 
	}

	@Override
	public String toString() {
		return String.format("BankAccount [%s, %d, %.2f]", accountHolder, id, balance);
	}
	
	public double getBalance() {
		return balance; 
	}

	public double transfer(BankAccount other, double amount) {
		this.balance -= amount;
		other.balance += amount;
		return balance; 
	}

}
