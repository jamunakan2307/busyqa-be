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
@Table(name = "intern_record")
public class InternRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String coopStarDate;

	private String coopEndDate;

	private String projectAssignment;

	private String performance;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public InternRecord(User user) {
		this.user = user;
	}

	public InternRecord() {
	}

	public String getCoopStarDate() {
		return coopStarDate;
	}

	public void setCoopStarDate(String coopStarDate) {
		this.coopStarDate = coopStarDate;
	}

	public String getCoopEndDate() {
		return coopEndDate;
	}

	public void setCoopEndDate(String coopEndDate) {
		this.coopEndDate = coopEndDate;
	}

	public String getProjectAssignment() {
		return projectAssignment;
	}

	public void setProjectAssignment(String projectAssignment) {
		this.projectAssignment = projectAssignment;
	}

	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
