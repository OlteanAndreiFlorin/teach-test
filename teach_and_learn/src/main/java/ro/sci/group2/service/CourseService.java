package ro.sci.group2.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.domain.Course;

@Service
public class CourseService {

	@Autowired
	private CourseDAO dao;
	
	public Course save(Course course){
		return dao.update(course);
	}
	
	public Collection<Course>listAll(){
		return dao.getAll();
	}
	public boolean delete(long id){
		Course course = dao.findById(id);
		if(course == null){
			return false;
		}else{
			return dao.delete(course);
		}
	}
	public Course findById(long id){
		Course course = dao.findById(id);
		return course;
	}
}
