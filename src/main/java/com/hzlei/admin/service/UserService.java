package com.hzlei.admin.service;

import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dto.UserDto;
import com.hzlei.admin.model.SysUser;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 21:03
 */
public interface UserService {
    // 用户保存
    int save(SysUser user);

    SysUser getUserByUsername(String username);

    Results<SysUser> getAllUsersByPage(Integer offset, Integer limit);

    Results saveUser(SysUser user, Integer roleId);

    SysUser getUserByPhone(String telePhone);

    SysUser getUserById(Long id);

    Results<SysUser> updateUser(SysUser user, Integer roleId);

    Results delUserById(Long id);

    Results<SysUser> findUsersByUsername(String username, Integer offset, Integer limit);

    Results<SysUser> changePassword(String username, String oldPassword, String newPassword);
}
