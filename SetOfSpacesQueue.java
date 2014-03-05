/**
 * Set of spaces that behaves like a queue implemented using an array
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 */
public class SetOfSpacesQueue implements SetOfSpaces{

	private Space [] list;
	private int start = 0;
	private int end = 0;
	private int capacity;
	private static int DEFAULT_CAPACITY = 16;

	/**
	* Constructor that takes an specified capacity and creates a queue
	* 
	* @param capacity The total capacity of the array that contains the queue
	* */
	public SetOfSpacesQueue(int capacity){
		//Check that the capacity is a valid value
		//If not, use the default value for it
		if(capacity<=0) capacity = DEFAULT_CAPACITY;
		//Initialize the array that contains the queue
		list = new Space[capacity];

		//Specify the beginning and the end of the queue in the array.
		start = 0;
		end = 0;
		//Keep track of the capacity of the array.
		this.capacity = capacity;
	}
	/**
	* Constructor when no capacity is specified.
	* It calls the previous constructor with the default capacity
	* */
	public SetOfSpacesQueue(){
		this(DEFAULT_CAPACITY);
	}
	/**
	* Adds a Space to the queue. If the array is at its maximum and the queue occupies more than half of it, make it larger.
	* If the array is at its maximum but the queue occupies less than half of the array, just trim the list.
	* 
	* @param item The Space element that is going to be added
	* */
	@Override
	public void add(Space item){
		//Check whether we are at the end of the array
		if(end==capacity){
			//Check if the size of the queue is greater than half of the capacity
			if((end-start)*2>capacity)
				//If so, make it larger
				makeLarger();
			else
				//Otherwise, trim the queue
				trimList();
		}
		//Check that the Space is not null, it has a value
		if(item!=null){
			//Add it to the end of the queue.
			list[end] = item;
			end++;
		}
	}
	/**
	* Removes a space from the queue and returns its value
	* 
	* @return The space element that has been removed
	* */
	@Override
	public Space remove(){
		//If the queue is empty, no element can be removed, so it returns null
		if(isEmpty()){
			return null;
		}else{
			//If the queue is not empty, remove an element from the beginning of the queue.
			start++;
			return list[start-1];
		}
	}
	/**
	* Checks if the queue is empty
	* 
	* @return Returns true if the queue is empty, false if it is not
	* */
	@Override
	public boolean isEmpty(){
		//if the beginnning is the same as the end, the queue is empty
		return (start==end)?true:false;
	}
	/**
	* Returns the size of the queue
	* 
	* @return The size of the queue
	* */
	public int size(){
		return end-start;
	}
	//Make the array larger
	private void makeLarger ()
	{
		//allocate larger array
		Space [] newList = new Space [capacity * 2 ];
		//copy the data over to the new array, at the beginning, for efficiency
		for (int i = start; i < end; i++)
		{
			newList[i-start] = list[i];
		}
		//reset list reference to the new array
		list = newList;
		//reset the capacity to the new value
		capacity = 2*capacity;
		end-=start;
		start=0;
	}
	//Trim the list, move the queue towards the beginning of the array
	private void trimList(){
		//This variable keeps track of where we are copying th current element to.
		int n = start;
		//For every element of the
		for(int i=0;i<end-start;i++){
			list[i] = list[n];
			n++;
		}
		//Set the new start and end values to the first elements of the array
		end -= start;
		start = 0;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "";
		//Add every element of the queue to the string to return
		for(int i =start;i<end;i++){
			s += list[i]+"\n";
		}
		return s;
	}
}