package generation;

public class StubOrder implements Order {

	MazeConfiguration mazeConfig;
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return 6;
	}
 
	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return Builder.Eller;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		this.mazeConfig = mazeConfig;

	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub

	}
    
	public MazeConfiguration getMazeConfig() {
		return mazeConfig;
	}
}
