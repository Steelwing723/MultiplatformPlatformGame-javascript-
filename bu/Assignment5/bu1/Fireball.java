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
	//Model model;
	
	public Fireball(int x, int y)
	{
		this.x = x;
		this.y = y;
		//this.model = m;
		this.type = "fireball";
		if(image == null)
			image = View.loadImage("fireball.png");
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
	
	// public Fireball(Json ob)
	// {
		// this.x = (int)ob.getLong("x");
		// this.y = (int)ob.getLong("y");
		// // w = 55;
		// // h = 400;
		// type = "fireball";
		// if(image == null)
			// image = View.loadImage("fireball.png");
	// }
	
	// Json marshal()
    // {
        // Json ob = Json.newObject();
		// ob.add("x", x);
		// ob.add("y", y);
        // return ob;
    // }
	
	void throwFireball()
	{
		
	}
	
	void update()
	{
		
	}
	
	void drawSelf(Graphics g)
	{
		g.drawImage(image, View.scrollDiff, this.y, null);
	}
}