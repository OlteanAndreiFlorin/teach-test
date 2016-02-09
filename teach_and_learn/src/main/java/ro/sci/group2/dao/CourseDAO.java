package ro.sci.group2.dao;

import java.util.Collection;

import ro.sci.group2.domain.Course;

public interface CourseDAO extends BaseDAO<Course> {

	Collection<Course> searchByName(String query);
	Collection<Course> searchByLevel(Integer query);

}
