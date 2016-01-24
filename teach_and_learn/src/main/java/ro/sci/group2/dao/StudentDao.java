package ro.sci.group2.dao;

import java.util.Collection;

import ro.sci.group2.domain.Student;

public interface StudentDao extends BaseDAO<Student> {

	Collection<Student> searchByName(String query);
}
