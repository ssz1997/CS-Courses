package falstad;

import falstad.MazeController.UserInput;
import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Cells;
import generation.Distance;
import generation.MazeConfiguration;
import generation.Order;
import generation.SingleRandom;
import java.util.*;

public class Explorer implements RobotDriver {
	Robot robot;
	int mazeWidth;
	int mazeHeight;
	Distance mazeDistance;
	MazeController mazeController;
	MazeConfiguration mazeConfiguration;
	int[][] visits;
	Cells cells;
	SingleRandom random = SingleRandom.getRandom();

	/**
	 * Set the robot to the driver for driving
	 */
	@Override
	public void setRobot(Robot r) {
		robot = r;
	}

	/**
	 * set the dimension, namely width and height, to the driver
	 */
	@Override
	public void setDimensions(int width, int height) {
		mazeWidth = width;
		mazeHeight = height;
		visits = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				visits[i][j] = 0;
			}
		}
	}

	/**
	 * set the distance, which is a 2-d array, to the driver
	 */
	@Override
	public void setDistance(Distance distance) {
		mazeDistance = distance;
	} 

	/**
	 * As specified in the instruction, when the robot is outside the room, it
	 * picks the cell that is least got passed; when it is in the room, it went 
	 * for the door that is least got passed.
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		cells = mazeController.getMazeConfiguration().getMazecells();
		List<List<int[]>> mainList = new ArrayList<List<int[]>>();
		List<int[]> doorList;
		visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]++;
		while(!robot.isAtExit() && robot.canSeeExit(Direction.FORWARD) == false && robot.canSeeExit(Direction.LEFT)==false && robot.canSeeExit(Direction.RIGHT) == false) {
			if(!robot.isInsideRoom()) {
				rotateToNextMove(robot.getCurrentPosition(),robot.getCurrentDirection());
			}
			else {
				//find the position of the four corners of the room
				int xRight = findBorderOfRoom1(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				int xLeft = findBorderOfRoom2(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				int yUp = findBorderOfRoom3(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				int yDown = findBorderOfRoom4(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1]);
				
				//add all the doors in the south/north side to the doorList
	            doorList = new ArrayList<int[]>(); 
	            
				for (int a = xLeft; a <= xRight; a ++) {
					if (cells.hasNoWall(a, yDown, CardinalDirection.North)) {
						int[] door = {a, yDown, 0};
						if (checkIfInList(mainList, door)) {
							doorList.add(door);
						}
						else {
							doorList = mainList.get(indexOfDoorList(mainList, door));
						}
					}
					if (cells.hasNoWall(a, yUp, CardinalDirection.South)) {
						int[] door = {a, yUp, 0};
						if (checkIfInList(mainList, door)) {
							doorList.add(door);
						}
						else {
							doorList = mainList.get(indexOfDoorList(mainList, door));
						}
					}
				}
				//add all the door on the east/west side to the doorList
				for (int b = yDown; b <= yUp; b ++) {
					if (cells.hasNoWall(xLeft, b, CardinalDirection.West)) {
						int[] door = {xLeft, b, 0};
						if (checkIfInList(mainList, door)) {
							doorList.add(door);
						}
						else {
							doorList = mainList.get(indexOfDoorList(mainList, door));
						}
					}
					if (cells.hasNoWall(xRight, b, CardinalDirection.East)) {
						int[] door = {xRight, b, 0};
						if (checkIfInList(mainList, door)) {
							doorList.add(door);
						}
						else {
							doorList = mainList.get(indexOfDoorList(mainList, door));
						}
					}
				}
				if (addDoorListToMainList(mainList, doorList)) {
					mainList.add(doorList);
				}
				
				//add 1 to the door where it went through in
				for (int j = 0; j < doorList.size(); j ++) {
					if (doorList.get(j)[0] == robot.getCurrentPosition()[0] && doorList.get(j)[1] == robot.getCurrentPosition()[1]) {
						doorList.get(j)[2] += 1;
						break;
					}
				}
				
				//select the door to escape room

	            int min = doorList.get(0)[2];
	            
	            for (int m = 0; m < doorList.size(); m++) {
	            	    if (doorList.get(m)[2] < min){
	            	    	    min = doorList.get(m)[2];
	            	    }
	            }
	            List<int[]> doorUsageList = new ArrayList<int[]>(doorList);	
				for (int n = 0; n < doorUsageList.size(); n ++) {
					if (doorUsageList.get(n)[2] > min) {
						doorUsageList.remove(n);
						n -= 1;
					}
				}

				int r = random.nextIntWithinInterval(0, doorUsageList.size() - 1);
				int q = doorList.indexOf(doorUsageList.get(r));
				
				//move robot to the selected door
				int doorx = doorList.get(q)[0];
				int doory = doorList.get(q)[1];
				int initx = robot.getCurrentPosition()[0];
				int inity = robot.getCurrentPosition()[1];
				
				//add 1 to the visit where it went out
				visits[doorx][doory] += 1;
				if (initx > doorx) {
					performRotation(robot.getCurrentDirection(), CardinalDirection.West);
					robot.move(initx - doorx, true);
					if (inity > doory) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.North);
						robot.move(inity - doory, true);
					}
					if (doory > inity) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.South);
						robot.move(doory - inity, true);
					}
				}
				else if (initx < doorx) {
					performRotation(robot.getCurrentDirection(), CardinalDirection.East);
					robot.move(doorx - initx, true);
					if (inity > doory) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.North);
						robot.move(inity - doory, true);
					}
					if (doory > inity) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.South);
						robot.move(doory - inity, true);
					}
				}
				else {
					if (inity > doory) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.North);
						robot.move(inity - doory, true);
					}
					else if (doory > inity) {
						performRotation(robot.getCurrentDirection(), CardinalDirection.South);
						robot.move(doory - inity, true);
					}
				}
				for (CardinalDirection cdd : CardinalDirection.values()) {
					if (mazeController.getMazeConfiguration().getMazecells().hasNoWall(robot.getCurrentPosition()[0], robot.getCurrentPosition()[1], cdd) && !mazeController.getMazeConfiguration().getMazecells().isInRoom(robot.getCurrentPosition()[0] + cdd.getDirection()[0], robot.getCurrentPosition()[1] + cdd.getDirection()[1])){
						if (robot.getBatteryLevel() >= 3) {
							performRotation(robot.getCurrentDirection(), cdd);
						}
						else {
							throw new Exception();
						}
						if (robot.getBatteryLevel() >= 5) {
							for (int k = 0; k < doorList.size(); k ++) {
								if (doorList.get(k)[0] == robot.getCurrentPosition()[0] && doorList.get(k)[1] == robot.getCurrentPosition()[1]) {
									doorList.get(k)[2] += 1;
									break;
								}
							}
							//walk out of the room
							robot.move(1, true);
							visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]] += 1;


							
							break;
						}
						else {
							throw new Exception();
						}
					}	
				}
			}
			
		}
		//when it can see the exit, move towards it
		for (Direction d : Direction.values()) {
			if (robot.canSeeExit(d)) {
				if (d == Direction.FORWARD) 
					robot.move(Integer.MAX_VALUE, true);
				
				else if (d == Direction.LEFT) {
					robot.rotate(Turn.LEFT);
					robot.move(Integer.MAX_VALUE, true);
					
				}
				else if (d == Direction.RIGHT) {
					robot.rotate(Turn.RIGHT);
					robot.move(Integer.MAX_VALUE, true);

				}
			}
		}
		return true;
	}
    
	/**
	 * Firstly put the cells around the robot can it can go to into a list, and then
	 * find the one that is least got passed, and go to that cell.
	 * @param currentPosition
	 * @param currentDirection
	 * @throws Exception
	 */
	private void rotateToNextMove(int[] currentPosition, CardinalDirection currentDirection) throws Exception {
		int minVisit = Integer.MAX_VALUE;
		ArrayList<Direction> directionList = new ArrayList<Direction>();
		int canGo = 0;
		//put the cells that cango into the list
		for(Direction d: Direction.values()) {
			if (robot.distanceToObstacle(d) != 0) {
				directionList.add(d);
				canGo += 1;
				if(visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]]<minVisit) {
					minVisit = visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]];
				}
			}
		}
		//extract the minimum visited go-able cells to be candidates
		int candidates = 0;
		ArrayList<Direction> directionCandidate = new ArrayList<Direction>();
		for(Direction d: directionList) {
			if(visits[directionToCellValue(d,currentDirection,currentPosition)[0]][directionToCellValue(d,currentDirection,currentPosition)[1]]==minVisit) {
				directionCandidate.add(d);
				candidates += 1;
			}
		}
		//rotate to that direction
		Direction direction = directionCandidate.get(random.nextIntWithinInterval(0, candidates-1));
		if(direction == Direction.BACKWARD) {
			robot.rotate(Turn.AROUND);
		}
		else if(direction == Direction.LEFT) {
			robot.rotate(Turn.LEFT);
		}
		else if(direction == Direction.RIGHT) {
			robot.rotate(Turn.RIGHT);
		}
		
		//move to that cell
		robot.move(1, true);
		
		//increase the number of times that the robot passed that cell
		if(canGo == 1) {
			visits[currentPosition[0]][currentPosition[1]]++;
		}
		visits[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]++;

	}
	
	/**
	 * Increase the number of times that the cell is passed.
	 * @param direction
	 * @param currentDirection
	 * @param currentPosition
	 * @return
	 */
	private int[] directionToCellValue(Direction direction, CardinalDirection currentDirection, int[] currentPosition) {
		if(currentDirection == CardinalDirection.East) {
			if(direction == Direction.FORWARD) {
				int[] position = {currentPosition[0]+1, currentPosition[1]};
				return position;
			}
			else if(direction == Direction.BACKWARD) {
				int[] position = {currentPosition[0]-1, currentPosition[1]};
				return position;
			}
			else if(direction == Direction.LEFT) {
				int[] position = {currentPosition[0], currentPosition[1]+1};
				return position;
			}
			else {
				int[] position = {currentPosition[0], currentPosition[1]-1};
				return position;
			}
		}
		else if(currentDirection == CardinalDirection.West) {
			if(direction == Direction.FORWARD) {
				int[] position = {currentPosition[0]-1, currentPosition[1]};
				return position;
			}
			else if(direction == Direction.BACKWARD) {
				int[] position = {currentPosition[0]+1, currentPosition[1]};
				return position;
			}
			else if(direction == Direction.LEFT) {
				int[] position = {currentPosition[0], currentPosition[1]-1};
				return position;
			}
			else {
				int[] position = {currentPosition[0], currentPosition[1]+1};
				return position;
			}
		}
		else if(currentDirection == CardinalDirection.North) {
			if(direction == Direction.FORWARD) {
				int[] position = {currentPosition[0], currentPosition[1]-1};
				return position;
			}
			else if(direction == Direction.BACKWARD) {
				int[] position = {currentPosition[0], currentPosition[1]+1};
				return position;
			}
			else if(direction == Direction.LEFT) {
				int[] position = {currentPosition[0]+1, currentPosition[1]};
				return position;
			}
			else {
				int[] position = {currentPosition[0]-1, currentPosition[1]};
				return position;
			}
		}
		else {
			if(direction == Direction.FORWARD) {
				int[] position = {currentPosition[0], currentPosition[1]+1};
				return position;
			}
			else if(direction == Direction.BACKWARD) {
				int[] position = {currentPosition[0], currentPosition[1]-1};
				return position;
			}
			else if(direction == Direction.LEFT) {
				int[] position = {currentPosition[0]-1, currentPosition[1]};
				return position;
			}
			else {
				int[] position = {currentPosition[0]+1, currentPosition[1]};
				return position;
			}
		}
	}

	
	public int findBorderOfRoom1(int x, int y) {
	      for (int i = x; i < 401; i ++) {
	            if (!cells.isInRoom(i, y)) {
	                  return i - 1;
	            }
	      }
	      return -1;
	}
	public int findBorderOfRoom2(int x, int y) {
	      for (int i = x; i >= 0; i --) {
	            if (!cells.isInRoom(i, y)) {
	                  return i + 1;
	            }
	      }
	      return -1;
	}
	public int findBorderOfRoom3(int x, int y) {
		for (int i = y; i < 401; i ++) {
			if (!cells.isInRoom(x, i)) {
				return i - 1;
			}
		}
		return -1;
	}
	public int findBorderOfRoom4(int x, int y) {
		for (int i = y; i >= 0; i --) {
			if (!cells.isInRoom(x, i)) {
				return i + 1;
			}
		}
		return -1;
	}
    
	public boolean checkIfInList(List<List<int[]>> mainList, int[] door) {
		for (int i = 0; i < mainList.size(); i ++) {
			for (int j = 0; j < mainList.get(i).size(); j ++) {
				if (mainList.get(i).get(j)[0] == door[0] && mainList.get(i).get(j)[1] == door[1]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int indexOfDoorList(List<List<int[]>> mainList, int[] door) {
		for (int i = 0; i < mainList.size(); i ++) {
			for (int j = 0; j < mainList.get(i).size(); j ++) {
				if (mainList.get(i).get(j)[0] == door[0] && mainList.get(i).get(j)[1] == door[1]) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public boolean addDoorListToMainList(List<List<int[]>> mainList, List<int[]> doorList) {
		for (int i = 0; i < mainList.size(); i ++) {
			for (int j = 0; j < mainList.get(i).size() && j < doorList.size(); j ++) {			
				if (mainList.get(i).get(j).equals(doorList.get(j))) {
					return false;
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

	public void trueSetMazeController(MazeController controller) {
		this.mazeController = controller;
		
	}
}
