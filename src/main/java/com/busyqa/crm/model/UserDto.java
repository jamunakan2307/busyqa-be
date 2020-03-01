package com.busyqa.crm.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

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

	private List<String> teams;

	private List<String> roles;


	private String status;

	private String statusAsOfDay;

	@NotBlank
	@Size(min = 8, max = 40)
	private String password;

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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getTeams() {
		return teams;
	}

	public void setTeams(List<String> teams) {
		this.teams = teams;
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
		this.statusAsOfDay = LocalDateTime.now().toString();
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
}

