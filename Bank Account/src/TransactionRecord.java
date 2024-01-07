import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * @author Nikhil Bugidi & Ashwin Santhosh
 * Date: December 2022
 * Description: This class contains and stores the transaction record information. It features
 * 				the default constructor that initializes the info to nothing, overloaded 
 * 				constructor that initalizes and processes the specified information, getters and
 * 				setters for all of the data, a processRecord method to store the information
 * 				into the private data, and the toString method to convert all the contents
 * 				of the record to a string. It also features a self-testing main method to test
 * 				the methods within this class.
 * 
 * Method List: public TransactionRecord() - Default constructor that initializes the private data to nothing
 * 				public char getAccountType() - Method to get the account type
 * 				public void setAccountType(char accountType) - Method to set the account type
 * 				public String getTransactionType() - Method to get the transaction type
 * 				public void setTransactionType(String transactionType) - Method to set the transaction type
 * 				public double getTransactionAmount() - Method to get the transaction amount
 * 				public void setTransactionAmount(double transactionAmount) - Method to set the transaction amount
 * 				public double getStartBalance() - Method to get the start balance
 * 				public void setStartBalance(double startBalance) - Method to set the start balance
 * 				public double getEndBalance() - Method to get the end balance
 * 				public void setEndBalance(double endBalance) - Method to set the end balance
 * 				public LocalDate getDate() - Method to get the date
 * 				void setDate(LocalDate date) - Method to set the date
 * 				public void processRecord(String record) - Method to process the transaction (without the date) and load it into private data variables
 * 				public void processRecordDate(String record) - Method to process the transaction (with the date) and load it into private data variables
 * 				public String toString() - Method to convert the contents of the record to a string
 * 				public static void main(String[] args) - Self-Testing Main Method
 */

public class TransactionRecord {

	/**
	 * Instance data variables
	 */
	private char accountType;
	private String transactionType;
	private double transactionAmount, startBalance, endBalance;
	private LocalDate date;

	/**
	 * Default constructor that initializes the private data to nothing
	 */
	public TransactionRecord() {
		// initialize all the private data to nothing 
		this.accountType = 0;
		this.transactionType = "";
		this.transactionAmount = 0;
		this.startBalance = 0;
		this.endBalance = 0;
		this.date = LocalDate.now();
	}
	/*
	 * Method to get the account type
	 */
	public char getAccountType() {
		return accountType;
	}

	/*
	 * Method to set the account type
	 */
	public void setAccountType(char accountType) {
		this.accountType = accountType;
	}

	/*
	 * Method to get the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/*
	 * Method to set the transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/*
	 * Method to get the transaction amount
	 */
	public double getTransactionAmount() {
		return transactionAmount;
	}

	/*
	 * Method to set the transaction amount
	 */
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/*
	 * Method to get the start balance
	 */
	public double getStartBalance() {
		return startBalance;
	}

	/*
	 * Method to set the start balance
	 */
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	/*
	 * Method to get the end balance
	 */
	public double getEndBalance() {
		return endBalance;
	}

	/*
	 * Method to set the end balance
	 */
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}



	/**
	 * Method to get the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Method to set the date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Method to process the transaction (without the date) and load it into private data variables
	 */
	public void processRecord(String record) {
		String word[]; // create the array variable
		word = record.split("/"); // split the data with '/'s

		// initialize the data into the private variables and array
		this.accountType = word[0].charAt(0);
		this.transactionType = word[1];
		this.transactionAmount = Double.parseDouble(word[2]);
		this.startBalance = Double.parseDouble(word[3]);
		this.endBalance = Double.parseDouble(word[4]);
	}

	/**
	 * Method to process the transaction (with the date) and load it into private data variables
	 */
	
	public void processRecordDate(String record) {
		String word[]; // create the array variable
		word = record.split("/"); // split the data with '/'s

		// initialize the data into the private variables and array
		this.date = LocalDate.parse(word[0]);
		this.accountType = word[1].charAt(0);
		this.transactionType = word[2];
		this.transactionAmount = Double.parseDouble(word[3]);
		this.startBalance = Double.parseDouble(word[4]);
		this.endBalance = Double.parseDouble(word[5]);
	}

	/**
	 * Method to convert the contents of the record to a string
	 */
	public String toString() {

		// format the transaction amount, start balance and end balance to two digits
		DecimalFormat twoDigits = new DecimalFormat("0.00");

		String type = ""; // String variable for the account type
		switch (this.accountType) { // switch case for the char type
		case 's': { // if char is s, type is Savings
			type = "Savings";
			break;
		}
		case 'c': { // if char is c, type is Chequing
			type = "Chequing";
			break;
		}
		default: { // else, it is an invalid type 
			type = "N/A";
			break;
		}
		}

		String type2 = ""; // String variable for the transaction type

		//if transaction type is valid, initialize type2 string as transaction type 
		if (this.transactionType.equalsIgnoreCase("Deposit") || 
				this.transactionType.equalsIgnoreCase("Withdrawl")) {
			type2 = this.transactionType;
		}

		// else, it is invalid
		else {
			type2 = "N/A";
		}

		// return the contents of the record as a string
		return ("Date: " + this.date + "\n\nAccount Type: " + type + "\nTransaction Type: " + type2 + 
				"\nTransaction Amount: $" + twoDigits.format(this.transactionAmount) +
				"\nStart Balance: $" + twoDigits.format(this.startBalance) 
				+ "\nEnd Balance: $" + twoDigits.format(this.endBalance));
	}


	/**
	 * Self-Testing Main Method
	 */
	public static void main(String[] args) {

		// crate a string containing transaction info
		String transactionInfo = "2022-12-03/c/Deposit/2.50/10.00/12.50";

		// create a TransactionRecord object
		TransactionRecord t = new TransactionRecord();
		t.processRecordDate(transactionInfo);
		System.out.println(t); // print the customer info

		// test setters
		t.setAccountType('s');
		t.setTransactionType("withdrawl");
		t.setTransactionAmount(-100);
		t.setStartBalance(1000);
		t.setEndBalance(900);

		// test the getters by printing the info
		System.out.println(t.getDate());
		System.out.println(t.getAccountType());
		System.out.println(t.getTransactionType());
		System.out.println(t.getTransactionAmount());
		System.out.println(t.getStartBalance());
		System.out.println(t.getEndBalance());

		// create another TransactionRecord object and re-initalize the info
		TransactionRecord t2 = new TransactionRecord();
		transactionInfo = "c/deposit/5000.15/10000/15000.15";

		// test the processRecord and toString methods
		t2.processRecord(transactionInfo);
		System.out.println(t2.toString());
	}
}
