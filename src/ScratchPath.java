import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScratchPath {

	public static void main(String[] args) {
		
		Path p1 = Paths.get("/Users/briansteele/Dropbox");
		
		System.out.format("toString: %s%n", p1.toString());
		System.out.format("getFileName: %s%n", p1.getFileName());
		System.out.format("getName(0): %s%n", p1.getName(0));
		System.out.format("getNameCount: %d%n", p1.getNameCount());
		System.out.format("subpath(0,2): %s%n", p1.subpath(0,2));
		System.out.format("getParent: %s%n", p1.getParent());
		System.out.format("getRoot: %s%n", p1.getRoot());
		
		Path p2 = p1.subpath(0, p1.getNameCount() - 1);
		System.out.println(p2.toString());
		
		
		// getParent() will go up 1 directory
		Path p3 = p1.getParent();
		System.out.println(p3.toString());
		
		// resolve("path") will add a directory or file to the end of the path
		Path p4 = Paths.get("/Users/briansteele/Desktop");
		
		p4 = p4.resolve("Inbox");
		
		System.out.println(p4);
		
		// Files.exists() will see if a path leads to a real directory
		System.out.println(Files.exists(p4));
	}

}
