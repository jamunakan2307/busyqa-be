package com.busyqa.crm.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm2 {

	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	private String firstName;

	@NotBlank
	@Size(min = 3, max = 50)
	private String lastName;

	@NotBlank
	@Size(min = 6, max = 40)
	private String username;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 40)
	private String password;

	private String phoneNumber;

	private String isRegisteredStudent;

	private String studentComments;

	private String createdTimeStamp;

	private String leadSource;

	private String status;

	private int batchId;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIsRegisteredStudent() {
		return isRegisteredStudent;
	}

	public void setIsRegisteredStudent(String isRegisteredStudent) {
		this.isRegisteredStudent = isRegisteredStudent;
	}

	public String getStudentComments() {
		return studentComments;
	}

	public void setStudentComments(String studentComments) {
		this.studentComments = studentComments;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
