package com.busyqa.crm.pojo.course;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.busyqa.crm.pojo.student.LearningProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ClientCourse")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String coursePublicID;
	private String courseTitle;
	private String courseBatchName;
	private boolean available;
	private int maxStudents;
	private int currentEnroll;
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)//(mappedBy = "studentProfile", fetch = FetchType.EAGER)
	//	@JoinColumn(name = "course_id", referencedColumnName = "id")
	@JsonBackReference
	private List<LearningProfile> learningProfiles;

	public Course() {
	}
	//	private ArrayList<CourseWeekTopic> courseWeeks;

	public Course(String coursePublicID, String courseTitle, String courseBatchName) {
		this.coursePublicID = coursePublicID;
		this.courseTitle = courseTitle;
		this.courseBatchName = courseBatchName;
	}

	// ========ref
	//	@OneToOne//(mappedBy = "course")
	//	private LearningProfile studentCourseProfile;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCoursePublicID() {
		return coursePublicID;
	}

	public void setCoursePublicID(String coursePublicID) {
		this.coursePublicID = coursePublicID;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseBatchName() {
		return courseBatchName;
	}

	public void setCourseBatchName(String courseBatchName) {
		this.courseBatchName = courseBatchName;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	public int getCurrentEnroll() {
		return currentEnroll;
	}

	public void setCurrentEnroll(int currentEnroll) {
		this.currentEnroll = currentEnroll;
	}

	public List<LearningProfile> getLearningProfiles() {
		return learningProfiles;
	}

	public void setLearningProfiles(List<LearningProfile> learningProfiles) {
		this.learningProfiles = learningProfiles;
	}
}
