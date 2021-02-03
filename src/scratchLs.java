
import java.io.*;

public class scratchLs {

	
	public static void main(String[] args) {
		
		String currentDirectory = System.getProperty("user.dir");
		System.out.println(currentDirectory);
		
		File f = new File(currentDirectory);
		String contents [] = (f.list());
		
		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
		}
		
		System.out.println("--------------------------");
		System.setProperty("user.dir", "/");
		currentDirectory = System.getProperty("user.dir");
		
		f = new File(currentDirectory);
		contents = (f.list());
		
		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
		}
		

	}

}
