package com.incture.example.dynamicFiltering;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("DynamicFilter")
public class EmployeeDynamicBean {

	private String name;
	private String city;
	private String state;
	private String pincode;

	public EmployeeDynamicBean(String name, String city, String state, String pincode) {
		super();
		this.name = name;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

}
