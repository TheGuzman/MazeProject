/**
 * Represents the type of a single space in the maze.
 * This could be a wall, a corridor, an exit...
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public enum SingleSpace{
	INITIAL('*'),
	WALL('X'),
	CORRIDOR(' '),
	VISITED('.'),
	WAY_OUT('O'),
	EXIT('E');

	private char type;
	/**
	* Constructor of the class that receives a character indicating the type of the space.
	* 
	* @param type Represents the character that identifies the current space
	* */
	SingleSpace(char type){
		this.type=type;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return type+"";
	}
}