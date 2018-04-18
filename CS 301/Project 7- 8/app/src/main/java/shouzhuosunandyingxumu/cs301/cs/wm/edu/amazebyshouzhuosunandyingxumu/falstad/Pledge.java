package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.*;

import static shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad.Robot.*;


public class Pledge implements RobotDriver {
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
	 * get method for the robot
	 * @return Robot
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
	 * get the mazeWidth
	 * @return
	 */
	public int getWidth() {
		return mazeWidth;
	}
	
	/**
	 * get the mazeHeight
	 */
	public int getHeight() {
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
	 * get the mazeDistance
	 */
	public Distance getDistance() {
		return mazeDistance;
	}

	/**
	 * For the pledge, at first it will go forward until to a wall. When meeting the obstacle, 
	 * the wall, it will turn left and the counter decrease by 1; as long as the counter is 
	 * negative, it has a tendency to turn right, which will increase the counter by 1 and make 
	 * the counter 0; if it cannot turn left, which means it must turn right, the counter will
	 * become 1, and it would have a tendency to turn left, which would decrease the counter by 1
	 * and make it to 0; as long as the counter is 0, the robot will keep going toward its setted
	 * direction till the end.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		int counter = 0;
		while(!robot.isAtExit()) {
			if(counter == 0) {
				if(robot.distanceToObstacle(Direction.FORWARD)==0) {
					robot.rotate(Turn.LEFT);
					counter -= 1;
				}
				else {
					
					robot.move(1, true);
				}
			}
			else if(counter < 0) {
				if(robot.distanceToObstacle(Direction.RIGHT)!=0) {
					robot.rotate(Turn.RIGHT);
					counter += 1;
					robot.move(1, true);
				}
				else if(robot.distanceToObstacle(Direction.FORWARD)!=0) {
					robot.move(1, true);
				}
				else {
					robot.rotate(Turn.LEFT);
					counter -= 1;
				}
			}
			else if(counter > 0) {
				if(robot.distanceToObstacle(Direction.LEFT)!=0) {
					robot.rotate(Turn.LEFT);
					counter -= 1;
					robot.move(1, true);
				}
				else if(robot.distanceToObstacle(Direction.FORWARD)!=0) {
					robot.move(1, true);
				}
				else {
					robot.rotate(Turn.RIGHT);
					counter += 1;
				}
			}
		}
		for (Direction d: Direction.values()) {
			if (robot.canSeeExit(d)) {
				if (d == Direction.LEFT) {
					robot.rotate(Turn.LEFT);
				}
				else if (d == Direction.RIGHT) {
					robot.rotate(Turn.RIGHT);
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
