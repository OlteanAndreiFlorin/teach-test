package ro.sci.group2.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.sci.group2.dao.CourseDAO;
import ro.sci.group2.domain.Course;

public class JDBCCourseDAO implements CourseDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class);
	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	public JDBCCourseDAO(String host, String port, String dbName, String userName, String pass) {
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.userName = userName;
		this.pass = pass;
	}

	protected Connection newConnection() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();

			String url = new StringBuilder().append("jdbc:").append("postgresql").append("://").append(host).append(":")
					.append(port).append("/").append(dbName).append("?user=").append(userName).append("&password=")
					.append(pass).toString();
			Connection result = DriverManager.getConnection(url);
			result.setAutoCommit(false);

			return result;
		} catch (Exception ex) {
			throw new RuntimeException("Error getting DB connection", ex);
		}
	}

	public Course extractCourse(ResultSet rs) throws SQLException {
		Course course = new Course();
		course.setId(rs.getLong("id"));
		course.setName(rs.getString("course_name"));
		course.setLevel(rs.getInt("course_level"));
		return course;
	}

	@Override
	public Collection<Course> getAll() {
		Connection connection = newConnection();
		Collection<Course> result = new LinkedList<>();
		try (ResultSet rs = connection.createStatement().executeQuery("select * from course")) {
			while (rs.next()) {
				result.add(extractCourse(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting course.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;

	}

	@Override
	public Course findById(Long id) {

		Connection connection = newConnection();

		List<Course> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from course where id = " + id)) {

			while (rs.next()) {
				result.add(extractCourse(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting course.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		if (result.size() > 1) {
			throw new IllegalStateException("Multiple courses for id: " + id);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public Course update(Course model) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (model.getId() > 0) {
				ps = connection.prepareStatement(
						"update course set course_name=?, course_level=?" + "where id =? returning id");
			} else {
				ps = connection.prepareStatement(
						"insert into course (course_name, course_level)" + "values (?, ?) returning id");
			}
			ps.setString(1, model.getName());
			ps.setInt(2, model.getLevel());

			if (model.getId() > 0) {
				ps.setLong(3, model.getId());
			}
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				model.setId(rs.getLong(1));
			}
			rs.close();
			connection.commit();

		} catch (SQLException ex) {
			throw new RuntimeException("Error getting course", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return model;
	}

	@Override
	public boolean delete(Course model) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			statement.execute("delete from course where id = " + model.getId());
			if (statement.getUpdateCount() > -1) {
				result = true;
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error deleting course.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	@Override
	public Collection<Course> searchByName(String query) {
		if (query == null) {
			query = "";
		} else {
			query = query.trim();
		}

		Connection connection = newConnection();

		Collection<Course> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from course where lower(course_name) like '%" + query.toLowerCase() + "%'")) {

			while (rs.next()) {
				result.add(extractCourse(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting course.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		return result;
	}

	@Override
	public Collection<Course> searchByLevel(Integer query) {
		String lvl = Integer.toString(query);
		Connection connection = newConnection();
		Collection<Course> result = new LinkedList<>();
		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from course where course_level like '$" + lvl + "%'")) {
			while (rs.next()) {
				result.add(extractCourse(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting course");
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

}
