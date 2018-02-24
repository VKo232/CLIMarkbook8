import java.util.*;

import javax.swing.JFileChooser;

import java.io.*;

public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();
	static String[][] options = { { "Reports", "Students", "Assignments", "Forgot Account", "Quit" },
			{ "Class report", "Missing assignments", "At risk students", "Back" },
			{ "Add new student", "Remove student", "Student Information", "Edit mark", "Back" }, 
			{ "Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight", "Back" } };
	static boolean done = false;

	public static void main(String[] args) {
		System.out.println("Welcome to the CLI Markbook of group 8\n");
		do {
			displayOptions();
			if (done) {
				break;
			}
		} while (true);
		System.out.println("Thank you for using group 8's services");
	}
	
	public static void studentList () {
		Scanner sc = new Scanner(System.in);
		if (classlist.size() < 1) {
			System.out.println("You have no students");
			return;
		}
		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			System.out.printf((i + 1) + "). %-20s%-20s%-20s" + classlist.get(i).getAverage() + "\n", classlist.get(i).getLast(), classlist.get(i).getFirst(),
					classlist.get(i).getStudentNumber());
		}
		while(true) {
			System.out.println("(num) Open up a student file? ");
		}
	}

	/**
	 * Calculates and displays the class average
	 */
	public static void classAverage() {
		if (classlist.size() < 1) {
			System.out.println("You have no students");
			return;
		}
		int sum = 0;
		System.out.println("Class Average \n");
		sortAlphabetically();
		for (int i = 0; i < classlist.size(); i++) {
			sum += classlist.get(i).getAverage();
		}
		
		System.out.println("\nClass Size: " + classlist.size());
		System.out.println("\nClass Average: " + (sum / classlist.size()));
		System.out.println("Number of assignments: " + classlist.get(0).getMarkArray().size());
		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			System.out.printf("%-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(), classlist.get(i).getFirst(),
					classlist.get(i).getStudentNumber(), classlist.get(i).getAverage());
		}

	}
	public static void addAssignment() {
		Scanner sc = new Scanner(System.in);
		if(classlist.size() < 1) {
			System.out.println("Add students in the class first!");
			return;
		}
		System.out.print("What is the name of your new assignment? ");
		String name = sc.nextLine();
		if(name.equals("")) {
			name = "unnamed";
		}
		System.out.print("What percent of the final mark will it be? ");
		while (!sc.hasNextInt()) {
			sc.nextLine();
			System.out.println("Thats not a number");
		}
		int percent = Integer.parseInt(sc.nextLine());
		classlist.get(0);
		Student.addAssignment(name);
		Student.addWeight(percent);
		for(int i = 0; i < classlist.size(); i++) {
			classlist.get(i).addMark(-1);
		}
	}

	/**
	 * simple bubble sort to change the array in alphabetical order last name
	 */
	public static void sortAlphabetically() {
		int size = classlist.size();
		Student placeholder = classlist.get(0);

		for (int i = 0; i < size; i++) {
			for (int j = 1; j < size - 1; j++) {
				if (classlist.get(j - 1).getLast().compareToIgnoreCase(classlist.get(j).getLast()) > 0) {
					placeholder = classlist.get(j - 1);
					classlist.set(j - 1, classlist.get(j));
					classlist.set(j, placeholder);
				} else if (classlist.get(j - 1).getLast().compareToIgnoreCase(classlist.get(j).getLast()) == 0
						&& classlist.get(j - 1).getFirst().compareToIgnoreCase(classlist.get(j).getFirst()) > 0) {
					placeholder = classlist.get(j - 1);
					classlist.set(j - 1, classlist.get(j));
					classlist.set(j, placeholder);
				}
			}
		}
	}

	public static void displayOptions() {
		int choice1, choice2;
		Scanner sc = new Scanner(System.in);

		// prints out the first set of possibilities
		System.out.println();
		for (int i = 0; i < options[0].length; i++) {
			System.out.println((i + 1) + " " + options[0][i]);
		}
		// takes in a user input
		System.out.print("(num) Whats your choice: ");
		do {
			while (!sc.hasNextInt()) {
				sc.nextLine();
				System.out.println("Thats not a number");
			}
			choice1 = Integer.parseInt(sc.nextLine());
			if (choice1 <= options[0].length && choice1 > 0) {
				break;
			} else {
				System.out.println("Invalid choice");
			}
		} while (true);
		if (choice1 == 5) {
			done = true;
			
			return;
		}
		// shows second set of possibilities
		System.out.println("\nHere are your options");
		for (int i = 0; i < options[choice1].length; i++) {
			System.out.println((i + 1) + " " + options[choice1][i]);
		}
		// gets user input
		System.out.print("(num) Whats your choice: ");
		do {
			while (!sc.hasNextInt()) {
				sc.nextLine();
				System.out.println("Thats not a number");
			}
			choice2 = Integer.parseInt(sc.nextLine());
			if (choice2 <= options[choice1].length && choice2 > 0) {
				break;
			} else {
				System.out.println("Invalid choice");
			}
		} while (true);
		if (choice2 == options[choice1].length) {
			
			return;
		}
		methodCall(choice1, choice2);
		
	}

	private static void methodCall(int choice1, int choice2) {
		int call = choice2;
		for (int i = 1; i < choice1; i++) {
			call += options[i].length - 1;
		}
		switch (call) {
		case 1:
			classAverage();
			break;
		case 2:
			addStudent();
			break;
		case 3:
			addAssignment();
			break;
		case 4:
			studentList();
			break;
		case 5:
			;
			break;
		case 6:
			;
			break;
		case 7:
			;
			break;
		case 8:
			;
			break;
		case 9:
			;
			break;
		case 10:
			;
			break;
		case 11:
			;
			break;
		case 12:
			;
			break;
		default:
			break;
		}

	}

	/**
	 * adds a new student initialized with name and number
	 */
	public static void addStudent() {
		boolean added = true;
		Scanner sc = new Scanner(System.in);
		try {
			do {
				
				
				// set first name
				System.out.print("\nEnter the first name of the student: ");
				String first = sc.nextLine();
				if(first.equalsIgnoreCase("quit")) {
					return;
				}
				first = first.toLowerCase();
				first = first.substring(0, 1).toUpperCase() + first.substring(1);

				// set last name
				System.out.print("Enter the last name of the student: ");
				String last = sc.nextLine();
				if(last.equalsIgnoreCase("quit")) {
					return;
				}
				last = last.toLowerCase();
				last = last.substring(0, 1).toUpperCase() + last.substring(1);

				// student number
				System.out.print("Enter the student number: ");
				String number = sc.nextLine();
				if(number.equalsIgnoreCase("quit")) {
					return;
				}
				
				classlist.add(new Student());
				classlist.get(classlist.size() - 1).setFirst(first);
				classlist.get(classlist.size() - 1).setLast(last);
				classlist.get(classlist.size() - 1).setStudentNumber(number);
				System.out.print("(y/n) Do you wish to add more? ");
				do {
					number = sc.nextLine();
				} while (!number.equalsIgnoreCase("y") && !number.equalsIgnoreCase("n"));

				if (number.equalsIgnoreCase("n")) {
					added = false;
				}
			} while (added);
		} catch (StringIndexOutOfBoundsException e) {
			addStudent();
		}
		
	}

	public static void save(String name) {
		try {
			FileOutputStream fs = new FileOutputStream(name);
			ObjectOutputStream obStream = new ObjectOutputStream(fs);
			obStream.writeObject(classlist);
			System.out.println("Saved~~");
			obStream.close();
			obStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void load() {
		System.out.println("Which class file?");
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream fs = new FileInputStream(fileChooser.getSelectedFile().getName());
				ObjectInputStream obStream = new ObjectInputStream(fs);
				classlist = (ArrayList<Student>) obStream.readObject();
				System.out.println("Loaded~~");
				obStream.close();
				fs.close();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("\nFailed to load");
			}

		}

	}

}
