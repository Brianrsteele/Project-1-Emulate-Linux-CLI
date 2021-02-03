
public class ScratchPWD {

	
	
	public static void main(String[] args) {
		String currentDirectory = System.getProperty("user.dir");
		String currentUser = System.getProperty("user.name");
		String userHome = System.getProperty("user.home");
		
		System.out.println(currentDirectory);
		System.out.println(currentUser);
		System.out.println(userHome);
	}

}
