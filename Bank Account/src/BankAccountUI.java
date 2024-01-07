import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 */

/**
 * @author Ashwin Santhosh
 * Date: December 15 2022
 * Description: Class constructs the GUI for the login screen where the user can create an account of they are new or 
 * 				login to an existing account if they are a returning user. New users fill out their personal data and 
 * 				their initial account balances in order to create the account. Existing user's datas are stored in an 
 * 				external file which is accessed during login to verify the user's login credentials and extract all the 
 * 				user's data to display in their account page
 * Method List: 
 * public BankAccountUI() - Constructor to build the window for the main login/create account screen
 * public void actionPerformed(ActionEvent e) - Method to listen to action events 
 * public void increase() - Method to increase the data array lengths
 * public int loginAttempt (String username, String password) - Method to determine successful or unsuccessful login attempts
 * public void writeAccounts (Customer c [], Chequing cq[], Savings sa[]) throws IOException - Method to write customers and customers 
 * 																							account data to a file and also write the customer 
 * 																							login and transaction logs in seperate files
 * public void readPasswords() throws IOException - method to read the stored passwords and store them in an existing array
 * public void readData() throws IOException - Method to read customer and account data
 * public static void main(String[] args) - Main method for testing BankAccountUI GUI and its functions
 * 
 */
public class BankAccountUI extends JFrame implements ActionListener {
	/*
	 * window components
	 */
	//buttons
	JButton newAccount, login, submit, submit2, backToHome;
	//text input fields
	JTextField username, name, address, telephone, savingsBalance, checkingBalance, username2, password2, usernameSet, sin;
	//password input fields
	JPasswordField password, passwordSet;
	//pictures
	ImagePicture bankLogo;
	//labels
	JLabel lblUsername, lblPassword, lblName, lblAddress, lblTelephone, lblSavingsBalance, lblCheckingBalance,lblUsername2, lblPassword2, lblSIN, lblError, lblIncomplete;

	//accounts and customer arrays
	Chequing cAccounts[];
	Savings sAccounts[];
	Customer customers [];
	//arrays for the passwords and usernames on file and the user inputed login credentials
	String userN [], pass [], inputU, inputP;
	//counter variables
	int counter, counter2;

