package falstad;

import falstad.Constants.StateGUI;
import falstad.MazeController.UserInput;
import falstad.Robot.Turn;
import generation.Distance;
import generation.Order;

public class ManualDriver implements RobotDriver {
	Robot robot;
	int mazeWidth;
	int mazeHeight;
	Distance mazeDistance;
	MazeController mazeController;

	@Override
	public void setRobot(Robot r) {
		robot = r;
	}

	@Override
	public void setDimensions(int width, int height) {
		mazeWidth = width;
		mazeHeight = height;
	}

	@Override
	public void setDistance(Distance distance) {
		mazeDistance = distance;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		return 3000-robot.getBatteryLevel();
	}

	@Override
	public int getPathLength() {
		return robot.getOdometerReading();
	}

	public void robotCommand(UserInput uikey) {
		if (mazeController.getState() == StateGUI.STATE_PLAY) {
			switch(uikey) {
			case Left:
				robot.rotate(Turn.LEFT);
				break;
			case Right:
				robot.rotate(Turn.RIGHT);	
				break;
			case Up:
				robot.move(1, true);
				break;
			case Down:
				robot.rotate(Turn.AROUND);
				break;
			default:
				break;
			}
		}
	}
	
	public MazeController getMazeController() {
		return mazeController;
	}

	public void setMazeController() {
		mazeController = new MazeController();
	}
	
	public void setMazeController(Order.Builder builder) {
		mazeController = new MazeController(builder);
	}
	
	public void setMazeController(String filename) {
		mazeController = new MazeController(filename);
	}
	
	public void setSkillLevel(UserInput uikey, int value) {
		BasicRobot rob = new BasicRobot();
		this.setRobot(rob);
		mazeController.setBasicRobot(rob);
		mazeController.keyDown(uikey, value);
		
		this.setDimensions(mazeController.getMazeConfiguration().getWidth(), mazeController.getMazeConfiguration().getHeight());
		this.setDistance(mazeController.getMazeConfiguration().getMazedists());
	}

	public void trueSetMazeController(MazeController controller) {
        this.mazeController = controller;
		
	}



}
