//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[Tube.java]
//Description: Contains the Tube class. When clicking in the top 1/3 of the screen, the tube 
//				image will appear flipped upside-down.
//================================================================================================
import java.awt.Graphics;				//for graphics handling
import java.awt.image.BufferedImage;	//for image handling
import java.awt.Color; 					//for bounding box testing

class Tube extends Sprite
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	static final int upperTubeLimit = Game.screenHeight/3;	//Determines when tube sprite should be flipped
	static BufferedImage tube_image_bottom = null;		
	static BufferedImage tube_image_top = null;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	public Tube(int new_x, int new_y)
	{
		this.x = new_x;
		this.y = new_y;
		w = 55;
		h = 400;
		type = "tube";
		loadTubeImages();
	}
	
	public Tube(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		w = 55;
		h = 400;
		type = "tube";
		loadTubeImages();
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	Json marshal()
    {
        Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
        return ob;
    }
	
	void loadTubeImages()
	{
		if(tube_image_bottom == null)
			tube_image_bottom = View.loadImage("tubeBottom.png");
		if(tube_image_top == null)
			tube_image_top = View.loadImage("tubeTop.png");
	}
	
	void update()
	{	}
	
	void drawSelf(Graphics g)
	{
		if(this.y >= upperTubeLimit)
			g.drawImage(tube_image_bottom, View.scrollDiff, this.y, null);
		else
			g.drawImage(tube_image_top, View.scrollDiff, this.y, null);
	}
}
