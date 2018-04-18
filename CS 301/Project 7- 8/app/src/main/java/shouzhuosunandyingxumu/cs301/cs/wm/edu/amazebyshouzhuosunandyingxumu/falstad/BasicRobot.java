package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.falstad;


import android.util.Log;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.generation.*;

public class BasicRobot implements Robot{
	//set up necessary fields for this class
    private MazeController mazeController;
    private Cells cells;
    private boolean roomSensor;
    private boolean distanceSensorLeft;
    private boolean distanceSensorRight;
    private boolean distanceSensorForward; 
    private boolean distanceSensorBackward;
    private int [] currentPosition = new int[2];
    private int odometer = 0;
    private float batteryLevel = 3000;
    
    //default constructor
    public BasicRobot() {
    	    roomSensor = true;
    	    distanceSensorLeft = true;
    	    distanceSensorRight = true;
    	    distanceSensorForward = true;
    	    distanceSensorBackward = true;
    }
    
    //constructor for if only certain features are wished to be active
    public BasicRobot(boolean hasRoomSensor, boolean hasDistanceSensorLeft, boolean hasDistanceSensorRight, boolean hasDistanceSensorForward, boolean hasDistanceSensorBackward) {
    	    roomSensor = hasRoomSensor;
    	    distanceSensorLeft = hasDistanceSensorLeft;
    	    distanceSensorRight = hasDistanceSensorRight;
    	    distanceSensorForward = hasDistanceSensorForward;
    	    distanceSensorBackward = hasDistanceSensorBackward;
    }
    
	@Override
	public void rotate(Turn turn) {
		if (batteryLevel >= 3 ) {
			if (turn == Turn.LEFT) {
				mazeController.operations.add(1);

				mazeController.operateGameInPlayingState(MazeController.UserInput.Left);
				batteryLevel -= 3;
				mazeController.setEnergyConsumption(3000-batteryLevel);
			}
			else if (turn == Turn.RIGHT) {
				mazeController.operations.add(2);

				mazeController.operateGameInPlayingState(MazeController.UserInput.Right);
				batteryLevel -= 3;
				mazeController.setEnergyConsumption(3000-batteryLevel);
			}
			else {
				if (batteryLevel >= 6) {
					mazeController.operations.add(3);

					mazeController.operateGameInPlayingState(MazeController.UserInput.Left);
					mazeController.operateGameInPlayingState(MazeController.UserInput.Left);
					batteryLevel -= 6;
					mazeController.setEnergyConsumption(3000-batteryLevel);
				}
			}
		}
	}

	@Override
	public void move(int distance, boolean manual) {
		if (manual ) {


			for (int steps = 0; steps < distance; steps ++) {
				if (!hasStopped() && batteryLevel >= 5) {
					mazeController.operations.add(0);

					mazeController.operateGameInPlayingState(MazeController.UserInput.Up);
					batteryLevel -= 5;
					mazeController.setEnergyConsumption(3000-batteryLevel);
					odometer += 1;
					mazeController.setPathLength(odometer);
					currentPosition = mazeController.getCurrentPosition();					
				}
				else if (hasStopped()) {
					break;
				}
			}

		}
	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		if (mazeController.getMazeConfiguration().isValidPosition(currentPosition[0], currentPosition[1]))
			return currentPosition;
		else {
			throw new Exception();
		}
	}

	@Override
	public void setMaze(MazeController maze) {
        mazeController = maze;


        cells = mazeController.getMazeConfiguration().getMazecells();
  
        currentPosition = maze.getCurrentPosition();
        
	}

