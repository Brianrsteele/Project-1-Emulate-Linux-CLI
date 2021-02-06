import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

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

	// CurrentDirectory path contains the path to the current directory
	public Command(String c, CurrentDirectory path) {
		theCommand = c;
		commandArray = theCommand.split(" ");
		argumentsCount = commandArray.length;

		// cwd is current working directory
		this.cwd = path;

	}

	synchronized public void run() {
		// code to handle what the command says to do

		switch (commandArray[0].trim()) {

		/**
		 * 
		 * cd
		 * 
		 */
		case "cd":

			// TODO - this needs to handle ../..

			// if there is no argument, don't do anything.
			if (commandArray.length == 1) {
				break;
			} else {
				// break the arguments into an array of folders...
				String argument = commandArray[1];

				if (argument.equals(".")) {
					break;
				} else if (argument.equals("..")) {
					// make sure current directory isn't root
					if (!this.cwd.toString().equals("/")) {
						this.cwd.upOneDirectory();
						System.out.println(this.cwd.toString());
					}
				} else {
					Path tmp = cwd.getCurrentWorkingDirectory();
					tmp = tmp.resolve(argument);
					if (Files.exists(tmp)) {
						cwd.nextDirectory(tmp);
						System.out.println(this.cwd.toString());
					} else {
						System.out.println("-bash: cd: " + tmp.toString() + ": no such file or directory");
					}

				}

			}

			break;

		/**
		 * 
		 * pwd
		 * 
		 */
		case "pwd":
			System.out.println(cwd.getCurrentWorkingDirectory());
			break;
		case "ls":

			File f = new File(cwd.toString());
			String contents[] = (f.list());

			for (int i = 0; i < contents.length; i++) {
				System.out.println(contents[i]);
			}

			break;

		/**
		 * 
		 * cat
		 * 
		 */
		case "cat":

			if (commandArray.length == 1) {
				System.out.println("Error: cat must have at least one argument");
				break;
			} else {

				for (int i = 1; i < commandArray.length; i++) {
					File file = new File(cwd + "/" + commandArray[i]);
					Scanner s;
					try {
						s = new Scanner(file);
						while (s.hasNextLine()) {
							System.out.println(s.nextLine());
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("-bash: cat: " + commandArray[i] + ": no such file or directory");
					}

				}

			}

			break;

		/**
		 * 
		 * cmp
		 * 
		 */
		case "cmp":

			if (commandArray.length <= 2) {
				System.out.println("Error: cmp must have two arguments");
				break;
			} else if (commandArray.length > 3) {
				System.out.println("cmp: invalid --ignore-initial value `" + commandArray[3] + "'");
				break;

			}

			try {

				File f1 = new File(cwd + "/" + commandArray[1]);
				if (!f1.exists()) {
					System.out.println("cmp: " + commandArray[1] + ": No such file or directory");
					break;
				}

				File f2 = new File(cwd + "/" + commandArray[2]);

				if (!f2.exists()) {
					System.out.println("cmp: " + commandArray[2] + ": No such file or directory");
					break;
				}

				FileReader fr1 = new FileReader(f1);
				FileReader fr2 = new FileReader(f2);
				BufferedReader br1 = new BufferedReader(fr1);
				BufferedReader br2 = new BufferedReader(fr2);

				int c1 = 0;
				int c2 = 0;
				int line = 1;
				int linePos = 1;

				while ((c1 = br1.read()) != -1 && (c2 = br2.read()) != -1) {

					char character1 = (char) c1;
					char character2 = (char) c2;
					if (character1 == '\n') {
						line++;
					}

					if (character1 != character2) {

						System.out.println(
								commandArray[1] + " " + commandArray[2] + "differ: char " + linePos + ", line " + line);
						break;
					}

					linePos++;

				}

				br1.close();
				br2.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		/**
		 * 
		 * sort
		 * 
		 */
		case "sort":

			String[] tempArray = new String[1000];
			String[] sortArray;
			int lineCount = 0;

			if (commandArray.length == 1) {
				break;
			} else {

				for (int i = 1; i < commandArray.length; i++) {
					File file = new File(cwd + "/" + commandArray[i]);
					Scanner s;
					try {
						s = new Scanner(file);
						while (s.hasNextLine()) {
							if (lineCount < 1000) {
								tempArray[lineCount] = s.nextLine();
								lineCount++;
							}
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println("-bash: sort: no such file or directory");
					}

				}

				sortArray = new String[lineCount];

				for (int j = 0; j < lineCount; j++) {
					sortArray[j] = tempArray[j];
				}

				Arrays.sort(sortArray);

				for (int k = 0; k < sortArray.length; k++) {
					System.out.println(sortArray[k]);
				}

			}

			break;

		/**
		 * 
		 * exit
		 * 
		 */
		case "exit":
			System.exit(0);
			break;
		default:
			System.out.println(commandArray[0] + ": command not found");

		}

	}

}
