package Lab4.ex3to6.BankServer;

public class Account {
    private final int customerId;
    private final int accountNumber;
    private int balance;

    public Account(int customerId, int accountNumber) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int newBalance) {
        balance = newBalance;
    }
}
