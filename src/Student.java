import java.io.Serializable;
import java.util.ArrayList;

/**
 * an object that stores all the values of the student
 * 
 * @author qwertyuiop1
 */
public class Student implements Serializable {

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
