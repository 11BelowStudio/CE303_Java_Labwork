package bank;

public class Constants { 
	public static final String[] CUSTOMERS = new String[] {"cust0", "cust1", "cust2"}; 
	
	// number of accounts of each customer 
	public static final int ACCOUNTS = 3; 
	

	// minimum and maximum initial balance, used for random initialisation of bank account balances 
	public static final int MIN_INIT_BALANCE = 1000;
	
	public static final int MAX_INIT_BALANCE = 10000;
	
	// precision for testing equality of floating point numbers
	public static final double EPSILON = 0.001; 
}
