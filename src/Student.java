import java.util.ArrayList;

/**
 * an object that stores all the values of the student
 * @author qwertyuiop1
 */
public class Student {
	private ArrayList<Integer> marks;
	private String first, last, number;
	private double average;
	private static ArrayList<String> assignmentName;
	private static ArrayList<Integer> weight;
	private static int totalCompletion;
	/**
	 * constructor
	 */
	public Student() {
		marks = new ArrayList<Integer>();
		first = "";
		last = "";
		number = "";
		average = 100;
		assignmentName = new ArrayList<String>();
		weight = new ArrayList<Integer>();
		totalCompletion = 0;
		marks.ensureCapacity(weight.size());
	}
	
	/**
	 * @param index
	 * @return int mark at the current index
	 */
	public int getMark(int index) {
		return marks.get(index);
	}
	
	/**
	 * @return String last name 
	 */
	public String getLast() {
		return last;
	}
	
	/**
	 * @return String first name
	 */
	public String getFirst() {
		return first;
	}
	
	/**
	 * @return average already calculated
	 */
	public double getAverage() {
		setAverage();
		return average;
	}
	

	/**
	 * @return String of the student number
	 */
	public String getStudentNumber() {
		return number;
	}
	
	/**
	 * adds a new mark 
	 * @param input
	 */
	public void addMark(int input) {
		marks.add(input);
	}
	
	/**
	 * removes a mark at the given index
	 * @param index int of the index
	 */
	public void removeMark(int index) {
		marks.remove(index);
	}
	
	/**
	 * changes the mark at the index
	 * @param index int
	 * @param mark int
	 */
	public void editMark(int index, int mark) {
		marks.set(index, mark);
	}
	
	/**
	 * 
	 * @return the amount of marks there are
	 */
	public int getMarkSize() {
		return marks.size();
	}
	

	/**
	 * sets the current student average
	 */
	public void setAverage() {
		int sum = 0;
		if(marks.size() != 0) {
			for(int i = 0; i < marks.size(); i++) {
				if(marks.get(i) != -1) {
					sum += marks.get(i) * weight.get(i);
				}
			}
			average = sum / (double) totalCompletion;
		}
	}
	
	/**
	 * Sets the student number
	 * @param num this is a string
	 */
	public void setStudentNumber(String num) {
		number = num;
	}
	
	/**
	 * sets first name
	 * @param first name string
	 */
	public void setFirst(String first) {
		this.first = first;
	}
	
	/**
	 * sets last name
	 * @param last
	 */
	public void setLast(String last) {
		this.last = last;
	}

	public static String getAssignmentName(int index) {
		return assignmentName.get(index);
	}

	public static void addAssignment(String name) {
		assignmentName.add(name);
		
	}
	
	public void editAssignmentName(int index, String name) {
		assignmentName.set(index, name);
	}
	
	public void removeAssignment(int index) {
		assignmentName.remove(index);
	}
	public ArrayList<Integer> getMarkArray() {
		return marks;
	}

	public int getWeight(int index) {
		return weight.get(index);
	}

	public void setWeight(int index, int percent) {
		weight.set(index, percent);
		setTotalCompletion();
	}
	
	public static void addWeight(int percent) {
		weight.add(percent);
		setTotalCompletion();
	}
	
	public static void setTotalCompletion() {
		totalCompletion = 0;
		for(int i = 0; i < weight.size(); i++) {
			totalCompletion += weight.get(i);
		}
		
	}
	public int getTotalCompletion() {
		return totalCompletion;
	}
	
}
