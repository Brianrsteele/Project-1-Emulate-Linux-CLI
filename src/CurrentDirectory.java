/**
 * 
 */

/**
 * @author briansteele
 *
 */


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CurrentDirectory {
	
	private Path currentWorkingDirectory;
	
	
	public CurrentDirectory(Path path) {
		this.currentWorkingDirectory = path;
	}
	
	
	
	/**
	 * @return the currentWorkingDirectory
	 */
	public Path getCurrentWorkingDirectory() {
		return this.currentWorkingDirectory;
	}

	/**
	 * @param currentWorkingDirectory the currentWorkingDirectory to set
	 */
	public void setCurrentWorkingDirectory(String newDirectory) {
		this.currentWorkingDirectory = Paths.get(newDirectory);
	}
	
	public String toString() {
		return this.currentWorkingDirectory.toString();
	}
	
	public void upOneDirectory() {
		this.currentWorkingDirectory = this.currentWorkingDirectory.getParent();
	}

	
	public void nextDirectory(Path path) {
		this.currentWorkingDirectory = 
				this.currentWorkingDirectory.resolve(path);
	}
		
	
	
	public Boolean checkFileSystemExists() {
			return Files.isDirectory(this.currentWorkingDirectory);
		}
	
	
	

}
