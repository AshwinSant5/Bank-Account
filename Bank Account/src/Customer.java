/**
 * 
 */

/**
 * @author Nikhil Bugidi
 * Date: December 2022
 * Description: This class contains all of the customer's information. It features the default constructor,
 * 				overloaded constructor that initializes the data to something specified, getters and setters
 * 				for all of the data, a toString method to convert the contents to a string, and a
 * 				processCustomer method to process the customer and set them in the private variables.
 * 				
 * Method List: public String getName()
 * 				public void setName(String name)
 * 				public String getAddress()
 * 				public void setAddress(String address)
 * 				public String getPhoneNumber()
 * 				public void setPhoneNumber(String phoneNumber)
 * 				public String getSIN()
 * 				public void setSIN(String SIN)
 * 				public String toString()
 * 				public void processCustomer(String customer)
 */

public class Customer {

	/**
	 * Instance data variables
	 */
	private String name, address, phoneNumber, SIN;


	/**
	 * Default constructor
	 */
	public Customer() { 

		// initialize the private data to nothing
		this.name = "";
		this.address = "";
		this.phoneNumber = "";
		this.SIN = "";
	}

	/**
	 * Overloaded constructor that initializes the data with specified information
	 */
	public Customer(String customer) {
		// call the processCustomer method to initialize data
		processCustomer(customer);
	}

	/*
	 * Method to get name
	 */
	public String getName() {
		return name;
	}

	/*
	 * Method to set name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Method to get address
	 */
	public String getAddress() {
		return address;
	}

	/*
	 * Method to set address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * Method to get phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/*
	 * Method to set phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*
	 * Method to get the SIN
	 */
	public String getSIN() {
		return SIN;
	}

	/*
	 * Method to set the SIN
	 */
	public void setSIN(String SIN) {
		this.SIN = SIN;
	}

	/**
	 * Method to convert the contents of the customer to a string
	 */
	public String toString() {
		return (this.name + " " + this.address + " " + this.phoneNumber + " " + SIN);
	}

	/**
	 * Method to process the customer's information
	 */
	public void processCustomer(String customer) {
		String word[]; // create the array variable
		word = customer.split("/"); // split the data with '/'s

		// initialize the customer info into the private variables and array
		this.name = word[0];
		this.address = word[1];
		this.phoneNumber = word[2];
		this.SIN = word[3];
	}

	/**
	 * Self-Testing Main Method
	 */
	public static void main (String args[]) {

		// crate a string containing customer info
		String cInfo = "Nikhil Bugidi/45 Daviselm Drive/647-819-5538/123 456 789";

		// create a Customer object
		Customer c = new Customer(cInfo);
		System.out.println(c); // print the customer info

		// test setters
		c.setName("Ashwin Santhosh");
		c.setAddress("123 Random Street");
		c.setPhoneNumber("647-523-7830");
		c.setSIN("987 654 321");

		// test the getters by printing the info
		System.out.println(c.getName());
		System.out.println(c.getAddress());
		System.out.println(c.getPhoneNumber());
		System.out.println(c.getSIN());

		// create another Customer object and re-initalize the info
		Customer c2 = new Customer();
		cInfo = "Mihir Mistry/88 Daviselm Drive/911/680 700 123";

		// test the processCustomer and toString methods
		c2.processCustomer(cInfo);
		System.out.println(c2.toString());

	}
}