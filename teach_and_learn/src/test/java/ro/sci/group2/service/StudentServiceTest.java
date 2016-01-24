package ro.sci.group2.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.TeachAndLearnApplication;
import ro.sci.group2.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TeachAndLearnApplication.class)
public class StudentServiceTest {

	@Autowired
	private StudentService service;
	
	@After
	public void tearDown(){
		for (Student student:service.listAll()){
			service.delete(student.getId());
		}
	}

	@Test
	public void testSaveNewStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Assert.assertEquals("Gigi", savedStudent.getFirstName());
		Assert.assertEquals("Beeeeecali", savedStudent.getLastName());
	}

	@Test
	public void testSaveExistingStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(savedStudent.getId() > 0);
		Student savedStudent2 = service.save(student);
		Assert.assertEquals(savedStudent, savedStudent2);
	}
	@Test
	public void testDeleteStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student);
		Assert.assertTrue(service.delete(savedStudent.getId()));		
		Assert.assertNull(service.findById(savedStudent.getId()));		
	}
	@Test
	public void testDoubleDeletionStudent() {
		Student student = new Student();
		student.setFirstName("Gigi");
		student.setLastName("Beeeeecali");
		Student savedStudent = service.save(student); //save returns Student
		Assert.assertTrue(service.delete(savedStudent.getId())); //delete and findById return boolean
		Assert.assertFalse(service.delete(savedStudent.getId()));
		Assert.assertNull(service.findById(savedStudent.getId()));		
	}
	@Test
	public void testDeleteInexistingStudent() {		
		Assert.assertFalse(service.delete(-1));				
	}

}
