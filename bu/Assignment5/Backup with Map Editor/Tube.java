//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4	[Tube.java]
//Description: Contains the Tube class. When clicking in the top 1/3 of the screen, the tube 
//				image will appear flipped upside-down.
//================================================================================================
import java.awt.image.BufferedImage;	//for image handling
class Tube
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	int x = 0;
	int y = 0;
	static final int width = 55;
	static final int height = 400;
	static final int upperTubeLimit = Game.screenHeight/3;
	static BufferedImage tube_image_bottom = null;
	static BufferedImage tube_image_top = null;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	Tube(int new_x, int new_y)
	{
		x = new_x;
		y = new_y;
		if(tube_image_bottom == null)
			tube_image_bottom = View.loadImage("tubeBottom.png");
		if(tube_image_top == null)
			tube_image_top = View.loadImage("tubeTop.png");
	}
	
	Tube(Json ob)
	{
		x = (int)ob.getLong("tube_x");
		y = (int)ob.getLong("tube_y");
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	Json marshal()
    {
        Json ob = Json.newObject();
		ob.add("tube_x", x);
		ob.add("tube_y", y);
        return ob;
    }
	
	boolean tubeClicked(int mouse_x, int mouse_y) 
	{
		if(mouse_x < x) 			//clicked left of tube
			return false;
		if(mouse_x > x + width) 	//clicked right of tube
			return false;
		if(mouse_y < y) 			//clicked above tube
			return false;
		if(mouse_y > y + height) 	//clicked below tube
			return false;
		return true; 				//clicked on tube
	}
}
