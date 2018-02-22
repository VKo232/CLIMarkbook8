import java.util.ArrayList;

/*
 * This is just the student object that holds all the student data and will contain methods to add, edit and delete marks
 * 
 * @author VKo232
 */
public class Student {
	ArrayList<Integer> marks;
	String first, last, number;
	int average;
	ArrayList<Boolean> completed;
	
	/*
	 * constructor
	 */
	public Student() {
		marks = new ArrayList<Integer>();
		first = "";
		last = "";
		number = "";
		average = 100;
		completed = new ArrayList<Boolean>();
	}
	
	public ArrayList<Integer> getAllMarks() {
		return marks;
	}
	
}
