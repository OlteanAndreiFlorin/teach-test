package ro.sci.group2.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.domain.Course;

public class IMCourseDAO extends IMBaseDAO<Course> implements CourseDAO {

	@Override
	public Collection<Course> searchByName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		Collection<Course> all = new LinkedList<>(getAll());
		for (Iterator<Course> it = all.iterator(); it.hasNext();) {
			Course course = it.next();
			String ss = course.getName();
			if (!ss.toLowerCase().equals(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	@Override
	public Collection<Course> searchByLevel(Integer query) {
		Collection<Course> all = new LinkedList<>(getAll());
		for (Iterator<Course> it = all.iterator(); it.hasNext();){
			Course course = it.next();
			Integer ii = course.getLevel();
			if(ii!=query){
				it.remove();
			}
		}
		return all;

	}

}
