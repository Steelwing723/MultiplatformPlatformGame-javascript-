//Name: Joshua Davis
//UAID: 010946462
//Date: 10/22/2020
//Assignment 5	[Fireball.java]
//Description: Contains the Fireball class. 47px x 47px
//================================================================================================
import java.awt.Graphics;
import java.awt.image.BufferedImage;	//for image handling
import java.awt.Color; //used for setting colors

public class Fireball extends Sprite
{
	static BufferedImage image;					//holds fireball sprite
	static double horiz_vel;					//horizontal velocity
	static boolean extinguished;				//determines if fireball should be deleted on update
	static int burnTime;						//how long the fireball exists
	static Sound fire;							//sound effect for fireball
	
	public Fireball(int x, int y, boolean right)
	{
		this.x = x;
		this.y = y;
		w = 47;
		h = 47;
		this.type = "fireball";
		this.extinguished = false;
		this.burnTime = 50;
		
		if(right)								//if mario is facing right, fireball should go right
			this.horiz_vel = 50;
		else									//otherwise it should go left
			this.horiz_vel = -50;
		
		if(image == null)
			image = View.loadImage("fireball.png");
	}
	
	void update()
	{
		vert_vel += 9.8;						//constant vertical velocity
		y += vert_vel;							//updates y value
		
		if(y >= View.groundLevel - h)			//if fireBall is on the ground
		{
			y = View.groundLevel - h; 			//snap to the ground and
			vert_vel = -50.0;					//bounce up
		}
		
		x += horiz_vel;							//increase x value based on horizontal velocity
	}
	
	void drawSelf(Graphics g)
	{
		g.drawImage(image, View.scrollDiff, this.y, null);
	}
	
	//Audio
	static void fire()
	{
		try 
		{
			fire = new Sound("smw_fireball.wav", 1);
			fire.play(0.5, 0);
		}
		catch (Exception e)
		{
			System.out.println("Error, cannot read sound file for Fireball!");
			e.printStackTrace(System.err);
		}
	}
}