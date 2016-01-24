package ro.sci.group2.dao;

import java.util.Collection;

import ro.sci.group2.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {

	Collection<T> getAll();
	
	T findById(Long id);
	
	T update(T model);
	
	boolean delete(T model);
}
