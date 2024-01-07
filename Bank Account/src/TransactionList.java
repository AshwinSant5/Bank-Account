import java.awt.Dimension;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 */

/**
 * @author Ashwin Santhosh & Nikhil Bugidi
 * Date: December 2022
 * Description: This class stores the transaction records into one list. It features the default constructor that
 * 				initializes the private data to nothing; getters and setters for the list, and the max size; methods 
 * 				to insert and delete records; a toString method that convert all the contents into a string; a select 
 * 				sort method that sorts the list by ascending transaction amount; a binary search method that searches 
 * 				for the entire record; a method to increase the list max size; and a self-testing main method
 * Method List: public TransactionList() - Default constructor to initialize the data variables
 * 				public int getMaxSize() - Method to get the max size
 * 				public void setMaxSize(int maxSize) - Method to set the max size
 * 				public TransactionRecord[] getList() - Method to get the list
 * 				public boolean insert (TransactionRecord record) - Method to insert elements into the list
 * 				public boolean delete(TransactionRecord record) - Method to delete a record from the list
 * 				public String toString() - Method to convert all the information into String and display it
 * 				public void selectSort() - Method to select sort the transaction list by ascending transaction amount
 * 				public int binarySearch(TransactionRecord record) - Method to binary search for the entire record
 * 				public boolean increase(int amount) - Method to increase maximum array size of the list
 * 				public static void main(String[] args) - Self-Testing Main Method
 */
public class TransactionList {

	/**
	 * Instance data variables
	 */
	private TransactionRecord [] list;
	private int maxSize;
	private int size;

	/**
	 * Default constructor to initialize the data variables
	 */
	public TransactionList() {
		this.maxSize = 0; // set max size as 0 initially
		this.list = new TransactionRecord[maxSize]; // initialize list array with maxSize
		this.size = 0; // list is empty
	}
	

	/*
	 * Method to get the max size
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/*
	 * Method to set the max size
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/*
	 * Method to get the list
	 */
	public TransactionRecord[] getList() {
		return list;
	}

	/**
	 * Method to insert elements into the list
	 * It does it by checking if the size is below the maxSize
	 * If the size is below the maxSize, increase size by 1 and 
	 * add record to location just below size
	 * Returns true if it's successful
	 * Passes in a TransactionRecord Object
	 */
	public boolean insert (TransactionRecord record) {
		// checks if size is below maxSize
		if (size < maxSize) {
			size++; // increases it by 1
			list[size-1] = record;
			selectSort(); // auto sort the transaction record
			return true;
		}
		return false; // return false if list is full
	}

	/**
	 * Method to delete a record from the list
	 * It does it by replacing the record to be deleted with the last record on the list
	 * It then decreases the list size and returns true if successful
	 * passes in a TransactionRecord object
	 */
	public boolean delete(TransactionRecord record) {

		// call the binary search method to find which element contains the exact same record
		int element = binarySearch(record); 

		// if record does not exist, return false
		if (element < 0) {
			return false;
		}

		// else, delete the record
		list[element] = list[size - 1]; // places record to the last one on the list
		size--; // decrease the size
		selectSort();  // select sort in ascending order after changes
		return true; // return true to the caller
	}

	/**
	 * Method to convert all the information into String and display it
	 */
	public String toString() {
		String theList = ""; // String variable
		// loop through the list to build a string to be returned
		for (int i = 0; i < size; i++) {
			theList = theList + list[i].toString() + "\n\n";
		}
		return theList; // return the entire list to the caller
	}

	/**
	 * Method to select sort the transaction list by ascending transaction amount
	 */
	public void selectSort() {

		int min = 0; // create a min variable

		for (int i = 0; i < size; i++) { // loop through the list
			min = i; // min is initialized to index i
			for (int j = i; j < size; j++) { // loop through the list starting from index i

				// if index min has a greater than or equal transaction amount to index j, min is index j
				if (list[min].getTransactionAmount() >= list[j].getTransactionAmount() ) {
					min = j;
				}

				// create a temp variable for list[i]
				TransactionRecord temp = list[i];

				// swap list[i] and list[min]
				list[i] = list[min];
				list[min] = temp;
			}
		}
	}

	/**
	 * Method to binary search for the entire record
	 */
	public int binarySearch(TransactionRecord record) {

		// limits for low and high and the middle
		int low = 0;
		int high = size-1;
		int middle;

		// while the low end is below the high end of the array
		while (low <= high) {

			middle = (low + high) / 2; // find the middle

			// check if the middle is the right record
			if (record.toString().compareToIgnoreCase(list[middle].toString()) == 0) {
				return middle; // the location
			}

			else if (record.toString().compareToIgnoreCase(list[middle].toString()) < 0) {  // lower in the alphabet
				high = middle - 1; // change my high end of the list
			}

			else {
				low = middle + 1;  // change the low end of the list
			}
		}

		// did not found the key
		return - 1;
	}


