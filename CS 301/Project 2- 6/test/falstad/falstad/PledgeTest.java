package falstad;

import static org.junit.Assert.*;

import org.junit.Test;

import falstad.Constants.StateGUI;
import falstad.MazeController.UserInput;
import generation.Distance;
import generation.Factory;
import generation.MazeConfiguration;
import generation.Order.Builder;

public class PledgeTest {

	Pledge pledge = new Pledge();
	BasicRobot robot;
	int skillLevel;
	MazeController mazeController;
	MazeConfiguration mazeConfig;
	Distance mazeDistance;
	MazeApplication mazeApplication;
	
	private void setUp(int skillLevel) throws Exception {
		mazeApplication = new MazeApplication();
		mazeController = new MazeController();
		pledge = new Pledge();
		robot = new BasicRobot();
		pledge.setRobot(robot);
		pledge.trueSetMazeController(mazeController);
		mazeController = pledge.getMazeController();
		mazeController.setBasicRobot(robot);
		mazeController.setApplication(mazeApplication);
		mazeController.setDriver(pledge);
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
	 * Test method for {Maze.Pledge.setRobot}
	 * Just as the manual driver, the driver needs a basic robot to perform rotate and move.
	 */
	@Test
	public void testSetRobot() {
		robot = new BasicRobot();
		assertEquals(null,pledge.getRobot());
		pledge.setRobot(robot);
		assertTrue(pledge.getRobot().equals(robot));
	}

	/**
	 * Test method for {Maze.Pledge.setDimensions}
	 * Test the dimension that is put in the driver is the same as the game dimension.
	 */
	@Test 
	public void testSetDimensions() {
		assertEquals(0, pledge.getWidth());
		assertEquals(0, pledge.getHeight());
		pledge.setDimensions(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		assertEquals(Constants.SKILL_X[skillLevel], pledge.getWidth());
		assertEquals(Constants.SKILL_Y[skillLevel], pledge.getHeight());
	}

	/**
	 * Test method for {Maze.Pledge.setDistance}
	 * Test the distance, which is a 2-d array, is the same as what we have in the game
	 */
	@Test
	public void testSetDistance() {
		assertEquals(null, pledge.getDistance());
		Distance distance = new Distance(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		pledge.setDistance(distance);
		assertTrue(pledge.getDistance().equals(distance));
	}
	/**
     * test method for {Maze.Pledge.drive2Exit}
     * If the robot can get to exit, the energy consumption must be less
     * than 3000 and path length greater than 0; if the robot cannot make 
     * to the exit, the energy consumption must be greater than 2000 and 
     * path length greater than 0
     * @throws Exception 
     */
	@Test
	public void testDrive2Exit() throws Exception {
		setUp(3);
		assertTrue(pledge.getEnergyConsumption() < 3000);
		assertTrue(pledge.getPathLength()>0);
		setUp(15);
		assertTrue(pledge.getEnergyConsumption()>2000);
		assertTrue(pledge.getPathLength()>0);
	}

	

	
    /**
     * test method for {Maze.Pledge.getMazeController}
     * test the controller we put in is the same as we get
     * @throws Exception
     */
	@Test
	public void testGetMazeController() throws Exception {
		setUp(0);
		//once set up pledge's mazeController is not null
		assertFalse(pledge.getMazeController()==null);
		//to test mazeController actually contains stuff, we get the skillLevel is correct
		assertEquals(0,pledge.getMazeController().getSkillLevel());
		setUp(1);
		assertEquals(1,pledge.getMazeController().getSkillLevel());
	}

	
    
	/**
	 * Test method for {Maze.Pledge.setSkillLevel}
	 * Test that after we call setSkillLevel the maze is actually built
	 * correctly.
	 * @throws Exception
	 */
	@Test
	public void testSetSkillLevel() throws Exception {	
		mazeApplication = new MazeApplication();
		mazeController = new MazeController();
		pledge = new Pledge();
		robot = new BasicRobot();
		pledge.setRobot(robot);
		pledge.trueSetMazeController(mazeController);
		mazeController = pledge.getMazeController();
		Factory factory = mazeController.factory;
		mazeController.setBasicRobot(robot);
		mazeController.setApplication(mazeApplication);
		mazeController.setDriver(pledge);
		mazeController.setState(StateGUI.STATE_TITLE);
		this.skillLevel = 3;
		pledge.setSkillLevel(UserInput.Start, 3);
		factory.waitTillDelivered();
		assertEquals(206,pledge.getPathLength());
	}



}
