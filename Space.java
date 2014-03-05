/**
 * Represents each space of the maze. It contains the type of space and its coordinates
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public class Space{
	private SingleSpace type;
	private int row;
	private int col;

	/**
	* Constructor for the class Space. It receives all the necessary information to create a space
	*
	*@param type Describes the type of space
	*@param row Indicates the row of the Space
	*@param col Indicates the column of the Space
	*@see SingleSpace
	*/
	public Space(SingleSpace type, int row, int col){
		this.type = type;
		this.row=row;
		this.col=col;
	}
	/**
	* Returns an integer representing the row of the space
	* @return the integer that represents the row
	*/
	public int getRow(){
		return row;
	}
	/**
	* Returns the type of this space element
	* 
	* @return The type of the space
	* @see SingleSpace
	*/
	public SingleSpace getType(){
		return type;
	}/**
	* Sets the type of the space
	* 
	* @param type The new type of this space
	*/
	public void setType(SingleSpace type){
		this.type = type;
	}
	/**
	* Returns the column number of this space
	* 
	* @return Represents the column of this space
	* */
	public int getCol(){
		return col;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return row+" "+col;
	}
}