package falstad;

import static org.junit.Assert.*;

import org.junit.Test;

import falstad.Constants.StateGUI;
import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import generation.Factory;
import generation.MazeConfiguration;

public class BasicRobotTest {
	ManualDriver manualDriver = new ManualDriver();
	BasicRobot robot;
	int skillLevel;
	MazeController mazeController;
	MazeConfiguration mazeConfig;
	Distance mazeDistance;
	MazeApplication mazeApplication;
	/**
	 * This method is for the setup of the BasicRobot, using the default constructor in which are sensors
	 * are turned on
	 * @param skillLevel - for testing different skillLevels
	 */
	private void setUp(int skillLevel) {	
		mazeApplication = new MazeApplication();
		mazeController = new MazeController();
		robot = new BasicRobot();
		manualDriver.setRobot(robot);
		manualDriver.trueSetMazeController(mazeController);
		mazeController = manualDriver.getMazeController();
		mazeController.setBasicRobot(robot);
		mazeController.setApplication(mazeApplication);
		mazeController.setDriver(manualDriver);
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
     * This method is for another setup of the BasicRobot, with constructor with 
	 * five parameters indicating which sensor is turned on; in this one the 
	 * roomSensor is turned on while the forward distanceSensor is turned off.
     * @param skillLevel - testing for different skillLevels
     */
	private void setUp3(int skillLevel, boolean room, boolean left, boolean right, boolean forward, boolean backward) {
		robot = new BasicRobot(room, left, right, forward, backward);
		mazeController = new MazeController();
		mazeController.setBasicRobot(robot);
		System.out.println("\n\n\n" + mazeController.robot);
		mazeController.setState(StateGUI.STATE_GENERATING);
		mazeController.setSkillLevel(skillLevel);
		Factory factory = mazeController.factory;
		factory.order(mazeController);
		factory.waitTillDelivered();
		mazeConfig = mazeController.getMazeConfiguration();	
		
	}
	
	/**
	 * Test method for {BasicRobot.rotate}
	 * We use skill level 3 and setSeed to 20, which gives us a consistent maze.
	 * We try to turn different angles and to see if its direction is as expected, 
	 * and battery level going down at the same time.
	 */
	@Test
	public void testRotate() {
		setUp(3);
		
		assertEquals(0, robot.getOdometerReading());
		
		
		robot.rotate(Turn.LEFT);
		assertEquals(CardinalDirection.South, robot.getCurrentDirection());
		assertEquals(2997, robot.getBatteryLevel(), 0);
		robot.rotate(Turn.AROUND);
		assertEquals(CardinalDirection.North, robot.getCurrentDirection());
		assertEquals(2991, robot.getBatteryLevel(), 0);
		robot.rotate(Turn.RIGHT);
		assertEquals(CardinalDirection.West, robot.getCurrentDirection());
		assertEquals(2988, robot.getBatteryLevel(), 0);
		
		robot.setBatteryLevel(3);
		robot.rotate(Turn.RIGHT);
		assertEquals(0, robot.getBatteryLevel(), 0);
		assertEquals(CardinalDirection.South, robot.getCurrentDirection());
		robot.rotate(Turn.RIGHT);
		//CardinalDirection should stay the same since didn't actually rotate
		//because not enough battery
		assertEquals(CardinalDirection.South, robot.getCurrentDirection());
		
		//turn around takes 6 battery unit for it is a 180 degree rotation
		setUp(3);
		robot.setBatteryLevel(3);
		robot.rotate(Turn.AROUND);
		assertEquals(3, robot.getBatteryLevel(), 0);
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
		robot.setBatteryLevel(6);
		robot.rotate(Turn.AROUND);
		assertEquals(0, robot.getBatteryLevel(), 0);
		assertEquals(CardinalDirection.West, robot.getCurrentDirection());
		
		//rotate should not change odometer field and should match with the
		//number of step before rotations
		assertEquals(0, robot.getOdometerReading());
	}

	/**
	 * Test method for {BasicRobot.move}
	 * We use skill level 3 and setSeed to 20, which gives us a consistent maze.
	 * We test if manual is set to false, which indicates that the robot is not 
	 * drived by manual driver, then it cannot move.
	 * Then we test distance = 0, which is not moving; then move one step. Then 
	 * test when it faces a wall, then it cannot move.
	 * When along the road there is wall, the robot stops right at the wall. 
	 * Always check batteryLevel and odometer after each move.
	 * @throws Exception
	 */
	@Test
	public void testMove() throws Exception {
		setUp(3);
		int[] originalPosition = mazeConfig.getStartingPosition();
		robot.move(0, true);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0]);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(3000, robot.getBatteryLevel(), 0);
		assertEquals(0, robot.getOdometerReading(), 0);
		
