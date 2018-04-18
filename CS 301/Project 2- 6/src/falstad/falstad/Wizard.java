package falstad;

import falstad.MazeController.UserInput;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import generation.MazeConfiguration;
import generation.Order;

public class Wizard implements RobotDriver {
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
	 * get robot
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
	 * get width
	 */
	public int getWidth() {
		return mazeWidth;
	}
	
	/**
	 * get height
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
	 * get the distance
	 */
	public Distance getDistance() {
		return mazeDistance;
	}

	/**
	 * The wizard looks into the distance 2-d array. It looks around to see which cell is closer
	 *  to exit than where it stands at, and move to that cell and looks around again until it 
	 *  reaches the exit.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		int[] projectPosition;
		while (!robot.isAtExit()) {
			if (robot.getBatteryLevel() >= 3) {
				projectPosition = mazeController.getMazeConfiguration().getNeighborCloserToExit(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				CardinalDirection projectCD = CardinalDirection.getDirection(projectPosition[0] - robot.getCurrentPosition()[0],projectPosition[1] - robot.getCurrentPosition()[1]);				
				performRotation(robot.getCurrentDirection(),projectCD);		
			}
			else {
				throw new Exception();
			}
			if (robot.getBatteryLevel() >= 5) {
				robot.move(1, true);
			}
			else {
				throw new Exception();
			}
		}
		for (CardinalDirection cd : CardinalDirection.values()) {
			if (mazeController.getMazeConfiguration().getMazecells().hasNoWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], cd) && !mazeController.getMazeConfiguration().isValidPosition(robot.getCurrentPosition()[0] + cd.getDirection()[0], robot.getCurrentPosition()[1] + cd.getDirection()[1])){
				if (robot.getBatteryLevel() >= 3) {
					performRotation(robot.getCurrentDirection(), cd);
				}
				else {
					throw new Exception();
				}
				if (robot.getBatteryLevel() >= 5) {
					robot.move(1, true);
					break;
				}
				else {
					throw new Exception();
				}
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
	public void setSkillLevel(UserInput uikey, int value) throws Exception {
		BasicRobot rob = new BasicRobot();
		this.setRobot(rob);
		mazeController.setBasicRobot(rob);
		mazeController.keyDown(uikey, value);
		
		
		
	}
	/**
	 * given the current cardinalDirection and the cardinal Direction that wished to be facing on, 
	 * this method performs the correct rotation to make robot facing the cardinal Direction desired
	 * @param current cardinal Direction
	 * @param project cardinal Direction wished to be facing
	 */
	protected void performRotation(CardinalDirection current, CardinalDirection project) {
		if(current == CardinalDirection.North) {
			if(project == CardinalDirection.North) {
				return;
			}
			if(project == CardinalDirection.South) {
				robot.rotate(Turn.AROUND);
			}
			if(project == CardinalDirection.West) {
				robot.rotate(Turn.RIGHT);
			}
			if(project == CardinalDirection.East) {
				robot.rotate(Turn.LEFT);
			}
		}
		if(current == CardinalDirection.South) {
			if(project == CardinalDirection.North) {
				robot.rotate(Turn.AROUND);
			}
			if(project == CardinalDirection.South) {
				return;
			}
			if(project == CardinalDirection.West) {
				robot.rotate(Turn.LEFT);
			}
			if(project == CardinalDirection.East) {
				robot.rotate(Turn.RIGHT);
			}
		}
		if(current == CardinalDirection.West) {
			if(project == CardinalDirection.North) {
				robot.rotate(Turn.LEFT);			
				}
			if(project == CardinalDirection.South) {
				robot.rotate(Turn.RIGHT);
			}
			if(project == CardinalDirection.West) {
				return;
			}
			if(project == CardinalDirection.East) {
				robot.rotate(Turn.AROUND);
			}
		}
		if(current == CardinalDirection.East) {
			if(project == CardinalDirection.North) {
				robot.rotate(Turn.RIGHT);
			}
			if(project == CardinalDirection.South) {
				robot.rotate(Turn.LEFT);
			}
			if(project == CardinalDirection.West) {
				robot.rotate(Turn.AROUND);
			}
			if(project == CardinalDirection.East) {
				return;
			}
		}
	}
	/**
	 * Set a setted up controller to the driver. Since the controller is created before the 
	 * driver type is specified, we can only create the controller first and then put it into
	 * a new driver, and thus this method is needed.
	 * @param mazeController
	 */
	public void trueSetMazeController(MazeController controller) {
		this.mazeController = controller;
	}
}
