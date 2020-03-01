package com.busyqa.crm.model;

public enum TrainerName {

	KEERTHANA_D("Keerthana Devatha", CourseName.AUTOMATES_TESTING.courseName, true), NARESH("NARESH",
			CourseName.AUTOMATION_TESTING_ONLINE.courseName, true), unAssigned("UnAssigned",
			CourseName.BUSINESS_ANALYSIS.courseName, true), unAssigned2("UnAssigned2",
			CourseName.BUSINESS_ANALYSIS.courseName, true), MARK_N("Mark Nicholas",
			CourseName.BIGDATA_DATA_SCIENCE.courseName, true), Pavan_K("Pavan Kumar",
			CourseName.BIGDATA_DATA_SCIENCE_ONLINE_1M.courseName, true), AKRAM_M("Akram Mohammed",
			CourseName.BIGDATA_DATA_SCIENCE_ONLINE_25M.courseName, true), SEAN("Sean",
			CourseName.CERTIFIED_SCRUM_MASTER.courseName, true), RICHA_P("Richa Prasad",
			CourseName.COOP_PROGRAM.courseName, true), JANISE_P("Janise Peters",
			CourseName.FULL_STACK_AUTOMATION_TESTING.courseName, true), MAHER_S("Maher Selim",
			CourseName.SCRUM_MASTER.courseName, true), IBRAHEEM_H("Ibraheem Haruna",
			CourseName.PERFORMANCE_TESTING.courseName, true), JAMES_H("James Hung",
			CourseName.FULL_STACK_JAVA_DEV.courseName, true), TYE_A("Tye Alli", CourseName.SOFTWARE_TESTING.courseName,
			true), RAHUL_M("Rahul Nimodiya", CourseName.SOFTWARE_TESTING_ONLINE.courseName, true), NA("NA", "", true);

	public String trainerName;
	public String trainerCourse;
	public boolean trainerActive;

	TrainerName(String trainerName, String trainerCourse, boolean trainerActive) {
		this.trainerName = trainerName;
		this.trainerCourse = trainerCourse;
	}

	public String getTrainerName() {
		return this.trainerName;
	}

	public String getTrainerCourse() {
		return this.trainerCourse;
	}

}
