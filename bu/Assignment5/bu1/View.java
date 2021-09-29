//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[View.java]
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
	static int scrollDiff;
	int renderDistance = 100;	//distance that sprites begin loading off screen
	
	//The following two variables are used to limit what is drawn to only tube objects that can 
	//be seen. 
	//Currently it's set to stop drawing tubes as soon as they're fully off the screen. 
	//Needs to be updated as wider models are added or when screen size changes.
	int offScreenL = scrollPosition - renderDistance;
	int offScreenR = Game.screenWidth + renderDistance + scrollPosition;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	View(Controller c, Model m)	//Requires the parameter "c" 
	{
		model = m;
		c.setView(this);	//calls the setter for view.
		this.scrollPosition = 0;
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
		//Background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Ground
		g.setColor(Color.gray);
		g.fillRect(0, groundLevel, this.getWidth(), 100);
		
		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			scrollDiff = s.x - scrollPosition;
			
			if(model.sprites.get(i) instanceof Mario)					//if it's mario
				s.drawSelf(g);											//always draw him
			else if(scrollDiff > offScreenL && scrollDiff < offScreenR)	//else if it's on screen
				s.drawSelf(g);											//draw it
		}
	}
}