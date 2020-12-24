package com.incture.example.versioning;

public class PersonBean {

	private String name;
	private String place;
	private String region;

	public PersonBean() {

	}

	public PersonBean(String name, String place, String region) {
		super();
		this.name = name;
		this.place = place;
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
