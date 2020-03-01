package com.busyqa.crm.model;

public class BusyqaBatchDto {

	private Long id;

	private String courseName;

	private String trainerName;

	private String startDate;

	private String endDate;

	private String courseLocation;

	private String batchDetails;

	private double taxPercentage;

	private double courseAmount;

	private double netCourseAmount;

	private int courseLenght;

	private String batchStatus;

	private double registrationFees;

	private String season;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public int getCourseLenght() {
		return courseLenght;
	}

	public void setCourseLenght(int courseLenght) {
		this.courseLenght = courseLenght;
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

	public double getCourseAmount() {
		return courseAmount;
	}

	public void setCourseAmount(double courseAmount) {
		this.courseAmount = courseAmount;
	}

	public double getNetCourseAmount() {
		return netCourseAmount;
	}

	public void setNetCourseAmount(double netCourseAmount) {
		this.netCourseAmount = netCourseAmount;
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
