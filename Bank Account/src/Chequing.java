/**
 * 
 */

/**
 * @author mihir
 * Dec. 2022
 * Desc: This class inherits the data and methods of the “Account” class. It is an Account class with the following additional rules:
 *			1. It deducts a fee of 0.5% of its current balance (before withdrawal) when funds are withdrawn.
 *			2. It has a service fee of $1.50 for each deposit.
 *
 * Method List:
 * 		public Chequings(Customer customer) - default constructor
 * 		public Chequings(Customer customer, double rate, double fee) - overloaded constructor
 * 		public boolean deposit(double amt) - method to deposit money
 * 		public boolean withdraw(double amt) - method to withdraw money
 * 		public void setdFee(double dFee) - set a new deposit fee
 * 		public void setwRate(double wRate) - set a new withdraw rate
 *
 */
public class Chequing extends Account{

	//private instance data
	private double dFee, wRate;

	/**
	 * Main constructor
	 */
	public Chequing(Customer customer) {
		//call the parent class and initialize variables
		super(customer);
		dFee = 1.5;
		wRate = 0.005;
	}

	/**
	 * Overloaded constructor
	 */
	public Chequing(Customer customer, double rate, double fee) {
		//call the parent class and initialize variables
		super(customer);
		dFee = fee;
		wRate = rate;
	}

	/**
	 * Method to deposit
	 */
	public boolean deposit(double amt) {
		//make sure the amount is greater than 0
		if(amt>0) {
			//deposits the amount while removing the fee
			super.deposit(amt - dFee);
			return true;
		}
		return false;
	}

	/**
	 * Method to withdraw
	 */
	public boolean withdraw(double amt) {
		//finds the new balance
		double newBal = getBalance() - amt - (getBalance()*this.wRate);
		//check if the new balance is sufficient
		if(newBal >= 0) {
			this.setBalance(newBal);
			return true;
		}
		return false;
	}


	/**
	 * @param dFee the dFee to set
	 */
	public void setdFee(double dFee) {
		this.dFee = dFee;
	}

	/**
	 * @param wRate the wRate to set
	 */
	public void setwRate(double wRate) {
		this.wRate = wRate;
	}

	/**
	 * @param args
	 * Self-testing main method
	 */
	public static void main(String[] args) {
		// create a customer object
		Customer c = new Customer("Mihir Mistry/45 Daviselm DR/9044950676/12345678");
		//test normal constructor
		Chequing s = new Chequing(c);
		
		//test deposit and withdraw methods
			//tests below the minimum
		s.deposit(5000);
		System.out.println(s.getBalance());
		s.withdraw(1000);
		System.out.println(s.getBalance());
		System.out.println();
			//tests for over the minimum 
		s.setBalance(5000);
		s.withdraw(2500);
		System.out.println(s.getBalance());
		System.out.println();

		//test overloaded constructor
		Chequing s1 = new Chequing(c, 0.01,10.00);
		s1.deposit(5000);
		System.out.println(s1.getBalance());
		s1.withdraw(1000);
		System.out.println(s1.getBalance());
		System.out.println();
		
		//test setters
		s1.setBalance(0);
		s1.setdFee(5.00);
		s1.setwRate(0.5);
		s1.deposit(5000);
		System.out.println(s1.getBalance());
		s1.withdraw(1000);
		System.out.println(s1.getBalance());

	}

}