	/**
	 * Method to increase maximum array size of the list
	 * Return true if successful and return false if not successful
	 */
	public boolean increase(int amount) {
		// if amount entered is less than 0, return false
		if (amount <= 0) {
			return false;
		}

		// else, increase max list size by amount specified
		this.setMaxSize(this.getMaxSize() + amount); 

		// create an array with the new max size
		TransactionRecord[] list1 = new TransactionRecord[this.getMaxSize()]; 

		// fill the elements of list1 with the elements of this.list
		for (int i = 0; i < this.list.length; i++) {
			list1[i] = this.list[i];
		}

		// update this.list with the elements and new array size of list1
		this.list = list1;
		return true; // return true to the caller
	}
	

	/**
	 * Self-Testing Main Method
	 */
	public static void main(String[] args) {
		// Self testing using a Dialog box
		// create a TransactionList object
		TransactionList tList = new TransactionList();

		// infinite loop to test
		while (true) {

			// create command variable to prompt for what to test
			char input = JOptionPane.showInputDialog(null, 
					"i - Insert Record\n" +
							"p - Print Transaction List\n" +
							"d - Delete Record\n" +
							"L - Increase List's Maximum Size\n" +
							"b - Binary Search for the Entire Record\n" +
							"q - Quit", "i").charAt(0);

			// if command is q, quit the program
			if (input == 'q') {
				break;
			}

			switch(input) { // switch case for the command

			case 'i':{ // if input is insert

				// prompt for and get a transaction record to insert
				String record = JOptionPane.showInputDialog(null, 
						"Please enter a transaction record", "c/Deposit/2.50/10.00/12.50");

				// create a Transaction Record object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // Process the record in the tInfo

				// If tInfo could be inserted into tList, display the record is inserted
				if (tList.insert(tInfo)) {
					JOptionPane.showMessageDialog(null, "Record Inserted");
				}

				// Else, display the insert failed
				else {
					JOptionPane.showMessageDialog(null, "Record Insert Failed");
				}

				break;
			}
			case 'p': { // if input is p, print tList

				// Create JTextArea
				JTextArea outputArea = new JTextArea();
				outputArea.setEditable(false);
				
				// Create scroll bar in order to scroll through the records
				JScrollPane scrollBar = new JScrollPane(outputArea,
						JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollBar.setPreferredSize(new Dimension(250, 500));
				outputArea.setText(tList.toString());

				// Display the transaction list
				JOptionPane.showMessageDialog(null, scrollBar, "Transaction Records",
						JOptionPane.OK_OPTION);
				break;
			}
			case 'd':{ // if input is to delete record

				// prompt for and get the transaction record to delete
				String record =  JOptionPane.showInputDialog(null, "Enter a record", "c/Deposit/2.50/10.00/12.50");

				// create a TransactionRecord object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // process the record

				// If tInfo is deleted from tList, display record is deleted
				if (tList.delete(tInfo)) {
					JOptionPane.showMessageDialog(null, "Record Deleted");
				}

				// If vInfo is not found, display it could be not deleted
				else {
					JOptionPane.showMessageDialog(null, "Delete failed!");
				}
				break;
			}
			case 'L': { // if input is to increase list size

				// prompt for and get how much to increase array by
				int amount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter by much you want "
						+ "the maximum size to be increased by"));

				// if increase in list size is a success, display the new max size
				if (tList.increase(amount)) {
					JOptionPane.showMessageDialog(null, "Maximum list size is now " + tList.getMaxSize());
				}

				else { // else, display that it failed and display the max size
					JOptionPane.showMessageDialog(null, "Maximum list size increase failed!\nMaximum size is"
							+ " still " + tList.getMaxSize());
				}
				break;
			}
			case 'b': { // if input is to binary search for the entire record

				// prompt for and get the record to be found
				String recordToFind = JOptionPane.showInputDialog(null, "Enter a record", "c/Deposit/2.50/10.00/12.50");

				// process the record to be found
				TransactionRecord recordObject = new TransactionRecord();
				recordObject.processRecord(recordToFind);

				// call the method to binary search the record
				int location = tList.binarySearch(recordObject);

				if (location >= 0) { // if returned value is >=0, record is found
					JOptionPane.showMessageDialog(null, tList.getList()[location]);
				}

				else { // else, record is not found
					JOptionPane.showMessageDialog(null, recordToFind + " not found");
				}

				break;
			}
			}
		}
	}
}