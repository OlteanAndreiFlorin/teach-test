package ro.sci.group2.dao;

import java.util.Collection;

import org.joda.time.Interval;

import ro.sci.group2.domain.Meeting;

public interface MeetingDAO extends BaseDAO<Meeting> {

	Collection<Meeting> searchByTeacher(String query);
	Collection<Meeting> searchByCity(String query);

	/**
	 * 
	 * @param interval
	 * @see <a href=
	 *      "http://www.joda.org/joda-time/apidocs/org/joda/time/Interval.html">
	 *      Joda-Interval</a> the interval in which to find meetings
	 * @return A collection with all the meetings that happen in the interval
	 */
	Collection<Meeting> searchByDate(Interval interval);
	Collection<Meeting> searchBySubject(Long id);
}
