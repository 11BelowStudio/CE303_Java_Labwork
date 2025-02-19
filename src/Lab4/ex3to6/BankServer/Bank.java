package Lab4.ex3to6.BankServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Bank {

    private static int NEXT_ACCOUNT = 1000;

    private static int validateAccount(int numToCheck){
        int accountToReturn;
        if (numToCheck > NEXT_ACCOUNT){
            NEXT_ACCOUNT = numToCheck + 1;
            accountToReturn = numToCheck;
        }
        else {
            accountToReturn = NEXT_ACCOUNT;
            NEXT_ACCOUNT++;
        }
        return accountToReturn;
    }

    private final Map<Integer, Account> accounts = new TreeMap<>();


    public void userCreateAccount(int customerId){
        int accountNum = validateAccount(1000);
        Account newAccount = new Account(customerId, accountNum);
        newAccount.setBalance(0);
        accounts.put(accountNum,newAccount);
    }

    public void createAccount(int customerId, int accountNumber, int initialBalance)
    {
        Account account = new Account(customerId, validateAccount(accountNumber));
        account.setBalance(initialBalance);
        accounts.put(accountNumber, account);
    }

    public List<Integer> getListOfAccounts(int customerId) {
        List<Integer> result = new ArrayList<Integer>();

        for (Account account : accounts.values())
            if (account.getCustomerId() == customerId)
                result.add(account.getAccountNumber());

        return result;
    }

    public int getAccountBalance(int customerId, int accountNumber) throws Exception {
        if (accounts.get(accountNumber).getCustomerId() != customerId)
            throw new Exception("Account " + accountNumber + " + belongs to a different customer; customer " + customerId + " is not authorised to query balance for this account.");

        return accounts.get(accountNumber).getBalance();
    }

    public void transfer(int customerId, int fromAccount, int toAccount, int amount) throws Exception {
        synchronized (accounts)
        {
            if (accounts.get(fromAccount).getCustomerId() != customerId)
                throw new Exception("Account " + fromAccount + " belongs to a different customer; customer " + customerId + " is not authorised to transfer from this account.");
            if (accounts.get(fromAccount).getBalance() < amount)
                throw new Exception(
                        "The balance of account " + fromAccount + " is " + accounts.get(fromAccount).getBalance() + " which is insufficient to transfer " + amount + ".");
            if (amount <= 0)
                throw new Exception("Transfer amount has to be a positive value.");
            accounts.get(fromAccount).setBalance(accounts.get(fromAccount).getBalance() - amount);
            accounts.get(toAccount).setBalance(accounts.get(toAccount).getBalance() + amount);
        }
    }
}
