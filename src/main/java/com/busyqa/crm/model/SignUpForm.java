package com.busyqa.crm.model;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignUpForm {
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

	private String teams;

	private String roles;

	private String status;

	private String statusAsOfDay;

	private String clientStatus;

	private String paymentPlan;

	private String paymentStatus;

	@NotBlank
	@Size(min = 8, max = 40)
	private String password;

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
		this.statusAsOfDay = LocalDateTime.now().toString();
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		if (clientStatus.equals(null)) {
			this.clientStatus = ClientStatusName.LEADS.name();
		} else {
			this.clientStatus = clientStatus;
		}
	}

	public String getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		if (paymentPlan.equals(null)) {
			this.paymentPlan = PaymentPlanName.UNSCHEDULED.name();
		} else {
			this.paymentPlan = paymentPlan;
		}
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		if (paymentStatus.equals(null)) {
			this.paymentStatus = PaymentStatusName.UNSCHEDULED.name();
		} else {
			this.paymentStatus = paymentStatus;
		}
	}

	public String getTeams() {
		return teams;
	}

	public void setTeams() {
		this.teams = TeamName.TEAM_CLIENT.name();
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles() {
		this.roles = RoleName.ROLE_CLIENT.name();
	}
}