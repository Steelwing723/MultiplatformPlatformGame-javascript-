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
	static boolean x_collision = false;			//determines horizontal collisions
	static boolean turnAround = false;			//variable controls when to turn around
	static boolean isDead;						//if goomba "isDead" it will be deleted next update
	static boolean onFire;						//if goomba is "onFire" it will stop moving and change sprites
	static int framesOnFire = 40;				//determines how long goomba stays on fire
	
	public Goomba(int x, int y)
	{
		this.x = x;
		this.y = y;
		w = 40;
		h = 47;
		this.type = "goomba";
		this.direction = randomDirection();
		this.onFire = false;
		this.isDead = false;
		if(image == null)
			image = View.loadImage("goomba.png");
	}
	
	public Goomba(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		w = 40;
		h = 47;
		type = "goomba";
		this.direction = randomDirection();
		this.onFire = false;
		this.isDead = false;
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
		vert_vel += 9.8;						//constant vertical velocity
		y += vert_vel;							//updates y value
		
		if(y >= View.groundLevel - h)			//if goomba is on the ground
		{
			vert_vel = 0.0;						//halt vertical velocity
			y = View.groundLevel - h; 			//snap to the ground
		}
		
		roam();									//walk randomly right or left
		
		if(framesOnFire < 40)					//controls how long goomba stays on fire
			framesOnFire++;
	}
	
	void roam()									//goomba should randomly walk right or left
	{
		if(!onFire)								//stops goomba if it's on fire
		{
			if(direction)
				this.x -= 5;
			else
				this.x += 5;
		}
	}
	
	void burn()									//sets goomba to be on fire
	{
		this.image = View.loadImage("goomba_on_fire.png");
		framesOnFire = 0;						//start frames on fire at 0
		this.onFire = true;
	}
	
	void die()
	{
		this.isDead = true;
	}
	
	boolean randomDirection()
	{
		return Math.random() < 0.5;
	}
	
	void tubeCollision(Sprite stationary)		//when colliding with a tube
	{
		if((y + h) > stationary.y				//if goomba is right or left of tube
			&& (y < stationary.y + stationary.h)) 			
		{
			this.direction = !this.direction;	//change walking direction
		}
	}
	
	void drawSelf(Graphics g)				
	{
		g.drawImage(image, View.scrollDiff, this.y, null);
	}
	
	
}