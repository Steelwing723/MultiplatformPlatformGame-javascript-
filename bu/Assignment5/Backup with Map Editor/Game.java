//Name: Joshua Davis
//UAID: 010946462
//Date: 9/29/2020
//Assignment 4 [Game.java]
//Description: Contains the main function and Game class for piecing the program together.
//================================================================================================
import javax.swing.JFrame;	
import java.awt.Toolkit;

public class Game extends JFrame	//JFrame is a class that comes with the Java class library, the Game 
									//class inherits all methods and member variables in JFrame by extension.
{
	//Member Variables////////////////////////////////////////////////////////////////////////////
	Model model;
	Controller controller;
	View view;
	Sound sound;
	static final int screenWidth = 800;
	static final int screenHeight = 600;
	
	//Constructors////////////////////////////////////////////////////////////////////////////////
	public Game()
	{
		//Set Member Variables
		model = new Model();	
		controller = new Controller(model);
		view = new View(controller, model);		
		
		
		
		//Music
		try 
		{
			sound = new Sound("smw_ovr1.mid", 1);
			sound.play(0.02, 10);
		}
		catch (Exception e)
		{
			System.out.println("Error, cannot read Sound file!\n");
			e.printStackTrace(System.err);
		}
		
		
				
		view.addMouseListener(controller);						//sets controller to be in charge of mouse clicks.
		this.addKeyListener(controller);						//sets controller to be in charge of key presses.
		
		this.setTitle("Mario Side-Scroller");					//sets current Game object's title.
		this.setSize(screenWidth, screenHeight);				//sets current Game object's size
		this.setFocusable(true);								//Allows the object to be "focusable" for interaction.
		this.getContentPane().add(view);						//Adds "view" to the content pane of the object.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Specifies to exit on close.
		this.setVisible(true);									//allows the object to be visible on the screen.
	}
	
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); 									// Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); 				// Updates screen

			//Go to sleep for 40 miliseconds
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	//Main////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) //Program begins here
	{
		Game g = new Game();	//"new Game" object is made, memory is allocated, constructor is called. 
		g.run();				//calls run method.
	}
}
