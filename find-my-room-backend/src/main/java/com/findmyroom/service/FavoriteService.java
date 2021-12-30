package com.findmyroom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void favoriteSublease(Integer userid, Integer subleaseid) {
		String sql = "INSERT INTO favorites (userID, subleaseID) VALUES (?,?)";
		Object[] params = { userid, subleaseid };
		jdbcTemplate.update(sql, params);
	}

	public void unfavoriteSublease(Integer userid, Integer subleaseid) {
		String sql = "DELETE FROM favorites WHERE userID=? AND subleaseID=?";
		Object[] params = { userid, subleaseid };
		jdbcTemplate.update(sql, params);
	}
}
