/**
 * Represents a single maze as an object.
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public class Maze{
	//All the spaces in a two dimensional array
	private Space [][]grid;
	//The total number of rows and colums
	private int rows, cols;
	//The current row and column of the Space
	private int currentR, currentC;
	//Stack of the found free neighbors in the stack
	private SetOfSpacesStack freeNeighborsStack = new SetOfSpacesStack();
	//Stack of the solution spaces, to be printed to the file
	private SetOfSpacesStack solutionStack = new SetOfSpacesStack();
	//Stack of the found free neighbors in the queue
	private SetOfSpacesQueue freeNeighborsQueue = new SetOfSpacesQueue();
	//Queue of the solution spaces, to be printed to the file
	private SetOfSpacesQueue solutionQueue = new SetOfSpacesQueue();

	/**
	* Constructor that creates a maze and assigns the initial position
	* 
	* @param maze String containing the information of the maze
	* @param initialR Row number of the initial position
	* @param initialC Column number of the initial position
	* */
	Maze(String maze, int initialR, int initialC){
		//If the given maze is empty, display an error
		if(maze.length()==0) errorFound(0);

		//Convert the given string to a 2D array that represents the maze
		stringToGrid(maze);
		//If the initial position is not valid, display an error
		if(!setInitial(initialR, initialC)) errorFound(1);
	}
	/**
	* Sets the initial position to the given row and column in the maze grid
	* 
	* @param r The row number of the initial position
	* @param c The column number of the initial position
	* @return False if the initial position is not valid, true otherwise
	* */
	private boolean setInitial(int r, int c){
		//If the initial position is not free, return false
		if(grid[r][c].getType()!=SingleSpace.CORRIDOR) return false;
		//Create a space with the initial position at row r and column c
		grid[r][c]= new Space(SingleSpace.INITIAL, r, c);
		currentR = r;
		currentC = c;
		return true;
	}
	/**
	* Convert the string representing the maze into a maze represented by a grid
	* 
	* @param maze String containing the characters that represent the maze
	* 
	* */
	private void stringToGrid(String maze){
		//Convert the string to a grid line by line, then character by character in each line
		String[]lines = maze.split("\n");
		this.rows = lines.length;
		this.cols = lines[0].length();
		//Check that the maze is rectangular
		for(int r = 0;r<rows;r++){
			if(lines[r].length()!=cols)errorFound(3);
		}
		grid = new Space[rows][cols];

		for(int r=0; r<rows; r++){
			for(int c=0;c<cols;c++){
				switch(lines[r].charAt(c)){		
					case 'X':		
					case 'x': grid[r][c] = new Space(SingleSpace.WALL, r, c); break;
					case '.': grid[r][c] = new Space(SingleSpace.VISITED, r, c); break;
					case 'O':
					case 'o': grid[r][c] = new Space(SingleSpace.WAY_OUT, r, c); break;
					case 'E': grid[r][c] = new Space(SingleSpace.EXIT, r, c); break;
					case ' ': grid[r][c] = new Space(SingleSpace.CORRIDOR, r, c); break;
					default: errorFound(2);
				}
			}
		}
	}
	/**
	* Return the space at the position given
	* 
	* @param r Row number of the space that needs to be returned
	* @param c Column number of the space that needs to be returned
	* @return Space element at row r, column c
	* */
	public Space getSpace(int r, int c){
		return grid[r][c];
	}
	/**
	* Given a specific space element, it returns a set of spaces around it that are free.
	* 
	* @param currentSpace The space of which the neighbors need to be checked
	* @return Returns the set of free spaces neighboring in a stack structure
	* */
	public SetOfSpacesStack getFreeNeighborsStack(Space currentSpace){
		//Get the row and column number of the current space
		int row = currentSpace.getRow();
		int col = currentSpace.getCol();

		//For each of the surrounding spaces, add the free ones to the stack
		if(getSpace(row-1, col).getType()==SingleSpace.CORRIDOR || getSpace(row-1, col).getType()==SingleSpace.WAY_OUT)
			freeNeighborsStack.add(getSpace(row-1, col));
		if(getSpace(row, col+1).getType()==SingleSpace.CORRIDOR || getSpace(row, col+1).getType()==SingleSpace.WAY_OUT)
			freeNeighborsStack.add(getSpace(row, col+1));
		if(getSpace(row+1, col).getType()==SingleSpace.CORRIDOR || getSpace(row+1, col).getType()==SingleSpace.WAY_OUT)
			freeNeighborsStack.add(getSpace(row+1, col));
		if(getSpace(row, col-1).getType()==SingleSpace.CORRIDOR || getSpace(row, col-1).getType()==SingleSpace.WAY_OUT)
			freeNeighborsStack.add(getSpace(row, col-1));

		return freeNeighborsStack;
	}
	/**
	* Finds the exit from a given Space, if found, returns true otherwise it returns false
	* 
	* @param currentSpace The space that is being focused on, the one from which it finds the exit
	* @return True if an exit is found, false if not (the stack is empty)
	* */
	public boolean findExitStack(Space currentSpace){
		//If the space has not been visited, add it to the solution
		if(currentSpace.getType()!=SingleSpace.VISITED)
			solutionStack.add(currentSpace);
		//If the space is a way out, convert it into an exit and return true because it has been found
		if(currentSpace.getType()==SingleSpace.WAY_OUT){
			currentSpace.setType(SingleSpace.EXIT);
			return true;
		}
		//Find the free neighbors of the current Space
		freeNeighborsStack = getFreeNeighborsStack(currentSpace);
		for(int i=0;i<freeNeighborsStack.size();i++){
			//System.out.println(freeNeighbors);
			//Change the current space to visited
			if(currentSpace.getType()==SingleSpace.CORRIDOR)
				currentSpace.setType(SingleSpace.VISITED);
			//If there are free neighbors
			if(freeNeighborsStack.size()!=0){
				//Remove the next space from the stack
				Space nextSpace = freeNeighborsStack.remove();
				//Print the maze, aesthetic step
				System.out.println(toString());
				//Wait to print the next step (it is necessary here due to recursion)
				try{Thread.sleep(200);}
				catch(InterruptedException e){}
				//"Clear" the screen
				for(int s=0;s<30;s++) System.out.println();
				//Display the stack of free neighbors, again, aesthetic purposes
				System.out.println(freeNeighborsStack);
				//Use recursion! Finding the exit from the next space
				findExitStack(nextSpace);
			}
			return true;
		}
		return false;
	}
	/**
	* Returns the solution stack with all the spaces that have been visited
	* 
	* @return The stack containing the solution
	* */
	public SetOfSpacesStack getSolutionStack(){
		return solutionStack;
	}
	/**
	* Given a specific space element, it returns a set of spaces around it that are free.
	* 
	* @param currentSpace The space of which the neighbors need to be checked
	* @return Returns the set of free spaces neighboring in a queue structure
	* */
	public SetOfSpacesQueue getFreeNeighborsQueue(Space currentSpace){
		//Get the row and column number of the current space
		int row = currentSpace.getRow();
		int col = currentSpace.getCol();

		//For each of the surrounding spaces, add the free ones to the stack
		if(getSpace(row-1, col).getType()==SingleSpace.CORRIDOR || getSpace(row-1, col).getType()==SingleSpace.WAY_OUT)
			freeNeighborsQueue.add(getSpace(row-1, col));
		if(getSpace(row, col+1).getType()==SingleSpace.CORRIDOR || getSpace(row, col+1).getType()==SingleSpace.WAY_OUT)
			freeNeighborsQueue.add(getSpace(row, col+1));
		if(getSpace(row+1, col).getType()==SingleSpace.CORRIDOR || getSpace(row+1, col).getType()==SingleSpace.WAY_OUT)
			freeNeighborsQueue.add(getSpace(row+1, col));
		if(getSpace(row, col-1).getType()==SingleSpace.CORRIDOR || getSpace(row, col-1).getType()==SingleSpace.WAY_OUT)
			freeNeighborsQueue.add(getSpace(row, col-1));
		return freeNeighborsQueue;
	}
	/**
	* Finds the exit from a given Space, if found, returns true otherwise it returns false
	* 
	* @param currentSpace The space that is being focused on, the one from which it finds the exit
	* @return True if an exit is found, false if not (the queue is empty)
	* */
	public boolean findExitQueue(Space currentSpace){
		//If the space has not been visited, add it to the solution
		if(currentSpace.getType()!=SingleSpace.VISITED)
			solutionQueue.add(currentSpace);
		//If the space is a way out, convert it into an exit and return true because it has been found
		if(currentSpace.getType()==SingleSpace.WAY_OUT){
			currentSpace.setType(SingleSpace.EXIT);
			return true;
		}
		//Find the free neighbors of the current Space
		freeNeighborsQueue = getFreeNeighborsQueue(currentSpace);
		for(int i=0;i<freeNeighborsQueue.size();i++){
			//System.out.println(freeNeighbors);
			//Change the current space to visited
			if(currentSpace.getType()==SingleSpace.CORRIDOR)
				currentSpace.setType(SingleSpace.VISITED);
			//If there are free neighbors
			if(freeNeighborsQueue.size()!=0){
				//Remove the next space from the queue
				Space nextSpace = freeNeighborsQueue.remove();
				//Print the maze, aesthetic step
				System.out.println(toString());
				//Wait to print the next step (it is necessary here due to recursion)
				try{Thread.sleep(200);}
				catch(InterruptedException e){}
				//"Clear" the screen
				for(int s=0;s<30;s++) System.out.println();
				//Display the queue of free neighbors, again, aesthetic purposes
				System.out.println(freeNeighborsQueue);
				//Use recursion! Finding the exit from the next space
				findExitQueue(nextSpace);
			}
			return true;
		}
		return false;
	}
	/**
	* Gets the solution in the queue format
	* 
	* @return A set of spaces in the format of the queue
	* */
	public SetOfSpacesQueue getSolutionQueue(){
		return solutionQueue;
	}
	//Method that manages errors, depending on a certain index
	private void errorFound(int n){
		switch(n){
			case 0:
				System.out.println("Maze is empty");				
				break;
			case 1:
				System.out.println("Initial position invalid");
				break;
			case 2:
				System.out.println("Invalid character in maze");
				break;
			case 3:
				System.out.println("All the rows should have the same number of columns");
		}
		System.exit(0);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer result= new StringBuffer("");
		for(int r=0;r<rows;r++){
			for(int c=0;c<cols;c++){
				result.append(grid[r][c].getType().toString());
			}
			result.append("\n");
		}
		return result.toString();
	}
}