//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[Goomba.java]
//Description: Contains the Goomba class. 40px x 47px
//================================================================================================
import java.awt.Graphics;
import java.awt.image.BufferedImage;	//for image handling
import java.awt.Color; //used for setting colors

public class Goomba extends Sprite
{
	static BufferedImage image;
	static boolean direction; 
	static boolean x_collision = false;			//determines horizontal collisions
	static int yFetch;
	static int yShadow;
	double collisionDiff;	
	
	public Goomba(int x, int y)
	{
		this.x = x;
		this.y = y;
		//this.model = m;
		w = 40;
		h = 47;
		this.type = "goomba";
		direction = randomDirection();
		if(image == null)
			image = View.loadImage("goomba.png");
	}
	
	// public Goomba(int x, int y)
	// {
		// this.x = x;
		// this.y = y;
		// //this.model = m;
		// this.type = "goomba";
		// if(image == null)
			// image = View.loadImage("goomba.png");
	// }
	
	public Goomba(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		w = 40;
		h = 47;
		type = "goomba";
		direction = randomDirection();
		if(image == null)
			image = View.loadImage("goomba.png");
	}
	
	Json marshal()
    {
        Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
        return ob;
    }
	
	void update()
	{
		vert_vel += 9.8;							//constant vertical velocity
		y += vert_vel;								//updates y value
		
		if(y >= View.groundLevel - h)			//if goomba is on the ground
		{
			vert_vel = 0.0;							//halt vertical velocity
			y = View.groundLevel - h; 			//snap to the ground
		}
		yFetch = y;
		
		roam();
	}
	
	void roam()
	{
		if(direction)
			this.x -= 5;
		else
			this.x += 5;
	}
	
	boolean randomDirection()
	{
		return Math.random() < 0.5;
	}
	
	void tubeCollision(Sprite stationary)
	{
		System.out.println("Checking Goomba Collision!");
		if((this.x <= stationary.x + stationary.w
			|| (this.x + w) >= stationary.x))		
		{												
			
			if((this.yShadow + h) <= (stationary.y + stepValue)
				|| this.y + h <= (stationary.y + stepValue))
			{
				onObject = true;	
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
			direction = !direction;
		}
	}
	
	void drawSelf(Graphics g)
	{
		g.drawImage(image, View.scrollDiff, this.y, null);
	}
}