		robot.move(100, false);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0]);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(3000, robot.getBatteryLevel(), 0);
		assertEquals(0, robot.getOdometerReading(), 0);
		
		robot.move(1, true);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(2995, robot.getBatteryLevel(), 0);
		assertEquals(1, robot.getOdometerReading(), 0);
		
		robot.move(1, true);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(2995, robot.getBatteryLevel(), 0);
		assertEquals(1, robot.getOdometerReading(), 0);
		
		robot.rotate(Turn.LEFT);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(2992, robot.getBatteryLevel(), 0);
		assertEquals(1, robot.getOdometerReading(), 0);
		
		robot.move(3, true);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1] + 3);
		assertEquals(2977, robot.getBatteryLevel(), 0);
		assertEquals(4, robot.getOdometerReading(), 0);
		
		robot.rotate(Turn.AROUND);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1] + 3);
		assertEquals(2971, robot.getBatteryLevel(), 0);
		assertEquals(4, robot.getOdometerReading(), 0);
		
		robot.move(4, true);
		assertEquals(robot.getCurrentPosition()[0], originalPosition[0] + 1);
		assertEquals(robot.getCurrentPosition()[1], originalPosition[1]);
		assertEquals(2956, robot.getBatteryLevel(), 0);
		assertEquals(7, robot.getOdometerReading(), 0);
		
		//robot should not move if the battery level is too low to support
		//this operation
		setUp(3);
		originalPosition = mazeConfig.getStartingPosition();
		robot.setBatteryLevel(5);
		robot.move(1, true);
		assertEquals(0, robot.getBatteryLevel(), 0);
		assertEquals(1, robot.getOdometerReading());
		mazeController.setState(StateGUI.STATE_TITLE);
		mazeController.setState(StateGUI.STATE_GENERATING);
		mazeController.setState(StateGUI.STATE_PLAY);
		robot.setBatteryLevel(9);
		robot.rotate(Turn.LEFT);
		assertEquals(6, robot.getBatteryLevel(), 0);
		assertEquals(1, robot.getOdometerReading());
		
		robot.move(3, true);
		assertEquals(1, robot.getBatteryLevel(), 0);
		assertEquals(2, robot.getOdometerReading());
		assertEquals(originalPosition[0]+1, robot.getCurrentPosition()[0]);
		assertEquals(originalPosition[1]+1, robot.getCurrentPosition()[1]);
	}

	/**
	 * getCurrentPosition method isn't tested, since on previous test we have already
	 * tested it and it behaved well. Exception isn't tested, since we get the current
	 * position directly from mazeController, which isn't possible to return a current
	 * position that is outside of the maze.
	 */

	@Test
	public void testSetMaze() {
		MazeApplication mazeApplication = new MazeApplication();
		BasicRobot robot = new BasicRobot();
		assertEquals(null, robot.getMazeController());
		
		MazeController mazeController1 = new MazeController();
		mazeController1.setApplication(mazeApplication);
		mazeController1.init();
		robot.setMaze(mazeController1);
		assertNotEquals(null, robot.getMazeController());
		assertEquals(robot.getMazeController(), mazeController1);
		
		MazeController mazeController2 = new MazeController();
		mazeController2.setApplication(mazeApplication);
		mazeController2.init();
		robot.setMaze(mazeController2);
		assertNotEquals(null, robot.getMazeController());
		assertFalse(mazeController1.equals(robot.getMazeController()));
	}
	
	/**
	 * Test getCurrentPosition but actually test change starting position
	 * on mazeController
	 * */
 
	@Test
	public void testGetCurrentPosition() throws Exception{
		setUp(3);
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertEquals(7, robot.getCurrentPosition()[0]);
		assertEquals(0, robot.getCurrentPosition()[1]);

		//illogical start position
		mazeController.setCurrentPosition(-1, -1);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		try {
			robot.getCurrentPosition();
		}
		catch(Exception ex) {
			System.out.print("Error expected, invalid position.");
		}
		
		//start position out of range
		mazeController.setCurrentPosition(20, 20);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		try {
			robot.getCurrentPosition();
		}
		catch(Exception ex) {
			System.out.print("Error expected, invalid position.");
		}

	}
    
	/**
	 * Test method for {BasicRobot.isAtExit}
	 * Test if currentPosition is at exit.
	 * @throws Exception
	 */
	@Test
	public void testIsAtExit() throws Exception {
		setUp(3);
		assertFalse(robot.isAtExit());
		
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.isAtExit());
		
		setUp(7);
		mazeController.setCurrentPosition(17, 34);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.isAtExit());
		
		setUp(3);
		mazeController.setCurrentPosition(8, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.isAtExit());
	}
    /**
     * test method for {BasicRobot.canSeeExit}
     * we use skill level 3, 5, 10, and 14, to get the exit on four different borders
     * and setSeed to 20 to get consistent mazes. We test when it does not have sensor
     * then throws exception. When it has sensor, when it can see the exit, it cannot 
     * see exit on the other three direction. 
     */
	@Test
	public void testCanSeeExit() throws Exception{
		//test if canSeeExit at exit
		setUp(10);
		mazeController.setCurrentPosition(69, 9);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		//assume starting cardinal Direction is always East
		assertEquals(CardinalDirection.East, robot.getCurrentDirection());
		assertEquals(CardinalDirection.East, mazeController.getCurrentDirection());
		assertEquals(robot.getCurrentDirection(), mazeController.getCurrentDirection());
		assertTrue(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		
		//test if canSeeExit one step from exit in the direction of exit
		//when the exit is at south border
		mazeController.setCurrentPosition(68, 9);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.canSeeExit(Direction.FORWARD));
		
		//test if canSeeExit two step from exit in the direction of exit
		//when the exit is at south border
		mazeController.setCurrentPosition(67, 9);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.canSeeExit(Direction.FORWARD));
		
		//test if canSeeExit in the direction of exit with a wall in front
		//when the exit is at south border
		mazeController.setCurrentPosition(66, 9);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		
		//test the robot can only seeExit in the direction of exit at exit
		//when the exit is at south border
		setUp(3);
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.canSeeExit(Direction.RIGHT));
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can seeExit in the direction of exit at one cell away from exit
		//when the exit is at south border
		mazeController.setCurrentPosition(7, 1);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		
		//test the robot cannot seeExit if none of its currentPosition[x,y] is the same as
		//exit[x,y]
		mazeController.setCurrentPosition(1, 1);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can only seeExit in the direction of exit at exit
		//when the exit is at north border
		setUp(5);
		mazeController.setCurrentPosition(10, 24);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertTrue(robot.canSeeExit(Direction.LEFT));
		
		////test the robot can only seeExit in the direction of exit one step away from exit
		//when the exit is at north border
		mazeController.setCurrentPosition(10, 23);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can only seeExit in the direction of exit when there is wall in front
		//when the exit is at north border
		mazeController.setCurrentPosition(10, 22);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can only seeExit in the direction of exit at exit
		//when the exit is at west border at the corner
		setUp(14);
		mazeController.setCurrentPosition(0, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		assertTrue(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		 
		//test the robot can only seeExit in the direction of exit at exit
		//when the exit is at west border at the corner after rotation.
		robot.rotate(Turn.AROUND);
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertTrue(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD)); 
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can only seeExit in the direction of exit two steps from exit
		//when the exit is at west border
		mazeController.setCurrentPosition(2, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertTrue(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//test the robot can only seeExit in the direction of exit when there is wall in front
		//when the exit is at west border
		mazeController.setCurrentPosition(4, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.canSeeExit(Direction.RIGHT));
		assertFalse(robot.canSeeExit(Direction.FORWARD));
		assertFalse(robot.canSeeExit(Direction.BACKWARD));
		assertFalse(robot.canSeeExit(Direction.LEFT));
		
		//if sensor is turn off on particular direction
		setUp3(3, false, false, false, false, false);
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		try {
			assertTrue(robot.canSeeExit(Direction.RIGHT));
		}
		catch(Exception ex) {
			System.out.print("Should not sense exit on the right as sensor is turn off as expected.");
		}
		
		
	}
    
	/**
	 * Test method for {BasicRobot.isInsideRoom}
	 * Test if the currentPosition is in room
	 * When roomSensor is not turned on, it throws exception.
	 * @throws UnsupportedOperationException
	 */
	@Test
	public void testIsInsideRoom() throws UnsupportedOperationException{
		setUp(3);
		
		//test the currentPosition is in room
		mazeController.setCurrentPosition(7, 7);
		//System.out.println(mazeController.getCurrentPosition()[0]);
		//System.out.println(mazeController.getCurrentPosition()[1]);
        System.out.println(robot);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertTrue(robot.isInsideRoom());
		
		//test the currentPosition is not in room
		mazeController.setCurrentPosition(0, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertFalse(robot.isInsideRoom());
		
		//test the robot does not have sensor - throw exception
		setUp3(3, false, false, false, false, false);
		mazeController.setCurrentPosition(7, 7);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		try{
			assertTrue(robot.isInsideRoom());
		}
		catch (Exception ex){
			System.out.println("Room Sensor is not activated.");
		}
	}
    
	/**
	 * Test method {BasicRobot.hasRoomSensor}
	 * The first test for testRoomSensor uses the default robot constructor,
	 * and thus it should have roomSensor.
	 */
	@Test
	public void testHasRoomSensorOne() {
		int skillLevel = 3;
		setUp(skillLevel);
		assertTrue(robot.hasRoomSensor());
	}
	
	/**
	 * Test method {BasicRobot.hasRoomSensor}
	 * The first test for testRoomSensor uses the non-default robot constructor,
	 * which is specified in the private method setUp2(int),
	 * and thus it should not have roomSensor.
	 */
	@Test
	public void testHasRoomSensorTwo() {
		int skillLevel = 3;
		setUp3(skillLevel, false, false, false, false, false);
		assertFalse(robot.hasRoomSensor());
	}
	
    /**
     * Test method for {BasicRobot.getBateryLevel}
     * The name of the method speaks for itself.
     */
	@Test
	public void testGetBatteryLevel() {
		setUp(3);
		assertEquals(3000, robot.getBatteryLevel(), 0);
		robot.setBatteryLevel(2800);
		assertEquals(2800, robot.getBatteryLevel(), 0);
	}
	
    /**
     * Test method for {BasicRobot.setBateryLevel}
     * Reset battery level to 0.
     */
	@Test
	public void testSetBatteryLevel() {
		setUp(3);
		robot.setBatteryLevel(0);
		assertEquals(0, robot.getBatteryLevel(), 0);
		//test unable to move when battery level is low
	}

	 /**
     * Test method for {BasicRobot.resetOdometer}
     * Reset odometer to 0.
     */
	@Test
	public void testResetOdometer() {
		setUp(3);
		robot.resetOdometer();
		assertEquals(0, robot.getOdometerReading(),0);
	}
    
	/**
	 * Test method for {BasicRobot.getEnergyForFullRotation}
     * The name of the method speaks for itself.
	 */
	@Test
	public void testGetEnergyForFullRotation() {
		setUp(3);
		assertEquals(12, robot.getEnergyForFullRotation(), 0);
	}

	/**
	 * Test method for {BasicRobot.getEnergyForStepForward}
     * The name of the method speaks for itself.
	 */
	@Test
	public void testGetEnergyForStepForward() {
		setUp(3);
		assertEquals(5, robot.getEnergyForStepForward(), 0);
	}

	/**
	 * Test method for {BasicRobot.hasStopped}
	 * The function of the method is specified in its interface.
	 */
	@Test
	public void testHasStopped() {
		setUp(3);
		assertFalse(robot.hasStopped());
		robot.move(1, true);
		assertTrue(robot.hasStopped());
		robot.rotate(Turn.LEFT);
		assertFalse(robot.hasStopped());
	}
	
    /**
     * Test method {BasicRobot.distanceToObstacle}
     * test at a not-in-room position and a in-room position
     */
	@Test
	public void testDistanceToObstacle() {
		setUp(3);
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		//if it looks at the exit, distance should be Integer.MAXVALUE
		assertEquals(Integer.MAX_VALUE, robot.distanceToObstacle(Direction.RIGHT));
		
		//test when currentPosition is not in room
		assertEquals(0, robot.distanceToObstacle(Direction.BACKWARD));
		assertEquals(0, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(2, robot.distanceToObstacle(Direction.FORWARD));
		
		//test when currentPosition is in room
		mazeController.setCurrentPosition(7, 7);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		assertEquals(3, robot.distanceToObstacle(Direction.LEFT));
		assertEquals(3, robot.distanceToObstacle(Direction.RIGHT));
		assertEquals(5, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(2, robot.distanceToObstacle(Direction.BACKWARD));
		
		//same test again for low battery level
		mazeController.setCurrentPosition(7, 0);
		robot.setCurrentPosition(mazeController.getCurrentPosition());
		robot.setBatteryLevel(0);
		try {
			robot.distanceToObstacle(Direction.RIGHT);
		}
		catch(Exception ex) {
			System.out.print("Battery too low to support rotation");
		}
		
		try{
			robot.distanceToObstacle(Direction.BACKWARD);
		}
		catch(Exception ex) {
			System.out.print("Battery too low to support rotation");
		}
		try{
			robot.distanceToObstacle(Direction.LEFT);
		}
		catch(Exception ex) {
			System.out.print("Battery too low to support rotation");
		}
		robot.setBatteryLevel(1);
		assertEquals(2, robot.distanceToObstacle(Direction.FORWARD));
		assertEquals(0, robot.getBatteryLevel(), 0);
	}
	
    /** Test method {BasicRobot.hasDistanceSensor}
     * Using the default constructor so that each distanceSensor is turned on
     */
	@Test
	public void testHasDistanceSensorOne() {
		setUp(3);
		for (Direction dir: Direction.values()) {
		    assertTrue(robot.hasDistanceSensor(dir));
		}
	}
	
	 /** Test method {BasicRobot.hasDistanceSensor}
     * Using the non-default constructor so that each distanceSensor is turned off
     */
	@Test
	public void testHasDistanceSensorTwo() {
		setUp3(3, true, false, false, false, false);
		for (Direction dir: Direction.values()) {
		    assertFalse(robot.hasDistanceSensor(dir));
		}
	}
	
	
}
