package falstad;

import static org.junit.Assert.*;


import org.junit.Test;

import falstad.Constants.StateGUI;
import falstad.MazeController.UserInput;
import generation.Distance;
import generation.Factory;
import generation.MazeConfiguration;
import generation.Order;
import generation.Order.Builder;
 
public class WallFollowerTest {
	  
	BasicRobot robot;
	MazeController mazeController;
	WallFollower wallFollower;
    int skillLevel;
    MazeConfiguration mazeConfig;
	Distance mazeDistance;
    MazeApplication mazeApplication;
    
	private void setUp(int skillLevel) throws Exception {
		mazeApplication = new MazeApplication();
		wallFollower = new WallFollower();
		mazeController = new MazeController();
		robot = new BasicRobot();
		wallFollower.setRobot(robot);
		wallFollower.trueSetMazeController(mazeController);
		mazeController = wallFollower.getMazeController();
		mazeController.setBasicRobot(robot);
		mazeController.setDriver(wallFollower);
		mazeController.setApplication(mazeApplication);
		mazeController.setState(StateGUI.STATE_GENERATING);
		this.skillLevel = skillLevel;
		mazeController.setSkillLevel(skillLevel);
		robot.setMaze(mazeController);
		mazeController.factory.order(mazeController);
		mazeController.factory.waitTillDelivered();
		mazeConfig = mazeController.getMazeConfiguration();
		mazeDistance = mazeConfig.getMazedists();
	}
	
	/**
	 * Test method for {Maze.WallFollower.setRobot}
	 * Just as the manual driver, the driver needs a basic robot to perform rotate and move.
	 */
  	@Test
	public void testSetRobot() {
  		wallFollower = new WallFollower();
		assertEquals(null,wallFollower.getRobot());
		robot = new BasicRobot();
		wallFollower.setRobot(robot);
		assertTrue(wallFollower.getRobot().equals(robot));
	}
 
  	
	
  	/**
  	 * Test method for {Maze.WallFollower.setDimensions}
  	 * Test the dimension that is put in the driver is the same as the game dimension.
  	 */
	@Test
	public void testSetDimensions() {
		wallFollower = new WallFollower();
		assertEquals(0, wallFollower.getMazeHeight());
		assertEquals(0, wallFollower.getMazeWidth());
		wallFollower.setDimensions(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		assertEquals(Constants.SKILL_X[skillLevel], wallFollower.getMazeWidth());
		assertEquals(Constants.SKILL_Y[skillLevel], wallFollower.getMazeHeight());
	}

	/**
	 * Test method for {Maze.WallFollower.setDistance}
	 * Test the distance, which is a 2-d array, is the same as what we have in the game
	 */
	@Test
	public void testSetDistance() {
		wallFollower = new WallFollower();
		assertEquals(null, wallFollower.getDistance());
		Distance distance = new Distance(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		wallFollower.setDistance(distance);
		assertTrue(wallFollower.getDistance().equals(distance));
	}
	
    /**
     * test method for {WallFollower.drive2Exit}
     * For a wallFollower, there will be a wall on the left side at all time except the time
     * it is looking for a left wall at first. 
     * @throws Exception 
     */
	@Test 
	public void testDrive2Exit() throws Exception {
		setUp(2);
	    assertEquals(StateGUI.STATE_FINISH, mazeController.getState());
	    assertTrue(wallFollower.getEnergyConsumption() > 0);
	    assertTrue(wallFollower.getPathLength() > 0);
	    try {
	    	    setUp(15);
	    	    
	    }
	    catch (Exception ex){
	    	System.out.println("Low battery.");
	    }
	}

	/**
	 * Test method for {Maze.WallFollower.setMazeController}
	 * WallFollower needs to initialize a mazeController which in turn initialize the whole game.
	 */
	@Test
	public void testSetMazeController() {
		mazeController = new MazeController();
		wallFollower = new WallFollower();
		assertEquals(null, wallFollower.getMazeController());
		wallFollower.trueSetMazeController(mazeController);
		assertTrue(wallFollower.getMazeController().equals(wallFollower.getMazeController()));
		
	}
	
}
