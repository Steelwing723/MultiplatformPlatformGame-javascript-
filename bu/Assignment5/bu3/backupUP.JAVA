// if((mario.xPos <= sprites.get(i).x + sprites.get(i).w		//if mario is above or below the tube
						// || (mario.xPos + mario.w) >= sprites.get(i).x))		
					// {															//if mario's feet were just at or above the 
																				// //top of the tube, or if the tube is short
																				// //enough to walk up like stairs:													
						// //System.out.println("Mario's height: [" + mario.h + "]");											
						// System.out.println("y after: [" + (mario.y + mario.h) + "]");
						// System.out.println("yShadow after: [" + (mario.yShadow + mario.h) + "]");
						// System.out.println("Tube y: [" + sprites.get(i).y + "]");
						
						// //System.out.println("yShadow + h: [" + (mario.yShadow + mario.h) + "]");
						// //System.out.println("Pipe y + stepValue: [" + (sprites.get(i).y + mario.stepValue) + "]");
						// //mario.overObject = true;
						
						// if((mario.yShadow + mario.h) <= (sprites.get(i).y + mario.stepValue)
							// || mario.y + mario.h <= (sprites.get(i).y + mario.stepValue))
						// {
							// mario.onObject = true;	
							// mario.vert_vel = 0;									//halt (downward) velocity
							// mario.y = sprites.get(i).y - mario.h;			//push mario up to top of tube
							// mario.yShadow = mario.y;
							// // if(mario.yShadow + mario.h == sprites.get(i).y)	//if mario's last frame was on an object
							// // {
								// //System.out.println("On Tube!");
													// //mario is on an object
								// //System.out.println("Tube's Top: " + sprites.get(i).y);
							// //}
						// }
						
																				// //if mario's head was just at or below the
																				// //bottom of the tube:
						// if((mario.yShadow) >= (sprites.get(i).y + sprites.get(i).h))
						// {
							// mario.vert_vel = 0;									//halt (upward) velocity
							// mario.y = sprites.get(i).y + sprites.get(i).h;				//push mario down to bottom of tube
						// }
					// }
					// if((mario.y + mario.h) > sprites.get(i).y				//if mario is right or left of the tube
						// && (mario.y < sprites.get(i).y + sprites.get(i).h)) 			
					// {
						// if(mario.rightFacing)									//if mario is facing right
						// {														//find how far mario entered the tube:
							// collisionDiff = Math.abs((mario.xPos + mario.w) - sprites.get(i).x);  
							// View.scrollPosition -= collisionDiff + 1;			//snap mario back to left side of tube
							// x_collision = true;									//an x collision has happened					
						// }
						
						// if(!mario.rightFacing)									//if mario is facing left
						// {														//find how far mario entered the tube:
							// collisionDiff = Math.abs((mario.xPos) - (sprites.get(i).x + mario.w));
							// View.scrollPosition += collisionDiff - 4;			//snap mario back to right side of tube
							// x_collision = true;									//an x collision has happened
						// }
					// }