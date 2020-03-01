package com.busyqa.crm.model;

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
@Table(name = "alumini_record")
public class AluminiRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String placementStatus;

	private String companyName;

	private String employementType;

	private String aluminiDesignation;

	private String aluminiComments;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public AluminiRecord(User user) {
		this.user = user;
	}

	public AluminiRecord() {
	}

	public String getPlacementStatus() {
		return placementStatus;
	}

	public void setPlacementStatus(String placementStatus) {
		this.placementStatus = placementStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmployementType() {
		return employementType;
	}

	public void setEmployementType(String employementType) {
		this.employementType = employementType;
	}

	public String getAluminiComments() {
		return aluminiComments;
	}

	public void setAluminiComments(String aluminiComments) {
		this.aluminiComments = aluminiComments;
	}

	public String getAluminiDesignation() {
		return aluminiDesignation;
	}

	public void setAluminiDesignation(String aluminiDesignation) {
		this.aluminiDesignation = aluminiDesignation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
