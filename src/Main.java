import java.util.*;

import javax.swing.JFileChooser;

import java.io.*;

public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();
	static String[][] options = { 
			{"Reports" , "Students" , "Assignments", "Forgot Account" } ,
			{"Class report" , "Missing assignment", "At risk students" },
			{"Student list", "Add new student", "Remove student", "Student Information", "Edit mark"}, // needs a display of total students and averages
			{"Add assignment", "Remove assignment", "Rename assignment", "Change assignment weight"}
	};
    public static void main(String[] args) {
    	System.out.println("Welcome to the CLI Markbook of group 8\n");
    	do {
    		displayOptions();
    		if(true) {
    			break;
    		}
    	} while(true);
    	
    }
    public static String getInput() {
    	Scanner sc = new Scanner(System.in);
    	String input = sc.nextLine();
    	return input;
    }
    
    /**
     * Calculates and displays the class average
     */
    public static void classAverage() {
    	int sum = 0;
    	System.out.println("Class Average \n");
    	sortAlphabetically();
   		for(int i = 0; i < classlist.size(); i++) {
   			sum += classlist.get(i).getAverage();
   		}
   		
  		System.out.println("\nClass Average: " + (sum / classlist.size()));
  		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number", "Average");
  		
  		for(int i = 0; i < classlist.size(); i++) {
  			System.out.printf("%-20s%-20s%-20s%.2f\n", classlist.get(i).getLast(), classlist.get(i).getFirst(), classlist.get(i).getStudentNumber(), classlist.get(i).getAverage());
  		}
  		
    }
    
    /**
     * simple bubble sort to change the array in alphabetical order last name
     */
    public static void sortAlphabetically() {
    	int size = classlist.size();
    	Student placeholder = classlist.get(0);
    	
    	for(int i = 0; i < size; i++) {
    		for(int j = 1; j < size - 1; j++) {
    			if(classlist.get(j - 1).getLast().compareToIgnoreCase(classlist.get(j).getLast()) > 0) {
    				placeholder = classlist.get(j - 1);
    				classlist.set(j - 1, classlist.get(j));
    				classlist.set(j, placeholder);
    			} else if(classlist.get(j - 1).getLast().compareToIgnoreCase(classlist.get(j).getLast()) == 0 && classlist.get(j - 1).getFirst().compareToIgnoreCase(classlist.get(j).getFirst()) > 0) {
    				placeholder = classlist.get(j - 1);
    				classlist.set(j - 1, classlist.get(j));
    				classlist.set(j, placeholder);
    			}
    		}
    	}
    }

    public static void displayOptions() {
    	String input;
    	int choice1, choice2;
    	Scanner sc = new Scanner(System.in);

    	// prints out the first set of possibilities
    	for(int i = 0; i < options.length; i++) {
    		System.out.println((i + 1) + " " + options[0][i]);
    	}
    	// takes in a user input
    	System.out.print("Whats your choice(num): ");
    	do {
    		while(!sc.hasNextInt()) {
    			sc.nextLine();
    			System.out.println("Thats not a number");
    		}
    		choice1 = Integer.parseInt(sc.nextLine());
    		if(choice1 <= options[0].length && choice1 > 0) {
    			break;
    		} else {
    			System.out.println("Invalid choice");
    		}
    	}while(true);
    	
    	// shows second set of possibilities
    	System.out.println("\nHere are your options");
      	for(int i = 0; i < options[choice1].length; i++) {
    		System.out.println((i + 1) + " " + options[choice1][i]);
    	}
      	// gets user input
    	System.out.print("Whats your choice(num): ");
    	do {
    		while(!sc.hasNextInt()) {
    			sc.nextLine();
    			System.out.println("Thats not a number");
    		}
    		choice2 = Integer.parseInt(sc.nextLine());
    		if(choice2 <= options[choice1].length && choice2 > 0) {
    			break;
    		} else {
    			System.out.println("Invalid choice");
    		}
    	}while(true);
    	
    	methodCall(choice1, choice2);
    }
    
    private static void methodCall(int choice1, int choice2) {
		int call = choice2;
		for(int i = 1; i < choice1; i++) {
			call += options[i].length;
		}
		switch(call) {
			case 1:
				;
				break;
			case 2: 
				;
				break;
			case 3: 
				;
				break;
			case 4: 
				;
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
			default: break;
		}
		
	}
	public static void findPassword() {
    	String input;
    	while(true) {
    		
    		input = getInput();
    		if(input.equalsIgnoreCase("quit")) {
    			break;
    		}
    	}
    }
    

    
    /**
     * adds a new student initialized with name and number
     */
    public static void addStudent() {
    	Scanner sc = new Scanner(System.in);
		classlist.add(new Student());
		// set first name
		System.out.print("Enter the first name of the student: ");
		String input = sc.nextLine();
		input = input.toLowerCase();
		input = input.substring(0, 1).toUpperCase() + input.substring(1);
		classlist.get(classlist.size() - 1).setFirst(input);
		
		// set last name
		System.out.print("Enter the last name of the student: ");
		input = sc.nextLine();
		input = input.toLowerCase();
		input = input.substring(0, 1).toUpperCase() + input.substring(1);
		classlist.get(classlist.size() - 1).setLast(input);
		
		// student number
		System.out.print("Enter the student number: ");
		input  = sc.nextLine();
		classlist.get(classlist.size() - 1).setStudentNumber(input);
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
