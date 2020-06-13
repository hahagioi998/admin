package com.hzlei.admin.dao;

import com.hzlei.admin.model.SysPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 权限 Dao
 * @Author hzlei
 * @Date 2019/12/7 17:07
 */
@Mapper
public interface PermissionDao {
    // 获取所有权限信息
    @Select("select sp.id, sp.parentId, sp.name, sp.css, sp.href, sp.type, sp.permission, sp.sort from sys_permission sp")
    List<SysPermission> findAll();
    // 获取某个角色的所有权限
    @Select("select sp.* from sys_permission sp inner join sys_role_permission srp on sp.id = srp.permissionId where srp.roleId = #{roleId} order by sp.sort")
    List<SysPermission> listAllPermissionByRoleId(Integer roleId);
    // 保存权限信息
    Integer save(SysPermission permission);
    // 获取权限信息 by id
    @Select("select sp.* from sys_permission sp where sp.id = #{id}")
    SysPermission getSysPermissionById(Integer id);
    // 更新权限信息
    Integer updatePermission(SysPermission permission);
    // 删除权限信息
    @Delete("delete from sys_permission sp where sp.id = #{id}")
    Integer deletePermissionById(Integer id);
    // 删除父级权限信息
    @Delete("delete from sys_permission sp where sp.parentId = #{parentId}")
    Integer deletePermissionByPaarentId(Integer parentId);
    // 某个用户所拥有的权限
    @Select("select sp.* from sys_role_user sru " +
            "inner join sys_role_permission srp on srp.roleId = sru.roleId " +
            "left join sys_permission sp on srp.permissionId = sp.id " +
            "where sru.userId = #{userId}")
    List<SysPermission> listByUserId(@Param("userId") Long userId);
}
