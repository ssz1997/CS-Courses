package generation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import falstad.Constants;
import generation.Order.Builder;

public class MazeFactoryTest {
	
	OrderStub orderStub = new OrderStub();
	MazeFactory mazeFactory;
	MazeBuilder builder;
	MazeContainer mazeContainer = new MazeContainer();
	Distance dists;
	Cells cells;
	int skillLevel;
	Builder mapBuilder;
	int width;
	int height;

	/**
	 * Set up for tests, can change skillLevel and mapBuilder here
	 * */
	private void setUp() {
		skillLevel = 4;
		mapBuilder = Builder.DFS;
		
		width = Constants.SKILL_X[skillLevel];
		height = Constants.SKILL_Y[skillLevel];
		
		/////change skill Level here!
		orderStub.setSkillLevel(skillLevel);
		/////change Builder here!
		orderStub.setBuilder(mapBuilder);
		
		mazeFactory = new MazeFactory(true);
		mazeFactory.order(orderStub);
		builder = mazeFactory.getBuilder();
		cells = builder.cells;
		dists = builder.dists;
		mazeFactory.waitTillDelivered();
		
		//set up container
		mazeContainer.setMazecells(cells);
		mazeContainer.setMazedists(dists);
		mazeContainer.setWidth(width);
		mazeContainer.setHeight(height);
		mazeContainer.setStartingPosition(dists.getStartPosition());
	}

	/**
	 * Not seeded constructor almost guaranteed should produce two different mazes.
	 * */
	@Test
	public void testMazeFactory() {		
		
		orderStub.setSkillLevel(0);
		orderStub.setBuilder(Builder.Eller);
		
		MazeFactory mazeFactory1 = new MazeFactory();
		mazeFactory1.order(orderStub);
		MazeBuilder builder1 = mazeFactory1.getBuilder();
		builder1.buildOrder(orderStub);
		Cells cells1 = builder1.cells;
		mazeFactory1.waitTillDelivered();
		
		MazeFactory mazeFactory2 = new MazeFactory();
		mazeFactory2.order(orderStub);
		MazeBuilder builder2 = mazeFactory2.getBuilder();
		builder2.buildOrder(orderStub);
		Cells cells2 = builder2.cells;
		mazeFactory2.waitTillDelivered();
		
		//two maze paths generated should not be equal, although there is a possibility for this to be true, but it is highly unlikely.
		assertFalse(cells1.equals(cells2));
	}
	
	/**
	 * Seeded constructor should produce the same maze every time.
	 * Here three are tested.
	 * */
	@Test
	public void testMazeFactoryBoolean() {
		
		orderStub.setSkillLevel(0);
		orderStub.setBuilder(Builder.Prim);
		
		
		MazeFactory mazeFactory1 = new MazeFactory(true);
		mazeFactory1.order(orderStub);
		MazeBuilder builder1 = mazeFactory1.getBuilder();
		builder1.buildOrder(orderStub);
		Cells cells1 = builder1.cells;
		mazeFactory1.waitTillDelivered();
		
		
		MazeFactory mazeFactory2 = new MazeFactory(true);
		mazeFactory2.order(orderStub);
		MazeBuilder builder2 = mazeFactory2.getBuilder();
		builder2.buildOrder(orderStub);
		Cells cells2 = builder2.cells;
		mazeFactory2.waitTillDelivered();
		
		MazeFactory mazeFactory3 = new MazeFactory(true);
		mazeFactory3.order(orderStub);
		MazeBuilder builder3 = mazeFactory3.getBuilder();
		builder3.buildOrder(orderStub);
		Cells cells3 = builder3.cells;
		mazeFactory3.waitTillDelivered();
	
		//If MazeFactory is seeded, then it should generate the same maze the first time it is played.
		assertTrue(cells1.equals(cells2));
		assertTrue(cells2.equals(cells1));
		assertTrue(cells2.equals(cells3));
		assertTrue(cells1.equals(cells3));
	}
	
