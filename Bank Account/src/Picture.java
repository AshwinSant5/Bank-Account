/**
 * 
 */
import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 * @author Ashwin
 * Date: October 18, 2022
 * Description: Creates a component called Picture 
 * Method List: 
 * public Picture() - Default Constructor
 * public Picture (int x, int y, int width, int height) - Overloaded Constructor
 * public Color getColor() - method to get the color
 * public int getxPos() - method to get the xPos
 * public int getyPos() - method to get the yPos
 * public int getMyWidth() - method to get the width
 * public int getMyHeight() - method to get the height
 * public void setColor(Color color) - method to set the desired color
 * public void setxPos(int xPos) - method to set the xPos
 * public void setyPos(int yPos) - method to set the yPos
 * public void setMyWidth(int myWidth) - method to set the width
 * public void setMyHeight(int myHeight) - method to set the height
 * public void paint (Graphics g) - this Picture's paint method
 * public static void main(String[] args) - for testing purposes
 *
 */
public class Picture extends JComponent {

	/* 
	 * Private Data
	 */
	private Color color;
	private int xPos, yPos, myWidth, myHeight;

	/*
	 * Default Constructor
	 */
	public Picture() {
		//set the colour
		this.color = Color.RED;
		//set the x-position
		this.xPos = 0;
		//set the y-position
		this.yPos = 0;
		//set the width
		this.myWidth = 50;
		//set the height
		this.myHeight = 25;
	}

	/**
	 * Overloaded Constructor
	 */
	public Picture (int x, int y, int width, int height) {
		//set the x-position
		this.xPos = x;
		//set the y-position
		this.yPos = y;
		//set the width
		this.myWidth = width;
		//set the height
		this.myHeight = height;
		//set the colour
		this.color = Color.RED;

	}

	/**
	 * method to get the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * method to get the xPos
	 */
	public int getxPos() {
		return xPos;
	}

	/**
	 * method to get the yPos
	 */
	public int getyPos() {
		return yPos;
	}

	/**
	 * method to get the width
	 */
	public int getMyWidth() {
		return myWidth;
	}

	/**
	 * method to get the height
	 */
	public int getMyHeight() {
		return myHeight;
	}

	/**
	 * method to set the desired color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * method to set the xPos
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * method to set the yPos
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	/**
	 * method to set the width
	 */
	public void setMyWidth(int myWidth) {
		this.myWidth = myWidth;
	}

	/**
	 * method to set the height
	 */
	public void setMyHeight(int myHeight) {
		this.myHeight = myHeight;
	}

	//this Picture's paint method
	public void paint (Graphics g) {
		//set the color to paint 
		g.setColor(this.color);
		//draw a rectangle
		g.drawRect(this.xPos, this.yPos, this.myWidth, this.myHeight);
		//draw an oval with the same dimensions as the rectangle
		g.drawOval(this.xPos,  this.yPos,  this.myWidth,  this.myHeight);
		//draw an oval with proportioned dimensions to the oval drawn with the dimensions of the rectangle
		g.fillOval(this.xPos+this.myWidth/3,  this.yPos,  this.myWidth/3,  this.myHeight);

	}

	/**
	 * @param args
	 * for testing purposes
	 */
	public static void main(String[] args) {
		//create a JFrame 
		JFrame f = new JFrame ("Just for Testing.");
		f.setSize(400,350);

		Picture p = new Picture(); //create a Picture object

		f.add(p);//add the picture to the frame

		f.setVisible(true); //paint the frame and all objects within it

		//JOptionPane to delay
		JOptionPane.showMessageDialog(null, "Wait");

		//set the picture's colour to blue
		p.setColor(Color.BLUE);
		//set the picture's x-position to 10
		p.setxPos(100);
		f.repaint(); //repaints the frame

		//create a second Picture object
		Picture p1 = new Picture();

		//set the second picture's x-position to 200
		p1.setxPos(200);
		//set the second picture's y-position to 100
		p1.setyPos(100);
		//set the second picture's width to 150
		p1.setMyWidth(150);

		//add the second picture to the frame
		f.add(p1);
		//repaint the frame by making it visible again
		f.setVisible(true);

		//JOptionPane to delay
		JOptionPane.showMessageDialog(null, "Wait");

		//testing new constructor
		//create a third Picture object
		Picture p2 = new Picture (300, 200, 50, 100);
		//add the third picture to the frame
		f.add(p2);
		//repaint the frame by making it visible again
		f.setVisible(true);
	}

}
