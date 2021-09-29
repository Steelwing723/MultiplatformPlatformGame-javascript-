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
	int xPos;
	int stepValue;
	double vert_vel;
	int w, h;
	String type;
	boolean collidable;
	boolean direction;
	
	BufferedImage image;
	
	abstract void update();
	abstract void drawSelf(Graphics g);
	
	// Json marshal()
	// {
        // Json ob = Json.newObject();
        // Json tmpList = Json.newList();
        // ob.add("tubes", tmpList);

        // return ob;
    // }
	
	Json marshal()
    {
        Json ob = Json.newObject();
		ob.add("type", this.type);
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("width", this.w);
		ob.add("height", this.h);
        return ob;
    }
	
	// boolean spriteCollision(Sprite me, Sprite them)
	// {
		// int myRight;
		// int myLeft;
		// if(me instanceof Mario)
		// {
			// myRight = Mario.xPos + me.w; 
			// myLeft = Mario.xPos;
		// }
		// else
		// {
			// myRight = me.x + me.w; 
			// myLeft = me.x;
		// }
		// int myBottom = me.y + me.h;
		// int myTop = me.y;
		// int theirRight = them.x + them.w;
		// int theirLeft = them.x;
		// int theirBottom = them.y + them.h;
		// int theirTop = them.y;
		
		// //System.out.println("Bottom is at: " + myBottom);
		
		// if(myRight < theirLeft)		//I am left of the entity
		// {
			// Model.x_collision = false;
			// return false;
		// }
		// if(myLeft > theirRight) 	//I am right of the entity
		// {
			// Model.x_collision = false;
			// return false;

		// }
		// if(myBottom <= theirTop) //I am above the entity
			// return false;
		// if(myTop > theirBottom) //I am below the entity
			// return false;
			
		// return true;
	// }
	
	boolean spriteCollision(Sprite s)
	{
		int myRight;
		int myLeft;
		if(this instanceof Mario)
		{
			myRight = Mario.xPos + this.w; 
			myLeft = Mario.xPos;
		}
		else
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
		
		//System.out.println("Bottom is at: " + myBottom);
		
		if(myRight < theirLeft)		//I am left of the entity
		{
			Mario.x_collision = false;
			Goomba.x_collision = false;
			//if(this instanceof Goomba)
				//System.out.println("Goomba is left!");
			//else
				//System.out.println("Mario is left!");
			return false;
		}
		if(myLeft > theirRight) 	//I am right of the entity
		{
			Mario.x_collision = false;
			Goomba.x_collision = false;
			//if(this instanceof Goomba)
				//System.out.println("Goomba is right!");
			//else
				//System.out.println("Mario is right!");
			return false;

		}
		if(myBottom <= theirTop) //I am above the entity
			return false;
		if(myTop > theirBottom) //I am below the entity
			return false;
			
		return true;
	}
	
	//MAP EDITOR FUNCTION
	boolean spriteClicked(int mouse_x, int mouse_y) 
	{
		// System.out.println("this.x: " + this.x);
		// System.out.println("this.y: " + this.y);
		// System.out.println("mouse_x: " + mouse_x);
		// System.out.println("mouse_y: " + mouse_y);
		
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