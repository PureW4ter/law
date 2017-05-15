package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfzy.service.OrderRoleService;
import com.jfzy.service.PropertyService;
import com.jfzy.service.bo.OrderRoleBo;
import com.jfzy.service.bo.PhaseBo;
import com.jfzy.service.bo.PropertyBo;
import com.jfzy.service.bo.PropertyTypeEnum;
import com.jfzy.service.po.PhasePo;
import com.jfzy.service.repository.PhaseRepository;

public class OrderRoleServiceImpl implements OrderRoleService {

	@Autowired
	private PropertyService propService;

	@Autowired
	private PhaseRepository phaseRepo;

	private List<OrderRoleBo> roles;

	@PostConstruct
	public void init() {
		List<PropertyBo> properties = propService.getPropertyByType(PropertyTypeEnum.ROLE.getId());
		Map<Integer, OrderRoleBo> roleMap = new HashMap<Integer, OrderRoleBo>();
		if (properties != null) {
			properties.forEach(bo -> {
				OrderRoleBo roleBo = new OrderRoleBo();
				roleBo.setRole(bo.getName());
				roleMap.put(Integer.valueOf(bo.getId()), roleBo);
			});
		}

		List<PhasePo> phases = phaseRepo.findAll();
		Map<Integer, PhaseBo> phaseMap = new HashMap<Integer, PhaseBo>();
		phases.forEach(po -> {
			PhaseBo bo = new PhaseBo();
			bo.setPhase(po.getPhase());
			phaseMap.put(Integer.valueOf(po.getId()), bo);
			if (po.getParentPhaseId() == 0 && po.getRoleId() > 0) {
				OrderRoleBo orderRole = roleMap.get(Integer.valueOf(po.getRoleId()));
				if (orderRole != null) {
					if (orderRole.getPhases() == null) {
						orderRole.setPhases(new ArrayList<PhaseBo>());
					}
					orderRole.getPhases().add(bo);
				}
			}
		});

		phases.forEach(po -> {
			if (po.getParentPhaseId() != 0) {
				PhaseBo self = phaseMap.get(Integer.valueOf(po.getId()));
				PhaseBo parent = phaseMap.get(Integer.valueOf(po.getParentPhaseId()));
				if (self != null && parent != null) {
					if (parent.getSubPhases() == null) {
						parent.setSubPhases(new ArrayList<PhaseBo>());
						parent.getSubPhases().add(self);
					}
				}
			}

		});

		List<OrderRoleBo> tmpRoles = new ArrayList<OrderRoleBo>();
		tmpRoles.addAll(roleMap.values());

		this.roles = tmpRoles;

	}

	@Override
	public List<OrderRoleBo> getAllRoles() {
		return this.roles;
	}

}
