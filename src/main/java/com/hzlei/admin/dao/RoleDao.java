package com.hzlei.admin.dao;

import com.hzlei.admin.dto.RoleDto;
import com.hzlei.admin.model.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 角色 Dao
 * @Author hzlei
 * @Date 2019/12/7 17:08
 */
@Mapper
public interface RoleDao {
    // 获取所有角色
    List<SysRole> getAllRoles();
    // 所有角色数量
    @Select("select count(*) from sys_role sr")
    Long countAllRole();
    // 获取所有角色
    List<SysRole> getAllRoles2ByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    // 获取角色 by roleId
    @Select("select sr.id, sr.name, sr.description, sr.createTime, sr.updateTime from sys_role sr where sr.id = #{id}")
    SysRole getRoleById(Long id);
    // 删除角色 by roleId
    @Delete("delete from sys_role sr where sr.id = #{id}")
    Long delRoleById(Long id);
    // 模糊查询角色 by roleName
    List<SysRole> findRoleByFuzzyRoleName(@Param("name") String roleName, @Param("offset") Integer offset, @Param("limit") Integer limit);
    // 根据 roleName 搜索的角色数量
    @Select("select count(*) from sys_role sr where sr.name like'%${roleName}%'")
    Long countAllRoleByFindRoleName(String roleName);
    // 保存角色信息
    Long saveRole(RoleDto role);
    // 更新角色信息
    Integer update(RoleDto role);
}
