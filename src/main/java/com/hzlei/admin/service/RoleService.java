package com.hzlei.admin.service;

import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dto.RoleDto;
import com.hzlei.admin.model.RolePermission;
import com.hzlei.admin.model.SysRole;

/**
 * @Description: 角色
 * @Author hzlei
 * @Date 2019/12/7 23:19
 */
public interface RoleService {
    Results<SysRole> getAllRoles();

    Results<SysRole> getAllRole2ByPage(Integer offset, Integer limit);

    SysRole getRoleById(Long id);

    Results deleteRoleByID(Long id);

    Results<SysRole> findRoleByFuzzyRoleName(String roleName, Integer offset, Integer limit);

    Results save(RoleDto role);

    Integer update(RoleDto role);
}
