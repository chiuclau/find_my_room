package com.findmyroom.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.findmyroom.model.User;

@Service
public class UserService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	SubleaseService subleaseService;

	/**
	 * For internal debug and enumeration
	 * 
	 * @return JSON serialized list of all users
	 */
	public List<User> getAllUsers() {
		List<User> usersList = new ArrayList<>();
		String sql = "SELECT * FROM users";
		Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		rows.stream().map((row) -> {
			User user = new User();
			int id = (Integer) row.get("userID");
			user.setId(id);
			// user.setUsername((String) row.get("username"));
			// user.setPassword((String) row.get("password"));
			user.setName((String) row.get("name"));
			user.setAbout((String) row.get("about"));
			user.setEmail((String) row.get("email"));
			user.setPhone((String) row.get("phone"));
			user.setPostedSubleases(subleaseService.getSubleases(getPostedSubleasesFromUser(id)));
			user.setFavoritedSubleases(subleaseService.getSubleases(getFavoritedSubleasesFromUser(id)));
			return user;
		}).forEach((user) -> {
			usersList.add(user);
		});
		return usersList;
	}

	public User getUser(Integer id) {
		String sql = "SELECT * FROM users WHERE userID = ?";
		return jdbcTemplate.query(sql, (PreparedStatement ps) -> {
			ps.setInt(1, id);
		}, (ResultSet rs) -> {
			if (rs.next()) {
				User user = new User();
				user.setId(id);
				// user.setUsername(rs.getString("username"));
				// user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setAbout(rs.getString("about"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setPostedSubleases(subleaseService.getSubleases(getPostedSubleasesFromUser(id)));
				user.setFavoritedSubleases(subleaseService.getSubleases(getFavoritedSubleasesFromUser(id)));
				return user;
			}
			return null;
		});
	}

	public User getUserBasicInformation(Integer id) {
		String sql = "SELECT * FROM users WHERE userID = ?";
		return jdbcTemplate.query(sql, (PreparedStatement ps) -> {
			ps.setInt(1, id);
		}, (ResultSet rs) -> {
			if (rs.next()) {
				User user = new User();
				user.setId(id);
				// user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setAbout(rs.getString("about"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				return user;
			}
			return null;
		});
	}

	public boolean isIdTaken(int id) {
		String sql = "SELECT * FROM users WHERE userID = ?";
		return jdbcTemplate.query(sql, (PreparedStatement ps) -> {
			ps.setInt(1, id);
		}, (ResultSet rs) -> {
			return rs.next();
		});
	}

	public boolean isEmailTaken(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
		return jdbcTemplate.query(sql, (PreparedStatement ps) -> {
			ps.setString(1, email);
		}, (ResultSet rs) -> {
			return rs.next();
		});
	}

	/**
	 * User login
	 * 
	 * @param username
	 * @param password
	 * @return the userID of the user, or -1 if the combination doesn't exist
	 */
	public Integer checkUser(String email, String password) {
		return jdbcTemplate.query(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con
						.prepareStatement("SELECT userID FROM users WHERE email=? AND password=SHA2(?,256)");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				return preparedStatement;
			}
		}, new ResultSetExtractor<Integer>() {

			@Override
			public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
				return -1;
			}
		});
	}

	/**
	 * User sign up
	 * 
	 * @param username
	 * @param password
	 * @return the userID of the created user, or -1 if the username already exists
	 */
	public synchronized Integer addUser(String email, String password) {
		if (!isEmailTaken(email)) {
			jdbcTemplate.update(new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement preparedStatement = con
							.prepareStatement("INSERT into users (email, password) VALUES (?,SHA2(?,256))");
					preparedStatement.setString(1, email);
					preparedStatement.setString(2, password);
					return preparedStatement;
				}
			});
			return jdbcTemplate.query("SELECT userID FROM users where email=? AND password=SHA2(?,256)",
					(PreparedStatement ps) -> {
						ps.setString(1, email);
						ps.setString(2, password);
					}, (ResultSet rs) -> {
						rs.next();
						return rs.getInt(1);
					});
		} else {
			return -1;
		}
	}
	
	public boolean updateUser(User user) {
		// String usersql = "UPDATE users SET username=? WHERE userID=?";
		String pwsql = "UPDATE users SET password=? WHERE userID=?";
		String namesql = "UPDATE users SET name=? WHERE userID=?";
		String aboutsql = "UPDATE users SET about=? WHERE userID=?";
		String emailsql = "UPDATE users SET email=? WHERE userID=?";
		String phonesql = "UPDATE users SET phone=? WHERE userID=?";
		int id = user.getId();
//		String username = user.getUsername();
//		if (!username.equals(null)) {
//			Object[] params = { username, id };
//			jdbcTemplate.update(usersql, params);
//		}
		String password = user.getPassword();
		if (!password.equals(null)) {
			Object[] params = { password, id };
			jdbcTemplate.update(pwsql, params);
		}
		String name = user.getName();
		if (!name.equals(null)) {
			Object[] params = { name, id };
			jdbcTemplate.update(namesql, params);
		}
		String about = user.getAbout();
		if (!about.equals(null)) {
			Object[] params = { about, id };
			jdbcTemplate.update(aboutsql, params);
		}
		String email = user.getEmail();
		if (!email.equals(null)) {
			Object[] params = { email, id };
			jdbcTemplate.update(emailsql, params);
		}
		String phone = user.getPhone();
		if (!phone.equals(null)) {
			Object[] params = { phone, id };
			jdbcTemplate.update(phonesql, params);
		}
		return true;
	}

	public List<Integer> getPostedSubleasesFromUser(Integer id) {
		return jdbcTemplate.queryForList("SELECT subleaseID FROM subleases WHERE authorID=?", Integer.class, id);
	}

	public List<Integer> getFavoritedSubleasesFromUser(Integer id) {
		return jdbcTemplate.queryForList("SELECT subleaseID FROM favorites WHERE userID=?", Integer.class, id);
	}
}
