import java.util.*;
import java.io.*;

// make sure all have a loop prompt
// response type should be on the right
// add a couple thread.sleep to make viewing smoother
// makes sure back escape option works

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
			classlist.get(index).editMark(choice - 1, mark);
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
		int index = chooseStudent();
		String confirm = readString("Type \"CONFIRM\" to delete student (It cannot be recovered): ");
		if (confirm.equals("CONFIRM")) {
			System.out.println(classlist.get(index).getFirst() + " has been removed.");
			classlist.remove(index);
		}
		if(classlist.size() > 0) {
			String again = readString("Remove more? (y/n)"); 
			if(again.equalsIgnoreCase("y")) {
				removeStudent();
			}
		}
		
	}
	
	
	
	/**
	 * Calculates and displays the class average
	 */
	public static void classAverageReport() {

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
	
	
	public static void missingAssignmentReport() {

		boolean[] students = new boolean[classlist.size()];
		Arrays.fill(students, false);
		System.out.println("\n              Missing Assignment Report");
		
		System.out.println("\nClass Size: " + classlist.size());
		System.out.println("Number of assignments: " + Student.getAssignmentSize());
		System.out.printf("%-20s%-20s%-20s\n", "Last name", "First name", "Assignment Numbers Incomplete");

		for (int i = 0; i < classlist.size(); i++) {
			String missingAssignments = "";
			for(int j = 0; j < Student.getAssignmentSize(); j++) {
				if((int) classlist.get(i).getMark(j) == -1) {
					missingAssignments += ", " + (j + 1);
					students[i] = true;
				}
				
			}

			if(students[i]) {
				missingAssignments = missingAssignments.substring(1);
				System.out.printf("%-20s%-20s" + missingAssignments + "\n", classlist.get(i).getLast(), classlist.get(i).getFirst());
			}
		}
	}
	
	public static void atRiskReport() {

		System.out.println("\n              At Risk Student Report");
		
		System.out.println("\nClass Size: " + classlist.size());
		System.out.printf("%-20s%-20s%-20s%-20s\n", "Last name", "First name", "# of Incomplete Work", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			int missingAssignments = 0;
			for(int j = 0; j < Student.getAssignmentSize(); j++) {
				if((int) classlist.get(i).getMark(j) == -1) {
					missingAssignments++;
				}
			}
			
			if(classlist.get(i).getAverage() <= 65.0) {
				System.out.printf("%-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(), classlist.get(i).getFirst(), missingAssignments, classlist.get(i).getAverage());
			}
		}
	}
	
	

	public static void removeAssignment() {

		int index = chooseAssignment();
		
		for(int i = 0; i < classlist.size(); i++) {
			classlist.get(i).removeMark(index);

		}
		Student.removeAssignment(index);
		Student.removeWeight(index);
		
		if(Student.getAssignmentSize() > 0) {
			String more = readString("Would you like to remove more assignments? (y/n)");
			if(more.equalsIgnoreCase("y")) {
				removeAssignment();
			}
		}
	}
	
	
	
	public static void addAssignment() {

		String name = readString("What is the name of your new assignment? ");
		if(name.equals("back")) {
			return;
		}
		double percent = readDouble("How much will the assignment weigh? %", 0, 100);
		if((int) percent == -2) {
			return;
		}
		classlist.get(0);
		Student.addAssignment(name);
		Student.addWeight(percent);
		for(int i = 0; i < classlist.size(); i++) {
			classlist.get(i).addMark(-1);
		}
		String more = readString("Would you like to add more assignments? (y/n)");
		if(more.equalsIgnoreCase("y")) {
			addAssignment();
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
	/**
	 * Displays assignments then allows user to choose the particular assignment
	 * @return choice index
	 */
	
	public static int chooseAssignment() {
		displayAssignment();
		int assignment;
		assignment = (int) readDouble("Which assignment would you like to view information for? ", 1, Student.getAssignmentSize());
		if(assignment == -2) {
			return -2;
		}
		return assignment - 1;
	}
	
	public static void renameAssignment() {
		int index = chooseAssignment();
		if(index == -2) {
			return;
		}
		String name = readString("What do you wish to rename the assignment to?");
		Student.editAssignmentName(index, name);
		String more = readString("Would you like to rename more assignments? (y/n)");
		if(more.equalsIgnoreCase("y")) {
			renameAssignment();
		}
	}
	
	public static void editAssignmentWeight() {
		int index = chooseAssignment();
		if(index == -2) {
			return;
		}
		double percent = readDouble("What do you wish to edit the assignment weight to?", 0, 100);
		if(percent == -2) {
			return;
		}
		Student.setWeight(index, percent);
		String more = readString("Would you like to change more assignments? (y/n)");
		if(more.equalsIgnoreCase("y")) {
			renameAssignment();
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
		if (choice1 == 7) {
			done = true;		
			return;
		} else if (choice1 == 6) {
			load();
			return;
		} else if (choice1 == 5) {
			save();
			return;
		} else if (choice1 == 4){
			forgotAccount();
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
		if (classlist.size() < 1 && call != 4) {
			System.out.println("You have no students, Please add a new student or load a class file");
			return;
		} else if(Student.getAssignmentSize() < 1) {
			if(call != 8 && call != 5 && call != 4 && call != 7) {
				System.out.println("You have no assignments, Please add an assignment or load a class file");
				return;
			}
		}
		switch (call) {
		case 1:
			classAverageReport();
			break;
		case 2:
			missingAssignmentReport(); 
			break;
		case 3:
			atRiskReport(); // at risk students
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
			; // forgot student account or maybe rename student information
			break;
		case 8:
			addAssignment();
			break;
		case 9:
			removeAssignment();
			break;
		case 10: 
			renameAssignment(); // rename assignment
			break;
		case 11:
			editAssignmentWeight(); // change assignment weight
			break;
		case 12:
			; // marks for individual assignment
			break;
		default:
			break;
		}

	}

	public static void forgotAccount() {
		
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
					while (!sc.hasNextInt() && !sc.hasNextDouble()) {
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

	
	private static void init() {
		options = new String[][] {
				{ "Reports", "Students", "Assignments", "Forgot Student Account", "Save", "Load", "Quit" },
				{ "Class report", "Missing assignments", "At risk students"},
				{ "Add new student", "Remove student", "Student Marks", "Student Information"}, 
				{ "Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight", "Marks for assignment"} 
				};
		// so that static methods can be called
		new Student();
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
