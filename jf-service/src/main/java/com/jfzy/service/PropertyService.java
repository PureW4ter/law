package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.PropertyBo;

public interface PropertyService {

	List<PropertyBo> getPropertyByType(int type);

}
