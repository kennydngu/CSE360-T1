package entityClasses;

// This class defines a feedback given and student and staff that are associated with it
public class feedback {
	int id;
	String content;
	String student;
	String staff;
	
	public feedback(){
		this.id = -1;
		this.content = "";
		this.student = "";
		this.staff = "";
	}
	public feedback(int id, String content, String student, String staff){
		this.id = id;
		this.content = content;
		this.student = student;
		this.staff = staff;
	}
	
	//getter methods
	public int getId() {return this.id;}
	public String getContent() {return this.content;}
	public String getStudent() {return this.student;}
	public String getStaff() {return this.staff;}
	
	//setter methods
	public void setId(int id) {this.id = id;}
	public void setContent(String s) {this.content = s;}
	public void setStudent(String s) {this.student = s;}
	public void setStaff(String s) {this.staff = s;}
}