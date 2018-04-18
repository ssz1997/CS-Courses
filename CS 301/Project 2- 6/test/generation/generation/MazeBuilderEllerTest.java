package generation;
import java.util.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MazeBuilderEllerTest extends MazeBuilderEller {
	

	/**
	 * Test method {MazeBuilderEller.initializeSets}
	 * initialize the sets which has exactly one element in each set
	 */
    @Test
    public void testSetsInitialization(){
       	initializeSets(15);
       	for (int i = 0; i < 14; i ++) {
       		assertTrue(mainList.get(i).contains(i));
       		for (int j = 0; j < 14 && j != i; j ++) {
       			assertFalse(mainList.get(i).contains(j));
       		}
       	}
       	
    } 
   
   /**
    * Test method {MazeBuilderEller.merge}
    * We merge the 2nd, 3rd, 4th cells in row 0
    * the mainList[1] then should contain 1,2 and 3; the mainList[2] should contain 4;
    */
    @Test
    public void testmerge() {
    	    Cells cells = new Cells(25,15);
    	    cells.initialize();
    	    mainList = initializeSets(15);
    	    merge(10, 0, 15, mainList);
    	    merge(11, 0, 15, mainList);
    	    assertTrue(mainList.get(10).contains(10));
    	    assertTrue(mainList.get(10).contains(11));
    	    assertTrue(mainList.get(10).contains(12));
    	    assertFalse(mainList.get(10).contains(13));
    	    assertTrue(mainList.get(11).contains(13));
    }
    /**
     * Test method {MazeBuilderEller.deleteRightWalls}
     * We randomly delete some walls on the first row
     * there should be walls taken down, and sets should be merged where the walls are down
     */
    @Test
    public void testdeleteRightWalls() {
    	    Cells cells = new Cells(25,15);
 	    cells.initialize();
 	    mainList = initializeSets(15);
 	    deleteRightWalls(15, 0, cells);
 	    for (int i = 0; i < 14; i ++) {
 	    	    if (cells.hasNoWall(i, 0, CardinalDirection.East) == true) {
 	    	    	    for (int j = 0; j < mainList.size(); j ++) {
 	    	    	    	    if (mainList.get(j).contains(i)) {
 	    	    	    	    	    assertTrue(mainList.get(j).contains(i + 1));
 	    	    	    	    	    break;
 	    	    	    	    }
 	    	    	    }
 	    	    }
 	    }
    }
    /**
     * Test method {MazeBuilderEller.deleteDownWalls} with room
     * for a width = 25, height = 15 maze, we put a room at x in (5,10)
     * Then we delete walls on the first row at 0, 6 and 7. We call the mathod.
     * then cell 0 or 1 has no south wall. Cell 6 and 7 would have south wall, cell 7, 8, 9, 10 
     * should not have east wall.
     * ATTENTION: I changed the method encloseArea in class Cells to public,
     *  in order to build a border 
     */
    @Test
    public void testdeleteDownWalls() {
    	    Cells cells = new Cells(25,15);
 	    cells.initialize();
 	    cells.encloseArea(5, 1, 10, 3);
 	    mainList = initializeSets(25);
 	    Wall wall1 = new Wall(0,0,CardinalDirection.East);
 	    cells.deleteWall(wall1);
 	    merge(0, 0, 25, mainList);
 	    Wall wall2 = new Wall(6,0,CardinalDirection.East); 	    
	    cells.deleteWall(wall2);
	    merge(6, 0, 25, mainList);
	    Wall wall3 = new Wall(7,0,CardinalDirection.East);
 	    cells.deleteWall(wall3);
 	    merge(7, 0, 25, mainList);
 
 	    System.out.println(mainList);
 	    deleteDownWalls(0, 25, mainList, cells);
 	    System.out.println(mainList);
 	    if (cells.hasWall(0, 0, CardinalDirection.South)) {
 	    	    assertTrue(cells.hasNoWall(1, 0, CardinalDirection.South));
 	    }
 	   assertFalse(cells.hasNoWall(6, 0, CardinalDirection.South));
 	   assertFalse(cells.hasNoWall(7, 0, CardinalDirection.South));
 	   assertTrue(cells.hasNoWall(7, 0, CardinalDirection.East));
 	   assertTrue(cells.hasNoWall(8, 0, CardinalDirection.East));
 	  
 	   
    }
    /**
     * Test method {MazeBuilderEller.addandGenerateNewSets} with room
     * Following the former test, we call the method
     * set 0 should contain 20, 21, 22, 23, 24, 25
     * set 1 should contain 2,17
     * set 5 should contain 5, 6, 7, 8, 9, 10, 11, 26
     * set 8 should contain 14
     * set 9 should contain 15/16
     */
    @Test
    public void testaddandGenerateNewSets() {
    	    Cells cells = new Cells(25,15);
  	    cells.initialize();
  	    //cells.encloseArea(5, 1, 10, 3);
  	    for (int x = 5; x < 11; x ++) {
  	      	cells.setInRoomToOne(x, 1);
  	    }
  	    
  	    mainList = initializeSets(25);
  	    System.out.println(mainList);
  	    Wall wall1 = new Wall(0,0,CardinalDirection.East);
  	    cells.deleteWall(wall1);
  	    merge(0, 0, 25, mainList);
  	    Wall wall2 = new Wall(6,0,CardinalDirection.East);
 	    cells.deleteWall(wall2);
 	    merge(6, 0, 25, mainList);
 	    Wall wall3 = new Wall(10,0,CardinalDirection.East);
  	    cells.deleteWall(wall3);
  	    merge(10, 0, 25, mainList);
  	    deleteDownWalls(0, 25, mainList, cells);
  	    addandGenerateNewSets(0, 15, mainList, cells);	  	
	  	assertTrue(mainList.get(1).contains(17));
  	    
    } 
    /**
     * A comprehensive test
     */
    @Test
    public void testComprehensive() {
    	    Cells cells = new Cells(300, 200);
    	    cells.initialize();
    	    cells.markAreaAsRoom(5, 5, 4, 4, 9, 9);
    	    cells.markAreaAsRoom(40, 50, 35, 34, 75, 84);
    	    cells.markAreaAsRoom(25, 33, 105, 67, 130, 100);
    	    cells.markAreaAsRoom(40, 27, 167, 151, 207, 178);
    	    mainList = initializeSets(25);
    		for (int i = 0; i < 14; i ++) {
    			deleteRightWalls(25, i, cells);
    			deleteDownWalls(i, 25, mainList, cells);
    			addandGenerateNewSets(i, 25, mainList, cells);		
    			deleteNotUseful(i, 25, mainList);
    		}
    		dealingLastRow(25, 15, mainList, cells);
    		assertEquals(1, mainList.size());
    }
    
    
}
