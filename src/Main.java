import java.util.*;
import java.io.*;

// make sure all have a loop prompt
// make sure all check if there are assignments or students
// response type should be on the right

public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();
	static String[][] options; 
	static boolean done = false;



	public static void main(String[] args) {
		System.out.println("Welcome to the CLI Markbook of group 8\n");
		init();

		do {
			displayOptions();
			if (done) {
				break;
			}
		} while (true);
		System.out.println("Thank you for using group 8's services");
	}

	
	/**
	 * displays list of students and users tells which one it is
	 * @return index of student chosen
	 */
	public static int chooseStudent() {
		studentList();
		int student;
		student = (int) readDouble("Which student would you like to select? (num): ", 1, classlist.size());
		if(student == -2) {
			return -2;
		}
		return student -1;
	}
	
	/**
	 * checks if there are students and assignments
	 * calls chooseStudent and then displays assignments and prompts user to edit
	 */
	public static void editStudentMark() {
		if (classlist.size() < 1){
			System.out.println("There are no students");
			return;
		} else if (Student.getAssignmentSize() < 1) {
			System.out.println("There are no assignments");
			return;
		}
		int choice;
		double mark;
		int index = chooseStudent();
		if(index == -2) {
			return;
		}
		displayStudent(index);
		while (true) {
			String check = readString("(y/n) Would you like to edit the student's mark? ");
			if (check.equalsIgnoreCase("n")) {
				return;
			}
			
			displayStudent(index);
			choice = (int) readDouble("Which assignment? ", 1, Student.getAssignmentSize());
			if(choice == -2) {
				return;
			}
			
			mark = readDouble("What mark? (-1 for incomplete): ", -1, 100);
			if(mark == -2) {
				continue;
			}
			classlist.get(index).editMark(choice, mark);
		}
	}
	/**
	 * displays all the assignments for a single student
	 * @param index
	 */
	public static void displayStudent(int index) {
		System.out.println("\n                  Assignment report for: " + classlist.get(index).getFirst() + " " + classlist.get(index).getLast() + " " + classlist.get(index).getStudentNumber());
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
	/**
	 * Displays all students and averages
	 */
	public static void studentList () {
		if (classlist.size() < 1) {
			System.out.println("You have no students");
			return;
		}
		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			System.out.printf((i + 1) + "). %-20s%-20s%-20s%.2f\n" , classlist.get(i).getLast(), classlist.get(i).getFirst(), classlist.get(i).getStudentNumber(), classlist.get(i).getAverage());
		}

	}
	/**
	 * checks num of students
	 * chooses student then confirms removal and prompts for again
	 */
	public static void removeStudent() {
		if (classlist.size() < 1) {
			System.out.println("You have no students to remove");
			return;
		}
		int index = chooseStudent();
		String confirm = readString("Type \"CONFIRM\" to delete student (It cannot be recovered): ");
		if (confirm.equals("CONFIRM")) {
			System.out.println(classlist.get(index).getFirst() + " has been removed.");
			classlist.remove(index);
		}
		String again = readString("Remove more? (y/n)"); 
		if(again.equalsIgnoreCase("y")) {
			removeStudent();
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
		System.out.println("\n              Class Average Report");
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
	
	public static void removeAssignment() {
		if(classlist.size() < 1) {
			System.out.println("Add students in the class first!");
			return;
		} else if(Student.getAssignmentSize() < 1) {
			System.out.println("Add assignments first!!");
			return;
		}
		int index = chooseAssignment();
		
		for(int i = 0; i < classlist.size(); i++) {
			classlist.get(i).removeMark(index);

		}
		Student.removeAssignment(index);
		Student.removeWeight(index);
	}
	
	public static void addAssignment() {
		if(classlist.size() < 1) {
			System.out.println("Add students in the class first!");
			return;
		}
		String name = readString("What is the name of your new assignment? ");
		double percent = readDouble("How much will the assignment weigh? %", 0, 100);
		classlist.get(0);
		Student.addAssignment(name);
		Student.addWeight(percent);
		for(int i = 0; i < classlist.size(); i++) {
			classlist.get(i).addMark(-1);
		}
	}
	
	
	public static void displayAssignment() {
		double average;

		for(int i = 0; i < Student.getAssignmentSize(); i++) {
			average = 0;
			for(int j = 0; j < classlist.size(); j++) {
				if(classlist.get(j).getMark(i) >= 0) {
					average += classlist.get(j).getMark(i);
				}
			}
			average /= classlist.size();
			System.out.printf((i + 1) + "%-23s%-10.2f%.2f", Student.getAssignmentName(i) , Student.getWeight(i), average);
		}
	}
	
	
	public static int chooseAssignment() {
		displayAssignment();
		int assignment;
		assignment = (int) readDouble("Which assignment would you like to view information for? ", 1, Student.getAssignmentSize());
		if(assignment == -2) {
			return -2;
		}
		return assignment - 1;
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
		choice1 = (int) readDouble("(num) Whats your choice: ", 1, options[0].length);
		if (choice1 == 6) {
			done = true;		
			return;
		} else if (choice1 == 5) {
			load();
			return;
		} else if (choice1 == 4) {
			save();
			return;
		} else if(choice1 == -2) {
			return;
		}
		
		// shows second set of possibilities
		System.out.println("\nHere are your options");
		for (int i = 0; i < options[choice1].length; i++) {
			System.out.println((i + 1) + " " + options[choice1][i]);
		}
		
		// gets user input
		choice2 = (int) readDouble("(num) Whats your choice: ", 1, options[choice1].length);
		if (choice2 == -2) {
			return;
		}
		methodCall(choice1, choice2);
	}

	
	
	private static void methodCall(int choice1, int choice2) {
		int call = choice2;
		for (int i = 1; i < choice1; i++) {
			call += options[i].length;
		}
		switch (call) {
		case 1:
			classAverage();
			break;
		case 2:
			; // missing assignments
			break;
		case 3:
			; // at risk students
			break;
		case 4:
			addStudent();
			break;
		case 5:
			removeStudent();
			break;
		case 6:
			editStudentMark();
			break;
		case 7:
			; // forgot student account
			break;
		case 8:
			addAssignment();
			break;
		case 9:
			removeAssignment();
			break;
		case 10: 
			; // rename assignment
			break;
		case 11:
			; // change assignment weight
			break;
		case 12:
			; // marks for individual assignment
			break;
		default:
			break;
		}

	}

	
	
	/**
	 * adds a new student initialized with name and number
	 */
	public static void addStudent() {
		String[] addStudentPrompts = { "\nEnter the first name of the student: ", "\nEnter the last name of the student: ", "\nEnter the student number: "};
		String[] addStudentInfo = new String[3]; 
		String input;
		for(int i = 0; i < addStudentPrompts.length; i++) {
			addStudentInfo[i] = readString(addStudentPrompts[i]);
			if(addStudentInfo[i].equalsIgnoreCase("back")) {
				if(i == 0) {
					return;
				} else {
					continue;
				}
			}
			addStudentInfo[i] = addStudentInfo[i].toLowerCase();
			addStudentInfo[i] = addStudentInfo[i].substring(0, 1).toUpperCase() + addStudentInfo[i].substring(1);
		}

		classlist.add(new Student());
		classlist.get(classlist.size() - 1).setFirst(addStudentInfo[0]);
		classlist.get(classlist.size() - 1).setLast(addStudentInfo[1]);
		classlist.get(classlist.size() - 1).setStudentNumber(addStudentInfo[2]);
		sortAlphabetically();
		do {
			input = readString("(y/n) Do you wish to add more? ");
		} while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
		if (input.equalsIgnoreCase("y")) {
			addStudent();
		}

	}
	

	public static String readString(String prompt) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String input;
		do {
			System.out.println(prompt + "\n");
			System.out.print(">>> ");
			input = sc.nextLine();
			input = input.trim();
		} while(input.length() == 0);
		return input;
	}
	
	public static double readDouble(String prompt, int start, int end) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in); 
		double choice;
		while (true) {
			try {
				do {
					System.out.println("\n" + prompt + "\n");
					System.out.print(">>> ");
					while (!sc.hasNextInt()) {
						if (sc.hasNext("back")) {
							return -2;
						}
						sc.nextLine();
						System.out.println("Thats not a number\n" + "enter (back) if you want to exit");
					}

					choice = Double.parseDouble(sc.nextLine());

					if (choice >= start && choice <= end) {
						return choice;
					} else {
						System.out.println("Invalid choice");
					}
				} while (true);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input!!!");
			}
		}

	}

	private static void init() {
		options = new String[][] {
				{ "Reports", "Students", "Assignments", "Save", "Load", "Quit" },
				{ "Class report", "Missing assignments", "At risk students"},
				{ "Add new student", "Remove student", "Student Marks", "Forgot Student Account"}, 
				{ "Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight", "Marks for assignment"} 
				};
	}
	
	public static void save() {
		String name = readString("\nWhat will you name your file? ");
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

	
	
	@SuppressWarnings("unchecked")
	public static void load() {
		String file = readString("\nWhich class file? ");
		try {
			FileInputStream fs = new FileInputStream(file);
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
