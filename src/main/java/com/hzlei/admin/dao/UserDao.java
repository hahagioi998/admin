package com.hzlei.admin.dao;

import com.hzlei.admin.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Description: 用户 Dao
 * @Author hzlei
 * @Date 2019/12/7 21:01
 */
@Mapper
public interface UserDao {
    // 更新用户信息
    int updateUser(SysUser user);
    // 获取用户信息 by username
    SysUser getUserByUsername(String username);
    // 获取用户 by id
    SysUser getUserById(Long id);
    // 添加用户
    // 返回自增 id
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    // 返回非自增 id
    @SelectKey(statement = "select last_insert_id()", keyProperty = "id", resultType = Long.class, before = false)
    @Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
    int save(SysUser user);
    // 删除用户 by id
    @Delete("delete from sys_user where id = #{id}")
    int deleteUserById(Long id);
    // 查询所有用户数
    @Select("select count(*) from sys_user su")
    Long countAllUsers();
    // 查询所有用户数, 根据名字
    @Select("select count(*) from sys_user su where su.username like'%${username}%'")
    Long countAllUsersByFindUser(@Param("username") String username);
    // 查询所有用户
    List<SysUser> getAllUsersByPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    // 根据手机号查找用户
    SysUser getUserByPhone(String telePhone);
    // 根据用户名查找用户
    List<SysUser> findUsersByUsername(@Param("username") String username, @Param("offset") Integer offset, @Param("limit") Integer limit);
    // 修改密码
    @Update("update sys_user su set su.password = #{password} where su.id = #{id}")
    Integer changePassword(@Param("id") Long id, @Param("password") String password);
}
