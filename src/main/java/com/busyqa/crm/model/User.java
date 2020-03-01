package com.busyqa.crm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "firstName" }), @UniqueConstraint(columnNames = { "email" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	private String firstName;

	@NotBlank
	@Size(min = 3, max = 50)
	private String lastName;

	@NotBlank
	@Size(min = 3, max = 50)
	private String username;

	@NotBlank
	@Size(min = 3, max = 50)
	private String email;

	@NotBlank
	@Size(min = 8, max = 100)
	private String password;

	private String statusAsOfDay;

	private String status;

	private String roles;

	private String teams;

	private String phoneNumber;

	private String address;

	private String city;

	private String state;

	private String zipCode;

	private String country;

	private String emergencyPhoneNumber;

	private String aboutUs;

	private String clientPassLock;

	public User() {
	}

	public User(String firstName, String lastName, String username, String email, String password, String status,
			String statusAsOfDay) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.status = status;
		this.statusAsOfDay = statusAsOfDay;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status.equals(StatusName.NO.name())) {
			this.status = status;
		} else {
			this.status = StatusName.YES.name();
		}
	}

	public String getStatusAsOfDay() {
		return statusAsOfDay;
	}

	public void setStatusAsOfDay(String statusAsOfDay) {
		this.statusAsOfDay = statusAsOfDay;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams(String teams) {
		this.teams = teams;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmergencyPhoneNumber() {
		return emergencyPhoneNumber;
	}

	public void setEmergencyPhoneNumber(String emergencyPhoneNumber) {
		this.emergencyPhoneNumber = emergencyPhoneNumber;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
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

	public String getClientPassLock() {
		return clientPassLock;
	}

	public void setClientPassLock(String clientPassLock) {
		this.clientPassLock = clientPassLock;
	}
}