package com.busyqa.crm.model;

import java.util.List;

public class ReportGenericDto {

	private String label;

	private  List<Integer> data;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Integer> getData() {
		return data;
	}

	public void setData(List<Integer> data) {
		this.data = data;
	}


}
