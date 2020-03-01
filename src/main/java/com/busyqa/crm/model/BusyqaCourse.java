package com.busyqa.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "busyqaCourse", uniqueConstraints = { @UniqueConstraint(columnNames = { "courseName" }) })
public class BusyqaCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	private String courseName;

	@Column(length = 60)
	private double courseAmount;

	@Column(length = 60)
	private int courseLenght;

	@Column(length = 60)
	private String status;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseLenght() {
		return courseLenght;
	}

	public void setCourseLenght(int courseLenght) {
		this.courseLenght = courseLenght;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getCourseAmount() {
		return courseAmount;
	}

	public void setCourseAmount(double courseAmount) {
		this.courseAmount = courseAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
