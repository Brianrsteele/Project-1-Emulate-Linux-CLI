
import java.io.*;
import java.util.Scanner;

public class scratchCat {

	public static void main(String[] args) throws Exception {
		File file1 = new File("fruits.txt");
		File file2 = new File("vegetables.txt");
		
		Scanner sc1 = new Scanner(file1);
		Scanner sc2 = new Scanner(file2);
		
		while (sc1.hasNextLine()) {
			System.out.println(sc1.nextLine());
		}
		
		while (sc2.hasNextLine()) {
			System.out.println(sc2.nextLine());
		}

	}

}
