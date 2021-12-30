package com.findmyroom.model;

import java.util.List;

public class User {
	private Integer id;
	// private String username;
	private String password;
	private String name;
	private String about;
	private String email;
	private String phone;
	private List<Sublease> postedSubleases;
	private List<Sublease> favoritedSubleases;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Sublease> getPostedSubleases() {
		return postedSubleases;
	}

	public void setPostedSubleases(List<Sublease> postedSubleases) {
		this.postedSubleases = postedSubleases;
	}
	
	public List<Sublease> getFavoritedSubleases() {
		return favoritedSubleases;
	}

	public void setFavoritedSubleases(List<Sublease> favoritedSubleases) {
		this.favoritedSubleases = favoritedSubleases;
	}
}