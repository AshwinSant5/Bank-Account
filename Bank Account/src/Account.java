import java.util.Random;

/**
 * 
 */

/**
 * @author Mihir
 * Dec. 2022
 * Desc: This program must keep the customer’s balance, account number and the Customer 
 * 		 object as private data and must enable other classes to deposit, withdraw
 * 		 and update its balance.
 * 
 * Method List:
 * 		public Account() - default constructor
 * 		public Account(Customer theOwner) - Overloaded constructor
 * 		public String generateAccNum() - generates a random 12-digit account number
 *		public boolean deposit(double amount) - deposits a specified amount into the account and updates the balance
 *		public boolean withdraw(double amount) - withdraws a specified amount from the account and updates the balance
 *		public double getBal() - returns the current balance of the account
 *		public boolean setBal(double bal) - sets the balance of the account to a specified amount
 *		public String getAccNum() - returns the account number of the account
 *		public Customer getCus() - returns the Customer object associated with the account
 *		public Customer setCus(Customer cus) - sets the Customer object associated with the account
 *
 */
public class Account {

	// instance data (only some here, you may need more)
	// balance
	private double balance;
	// account number
	private String accNum;
	// Customer
	Customer cus;

	/**
	 * Default Constructor
	 */
	public Account() {
		//initializes the balance
		this.balance = 0;
		// generates an account number
		this.accNum = generateAccNum();
		// Initializes Customer object
		this.cus = new Customer();
	}

	// Overloaded Constructor 
	public Account (Customer theOwner)
	{
		//initializes balance
		this.balance = 0;
		// generates an account number
		this.accNum = generateAccNum();
		// initializes customer object theOwner
		this.cus = theOwner;
	}
	
	public String generateAccNum() {
		Random r = new Random();
		String num = "";
		
		for(int i =0;i<+12;i++) {
			num = num + r.nextInt(10);
		}
		
		return num;
	}

	public boolean deposit (double amount)
	{
		// deposits amount into balance
		// returns true if deposit is successful and false if it is not
		if(amount>0) {
			this.balance = this.balance + amount;
			return true;
		}
		return false;
	}

	public boolean withdraw (double amount)
	{
		// Checks if the amount can be withdrawn
		// and returns true if it is possible and false if it is not
		// updates balance
		if(amount>0 && this.balance>=amount){
			this.balance = this.balance - amount;
			return true;
		}
		return false;
	}


	// getters and setters as needed


	// other useful methods could include a method to
	// change the customer object and
	// to retrieve the customer method
	// toString method


	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double bal) {
		this.balance = bal;
	}

	/**
	 * @return the accNum
	 */
	public String getAccNum() {
		return accNum;
	}

	/**
	 * @return the cus
	 */
	public Customer getCus() {
		return cus;
	}

	/**
	 * @param cus the cus to set
	 */
	public void setCus(Customer cus) {
		this.cus = cus;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Declare a new account
		Account a = new Account();
		//check account number using getter
		System.out.println(a.getAccNum());
		
		//test setters and deposit method
		a.setBalance(22.45);
		a.deposit(18.00);
		System.out.println(a.getBalance());
		
		//test withdraw method 
		a.withdraw(12.01);
		System.out.println(a.getBalance());
		
		//test the customer getters, setters and overloaded constructor
		a.setCus(new Customer("Mihir Mistry/45 Daviselm DR/9044950676/12345678"));
		System.out.println(a.getCus());
		
		Account a2 = new Account(new Customer("Ashwin Santhosh/12 Chinguacousy Rd/6475237830/87654321"));
		System.out.println(a2.getCus());

	}

}
