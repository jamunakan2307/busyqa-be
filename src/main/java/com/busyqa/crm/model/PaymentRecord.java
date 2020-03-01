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
@Table(name = "client_payment_records")
public class PaymentRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double amountPaid;

	private double totalCourseAmount;

	private double totalCourseAmountAfterDiscount;
	private double totaDiscountAmount;
	//registration Variables

	private String currentlyEmployed;

	private String currentlyTechIndustry;

	private String desriredJob;

	private String currentJob;

	private String leadSource;

	private String studentComments;

	private String createdTimeStamp;

	private String registerationTimeStamp;

	private String lastModifiedTimeStamp;

	private String lastPaymentTimeStamp;
	//Old
	private String isRegisteredStudent;
	private String studentLock;

	private String isCourse;

	private String internDate;

	private String paySetupDone;

	private String anchorDate;

	private double registrationFees;

	private String courseName;


	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teams_id", nullable = false, updatable = false)
	private Team team;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_status_id", nullable = true, updatable = true)
	private ClientStatus clientStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_plan_id", nullable = true, updatable = true)
	private PaymentPlan paymentPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_status_id", nullable = true, updatable = true)
	private PaymentStatus paymentStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_history_id", nullable = true, updatable = true)
	private PaymentHistroy paymentHistroy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busyqa_batch_id", nullable = true, updatable = true)
	private BusyqaBatch busyqaBatch;

	public PaymentRecord(User user, Team team, ClientStatus clientStatus, PaymentPlan paymentPlan,
			PaymentStatus paymentStatus, PaymentHistroy paymentHistroy) {
		this.user = user;
		this.team = team;
		this.clientStatus = clientStatus;
		this.paymentPlan = paymentPlan;
		this.paymentStatus = paymentStatus;
		this.paymentHistroy = paymentHistroy;
	}

	public PaymentRecord() {
	}

	public String getCurrentlyEmployed() {
		return currentlyEmployed;
	}

	public void setCurrentlyEmployed(String currentlyEmployed) {
		this.currentlyEmployed = currentlyEmployed;
	}

	public String getCurrentlyTechIndustry() {
		return currentlyTechIndustry;
	}

	public void setCurrentlyTechIndustry(String currentlyTechIndustry) {
		this.currentlyTechIndustry = currentlyTechIndustry;
	}

	public String getDesriredJob() {
		return desriredJob;
	}

	public void setDesriredJob(String desriredJob) {
		this.desriredJob = desriredJob;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public ClientStatus getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(ClientStatus clientStatus) {
		this.clientStatus = clientStatus;
	}

	public PaymentPlan getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(PaymentPlan paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getIsRegisteredStudent() {
		return isRegisteredStudent;
	}

	public void setIsRegisteredStudent(String isRegisteredStudent) {
		this.isRegisteredStudent = isRegisteredStudent;
	}

	public String getStudentLock() {
		return studentLock;
	}

	public void setStudentLock(String studentLock) {
		this.studentLock = studentLock;
	}

	public String getStudentComments() {
		return studentComments;
	}

	public void setStudentComments(String studentComments) {
		this.studentComments = studentComments;
	}

	public String getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(String createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getRegisterationTimeStamp() {
		return registerationTimeStamp;
	}

	public void setRegisterationTimeStamp(String registerationTimeStamp) {
		this.registerationTimeStamp = registerationTimeStamp;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public double getTotalCourseAmount() {
		return totalCourseAmount;
	}

	public void setTotalCourseAmount(double totalCourseAmount) {
		this.totalCourseAmount = totalCourseAmount;
	}

	public String getLastPaymentTimeStamp() {
		return lastPaymentTimeStamp;
	}

	public void setLastPaymentTimeStamp(String lastPaymentTimeStamp) {
		this.lastPaymentTimeStamp = lastPaymentTimeStamp;
	}

	public String getLastModifiedTimeStamp() {
		return lastModifiedTimeStamp;
	}

	public void setLastModifiedTimeStamp(String lastModifiedTimeStamp) {
		this.lastModifiedTimeStamp = lastModifiedTimeStamp;
	}

	public BusyqaBatch getBusyqaBatch() {
		return busyqaBatch;
	}

	public void setBusyqaBatch(BusyqaBatch busyqaBatch) {
		this.busyqaBatch = busyqaBatch;
	}

	public double getTotaDiscountAmount() {
		return totaDiscountAmount;
	}

	public void setTotaDiscountAmount(double totaDiscountAmount) {
		this.totaDiscountAmount = totaDiscountAmount;
	}

	public double getTotalCourseAmountAfterDiscount() {
		return totalCourseAmountAfterDiscount;
	}

	public void setTotalCourseAmountAfterDiscount(double totalCourseAmountAfterDiscount) {
		this.totalCourseAmountAfterDiscount = totalCourseAmountAfterDiscount;
	}

	public String getisCourse() {
		return isCourse;
	}

	public void setisCourse(String isCourse) {
		this.isCourse = isCourse;
	}

	public String getInternDate() {
		return internDate;
	}

	public void setInternDate(String internDate) {
		this.internDate = internDate;
	}

	public String getPaySetupDone() {
		return paySetupDone;
	}

	public void setPaySetupDone(String paySetupDone) {
		this.paySetupDone = paySetupDone;
	}

	public String getAnchorDate() {
		return anchorDate;
	}

	public void setAnchorDate(String anchorDate) {
		this.anchorDate = anchorDate;
	}

	public double getRegistrationFees() {
		return registrationFees;
	}

	public void setRegistrationFees(double registrationFees) {
		this.registrationFees = registrationFees;
	}


}

