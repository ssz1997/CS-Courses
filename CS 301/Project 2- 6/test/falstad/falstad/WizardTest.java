package falstad;

import static org.junit.Assert.*;

import generation.Distance;
import generation.Factory;


import falstad.Constants.StateGUI;
import falstad.MazeController.UserInput;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;
 
import org.junit.Test;

public class WizardTest {
	Wizard wizard = new Wizard();
	BasicRobot robot;
	int skillLevel;
	MazeController mazeController;
	MazeConfiguration mazeConfig;
	Distance mazeDistance;
	MazeApplication mazeApplication;
	
	private void setUp(int skillLevel) throws Exception {
		mazeApplication = new MazeApplication();
		wizard = new Wizard();
		robot = new BasicRobot();
		wizard.setRobot(robot);
		mazeController = new MazeController();
		wizard.trueSetMazeController(mazeController);
		mazeController.setBasicRobot(robot);
		mazeController.setDriver(wizard);
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
	 * Test method for {Maze.Wizard.setRobot}
	 * Just as the manual driver, the driver needs a basic robot to perform rotate and move.
	 */
	@Test
	public void testSetRobot() {
		robot = new BasicRobot();
		assertEquals(null,wizard.getRobot());
		wizard.setRobot(robot);
		assertTrue(wizard.getRobot().equals(robot));
	}

	/**
	 * Test method for {Maze.Wizard.setDimensions}
	 * Test the dimension that is put in the driver is the same as the game dimension.
	 */
	@Test 
	public void testSetDimensions() {
		assertEquals(0, wizard.getWidth());
		assertEquals(0, wizard.getHeight());
		wizard.setDimensions(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		assertEquals(Constants.SKILL_X[skillLevel], wizard.getWidth());
		assertEquals(Constants.SKILL_Y[skillLevel], wizard.getHeight());
	}

	/**
	 * Test method for {Maze.Pledge.setDistance}
	 * Test the distance, which is a 2-d array, is the same as what we have in the game
	 */
	@Test
	public void testSetDistance() {
		assertEquals(null, wizard.getDistance());
		Distance distance = new Distance(Constants.SKILL_X[skillLevel], Constants.SKILL_Y[skillLevel]);
		wizard.setDistance(distance);
		assertTrue(wizard.getDistance().equals(distance));
	}
    
	/**
	 * Test method for {Maze.Wizard.drive2Exit}
	 * The path length for a wizard is the same as the number in the cell
	 * in the Distance
	 * @throws Exception
	 */
	@Test
	public void testDrive2Exit() throws Exception {
		setUp(3);
		int shortestToExit = mazeDistance.getDistance(18, 0);
		assertEquals(shortestToExit, wizard.getPathLength());
	}
    
	/**
	 * Test method for {Maze.Wizard.getEnergyConsumption()}
	 * Test that the energy consumption is the same as shown on the finish
	 * screen.
	 * @throws Exception
	 */
	@Test
	public void testGetEnergyConsumption() throws Exception {
		wizard = new Wizard();
		robot = new BasicRobot();
		wizard.setRobot(robot);
		assertEquals(0,wizard.getEnergyConsumption(), 0);
		setUp(0);
		assertEquals(3000-robot.getBatteryLevel(),wizard.getEnergyConsumption(), 0);
//		setUp(13);
//		assertTrue(wizard.getEnergyConsumption()>=2995);
	}

	/**
	 * Test method for {Maze.Wizard.getPathLength()}
	 * Test that the path length is the same as shown on the finish
	 * screen.
	 * @throws Exception
	 */
	@Test
	public void testGetPathLength() throws Exception {
		wizard = new Wizard();
		robot = new BasicRobot();
		wizard.setRobot(robot);
		assertEquals(0,wizard.getPathLength());
		setUp(3);
		assertEquals(wizard.getPathLength(),robot.getOdometerReading());
		int shortestPath = mazeDistance.getDistance(mazeConfig.getStartingPosition()[0], mazeConfig.getStartingPosition()[1]);
		assertEquals(shortestPath, wizard.getPathLength());
		setUp(15);
		shortestPath = mazeDistance.getDistance(mazeConfig.getStartingPosition()[0], mazeConfig.getStartingPosition()[1]);
		assertTrue(wizard.getPathLength()<=shortestPath);
		assertTrue(wizard.getPathLength()>=3000/8);
	}

	/**
	 * setMazeController test is actually also built in into this test
	 * @throws Exception */
	@Test
	public void testGetMazeController() throws Exception {
		setUp(0);
		//once set up wizard's mazeController is not null
		assertFalse(wizard.getMazeController()==null);
		//to test mazeController actually contains stuff, we get the skillLevel is correct
		assertEquals(0,wizard.getMazeController().getSkillLevel());
		setUp(1);
		assertEquals(1,wizard.getMazeController().getSkillLevel());
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
		wizard = new Wizard();
		robot = new BasicRobot();
		wizard.setRobot(robot);
        mazeController = new MazeController();
        wizard.trueSetMazeController(mazeController);
        mazeController.setDriver(wizard);
        Factory factory = mazeController.factory;
		mazeController.setBasicRobot(robot);
		mazeController.setApplication(mazeApplication);
		mazeController.setDriver(wizard);
		mazeController.setState(StateGUI.STATE_TITLE);
		this.skillLevel = 3;
		wizard.setSkillLevel(UserInput.Start, 3);
		factory.waitTillDelivered();
		assertEquals(126,wizard.getPathLength());
	}

}
