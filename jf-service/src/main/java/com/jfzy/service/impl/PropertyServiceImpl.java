package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.PropertyService;
import com.jfzy.service.bo.PropertyBo;
import com.jfzy.service.po.PropertyPo;
import com.jfzy.service.repository.PropertyRepository;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyRepository propRepo;

	private Map<Integer, List<PropertyBo>> properties;

	@PostConstruct
	public void init() {
		Map<Integer, List<PropertyBo>> tmp = new HashMap<Integer, List<PropertyBo>>();
		List<PropertyPo> propList = propRepo.findAll();

		for (PropertyPo po : propList) {
			List<PropertyBo> typeList = tmp.get(Integer.valueOf(po.getType()));
			if (typeList == null) {
				typeList = new ArrayList<PropertyBo>();
				tmp.put(Integer.valueOf(po.getType()), typeList);
			}

			typeList.add(poToBo(po));
		}

		this.properties = tmp;
	}

	@Override
	public List<PropertyBo> getPropertyByType(int type) {
		return properties.get(Integer.valueOf(type));
	}

	private static PropertyBo poToBo(PropertyPo po) {
		PropertyBo bo = new PropertyBo();
		bo.setId(po.getId());
		bo.setType(po.getType());
		bo.setName(po.getName());

		return bo;
	}

}
