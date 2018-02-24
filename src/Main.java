import java.util.*;

import javax.swing.JFileChooser;

import java.io.*;

public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();
	static String[][] options = { { "Reports", "Students", "Assignments", "Save", "Load", "Quit" },
			{ "Class report", "Missing assignments", "At risk students"},
			{ "Add new student", "Remove student", "Student Information", "Forgot Student Account"}, 
			{ "Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight","Marks for assignment"} };
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

	public static void chooseStudent() {
		Scanner sc = new Scanner(System.in);
		studentList();
		int student;
		student = readInt("Which student would you like to view information for? ", 1, classlist.size());
		if(student == -2) {
			return;
		}
		displayStudent(student - 1);
		editStudentMark(student - 1);
	}
	public static void editStudentMark(int index) {
		Scanner sc = new Scanner(System.in);
		int choice;
		int mark;
		while (true) {
			System.out.print("(y/n) Would you like to edit the student's mark? ");
			if(sc.hasNext("n")) {
				sc.nextLine();
				break;
			}
			displayStudent(index);
			choice = readInt("Which assignment? ", 1, Student.getAssignmentSize());
			if(choice == -2) {
				return;
			} 
			mark = readInt("What mark? (-1 for incomplete): ", -1, 100);
			if(mark == -2) {
				continue;
			}
			classlist.get(index).editMark(choice, mark);
		}
	}
	
	public static void displayStudent(int index) {
		System.out.println("\nAssignment report for: " + classlist.get(index).getFirst() + " " + classlist.get(index).getLast() + " " + classlist.get(index).getStudentNumber());
		System.out.println("Total course completed: " + Student.getTotalCompletion() + "%");
		System.out.println("Average: " + classlist.get(index).getAverage());
		System.out.printf("%-20s%-20s%-20s%-30s\n", "Name of assignment", "Mark Received (%)", "Weight (%)", "Percent on final mark");
		for(int i = 0; i < classlist.get(index).getMarkArray().size(); i++) {
			if(classlist.get(index).getMark(i) == -1) {
				System.out.printf((i + 1) + ". %-20s%-20s%-20s%-30s\n", Student.getAssignmentName(i), "Incomplete", Student.getWeight(i) + "%", "0");
			} else {
				System.out.printf((i + 1) + ". %-20s%-20s%-20s%-30s\n", Student.getAssignmentName(i), classlist.get(index).getMark(i) + "%", Student.getWeight(i) + "%", classlist.get(index).getMark(i) * Student.getWeight(i) / 100.0);
		
			}
		}
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
			name = "Unnamed Assignment";
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

		// prints out the first set of possibilities
		System.out.println();
		for (int i = 0; i < options[0].length; i++) {
			System.out.println((i + 1) + " " + options[0][i]);
		}
		
		// takes in a user input
		choice1 = readInt("(num) Whats your choice: ", 1, options.length);
		if (choice1 == 6) {
			done = true;		
			return;
		} else if (choice1 == 5) {
			load();
			return;
		} else if (choice1 == 4) {
			save();
			return;
		}
		
		// shows second set of possibilities
		System.out.println("\nHere are your options");
		for (int i = 0; i < options[choice1].length; i++) {
			System.out.println((i + 1) + " " + options[choice1][i]);
		}
		
		// gets user input
		choice2 = readInt("(num) Whats your choice: ", 1, options[choice1].length);
		if (choice2 == -2) {
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
			
			break;
		case 3:
			addAssignment();
			break;
		case 4:
			addStudent();
			break;
		case 5:
			save();
			break;
		case 6:
			load();
			break;
		case 7:
			chooseStudent();
			break;
		case 8:
			displayStudent(0);
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
				if(first.equalsIgnoreCase("back")) {
					return;
				}
				first = first.toLowerCase();
				first = first.substring(0, 1).toUpperCase() + first.substring(1);

				// set last name
				System.out.print("Enter the last name of the student: ");
				String last = sc.nextLine();
				if(last.equalsIgnoreCase("back")) {
					return;
				}
				last = last.toLowerCase();
				last = last.substring(0, 1).toUpperCase() + last.substring(1);

				// student number
				System.out.print("Enter the student number: ");
				String number = sc.nextLine();
				if(number.equalsIgnoreCase("back")) {
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
	public static String readString(String prompt) {
		Scanner sc = new Scanner(System.in);
		String input;
		do {
		input = sc.nextLine();
		input = input.trim();
		} while(input.length() != 0);
		return input;
	}
	
	public static int readInt(String prompt, int start, int end) {
		Scanner sc = new Scanner(System.in); 
		int choice;
		System.out.print(prompt);
		while (true) {
		try {
			do {
				while (!sc.hasNextInt()) {
					sc.nextLine();
					System.out.println("Thats not a number");
					}
					if(sc.hasNext("back")) {
						return -2;
					}
					choice = Integer.parseInt(sc.nextLine());
					if(choice == -2) {
						return -1;
					} else if(choice >= start && choice <= end) {
						return choice;
					} else {
						System.out.print("\n" + prompt);
					}
				} while (true);
			} catch(NumberFormatException e) {
			
			}
		}
		
	}
	
	
	public static void save() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nWhat will you name your file? ");
		String name = sc.nextLine();
		try {
			FileOutputStream fs = new FileOutputStream(new File(name));
			ObjectOutputStream obStream = new ObjectOutputStream(fs);
			obStream.writeObject(classlist);
			System.out.println("Saved~~");
			obStream.close();
			fs.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	
	
	public static void load() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Which class file? ");
		String file = sc.nextLine();
		try {
			FileInputStream fs = new FileInputStream(file);
			ObjectInputStream obStream = new ObjectInputStream(fs);
			Object obj = obStream.readObject();
			if (obj instanceof ArrayList) {
				obj = classlist;
			}
			System.out.println("Loaded~~");
			obStream.close();
			fs.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("\nFailed to load");
		}


	}

}
