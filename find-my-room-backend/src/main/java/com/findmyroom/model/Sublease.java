package com.findmyroom.model;

import java.sql.Timestamp;
import java.util.List;

public class Sublease {
	private int subleaseID;
	private int authorID;
	private String address;
	private String direction;
	private int sqFootage;
	private int price;
	private int numBeds;
	private Timestamp dateAvailability;
	private List<Image> images; 
	public int getSubleaseID() {
		return subleaseID;
	}
	public void setSubleaseID(int subleaseID) {
		this.subleaseID = subleaseID;
	}
	public int getAuthorID() {
		return authorID;
	}
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public int getSqFootage() {
		return sqFootage;
	}
	public void setSqFootage(int sqFootage) {
		this.sqFootage = sqFootage;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getNumBeds() {
		return numBeds;
	}
	public void setNumBeds(int numBeds) {
		this.numBeds = numBeds;
	}
	public Timestamp getDateAvailability() {
		return dateAvailability;
	}
	public void setDateAvailability(Timestamp dateAvailability) {
		this.dateAvailability = dateAvailability;
	}
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
}
