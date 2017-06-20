package com.jfzy.service;

import java.util.List;

import com.jfzy.service.bo.RoleBo;

public interface OssRoleService {

	List<RoleBo> getRoles();

	List<String> getPermissionsByRoleName(String roleName);

}
