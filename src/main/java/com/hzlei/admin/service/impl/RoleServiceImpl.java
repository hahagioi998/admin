package com.hzlei.admin.service.impl;

import com.hzlei.admin.base.result.ResponseCode;
import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dao.RoleDao;
import com.hzlei.admin.dao.RolePermissionDao;
import com.hzlei.admin.dao.RoleUserDao;
import com.hzlei.admin.dto.RoleDto;
import com.hzlei.admin.model.RolePermission;
import com.hzlei.admin.model.SysRole;
import com.hzlei.admin.model.SysRoleUser;
import com.hzlei.admin.model.SysUser;
import com.hzlei.admin.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 角色
 * @Author hzlei
 * @Date 2019/12/7 23:20
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleUserDao roleUserDao;
    @Resource
    private RolePermissionDao rolePermissionDao;

    @Override
    public Results<SysRole> getAllRoles() {
        return Results.success(10, roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> getAllRole2ByPage(Integer offset, Integer limit) {
        // 1, 返回 总条数
        Long count = roleDao.countAllRole();
        // 2, data 数据
        List<SysRole> datas = roleDao.getAllRoles2ByPage(offset, limit);
        return Results.success(count.intValue(), datas);
    }

    @Override
    public SysRole getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public Results deleteRoleByID(Long id) {
        List<SysRoleUser> datas = roleUserDao.listAllSysRoleUserByRoleId(id);
        if (datas.size() <= 0) {
            roleDao.delRoleById(id);
            return Results.success();
        }
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(), ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }

    @Override
    public Results<SysRole> findRoleByFuzzyRoleName(String roleName, Integer offset, Integer limit) {
        // 1, 返回数据总条数
        Long count = roleDao.countAllRoleByFindRoleName(roleName);
        // 2, data 数据
        List<SysRole> datas = roleDao.findRoleByFuzzyRoleName(roleName, offset, limit);
        return Results.success(count.intValue(), datas);
    }

    @Override
    public Results save(RoleDto role) {
        // 1, 保存角色
        roleDao.saveRole(role);
        List<Long> permissionIds = role.getPermissionIds();
        // 移除0, permission, id 是从1开始
        permissionIds.remove(0L);
        // 2, 保存角色对应的所有权限
        if (!CollectionUtils.isEmpty(permissionIds)) {
            rolePermissionDao.save(role.getId(), permissionIds);
        }
        return Results.success();
    }

    @Override
    public Integer update(RoleDto role) {
        List<Long> permissionIds = role.getPermissionIds();
        permissionIds.remove(0L);
        // 1, 更新角色权限之前要删除原有的权限
        rolePermissionDao.delRolePermission(role.getId());
        // 2, 判断角色是否有赋予权限值, 有就添加
        if (!CollectionUtils.isEmpty(permissionIds))
            rolePermissionDao.save(role.getId(), permissionIds);
        return roleDao.update(role);
    }
}
