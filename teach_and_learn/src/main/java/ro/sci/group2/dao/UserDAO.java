package ro.sci.group2.dao;

import java.util.Collection;

import ro.sci.group2.domain.User;

public interface UserDAO extends BaseDAO<User> {

	Collection<User> searchByName(String query);

	User findByUsername(String username);
}
