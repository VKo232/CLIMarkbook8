import java.util.*;
import java.io.*;

// make sure all have a loop prompt
// response type should be on the right
// add a couple thread.sleep to make viewing smoother
// makes sure back escape option works

/**
 * A program that helps teachers store, edit, and remove students, assignments, and marks.
 * The program can display class reports, including averages and at-risk students,
 * and can generate email and password information for students.
 *
 * @author qwertyuiop1
 *
 *         note: do not run with student class, it is already in this program
 */
public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();
	static String[][] options;
	static ArrayList<String> assignmentName = new ArrayList<String>();
	static ArrayList<Double> weight = new ArrayList<Double>();
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
   	  * Displays the list of students and returns the class list index for the student.
     	  *
    	  * @return index of student chosen
          */
	public static int chooseStudent() {
		studentList();
		int student;
		student = (int) readDouble("Which student would you like to select? (num): ", 1, classlist.size());
		if (student == -2) {
			return -2;
		}
		return student - 1;
	}

	/**
     	  * Calls chooseStudent() and then displays assignments. Asks user if they want to
          * edit the student's mark and allows user to choose which assignment to edit.
          * User can input a mark or input -1 for an incomplete assignment.
          * Mark at the chosen index in the marks array of students is edited.
          */
	public static void editStudentMark() {
		int choice;
		double mark;
		int index = chooseStudent();
		if (index == -2) {
			return;
		}
		displayStudent(index);
		while (true) {
			String check = readString("(y/n) Would you like to edit the student's mark? ");
			if (check.equalsIgnoreCase("n")) {
				return;
			}

			displayStudent(index);
			choice = (int) readDouble("Which assignment? ", 1, assignmentName.size());
			if (choice == -2) {
				return;
			}

			mark = readDouble("What mark? (-1 for incomplete): ", -1, 100);
			if (mark == -2) {
				continue;
			}
			classlist.get(index).editMark(choice - 1, mark);
		}
	}

	/**
          * Displays the assignments, marks, average and for a single student.
          *
          * @param index
          *            index of the student in the classlist
          */
	public static void displayStudent(int index) {
		System.out.println("\n                  Assignment report for: " + classlist.get(index).getFirst() + " "
				+ classlist.get(index).getLast() + " " + classlist.get(index).getStudentNumber());
		System.out.println("Total course completed: " + getTotalCompletion() + "%");
		System.out.println("Average: " + classlist.get(index).getAverage(weight, getTotalCompletion()) + "\n");
		System.out.printf("%-20s%-20s%-20s%-30s\n", "Name of assignment", "Mark Received (%)", "Weight (%)",
				"Percent on final mark");
		for (int i = 0; i < assignmentName.size(); i++) {
			if (classlist.get(index).getMark(i) == -1) {
				System.out.printf((i + 1) + ". %-20s%-20s%-20s%-30s\n", assignmentName.get(i), "Incomplete",
						weight.get(i) + "%", "0");
			} else {
				System.out.printf((i + 1) + ". %-20s%-20s%-20s%-30s\n", assignmentName.get(i),
						classlist.get(index).getMark(i) + "%", weight.get(i) + "%",
						classlist.get(index).getMark(i) * weight.get(i) / 100.0);

			}
		}
	}

	/**
	 * Displays all students and averages
	 */
	public static void studentList() {
		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			System.out.printf((i + 1) + "). %-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(),
					classlist.get(i).getFirst(), classlist.get(i).getStudentNumber(),
					classlist.get(i).getAverage(weight, getTotalCompletion()));
		}

	}

	/**
	 * chooses student then confirms removal
	 */
	public static void removeStudent() {
		int index = chooseStudent();
		if (index == -2) {
			return;
		}
		String confirm = readString("Type \"CONFIRM\" to delete student (It cannot be recovered): ");
		if (confirm.equals("CONFIRM")) {
			System.out.println(classlist.get(index).getFirst() + " has been removed.");
			classlist.remove(index);
		}
		if (classlist.size() > 0) {
			String again = readString("Remove more? (y/n)");
			if (again.equalsIgnoreCase("y")) {
				removeStudent();
			}
		}

	}

	/**
	 * allows user to edit Students' name or number
	 */
	public static void editStudentInformation() {
		String[] prompt = { "Edit First Name", "Edit Last Name", "Edit Number" };
		int index = chooseStudent();
		if (index == -2) {
			return;
		}
		while (true) {
			System.out.println("\nHere are your options");
			for (int i = 0; i < 3; i++) {
				System.out.println((i + 1) + ". " + prompt[i]);
			}
			int choice = (int) readDouble("What do you want to edit? ", 1, 3);
			if (choice == -2) {
				return;
			}
			String answer = readString("What will you change it to?");
			if (answer.equals("back")) {
				return;
			}
			answer = answer.substring(0, 1).toUpperCase() + answer.substring(1).toLowerCase();
			switch (choice) {
				case 1:
					classlist.get(index).setFirst(answer);
					break;
				case 2:
					classlist.get(index).setLast(answer);
					break;
				case 3:
					classlist.get(index).setStudentNumber(answer);
					break;
				default:
					break;
			}

			sortAlphabetically();
			String again = readString("edit more? (y/n)");
			if (again.equalsIgnoreCase("n")) {
				return;
			}

		}
	}

	/**
	 * adds a new student initialized with name and number the marks for each
	 * assignment will be considered incomplete
	 */
	public static void addStudent() {
		String[] addStudentPrompts = { "\nEnter the first name of the student: ",
				"\nEnter the last name of the student: ", "\nEnter the student number: " };
		String[] addStudentInfo = new String[3];
		String input;
		for (int i = 0; i < addStudentPrompts.length; i++) {
			addStudentInfo[i] = readString(addStudentPrompts[i]);
			if (addStudentInfo[i].equalsIgnoreCase("back")) {
				if (i == 0) {
					return;
				} else {
					continue;
				}
			}
			addStudentInfo[i] = addStudentInfo[i].substring(0, 1).toUpperCase()
					+ addStudentInfo[i].substring(1).toLowerCase();
		}

		classlist.add(new Student());
		classlist.get(classlist.size() - 1).setFirst(addStudentInfo[0]);
		classlist.get(classlist.size() - 1).setLast(addStudentInfo[1]);
		classlist.get(classlist.size() - 1).setStudentNumber(addStudentInfo[2]);
		try {
			for (int i = 0; i < weight.size(); i++) {
				classlist.get(classlist.size() - 1).addMark(-1);
			}
		} catch (IndexOutOfBoundsException e) {

		}
		sortAlphabetically();
		do {
			input = readString("(y/n) Do you wish to add more? ");
		} while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
		if (input.equalsIgnoreCase("y")) {
			addStudent();
		}

	}

	/**
	 * Calculates and displays the class average as well as individual students
	 */
	public static void classAverageReport() {

		double sum = 0;
		System.out.println("\n              Class Average Report");

		for (int i = 0; i < classlist.size(); i++) {
			sum += classlist.get(i).getAverage(weight, getTotalCompletion());
		}

		System.out.println("\nClass Size: " + classlist.size());
		System.out.println("\nClass Average: " + (sum / classlist.size()));
		System.out.println("Number of assignments: " + weight.size() + "\n");
		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			System.out.printf("%-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(), classlist.get(i).getFirst(),
					classlist.get(i).getStudentNumber(), classlist.get(i).getAverage(weight, getTotalCompletion()));
		}

	}

	/**
	 * prints out a list of students with missing assignments and the index number +
	 * 1 of it
	 */
	public static void missingAssignmentReport() {

		boolean[] students = new boolean[classlist.size()];
		Arrays.fill(students, false);
		System.out.println("\n              Missing Assignment Report");

		System.out.println("\nClass Size: " + classlist.size());
		System.out.println("Number of assignments: " + weight.size() + "\n");
		System.out.printf("%-20s%-20s%-20s\n", "Last name", "First name", "Assignment Numbers Incomplete");

		for (int i = 0; i < classlist.size(); i++) {
			String missingAssignments = "";
			for (int j = 0; j < weight.size(); j++) {
				if ((int) classlist.get(i).getMark(j) == -1) {
					missingAssignments += ", " + (j + 1);
					students[i] = true;
				}

			}

			if (students[i]) {
				missingAssignments = missingAssignments.substring(1);
				System.out.printf("%-20s%-20s" + missingAssignments + "\n", classlist.get(i).getLast(),
						classlist.get(i).getFirst());
			}
		}
	}

	/**
	 * prints out a list of students with less or equal to 65% average the format
	 * will still print out even with no students below that range
	 */
	public static void atRiskReport() {

		System.out.println("\n              At Risk Student Report");

		System.out.println("\nClass Size: " + classlist.size() + "\n");
		System.out.printf("%-20s%-15s%-25s%-20s\n", "Last name", "First name", "# of Incomplete Work", "Average");

		for (int i = 0; i < classlist.size(); i++) {
			int missingAssignments = 0;
			for (int j = 0; j < weight.size(); j++) {
				if ((int) classlist.get(i).getMark(j) == -1) {
					missingAssignments++;
				}
			}

			if (classlist.get(i).getAverage(weight, getTotalCompletion()) <= 65.0) {
				System.out.printf("%-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(), classlist.get(i).getFirst(),
						missingAssignments, classlist.get(i).getAverage(weight, getTotalCompletion()));
			}
		}
	}

	/**
	 * lets user choose an assignment and removes it
	 */
	public static void removeAssignment() {

		int index = chooseAssignment();
		if (index == -2) {
			return;
		}

		for (int i = 0; i < classlist.size(); i++) {
			classlist.get(i).removeMark(index);

		}
		assignmentName.remove(index);
		weight.remove(index);

		if (weight.size() > 0) {
			String more = readString("Would you like to remove more assignments? (y/n)");
			if (more.equalsIgnoreCase("y")) {
				removeAssignment();
			}
		}
	}

	/**
	 * adds an assignment with name and weight adds the assignment to all the
	 * students marks and sets it as incomplete
	 */
	public static void addAssignment() {

		String name = readString("What is the name of your new assignment? ");
		if (name.equals("back")) {
			return;
		}
		double percent = readDouble("How much will the assignment weigh? %", 0, 100);
		if ((int) percent == -2) {
			return;
		}

		for (int i = 0; i < classlist.size(); i++) {
			classlist.get(i).addMark(-1);
		}
		assignmentName.add(name);
		weight.add(percent);
		String more = readString("Would you like to add more assignments? (y/n)");
		if (more.equalsIgnoreCase("y")) {
			addAssignment();
		}
	}

	/**
	 * Displays a list of assignments and the averages
	 */
	public static void displayAssignment() {
		double average;
		System.out.println("\n                               Assignments for this class");
		for (int i = 0; i < weight.size(); i++) {
			average = 0;
			for (int j = 0; j < classlist.size(); j++) {
				if (classlist.get(j).getMark(i) >= 0) {
					average += classlist.get(j).getMark(i);
				}
			}
			average /= classlist.size();
			System.out.printf((i + 1) + ". %-23s%-10.2f%.2f\n", assignmentName.get(i), weight.get(i), average);
		}
	}

	/**
	 * Displays assignments then allows user to choose the particular assignment
	 *
	 * @return choice index
	 */
	public static int chooseAssignment() {
		displayAssignment();
		int assignment;
		assignment = (int) readDouble("Which assignment would you like to select? ", 1, weight.size());
		if (assignment == -2) {
			return -2;
		}
		return assignment - 1;
	}

	/**
	 * renames assignment that user chooses calls the chooseAssignment method
	 */
	public static void renameAssignment() {
		int index = chooseAssignment();
		if (index == -2) {
			return;
		}
		String name = readString("What do you wish to rename the assignment to?");
		for (int i = 0; i < classlist.size(); i++) {
			assignmentName.set(index, name);
		}
		String more = readString("Would you like to rename more assignments? (y/n)");
		if (more.equalsIgnoreCase("y")) {
			renameAssignment();
		}
	}

	/**
	 * changes the weight of an assignment calls choose assignment to pick the
	 * assignment
	 */
	public static void editAssignmentWeight() {
		int index = chooseAssignment();
		if (index == -2) {
			return;
		}
		double percent = readDouble("What do you wish to edit the assignment weight to?", 0, 100);
		if (percent == -2) {
			return;
		}
		for (int i = 0; i < classlist.size(); i++) {
			weight.set(index, percent);
		}
		String more = readString("Would you like to change more assignments? (y/n)");
		if (more.equalsIgnoreCase("y")) {
			renameAssignment();
		}
	}

	/**
	 * prints out all the student marks for a user inputed assignment calls
	 * chooseAssignment to pick the assignment
	 */
	public static void displayAssignmentMarks() {
		int index = chooseAssignment();
		System.out.println("\n                  Marks for \"" + assignmentName.get(index) + "\"");
		double sum = 0;
		for (int i = 0; i < classlist.size(); i++) {
			if (classlist.get(i).getMark(index) != -1) {
				sum += classlist.get(i).getMark(index);
			}
		}
		System.out.printf("Class Average for assignment: %.2f\n" + ((double) sum / classlist.size()));
		System.out.println("Percentage weight of final mark: " + weight.get(index) + "\n");
		System.out.printf("%-20s%-20s%s\n", "Last name", "First name", "%mark received");
		for (int i = 0; i < classlist.size(); i++) {
			String complete;
			if ((int) classlist.get(i).getMark(index) == -1) {
				complete = "incomplete";
			} else {
				complete = Double.toString(classlist.get(i).getMark(index));
			}
			System.out.printf("%-20s%-20s%s\n", classlist.get(i).getLast(), classlist.get(i).getFirst(), complete);
		}
		String again = readString("Would you like to view more assignments? (y/n)");
		if (again.equalsIgnoreCase("y")) {
			displayAssignmentMarks();
		}

	}

	/**
	 * displays a set of options for user to use also has a quit selection structure
	 * that will terminate the program calls methodCall to call the corresponding
	 * method
	 */
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
		} else if (choice1 == 4) {
			forgotAccount();
			return;
		} else if (choice1 == -2) {
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

	/**
	 * method receives the choices of the user and calls the corresponding method
	 *
	 * @param choice1
	 *            int of choice
	 * @param choice2
	 *            int choice
	 */
	private static void methodCall(int choice1, int choice2) {
		int call = choice2;
		for (int i = 1; i < choice1; i++) {
			call += options[i].length;
		}
		if (classlist.size() < 1 && call != 4) {
			System.out.println("You have no students, Please add a new student or load a class file");
			return;
		} else if (weight.size() < 1) {
			if (call != 8 && call != 5 && call != 4 && call != 7 && call != 1) {
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
				editStudentInformation(); // forgot student account or maybe rename student information
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
				displayAssignmentMarks(); // marks for individual assignment
				break;
			default:
				break;
		}

	}

	/**
	 * method to generate user and password for school account takes in user iputs
	 * to generate this
	 */
	public static void forgotAccount() {
		String[] account = new String[4];
		String[] prompts = { "First Name", "Last Name", "Graduating Year", "Student Number" };
		System.out.println("Please fill in the below form to generate your account information");
		for (int i = 0; i < 4; i++) {
			account[i] = readString(prompts[i]);
			if (account[i].equals("back")) {
				return;
			}
		}
		try {
			System.out.println("Your user name is: " + account[0].toLowerCase() + "." + account[1].toLowerCase()
					+ account[2].substring(account[2].length() - 2) + "@ycdsbk12.ca");
			System.out.println("Your password is: " + Character.toUpperCase(account[0].charAt(0))
					+ Character.toLowerCase(account[1].charAt(0)) + account[3]);
			String again = readString("Do you wish to generate more account names? (y/n)");
			if (again.equalsIgnoreCase("y")) {
				forgotAccount();
			}
		} catch (StringIndexOutOfBoundsException e) {
			forgotAccount();
		}
	}

	/**
	 * uses scanner to read a string user input and returns it
	 *
	 * @param prompt
	 *            Prints out a String prompt
	 * @return returns the user's answer without spaces on the sides
	 */
	public static String readString(String prompt) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String input;
		do {
			System.out.println(prompt + "\n");
			System.out.print(">>> ");
			input = sc.nextLine();
			input = input.trim();
		} while (input.length() == 0);
		return input;
	}

	/**
	 * method that allows a continual inputs until there is a number in a defined
	 * range, inclusive
	 *
	 * @param prompt
	 *            String that prompts user to answer
	 * @param start
	 *            int inclusive of starting range
	 * @param end
	 *            int inclusive of ending range
	 * @return returns value or -2 which is a key to exit method
	 */
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
	 * simple bubble sort to change the array in alphabetical order of last name
	 * from a to z
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

	/**
	 * initializes the String array options used to display options for user
	 */
	private static void init() {
		options = new String[][] {
				{ "Reports", "Students", "Assignments", "Forgot Student Account", "Save", "Load", "Quit" },
				{ "Class report", "Missing assignments", "At risk students" },
				{ "Add new student", "Remove student", "Student Marks", "Student Information" },
				{ "Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight",
						"Marks for assignment" } };
	}

	/**
	 * writes information in an object ArrayList then writes it to a file named by
	 * the user
	 */
	public static void save() {
		String name = readString("\nWhat will you name your file? ");
		if (name.equals("back")) {
			return;
		}
		try {
			ArrayList<Object> objArray = new ArrayList<Object>();
			objArray.add(classlist);
			objArray.add(assignmentName);
			objArray.add(weight);
			FileOutputStream fs = new FileOutputStream(new File(name));
			ObjectOutputStream obStream = new ObjectOutputStream(fs);
			obStream.writeObject(objArray);
			System.out.println("Saved~~");
			obStream.close();
			fs.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	/**
	 * A method that reads a file with name from user input It will catch most
	 * errors and do nothing with it
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		String file = readString("\nWhich class file name? ");
		if (file.equals("back")) {
			return;
		}
		try {
			FileInputStream fs = new FileInputStream(file);
			ObjectInputStream obStream = new ObjectInputStream(fs);
			Object obj = obStream.readObject();

			ArrayList<Object> objArray = (ArrayList<Object>) obj;

			classlist = (ArrayList<Student>) objArray.get(0);
			assignmentName = (ArrayList<String>) objArray.get(1);
			weight = (ArrayList<Double>) objArray.get(2);

			System.out.println("Loaded~~");
			obStream.close();
			fs.close();
		} catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e) {
			System.out.println("\nFailed to load");
		}

	}

	/**
	 * calculates and returns the percentage of the course that is completed
	 *
	 * @return totalCompletion double which is percent of course completed
	 */
	public static double getTotalCompletion() {
		double totalCompletion = 0;
		for (int i = 0; i < weight.size(); i++) {
			totalCompletion += weight.get(i);
		}
		return totalCompletion;

	}

}

/**
 * Student object that stores name, number, average, and marks for for each
 * assignment
 *
 * @author qwertyuiop1
 *
 */
class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Double> marks;
	private String first, last, number;
	private double average;

	/**
	 * constructor
	 */
	public Student() {
		marks = new ArrayList<Double>();
		first = "";
		last = "";
		number = "";
		average = 100;
	}

	/**
	 * returns mark at given index
	 *
	 * @param index
	 * @return int mark at the current index
	 */
	public double getMark(int index) {
		return marks.get(index);
	}

	/**
	 * returns last name
	 *
	 * @return String last name
	 */
	public String getLast() {
		return last;
	}

	/**
	 * returns first name
	 *
	 * @return String first name
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * calculates the total average then returns it
	 *
	 * @return average already calculated
	 */
	public double getAverage(ArrayList<Double> weight, double totalCompletion) {
		double sum = 0;

		if (marks.size() != 0) {
			for (int i = 0; i < marks.size(); i++) {
				if (marks.get(i) != -1) {
					sum += marks.get(i) * weight.get(i);
				}
			}
			average = sum / (double) totalCompletion;
		}
		return average;
	}

	/**
	 * returns a string of student number
	 *
	 * @return String of the student number
	 */
	public String getStudentNumber() {
		return number;
	}

	/**
	 * adds a new mark
	 *
	 * @param input,
	 *            double of percentage
	 */
	public void addMark(double input) {
		marks.add(input);
	}

	/**
	 * removes a mark at the given index
	 *
	 * @param index
	 *            int of the index
	 */
	public void removeMark(int index) {
		marks.remove(index);
	}

	/**
	 * changes the mark at the index
	 *
	 * @param index
	 *            int of mark index
	 * @param mark
	 *            double of mark
	 */
	public void editMark(int index, double mark) {
		marks.set(index, mark);
	}

	/**
	 * Sets the student number
	 *
	 * @param num
	 *            this is a string
	 */
	public void setStudentNumber(String num) {
		number = num;
	}

	/**
	 * sets first name
	 *
	 * @param first
	 *            name string
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * sets last name
	 *
	 * @param String
	 *            last name
	 */
	public void setLast(String last) {
		this.last = last;
	}

}
