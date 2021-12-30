package com.findmyroom.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.findmyroom.model.Image;
import com.findmyroom.model.Sublease;

@Service
public class SubleaseService {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Iterable<Sublease> getAllSubleases() {
		List<Sublease> subleaseList = new ArrayList<>();
		String sql = "SELECT * FROM subleases";
		Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		rows.stream().map((row) -> {
			return (Integer) row.get("subleaseID");
		}).forEach((id) -> {
			subleaseList.add(getSublease(id));
		});
		return subleaseList;
	}

	public List<Sublease> getSubleases(List<Integer> subleaseIds) {
		return subleaseIds.stream().map((id) -> {
			return getSublease(id);
		}).collect(Collectors.toList());
	}

	public Sublease getSublease(Integer id) {
		return jdbcTemplate.query("SELECT * FROM subleases WHERE subleaseID = ?", (PreparedStatement ps) -> {
			ps.setInt(1, id);
		}, (ResultSet rs) -> {
			if (rs.next()) {
				Sublease sublease = new Sublease();
				sublease.setSubleaseID(id);
				sublease.setAuthorID(rs.getInt("authorID"));
				sublease.setAddress(rs.getString("address"));
				sublease.setDirection(rs.getString("direction"));
				sublease.setSqFootage(rs.getInt("sqFootage"));
				sublease.setPrice(rs.getInt("price"));
				sublease.setNumBeds(rs.getInt("numBeds"));
				sublease.setDateAvailability(rs.getTimestamp("dateAvailability"));
				sublease.setImages(getImagesFromSublease(id));
				return sublease;
			}
			return null;
		});
	}

	public List<List<Image>> getImagesFromSubleases(List<Integer> subleaseIds) {
		return subleaseIds.stream().map((id) -> {
			return getImagesFromSublease(id);
		}).collect(Collectors.toList());
	}

	public List<Image> getImagesFromSublease(Integer id) {
		return jdbcTemplate.query("SELECT imageID, image FROM images WHERE subleaseID = ?", (PreparedStatement ps) -> {
			ps.setInt(1, id);
		}, (ResultSet rs) -> {
			List<Image> images = new ArrayList<>();
			while (rs.next()) {
				images.add(new Image(rs.getInt(1), rs.getString(2)));
			}
			return images;
		});
	}

	public int addSublease(Integer userid, String location, String direction, String squareFt, String price,
			String beds, String da) {
		String sql = "INSERT INTO subleases (authorID, address, direction, sqFootage, price, numBeds, dateAvailability, datePosted) VALUES (?,?,?,?,?,?,?,?)";
		int sqft = squareFt == null ? 0 : Integer.parseInt(squareFt);
		int pr = price == null ? 0 : Integer.parseInt(price);
		int beds2 = beds == null ? 0 : Integer.parseInt(beds);
		TimeZone tz = TimeZone.getTimeZone("GMT");
		Calendar calendar = Calendar.getInstance(tz);
		Object[] params = { userid, location, direction, sqft, pr, beds2, da, calendar };
		jdbcTemplate.update(sql, params);
		return jdbcTemplate.query("SELECT LAST_INSERT_ID()", (ResultSet rs) -> {
			rs.next();
			return rs.getInt(1);
		});
	}

	public void removeSublease(Integer userid, Integer subleaseid) {
		String sql = "DELETE FROM subleases WHERE authorID=? AND subleaseID=?";
		String fav = "DELETE FROM favorites WHERE subleaseID=?";
		String img = "DELETE FROM images WHERE subleaseID=?";
		Object[] params = { userid, subleaseid };
		Object[] params2 = { subleaseid };
		jdbcTemplate.update(fav, params2);
		jdbcTemplate.update(img, params2);
		jdbcTemplate.update(sql, params);
	}

	public Iterable<Sublease> sortSubleases(String order) {
		if (order.equals("low")) {
			List<Sublease> subleaseList = new ArrayList<>();
			String sql = "SELECT * FROM subleases ORDER BY price ASC";
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});
			return subleaseList;
		} else if (order.equals("high")) {
			List<Sublease> subleaseList = new ArrayList<>();
			String sql = "SELECT * FROM subleases ORDER BY price DESC";
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});
			return subleaseList;
		}
		return null;
	}

	public Iterable<Sublease> filterAndSort(String filter, String value, String order) {
		List<Sublease> subleaseList = new ArrayList<>();
		if (filter.equals("direction")) {
			String temp = "";
			if (order.equals("low")) {
				temp = "ASC";
			} else if (order.equals("high")) {
				temp = "DESC";
			}
			String sql = "SELECT * FROM subleases WHERE direction = '" + value + "' ORDER BY price " + temp;
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});
			return subleaseList;
		} else if (filter.equals("numBeds")) {
			String temp = "";
			if (order.equals("low")) {
				temp = "ASC";
			} else if (order.equals("high")) {
				temp = "DESC";
			}
			String sql = "SELECT * FROM subleases WHERE numBeds = " + value + " ORDER BY price " + temp;
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});
			return subleaseList;
		}
		return null;
	}

	public Iterable<Sublease> filter(String filter, String value) {
		if (filter.equals("direction")) {

			List<Sublease> subleaseList = new ArrayList<>();
			String sql = "SELECT * FROM subleases WHERE direction = '" + value + "'";
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});
			return subleaseList;
		} else if (filter.equals("numBeds")) {
			List<Sublease> subleaseList = new ArrayList<>();
			String sql = "SELECT * FROM subleases WHERE numBeds = " + value;
			Collection<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			rows.stream().map((row) -> {
				return (Integer) row.get("subleaseID");
			}).forEach((id) -> {
				subleaseList.add(getSublease(id));
			});

			return subleaseList;
		}
		return null;
	}

	public void updateSublease(Integer id, String field, String value) {
		jdbcTemplate.update("UPDATE subleases SET " + field + " = '" + value + "' WHERE subleaseID = " + id);
	}
}