	/**
	 * Maze should have only one exit, checked by there is only one cell without
	 * border on the edge of the maze.
	 * */
	@Test
	public void testOnlyOneExit1() {
		setUp();
		
		int numberOfExit = 0;
		
		//checking top and bottom rows
		for (int i = 0; i < width; i++) {
			if(!cells.hasWall(i, 0, CardinalDirection.North)) {
				numberOfExit += 1;
			}
			if(!cells.hasWall(i, height-1, CardinalDirection.South)) {
				numberOfExit += 1;
			}
		}
		
		//checking left and right columns
		for (int j = 0; j < height; j++) {
			if(!cells.hasWall(0, j, CardinalDirection.West)) {
				numberOfExit += 1;
			}
			if(!cells.hasWall(width-1, j, CardinalDirection.East)) {
				numberOfExit += 1;
			}
		}
		
		//per game's rule, there should only be one exit.
		assertEquals(1,numberOfExit);
	}
	
	/**
	 * Second test for testing there is only one exit by checking that there is only one
	 * cell with exit path length of 1.*/
	@Test
	public void testOnlyOneExit2() {
		setUp();
		
		int numberOfDistOneCell = 0;
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(dists.getDistance(i, j) == 1) {
					numberOfDistOneCell += 1;
				}
			}
		}
		assertEquals(1, numberOfDistOneCell);
	}
	
	/**
	 * This tests that there is no enclosed cell.
	 * */
	@Test
	public void testNoEnclosedCell() {
		setUp();
		
		for (int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				
				//each cell should have at least one wall tore down to connect to another cell
				assertFalse(cells.hasWall(i, j, CardinalDirection.North)&&
						cells.hasWall(i, j, CardinalDirection.South)&&
						cells.hasWall(i, j, CardinalDirection.West)&&
						cells.hasWall(i, j, CardinalDirection.East));
			}
		}
	}
	
	/**
	 * starting position should be farthest from exist. This also checks that every cell can be 
	 * reached from exit, which further indicating that every cell is connected. Note that this 
	 * relies heavily on getDistance method from Distance class to be correct.
	 * One more thing this test checks is that the path length of each cell to exit should not 
	 * exit the width*height value. Just a safe check that nothing too crazy happening on computing
	 * the path length to exit.
	 * */
	@Test
	public void testValidStartingPosition() {
		setUp();

		int[] start = dists.getStartPosition();
		assertTrue(dists.getDistance(start[0], start[1]) <= width * height);
		for (int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				assertTrue(dists.getDistance(start[0], start[1]) >= dists.getDistance(i, j));
			}
		} 
	}
	
	/* I will not perform this test, since I just figured out that computeDists method is private.
	 * And the previous method sort of checks the intention of this test, so I think that is good.
	 * 
	 * To check if every cell can connect to every other cell, it suffices that if I can show 
	 * every cell can be connect to one cell. I will choose this cell to be the exiting cell,
	 * hence, reinforces that every cell on the maze can be directed to exit position. This test 
	 * rely heavily on the computeDists method, I will use the fact that it will return integer every
	 * time to test the cell are all connected. Lastly, I will compare the computed distance to the original
	 * distance, which should remain the same.
	 * */
	
	
	/**
	 * Here I will test if one can go from start position to end position. This is done so by
	 * checking each successive cell down the path with shorter exit length, that there is no wall
	 * in between such two cells.*/
	@Test
	public void testValidPathFromStartPosition() {
		setUp();
		
		int[] start1 = dists.getStartPosition();
		int[] start2 = mazeContainer.getStartingPosition();
		int pathLength = dists.getDistance(start1[0], start1[1]);
		assertEquals(start1,start2);	//Just a quick check that mazeContianer and dists is the same thing, since I have been using dists all along, and I figured the goal was to use container.
		
		int count = 0;
		while(!cells.isExitPosition(start2[0], start2[1])) {
		    start2 = mazeContainer.getNeighborCloserToExit(start2[0], start2[1]);
		    int dx = start2[0] - start1[0];
		    int dy = start2[1] - start1[1];
		    CardinalDirection cd = CardinalDirection.getDirection(dx, dy);
		    assertTrue(cells.hasNoWall(start1[0], start1[1], cd));
		    start1 = start2;
		    count += 1;
		}
		
		//the times that this looped should be the path of starting position to end position - 1.
		assertEquals(pathLength, count + 1);
	}

}
