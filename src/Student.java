import java.util.ArrayList;

/**
 * an object that stores all the values of the student
 * @author qwertyuiop1
 */
public class Student {
	private ArrayList<Integer> marks;
	private String first, last, number;
	private double average;
	private ArrayList<Boolean> completed;
	
	/**
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
	 * @param index which is assignment number starting from 0
	 * @return boolean of whether it is completed or not
	 */
	public boolean getCompleted(int index) {
		return completed.get(index);
	}
	
	/**
	 * sets the assignment at that index to be marked complete, or true
	 * @param index 
	 */
	public void setCompleted(int index) {
		completed.set(index, true);
	}
	
	/**
	 * adds a new assignment that is marked incomplete
	 */
	public void addIncomplete() {
		completed.add(false);
	}
	
	/**
	 * removes an assignment
	 * @param index for assignment to be removed
	 */
	public void removeAssignment(int index) {
		completed.remove(index);
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
	 * 
	 * @return the amount of assignments there are
	 */
	public int getAssignmentSize() {
		return completed.size();
	}
	
	/**
	 * sets the current student average
	 */
	public void setAverage() {
		int sum = 0;
		for(int i = 0; i < marks.size(); i++) {
			sum += marks.get(i);
		}
		average = sum / (double) marks.size();
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
	
	
}
