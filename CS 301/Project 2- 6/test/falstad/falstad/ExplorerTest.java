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

public class ExplorerTest {

	BasicRobot robot;
	MazeController mazeController;
	Explorer explorer;
    int skillLevel; 
    MazeConfiguration mazeConfig;
	Distance mazeDistance;
	MazeApplication mazeApplication;
    
	private void setUp(int skillLevel) throws Exception {
		mazeApplication = new MazeApplication();
		mazeController = new MazeController();
		explorer = new Explorer();
		robot = new BasicRobot();
		explorer.setRobot(robot);
		explorer.trueSetMazeController(mazeController);
		mazeController = explorer.getMazeController();
		mazeController.setBasicRobot(robot);
		mazeController.setApplication(mazeApplication);
		mazeController.setDriver(explorer);
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
		explorer = new Explorer();
		assertEquals(null,explorer.robot);
		robot = new BasicRobot();
		explorer.setRobot(robot);
		assertTrue(explorer.robot.equals(robot));
	}

	
	@Test
	public void testSetDimensions() {
		explorer = new Explorer();
		assertEquals(0, explorer.mazeWidth);
		assertEquals(0, explorer.mazeHeight);
		explorer.setDimensions(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		assertEquals(Constants.SKILL_X[skillLevel], explorer.mazeWidth);
		assertEquals(Constants.SKILL_Y[skillLevel], explorer.mazeHeight);
	}
 
	@Test
	public void testSetDistance() {
		explorer = new Explorer();
		assertEquals(null, explorer.mazeDistance);
		Distance distance = new Distance(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		explorer.setDistance(distance);
		assertTrue(explorer.mazeDistance.equals(distance));
	}

	@Test
	public void testDrive2Exit() throws Exception {
		setUp(2);
	    assertTrue(explorer.getEnergyConsumption() > 0);
	    assertTrue(explorer.getPathLength() > 0);
	    try {
	    	    setUp(15);
	    	    
	    }
	    catch (Exception ex){
	    	System.out.println("Low battery.");
	    }
	}



}