	/**
	 * Constructor to build the window for the main login/create account screen
	 */
	public BankAccountUI() {
		super("Mistry Bank"); //name the window
		setLayout(null); //set the layout to null

		//initializing arrays with length of 0
		cAccounts = new Chequing[0];
		sAccounts = new Savings[0];
		pass = new String[0];
		userN = new String[0];
		customers = new Customer[0];
		
		try {
			//read existing data and set the arrays to the read data
			readData();
			//read the existing usernames and passwords
			readPasswords();
		} catch (IOException e) {
			//handle exceptions
		}

		//component for the bank logo picture
		bankLogo = new ImagePicture(new ImageIcon("bank logo.png"));
		//set the bounds
		bankLogo.setBounds(175, 20, bankLogo.getMyWidth(), bankLogo.getMyHeight());
		//add it to the window
		add(bankLogo);

		//component for creating a new account button and logging into an existing account
		newAccount = new JButton("Create an Account");
		login = new JButton("Login");

		//set the bounds of both buttons and add it to the window
		login.setBounds(420, 340, 160, 60);
		add(login);
		newAccount.setBounds(420, 460, 160, 60);
		add(newAccount);

		//for the login screen of an existing user
		//add components for the labels, set the bounds, set the font and add them to the window
		lblUsername = new JLabel("Username:");
		lblUsername.setBounds(350, 280, 300, 60);
		lblUsername.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(lblUsername);
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(350, 380, 300, 60);
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(lblPassword);
		//add components for the input fields, set the bounds and the font and add it to the window
		username = new JTextField();
		username.setBounds(350, 330, 300, 40);
		username.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(username);
		password = new JPasswordField();
		password.setBounds(350, 430, 300, 40);
		password.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(password);
		//component for the submit button
		submit = new JButton("Login");
		//set the bounds
		submit.setBounds(420, 530, 160, 60);
		//add the button to the window
		add(submit);

		//button to return to the home screen
		backToHome = new JButton("<-- Return to home Screen");
		//set the font of the text
		backToHome.setFont(new Font("SansSerif", Font.PLAIN, 9));
		//set the bounds
		backToHome.setBounds(10, 250, 150, 40);
		//add it to the window
		add(backToHome);

		//create a new label for the error message, set the color, font and bounds and add it to the window
		lblError = new JLabel("Login Failed");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblError.setBounds(400, 220, 460, 80);
		add(lblError);
		//set the label to invisible
		lblError.setVisible(false);

		//create a new label for the error: incomplete information message, set the color, font and bounds and add it to the window
		lblIncomplete = new JLabel("Error: Incomplete Data");
		lblIncomplete.setForeground(Color.RED);
		lblIncomplete.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblIncomplete.setBounds(600, 220, 460, 80);
		add(lblIncomplete);
		//set the label to invisible
		lblIncomplete.setVisible(false);

		//set the login screen components to not visible initially
		lblUsername.setVisible(false);
		lblPassword.setVisible(false);
		username.setVisible(false);
		password.setVisible(false);
		submit.setVisible(false);
		backToHome.setVisible(false);


		//for new account screen

		//add labels for the titles, set the font and the bounds and add them to the window
		lblName = new JLabel("First and Last Name:");
		lblName.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblName.setBounds(200, 210, 300, 40);
		add(lblName);
		lblAddress= new JLabel("Address:");
		lblAddress.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblAddress.setBounds(200, 280, 300, 40);
		add(lblAddress);
		lblTelephone = new JLabel("Telephone Number:");
		lblTelephone.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblTelephone.setBounds(200, 350, 300, 40);
		add(lblTelephone);
		lblSIN = new JLabel("SIN:");
		lblSIN.setBounds(200, 420, 300, 40);
		lblSIN.setFont(new Font("SansSerif", Font.PLAIN, 15));
		add(lblSIN);


		//labels for the initial balance for both chequing and savings account
		//create the label, set the font and bounds, then add them to the window
		lblSavingsBalance = new JLabel("Savings Account Balance:");
		lblSavingsBalance.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSavingsBalance.setBounds(200, 490, 300, 40);
		add(lblSavingsBalance);
		lblCheckingBalance = new JLabel("Chequings Account Balance:");
		lblCheckingBalance.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblCheckingBalance.setBounds(200, 560, 300, 40);
		add(lblCheckingBalance);

		//create text fields fo user inputed data and information, set the bounds and the font and add them to the window
		name = new JTextField();
		name.setBounds(200, 250, 300, 30);
		name.setFont(new Font("SansSerif", Font.PLAIN, 15));
		add(name);
		address = new JTextField();
		address.setBounds(200, 320, 300, 30);
		address.setFont(new Font("SansSerif", Font.PLAIN, 15));
		add(address);
		telephone = new JTextField();
		telephone.setBounds(200, 390, 300, 30);
		telephone.setFont(new Font("SansSerif", Font.PLAIN, 15));
		add(telephone);
		sin = new JTextField();
		sin.setBounds(200, 460, 300, 30);
		sin.setFont(new Font("SansSerif", Font.PLAIN, 15));
		add(sin);
		//text fields for the user inputed initial balance for both accounts
		//set bounds and font and add them to the window
		savingsBalance = new JTextField();
		savingsBalance.setBounds(200, 530, 300, 30);
		savingsBalance.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(savingsBalance);
		checkingBalance = new JTextField();
		checkingBalance.setBounds(200, 600, 300, 30);
		checkingBalance.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(checkingBalance);

		//labels for the username and password for user to set
		//set bounds and font and add them to the window
		lblUsername2 = new JLabel("Set Username:");
		lblUsername2.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblUsername2.setBounds(620, 300, 300, 40);
		add(lblUsername2);
		lblPassword2 = new JLabel("Set Password:");
		lblPassword2.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblPassword2.setBounds(620, 400, 300, 40);
		add(lblPassword2);
		//text fields for the user inputed username and password to set
		//set bounds and font and add them to the window
		usernameSet = new JTextField();
		usernameSet.setBounds(620, 340, 300, 40);
		usernameSet.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(usernameSet);
		passwordSet = new JPasswordField();
		passwordSet.setBounds(620, 440, 300, 40);
		passwordSet.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(passwordSet);

		//create a button to submit the information to create the account, set bounds and add to the window
		submit2 = new JButton("Create Account");
		submit2.setBounds(700, 530, 160, 60);
		add(submit2);

		//set all of the new account screen components to not visible initially
		lblName.setVisible(false);
		lblAddress.setVisible(false);
		lblTelephone.setVisible(false);
		lblSIN.setVisible(false);
		lblSavingsBalance.setVisible(false);
		lblCheckingBalance.setVisible(false);
		name.setVisible(false);
		address.setVisible(false);
		telephone.setVisible(false);
		sin.setVisible(false);
		savingsBalance.setVisible(false);
		checkingBalance.setVisible(false);
		submit2.setVisible(false);
		lblUsername2.setVisible(false);
		lblPassword2.setVisible(false);
		usernameSet.setVisible(false);
		passwordSet.setVisible(false);

		//add the buttons as listeners in this class
		login.addActionListener(this);
		newAccount.addActionListener(this);
		submit.addActionListener(this);
		submit2.addActionListener(this);
		backToHome.addActionListener(this);


		setSize(1000,700); //set the size of the window to 1000 X 700
		getContentPane().setBackground(new Color(88, 88, 88)); //set frame background color
		setLocationRelativeTo(null); //position the window to the center of the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);//exit program if "x" is clicked
		setVisible(true);//set the window as visible
	}

