package com.jfzy.service.bo;

import java.util.List;

public class OrderRoleBo {

	private String role;

	private List<PhaseBo> phases;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<PhaseBo> getPhases() {
		return phases;
	}

	public void setPhases(List<PhaseBo> phases) {
		this.phases = phases;
	}

}
