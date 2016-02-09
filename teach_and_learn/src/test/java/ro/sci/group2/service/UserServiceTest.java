package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ro.sci.group2.ApplicationTest;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.Gender;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTest.class)
public class UserServiceTest {

	@Autowired
	private UserService service;

	@After
	public void tearDown() {
		for (User user : service.listAll()) {
			service.delete(user.getId());
		}
	}

	@Test
	public void testEmptyGetAll(){
		Collection<User> all=service.listAll(); 
		Assert.assertTrue(all.isEmpty());
	}
	@Test
	public void testFindById(){
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertEquals(savedUser,service.findById(savedUser.getId()) );
	}
	@Test
	public void testSaveNewuser() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		user.setGender(Gender.MALE);
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		Assert.assertEquals("Gigi", savedUser.getUsername());
		Assert.assertEquals("Becali", savedUser.getPassword());
	}

	@Test
	public void testSaveExistinguser() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(savedUser.getId() > 0);
		User savedUser2 = service.save(user);
		Assert.assertEquals(savedUser, savedUser2);
	}

	@Test
	public void testDeleteUser() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
	}

	@Test
	public void testDoubleDeletionuser() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		User savedUser = service.save(user);
		Assert.assertTrue(service.delete(savedUser.getId()));
		Assert.assertFalse(service.delete(savedUser.getId()));
		Assert.assertNull(service.findById(savedUser.getId()));
	}

	@Test
	public void testDeleteInexistinguser() {
		Assert.assertFalse(service.delete(-1));
	}

	@Test
	public void testAddCourse() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(user.getCourses().contains(mate));
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(user.getId(), romana);
		Assert.assertTrue(user.getCourses().contains(romana));
		Assert.assertEquals(2, user.getCourses().size());
	}

	@Test
	public void testDoubleAddCourse() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(user.getCourses().contains(mate));
		Assert.assertEquals(1, user.getCourses().size());
	}

	@Test
	public void testRemoveExistingCourse() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Course romana = new Course();
		romana.setName("romana");
		romana.setLevel(5);
		service.addCourse(user.getId(), mate);
		service.addCourse(user.getId(), romana);
		Assert.assertTrue(user.getCourses().contains(mate));
		Assert.assertTrue(service.removeCourse(user.getId(), mate));
		Assert.assertEquals(1, user.getCourses().size());
		Assert.assertTrue(service.removeCourse(user.getId(), romana));
		Assert.assertTrue(user.getCourses().isEmpty());
	}

	@Test
	public void testDoubleRemoveCourse() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		user.setGender(Gender.MALE);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		service.addCourse(user.getId(), mate);
		Assert.assertTrue(service.removeCourse(user.getId(), mate));
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}

	@Test
	public void testRemoveInexistingCourse() {
		User user = new User();
		user.setUsername("Gigi");
		user.setPassword("Becali");
		user.setFirstName("");
		user.setLastName("");
		user.setAddress("");
		user.setEmail("");
		user.setPhone("");
		user.setGender(Gender.MALE);
		Collection<Role> roles=new LinkedList<>();
		roles.add(Role.ROLE_ADMIN);
		roles.add(Role.ROLE_STUDENT);
		user.setRoles(roles);
		service.save(user);
		Course mate = new Course();
		mate.setName("mate");
		mate.setLevel(12);
		Assert.assertFalse(service.removeCourse(user.getId(), mate));
	}
}
