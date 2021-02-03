import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 */

/**
 * @author briansteele
 *
 */
public class Command implements Runnable {

	private String theCommand;
	private String commandArray[];
	private int argumentsCount;
	CurrentDirectory cwd;

	
	
	public Command (String c, CurrentDirectory path) {
		theCommand = c;
		commandArray = theCommand.split(" ");
		argumentsCount = commandArray.length;
		this.cwd = path;
		
	}
	
	public void run() {
		// code to handle what the command says to do
		
		
		
		switch (commandArray[0].trim()) {
		
		case "cd":
			if (commandArray.length == 1) {
					break;
			} else {
				String argument = commandArray[1];
				String [] directories = argument.split("/");
				int numberOfFolders = directories.length;
				
				for (int i = 0; i <directories.length; i++) {
					System.out.println(directories[i]);
					
//					switch (directories[i]) {
//					
//					case ".":
//						break;
//					case "..":
//						numberOfFolders -= 1;
//						break;
//					default:
//						break;
//					}
//					
//					String updatedArgument = "";
//					for(int j =0; j < numberOfFolders; j++) {
//						updatedArgument.concat(directories[j]);
//					}
//					
//					argument = updatedArgument;
//					System.out.println(argument);
				}
				
				
				if (checkFileSystemExists(argument) == true) {
					cwd.setCurrentWorkingDirectory(argument);
					System.out.println(cwd);
				} else {
					System.out.println("-bash: cd: " 
							+ argument + ": No such file or directory");
				}
				
			}
			
			break;
		case "pwd":
			System.out.println(cwd.getCurrentWorkingDirectory());
			break;
		case "ls":
			
			

					File f = new File(cwd.getCurrentWorkingDirectory());
					String contents [] = (f.list());
					
					for (int i = 0; i < contents.length; i++) {
						System.out.println(contents[i]);
					}
			
			
			
			
			
			break;
		case "cat":
			System.out.println("I'm a cat command");
			break;
		case "cmp":
			System.out.println("I'm a cmp command");
			break;
		case "sort":
			System.out.println("I'm a sort command");
			break;
		case "exit":
			System.exit(0);
			break;
		default:
			System.out.println(commandArray[0] + ": command not found");
		
		
		
		}
		
		
		
		
		
		
	}
	
	private Boolean checkFileSystemExists(String location) {
		Path path = Paths.get(location);
		System.out.println(path);
		return Files.isDirectory(path);		
	}
	
}
