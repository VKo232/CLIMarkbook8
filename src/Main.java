import java.util.*;
import java.io.*;

public class Main {
	static ArrayList<Student> classlist = new ArrayList<Student>();

    public static void main(String[] args) {
    	
    	System.out.println("Welcome to the CLI Markbook");
    	do {
    		classlist.add(new Student());
    		classAverage();
    		if(true) {
    			break;
    		}
    	} while(true);
    	
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
   		
  		System.out.println("Class Average: " + (sum / classlist.size()));
  		System.out.printf("%-20s%-20s%-20s%s\n", "Last name", "First name", "Student number","Average");
  		
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
    public static String getInput() {
    	Scanner sc = new Scanner(System.in);
    	String s = sc.nextLine();
    	return s;
    }
    	
    public static void displayOptions() {
    	
    }
    
    public static void findPassword() {
    	
    }
    
    public static void callMethods() {
    	
    }
    
}
