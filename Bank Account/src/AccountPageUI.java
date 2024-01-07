import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 */

/**
 * @author Ashwin Santhosh
 *	Date: December 16, 2022
 *  Decription: Class constructs the GUI for the user account page once the user has logged in. The initial home page has 
 * 				options to view their chequing and savings account and view their complete transaction log. Users can choose 
 * 				to deposit or withdraw from each account as well as check their chequing and savings balance and transaction log 
 * 				once navigated to desired account through the account home page. All of user data is kept even if program is ended 
 * 				and reinitiated to allow user to log into an existing account. The initial home page also has the user information as well 
 * 				as a logout button. Users can click the logout button to return back to the BankAccountGUI page to log back in or create another account. 
 *  Method List: 
 * public AccountPageUI(Chequing cAccount, Savings sAccount, Customer customers) throws IOException - constructor to build the window for the user account
 * public void actionPerformed(ActionEvent e) - Method to listen to action events
 * public void balanceUpdate(Customer c, Chequing cq, Savings sq) throws IOException - Method to update the user's account balances in the accounts text file 
 * 																					containing all user's information and balances
 * public void writeFile (TransactionList list) throws IOException - Method to write the complete transaction list into the specified account's transaction log file
 * public void readFile () throws IOException - Method to read the complete transaction log for the specified account from that account's transaction log file
 * public TransactionList searchChequing (TransactionList tList) - Method to search for the chequing transaction records in the transaction list
 * public TransactionList searchSavings (TransactionList tList) - Method to search for the savings transaction records in the transaction list
 * static void main(String[] args) throws IOException - Main method to test AccountsPageUI GUI and its functions
 * 
 */
public class AccountPageUI extends JFrame implements ActionListener {
	//window components

	//images
	ImagePicture logo, pfp;
	//labels
	JLabel lblProfile, lblHello, lblName, lblChequingsAcc, lblSavingsAcc, lblDepositC, 
	lblDepositS, lblWithdrawC, lblWithdrawS, lblDollarSign, lblChequingBalance, lblSavingsBalance, 
	lblChequingBalanceTitle, lblSavingsBalanceTitle, lblCFullLog, lblSFullLog, lblAccountNum, lblFullLog,
	lblInfo, lblAddress, lblPhoneNumber, lblSIN, lblError, lblSuccess;
	//buttons
	JButton chequings, savings, fullLog, depositC, withdrawC, logC, depositS, withdrawS, 
	logS, returnHome, submitCDep, submitSDep, submitCWith, submitSWith, backC, backS, logout;
	//text input fields
	JTextField cDeposit, sDeposit, cWithdraw, sWithdraw;
	//chequing and savings accounts
	Chequing cAcc;
	Savings sAcc;
	//customer 
	Customer cus;
	//Output areas to display transaction records 
	JTextArea cOutputArea, sOutputArea, tOutputArea;
	//scroll panes to make output area scrollable
	JScrollPane cScrollPane, sScrollPane, tScrollPane;
	//Transaction list to hold transaction records
	TransactionList list = new TransactionList();
	//decimal format to two decimal places
	DecimalFormat twoDigits = new DecimalFormat("0.00");

