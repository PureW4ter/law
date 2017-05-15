package com.jfzy.service.bo;

import java.util.List;

public class PhaseBo {

	private String phase;

	private List<PhaseBo> subPhases;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public List<PhaseBo> getSubPhases() {
		return subPhases;
	}

	public void setSubPhases(List<PhaseBo> subPhases) {
		this.subPhases = subPhases;
	}

}
