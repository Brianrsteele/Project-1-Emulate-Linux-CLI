
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * The CommandPrompt class contains the main() method for this project.
 * 
 * @author briansteele
 *
 */

public class CommandPrompt {
	static Scanner console = new Scanner(System.in);
	static String commandInput;
	static int numberOfCommands = 0;
	static String commandStringArray[];
	// the current users home directory
	static String userHome = System.getProperty("user.home");
	static Path initialPath = Paths.get(userHome);
	
	static CurrentDirectory cwd = new CurrentDirectory(initialPath);

	/**
	 * main() runs the command line simulation
	 * 
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		while (true) {
			
			//print shell prompt and accept input
		    System.out.print("myshell> ");
		    commandInput = console.nextLine();
		    commandInput = commandInput.replaceAll("\\s+", " ");
		      
		    
		    //Split commands into an array
		    commandStringArray = commandInput.split(";");
		    numberOfCommands = commandStringArray.length;
		      
		    
		     //Create array to hold that many threads
		     Thread threadList[] = new Thread[numberOfCommands];
		     
		     for (int i=0; i < numberOfCommands; i++) {
		        String command = commandStringArray[i].trim();
		        
		        		
		        /* Create thread and store in array */
		        threadList[i] = new Thread(new Command(command, cwd));
		        //Fire the next thread off to execute c
		        threadList[i].start();  
		        
		     }

		      /* Wait for threads to finish before prompting again */
		     
	    	 for (int i=0; i < numberOfCommands; i++) {
	    		 threadList[i].join();
	    	 }
	    	 
		     
		      
		   }

	}

}
