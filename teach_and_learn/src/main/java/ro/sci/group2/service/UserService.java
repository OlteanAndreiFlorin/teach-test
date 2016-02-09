package ro.sci.group2.service;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.Course;
import ro.sci.group2.domain.User;

@Service
public class UserService {
	@Autowired
	private UserDAO dao;
	private CourseDAO courseDao;

	public User save(User user) {
		return dao.update(user);
	}

	public Collection<User> listAll() {
		return dao.getAll();
	}

	/**
	 * 
	 * @param order
	 *            the order in which to sort the list (must be "firstascend",
	 *            "firstdescend","lastascend"or"lastdescend")
	 * @return a sorted collection of users
	 * @throws IllegalArgumentException
	 *             if the order that has been passed is invalid
	 */
	public Collection<User> listAll(String order) throws IllegalArgumentException {
		UserSorter sorter = new UserSorter();
		switch (order.toLowerCase()) {
		case "firstascend":
			return sorter.sortByFirstNameAscending(dao.getAll());
		case "firstdescend":
			return sorter.sortByFirstNameDescending(dao.getAll());
		case "lastascend":
			return sorter.sortByLastNameAscending(dao.getAll());
		case "lastdescend":
			return sorter.sortByLastNameDescending(dao.getAll());
		default:
			throw new IllegalArgumentException("Order not valid ");
		}

	}

	public boolean delete(long id) {
		User user = dao.findById(id);
		if (user == null) {
			return false;
		} else {
			return dao.delete(user);
		}
	}

	public User findById(long id) {
		User user = dao.findById(id);
		return user;
	}
	
	public User findByUserName(String username) {
		User user = dao.findByUsername(username);
		return user;
	}
	
	public Collection<User> findByName(String query){
		return dao.searchByName(query);
	}

	public void addCourse(long userId, Course course) {
		User user = dao.findById(userId);
		Collection<Course> courses = new LinkedList<>(user.getCourses());
		if (!courses.contains(course)) {
			courses.add(course);
			user.setCourses(courses);
		}
	}

	public boolean removeCourse(long id, Course course) {
		User user = dao.findById(id);
		Collection<Course> courses = new LinkedList<>(user.getCourses());
		if (courses.remove(course)) {
			user.setCourses(courses);
			return true;
		} else {
			return false;
		}
	}

}
