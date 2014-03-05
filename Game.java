import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
/**
 * Driver class for solving a given maze using stacks and queues
 * 
 * @author Carlos Guzman
 * @version Feb 21, 2014 
 *
 *
 */
public class Game{
	public static void main(String[] args) throws Exception{
		//Read the file that contains the maze
		File inputFile = new File(args[0]);

		//Check that the number of arguments is correct
		if(args.length!=3){
			System.out.println("Wrong number of arguments");
			System.exit(0);
		}

		//Check that the file exists and is readable, otherwise stop the program
		if(!(inputFile.exists() && inputFile.canRead())){
			System.err.println("The file does not exist or is not readable");
			System.exit(0);
		}
		//Make the file useful for the program using Scanner class
		Scanner input = new Scanner(inputFile);
		
//--------------------------------------------------------------------------
		//Separate the initial position from the rest of the data
		String[] initial=input.nextLine().split(" ");

		//Check that the initial position is made up of integers
		try { 
        	Integer.parseInt(initial[0]);
        	Integer.parseInt(initial[1]);

    	} catch(NumberFormatException e) { 
    		System.out.println("Please enter numbers for the initial position coordinates in the first line of the input.");
        	System.exit(0); 
    	}

		//New String variable where the maze will be saved. Since it will be increasing, make it StringBuffer
		StringBuffer initialMaze = new StringBuffer();
		
		//Check that the input is not at the end of the file
		while(input.hasNextLine()){
			//Add a row to the string variable
			initialMaze.append(input.nextLine()+"\n");
		}
		//Close the input file because it will not be used anymore
		input.close();
		//Declare and initialize the maze objects for stack and queue
		Maze mazeStack = new Maze(initialMaze.toString(), Integer.parseInt(initial[0]), Integer.parseInt(initial[1]));
		Maze mazeQueue = new Maze(initialMaze.toString(), Integer.parseInt(initial[0]), Integer.parseInt(initial[1]));
//----------------------------------------------------------------------

		//Check if the maze could be solved, while computing the solution for the stack and the queue at the same time
		//Check if the maze has an exit, not just a way out to determine whether it has been solved
		if(mazeStack.findExitStack(new Space(SingleSpace.INITIAL, Integer.parseInt(initial[0]), Integer.parseInt(initial[1]))) 
			&& mazeQueue.findExitQueue(new Space(SingleSpace.INITIAL, Integer.parseInt(initial[0]), Integer.parseInt(initial[1])))
			&& mazeStack.toString().contains(SingleSpace.EXIT.toString())
			&& mazeQueue.toString().contains(SingleSpace.EXIT.toString())){
			System.out.println("The maze has been solved!");
			
		}
		else
			System.out.println("The maze cannot be solved.");

		//Create two different files for the outputs of the stack and the queue
		File stackFile = new File(args[1]);
		File queueFile = new File(args[2]);

		//Check that the file does not already exist
		if(stackFile.exists()){
			System.err.println("The output file for the stack already exists.");
		}else{
			//Print the solution onto the file
			PrintWriter outputStack = new PrintWriter(stackFile);
			outputStack.print(mazeStack.getSolutionStack());
			outputStack.close();
		}
		//Print the final result of the maze, for aesthetic purposes
		System.out.println("Stack\n"+mazeStack);

		//Check that the file does not already exist
		if(queueFile.exists()){
			System.err.println("The output file for the queue already exists.");
		}else{
			//Print the solution onto the file
			PrintWriter outputQueue = new PrintWriter(queueFile);
			outputQueue.print(mazeQueue.getSolutionQueue());
			outputQueue.close();
		}
		//Print the final result of the maze, for aesthetic purposes
		System.out.println("Queue\n"+mazeQueue);

	}
}