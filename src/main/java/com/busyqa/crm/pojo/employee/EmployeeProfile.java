package com.busyqa.crm.pojo.employee;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeProfile")
public class EmployeeProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String password;
	private String email;
	private String phone1;
	private String phone2;
	private boolean active;

	private EmployeeRule rule;

	private boolean login;
	private Date lastLoginTime;
	private Date lastActionTime;
	private Date expectExpireTime;
	private String sessionID;
}
