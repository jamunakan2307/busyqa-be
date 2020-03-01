package com.busyqa.crm.pojo.payment;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.busyqa.crm.pojo.student.LearningProfile;

@Entity
@Table(name = "PaymentRecord")
public class PaymentRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentPlanType")
	private PaymentPlanType paymentPlanType;

	private int paymentTotalAomount;

	@OneToMany//(mappedBy = "paymentRecord", cascade = CascadeType.ALL)
	//	@JoinColumn(name = "payment_record_id", referencedColumnName = "id")
	private List<PaymentPlanWeek> paymentPlanWeeks;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentStatus")
	private PaymentStatus paymentStatus;

	// ========ref
	@OneToOne//(mappedBy = "paymentRecord")
	private LearningProfile learningProfile;

}
