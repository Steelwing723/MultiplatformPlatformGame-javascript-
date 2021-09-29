//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[Mario.java]
//Description: Contains the Mario class. Mario images have 60px width and 95px height
//				Added a variable for "stepValue" to determine when a tube is low enough to be
//				treated like a step for making a staircase. Lack of collision for very low tubes
//				is intended.
//================================================================================================
import java.awt.Graphics;							//for graphics handling
import java.awt.image.BufferedImage;				//for image handling
import java.awt.Color; 								//for bounding box testing

class Mario extends Sprite
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	static final int speed = 20;					//mario's speed
	static final int jumpHeight = -70;				//height mario can jump
	static final int jumpLimit = 8;					//limit to jump time
	static int jumpTimer = 0;						//time in air during a jump
	static int xPos;								//x-value relative to game
	static int yShadow; 							//previous y position
	static int yFetch;
	static int stepValue = 15;						//y_limit before object stops mario (stairs)
	int mario_cycle = 0;							//position in sprite array
	static boolean rightFacing = true;				//direction detection
	static boolean onObject = false;				//object surface detection
	static BufferedImage[] mario_images_R;			//right facing image array
	static BufferedImage[] mario_images_L;			//left facing image array
	static boolean x_collision = false;				//determines horizontal collisions
	double collisionDiff;							//distance penetrated during collision
	Sound jumpSound;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	public Mario(int x, int y)
	{
		this.x = x;									//set x to called value
		this.y = y;									//set y to called value
		w = 60;
		h = 95;
		type = "mario";
		
		if(mario_images_R == null 					//if sprite arrays are empty
			&& mario_images_L == null)
			loadSprites();							//load sprites into array
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	void update()
	{
		//x position
		xPos = x + View.scrollPosition;			//update mario's x position in world
		
		//y position
		vert_vel += 9.8;						//constant vertical velocity
		y += vert_vel;							//updates y value
		
		if(y >= View.groundLevel - h)			//if mario is on the ground
		{
			vert_vel = 0.0;						//halt vertical velocity
			y = View.groundLevel - h; 			//snap mario to the ground
		}
			
		if(y < 0)								//if mario's head goes past the
			y = 0;								//top of the screen, snap to top
			
		if(jumpTimer <= jumpLimit)				//if jumpTimer is less than jumpLimit
			jumpTimer++;						//count up to jumpLimit
			
		yFetch = y;
	}
	
	void jump()
	{
		vert_vel = jumpHeight;					//upward shift in vert_vel
		onObject = false;						//mario is not on an object
		jumpSound();							//make jump sound
	}
	
	void stopJump()
	{
		if(vert_vel < 0) 						//if mario is still going up
			vert_vel = 0; 						//halt upward vertical velocity
	}
	
	void moveRight()
	{
		rightFacing = true;						//mario is facing right

		View.scrollPosition += speed;			//update scroll position
		
		if(mario_cycle < 4)						//if the cycle is below 4
			mario_cycle++;						//cycle forwards through sprites
		else									//otherwise
			mario_cycle = 0;					//start the cycle over
	}
	
	void moveLeft()
	{
		rightFacing = false;					//mario is facing left
		
		View.scrollPosition -= speed;			//update scroll position
		
		if(mario_cycle > 0)						//if the cycle is above 0
			mario_cycle--;						//cycle backwards through sprites
		else									//otherwise
			mario_cycle = 4;					//start the cycle over	
	}
	
	boolean onLand()
	{
		if((y + h) >= (View.groundLevel))		//if mario is on ground level
		{
			onObject = false;					//mario is not on an object
			jumpTimer = 0;						//reset jump time
			return true;						//mario is on land
		}
		else if(onObject)						//else if mario is on an object
		{
			jumpTimer = 0;						//reset jump time
			return true;						//mario is on land
		}
		return false;							//else mario is not on land 
	}
	
	void tubeCollision(Sprite stationary)
	{
		if((xPos <= stationary.x + stationary.w						//if mario is above or below the tube
			|| (xPos + w) >= stationary.x))		
		{															//if mario's feet were just at or above the 
																	//top of the tube, or if the tube is short
																	//enough to walk up like stairs:													
			if((yShadow + h) <= (stationary.y + stepValue)
				|| y + h <= (stationary.y + stepValue))
			{
				onObject = true;	
				vert_vel = 0;										//halt (downward) velocity
				y = stationary.y - h;								//push mario up to top of tube
				yShadow = y;
			}
																	//if mario's head was just at or below the
																	//bottom of the tube:
			if((yShadow) >= (stationary.y + stationary.h))
			{
				vert_vel = 0;										//halt (upward) velocity
				y = stationary.y + stationary.h;					//push mario down to bottom of tube
				Model.fizzle();										//play sound
			}
		}
		if((y + h) > stationary.y									//if mario is right or left of the tube
			&& (y < stationary.y + stationary.h)) 			
		{
			if(rightFacing)											//if mario is facing right
			{														//find how far mario entered the tube:
				collisionDiff = Math.abs((xPos + w) - stationary.x);  
				View.scrollPosition -= collisionDiff + 1;			//snap mario back to left side of tube
				x_collision = true;									//an x collision has happened					
			}
			
			if(!rightFacing)										//if mario is facing left
			{														//find how far mario entered the tube:
				collisionDiff = Math.abs((xPos) - (stationary.x + w));
				View.scrollPosition += collisionDiff - 4;			//snap mario back to right side of tube
				x_collision = true;									//an x collision has happened
			}
		}
	}
	
	//Graphics
	void loadSprites()
	{
		mario_images_R = new BufferedImage[5];	    				//sets right facing images
		mario_images_R[0] = View.loadImage("marioR1.png");
		mario_images_R[1] = View.loadImage("marioR2.png");
		mario_images_R[2] = View.loadImage("marioR3.png");
		mario_images_R[3] = View.loadImage("marioR4.png");
		mario_images_R[4] = View.loadImage("marioR5.png");
		
		mario_images_L = new BufferedImage[5];						//sets left facing images
		mario_images_L[0] = View.loadImage("marioL1.png");
		mario_images_L[1] = View.loadImage("marioL2.png");
		mario_images_L[2] = View.loadImage("marioL3.png");
		mario_images_L[3] = View.loadImage("marioL4.png");
		mario_images_L[4] = View.loadImage("marioL5.png");
	}
	
	void drawSelf(Graphics g)
	{
		if(rightFacing)												
			g.drawImage(mario_images_R[mario_cycle], x, y, null);
		else
			g.drawImage(mario_images_L[mario_cycle], x, y, null);
	}
	
	//Audio
	void jumpSound()
	{
		try 
		{
			jumpSound = new Sound("smw_jump.wav", 1);
			jumpSound.play(0.5, 0);
		}
		catch (Exception e)
		{
			System.out.println("Error, cannot read sound file!");
			e.printStackTrace(System.err);
		}
	}
}