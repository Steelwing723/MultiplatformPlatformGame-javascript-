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
	static BufferedImage image;
	static double horiz_vel;		//horizontal velocity
	static int yFetch;
	static int yShadow;
	static boolean extinguished;
	static int burnTime;
	static Sound fire;
	
	
	public Fireball(int x, int y, boolean right)
	{
		this.x = x;
		this.y = y;
		w = 47;
		h = 47;
		this.type = "fireball";
		this.extinguished = false;
		this.burnTime = 50;
		
		if(right)
			this.horiz_vel = 50;
		else
			this.horiz_vel = -50;
		
		if(image == null)
			image = View.loadImage("fireball.png");
	}
	
	// void throwFireball()
	// {
		// if(Mario.rightFacing)
			// this.x += horiz_vel;
		// else
			// this.x -= horiz_vel;
	// }
	
	void update()
	{
		vert_vel += 9.8;						//constant vertical velocity
		y += vert_vel;							//updates y value
		
		if(y >= View.groundLevel - h)			//if fireBall is on the ground
		{
			y = View.groundLevel - h; 			//snap to the ground
			vert_vel = -50.0;						//bounce up
		}
		yFetch = y;
		
		x += horiz_vel;
	}
	
	void drawSelf(Graphics g)
	{
		g.drawImage(image, View.scrollDiff, this.y, null);
	}
	
	
	void tubeCollision(Sprite stationary)
	{
		if((this.x <= stationary.x + stationary.w
			|| (this.x + w) >= stationary.x))		
		{												
			if((this.yShadow + h) <= (stationary.y + stepValue)
				|| this.y + h <= (stationary.y + stepValue))
			{	
				vert_vel = 0;								
				this.y = stationary.y - this.h;
				this.yShadow = this.y;
			}
			if((this.yShadow) >= (stationary.y + stationary.h))
			{
				this.vert_vel = 0;			
				this.y = stationary.y + stationary.h;
			}
		}
		if((y + h) > stationary.y		
			&& (y < stationary.y + stationary.h)) 			
		{
			this.direction = !this.direction;
		}
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