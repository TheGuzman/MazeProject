/**
 * This class represents an interface for the set of spaces that will be implemented in the form of a Stack or a Queue
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public interface SetOfSpaces{
	void add (Space s);
	Space remove();
	boolean isEmpty();
}