	/*
	 * Method to listen to action events 
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==login) { //of login was clicked
			username.setText("");
			password.setText("");
			//set the home page components to not visible
			login.setVisible(false);
			newAccount.setVisible(false);
			//set the login screen components to visible
			lblUsername.setVisible(true);
			lblPassword.setVisible(true);
			username.setVisible(true);
			password.setVisible(true);
			submit.setVisible(true);
			//set the "back to hom screen" button to visible
			backToHome.setVisible(true);
		}//end of login

		else if (e.getSource()==submit) { //if submit for login screen was clicked
			//set the error message to invisible
			lblError.setVisible(false);
			//call the readData and readPasswords methods to read existing data about the user from the file (personal data
			//and login data)
			try {
				readData();
				readPasswords();
			} //end of try
			catch (IOException a) { //catch errors caused by user trying to login without creating account
				//display error message
				JOptionPane.showMessageDialog(null, "Please Create An Account First");
			}//end of catch

			//if the username and password match that already existing in the database
			if (loginAttempt(username.getText(), String.valueOf(password.getPassword())) > -1) {
				//find location of the username and password in the database
				int location = loginAttempt(username.getText(), String.valueOf(password.getPassword()));

				//create a new AccountPageUI GUI objecy which has the account homepage and functions 
				try {
					AccountPageUI accountPage = new AccountPageUI(cAccounts[location], sAccounts[location], customers[location]);
				} //end of try
				catch (IOException e1) {
					//catch errors that may arise

				}//end of catch
			}//end of if

			//of the login attempt failed (incorrect credentials)
			else {
				//make error message visible
				lblError.setVisible(true);
				//clear the login fields
				username.setText("");
				password.setText("");
			}//end of else
		}//end of if login screen submit button was clicked

		else if (e.getSource()==newAccount) { //if user clicks on create a new account
			//set the home screen components to not visible
			login.setVisible(false);
			newAccount.setVisible(false);
			//clear the text fields 
			name.setText("");
			address.setText("");
			telephone.setText("");
			sin.setText("");
			checkingBalance.setText("");
			savingsBalance.setText("");
			usernameSet.setText("");
			passwordSet.setText("");
			//set the new account creation screen components to visible
			lblName.setVisible(true);
			lblAddress.setVisible(true);
			lblTelephone.setVisible(true);
			lblSIN.setVisible(true);
			lblSavingsBalance.setVisible(true);
			lblCheckingBalance.setVisible(true);
			name.setVisible(true);
			address.setVisible(true);
			telephone.setVisible(true);
			sin.setVisible(true);
			savingsBalance.setVisible(true);
			checkingBalance.setVisible(true);
			lblUsername2.setVisible(true);
			lblPassword2.setVisible(true);
			usernameSet.setVisible(true);
			passwordSet.setVisible(true);
			submit2.setVisible(true);
			//set the back to home screen button to visible
			backToHome.setVisible(true);
		}

		else if (e.getSource()==submit2) { //if the submit button for creating a new account was clicked
			//set the error message color for the checking balance, savings balance and the sin to black
			checkingBalance.setForeground(Color.black);
			savingsBalance.setForeground(Color.black);
			sin.setForeground(Color.black);
			//if any of the text fields are empty
			if (name.getText().equals("") || address.getText().equals("") || telephone.getText().equals("") || sin.getText().equals("") 
					|| checkingBalance.getText().equals("") || savingsBalance.getText().equals("") || usernameSet.getText().equals("")
					|| String.valueOf(passwordSet.getPassword()).equals("")) {
				//display the error tag
				lblIncomplete.setVisible(true);
			}
			//check if the intial balance for both accounts are 0 or above
			else if (Double.parseDouble(checkingBalance.getText()) >= 0 && Double.parseDouble(savingsBalance.getText()) >= 0 && Long.parseLong(sin.getText()) > 0) {
				//add one to the counter variable
				counter2++;
				//call the increase method to increase the array size
				increase();
				//create a new customer object
				customers[customers.length-1] = new Customer(name.getText() + "/" + address.getText() + "/" + telephone.getText() + "/" + sin.getText());
				//create a new chequing account object and set the balance to user inputed balance
				cAccounts[cAccounts.length-1] = new Chequing(customers[customers.length-1]);
				cAccounts[cAccounts.length-1].setBalance(Double.parseDouble(checkingBalance.getText()));
				//create a new savings account object and set the balance to user inputed balance
				sAccounts[sAccounts.length-1] = new Savings(customers[customers.length-1]);
				sAccounts[sAccounts.length-1].setBalance(Double.parseDouble(savingsBalance.getText()));
				//create a new username and password at the element of [username.length-1]
				userN[userN.length-1] = usernameSet.getText();
				pass[pass.length - 1] = String.valueOf(passwordSet.getPassword());

				//write the new account data into a file
				try {
					writeAccounts(customers, cAccounts, sAccounts);
				}
				catch (IOException a) {
					//catches errors that may arise
				}
				//set the home screen to visible
				login.setVisible(true);
				newAccount.setVisible(true);

				//set the error message to false
				lblIncomplete.setVisible(false);

				//set the create a new account screen to not-visible
				lblName.setVisible(false);
				lblAddress.setVisible(false);
				lblTelephone.setVisible(false);
				lblSIN.setVisible(false);
				lblSavingsBalance.setVisible(false);
				lblCheckingBalance.setVisible(false);
				name.setVisible(false);
				address.setVisible(false);
				telephone.setVisible(false);
				sin.setVisible(false);
				savingsBalance.setVisible(false);
				checkingBalance.setVisible(false);
				lblUsername2.setVisible(false);
				lblPassword2.setVisible(false);
				usernameSet.setVisible(false);
				passwordSet.setVisible(false);
				submit2.setVisible(false);
				//set the back t home screen button to invisible
				backToHome.setVisible(false);
			}
			//if the chequing balance entered is less than 0
			if (Double.parseDouble(checkingBalance.getText()) < 0) {
				//set the error message color to red
				checkingBalance.setForeground(Color.RED);
				//display error message
				checkingBalance.setText("Error");
			}
			//if the savings balance entered is less than 0
			if (Double.parseDouble(savingsBalance.getText()) < 0) {
				//set the error message color to red
				savingsBalance.setForeground(Color.RED);
				//display error message
				savingsBalance.setText("Error");
			}
			//if the sin number entered is less than 1
			if (Double.parseDouble(sin.getText()) <= 0) {
				//set the error message color to red
				sin.setForeground(Color.RED);
				//display error message
				sin.setText("Error");
			}

		}//end of if submit 2 was clicked 

		else if(e.getSource()==backToHome) { //if the back to home page button was clicked
			//set the home screen to visible
			login.setVisible(true);
			newAccount.setVisible(true);
			//set the new account screen to not visible
			lblName.setVisible(false);
			lblAddress.setVisible(false);
			lblTelephone.setVisible(false);
			lblSIN.setVisible(false);
			lblSavingsBalance.setVisible(false);
			lblCheckingBalance.setVisible(false);
			name.setVisible(false);
			address.setVisible(false);
			telephone.setVisible(false);
			sin.setVisible(false);
			savingsBalance.setVisible(false);
			checkingBalance.setVisible(false);
			lblUsername2.setVisible(false);
			lblPassword2.setVisible(false);
			usernameSet.setVisible(false);
			passwordSet.setVisible(false);
			submit2.setVisible(false);
			//set the existing user login screen to not visible
			lblUsername.setVisible(false);
			lblPassword.setVisible(false);
			username.setVisible(false);
			password.setVisible(false);
			submit.setVisible(false);
			//set the back to home screen button to invisible
			backToHome.setVisible(false);
			//set error message to invisible
			lblError.setVisible(false);
			//set the error message to invisible
			lblIncomplete.setVisible(false);

		}

	}

	/*
	 * Method to increase the data array lengths
	 */
	public void increase() {
		//create a new array with the length of the original array + the number to increase by
		Customer [] cust = new Customer [customers.length + 1];
		Chequing [] cList = new Chequing[cAccounts.length + 1];
		Savings [] sList = new Savings [sAccounts.length + 1];
		String [] passwords = new String [pass.length + 1];
		String [] usernames = new String [userN.length + 1];
		//loop through each element of the original array
		for (int i = 0; i < cAccounts.length; i++) {
			//copy the data from the original array to the new increased array
			cust[i] = customers[i];
			cList[i] = cAccounts[i];
			sList [i] = sAccounts[i];
			passwords[i] = pass[i];
			usernames[i] = userN[i];
		}//end of for
		//set the existing array to the new array
		customers = cust;
		cAccounts = cList;
		sAccounts = sList;
		pass = passwords;
		userN = usernames;
	}//end of increase


