//Name: Joshua Davis
//UAID: 010946462
//Date: 10/15/2020
//Assignment 5	[Model.java]
//Description: Contains the Model class for representing the state of the game
//			   and the TubeComparator class for organizing Tube objects.
//================================================================================================
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

// //MAP EDITOR CLASS for TubeComparator
// class TubeComparator implements Comparator<Sprite>
// {
	// public int compare(Sprite a, Sprite b)
	// {
		// if(a.x < b.x)
			// return -1;
		// else if(a.x > b.x)
			// return 1;
		// else
			// return 0;
	// }

	// public boolean equals(Object obj)
	// {
		// return false;
	// }
// }

class Model
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	ArrayList<Sprite> sprites;					
	Mario mario; //redundant reference to mario, remove if you can	
	Goomba goomba = null;	
	//View view;									
	static boolean collision = false;			//determines collisions
	//static boolean x_collision = false;			//determines horizontal collisions
	static boolean g_collision = false;
	int updateCounter = 0;						//(uncomment when debugging)
	double collisionDiff;						//distance penetrated during collision
	Sprite test;

	//Constructors////////////////////////////////////////////////////////////////////////////////
	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario((Game.screenWidth/2 - 60), 0);	
		sprites.add(mario);
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	public void update()
	{
		//Uncomment for debugging!
		updateCounter++;													
		System.out.println("===============[Frame #" + updateCounter + "]");
		//if(mario.onObject)
			//System.out.println("Mario is on an object!");
		//System.out.println("xPos: " + mario.xPos);
		//System.out.println("yShadow: " + mario.yShadow);
		//System.out.println("y: " + mario.yFetch);
		//System.out.println("y + h before: [" + (mario.y + mario.h) + "]");
		//System.out.println("yShadow + h before: [" + (mario.yShadow + mario.h) + "]");
		
		// for(int i = 0; i < sprites.size(); i++)								//example code
		// {
			// sprites.get(i).update();
			// System.out.println();
			// if(sprites.get(i).type.equals("tube"))
			// {
				// Tube t = (Tube)sprites.get(i);
				
				// if(collision(t))
				// {
				// }
			// }
		// }
		
		
		// for(int j = sprites.size() - 1; j >= 0; j--)							//test array size
			// {
				// System.out.println("Current Array is: [" + j + "] full");
				// System.out.println("Test at x: " + test.x);
			// }
			
		
		if(!collision)															//if no collision is found
			mario.onObject = false;												//mario can't be on an object

		
		//Collision Handling//////////////////////////////////////////////////////////////////////////////////////////
		for(int i = 0; i < sprites.size(); i++)									//loop through sprite array
		{
			//System.out.println("yShadow in Model: " + Controller.yShadow);
			sprites.get(i).update();											//update each sprite in the array
			
			if((sprites.get(i).type.equals("goomba")))
			{
				System.out.println("It's a goomba");
				goomba = (Goomba)sprites.get(i);
			}
			
			if((sprites.get(i).type.equals("tube")))
			{
				collision = mario.spriteCollision(sprites.get(i));				//check if mario collides with it
				if(collision)
					mario.tubeCollision(sprites.get(i));
				
				if(goomba != null)
				{
					System.out.println("goomba isn't null!");
					g_collision = goomba.spriteCollision(sprites.get(i));
					System.out.println("g_collision: " + g_collision);
					if(g_collision)
						goomba.tubeCollision(sprites.get(i));
				}
			}
			
			// if(collision)														//if a collision is found
			// {
				// //System.out.println("Collision detected!");
				
				// if(sprites.get(i) instanceof Tube)								//if the sprite is a Tube
				// {
					// mario.tubeCollision(sprites.get(i));
					// //onCollision(sprites.get(i));
				// }
			// }
		}
	}
	
	// void onCollision(Sprite stationary) //Stationary Object Collision Control
	// {
		// //System.out.println("mario xPos: [" + mario.xPos + "]");
		// //System.out.println("stationary x: [" + stationary.x + "]");
		// if((mario.xPos <= stationary.x + stationary.w		//if mario is above or below the tube
			// || (mario.xPos + mario.w) >= stationary.x))		
		// {															//if mario's feet were just at or above the 
																	// //top of the tube, or if the tube is short
																	// //enough to walk up like stairs:													
			// //System.out.println("Mario's height: [" + mario.h + "]");	
			
			
			// // System.out.println("y after: [" + (mario.y + mario.h) + "]");
			// // System.out.println("yShadow after: [" + (mario.yShadow + mario.h) + "]");
			// // System.out.println("Tube y: [" + stationary.y + "]");
			
			// //System.out.println("yShadow + h: [" + (mario.yShadow + mario.h) + "]");
			// //System.out.println("Pipe y + stepValue: [" + (stationary.y + mario.stepValue) + "]");
			// //mario.overObject = true;
			
			// if((mario.yShadow + mario.h) <= (stationary.y + mario.stepValue)
				// || mario.y + mario.h <= (stationary.y + mario.stepValue))
			// {
				// mario.onObject = true;	
				// mario.vert_vel = 0;									//halt (downward) velocity
				// mario.y = stationary.y - mario.h;			//push mario up to top of tube
				// mario.yShadow = mario.y;
				// // if(mario.yShadow + mario.h == stationary.y)	//if mario's last frame was on an object
				// // {
					// //System.out.println("On Tube!");
										// //mario is on an object
					// //System.out.println("Tube's Top: " + stationary.y);
				// //}
			// }
			
																	// //if mario's head was just at or below the
																	// //bottom of the tube:
			// if((mario.yShadow) >= (stationary.y + stationary.h))
			// {
				// mario.vert_vel = 0;									//halt (upward) velocity
				// mario.y = stationary.y + stationary.h;				//push mario down to bottom of tube
			// }
		// }
		// if((mario.y + mario.h) > stationary.y				//if mario is right or left of the tube
			// && (mario.y < stationary.y + stationary.h)) 			
		// {
			// if(mario.rightFacing)									//if mario is facing right
			// {														//find how far mario entered the tube:
				// collisionDiff = Math.abs((mario.xPos + mario.w) - stationary.x);  
				// View.scrollPosition -= collisionDiff + 1;			//snap mario back to left side of tube
				// x_collision = true;									//an x collision has happened					
			// }
			
			// if(!mario.rightFacing)									//if mario is facing left
			// {														//find how far mario entered the tube:
				// collisionDiff = Math.abs((mario.xPos) - (stationary.x + mario.w));
				// View.scrollPosition += collisionDiff - 4;			//snap mario back to right side of tube
				// x_collision = true;									//an x collision has happened
			// }
		// }
	// }

	// Json marshal()
	// {
        // Json ob = Json.newObject();
        // Json tmpList = Json.newList();
        // ob.add("sprites", tmpList);
        // for(int i = 0; i < sprites.size(); i++)
            // tmpList.add(sprites.get(i).marshal());
        // return ob;
    // }
	
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spritesOb = Json.newObject();
		Json tempList = Json.newList();
		ob.add("sprites", spritesOb);
		spritesOb.add("tubes", tempList);
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type.equals("tube"))
			{
				Tube t = (Tube)sprites.get(i);
				tempList.add(t.marshal());
			}
		}
		tempList = Json.newList();
		spritesOb.add("goombas", tempList);
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).type.equals("goomba"))
			{
				Goomba g = (Goomba)sprites.get(i);
				tempList.add(g.marshal());
			}
		}
		return ob;
	}
	
	// void unmarshal(Json ob)
	// {
		// sprites = new ArrayList<Sprite>();
        // Json tmpList = ob.get("sprites");
		// for(int i = 0; i < tmpList.size(); i++)
		// {
			// if(sprites.get(i).type.equals("tube"))
			// {
				// System.out.println("Unmarshaling Tubes");
				// sprites.add(new Tube(tmpList.get(i)));
			// }
		// }
	// }
	
	void unmarshal(Json ob)
	{
		sprites = new ArrayList<Sprite>();
        sprites.add(mario);
		Json jsonList = ob.get("sprites");
		Json tubesList = jsonList.get("tubes");
		Json goombasList = jsonList.get("goombas");
		for(int i = 0; i < tubesList.size(); i++)
		{
			sprites.add(new Tube(tubesList.get(i)));
		}
		for(int i = 0; i < goombasList.size(); i++)
		{
			sprites.add(new Goomba(goombasList.get(i)));
		}
	} 
	
	
	// void unmarshal (Json ob)
	// {	
		// sprites = new ArrayList<Sprites>();
		// sprites.add(mario);
		// Json tmpList = ob.get ("sprites");
		// for(int i = 0; i < tmpList.size(); i++)
		// {        
			// //sprites.add(new Tube(tmpList.get(i)));
			// //get the type that is listed
			// //if Tube:
			// sprites.add(new Tube (tmpList.get(i)));
			// //if Goomba:
			// sprites.add(new Goomba (tmpList.get(i)));
		// }
	// }

	// //MAP EDITOR FUNCTION FOR NEW TUBES
	// public void createNewTube(int x, int y)
	// {
		// int order;
		// boolean rightMost = false; 
		// TubeComparator tC = new TubeComparator();
		// Sprite t = new Tube(x, y);
		// if(sprites.size() > 0)
		// {
			// for(int i = 0; i < sprites.size(); i++)
			// {
				// order = tC.compare(t, sprites.get(i));
				// if(order <= 0)
				// {
					// sprites.add(i, t);
					// System.out.println("New Sprite at x pos: [" + t.x + "]");
					// System.out.println("Adding Sprite at position: [" + i + "]");
					// rightMost = false;
					// break;
				// }
				// if(order > 0)
					// rightMost = true;
			// }
			// if(rightMost)
				// sprites.add(t);
		// }
		// else
			// sprites.add(t);
	// }
	
	//MAP EDITOR FUNCTION FOR ADDING TUBES
	public void addTube(int mx, int my)
	{
		Sprite t = new Tube(mx, my);
		boolean existingTube = false;
		Iterator <Sprite> spriteIterator = sprites.iterator();
		while(spriteIterator.hasNext())
		{
			Sprite temp = spriteIterator.next();
			if(temp.spriteClicked(mx, my))
			{
				sprites.remove(temp);
				existingTube = true;
				break;
			}
		}
		if(!existingTube)
			if(my < Tube.upperTubeLimit)
			{
				t.y -= t.h;
				sprites.add(t);
			}
			else
				sprites.add(t);
	}
	
	public void addGoomba(int mx, int my)
	{
		Sprite g = new Goomba(mx, my);
		boolean existingTube = false;
		Iterator <Sprite> spriteIterator = sprites.iterator();
		while(spriteIterator.hasNext())
		{
			Sprite temp = spriteIterator.next();
			if(temp.spriteClicked(mx, my))
			{
				sprites.remove(temp);
				existingTube = true;
				break;
			}
		}
		if(!existingTube)
			sprites.add(g);
	}
}