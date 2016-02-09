package ro.sci.group2.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.ApplicationTest;
import ro.sci.group2.domain.Course;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class CourseServiceTest {

	@Autowired
	private CourseService service;

	@After
	public void clenUp() {
		for (Course c : service.listAll()) {
			service.delete(c.getId());
		}
	}

	@Test
	public void listEmptyDb() {
		Assert.assertTrue(service.listAll().isEmpty());
	}

	@Test
	public void testSaveNewCourse() {
		Course course = new Course();
		course.setName("mate");
		course.setLevel(2);
		Course savedCourse = service.save(course);
		Assert.assertTrue(savedCourse.getId() > 0);
		Assert.assertEquals("mate", savedCourse.getName());
		Assert.assertEquals(2, savedCourse.getLevel());
	}

	@Test
	public void testSaveExistingCourse() {
		Course co = new Course();
		co.setName("romana");
		co.setLevel(1);
		Course savedCo = service.save(co);
		Course savedCo2 = service.save(co);
		Assert.assertEquals(savedCo, savedCo2);
	}

	@Test
	public void testDeletExistingCourse() {
		Course co = new Course();
		co.setName("Info");
		co.setLevel(1);
		service.save(co);
		Assert.assertTrue(service.delete(co.getId()));
		Assert.assertNull(service.findById(co.getId()));
	}

	@Test
	public void testDeleteInexistingCourse() {
		Assert.assertFalse(service.delete(40));
	}

	@Test
	public void testMultipleDeleteCourse() {
		Course course = new Course();
		course.setName("Info");
		course.setLevel(1);
		service.save(course);
		Assert.assertTrue(service.delete(course.getId()));
		Assert.assertFalse(service.delete(course.getId()));
		Assert.assertNull(service.findById(course.getId()));
	}

}
