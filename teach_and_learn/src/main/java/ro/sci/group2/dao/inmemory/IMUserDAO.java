package ro.sci.group2.dao.inmemory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.User;

public class IMUserDAO extends IMBaseDAO<User> implements UserDAO {

	@Override
	public Collection<User> searchByName(String query) {
		if (StringUtils.isEmpty(query)) {
			return getAll();
		}
		Collection<User> all = new LinkedList<User>(getAll());
		for (Iterator<User> it = all.iterator(); it.hasNext();) {
			User emp = it.next();
			String ss = emp.getFirstName() + " " + emp.getLastName();
			if (!ss.toLowerCase().contains(query.toLowerCase())) {
				it.remove();
			}
		}
		return all;
	}

	/**
	 * @throws UsernameNotFoundException
	 */
	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		Collection<User> all = new LinkedList<User>(getAll());
		for (Iterator<User> it = all.iterator(); it.hasNext();) {
			User emp = it.next();
			if (emp.getUsername().toLowerCase().equals(username.toLowerCase())) {
				return emp;
			}
		}
		throw new UsernameNotFoundException("He ain't there");
	}

}
