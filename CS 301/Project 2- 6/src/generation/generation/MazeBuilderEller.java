package generation;


import java.util.*;
/**
 * This class uses Eller's algorithm to generate a maze with given width and 
 * height. It's merge has a performance of O(n), which considered quicker than 
 * other algorithms. It randomly deletes east walls that can be deleted (is not 
 * a border), then merge the set of the two cells, and generate a new cell beneath
 * a cell in each set, in our case, delete the south wall that can be deleted. 
 * Repeat the same from the first row to the second last row. At the last row we join
 * all the cells to ensure that all cells can lead to exit.
 * 
 * @author ShawnSun
 *
 */
public class MazeBuilderEller extends MazeBuilder implements Runnable {
	//make a list for holding sets
	public List<Set<Integer>> mainList = new ArrayList<Set<Integer>>();	
	//make a 2d array copy of the maze
	public int [][] MazeCopy;
	//the set going to be merged;
	public Set<Integer> setToAdd;
	//The list for randomly deleting vertical walls
    public ArrayList<Integer> list;
    //The list for randomly deleting horizontal walls
    public ArrayList<Integer> list2;
    //The walls that is going to be deleted
    Wall wall;
    boolean deletable;
	
    public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	 
	public MazeBuilderEller(boolean det) {
		super(det);
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	 
	/**
	 * generatePathWays contains all the method that is going to be used to build the maze
	 */
	@Override
	protected void generatePathways() {		
		mainList = initializeSets(width);
		for (int i = 0; i < height - 1; i ++) {
			deleteRightWalls(width, i, cells);
			deleteDownWalls(i, width, mainList, cells);
			addandGenerateNewSets(i, width, mainList, cells);		
			deleteNotUseful(i, width, mainList);
		}
		dealingLastRow(width, height, mainList, cells);
		
    }
		
	
    
	
	/**
	 * initialize the list that has all the sets
	 * @param height - easier for testing
	 * @return the list
	 */
	protected List<Set<Integer>> initializeSets(int width) {
		for (int i = 0; i < width; i ++) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(i);
			mainList.add(set);
		}
		return mainList;
	}
	/**
	 * randomly delete east walls that can be deleted in a row, and merge the sets after deletion
	 * @param height - easier for testing
	 * @param rowNumber - easier for testing
	 * @param cells - easier for testing
	 */
	protected void deleteRightWalls(int width, int rowNumber, Cells cells) {
		for (int j = 0; j < mainList.size(); j++) {
			if (mainList.get(j).isEmpty()){
				mainList.remove(j);
				j -= 1;
			}
		}
		int r1 = random.nextIntWithinInterval(0, width - 2);
		for ( int i = 0; i < r1; i ++) {
			
			int r2 = random.nextIntWithinInterval(0, width - 2);
			wall = new Wall(r2, rowNumber, CardinalDirection.East);
			if (cells.canGo(wall)) {
				cells.deleteWall(wall);
				merge(r2, rowNumber, width, mainList);
			}
			
		}
	}
	
	
	/**
	 * merge the adjacent cells where the wall in between has been deleted
	 * @param i
	 * @param rowNumber
	 * @param height
	 * @param mainList
	 */
	protected void merge(int i, int rowNumber, int width, List<Set<Integer>> mainList) {
		int deleted = rowNumber * width + i;
		int tobemerged = deleted + 1;
		loop:
		for (int j = 0; j < mainList.size(); j ++) {
			if (mainList.get(j).contains(deleted)) {    			
				for (int k = 0 ;k < mainList.size(); k++) {
					if (mainList.get(k).contains(tobemerged)) {
						mainList.get(j).addAll(mainList.get(k));   //merge the adjacent sets		 
					    if (j != k) {mainList.remove(mainList.get(k));}
					    break loop;
					}
				}
			}
		}	
	}
	/**
	 * randomly select a cell from each cell to delete its south wall
	 * if all cells on a particular row cannot delete their south walls, 
	 * that is to say, all cells' south cells are in the room, 
	 * then randomly select one cell from the list to try to delete its other walls 
	 * rather than south wall, and merge with its adjacent cell. Then check if 
	 * the set now can have at least one cell can delete south wall;
	 * If after this deletion it still is disjointed, repeat deletion.
	 * @param rowNumber - easier for testing
	 * @param height - easier for testing
	 * @param mainList - easier for testing
	 * @param cells - easier for testing
	 */
	protected void deleteDownWalls(int rowNumber, int width, List<Set<Integer>> mainList, Cells cells){
		boolean southwalldeletable;
		int deleted;
		int r;
		//check if any cell in each set can delete down wall
		for (int i = 0; i < mainList.size(); i ++) {
			list2 = new ArrayList<Integer>(mainList.get(i));
			southwalldeletable = checkDeletable(width, rowNumber, list2, cells);

			if (southwalldeletable) {
				//random until we find the cell that we are going to delete its south wall
				r = random.nextIntWithinInterval(0, list2.size()-1);
				while (list2.get(r) < rowNumber * width) {
					r = random.nextIntWithinInterval(0, list2.size()-1);
				}
				wall = new Wall(list2.get(r) % width, rowNumber, CardinalDirection.South);
				while (!cells.canGo(wall)) {
					r = random.nextIntWithinInterval(0, list2.size()-1);
					wall = new Wall(list2.get(r) % width, rowNumber, CardinalDirection.South);
				} 
				wall = new Wall(list2.get(r) % width, rowNumber, CardinalDirection.South);
				
				cells.deleteWall(wall);
			}
			//Some crazy solving method; if we meet an infinite loop, just ignore it.
			//The probability of infinite loop is under reasonable range, so I suppose
			//it is ok to do so.
			for(int count = 0; count < 100 && southwalldeletable == false; count ++) {
				r = random.nextIntWithinInterval(0, list2.size()-1);
				Wall wall = new Wall(list2.get(r) % width, rowNumber, CardinalDirection.East);
				for (CardinalDirection cd : CardinalDirection.values()) {
					//delete north wall is of no meaning because we don't need those cells any more
					//and we don't store any info about them
					if (cd == CardinalDirection.North) { 
						continue;
					}
					if (cells.hasWall(list2.get(r) %width, rowNumber, cd)) {
						wall.setWall(list2.get(r) % width, rowNumber, cd);
						if (cells.canGo(wall)) {
							cells.deleteWall(wall);				
							deleted = rowNumber * width + list2.get(r) % width;
							
							loop:
							//merge sets
							for (int j = 0; j < mainList.size(); j ++) {
								if (mainList.get(j).contains(deleted)) {    			
									for (int k = 0 ;k < mainList.size(); k++) {
										if (mainList.get(k).contains(deleted + cd.getDirection()[0])) {
											mainList.get(k).addAll(mainList.get(j));   //merge the adjacent sets		 
										    if (j != k) {
										      	mainList.remove(mainList.get(j));								  
										      	i -= 1; //after remove the elements in the list is moved forward
										      	if (i < 0) {
										      		i = -1; //in case the first cell of the row merges lots of times
										      	}
										    }							    
										    break loop;
										}
									}
								}
							}
						}
						// if the cell's east, west, south all have borders, we have to open a door,
						// even though this may lead to more than 5 doors. The probability is also low enough.
						else {
							if (list2.get(r) % width != 0 && cd == CardinalDirection.West) {
								cells.deleteWall(wall);
							}
							if (list2.get(r) % width != width - 1 && cd == CardinalDirection.East) {
								cells.deleteWall(wall);
							}
						}
						//check if the set has a cell that can delete south wall
						for (int j = 0; j < mainList.size(); j ++) {
	                	       if (mainList.get(j).contains(list2.get(r))) {
	                	    	     list2 = new ArrayList<Integer>(mainList.get(j));                	    	     
	                	    	      
	                	       }
						}
	                	    	southwalldeletable = checkDeletable(width, rowNumber, list2, cells);
	                	    	if (southwalldeletable) {
							 
						}	
					}	
				}
			}
		}
	}
	
