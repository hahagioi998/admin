package com.hzlei.admin.dao;

import com.hzlei.admin.model.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description: 用户-角色 Dao
 * @Author hzlei
 * @Date 2019/12/7 17:09
 */
@Mapper
public interface RoleUserDao {
    // 保存用户角色信息
    int save(SysRoleUser sysRoleUser);
    // 获取用户角色 by userId
    SysRoleUser getSysRoleUserByUserId(Long userId);
    // 更新用户角色信息
    int updateSysRoleUser(SysRoleUser sru);
    // 删除用户角色信息 by userId
    int delRoleUserByUserId(Long userId);
    //
    @Select("select t.userId, t.roleId from sys_role_user t where t.roleId = #{roleId}")
    List<SysRoleUser> listAllSysRoleUserByRoleId(Long roleId);
}
