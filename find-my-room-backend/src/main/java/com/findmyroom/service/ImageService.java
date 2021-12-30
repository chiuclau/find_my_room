package com.findmyroom.service;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/*
 * Solely used to add/remove images from subleases.
 */
@Service
public class ImageService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void addImageToSublease(Integer id, String src) {
		jdbcTemplate.update("INSERT INTO images (subleaseID, image) VALUES (?,?)", (PreparedStatement ps) -> {
			ps.setInt(1, id);
			ps.setString(2, src);
		});
	}

	public void removeImageFromSublease(Integer id, Integer imageID) {
		jdbcTemplate.update("DELETE FROM images WHERE subleaseID=? AND imageID=?", (PreparedStatement ps) -> {
			ps.setInt(1, id);
			ps.setInt(2, imageID);
		});
	}
}