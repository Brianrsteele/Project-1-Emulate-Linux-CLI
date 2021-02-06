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
 * The Command class contains the logic for sorting through what unix command was issued
 * and then executing the command.
 * 
 * @author briansteele
 *
 */
public class Command implements Runnable {

	
	private String theCommand;
	private String commandArray[];
	private int argumentsCount;
	CurrentDirectory cwd;

	/*
	 *  Constructor for the Command class
	 *  @param c the string for the unix command issued
	 *  @param path the CurrentDirectory object that holds the path to the users home directory
	 */
	public Command(String c, CurrentDirectory path) {
		theCommand = c;
		// split the arguments into an array
		commandArray = theCommand.split(" ");
		argumentsCount = commandArray.length;

		// cwd is current working directory
		this.cwd = path;

	}

	/*
	 *  The run method required by implementing the Runnable interface
	 */
	synchronized public void run() {
		// code to handle what the command says to do

		switch (commandArray[0].trim()) {

		/**
		 * 
		 * cd command
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
					// make sure the file exits
					Path tmp = cwd.getCurrentWorkingDirectory();
					tmp = tmp.resolve(argument);
					if (Files.exists(tmp)) {
						cwd.nextDirectory(tmp);
						System.out.println(this.cwd.toString());
					} else {
						//simulate the unix error if file doesn't exist
						System.out.println("-bash: cd: " + tmp.toString() + ": no such file or directory");
					}

				}

			}

			break;

		/**
		 * 
		 * pwd command
		 * 
		 */
		case "pwd":
			// pwd only prints the current working directory
			System.out.println(cwd.getCurrentWorkingDirectory());
			break;
		
		
		/**
		 * 
		 * ls command
		 * 
		 */
		case "ls":
			
			// TODO - need to handle a directory that doesn't exist

			// create a file object from the current working directory
			File f = new File(cwd.toString());
			// get the contents of the directory
			String contents[] = (f.list());
			// print the contents of the directory
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

			// check if the proper number of arguments exit
			if (commandArray.length == 1) {
				System.out.println("Error: cat must have at least one argument");
				break;
			} else {

				// for each argument, created a file object
				for (int i = 1; i < commandArray.length; i++) {
					File file = new File(cwd + "/" + commandArray[i]);
					Scanner s;
					try {
						// use a scanner to get every line of the file and print it.
						s = new Scanner(file);
						while (s.hasNextLine()) {
							System.out.println(s.nextLine());
						}
					} catch (FileNotFoundException e) {
						// Simulate the unix error if file doesn't exist
						System.out.println("-bash: cat: " + commandArray[i] + ": no such file or directory");
					}

				}

			}

			break;

		/**
		 * 
		 * cmp command
		 * 
		 */
		case "cmp":

			// check for the proper number of arguments
			if (commandArray.length <= 2) {
				System.out.println("Error: cmp must have two arguments");
				break;
			} else if (commandArray.length > 3) {
				System.out.println("cmp: invalid --ignore-initial value `" + commandArray[3] + "'");
				break;

			}

			try {

				// create two file objects
				File f1 = new File(cwd + "/" + commandArray[1]);
				if (!f1.exists()) {
					// simulate unix error if no file
					System.out.println("cmp: " + commandArray[1] + ": No such file or directory");
					break;
				}

				File f2 = new File(cwd + "/" + commandArray[2]);

				if (!f2.exists()) {
					// simulate unix error if no file
					System.out.println("cmp: " + commandArray[2] + ": No such file or directory");
					break;
				}

				// create file readers for each object
				FileReader fr1 = new FileReader(f1);
				FileReader fr2 = new FileReader(f2);
				// create buffered readers from each file reader
				BufferedReader br1 = new BufferedReader(fr1);
				BufferedReader br2 = new BufferedReader(fr2);

				// initialize a character count for each file for the buffered reader
				int c1 = 0;
				int c2 = 0;
				// initialize line and line position counts 
				int line = 1;
				int linePos = 1;

				while ((c1 = br1.read()) != -1 && (c2 = br2.read()) != -1) {

					// read the character
					char character1 = (char) c1;
					char character2 = (char) c2;
					// increment if the character is a line
					if (character1 == '\n') {
						line++;
					}

					// if there is a difference
					if (character1 != character2) {
						// output difference
						System.out.println(
								commandArray[1] + " " + commandArray[2] + " differ: char " + linePos + ", line " + line);
						break;
					}
					// increment the position
					linePos++;

				}

				br1.close();
				br2.close();
			} catch (Exception e) {
				// missing files are caught earlier, but this is just in case
				System.out.println("cmp: No such file or directory");
			}

			break;

		/**
		 * 
		 * sort command
		 * 
		 */
		case "sort":

			// create a temporary array to hold 1000 lines, so that multiple
			// files can be assembled into a sort
			String[] tempArray = new String[1000];
			// the array that will be sorted
			String[] sortArray;
			// initialize a line count variable
			int lineCount = 0;

			// check for the correct number of arguments - must have at least one filename
			if (commandArray.length == 1) {
				break;
			} else {

				// for all of the files in the arguments
				for (int i = 1; i < commandArray.length; i++) {
					// create a file object
					File file = new File(cwd + "/" + commandArray[i]);
					Scanner s;
					try {
						// scan the file and add each line to the temporary array
						s = new Scanner(file);
						while (s.hasNextLine()) {
							if (lineCount < 1000) {
								tempArray[lineCount] = s.nextLine();
								// increment the line count
								lineCount++;
							}
						}
					} catch (FileNotFoundException e) {
						// simulate the unix error message for a file that doesn't exist
						System.out.println("-bash: sort: no such file or directory");
						break;
					}

				}

				// create the sorted array, with a length of line count.
				sortArray = new String[lineCount];

				// copy the temp array into the sorted array
				for (int j = 0; j < lineCount; j++) {
					sortArray[j] = tempArray[j];
				}

				// sort the sorted array
				Arrays.sort(sortArray);

				// print the sorted array
				for (int k = 0; k < sortArray.length; k++) {
					System.out.println(sortArray[k]);
				}

			}

			break;

		/**
		 * 
		 * exit command
		 * 
		 */
		case "exit":
			// TODO - this is not handled gracefully in terms of threading, per 
			// project instructions.
			System.exit(0);
			break;
			
		
		default:
			// handle a command that hasn't been implemented.
			System.out.println(commandArray[0] + ": command not found");

		}

	}

}
