/**
 * 
 */
package generation;

import falstad.Constants;

/**
 * @author Yingxu Mu
 *
 */
public class OrderStub implements Order {

	private Builder builder=null;
	private int skill;
	private boolean perfect=false;

	/**
	 * 
	 */
	public OrderStub() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see generation.Order#getSkillLevel()
	 */
	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return this.skill;
	}
	
	public void setSkillLevel(int skill) {
		this.skill = skill;
		if(0==Constants.SKILL_ROOMS[skill]) {
			this.perfect = true;
		}
	}

	/* (non-Javadoc)
	 * @see generation.Order#getBuilder()
	 */
	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return this.builder;
	}
	
	public void setBuilder(Builder builder) {
		this.builder = builder;
	}

	/* (non-Javadoc)
	 * @see generation.Order#isPerfect()
	 */
	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}

	/* (non-Javadoc)
	 * @see generation.Order#deliver(generation.MazeConfiguration)
	 */
	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see generation.Order#updateProgress(int)
	 */
	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub

	}

}
