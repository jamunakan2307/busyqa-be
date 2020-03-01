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
@Table(name = "payment_history")
public class PaymentHistroy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double amountPaid;
	private double weeklyAmountPaid;

	private double totalAmount;
	private double differenceAmount;

	private String paymentDate;

	private String scheduledPaymentDate;

	private String paymentNotes;

	private String recordStatus;

	private String payLock;

	private String emailReminder;

	private String paymentMode;



	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public PaymentHistroy(User user) {
		this.user = user;
	}

	public PaymentHistroy() {
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentNotes() {
		return paymentNotes;
	}

	public void setPaymentNotes(String paymentNotes) {
		this.paymentNotes = paymentNotes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getWeeklyAmountPaid() {
		return weeklyAmountPaid;
	}

	public void setWeeklyAmountPaid(double weeklyAmountPaid) {
		this.weeklyAmountPaid = weeklyAmountPaid;
	}

	public String getScheduledPaymentDate() {
		return scheduledPaymentDate;
	}

	public void setScheduledPaymentDate(String scheduledPaymentDate) {
		this.scheduledPaymentDate = scheduledPaymentDate;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayLock() {
		return payLock;
	}

	public void setPayLock(String payLock) {
		this.payLock = payLock;
	}

	public String getEmailReminder() {
		return emailReminder;
	}

	public void setEmailReminder(String emailReminder) {
		this.emailReminder = emailReminder;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
}
