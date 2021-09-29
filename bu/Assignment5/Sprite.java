//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[Sprite.java]
//Description: Contains the Sprite class
//================================================================================================
import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;	//for image handling

abstract class Sprite
{
	int x, y;
	int w, h;
	double vert_vel;				//gravity on sprite
	boolean direction;				//used to determine direction of sprite
	String type;					//holds type of sprite
	
	BufferedImage image;
	
	abstract void update();
	abstract void drawSelf(Graphics g);
	
	Json marshal()
    {
        Json ob = Json.newObject();
		ob.add("type", this.type);
		ob.add("x", this.x);
		ob.add("y", this.y);
        return ob;
    }
	
	boolean spriteCollision(Sprite s)
	{
		int myRight;
		int myLeft;
		if(this instanceof Mario)	//checks for mario specifically since
		{							//game relies the xPos and scroll variables
			myRight = Mario.xPos + this.w; 
			myLeft = Mario.xPos;
		}
		else						//otherwise, x values are the usual.
		{
			myRight = this.x + this.w; 
			myLeft = this.x;
		}
		int myBottom = this.y + this.h;
		int myTop = this.y;
		int theirRight = s.x + s.w;
		int theirLeft = s.x;
		int theirBottom = s.y + s.h;
		int theirTop = s.y;
		
		if(myRight < theirLeft)		//I am left of the entity
		{
			Mario.x_collision = false;
			Goomba.x_collision = false;
			return false;
		}
		if(myLeft > theirRight) 	//I am right of the entity
		{
			Mario.x_collision = false;
			Goomba.x_collision = false;
			return false;
		}
		if(myBottom <= theirTop) 	//I am above the entity
			return false;
		if(myTop > theirBottom) 	//I am below the entity
			return false;
			
		return true;
	}
	
	//MAP EDITOR FUNCTION
	boolean spriteClicked(int mouse_x, int mouse_y) 
	{
		if(this instanceof Mario)
			return false;
		
		if(mouse_x < this.x) 			//clicked left of tube
			return false;
		if(mouse_x > this.x + this.w) 	//clicked right of tube
			return false;
		if(mouse_y < this.y) 			//clicked above tube
			return false;
		if(mouse_y > this.y + this.h) 	//clicked below tube
			return false;
		return true; 				    //clicked on tube
	}
}