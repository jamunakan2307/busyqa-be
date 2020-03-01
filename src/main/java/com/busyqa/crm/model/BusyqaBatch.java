package com.busyqa.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "busyqa_batch")
public class BusyqaBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busyqaCourse_id", nullable = false, updatable = false)
	private BusyqaCourse busyqaCourse;

	@Column(length = 60)
	private String startDate;

	@Column(length = 60)
	private String endDate;

	@Column(length = 100)
	private String courseLocation;

	@Column(length = 3500)
	private String batchDetails;

	@Column
	private String batchStatus;

	@Column
	private String batcDetailsFile;

	@Column(length = 60)
	private double taxPercentage;

	@Column(length = 60)
	private double netCourseAmount;

	@Column(length = 60)
	private double registrationFees;

	@Column
	private String season;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public BusyqaBatch(BusyqaCourse busyqaCourse, User user) {
		this.busyqaCourse = busyqaCourse;
		this.user = user;
	}

	public BusyqaBatch() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BusyqaCourse getBusyqaCourse() {
		return busyqaCourse;
	}

	public void setBusyqaCourse(BusyqaCourse busyqaCourse) {
		this.busyqaCourse = busyqaCourse;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCourseLocation() {
		return courseLocation;
	}

	public void setCourseLocation(String courseLocation) {
		this.courseLocation = courseLocation;
	}

	public String getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(String batchDetails) {
		this.batchDetails = batchDetails;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public double getNetCourseAmount() {
		return netCourseAmount;
	}

	public void setNetCourseAmount(double netCourseAmount) {
		this.netCourseAmount = netCourseAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getRegistrationFees() {
		return registrationFees;
	}

	public void setRegistrationFees(double registrationFees) {
		this.registrationFees = registrationFees;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}
}
