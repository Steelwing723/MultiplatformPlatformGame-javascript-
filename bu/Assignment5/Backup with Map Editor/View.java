//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4	[View.java]
//Description: Contains the View class for graphical components of the program.
//================================================================================================
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.Color; //used for setting colors

class View extends JPanel	//Extends JPanel to create a base for other graphical components.
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	Model model;
	static int scrollPosition;
	static int groundLevel = Game.screenHeight - 100;
	static int mario_cycle;
	
	//The following two variables are used to limit what is drawn to only tube objects that can 
	//be seen. 
	//Currently it's set to stop drawing tubes as soon as they're fully off the screen. 
	//Needs to be updated as wider models are added or when screen size changes.
	int offScreenL = scrollPosition - Tube.width; 
	int offScreenR = Game.screenWidth + Tube.width + scrollPosition;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	View(Controller c, Model m)	//Requires the parameter "c" 
	{
		model = m;
		c.setView(this);	//calls the setter for view.
		this.scrollPosition = 0;
		
		//Mario
		Mario mario = model.mario;
		mario_cycle = 0;
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	static BufferedImage loadImage(String filename)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File(filename));
		}
		catch(Exception e)
		{
			System.out.println("Error, cannot read " + filename + "!\n");
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return img;
	}
	
	//Draw/////////////////////////////////////////////////////////////////////////////////////
	public void paintComponent(Graphics g)
	{
		int scrollDiff;
		Mario m = model.mario;
		//Background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Ground
		g.setColor(Color.gray);
		g.fillRect(0, groundLevel, this.getWidth(), 100);
		
		// //Mario Bounding Box Testing
		// g.setColor(Color.yellow);
		// g.drawLine(m.x, m.y, m.x + m.width, m.y);
		// g.drawLine(m.x, m.y, m.x, m.y + m.height);
		// g.drawLine(m.x, m.y + m.height, m.x + m.width, m.y + m.height);
		// g.drawLine(m.x + m.width, m.y, m.x + m.width, m.y + m.height);
		
		
		//Tubes
		for(int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			scrollDiff = t.x - scrollPosition;
			
			if(scrollDiff > offScreenL && scrollDiff < offScreenR)
			{
				if(t.y >= t.upperTubeLimit)
					g.drawImage(t.tube_image_bottom, scrollDiff, t.y, null);
				else
					g.drawImage(t.tube_image_top, scrollDiff, t.y, null);

				// //Tube Bounding Box Testing
				// g.drawLine(scrollDiff, t.y, scrollDiff + t.width, t.y);
				// g.drawLine(scrollDiff, t.y, scrollDiff, t.y + t.height);
				// g.drawLine(scrollDiff, t.y + t.height, scrollDiff + t.width, t.y + t.height);
				// g.drawLine(scrollDiff + t.width, t.y, scrollDiff + t.width, t.y + t.height);
			}
		}
		
		//Mario
		if(m.rightFacing)
			g.drawImage(m.mario_images_R[mario_cycle], m.x, m.y, null);
		else
			g.drawImage(m.mario_images_L[mario_cycle], m.x, m.y, null);
	}
}