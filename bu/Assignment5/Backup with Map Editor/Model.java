//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4	[Model.java]
//Description: Contains the Model class for representing the state of the game
//			   and the TubeComparator class for organizing Tube objects.
//================================================================================================
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

class TubeComparator implements Comparator<Tube>
{
	public int compare(Tube a, Tube b)
	{
		if(a.x < b.x)
			return -1;
		else if(a.x > b.x)
			return 1;
		else
			return 0;
	}

	public boolean equals(Object obj)
	{
		return false;
	}
}

class Model
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	ArrayList<Tube> tubes;					
	Mario mario;							
	View view;									
	static boolean collision = false;			//determines collisions
	static boolean x_collision = false;			//determines horizontal collisions
	//int updateCounter = 0;						//(uncomment when debugging)
	double collisionDiff;						//distance penetrated during collision

	//Constructors////////////////////////////////////////////////////////////////////////////////
	Model()
	{
		tubes = new ArrayList<Tube>();
		mario = new Mario((Game.screenWidth/2 - Mario.width), 0);	
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	public void update()
	{
		// //Uncomment for debugging!
		// updateCounter++;													
		// System.out.println("===============[Frame #" + updateCounter + "]");
		// System.out.println("Mario is on an object: " + mario.onObject);
		// System.out.println("xPos: " + mario.xPos);
		// System.out.println("Feet are at: " + (mario.y + mario.height));
		
		mario.update();														//update mario
		
		if(!collision)														//if no collision is found
			mario.onObject = false;											//mario can't be on an object
		
		//Collision detection//////////////////////////////////////////////////////////////////////////////////////////
		for(int i = 0; i < tubes.size(); i++)								//loop through tube array
		{
			collision = tubeCollision(mario, tubes.get(i));					//check for collisions
			
			if(collision)													//if a collision is found
			{
				if((mario.xPos <= tubes.get(i).x + tubes.get(i).width		//if mario is above or below the tube
					|| (mario.xPos + mario.width) >= tubes.get(i).x))		
				{															//if mario's feet were just at or above the 
																			//top of the tube, or if the tube is short
																			//enough to walk up like stairs:
					if((mario.yShadow + mario.height) <= tubes.get(i).y + mario.stepValue)
					{
						mario.vert_vel = 0;									//halt (downward) velocity
						mario.y = tubes.get(i).y - mario.height;			//push mario up to top of tube
						if(mario.yShadow + mario.height == tubes.get(i).y)	//if mario's last frame was on an object
							mario.onObject = true;							//mario is on an object
					}
					
																			//if mario's head was just at or below the
																			//bottom of the tube:
					if((mario.yShadow) >= (tubes.get(i).y + Tube.height))
					{
						mario.vert_vel = 0;									//halt (upward) velocity
						mario.y = tubes.get(i).y + Tube.height;				//push mario down to bottom of tube
					}
				}
				if((mario.y + mario.height) > tubes.get(i).y				//if mario is right or left of the tube
					&& (mario.y < tubes.get(i).y + Tube.height)) 			
				{
					if(mario.rightFacing)									//if mario is facing right
					{														//find how far mario entered the tube:
						collisionDiff = Math.abs((mario.xPos + mario.width) - tubes.get(i).x);  
						View.scrollPosition -= collisionDiff + 1;			//snap mario back to left side of tube
						x_collision = true;									//an x collision has happened					
					}
					
					if(!mario.rightFacing)									//if mario is facing left
					{														//find how far mario entered the tube:
						collisionDiff = Math.abs((mario.xPos) - (tubes.get(i).x + mario.width));
						View.scrollPosition += collisionDiff - 4;			//snap mario back to right side of tube
						x_collision = true;									//an x collision has happened
					}
				}
				
			
			}
		}
	}

	Json marshal()
	{
        Json ob = Json.newObject();
        Json tmpList = Json.newList();
        ob.add("tubes", tmpList);
        for(int i = 0; i < tubes.size(); i++)
            tmpList.add(tubes.get(i).marshal());
        return ob;
    }
	
	void unmarshal(Json ob)
	{
		tubes = new ArrayList<Tube>();
        Json tmpList = ob.get("tubes");
        for(int i = 0; i < tmpList.size(); i++)
            tubes.add(new Tube(tmpList.get(i)));
	}

	public void createNewTube(int x, int y)
	{
		int order;
		boolean rightMost = false; 
		TubeComparator tC = new TubeComparator();
		Tube t = new Tube(x, y);
		if(tubes.size() > 0)
		{
			for(int i = 0; i < tubes.size(); i++)
			{
				order = tC.compare(t, tubes.get(i));
				if(order <= 0)
				{
					tubes.add(i, t);
					rightMost = false;
					break;
				}
				if(order > 0)
					rightMost = true;
			}
			if(rightMost)
				tubes.add(t);
		}
		else
			tubes.add(t);
	}
	
	public void addTube(int mx, int my)
	{
		Tube t = new Tube(mx, my);
		boolean existingTube = false;
		Iterator <Tube> tubeIterator = tubes.iterator();
		while(tubeIterator.hasNext())
		{
			Tube temp = tubeIterator.next();
			if(temp.tubeClicked(mx, my))
			{
				tubes.remove(temp);
				existingTube = true;
				break;
			}
		}
		if(!existingTube)
			if(my < t.upperTubeLimit)
			{
				t.y -= t.height;
				tubes.add(t);
			}
			else
				tubes.add(t);
	}
	
	public boolean tubeCollision(Mario m, Tube t)
	{
		if((m.xPos + (m.width)) < t.x)		//Mario is left of the tube
		{
			x_collision = false;
			return false;
		}
		if(m.xPos > (t.x + (t.width))) 		//Mario is right of the tube
		{
			x_collision = false;
			return false;
		}
		if((m.y + m.height) <= t.y) 		//Mario is higher than the tube
			return false;
		if(m.y > (t.y + t.height)) 			//Mario is below the tube
			return false;
			
		return true;
	}
}