	/**
	 * check if all cells on this row cannot delete their south walls.
	 * return false if all cannot; return true if some of them can.
	 */
	protected boolean checkDeletable(int width, int rowNumber, ArrayList<Integer> list, Cells cells) {
		deletable = false;
		for (int i = 0 ; i < list.size(); i ++) {			
			if (list.get(i) >= rowNumber * width && list.get(i)< (rowNumber + 1) * width) {
				if (cells.hasWall(list.get(i) % width, rowNumber, CardinalDirection.South)) {
					wall = new Wall(list.get(i) % width, rowNumber, CardinalDirection.South);
				}
				deletable = deletable || cells.canGo(wall);	
			}	
		}
		return deletable;
	}
	

	/**
	 * iterate the row. If the cell doesn't have south wall, which means the south cell is 
	 * connected to the maze, we add it to the set; if the cell has south wall, which means 
	 * the south cell is not connected, we create a new set for the south cell and add the 
	 * set to the list.
	 * @param rowNumber - easier for testing
	 * @param height - easier for testing
	 * @param mainList - easier for testing
	 * @param cells - easier for testing
	 */
	protected void addandGenerateNewSets(int rowNumber, int width, List<Set<Integer>> mainList, Cells cells){
		for (int i = 0; i < width; i ++) {
			if (cells.hasNoWall(i, rowNumber, CardinalDirection.South)) {
				for (int j = 0; j < mainList.size(); j ++) {
					if (mainList.get(j).contains(width * rowNumber + i)) {
						mainList.get(j).add(width * (rowNumber + 1) + i);
						 
					}
				}				
			}
			else {
					setToAdd = new HashSet<Integer>();
					setToAdd.add(width * (rowNumber + 1 ) + i);
					mainList.add(setToAdd);
				}
		}
	}
	/**
	 * 
     * After we are done with one row, we do not need them anymore; deleting them also 
     * make our next round of deleting walls easier, and guarentee we have merge at O(n)
     * performance
	 * @param rowNumber
	 * @param height
	 * @param mainList
	 */
	protected void deleteNotUseful(int rowNumber, int width, List<Set<Integer>> mainList) {
		for (int i = 0; i < mainList.size(); i++) {
			for (int j = 0; j < width; j++) {
			    if (mainList.get(i).contains(width * rowNumber + j)) {
			    	    mainList.get(i).remove(width * rowNumber + j);
			    }
		    }
		}
	}
	
	
	/**
	 * at the very last row, we iterate the row. If any cell is in a different set of 
	 * its right cell, we delete the wall and merge the sets
	 * @param height - easier for testing
	 * @param width - easier for testing
	 * @param mainList - easier for testing
	 * @param cells - easier for testing
	 */
	protected void dealingLastRow(int width, int height, List<Set<Integer>> mainList, Cells cells) {
		
		for (int i = 0; i < width - 1; i ++) {
	    	    for (int j = 0; j < mainList.size(); j ++) {
	    	    	    if (mainList.get(j).contains((height - 1) * width + i)){
	    	    	    	    // if not in the same cell, delete right wall
	    	    	    	    if (!mainList.get(j).contains((height - 1) * width + i + 1)){
	    	    	    	    	    Wall wall = new Wall(i, height - 1, CardinalDirection.East);
	    	    	    	    	    wall.setWall(i, height - 1, CardinalDirection.East);    	    	    	    	    
	    	    				cells.deleteWall(wall);
	    	    				merge(i, height - 1, width, mainList);
	    	    				 
	    	    	    	    }
	    	    	    }
	    	    }
		}	
	}
}
