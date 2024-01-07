/**
 * 
 */

/**
 * @author Mihir
 * Dec. 2022
 * Desc: : This class inherits the data and methods of the “Account” class. It is an Account class with the following additional rules:
 *			1. It deducts a fee of $1.25 from the balance for each withdrawal if there is a balance lower than $2000.
 *			2. There are no transaction fees if it has a balance of $2000 or more.
 *
 * Method List: 
 * 		public Savings(Customer customer) - default constructor
 * 		public Savings(Customer customer, double fee) - overloaded constructor
 * 		public boolean withdraw(double amt) - method to withdraw money
 * 		public void setFee(double fee) - method to set fee
 *
 */
public class Savings extends Account {
	
	//Private instance data
	private double fee;

	/**
	 * default constructor
	 */
	public Savings(Customer customer) {
		//call parent constructor and initializes fee
		super(customer);
		this.fee = 1.25;
	}

	/**
	 * Overloaded Constructor
	 */
	public Savings(Customer customer, double fee) {
		//call parent constructor and initializes fee
		super(customer);
		this.fee = fee;
	}

	// method to withdraw money from the savings account
	public boolean withdraw(double amt) {
		// check if balance is sufficient
		if (getBalance() >= amt) {
			// check if the balance is below minimum
			if (getBalance() < 2000) {
				// charge a fee if balance is below minimum
				// actually withdraw the money
				super.withdraw(amt + this.fee);
			}
			else {
				super.withdraw(amt);
			}
			
			return true;
		}
		return false;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * @param args
	 * Self-testing main method
	 */
	public static void main(String[] args) {
		// create a customer object
		Customer c = new Customer("Mihir Mistry/45 Daviselm DR/9044950676/12345678");
		//test normal constructor
		Savings s = new Savings(c);
		
		//test the withdraw, deposit, and setter methods
			//test under minimum
		s.deposit(1999);
		
		s.withdraw(50);
		System.out.println(s.getBalance());
		System.out.println();
		
			//test over minimum
		s.setBalance(5000);
		s.withdraw(2500);
		System.out.println(s.getBalance());
		System.out.println();
		
		//test the overloaded constructor
		Savings s1 = new Savings(c, 10.00);
		s1.setBalance(1990);
		s1.withdraw(100);
		System.out.println(s1.getBalance());
		System.out.println();
		
		//test setter
		s1.setFee(1.00);
		s1.withdraw(1000);
		System.out.println(s1.getBalance());
		
		

	}
}