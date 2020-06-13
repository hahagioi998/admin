package com.hzlei.admin.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 角色-权限 Dao
 * @Author hzlei
 * @Date 2019/12/7 17:08
 */
@Mapper
public interface RolePermissionDao {
    // 保存角色权限
    Integer save(@Param("roleId") Integer id, @Param("permissionIds") List<Long> permissionIds);
    // 删除角色权限 by roleId
    @Delete("delete from sys_role_permission where roleId = #{roleId}")
    Integer delRolePermission(Integer roleId);
}
