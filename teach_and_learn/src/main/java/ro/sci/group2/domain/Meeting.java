package ro.sci.group2.domain;

import java.util.Collection;

import org.joda.time.Interval;
import org.joda.time.ReadableInterval;


/**
 * The meeting class models a meeting that a teacher can initiate inside the
 * meeting the following information are saved: The teacher who initiated the
 * meeting, the city and location where it takes place,the time interval of the
 * meeting,observations that the teacher might add for the meeting the course
 * that will be taught and a list with the attendees;
 * 
 * @author Andrei 
 *
 */
public class Meeting extends AbstractModel {

	private User teacher;
	private String city;
	private String location;
	private ReadableInterval meetingInterval;
	private String observation;
	private Collection<User> attendees;
	private Course course;

	public Meeting(long id) {
		setId(id);
	}

	public Meeting() {
		this(0);
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ReadableInterval getMeetingInterval() {
		return meetingInterval;
	}

	public void setMeetingInterval(Interval meetingInterval) {
		this.meetingInterval = meetingInterval;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Collection<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(Collection<User> attendees) {
		this.attendees = attendees;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