	/*
	 * Method to determine successful or unsuccessful login attempts
	 * passes in the inputed username and password
	 */
	public int loginAttempt (String username, String password) {
		//read the passwords from the text file
		try {
			readPasswords();
		} catch (IOException e) {
			//catches errors that mat arise
		}
		//loop through the userName list to find the matching username
		for (int i = 0; i < userN.length; i++) {
			//if the username is found
			if (username.equals(userN[i])) {
				//if the password at the same location matches
				if (password.equals(pass[i])) {
					//return the location in the array found
					return i;
				}
				else {
					//return an invalid array index (password wrong)
					return -1;
				}
			}//end of if
		}//end of for
		return -1; //username was not found
	}//end of loginAttempt method 

	/*
	 * Method to write customers and customers account data to a file and also write the customer login and transaction logs in seperate files
	 * passes in the array of customer, chequing and saving account objects
	 * Throws IOException
	 */
	public void writeAccounts (Customer c [], Chequing cq[], Savings sa[]) throws IOException {
		//open a new print writer to print the customer and account details
		PrintWriter writeFileAcc = new PrintWriter(new FileWriter("accounts.txt"));
		//open a new print writer to print the customer login details
		PrintWriter writeFileLogin = new PrintWriter(new FileWriter("loginData.txt"));

		//loop through each array
		for (int i = 0; i < c.length; i++) {
			//write the customer personal data into the file
			writeFileAcc.println(c[i].getName() + "/" + c[i].getAddress() + "/" + c[i].getPhoneNumber() + "/" + c[i].getSIN());
			//write the customer's savings balance into the file
			writeFileAcc.println(sa[i].getBalance());
			//write the chequing's savings balance into the file
			writeFileAcc.println(cq[i].getBalance());
			//write the user's login username and password into a seperate file
			writeFileLogin.println(userN[i]);
			writeFileLogin.println(pass[i]);
		}//end of for
		//print EOF in the other two files (customer and account data and logins) and close the print writers (complete writing)
		writeFileAcc.println("EOF");
		writeFileAcc.close();
		writeFileLogin.println("EOF");
		writeFileLogin.close();

		//open a new print writer to create a file for each customer's transaction log
		PrintWriter writeFileTransactionLog = new PrintWriter(new FileWriter(c[c.length-1].getName() + " TransactionLog.txt"));
		//print "EOF" at the end of file
		writeFileTransactionLog.println("EOF");
		//close the print writer to create the transaction log
		writeFileTransactionLog.close();

	}//end of writeAccounts

