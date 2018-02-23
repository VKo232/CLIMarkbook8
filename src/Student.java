import java.util.ArrayList;

/**
 * an object that stores all the values of the student
 * @author qwertyuiop1
 */
public class Student {
	private ArrayList<Integer> marks;
	private String first, last, number;
	private double average;
	
	/**
	 * constructor
	 */
	public Student() {
		marks = new ArrayList<Integer>();
		first = "";
		last = "";
		number = "";
		average = 100;
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
					sum += marks.get(i);
				}
			}
			average = sum / (double) marks.size();
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

	
}
