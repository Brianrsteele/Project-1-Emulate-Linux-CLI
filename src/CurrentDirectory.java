import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * CurrentDirectory class acts as a placeholder for the string of the current working
 * directory. This allows multiple threads to share and set information about the location
 * of the current working directory. 
 * 
 * @author briansteele
 *
 */
public class CurrentDirectory {
	
	// Path object to hold the current working directory
	private Path currentWorkingDirectory;
	
	/*
	 * constructor for CurrentWorkingDirectory
	 * 
	 * @param path A Path object holding the location of the users home directory.
	 */
	public CurrentDirectory(Path path) {
		this.currentWorkingDirectory = path;
	}
	
	
	
	/**
	 * Get the current working directory
	 * 
	 * @return the currentWorkingDirectory
	 */
	public Path getCurrentWorkingDirectory() {
		return this.currentWorkingDirectory;
	}

	/**
	 * Set the current working directory
	 * 
	 * @param currentWorkingDirectory String of the currentWorkingDirectory to set
	 */
	public void setCurrentWorkingDirectory(String newDirectory) {
		this.currentWorkingDirectory = Paths.get(newDirectory);
	}
	
	
	/*
	 *  return a string of the current working directory
	 *  
	 *  @return string of path to current working directory
	 */
	public String toString() {
		return this.currentWorkingDirectory.toString();
	}
	
	/*
	 * remove the last directory from the end of the string of the path...
	 */
	
	public void upOneDirectory() {
		this.currentWorkingDirectory = this.currentWorkingDirectory.getParent();
	}

	
	/*
	 * Add another directory to the end of the string of the path
	 * 
	 * @param path A path object with the absolute path to the next directory
	 */
	public void nextDirectory(Path path) {
		this.currentWorkingDirectory = 
				this.currentWorkingDirectory.resolve(path);
	}
		
	
	/*
	 * Make sure the file or directory exists
	 * 
	 *  @return true of the file exists, false if it doesn't
	 */
	public Boolean checkFileSystemExists() {
			return Files.isDirectory(this.currentWorkingDirectory);
		}
	
	
	

}
