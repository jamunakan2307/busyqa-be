package com.busyqa.crm.message.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.busyqa.crm.model.ClientStatusName;
import com.busyqa.crm.model.CourseName;
import com.busyqa.crm.model.PaymentPlanName;
import com.busyqa.crm.model.PaymentStatusName;
import com.busyqa.crm.model.RoleName;
import com.busyqa.crm.model.StatusName;
import com.busyqa.crm.model.TeamName;

public class SignUpForm {

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

	private String teams;

	private String roles;

	private String status;

	private String statusAsOfDay;

	private String clientCourse;

	private String clientStatus;

	private String paymentPlan;

	private String paymentStatus;

	//Additional User Info
	private String phoneNumber;

	private String address;

	private String city;

	private String state;

	private String zipCode;

	private String country;

	private String emergencyPhoneNumber;

	private String aboutUs;

	//Client ClientCourse Record

	private String isRegisteredStudent;

	private String trainingLocation;

	//Payment Record
	private String studentComments;

	private String createdTimeStamp;

	private String startDateTime;

	private String trainerName;

	private String leadSource;

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

	public String getClientCourse() {
		return clientCourse;
	}

	public void setClientCourse(String clientCourse) {
		if (clientCourse.equals(null)) {
			this.clientCourse = CourseName.NOT_DECIDED.name();
		}
		this.clientCourse = clientCourse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIsRegisteredStudent() {
		return isRegisteredStudent;
	}

	public void setIsRegisteredStudent(String isRegisteredStudent) {
		this.isRegisteredStudent = isRegisteredStudent;
	}

	public String getTrainingLocation() {
		return trainingLocation;
	}

	public void setTrainingLocation(String trainingLocation) {
		this.trainingLocation = trainingLocation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getStudentComments() {
		return studentComments;
	}

	public void setStudentComments(String studentComments) {
		this.studentComments = studentComments;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}
}