	/*
	 * method to read the stored passwords and store them in an existing array
	 */
	public void readPasswords() throws IOException{
		//Open a file to read
		BufferedReader reader = new BufferedReader(new FileReader("loginData.txt"));
		//initialize the length of the file to be 0
		int length = 0;
		//while the reader hasn't reached "EOF"
		while (!reader.readLine().equalsIgnoreCase("EOF")) {
			//add one to the length
			length = length + 1;
		}//end of while
		//close the reader
		reader.close();
		//create an array of contents the same length as the file
		String contents[] = new String[length];
		//open a file to read
		reader = new BufferedReader(new FileReader("loginData.txt"));
		//Read and loop through file contents
		for (int i = 0; i < contents.length; i++) {
			contents[i] = reader.readLine();
		}//end of for 
		//Close file
		reader.close();
		//reinitialize the username and password arrays to the length of half the file length
		userN = new String[contents.length/2];
		pass = new String[contents.length/2];

		//loop through each element of the contents array
		for (int i = 0; i < contents.length; i++) {
			//if i is divisible by 2
			if (i%2 == 0) {
				//add the username and password to respective arrays
				userN[i/2] = contents[i];
				pass[(i/2)] = contents [i+1];
			}//end of if
		}//end of for
	}//end of readPasswords

