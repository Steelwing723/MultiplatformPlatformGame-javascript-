//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4	[Mario.java]
//Description: Contains the Mario class. Mario images have 60px width and 95px height
//				Added a variable for "stepValue" to determine when a tube is low enough to be
//				treated like a step for making a staircase. Lack of collision for very low tubes
//				is intended.
//================================================================================================
import java.awt.image.BufferedImage;				//for image handling

class Mario
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	static final int width = 60;					//sprite width
	static final int height = 95;					//sprite height
	static final int speed = 20;					//mario's speed
	static final int jumpHeight = -70;				//height mario can jump
	static final int jumpLimit = 8;					//limit to jump time
	static int jumpTimer = 0;						//time in air during a jump
	static int x;									//x-value relative to view
	static int y;									//y-value relative to view
	static int xPos;								//x-value relative to game
	static int yShadow; 							//previous y position
	static int stepValue = 15;						//y_limit before object stops mario (stairs)
	static double vert_vel;							//vertical velocity
	static boolean rightFacing = true;				//direction detection
	static boolean onObject = false;				//object surface detection
	static BufferedImage[] mario_images_R;			//right facing image array
	static BufferedImage[] mario_images_L;			//left facing image array
	float volume = 1;
	Sound jumpSound;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	public Mario(int x, int y)
	{
		this.x = x;									//set x to called value
		this.y = y;									//set y to called value
		if(mario_images_R == null 					//if sprite arrays are empty
			&& mario_images_L == null)
			drawSelf();								//load sprites into array
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	void update()
	{
		//x position
		xPos = x + View.scrollPosition;				//update mario's x position in world
		
		//y position
		vert_vel += 9.8;							//constant vertical velocity
		y += vert_vel;								//updates y value
		
		if(y >= View.groundLevel - height)			//if mario is on the ground
		{
			vert_vel = 0.0;							//halt vertical velocity
			y = View.groundLevel - height; 			//snap mario to the ground
		}
			
		if(y < 0)									//if mario's head goes past the
			y = 0;									//top of the screen, snap to top
			
		if(jumpTimer <= jumpLimit)					//if jumpTimer is less than jumpLimit
			jumpTimer++;							//count up to jumpLimit
	}
	
	void jump()
	{
		vert_vel = jumpHeight;						//upward shift in vert_vel
		onObject = false;							//mario is not on an object
		jumpSound();								//make jump sound
	}
	
	void stopJump()
	{
		if(vert_vel < 0) 							//if mario is still going up
			vert_vel = 0; 							//halt upward vertical velocity
	}
	
	void moveRight()
	{
		rightFacing = true;							//mario is facing right

		View.scrollPosition += speed;				//update scroll position
		
		if(View.mario_cycle < 4)					//if the cycle is below 4
			View.mario_cycle++;						//cycle forwards through sprites
		else										//otherwise
			View.mario_cycle = 0;					//start the cycle over
	}
	
	void moveLeft()
	{
		rightFacing = false;						//mario is facing left
		
		View.scrollPosition -= speed;				//update scroll position
		
		if(View.mario_cycle > 0)					//if the cycle is above 0
			View.mario_cycle--;						//cycle backwards through sprites
		else										//otherwise
			View.mario_cycle = 4;					//start the cycle over	
	}
	
	boolean onLand()
	{
		if((y + height) >= (View.groundLevel))		//if mario is on ground level
		{
			onObject = false;						//mario is not on an object
			jumpTimer = 0;							//reset jump time
			return true;							//mario is on land
		}
		else if(onObject)							//else if mario is on an object
		{
			jumpTimer = 0;							//reset jump time
			return true;							//mario is on land
		}
		return false;								//else mario is not on land 
	}
	
	void drawSelf()
	{
		mario_images_R = new BufferedImage[5];	    //sets right facing images
		mario_images_R[0] = View.loadImage("marioR1.png");
		mario_images_R[1] = View.loadImage("marioR2.png");
		mario_images_R[2] = View.loadImage("marioR3.png");
		mario_images_R[3] = View.loadImage("marioR4.png");
		mario_images_R[4] = View.loadImage("marioR5.png");
		
		mario_images_L = new BufferedImage[5];		//sets left facing images
		mario_images_L[0] = View.loadImage("marioL1.png");
		mario_images_L[1] = View.loadImage("marioL2.png");
		mario_images_L[2] = View.loadImage("marioL3.png");
		mario_images_L[3] = View.loadImage("marioL4.png");
		mario_images_L[4] = View.loadImage("marioL5.png");
	}
	
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