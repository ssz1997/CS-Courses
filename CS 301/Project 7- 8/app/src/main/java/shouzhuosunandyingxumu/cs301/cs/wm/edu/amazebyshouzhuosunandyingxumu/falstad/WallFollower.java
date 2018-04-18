package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad;;

import android.util.Log;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.*;

public class WallFollower implements RobotDriver {
	private Robot robot;
	private int mazeWidth;
	private int mazeHeight;
	private Distance mazeDistance;
	private MazeController mazeController;
	private MazeConfiguration mazeConfiguration;
	
	/**
	 * Set the robot to the driver for driving
	 */
	@Override
	public void setRobot(Robot r) {
		robot = r;
	}

	/**
	 * get the robot
	 */
	public Robot getRobot() {
		return robot;
	}
	/**
	 * set the dimension, namely width and height, to the driver
	 */
	@Override
	public void setDimensions(int width, int height) {
		mazeWidth = width;
		mazeHeight = height;
	}
	
	/**
	 * get mazeWidth 
	 */
	public int getMazeWidth() {
		return mazeWidth;
	}

	/**
	 * get mazeHeight
	 */
	public int getMazeHeight() {
		return mazeHeight;
	}
	/**
	 * set the distance, which is a 2-d array, to the driver
	 */
	@Override
	public void setDistance(Distance distance) {
		mazeDistance = distance;
	}

	/**
	 * get distance
	 */
	public Distance getDistance() {
		return mazeDistance;
	}
	/**
	 * For a wall follower, it first will go toward left to find a wall, then it 
	 * will let the robot follow the wall - that is to say, the robot to the left 
	 * must be a wall, until it's at the exit.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		//first move robot next to a wall
		int distanceToLeft = robot.distanceToObstacle(Robot.Direction.LEFT);
		int distanceToFront;
		if(distanceToLeft != 0) {
			robot.rotate(Robot.Turn.LEFT);
			robot.move(distanceToLeft, true);
			robot.rotate(Robot.Turn.RIGHT);
		}
		robot.move(1, true);
		while(!robot.isAtExit()) {
			if(robot.getBatteryLevel()>=1) {
			    distanceToLeft = robot.distanceToObstacle(Robot.Direction.LEFT);
			}
			else throw new Exception();
			
			if(robot.getBatteryLevel()>=1) {
			    distanceToFront = robot.distanceToObstacle(Robot.Direction.FORWARD);
			}
			else throw new Exception();
			
			if(distanceToLeft == 0) {
				if(distanceToFront == 0) {
					if(robot.getBatteryLevel()>=3) {
					    robot.rotate(Robot.Turn.RIGHT);
					}
					else throw new Exception();
				}
				else {
					if(robot.getBatteryLevel()>=5) {
					    robot.move(1, true);
					    System.out.println("move mvoe moe");
					}
					else throw new Exception();
				}
			}
			
			//if distance to left is not zero, then turn left and follow the wall
			else {
				if(robot.getBatteryLevel()>=3) {
					robot.rotate(Robot.Turn.LEFT);
				}
				else throw new Exception();
				
				if(robot.getBatteryLevel()>=5) {
					robot.move(1, true);
				}
				else throw new Exception();
				
			}
		}
		for (Robot.Direction d: Robot.Direction.values()) {
			if (robot.canSeeExit(d)) {
				if (d == Robot.Direction.LEFT) {
					robot.rotate(Robot.Turn.LEFT);
				}
				else if (d == Robot.Direction.RIGHT) {
					robot.rotate(Robot.Turn.RIGHT);
				}
				robot.move(1, true);
			}
		}
		return true;
	}

	/** 
	 * get energy consumption, as shown in the method name
	 */
	@Override
	public float getEnergyConsumption() {
		return 3000-robot.getBatteryLevel();
	}

	/**
	 * get path length, as shwon in the method name.
	 */
	@Override
	public int getPathLength() {
		return robot.getOdometerReading();
	}

	/**
	 * get the mazeController the robot driver has.
	 * @return mazeController;
	 */
	public MazeController getMazeController() {
		return mazeController;
	}

	
	/**
	 * The maze is generated through this method call. Calling keydown and pass the skillLevel
	 * to it starts to build the maze.
	 * @param uikey
	 * @param value
	 * @throws Exception
	 */
	public void setSkillLevel(MazeController.UserInput uikey, int value) throws Exception {
		BasicRobot rob = new BasicRobot();
		this.setRobot(rob);
		mazeController.setBasicRobot(rob);
		mazeController.keyDown(uikey, value);
		
	}
	
	/**
	 * Set a setted up controller to the driver. Since the controller is created before the 
	 * driver type is specified, we can only create the controller first and then put it into
	 * a new driver, and thus this method is needed.
	 */
	public void trueSetMazeController(MazeController controller) {
		this.mazeController = controller;
	}

}