	/*
	 * Method to read customer and account data
	 * Throws IOException
	 */
	public void readData() throws IOException {
		//Open a file to read
		BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
		//initialize the length of the file to be 0
		int length = 0;
		//while the reader hasn't reached "EOF"
		while (!reader.readLine().equalsIgnoreCase("EOF")) {
			//add one to the length
			length = length + 1;
		}//end of while
		//close the reader
		reader.close();
		//create an array of contents the same length as the file
		String contents[] = new String[length];
		//open a file to read
		reader = new BufferedReader(new FileReader("accounts.txt"));
		//Read and loop through file contents
		for (int i = 0; i < contents.length; i++) {
			contents[i] = reader.readLine();
		}//end of for
		//Close file
		reader.close();
		//reinitialize the customer and accounts arrays to the length of a third the file length
		customers = new Customer[contents.length/3];
		sAccounts = new Savings[contents.length/3];
		cAccounts = new Chequing[contents.length/3];

		//loop through each element of the contents array
		for (int i = 0; i < contents.length; i++) {
			//if i is divisible by 3
			if (i%3 == 0) {
				//assign the customer and savings/chequings accounts the input parameters read from the file (existing data)
				customers[i/3] = new Customer(contents[i]);
				sAccounts[i/3] = new Savings(customers[i/3]);
				sAccounts[i/3].setBalance(Double.parseDouble(contents[i+1])); //set balance of savings account
				cAccounts[i/3] = new Chequing(customers[i/3]);
				cAccounts[i/3].setBalance(Double.parseDouble(contents[i+2])); //set balance of chequing account
			}//end of if
		}//end of for
	}//end of readData

	/**
	 * Main method for testing BankAccountUI GUI and its functions
	 */
	public static void main(String[] args){
		/*
		 * Look and feel of the UI
		 * Source: https://www.geeksforgeeks.org/java-swing-look-feel/
		 */
		try {
			// Change look and feel of program
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}

		catch (ClassNotFoundException e) {
			// handle exception
		}

		catch (InstantiationException e) {
			// handle exception
		}

		catch (IllegalAccessException e) {
			// handle exception
		}

		//create a new BankAccountUI to display the bank account login and create account GUI and test its functions
		new BankAccountUI();
	}

}