	/**
	 * constructor to build the window for the user account
	 * Passes in the customer, chequing and savings account objects
	 * Throws IOException
	 */
	public AccountPageUI(Chequing cAccount, Savings sAccount, Customer customers) throws IOException{
		super("Account Page"); //name the window
		setLayout(null); //set the window layout to null

		//assign the customer and the savings/chequing accounts to the data passed into the constructor
		cAcc = cAccount;
		sAcc = sAccount;
		cus = customers;
		//reads the stored transaction log
		readFile();

		//create a new ImagePicture to display bank logo, set bounds and add to the window 
		logo = new ImagePicture(new ImageIcon("bank logo small.png"));
		logo.setBounds(0, 0, logo.getMyWidth(), logo.getMyHeight());
		add(logo);

		//Create a new label for the chequing/savings account number, set the font and bounds and add it to the window
		lblAccountNum = new JLabel("");
		lblAccountNum.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblAccountNum.setBounds(logo.getMyWidth() + 20, 30, 400, 20);
		add(lblAccountNum);
		//set the account number label to invisible
		lblAccountNum.setVisible(false);

		//create a new ImagePicture to display the profile picture, set bounds and add to the window 
		pfp = new ImagePicture(new ImageIcon("profile.png"));
		pfp.setBounds(890, 10, pfp.getMyWidth(), pfp.getMyHeight());
		add(pfp);

		//create a new label for the customer name, set the font and bounds and add to the window
		lblProfile = new JLabel(cus.getName());
		lblProfile.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblProfile.setBounds((885-(cus.getName().length()*8)), 40, (cus.getName().length()*8+10), 20);
		add(lblProfile);

		//home screen 

		//create a new label for user information, set the font and bounds and add it to the window
		lblInfo = new JLabel("Your Information");
		lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblInfo.setBounds(20, 110, 200, 20);
		add(lblInfo);

		//create a new label for user address, set the font and bounds and add it to the window
		lblAddress = new JLabel("Address:  " + cus.getAddress());
		lblAddress.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblAddress.setBounds(20, 140, 200, 20);
		add(lblAddress);

		//create a new label for user phone number, set the font and bounds and add it to the window
		lblPhoneNumber = new JLabel("Phone #:  " + cus.getPhoneNumber());
		lblPhoneNumber.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblPhoneNumber.setBounds(20, 160, 200, 20);
		add(lblPhoneNumber);

		//create a new label for user SIN number, set the font and bounds and add it to the window
		lblSIN = new JLabel("SIN #:  " + cus.getSIN());
		lblSIN.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblSIN.setBounds(20, 180, 180, 20);
		add(lblSIN);

		//create a new label for welcome message, set the font and bounds and add it to the window
		lblHello = new JLabel("Welcome,");
		lblHello.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblHello.setBounds(80, 320, 200, 40);
		add(lblHello);

		//create a new label for welcome message name, set the font and bounds and add it to the window
		lblName = new JLabel(cus.getName());
		lblName.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblName.setBounds(80, 370, (cus.getName().length()*15+10), 40);
		add(lblName);

		//create a new button for logging out, set the text of the button, bounds and the background and add it to the window
		logout = new JButton(new ImageIcon("leave (1).png"));
		logout.setText("    logout");
		logout.setBounds(20, 550, 150, 60);
		logout.setBackground(Color.GRAY);
		add(logout);

		//create a new button to view the chequing account, set background and bounds and add to the window
		chequings = new JButton("View Chequing Account");
		chequings.setBounds(600, 180, 200, 80);
		chequings.setBackground(new Color(201, 151, 12));
		add(chequings);
		//create a new button to view the savings account, set background and bounds and add to the window
		savings = new JButton("View Savings Account");
		savings.setBounds(600, 320, 200, 80);
		savings.setBackground(new Color(201, 151, 12));
		add(savings);
		//create a new button to view the user's full transaction history, set background and bounds and add to the window
		fullLog = new JButton("Full Transaction Log");
		fullLog.setBackground(new Color(201, 151, 12));
		fullLog.setBounds(600, 460, 200, 80);
		add(fullLog);


		//chequings account components

		//create a new label for the chequings account title, set the font and bounds and add it to the window
		lblChequingsAcc = new JLabel("Chequings");
		lblChequingsAcc.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblChequingsAcc.setBounds(420, 120, 200, 80);
		add(lblChequingsAcc);
		//create a new label for the deposit title, set the font and bounds and add it to the window
		depositC = new JButton("Deposit");
		depositC.setBackground(new Color(201, 151, 12));
		depositC.setBounds(400, 240, 200, 80);
		add(depositC);
		//create a new label for the withdraw title, set the font and bounds and add it to the window
		withdrawC = new JButton("Withdraw");
		withdrawC.setBackground(new Color(201, 151, 12));
		withdrawC.setBounds(400, 380, 200, 80);
		add(withdrawC);
		//create a new label for the chequings transaction log title, set the font and bounds and add it to the window
		logC = new JButton("Chequing Balance + Log");
		logC.setBackground(new Color(201, 151, 12));
		logC.setBounds(400, 520, 200, 80);
		add(logC);

		//set the chequing account page components to invisible initially
		lblChequingsAcc.setVisible(false);
		depositC.setVisible(false);
		withdrawC.setVisible(false);
		logC.setVisible(false);


		//savings account components

		//create a new label for the savings account title, set the font and bounds and add it to the window
		lblSavingsAcc = new JLabel("Savings");
		lblSavingsAcc.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblSavingsAcc.setBounds(440, 120, 200, 80);
		add(lblSavingsAcc);
		//create a new label for the deposit title, set the font and bounds and add it to the window
		depositS = new JButton("Deposit");
		depositS.setBackground(new Color(201, 151, 12));
		depositS.setBounds(400, 240, 200, 80);
		add(depositS);
		//create a new label for the withdraw title, set the font and bounds and add it to the window
		withdrawS = new JButton("Withdraw");
		withdrawS.setBackground(new Color(201, 151, 12));
		withdrawS.setBounds(400, 380, 200, 80);
		add(withdrawS);
		//create a new label for the savings transaction log title, set the font and bounds and add it to the window
		logS = new JButton("Savings Balance + Log");
		logS.setBackground(new Color(201, 151, 12));
		logS.setBounds(400, 520, 200, 80);
		add(logS);

		//set the savings account page components to invisible initially
		lblSavingsAcc.setVisible(false);
		depositS.setVisible(false);
		withdrawS.setVisible(false);
		logS.setVisible(false);

		//create a new button to return back to the home screen, set the background and bounds and add it to the window
		returnHome = new JButton("<-- Return to home Screen");
		returnHome.setBackground(new Color(201, 151, 12));
		returnHome.setBounds(20, 120, 200, 40);
		add(returnHome);

		//set the return home button to invisible initially
		returnHome.setVisible(false);

		//ERROR and SUCCESS messages 
		
		//create a new label for the error message, set the color, font and bounds and add it to the window
		lblError = new JLabel("ERROR");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblError.setBounds(430, 500, 460, 80);
		add(lblError);
		//create a new label for the success message, set the color, font and bounds and add it to the window
		lblSuccess = new JLabel("SUCCESS");
		lblSuccess.setForeground(Color.GREEN);
		lblSuccess.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblSuccess.setBounds(400, 500, 460, 80);
		add(lblSuccess);
		//set both labels initially to invisible
		lblError.setVisible(false);
		lblSuccess.setVisible(false);

		//chequing deposit screen

		//create a new label for the chequings deposit amount title, set the font and bounds and add it to the window
		lblDepositC = new JLabel("Amount to Deposit:");
		lblDepositC.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblDepositC.setBounds(320, 240, 400, 80);
		add(lblDepositC);
		//create a new textfield for the chequings deposit amount, set the font and bounds and add it to the window
		cDeposit = new JTextField();
		cDeposit.setBounds(370, 333, 300, 40);
		cDeposit.setFont(new Font("SansSerif", Font.PLAIN, 30));
		add(cDeposit);
		//create a new button to submit the deposit, set the color and bounds and add it to the window
		submitCDep = new JButton("Deposit");
		submitCDep.setBackground(new Color(201, 151, 12));
		submitCDep.setBounds(425, 410, 150, 60);
		add(submitCDep);

		//set the chequing deposit screen components to invisible initially
		lblDepositC.setVisible(false);
		cDeposit.setVisible(false);
		submitCDep.setVisible(false);


		//chequing withdraw screen

		//create a new label for the chequings withdraw amount title, set the font and bounds and add it to the window
		lblWithdrawC = new JLabel("Amount to Withdraw:");
		lblWithdrawC.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblWithdrawC.setBounds(320, 240, 460, 80);
		add(lblWithdrawC);
		//create a new textfield for the chequings withdraw amount, set the font and bounds and add it to the window
		cWithdraw = new JTextField();
		cWithdraw.setBounds(370, 333, 300, 40);
		cWithdraw.setFont(new Font("SansSerif", Font.PLAIN, 30));
		add(cWithdraw);
		//create a new button to submit the deposit, set the color and bounds and add it to the window
		submitCWith = new JButton("Withdraw");
		submitCWith.setBackground(new Color(201, 151, 12));
		submitCWith.setBounds(425, 410, 150, 60);
		add(submitCWith);

		//set the chequing withdraw screen components to invisible initially
		lblWithdrawC.setVisible(false);
		cWithdraw.setVisible(false);
		submitCWith.setVisible(false);

		//create a new button to go back to the chequings home page, set the color and bounds and add it to the window
		backC = new JButton("<-- Back to Chequings Page");
		backC.setBackground(new Color(201, 151, 12));
		backC.setBounds(20, 120, 200, 40);
		add(backC);
		//set the back to chequings home page button to invisible
		backC.setVisible(false);


		//savings deposit screen

		//create a new label for the savings deposit amount title, set the font and bounds and add it to the window
		lblDepositS = new JLabel("Amount to Deposit:");
		lblDepositS.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblDepositS.setBounds(320, 240, 400, 80);
		add(lblDepositS);
		//create a new textfield for the savings deposit amount, set the font and bounds and add it to the window
		sDeposit = new JTextField();
		sDeposit.setBounds(370, 333, 300, 40);
		sDeposit.setFont(new Font("SansSerif", Font.PLAIN, 30));
		add(sDeposit);
		//create a new button to submit the withdrawl, set the color and bounds and add it to the window
		submitSDep = new JButton("Deposit");
		submitSDep.setBounds(425, 410, 150, 60);
		add(submitSDep);

		//set the savings deposit screen components to not visible
		lblDepositS.setVisible(false);
		sDeposit.setVisible(false);
		submitSDep.setVisible(false);


		//savings withdraw screen

		//create a new label for the savings withdraw amount title, set the font and bounds and add it to the window
		lblWithdrawS = new JLabel("Amount to Withdraw:");
		lblWithdrawS.setFont(new Font("SansSerif", Font.BOLD, 40));
		lblWithdrawS.setBounds(320, 240, 460, 80);
		add(lblWithdrawS);
		//create a new textfield for the savings withdraw amount, set the font and bounds and add it to the window
		sWithdraw = new JTextField();
		sWithdraw.setBounds(370, 333, 300, 40);
		sWithdraw.setFont(new Font("SansSerif", Font.PLAIN, 30));
		add(sWithdraw);
		//create a new button to submit the withdrawl, set the color and bounds and add it to the window
		submitSWith = new JButton("Withdraw");
		submitSWith.setBounds(425, 410, 150, 60);
		add(submitSWith);

		//set the savings withdraw screen components to invisible
		lblWithdrawS.setVisible(false);
		sWithdraw.setVisible(false);
		submitSWith.setVisible(false);

		//create a new label for the dollar sign, set the font and bounds and add it to the window
		lblDollarSign = new JLabel("$");
		lblDollarSign.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblDollarSign.setBounds(340, 312, 20, 80);
		add(lblDollarSign);
		//set the dollar sign initially to invisible
		lblDollarSign.setVisible(false);

		//create a new button to return back to the savings account home page, set the color and bounds and add it to the window
		backS = new JButton("<-- Back to Savings page");
		backS.setBackground(new Color(201, 151, 12));
		backS.setBounds(20, 120, 200, 40);
		add(backS);
		//set the back to savings home page button to false initially
		backS.setVisible(false);


		//chequing check balance screen 

		//create a new label for the chequing account balance title, set the font and bounds and add it to the window
		lblChequingBalanceTitle = new JLabel("Chequing Balance");
		lblChequingBalanceTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblChequingBalanceTitle.setBounds(250, 170, 300, 40);
		add(lblChequingBalanceTitle);
		//create a new label for the chequing account balance, set the font and bounds and add it to the window
		lblChequingBalance = new JLabel("$" + twoDigits.format(cAcc.getBalance()));
		lblChequingBalance.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblChequingBalance.setBounds(315, 210, 300, 40);
		add(lblChequingBalance);
		//create a new label for the chequing account transaction log, set the font and bounds and add it to the window
		lblCFullLog = new JLabel("Chequing Transaction Log");
		lblCFullLog.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblCFullLog.setBounds(200, 270, 450, 40);
		add(lblCFullLog);
		//create an output area to display the chequing transation log
		cOutputArea = new JTextArea(20, 10);
		//set the tab size between each column
		cOutputArea.setTabSize(15);
		//make the outputed/displayed table non-editable
		cOutputArea.setEditable(false);
		//set the background to black
		cOutputArea.setBackground(Color.BLACK);
		//change the colour of the text to white
		cOutputArea.setForeground(Color.WHITE);
		//create a scroll pane
		cScrollPane = new JScrollPane(cOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//set the bounds of the scrollpane 
		cScrollPane.setBounds(200, 330, 400, 250);
		//add it to the window
		add(cScrollPane);

		//set the chequing check balance screen components to invisible
		lblChequingBalanceTitle.setVisible(false);
		lblChequingBalance.setVisible(false);
		lblCFullLog.setVisible(false);
		cScrollPane.setVisible(false);


		//savings check balance screen 

		//create a new label for the savings account balance title, set the font and bounds and add it to the window
		lblSavingsBalanceTitle = new JLabel("Savings Balance");
		lblSavingsBalanceTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblSavingsBalanceTitle.setBounds(250, 170, 300, 40);
		add(lblSavingsBalanceTitle);
		//create a new label for the savings account balance, set the font and bounds and add it to the window
		lblSavingsBalance = new JLabel("$" + twoDigits.format(sAcc.getBalance()));
		lblSavingsBalance.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblSavingsBalance.setBounds(315, 210, 300, 40);
		add(lblSavingsBalance);
		//create a new label for the savings account transaction log, set the font and bounds and add it to the window
		lblSFullLog = new JLabel("Savings Transaction Log");
		lblSFullLog.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblSFullLog.setBounds(200, 270, 450, 40);
		add(lblSFullLog);
		//create an output area to display the savings transation log
		sOutputArea = new JTextArea(20, 10);
		//set the tab size between each column
		sOutputArea.setTabSize(15);
		//make the outputed/displayed table non-editable
		sOutputArea.setEditable(false);
		//set the background to black
		sOutputArea.setBackground(Color.BLACK);
		//change the colour of the text to white
		sOutputArea.setForeground(Color.WHITE);
		//create a scrollpane
		sScrollPane = new JScrollPane(sOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//set the bounds of the scrollpane 
		sScrollPane.setBounds(200, 330, 400, 250);
		//add it to the window
		add(sScrollPane);

		//set the savings check balance screen components to invisible
		lblSavingsBalanceTitle.setVisible(false);
		lblSavingsBalance.setVisible(false);
		lblSFullLog.setVisible(false);
		sScrollPane.setVisible(false);


		//account full transaction log screen

		//create a new label for the total transaction log title, set the font and bounds and add it to the window
		lblFullLog = new JLabel("Full Transaction Log");
		lblFullLog.setFont(new Font("SansSerif", Font.BOLD, 30));
		lblFullLog.setBounds(340, 250, 450, 40);
		add(lblFullLog);
		//create an output area to display the full transation log
		tOutputArea = new JTextArea(20, 10);
		//set the tab size between each column
		tOutputArea.setTabSize(15);
		//make the outputed/displayed table non-editable
		tOutputArea.setEditable(false);
		//set the background to black
		tOutputArea.setBackground(Color.BLACK);
		//change the colour of the text to white
		tOutputArea.setForeground(Color.WHITE);
		//set the text of the output area to the transaction list
		tOutputArea.setText(list.toString());
		//create a scrollpane
		tScrollPane = new JScrollPane(tOutputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//set the bounds of the scroll pane
		tScrollPane.setBounds(240, 300, 500, 250);
		//add the scroll pane to the window
		add(tScrollPane);

		//set the account full transaction log screen components to initially invisible
		lblFullLog.setVisible(false);
		tScrollPane.setVisible(false);

		//add the buttons as listeners in this class
		chequings.addActionListener(this);
		savings.addActionListener(this);
		fullLog.addActionListener(this);
		returnHome.addActionListener(this);
		depositC.addActionListener(this);
		withdrawC.addActionListener(this);
		logC.addActionListener(this);
		depositS.addActionListener(this);
		withdrawS.addActionListener(this);
		logS.addActionListener(this);
		backC.addActionListener(this);
		backS.addActionListener(this);
		submitCDep.addActionListener(this);
		submitCWith.addActionListener(this);
		submitSDep.addActionListener(this);
		submitSWith.addActionListener(this);
		logout.addActionListener(this);

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
		if (e.getSource() == chequings) { //if view chequings account was chosen
			//set the account id label to the chequing account id
			lblAccountNum.setText("Chequing Account ID: " + cAcc.getAccNum());
			lblAccountNum.setVisible(true);

			//set the user information labels to invisible
			lblInfo.setVisible(false);
			lblAddress.setVisible(false);
			lblPhoneNumber.setVisible(false);
			lblSIN.setVisible(false);

			//set the home screen components to invisible
			lblHello.setVisible(false);
			lblName.setVisible(false);
			chequings.setVisible(false);
			savings.setVisible(false);
			fullLog.setVisible(false);
			logout.setVisible(false);

			//set the chequing account home screen components to visible
			lblChequingsAcc.setVisible(true);
			depositC.setVisible(true);
			withdrawC.setVisible(true);
			logC.setVisible(true);

			//set the return home button to visible
			returnHome.setVisible(true);
		}

		else if (e.getSource() == savings) { //if view savings account was chosen
			//set the account id label to the savings account id
			lblAccountNum.setText("Savings Account ID: " + sAcc.getAccNum());
			lblAccountNum.setVisible(true);

			//set the user information labels to invisible
			lblInfo.setVisible(false);
			lblAddress.setVisible(false);
			lblPhoneNumber.setVisible(false);
			lblSIN.setVisible(false);

			//set the home screen components to invisible
			lblHello.setVisible(false);
			lblName.setVisible(false);
			chequings.setVisible(false);
			savings.setVisible(false);
			fullLog.setVisible(false);
			logout.setVisible(false);

			//set the savings account home screen components to visible
			lblSavingsAcc.setVisible(true);
			depositS.setVisible(true);
			withdrawS.setVisible(true);
			logS.setVisible(true);

			//set the return home button to visible
			returnHome.setVisible(true);
		}

		else if (e.getSource() == fullLog) { //if view full transaction log was chosen
			//set the user information labels to invisible
			lblInfo.setVisible(false);
			lblAddress.setVisible(false);
			lblPhoneNumber.setVisible(false);
			lblSIN.setVisible(false);

			//set the home screen components to invisible
			lblHello.setVisible(false);
			lblName.setVisible(false);
			chequings.setVisible(false);
			savings.setVisible(false);
			fullLog.setVisible(false);
			logout.setVisible(false);

			//set the full transaction log page components to visible
			tScrollPane.setVisible(true);
			lblFullLog.setVisible(true);
			//set the return home button to visible
			returnHome.setVisible(true);
		}

		else if (e.getSource() == logout) { //if logout was chosen
			dispose(); //dispose the window
		}

		else if (e.getSource() == returnHome) { //if return home was chosen
			//set user information to visible
			lblInfo.setVisible(true);
			lblAddress.setVisible(true);
			lblPhoneNumber.setVisible(true);
			lblSIN.setVisible(true);
			//set home screen components to visible
			lblHello.setVisible(true);
			lblName.setVisible(true);
			chequings.setVisible(true);
			savings.setVisible(true);
			fullLog.setVisible(true);
			logout.setVisible(true);
			//set chequings account homepage components to false
			lblChequingsAcc.setVisible(false);
			depositC.setVisible(false);
			withdrawC.setVisible(false);
			logC.setVisible(false);
			//set savings account homepage components to false
			lblSavingsAcc.setVisible(false);
			depositS.setVisible(false);
			withdrawS.setVisible(false);
			logS.setVisible(false);
			//set the account id label to empty
			lblAccountNum.setText("");
			//set it to not visible
			lblAccountNum.setVisible(false);
			//set the account full transaction log screen components to invisible
			lblFullLog.setVisible(false);
			tScrollPane.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
		}

		else if (e.getSource() == depositC) { //if user chooses deposit into chequings account
			//set the chequings account home page components to invisible
			lblChequingsAcc.setVisible(false);
			depositC.setVisible(false);
			withdrawC.setVisible(false);
			logC.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the chequings account deposit screen to visible
			lblDepositC.setVisible(true);
			lblDollarSign.setVisible(true);
			cDeposit.setVisible(true);
			submitCDep.setVisible(true);
			//set the back to chequings home page button to visible
			backC.setVisible(true);
		}

		else if (e.getSource() == withdrawC) { //if user chooses withdraw into chequings account
			//set the chequings account home page components to invisible
			lblChequingsAcc.setVisible(false);
			depositC.setVisible(false);
			withdrawC.setVisible(false);
			logC.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the chequings account withdraw screen to visible
			lblWithdrawC.setVisible(true);
			lblDollarSign.setVisible(true);
			cWithdraw.setVisible(true);
			submitCWith.setVisible(true);
			//set the back to chequings home page button to visible
			backC.setVisible(true);
		}

		else if (e.getSource() == logC) { //if user chooses to view the chequings balance and transaction log
			//set the chequings account home page components to invisible
			lblChequingsAcc.setVisible(false);
			depositC.setVisible(false);
			withdrawC.setVisible(false);
			logC.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the chequing balance and chequing transaction log screen to visible
			lblChequingBalanceTitle.setVisible(true);
			lblChequingBalance.setVisible(true);
			lblCFullLog.setVisible(true);
			cScrollPane.setVisible(true);
			//set the back to chequings home page button to visible
			backC.setVisible(true);

			//search for the chequing account transactions and set the list to the chequing transaction log output area
			TransactionList temp = searchChequing(list);
			cOutputArea.setText(temp.toString());
		}

		else if (e.getSource() == depositS) { //if user chooses to deposit into savings account
			//set the savings home screen components to invisible
			lblSavingsAcc.setVisible(false);
			depositS.setVisible(false);
			withdrawS.setVisible(false);
			logS.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the savings account deposit screen to visible
			lblDepositS.setVisible(true);
			lblDollarSign.setVisible(true);
			sDeposit.setVisible(true);
			submitSDep.setVisible(true);
			//set the back to savings account button to visible
			backS.setVisible(true);
		}

		else if (e.getSource() == withdrawS) { //if user chooses to withdraw from savings account
			//set the savings home screen components to invisible
			lblSavingsAcc.setVisible(false);
			depositS.setVisible(false);
			withdrawS.setVisible(false);
			logS.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the savings account withdraw screen to visible
			lblWithdrawS.setVisible(true);
			lblDollarSign.setVisible(true);
			sWithdraw.setVisible(true);
			submitSWith.setVisible(true);
			//set the back to savings account button to visible
			backS.setVisible(true);
		}

		else if (e.getSource() == logS) { //if user chooses to view the savings balance and transaction log
			//set the savings home screen components to invisible
			lblSavingsAcc.setVisible(false);
			depositS.setVisible(false);
			withdrawS.setVisible(false);
			logS.setVisible(false);
			//set the return home button to invisible
			returnHome.setVisible(false);
			//set the savings balance and savings transaction log screen to visible
			lblSavingsBalanceTitle.setVisible(true);
			lblSavingsBalance.setVisible(true);
			lblSFullLog.setVisible(true);
			sScrollPane.setVisible(true);
			//set the back to savings account button to visible
			backS.setVisible(true);
			//search for the savings account transactions and set the list to the savings transaction log output area
			sOutputArea.setText((searchSavings(list)).toString());
		}

		else if (e.getSource() == backC) { //if user selects the back to chequing account home screen button
			//set the chequing home screen components to invisible
			lblChequingsAcc.setVisible(true);
			depositC.setVisible(true);
			withdrawC.setVisible(true);
			logC.setVisible(true);
			//set the return home button to visible
			returnHome.setVisible(true);
			//set the chequing account deposit screen to visible
			lblDepositC.setVisible(false);
			lblDollarSign.setVisible(false);
			cDeposit.setVisible(false);
			submitCDep.setVisible(false);
			//set the chequing account withdraw screen to visible
			lblWithdrawC.setVisible(false);
			lblDollarSign.setVisible(false);
			cWithdraw.setVisible(false);
			submitCWith.setVisible(false);
			//set the chequing balance and chequing transaction log screen to visible
			lblChequingBalanceTitle.setVisible(false);
			lblChequingBalance.setVisible(false);
			lblCFullLog.setVisible(false);
			cScrollPane.setVisible(false);
			//set the error and success message labels to invisible
			lblError.setVisible(false);
			lblSuccess.setVisible(false);
			//set the back to chequing account home screen button to visible
			backC.setVisible(false);
		}

		else if (e.getSource() == backS) { //if user selects the back to savings account home screen button
			//set the savings home screen components to invisible
			lblSavingsAcc.setVisible(true);
			depositS.setVisible(true);
			withdrawS.setVisible(true);
			logS.setVisible(true);
			//set the return home button to visible
			returnHome.setVisible(true);
			//set the savings account deposit screen to visible
			lblDepositS.setVisible(false);
			lblDollarSign.setVisible(false);
			sDeposit.setVisible(false);
			submitSDep.setVisible(false);
			//set the savings account withdraw screen to visible
			lblWithdrawS.setVisible(false);
			lblDollarSign.setVisible(false);
			sWithdraw.setVisible(false);
			submitSWith.setVisible(false);
			//set the savings balance and savings transaction log screen to visible
			lblSavingsBalanceTitle.setVisible(false);
			lblSavingsBalance.setVisible(false);
			lblSFullLog.setVisible(false);
			sScrollPane.setVisible(false);
			//set the error and success message labels to invisible
			lblError.setVisible(false);
			lblSuccess.setVisible(false);
			//set the back to savings account home screen button to visible
			backS.setVisible(false);
		}

		else if (e.getSource() == submitCDep) { //if the submit deposit button for the chequing deposit was clicked
			lblSuccess.setVisible(false); //make the success label invisible
			lblError.setVisible(false); //make the error label invisible
			//if the deposit input field is empty
			if (cDeposit.getText().equals("")) {
				lblError.setVisible(true); //make the error label visible
			}
			//if the inputed deposit amount is greater than 0
			else if (Double.parseDouble(cDeposit.getText()) >0) {
				//create a transaction record string for the action performed and previous balance
				String record = "c/Deposit/" + Double.parseDouble(cDeposit.getText()) + "/" + cAcc.getBalance()+ "/";
				//deposit the inputed value into the chequing account
				cAcc.deposit(Double.parseDouble(cDeposit.getText()));
				//update the balance in the user's saved data on file
				try {
					//call the method to update the balance in the user's saved data on file
					balanceUpdate(cus, cAcc, sAcc);
				}
				catch (IOException a){
					//handle exceptions
				}
				//add updated balance to transaction record string
				record = record + cAcc.getBalance();

				// create a Transaction Record object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // Process the record string in the tInfo

				if (list.insert(tInfo)) { //add the record to the list of transactions if there is space in the list
					lblSuccess.setVisible(true); //make the success label visible
				}
				else { //if the list is full
					list.increase(1); //increase the length of the list by 1
					list.insert(tInfo); //add the record to the list of transactions
					lblSuccess.setVisible(true); //make the success label visible
				}
				//set the chequing deposit input field to empty
				cDeposit.setText("");
				//set the output area to the updated complete transaction list
				tOutputArea.setText(list.toString());
				//update the chequing balance label to the updated balance
				lblChequingBalance.setText("$" + twoDigits.format(cAcc.getBalance()));
				//try writing the new transaction list to the transaction log file for the user
				try {
					//call method to write transaction list to the file
					writeFile(list);
				} 
				catch (IOException e1) {
					//handle exceptions
				}
			}
			//if the inputed value to deposit is < 0
			else {
				cDeposit.setText(""); //clear the deposit amount input field
				lblError.setVisible(true); //make the error label visible
			}
		}

		else if (e.getSource() == submitCWith) { //if the submit withdraw button for the chequing deposit was clicked
			lblSuccess.setVisible(false); //make the success label invisible
			lblError.setVisible(false); //make the error label invisible
			//if the amount to withdraw input field is empty
			if (cWithdraw.getText().equals("")) {
				lblError.setVisible(true); //make the error label visible
			}
			//if the amount to withdraw will result in account balance to be negative
			else if (cAcc.getBalance() - Double.parseDouble(cWithdraw.getText()) < 0) {
				//clear the withdraw amount input field
				cWithdraw.setText("");
				lblError.setVisible(true); //make the error label visible
			}
			//if the amount to withdraw is > 0
			else if (Double.parseDouble(cWithdraw.getText()) >0) {
				//create a transaction record string for the action performed and previous balance
				String record = "c/withdrawl/" + Double.parseDouble(cWithdraw.getText()) + "/" + cAcc.getBalance()+ "/";
				//call the withdraw function to withdraw from the chequing account
				cAcc.withdraw(Double.parseDouble(cWithdraw.getText()));
				//try updating the balance of the user's chequing account in the data file
				try {
					//call method to update the chequing balance of the user in the stored data on file
					balanceUpdate(cus, cAcc, sAcc);
				}
				catch (IOException a){
					//handle exceptions
				}
				//add updated balance to transaction record string
				record = record + cAcc.getBalance();

				// create a Transaction Record object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // Process the record in the tInfo

				if (list.insert(tInfo)) { //add the record to the list of transactions if there is space in the list
					lblSuccess.setVisible(true); //make the success label visible
				}
				else { //if the list is full
					list.increase(1); //increase the list by 1
					list.insert(tInfo); //add the record to the list of transactions
					lblSuccess.setVisible(true); //make the success label visible
				}
				//clear the withdraw amount input field
				cWithdraw.setText("");
				//set the output area to the updated complete transaction list
				tOutputArea.setText(list.toString());
				//set the chequing balance label to the updated chequing account balance
				lblChequingBalance.setText("$" + twoDigits.format(cAcc.getBalance()));
				//try writing the updated transaction log into the user's transaction log file
				try {
					//call method to write to transaction log file
					writeFile(list);
				} 
				catch (IOException e1) {
					//handle exceptions
				}
			}
			//if the user inputed withdraw amount is < 0
			else {
				sWithdraw.setText(""); //clear the chequing withdraw amount input field
				lblError.setVisible(true); //make the error label visible
			}
		}

		else if (e.getSource() == submitSDep) { //if the submit deposit button for the savings deposit was clicked
			lblSuccess.setVisible(false); //make the success label invisible
			lblError.setVisible(false); //make the error label invisible
			//if the amount to deposit input field is empty
			if (sDeposit.getText().equals("")) {
				lblError.setVisible(true); //make the error label visible
			}
			//if the amount to deposit is > 0
			else if (Double.parseDouble(sDeposit.getText()) >0) {
				//create a transaction record string for the action performed and previous balance
				String record = "s/Deposit/" + Double.parseDouble(sDeposit.getText()) + "/" + sAcc.getBalance()+ "/";
				//call the deposit function to deposit into the user's savings account
				sAcc.deposit(Double.parseDouble(sDeposit.getText()));
				//try updating the balance of the user's savings account in the data file
				try {
					//call method to update the savings balance of the user in the stored data on file
					balanceUpdate(cus, cAcc, sAcc);
				}
				catch (IOException a){
					//handle exceptions
				}
				//add updated balance to transaction record string
				record = record + sAcc.getBalance();

				// create a Transaction Record object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // Process the record in the tInfo

				if (list.insert(tInfo)) { //add the record to the list of transactions if there is space in the list
					lblSuccess.setVisible(true); //make the success label visible
				}
				else { //if the list is full
					list.increase(1); //increase the size of the list by 1
					list.insert(tInfo); //add the record to the list of transactions
					lblSuccess.setVisible(true); //make the success label visible
				}
				//clear the deposit amount input field
				sDeposit.setText("");
				//set the output area to the updated complete transaction list
				tOutputArea.setText(list.toString());
				//set the savings balance label to the updated savings account balance
				lblSavingsBalance.setText("$" + twoDigits.format(sAcc.getBalance()));
				//try writing the updated transaction log into the user's transaction log file
				try {
					//call method to write to transaction log file
					writeFile(list);
				} 
				catch (IOException e1) {
					//handle exceptions
				}
			}
			//if the user inputed deposit amount is < 0
			else {
				sDeposit.setText(""); //clear the savings deposit amount input field
				lblError.setVisible(true); //make the error label visible
			}
		}

		else if (e.getSource() == submitSWith) { //if the submit withdraw button for the savings deposit was clicked
			lblSuccess.setVisible(false); //make the success label invisible
			lblError.setVisible(false); //make the error label invisible
			//if the amount to withdraw input field is empty
			if (sWithdraw.getText().equals("")) {
				lblError.setVisible(true); //make the error label visible
			}
			//if the amount to withdraw will result in account balance to be negative
			else if (sAcc.getBalance() - Double.parseDouble(sWithdraw.getText()) < 0) {
				//clear the withdraw amount input field
				sWithdraw.setText("");
				lblError.setVisible(true); //make the error label visible
			}
			//if the amount to withdraw is > 0
			else if (Double.parseDouble(sWithdraw.getText()) >0) {
				//create a transaction record string for the action performed and previous balance
				String record = "s/withdrawl/" + Double.parseDouble(sWithdraw.getText()) + "/" + sAcc.getBalance()+ "/";
				//call the withdraw function to withdraw into the user's savings account
				sAcc.withdraw(Double.parseDouble(sWithdraw.getText()));
				//try updating the balance of the user's savings account in the data file	
				try {
					//call method to update the savings balance of the user in the stored data on file
					balanceUpdate(cus, cAcc, sAcc);
				}
				catch (IOException a){
					//handle exceptions
				}
				//add updated balance to transaction record string
				record = record + sAcc.getBalance();

				// create a Transaction Record object
				TransactionRecord tInfo = new TransactionRecord();
				tInfo.processRecord(record); // Process the record in the tInfo


				if (list.insert(tInfo)) { //add the record to the list of transactions if there is space in the list
					lblSuccess.setVisible(true); //make the success label visible
				}
				else { //if the list is full
					list.increase(1); //increase the size of the list by 1
					list.insert(tInfo); //add the record to the list of transactions
					lblSuccess.setVisible(true); //make the success label visible
				}
				//clear the withdraw amount input field
				sWithdraw.setText("");
				//set the output area to the updated complete transaction list
				tOutputArea.setText(list.toString());
				//set the savings balance label to the updated savings account balance
				lblSavingsBalance.setText("$" + twoDigits.format(sAcc.getBalance()));
				//try writing the updated transaction log into the user's transaction log file
				try { 
					//call method to write to transaction log file
					writeFile(list);
				} 
				catch (IOException e1) {
					//handle exceptions
				}
			}
			//if the user inputed deposit amount is < 0
			else {
				sWithdraw.setText(""); //clear the savings deposit amount input field
				lblError.setVisible(true); //make the error label visible
			}
		}
	}
	/*
	 * Method to update the user's account balances in the accounts text file containing all user's information and balances
	 * Input parameters of customer, chequing and savings account
	 * Throws IOException
	 */
	public void balanceUpdate(Customer c, Chequing cq, Savings sq) throws IOException {
		//Open the file to read
		BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"));
		//initialize the length of the file to 0
		int length = 0;
		//while the line read is not "EOF"
		while (!reader.readLine().equalsIgnoreCase("EOF")) {
			//add one to the length
			length = length + 1;
		}
		//close the reader
		reader.close();
		//create a new array to contain the file contents initialized to the length of the file
		String contents[] = new String[length];
		//Open the file to read
		reader = new BufferedReader(new FileReader("accounts.txt"));
		//Read and loop through file contents
		for (int i = 0; i < contents.length; i++) {
			//add the content at the line to the contents array
			contents[i] = reader.readLine();
		}
		//Close file
		reader.close();

		//loop through the contents array to update the balance of the updated account
		for (int i = 0; i < contents.length; i++) {
			//if the customer at contenrs[i] is equal to the passed in customer
			if (contents[i].equals(c.getName() + "/" + c.getAddress() + "/" + c.getPhoneNumber() + "/" + c.getSIN())) {
				//update the balance of the chequing and savings account in the contents array
				contents[i+1] = Double.toString(sq.getBalance());
				contents[i+2] = Double.toString(cq.getBalance());
				//break out of the for loop
				break;
			}
		}
		//Open the file to write to
		PrintWriter writeFileAcc = new PrintWriter(new FileWriter("accounts.txt"));
		//loop through the contents array
		for (int i = 0; i < contents.length; i++) {
			//write the data at contents[i] into the specified file
			writeFileAcc.println(contents[i]);
		}
		//print "EOF" at the end of the file
		writeFileAcc.println("EOF");
		//close the writer
		writeFileAcc.close();
	}

	/*
	 * Method to write the complete transaction list into the specified account's transaction log file
	 * passes in the account's transaction list
	 */
	public void writeFile (TransactionList list) throws IOException {
		//Declare write to file
		PrintWriter writeF = new PrintWriter(new FileWriter(cus.getName() + " TransactionLog.txt"));
		//loop through each element of the transaction list while the element at list[i] is not empty
		for (int i = 0; i < list.getList().length && list.getList()[i] != null; i++) {
			//write the transaction record in the transaction log file for the specified account
			writeF.println(list.getList()[i].getDate() + "/" + list.getList()[i].getAccountType() + "/" + list.getList()[i].getTransactionType() + "/" + list.getList()[i].getTransactionAmount() + "/" + list.getList()[i].getStartBalance() + "/" + list.getList()[i].getEndBalance());
		}
		//Print "EOF" in the transaction log file for the specified account
		writeF.println("EOF");
		//Close file
		writeF.close();
	}

	/*
	 * Method to read the complete transaction log for the specified account from that account's transaction log file
	 * Throws IOException
	 */
	public void readFile () throws IOException {
		//Open the transaction log file to read
		BufferedReader reader = new BufferedReader(new FileReader(cus.getName() + " TransactionLog.txt"));
		//initialize variable for file length to 0
		int length = 0;

		//Read through file until "EOF" is reached
		while (!reader.readLine().equalsIgnoreCase("EOF")) {
			//add one to the length
			length = length + 1;
		}
		//Close file
		reader.close();
		//create a temporary transaction list
		TransactionList listTemp = new TransactionList();
		//Declare and initialize string array for fileContents to the length of the file
		String fileContents [] = new String [length];

		//Open the transaction log file to read
		reader = new BufferedReader(new FileReader(cus.getName() + " TransactionLog.txt"));
		//Read and loop through file contents
		for (int i = 0; i < fileContents.length; i++) {
			//assign the read data from the file to the element[i] of the fileContents array
			fileContents[i] = reader.readLine();
			//create a new transaction record
			TransactionRecord tRecord = new TransactionRecord();
			//process the read data
			tRecord.processRecordDate(fileContents[i]);
			if (listTemp.insert(tRecord)) { //if inserting the new transaction record into the list was successful (available space)
				//do nothing
			}
			else { //if there is no available space in the list
				listTemp.increase(1); //increase the length of the list by one
				listTemp.insert(tRecord); //insert the transaction record into the list
			}
		}
		//Close file
		reader.close();
		//set the transaction list of the account to the temporary transaction list
		list = listTemp;
	}

	/*
	 * Method to search for the chequing transaction records in the transaction list
	 * Passes in the complete transaction list
	 */
	public TransactionList searchChequing (TransactionList tList) {
		//create a new transaction list for the chequing transaction records
		TransactionList cList = new TransactionList();
		//loop through each element of the complete transactions list array
		for (int i = 0; i < tList.getList().length; i++) {
			//if the type of transaction record at the element[i] in the transaction list is chequing
			if (tList.getList()[i].getAccountType() == 'c') {
				//insert the transaction record into the chequing transaction list (if space available)
				if (cList.insert(tList.getList()[i])) {
					//do nothing
				}
				//if the chequing transaction list is full
				else {
					cList.increase(1); //increase the chequing transaction list size by one
					cList.insert(tList.getList()[i]); //insert the transaction record into the chequing transaction list
				}
			}
		}
		//return the chequing transaction list
		return cList;
	}

	/*
	 * Method to search for the savings transaction records in the transaction list
	 * Passes in the complete transaction list
	 */
	public TransactionList searchSavings (TransactionList tList) {
		//create a new transaction list for the savings transaction records
		TransactionList sList = new TransactionList();
		//loop through each element of the complete transactions list array
		for (int i = 0; i < tList.getList().length; i++) {
			//if the type of transaction record at the element[i] in the transaction list is savings
			if (tList.getList()[i].getAccountType() == 's') {
				//insert the transaction record into the savings transaction list (if space available)
				if (sList.insert(tList.getList()[i])) {
					//do nothing
				}
				//if the savings transaction list is full
				else {
					sList.increase(1); //increase the savings transaction list size by one
					sList.insert(tList.getList()[i]); //insert the transaction record into the savings transaction list
				}
			}
		}
		//return the savings transaction list
		return sList;
	}



	/**
	 * Main method to test AccountsPageUI GUI and its functions
	 */
	public static void main(String[] args) throws IOException {
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
		
		//create a new customer object with passed in details
		Customer customer = new Customer("Bim Bob/52 olde Town Rd/6475237830/11223344");
		//create a new chequing account for the passed in customer
		Chequing cAccount = new Chequing(customer);
		//set the balance of the chequing account to 5000
		cAccount.setBalance(5000);
		//create a new savings account for the passed in customer
		Savings sAccount = new Savings(customer);
		//set the balance of the savings account to 5000
		sAccount.setBalance(10000);
		//create a new AccountPageUI to display the account page GUI and test its functions
		new AccountPageUI(cAccount, sAccount, customer);

	}

}
