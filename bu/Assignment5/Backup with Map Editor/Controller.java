//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4	[Controller.java]
//Description: Contains the Controller class for interactive elements in the program.
//================================================================================================
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keyS;
	boolean keySpace;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	Controller(Model m)     //setter for model
	{
		model = m;
	}
	
	//Methods/////////////////////////////////////////////////////////////////////////////////////
	
	//Setters
	void setView(View v)	//setter for view
	{
		view = v;
	}
	
	//Mouse Click Methods
	public void mousePressed(MouseEvent e)
	{
		model.addTube(e.getX()+view.scrollPosition, e.getY());
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	//Keyboard Methods
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			//Arrow key controls
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
			
			//WASD controls for comfort
			case KeyEvent.VK_D: 
				keyRight = true; 
				break;
			case KeyEvent.VK_A: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_W: 
				keyUp = true; 
				break;
			// case KeyEvent.VK_S: //shares same key as save
				// keyDown = true; 
				// break;
			case KeyEvent.VK_SPACE:
				keySpace = true; 
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			//Arrow key controls
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
				break;
			
			//WASD controls for comfort
			case KeyEvent.VK_D: 
				keyRight = false; 
				break;
			case KeyEvent.VK_A: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_W: 
				keyUp = false; 
				break;
			// case KeyEvent.VK_S: //shares same key as save
				// keyDown = false; 
				// break;
			case KeyEvent.VK_SPACE: 
				keySpace = false; 
				break;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE
			|| e.getKeyCode() == KeyEvent.VK_W)
		{
			model.mario.stopJump();
		}
			
		
		char c = e.getKeyChar();
		if(c == 's') //Saves into map.json when user releases 's' key.
		{
			model.marshal().save("map.json");
			System.out.println("You have saved map.json!\n");
		}
		if(c == 'l') //Loads map.json when user releases 'l' key.
		{
			Json j = Json.load("map.json");
			model.unmarshal(j);
			System.out.println("You have loaded map.json!\n");
		}
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
	
	//Others
	public void actionPerformed(ActionEvent e)
	{
	}

	void update()
	{
		Mario.yShadow = Mario.y;
		if(keyRight) 						//on [D or ->]
			if(!model.x_collision) 			//if no obj is hit
				model.mario.moveRight(); 	//move right
			
		if(keyLeft) 						//on [A or <-]
			if(!model.x_collision) 			//if no obj is hit
				model.mario.moveLeft(); 	//move left
			
		if(keyUp || keySpace)				//on [Space or Up Arrow]
		{
			if(model.mario.onLand() 		//if mario is on land
				&& !(model.mario.jumpTimer >= model.mario.jumpLimit) )	//and not jumping:
				model.mario.jump();			//jump
		}
	}
}
