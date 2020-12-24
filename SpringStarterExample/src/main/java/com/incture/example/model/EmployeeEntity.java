package com.incture.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.jfr.Timestamp;

@Entity
@Table(name = "EMPLOYEE")
@ApiModel(description = "All details about the employee")
public class EmployeeEntity {

	// @Id
	// @GeneratedValue(strategy = GenerationType.z)

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	@NotBlank(message = "First Name is mandatory")
	@ApiModelProperty(notes = "First Name is mandatory")
	private String firstName;

	@Column(name = "last_name")
	@NotBlank(message = "Last Name is mandatory")
	@ApiModelProperty(notes = "Last Name is mandatory")
	private String lastName;

	@Column(name = "email", nullable = false, length = 200)
	@ApiModelProperty(notes = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
	private String email;

	@Column(name = "designation", nullable = false, length = 200)
	private String designation;

	@Column(name = "dob")
	private String dateOfBirth;

	@Column(name = "company", length = 200)
	private String company;

	@Column(name = "phone", length = 10)
	private String phone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}
}