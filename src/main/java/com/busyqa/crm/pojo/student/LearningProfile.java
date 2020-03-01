package com.busyqa.crm.pojo.student;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.busyqa.crm.pojo.course.Course;
import com.busyqa.crm.pojo.payment.PaymentRecord;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "LearningProfile")
public class LearningProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	//    @OneToOne(cascade = CascadeType.ALL)
	//    @JoinColumn(name = "course_id")
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private Course course;
	// LEAD
	@Enumerated(EnumType.STRING)
	@Column(name = "learningStatus")
	private LearningStatus learningStatus;
	private String studentCourseInterestNote;
	// REGISTERED STUDENT
	@OneToOne(cascade = CascadeType.ALL)
	//    @JoinColumn(name = "payment_record_id")
	private PaymentRecord paymentRecord;
	@ManyToOne
	//    @JsonManagedReference
	private StudentProfile studentProfile;

	public LearningProfile() {
	}

	//	private CourseRecord courseRecord;

	// ========ref

	public LearningProfile(Course course, LearningStatus learningStatus, String studentCourseInterestNote) {
		this.course = course;
		this.learningStatus = learningStatus;
		this.studentCourseInterestNote = studentCourseInterestNote;
	}

	//========getter&setter

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public LearningStatus getLearningStatus() {
		return learningStatus;
	}

	public void setLearningStatus(LearningStatus learningStatus) {
		this.learningStatus = learningStatus;
	}

	public String getStudentCourseInterestNote() {
		return studentCourseInterestNote;
	}

	public void setStudentCourseInterestNote(String studentCourseInterestNote) {
		this.studentCourseInterestNote = studentCourseInterestNote;
	}

	public PaymentRecord getPaymentRecord() {
		return paymentRecord;
	}

	public void setPaymentRecord(PaymentRecord paymentRecord) {
		this.paymentRecord = paymentRecord;
	}

	public StudentProfile getStudentProfile() {
		return studentProfile;
	}

	public void setStudentProfile(StudentProfile studentProfile) {
		this.studentProfile = studentProfile;
	}
}
