package com.busyqa.crm.pojo.student;

/* 1:46 PM 
created by: ajaypalsingh , 2018-12-06
Project: crm 
*/

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Student_Course_Type")
//@Embeddable
public class StudentCourseType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private long Id;

	private String courseType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "studentCourseType", orphanRemoval = true)
	@JsonBackReference
	private List<StudentRecords> studentRecordsList;

	public StudentCourseType() {
	}

	public StudentCourseType(String courseType) {
		this.courseType = courseType;
	}

	public StudentCourseType(long Id, String courseType) {
		this.Id = Id;
		this.courseType = courseType;
	}

	public long getId() {
		return Id;
	}

	public void setId(long Id) {
		this.Id = Id;
	}

	public List<StudentRecords> getStudentRecordsList() {
		return studentRecordsList;
	}

	public void setStudentRecordsList(List<StudentRecords> studentRecordsList) {
		this.studentRecordsList = studentRecordsList;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	//Software_Testing, Full_Stack_Java_Developer, Business_Analyst, Project_Management
}