	@Override
	public boolean isAtExit() {
		return cells.isExitPosition(currentPosition[0], currentPosition[1]);
	}
    /**
     * if exit and current not on same row or same column, return false
     * if any of them is the same, check if the "real" direction is the same as 
     * the direction that user wants to check. If not the same, return false; if the 
     * same, check if this direction has no wall at all until we are out of the maze;
     * if no wall, return true; otherwise return false.
     * @param direction - user input
     */
	@Override
	public boolean canSeeExit(Direction direction) throws UnsupportedOperationException {

		try {
			Log.v(" x", String.valueOf(currentPosition[0]));
			Log.v(" y", String.valueOf(currentPosition[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!hasDistanceSensor(direction) || batteryLevel <= 0) {
			throw new UnsupportedOperationException();
		}
		
		CardinalDirection cd;
		cd = directionConverter(direction);
		batteryLevel -= 1;
		mazeController.setEnergyConsumption(3000-batteryLevel);
		if (currentPosition[0] != cells.getExit()[0] && currentPosition[1] != cells.getExit()[1]) {
			return false;
		}
		else {
			if (currentPosition[0] == cells.getExit()[0] && currentPosition[1] == cells.getExit()[1]) {
				return cells.hasNoWall(currentPosition[0], currentPosition[1], cd) && !mazeController.getMazeConfiguration().isValidPosition(currentPosition[0] + cd.getDirection()[0], currentPosition[1] + cd.getDirection()[1]);
			}
			else if (currentPosition[0] == cells.getExit()[0] && currentPosition[1] != cells.getExit()[1]){
                if (cells.getExit()[1] > currentPosition[1]) {
					if (cd == CardinalDirection.South) {
						for (int row = currentPosition[1]; row <= cells.getExit()[1]; row ++) {
							if (cells.hasWall(currentPosition[0], row, cd)) {
								return false;
							}
						}
						return true;
					}
					return false;
				}
                else if (cells.getExit()[1] < currentPosition[1]) {
					if (cd == CardinalDirection.North) {
						for (int row = currentPosition[1]; row >= cells.getExit()[1]; row --) {
							if (cells.hasWall(currentPosition[0], row, cd)) {
								return false;
							}
						}
						return true;
					}
					return false;
				}
			}
			else if (currentPosition[1] == cells.getExit()[1] && currentPosition[0] != cells.getExit()[0]) {
                if (cells.getExit()[0] > currentPosition[0]) {
                	    if (cd == CardinalDirection.East) {
						for (int column = currentPosition[0]; column <= cells.getExit()[0]; column ++) {
							if (cells.hasWall(column, currentPosition[1], cd)) {
								return false;
							}
						}
						return true;
					}
					return false;
				}
                else if (cells.getExit()[0] < currentPosition[0]) {
                	     if (cd == CardinalDirection.West) {
 						for (int column = currentPosition[0]; column >= cells.getExit()[0]; column --) {
 							if (cells.hasWall(column, currentPosition[1], cd)) {
 								return false;
 							}
 						}
 						return true;
 					}
 					return false;
				}
			}
			
		}
		return false; 
		
	
	}

	@Override
	public boolean isInsideRoom() throws UnsupportedOperationException {
		if (hasRoomSensor()) {
		    return cells.isInRoom(currentPosition[0], currentPosition[1]);
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean hasRoomSensor() {
		return roomSensor;
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return mazeController.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		return batteryLevel;
	}

	@Override
	public void setBatteryLevel(float level) {
        batteryLevel = level;
	} 
 
	@Override
	public int getOdometerReading() {
		return odometer;
	}

	@Override
	public void resetOdometer() {
        odometer = 0;
	}

	@Override
	public float getEnergyForFullRotation() {
		// TODO Auto-generated method stub
		return 12;
	}

	@Override
	public float getEnergyForStepForward() {
		return 5;
	}

	@Override
	public boolean hasStopped() {
		return batteryLevel <= 0 || cells.hasWall(currentPosition[0], currentPosition[1], getCurrentDirection());
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		if (this.isAtExit()){
			return -1;
		}
		else if (hasDistanceSensor(direction)) {

			CardinalDirection cd = directionConverter(direction);
			int startx = currentPosition[0];
			int starty = currentPosition[1];
			int dis = 0;
			if (canSeeExit(direction)) {
				return Integer.MAX_VALUE;
			}
			else {
				while (!cells.hasWall( startx, starty, cd) && mazeController.getMazeConfiguration().isValidPosition(startx + cd.getDirection()[0], starty + cd.getDirection()[1])) {
					startx += cd.getDirection()[0];
					starty += cd.getDirection()[1];
					dis += 1;
				}
				return dis;
			}
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean hasDistanceSensor(Direction direction) {
		if (direction == Direction.LEFT) {
			return distanceSensorLeft;
		}
		else if (direction == Direction.RIGHT) {
			return distanceSensorRight;
		}
		else if (direction == Direction.FORWARD) {
			return distanceSensorForward;
		}
		else {
			return distanceSensorBackward;
		}
	}
	
	/**
	 * knowing the current cardinalDirection the robot is facing and given the direction relative to robot,
	 * returns cardinalDirection of this direction.
	 * @param direction relative to robot(Left, Right, Forward, Backward)
	 * @return cardinalDirection of the direction of relative to robot.
	 */
	public CardinalDirection directionConverter(Direction direction) {
		if (direction == Direction.LEFT) {
			return getCurrentDirection().rotateClockwise();
		}
		else if (direction == Direction.RIGHT) {
			return getCurrentDirection().oppositeDirection().rotateClockwise();
		}
		else if (direction == Direction.FORWARD) {
			return getCurrentDirection();
		}
		else {
			return getCurrentDirection().oppositeDirection();
		}
	}
	
	public Turn directionToTurn(Direction direction) {
		if (direction == Direction.LEFT) {
			return Turn.LEFT;
		}
		else if (direction == Direction.RIGHT) {
			return Turn.RIGHT;
		}
		else if (direction == Direction.BACKWARD) {
			return Turn.AROUND;
		}
		else {
			return null;
		}
	}
	
	public MazeController getMazeController() {
		return mazeController;
	}
	
	public void setCurrentPosition(int[] newPosition) {
		System.out.println(newPosition[0]);
		System.out.println(newPosition[1]);
		System.out.println(currentPosition);
		currentPosition[0] = newPosition[0];
		currentPosition[1] = newPosition[1];
	}
    
}
