package com.jfzy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfzy.service.OssRoleService;
import com.jfzy.service.bo.RoleBo;
import com.jfzy.service.po.OssPermissionPo;
import com.jfzy.service.po.RolePo;
import com.jfzy.service.repository.OssPermissionRepository;
import com.jfzy.service.repository.OssRoleRepository;

@Service
public class OssRoleServiceImpl implements OssRoleService {

	@Autowired
	private OssRoleRepository roleRepo;

	@Autowired
	private OssPermissionRepository permRepo;

	private List<RoleBo> roles;
	private Map<String, List<String>> permissionMap;

	@PostConstruct
	public void init() {
		List<RolePo> rolePos = roleRepo.findAll();
		List<OssPermissionPo> permPos = permRepo.findAll();
		Map<String, RoleBo> roleMap = new HashMap<String, RoleBo>();
		if (rolePos != null) {
			rolePos.forEach(po -> {
				RoleBo bo = poToBo(po);
				roleMap.put(bo.getName(), bo);
			});
		}
		if (permPos != null) {
			permPos.forEach(po -> {
				RoleBo bo = roleMap.get(po.getRole());
				if (bo != null) {
					if (bo.getPermissons() == null) {
						bo.setPermissons(new ArrayList<String>());
					}
					bo.getPermissons().add(po.getPermission());
				}
			});
		}

		List<RoleBo> tmpRoles = new ArrayList<RoleBo>();
		tmpRoles.addAll(roleMap.values());

		Map<String, List<String>> tmpPermMap = new HashMap<String, List<String>>();
		tmpRoles.forEach(role -> {
			tmpPermMap.put(role.getName(), role.getPermissons());
		});

		this.roles = tmpRoles;
		this.permissionMap = tmpPermMap;
	}

	@Override
	public List<RoleBo> getRoles() {
		return this.roles;
	}

	private static RoleBo poToBo(RolePo po) {
		RoleBo bo = new RoleBo();
		BeanUtils.copyProperties(po, bo);
		return bo;
	}

	@Override
	public List<String> getPermissionsByRoleName(String roleName) {
		return this.permissionMap.get(roleName);
	}

}
