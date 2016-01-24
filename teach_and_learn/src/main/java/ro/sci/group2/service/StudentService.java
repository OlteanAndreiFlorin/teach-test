package ro.sci.group2.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.sci.group2.dao.StudentDao;
import ro.sci.group2.domain.Student;

@Service
public class StudentService {
	@Autowired
	private StudentDao dao;

	public Student save(Student student) {
		if (student.getId() <= 0 && !dao.searchByName(student.getLastName()).isEmpty()) {
			throw new IllegalArgumentException("Duplicated Student");
		}
		return dao.update(student);
	}

	public Collection<Student> listAll() {
		return dao.getAll();
	}

	public boolean delete(long id) {
		Student student = dao.findById(id);
		if (student == null) {
			return false;
		} else {
			return dao.delete(student);
		}
	}

	public Student findById(long id) {
		Student student = dao.findById(id);
		return student;
	}

}
