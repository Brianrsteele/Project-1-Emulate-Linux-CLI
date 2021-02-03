/**
 * 
 */

/**
 * @author briansteele
 *
 */
public class CurrentDirectory {
	
	private String currentWorkingDirectory;
	
	
	public CurrentDirectory(String path) {
		this.currentWorkingDirectory = path;
	}
	
	
	
	/**
	 * @return the currentWorkingDirectory
	 */
	public String getCurrentWorkingDirectory() {
		return currentWorkingDirectory;
	}

	/**
	 * @param currentWorkingDirectory the currentWorkingDirectory to set
	 */
	public void setCurrentWorkingDirectory(String currentWorkingDirectory) {
		this.currentWorkingDirectory = currentWorkingDirectory;
	}
	
	public String toString() {
		return this.currentWorkingDirectory;
	}

	
	
	

}
