/**
 * Set of spaces that behaves like a stack implemented using an array
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public class SetOfSpacesStack implements SetOfSpaces{

	private Space [] list;
	private int size;
	private int capacity;
	private static int DEFAULT_CAPACITY = 16;

	/**
	* Constructor that takes an specified capacity and creates a stack
	* 
	* @param capacity The total capacity of the array that contains the stack
	* */
	public SetOfSpacesStack(int capacity){
		//Check that the capacity is a valid value
		//If not, use the default value for it
		if(capacity<=0) capacity = DEFAULT_CAPACITY;

		//Initialize the array that contains the stack
		list = new Space [capacity];

		//Specify the size of the stack (also where the next element to add should be) in the array.
		size = 0;

		//Keep track of the capacity of the array.
		this.capacity = capacity;
	}
	/**
	* Constructor when no capacity is specified.
	* It calls the previous constructor with the default capacity
	* */
	public SetOfSpacesStack(){
		this(DEFAULT_CAPACITY);
	}
	/**
	* Adds a Space to the top of the stack.
	* 
	* @param item The Space element that is going to be added
	* */
	@Override
	public void add(Space item){

		//if the stack is full, allocate a larger array
		if (  size == capacity )
			makeLarger();
		//add the new value to the top of the stack
		if (item != null) {
			list[size] = item;
			size++;
		}
	}

	/**
	* Removes the top space from the stack and returns its value
	* 
	* @return The space element that has been removed
	* */
	@Override
	public Space remove() {

		//if stack is empty return null reference
		if ( size == 0 ) return null;
		//otherwise remove and return character from the top of the stack
		else {
			size--;
			return list[size];
		}
	}
	/**
	* Checks if the stack is empty
	* 
	* @return Returns true if the stack is empty, false if it is not
	* */
	@Override
	public boolean isEmpty(){
		return (size==0)?true:false;
	}
	/**
	* Returns the size of the stack
	* 
	* @return The size of the stack
	* */
	public int size(){
		return size;
	}
	/*
	 * Allocates an array twice the size of the current array used for 
	 * storing the stack and copies all the data to the new array.
	 */
	private void makeLarger ()
	{
		//allocate larger array
		Space [] newList = new Space [capacity * 2 ];
		//copy the data over to the new array
		for (int i = 0; i < capacity; i++)
		{
			newList[i] = list[i];
		}
		//reset list reference to the new array
		list = newList;
		//reset the capacity to the new value
		capacity = 2*capacity;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "";
		//Add every element of the queue to the string to return
		for(int i =0;i<size;i++){
			s += list[i]+"\n";
		}
		return s;
	}
}