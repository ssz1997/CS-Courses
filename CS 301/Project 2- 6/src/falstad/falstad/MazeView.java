package falstad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import falstad.Constants.StateGUI;
import falstad.MazeController.UserInput;
import generation.Order;

/**
 * Implements the screens that are displayed whenever the game is not in 
 * the playing state. The screens shown are the title screen, 
 * the generating screen with the progress bar during maze generation,
 * and the final screen when the game finishes.
 * @author pk
 *
 */
public class MazeView extends DefaultViewer {

	// need to know the maze model to check the state 
	// and to provide progress information in the generating state
	private MazeController controller ;
	private SelectionPanel selection = new SelectionPanel();
	
	public MazeView(MazeController c) {
		super() ;
		controller = c ;
		
	}

	@Override
	public void redraw(MazePanel panel) {
		//dbg("redraw") ;
		switch (controller.getState()) {
		case STATE_TITLE:
			redrawTitle(panel.getBufferGraphics());
			break;
		case STATE_GENERATING:
			redrawGenerating(panel.getBufferGraphics());
			break;
		case STATE_PLAY:
			// skip this one
			break;
		case STATE_FINISH:
			redrawFinish(panel.getBufferGraphics());
			break;
		}
	}
	
	private void dbg(String str) {
		System.out.println("MazeView:" + str);
	}
	
	// 
	
	/**
	 * Helper method for redraw to draw the title screen, screen is hard coded
	 * @param  gc graphics is the off screen image
	 */
	void redrawTitle(Graphics gc) {
		// produce white background
		gc.setColor(Color.white);
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		// write the title 
		gc.setFont(largeBannerFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.setColor(Color.red);
		centerString(gc, fm, "MAZE", 100);
		// write the reference to falstad
		gc.setColor(Color.blue);
		gc.setFont(smallBannerFont);
		fm = gc.getFontMetrics();
		centerString(gc, fm, "by Paul Falstad", 160);
		centerString(gc, fm, "www.falstad.com", 190);
		
		MazeApplication.controller.mazeApplication.getContentPane().add(selection.panel1, BorderLayout.SOUTH);
		
		selection.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String generation = (String)selection.box2.getSelectedItem();
            	String driver = (String)selection.box1.getSelectedItem();
            	if (generation.equals("DFS")) {
            		controller.setBuilder(Order.Builder.DFS);
            	}
            	else if (generation.equals("prim")) {
            		controller.setBuilder(Order.Builder.Prim);
            	}
            	else if(generation.equals("Eller")) {
            		controller.setBuilder(Order.Builder.Eller);
            	}
            	
                if (driver.equals("Wizard")) {
                	    controller.wizardDriver.trueSetMazeController(controller);
                	    try {
							controller.wizardDriver.setSkillLevel(UserInput.Start, Integer.parseInt((String)selection.box3.getSelectedItem()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                }
                else if (driver.equals("Wall Follower")) {
                	System.out.println("you choose wallfollwer");
                	    controller.wallFollowerDriver = new WallFollower();
                	    controller.setDriver(controller.wallFollowerDriver);
                	    controller.wallFollowerDriver.trueSetMazeController(controller);
                	    try {
							controller.wallFollowerDriver.setSkillLevel(UserInput.Start, Integer.parseInt((String)selection.box3.getSelectedItem()));
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                }
                else if (driver.equals("Pledge")) {
                	controller.pledgeDriver = new Pledge();
                	controller.setDriver(controller.pledgeDriver);
                	controller.pledgeDriver.trueSetMazeController(controller);
            	    try {
						controller.pledgeDriver.setSkillLevel(UserInput.Start, Integer.parseInt((String)selection.box3.getSelectedItem()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                else if (driver.equals("Explorer")) {
                	controller.explorerDriver = new Explorer();
                	controller.setDriver(controller.explorerDriver);
                	controller.explorerDriver.trueSetMazeController(controller);
            	    try {
						controller.explorerDriver.setSkillLevel(UserInput.Start, Integer.parseInt((String)selection.box3.getSelectedItem()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                else {
                	controller.manualDriver = new ManualDriver();
                	controller.setDriver(controller.mazeApplication.driver);
                	controller.manualDriver.trueSetMazeController(controller);
                	 try {
 						controller.manualDriver.setSkillLevel(UserInput.Start, Integer.parseInt((String)selection.box3.getSelectedItem()));
 					} catch (NumberFormatException e1) {
 						// TODO Auto-generated catch block
 						e1.printStackTrace();
 					} catch (Exception e1) {
 						// TODO Auto-generated catch block
 						e1.printStackTrace();
 					}
                }
            }
            });
	}
	/**
	 * Helper method for redraw to draw final screen, screen is hard coded
	 * @param gc graphics is the off screen image
	 */
	void redrawFinish(Graphics gc) {
		//If energy exhausted
		if(controller.energyConsumption >= 2996) {
			// produce blue background
			gc.setColor(Color.RED);
			gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
			// write the title 
			gc.setFont(largeBannerFont);
			FontMetrics fm = gc.getFontMetrics();
			gc.setColor(Color.yellow);
			centerString(gc, fm, "You lose :'(", 100);
			// write some extra blufb
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Try again!", 160);
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Energy Consumption: " + Float.toString(controller.energyConsumption + 5), 200);
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Path Length: " + Float.toString(controller.pathLength + 1), 250);
			// write the instructions
			gc.setColor(Color.white);
			centerString(gc, fm, "Guess press which key you can restart", 300);
		}
	    else{
			// produce blue background
			gc.setColor(Color.RED);
			gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
			// write the title 
			gc.setFont(largeBannerFont);
			FontMetrics fm = gc.getFontMetrics();
			gc.setColor(Color.yellow);
			centerString(gc, fm, "You won!!!", 100);
			// write some extra blufb
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Congratulations!", 160);
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Energy Consumption: " + Float.toString(controller.energyConsumption + 5), 200);
			gc.setColor(Color.yellow);
			gc.setFont(smallBannerFont);
			fm = gc.getFontMetrics();
			centerString(gc, fm, "Path Length: " + Float.toString(controller.pathLength + 1), 250);
			// write the instructions
			gc.setColor(Color.white);
			centerString(gc, fm, "Hit any key to restart", 300);
		}
	}

	/**
	 * Helper method for redraw to draw screen during phase of maze generation, screen is hard coded
	 * only attribute percentdone is dynamic
	 * @param gc graphics is the off screen image
	 */
	void redrawGenerating(Graphics gc) {
		// produce yellow background
		gc.setColor(Color.yellow);
		gc.fillRect(0, 0, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);
		// write the title 
		gc.setFont(largeBannerFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.setColor(Color.red);
		centerString(gc, fm, "Building maze", 150);
		gc.setFont(smallBannerFont);
		fm = gc.getFontMetrics();
		// show progress
		gc.setColor(Color.black);
		if (null != controller)
			centerString(gc, fm, controller.getPercentDone()+"% completed", 200);
		else
			centerString(gc, fm, "Error: no controller, no progress", 200);
		// write the instructions
		centerString(gc, fm, "Hit escape to stop", 300);
		controller.mazeApplication.getContentPane().remove(selection.panel1);
	}
	
	private void centerString(Graphics g, FontMetrics fm, String str, int ypos) {
		g.drawString(str, (Constants.VIEW_WIDTH-fm.stringWidth(str))/2, ypos);
	}

	final Font largeBannerFont = new Font("TimesRoman", Font.BOLD, 48);
	final Font smallBannerFont = new Font("TimesRoman", Font.BOLD, 16);

}
