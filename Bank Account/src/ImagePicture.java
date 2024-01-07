import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * @author Ashwin
 * Date: October 19, 2022
 * Description: Display images. Inherits from Picture
 * Method List: 
 * public ImagePicture(ImageIcon image) - Overloaded constructor to set desired image
 * public ImagePicture(ImageIcon image, int x, int y) - Overloaded Constructor to set desired image, x and y positions
 * public void setImage(ImageIcon image) - method to set the image
 * public void paint (Graphics g) - Override the paint from picture
 * public static void main(String[] args) - self testing main method
 *
 */
public class ImagePicture extends Picture{
	/**
	 * Instance variables
	 */
	private ImageIcon image;

	/**
	 * Overloaded constructor to set desired image 
	 */
	public ImagePicture(ImageIcon image) {
		//call the parent constructor
		super();
		this.image = image;
		//sets the width and height based on the image's width and height
		this.setMyWidth(image.getIconWidth());
		this.setMyHeight(image.getIconHeight());
	}

	/**
	 * Overloaded Constructor to set desired image, x and y positions 
	 */
	public ImagePicture(ImageIcon image, int x, int y) {
		//call the parent constructor
		super(x, y, image.getIconWidth(), image.getIconHeight());
		this.image = image;

	}

	/**
	 * method to set the image
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}


	/**
	 * Override the paint from picture
	 */
	public void paint (Graphics g) {
		this.image.paintIcon(this, g, getxPos(), getyPos());
	}

	/**
	 * @param args
	 * self testing main method
	 */
	public static void main(String[] args) {
		//testing
		
		//create a new JFrame
		JFrame f = new JFrame("Testing Only");
		//set the size of the frame and make the frame visible
		f.setSize(400, 350);
		f.setVisible(true);

		//wait
		JOptionPane.showMessageDialog(null,  "Wait");

		//create ImagePicture object
		ImagePicture p = new ImagePicture(new ImageIcon("bank logo.png"));
		//add the object
		f.add(p);
		//repaint the frame
		f.setVisible(true);

		//wait
		JOptionPane.showMessageDialog(null,  "Wait");

		//set the xPos and yPos
		p.setxPos(50);
		p.setyPos(80);

		//repaints the ImagePicture object
		p.repaint();

		//wait
		JOptionPane.showMessageDialog(null,  "Wait");

		//create a second ImagePicture object
		ImagePicture p1 = new ImagePicture(new ImageIcon("minion.png"), 150, 50);
		//add the second ImagePicture object to the frame
		f.add(p1);
		//repaint the frame
		f.setVisible(true);

		//wait
		JOptionPane.showMessageDialog(null,  "Wait");
		//change the picture of p1 object
		p1.setImage(new ImageIcon("bank logo .png"));
		//repaint p1
		p1.repaint();

		//wait
		JOptionPane.showMessageDialog(null,  "Wait");
		
		//change the picture of p1 object
		p1.setImage(new ImageIcon("bank logo.png"));
		//repaint p1
		p1.repaint();
		
		//make p1 object move across the frame (animation)
		for (int i = 0; i < f.getWidth()-95; i++) {
			//set the x-position of p1 to "i"
			p1.setxPos(i);
			//repaint p1
			p1.repaint();
			//put the program to sleep for 5 milliseconds
			try {
				Thread.sleep(5);
			}
			catch (Exception e) {

			}
		}//end of for loop
		
	}
}
