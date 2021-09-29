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

class Model
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	ArrayList<Sprite> sprites;					
	Mario mario;							
	static boolean collision = false;			//determines collisions
	//int updateCounter = 0;						//(uncomment when debugging)
	double collisionDiff;						//distance penetrated during collision
	boolean fireballCollision = false;
	static Sound fizzle;
	Sound goombaIgnited;

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
		//updateCounter++;													
		//System.out.println("===============[Frame #" + updateCounter + "]");
		//if(mario.onObject)
		//System.out.println("Mario is on an object!");
		//System.out.println("xPos: " + mario.xPos);
		//System.out.println("yShadow: " + mario.yShadow);
		//System.out.println("y: " + mario.yFetch);
		//System.out.println("y + h before: [" + (mario.y + mario.h) + "]");
		//System.out.println("yShadow + h before: [" + (mario.yShadow + mario.h) + "]");

		if(!collision)															//if no collision is found
			mario.onObject = false;												//mario can't be on an object
		
		checkFireballs();
		checkGoombas();
		
		//Collision Handling//////////////////////////////////////////////////////////////////////////////////////////
		for(int i = 0; i < sprites.size(); i++)									//loop through sprite array
		{
			sprites.get(i).update();											//update each sprite in the array
			
			if((sprites.get(i).type.equals("goomba")))							//grab a goomba
			{
				Goomba g = (Goomba)sprites.get(i);								//set a new goomba to current goomba
				if(g.framesOnFire < 40
					&& g.framesOnFire > 15)
					g.isDead = true;
				
				for(int j = 0; j < sprites.size(); j++)							//loop through sprite array
				{
					if((sprites.get(j).type.equals("tube")))					//check against tubes
					{
						collision = g.spriteCollision(sprites.get(j));			
						if(collision)
						{
							g.turnAround = false;
							g.tubeCollision(sprites.get(j));
							if(g.turnAround)
								g.direction = !sprites.get(i).direction;
						}
					}
				}
			}
			
			if((sprites.get(i).type.equals("fireball")))
			{
				Fireball f = (Fireball)sprites.get(i);
				
				f.burnTime --;
				if(f.burnTime <= 0)
					f.extinguished = true;
				
				for(int j = 0; j < sprites.size(); j++)							//loop through sprite array
				{
					if((sprites.get(j).type.equals("tube")))					//check against tubes
					{
						collision = f.spriteCollision(sprites.get(j));
						
						if(collision)
						{
							f.extinguished = true;
							fizzle();
						}
					}
					if((sprites.get(j).type.equals("goomba")))
					{
						collision = f.spriteCollision(sprites.get(j));
						
						if(collision)
						{
							Goomba g = (Goomba)sprites.get(j);
							g.burn();
							goombaIgnited();
							f.extinguished = true;
						}
					}
				}
			}
			
			if((sprites.get(i).type.equals("tube")))
			{
				collision = mario.spriteCollision(sprites.get(i));				//check if mario collides with it
				if(collision)
					mario.tubeCollision(sprites.get(i));
			}
		}
	}
	
	void checkFireballs()
	{
		for(int i = 0; i < sprites.size(); i++)	
		{
			if((sprites.get(i).type.equals("fireball")))
			{
				Fireball f = (Fireball)sprites.get(i);
				if(f.extinguished)
					sprites.remove(i);
			}
		}
	}
	
	void checkGoombas()
	{
		for(int i = 0; i < sprites.size(); i++)	
		{
			if((sprites.get(i).type.equals("goomba")))
			{
				Goomba g = (Goomba)sprites.get(i);
				if(g.isDead)
					sprites.remove(i);
			}
		}
	}
	
	
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
		boolean existingGoomba = false;
		Iterator <Sprite> spriteIterator = sprites.iterator();
		while(spriteIterator.hasNext())
		{
			Sprite temp = spriteIterator.next();
			if(temp.spriteClicked(mx, my))
			{
				sprites.remove(temp);
				existingGoomba = true;
				break;
			}
		}
		if(!existingGoomba)
			sprites.add(g);
	}
	
	public void addFireball()
	{
		Sprite f = new Fireball(mario.xPos, mario.y, mario.rightFacing);
		boolean existingFB = false;
		Iterator <Sprite> spriteIterator = sprites.iterator();
		sprites.add(f);
	}
	
	//Audio
	static void fizzle()
	{
		try 
		{
			fizzle = new Sound("smw_shell_ricochet.wav", 1);
			fizzle.play(0.5, 0);
		}
		catch (Exception e)
		{
			System.out.println("Error, cannot read sound file for fizzle in Model!");
			e.printStackTrace(System.err);
		}
	}
	
	void goombaIgnited()
	{
		try 
		{
			goombaIgnited = new Sound("smw_bowser_fire.wav", 1);
			goombaIgnited.play(0.5, 0);
		}
		catch (Exception e)
		{
			System.out.println("Error, cannot read sound file for goombaIgnited in Model!");
			e.printStackTrace(System.err);
		}
	}
}