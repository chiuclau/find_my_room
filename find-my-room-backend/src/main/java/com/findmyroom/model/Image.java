package com.findmyroom.model;

public class Image {
	private int imageID;
	private String src;
	public Image(int imageID, String src) {
		this.imageID = imageID;
		this.src = src;
	}
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
}
