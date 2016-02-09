package ro.sci.group2.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.sci.group2.domain.User;

@Service
public class UserSorter {

	private static final Comparator<User> COMPARE_BY_FIRST_NAME_ASCENDING = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {

			return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
		}
	};
	private static final Comparator<User> COMPARE_BY_FIRST_NAME_DESCENDING = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {

			return -o1.getFirstName().compareTo(o2.getFirstName());
		}
	};
	private static final Comparator<User> COMPARE_BY_LAST_NAME_ASCENDING = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {

			return o1.getLastName().compareTo(o2.getLastName());
		}
	};
	private static final Comparator<User> COMPARE_BY_LAST_NAME_DESCENDING = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {

			return -o1.getLastName().compareTo(o2.getLastName());
		}
	};

	/**
	 * 
	 * @param col
	 *            a unsorted collection of students
	 * @return a sorted collection of students by first name ascending
	 */
	public Collection<User> sortByFirstNameAscending(Collection<User> col) {
		List<User> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol, COMPARE_BY_FIRST_NAME_ASCENDING);
		return sortedCol;
	}

	/**
	 * 
	 * @param col
	 *            a unsorted collection of students
	 * @return a sorted collection of students by last name ascending
	 */
	public Collection<User> sortByLastNameAscending(Collection<User> col) {
		List<User> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol, COMPARE_BY_LAST_NAME_ASCENDING);
		return sortedCol;
	}

	/**
	 * 
	 * @param col
	 *            a unsorted collection of students
	 * @return a sorted collection of students by first name descending
	 */
	public Collection<User> sortByFirstNameDescending(Collection<User> col) {
		List<User> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol, COMPARE_BY_FIRST_NAME_DESCENDING);
		return sortedCol;
	}

	/**
	 * 
	 * @param col
	 *            a unsorted collection of students
	 * @return a sorted collection of students by last name descending
	 */
	public Collection<User> sortByLastNameDescending(Collection<User> col) {
		List<User> sortedCol = new LinkedList<>(col);
		Collections.sort(sortedCol, COMPARE_BY_LAST_NAME_DESCENDING);
		return sortedCol;
	}

	public UserSorter() {

	}
}
