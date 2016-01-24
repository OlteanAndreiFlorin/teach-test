package ro.sci.group2.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import ro.sci.group2.dao.StudentDao;
import ro.sci.group2.domain.Student;

@Repository
public class IMStudentDAO extends IMBaseDAO<Student> implements StudentDao {

	@Override
	public Collection<Student> searchByName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		
		Collection<Student> all = new LinkedList<Student>(getAll());
		for (Iterator<Student> it = all.iterator(); it.hasNext();) {
			Student emp = it.next();
			String ss = emp.getFirstName() + " " + emp.getLastName();
			if (!ss.toLowerCase().contains(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	

}
