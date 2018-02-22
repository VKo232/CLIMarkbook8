import java.util.*;

public class Hello {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
        System.out.print("Hello worlds");
        ArrayList<Student> classlist = new ArrayList<Student>();
        
        while(true) {
        	String line = sc.nextLine();
        	if(line.equals("add")) {
        		classlist.add(new Student());
        		System.out.println(classlist.size() + "\n" + classlist.get(0).getFirst());
        	} else {
        		break;
        	}
        }
    }
}
