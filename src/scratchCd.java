


public class scratchCd {

	public static void main(String[] args) {
		String currentDirectory = System.getProperty("user.dir");
		System.out.println(currentDirectory);
		
		System.setProperty("user.dir", "/");
		
		currentDirectory = System.getProperty("user.dir");
		System.out.println(currentDirectory);
	}

}
