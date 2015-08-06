package cn.xdf.learn.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bingo.annotation.ExcelEntity;
import com.bingo.annotation.ExcelId;
import com.bingo.annotation.ExcelJoinColumn;
import com.bingo.annotation.ExcelOneToMany;
import com.bingo.annotation.ExcelSheet;
import com.bingo.annotation.ExcelTransient;
import com.bingo.io.DefaultExcelEntityWriter;
import com.bingo.io.EntityWriter;

public class Test {

	public static void main(String[] args) {
		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < 10; i++) {
			Teacher teacher = new Teacher();
			teacher.setAge(i);
			teacher.setName("张三" + i);
			teacher.setSubject("数学");
			teacher.setUnTrs("不映射");
			List<Student> students = new ArrayList<Student>();
			for (int j = 0; j < 10; j++) {
				Student student = new Student();
				student.setBanji("高三" + j + "班");
				student.setName("李四" + j);
				students.add(student);

				List<Book> books = new ArrayList<Book>();
				for (int h = 0; h < 10; h++) {
					Book book = new Book();
					book.setName("安全出口" + h);
					book.setSubject("生活" + h);
					books.add(book);
				}
				student.setBooks(books);
			}
			teacher.setStudents(students);
			teachers.add(teacher);
		}

		EntityWriter writer = new DefaultExcelEntityWriter("d://test//test_teacher.xlsx");
		Map<Object, Object> params = new HashMap<Object, Object>();
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Teacher.class);
		classes.add(Student.class);
		classes.add(Book.class);
		params.put(DefaultExcelEntityWriter.SHEET_WIDTH_HEADS, classes);
		params.put(DefaultExcelEntityWriter.SHEET_ORDER, classes);
//		params.put(DefaultExcelEntityWriter.WRITE_EXCEL_OBJECT, true);
		writer.setParamters(params);
		writer.write(teachers, Teacher.class);
	}
}

@ExcelEntity
@ExcelSheet(name = "teacher", model = "/com/bingo/teacher.xml")
class Teacher {
	@ExcelId(auto = true)
	private Integer id;

	private String name;

	private Integer age;

	private String subject;

	@ExcelTransient
	private String unTrs;

	@ExcelOneToMany
	@ExcelJoinColumn(name = "t_id")
	private List<Student> students;

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnTrs() {
		return unTrs;
	}

	public void setUnTrs(String unTrs) {
		this.unTrs = unTrs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}

@ExcelEntity
@ExcelSheet(name = "student", model = "/com/bingo/student.xml")
class Student {
	@ExcelId(auto = true)
	private Integer id;

	private String name;

	private String banji;

	@ExcelOneToMany
	@ExcelJoinColumn(name = "s_id")
	private List<Book> books;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBanji() {
		return banji;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}

@ExcelEntity
@ExcelSheet(name = "book", model = "/com/bingo/book.xml")
class Book {
	@ExcelId(auto = true)
	private Integer id;

	private String name;

	private String subject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
