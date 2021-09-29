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
	Mario mario;								//I know this is a redundant reference, but I use it
												//for quick access in collision handling below. 
	static boolean collision = false;			//determines collisions
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
		if(!collision)															//if no collision is found
			mario.onObject = false;												//mario can't be on an object
		
		checkFireballs();
		checkGoombas();
		
		//Collision Handling//////////////////////////////////////////////////////////////////////////////////////////
		//Collision detection is universal and in sprite class
		for(int i = 0; i < sprites.size(); i++)									//loop through sprite array
		{
			sprites.get(i).update();											//update each sprite in the array
			
			if((sprites.get(i).type.equals("goomba")))							//if current sprite is a goomba
			{
				Goomba g = (Goomba)sprites.get(i);								//set a new goomba to current goomba
				if(g.framesOnFire < 40											//if this goomba has started burning
					&& g.framesOnFire > 20)										//and it's been past 20 frames.
					g.isDead = true;											//set goomba to die soon
				
				for(int j = 0; j < sprites.size(); j++)							//loop through sprite array
				{
					if((sprites.get(j).type.equals("tube")))					//check against tubes
					{
						collision = g.spriteCollision(sprites.get(j));			//check if goomba has collided with a tube.
						if(collision)											//has the goomba collided with a tube?
						{
							g.tubeCollision(sprites.get(j));					//get goomba to reverse direction
							if(g.turnAround)									//if goomba is turning around
								g.direction = !sprites.get(i).direction;		//reverse its direction
						}
					}
				}
			}
			if((sprites.get(i).type.equals("fireball")))						//if current sprite is a fireball
			{
				Fireball f = (Fireball)sprites.get(i);							//set a new fireball to current fireball
				
				f.burnTime --;													//decrement burn time
				if(f.burnTime <= 0)												//if burntime is less than or equal to 0
					f.extinguished = true;										//delete the fireball
				
				for(int j = 0; j < sprites.size(); j++)							//loop through sprite array
				{
					if((sprites.get(j).type.equals("tube")))					//check fireball against tubes
					{
						collision = f.spriteCollision(sprites.get(j));			//check if fireball collided with tube
						
						if(collision)											//if fireball has collided with tube
						{
							f.extinguished = true;								//set fireball to be deleted soon
							fizzle();											//play fizzle sound
						}
					}
					if((sprites.get(j).type.equals("goomba")))					//check fireball against goombas
					{
						collision = f.spriteCollision(sprites.get(j));			//check if fireball collided with a goomba
						
						if(collision)											//has fireball collided with a goomba?
						{
							Goomba g = (Goomba)sprites.get(j);					//set a new goomba to current goomba
							g.burn();											//light goomba on fire
							goombaIgnited();									//play goomba ignited sound
							f.extinguished = true;								//set fireball to be deleted soon
						}
					}
				}
			}
			
			if((sprites.get(i).type.equals("tube")))							//if current sprite is a tube
			{
				collision = mario.spriteCollision(sprites.get(i));				//check if mario collides with tube
				if(collision)													//has mario collided with a tube?
					mario.tubeCollision(sprites.get(i));						//get mario out of the tube
			}
		}
	}
	
	void checkFireballs()														//checks for extinguished fireballs
	{
		for(int i = 0; i < sprites.size(); i++)									//loop through sprite array
		{
			if((sprites.get(i).type.equals("fireball")))						//get a fireball
			{
				Fireball f = (Fireball)sprites.get(i);							//set new fireball to current fireball
				if(f.extinguished)												//if fireball has been set to extinguished
					sprites.remove(i);											//delete the fireball
			}
		}
	}
	
	void checkGoombas()															//checks for dead goombas
	{
		for(int i = 0; i < sprites.size(); i++)									//loop through the sprite array
		{
			if((sprites.get(i).type.equals("goomba")))							//get a goomba
			{
				Goomba g = (Goomba)sprites.get(i);								//set new goomba to current goomba
				if(g.isDead)													//if goomba is set to die
					sprites.remove(i);											//delete the goomba
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
	
	//MAP EDITOR FUNCTION FOR ADDING GOOMBAS
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