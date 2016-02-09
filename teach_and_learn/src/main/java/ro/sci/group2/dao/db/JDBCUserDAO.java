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

import javax.print.attribute.Size2DSyntax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.sci.group2.dao.UserDAO;
import ro.sci.group2.domain.Gender;
import ro.sci.group2.domain.Role;
import ro.sci.group2.domain.User;

/**
 * JDBC implementation for{@link UserDAO}}
 * 
 * @author Oltean Andrei
 *
 */
public class JDBCUserDAO implements UserDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(JDBCUserDAO.class);
	private String host;
	private String port;
	private String dbName;
	private String userName;
	private String pass;

	public JDBCUserDAO(String host, String port, String dbName, String userName, String pass) {
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

	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setAddress(rs.getString("address"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("phone"));
		user.setGender(Gender.valueOf(rs.getString("gender")));
		Collection<Role> roles = new LinkedList<>();
		String dbRole = rs.getString("role");
		String[] r = dbRole.split(";&;");
		for (String roleString : r) {
			roles.add(Role.valueOf((roleString.trim())));
		}
		user.setRoles(roles);
		return user;
	}

	@Override
	public Collection<User> getAll() {
		Connection connection = newConnection();
		Collection<User> result = new LinkedList<>();
		try (ResultSet rs = connection.createStatement().executeQuery("select * from person")) {
			while (rs.next()) {
				result.add(extractUser(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting user.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	@Override
	public User findById(Long id) {
		Connection connection = newConnection();

		List<User> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement().executeQuery("select * from person where id = " + id)) {

			while (rs.next()) {
				result.add(extractUser(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting user.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}

		if (result.size() > 1) {
			throw new IllegalStateException("Multiple Users for id: " + id);
		}
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public User update(User model) {
		Connection connection = newConnection();
		try {
			PreparedStatement ps = null;
			if (model.getId() > 0) {
				ps = connection.prepareStatement(
						"update person set username=?, password=?, first_name=?, last_name=?, address=?, email=?, phone=?, gender = ?, role=? "
								+ "where id = ? returning id");

			} else {

				ps = connection.prepareStatement(
						"insert into person (username, password, first_name, last_name, address, email, phone, gender, role) "
								+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?) returning id");

			}
			ps.setString(1, model.getUsername());
			ps.setString(2, model.getPassword());
			ps.setString(3, model.getFirstName());
			ps.setString(4, model.getLastName());
			ps.setString(5, model.getAddress());
			ps.setString(6, model.getEmail());
			ps.setString(7, model.getPhone());
			ps.setString(8, model.getGender().name());
			StringBuilder roles = new StringBuilder();
			for (Role r : model.getRoles()) {
				roles.append(r.name() + ";&;");
			}
			ps.setString(9, roles.toString());

			if (model.getId() > 0) {
				ps.setLong(10, model.getId());
			}

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				model.setId(rs.getLong(1));
			}
			rs.close();
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting user.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return model;
	}

	@Override
	public boolean delete(User model) {
		boolean result = false;
		Connection connection = newConnection();
		try {
			Statement statement = connection.createStatement();
			statement.execute("delete from person where id = " + model.getId());
			if (statement.getUpdateCount() > -1) {
				result = true;
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error deleting user.", ex);
		} finally {
			try {
				connection.close();
			} catch (Exception ex) {

			}
		}
		return result;
	}

	@Override
	public Collection<User> searchByName(String query) {
		if (query == null) {
			query = "";
		} else {
			query = query.trim();
		}

		Connection connection = newConnection();

		Collection<User> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from person where lower(first_name || ' ' || last_name) like '%"
						+ query.toLowerCase() + "%'")) {

			while (rs.next()) {
				result.add(extractUser(rs));
			}
			connection.commit();
		} catch (SQLException ex) {

			throw new RuntimeException("Error getting user.", ex);
		}

		return result;
	}

	@Override
	public User findByUsername(String username) {
		if (username == null) {
			username = "";
		} else {
			username = username.trim();
		}
		Connection connection = newConnection();

		List<User> result = new LinkedList<>();

		try (ResultSet rs = connection.createStatement()
				.executeQuery("select * from person where lower(username) like '%" + username.toLowerCase() + "%'")) {
			while (rs.next()) {
				result.add(extractUser(rs));
			}
			connection.commit();
		} catch (SQLException ex) {
			throw new RuntimeException("Error getting user", ex);
		}
		if (result.size() > 1) {
			throw new IllegalStateException("Multiple users for username");
		}
		return result.isEmpty() ? null : result.get(0);
	}

}
