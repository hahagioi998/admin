package com.hzlei.admin.service.impl;

import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dao.RoleUserDao;
import com.hzlei.admin.dao.UserDao;
import com.hzlei.admin.model.SysRoleUser;
import com.hzlei.admin.model.SysUser;
import com.hzlei.admin.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 21:04
 */
@Service
// 事务
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;
    @Resource
    RoleUserDao roleUserDao;

    @Override
    public int save(SysUser user) {
        return userDao.save(user);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    // 获取用户
    @Override
    public Results<SysUser> getAllUsersByPage(Integer offset, Integer limit) {
        // 1, 返回 count userlist, 总条数
        Long count = userDao.countAllUsers();
        // 2, data 数据
        List<SysUser> datas = userDao.getAllUsersByPage(offset, limit);
        return Results.success(count.intValue(), datas);
    }

    // 模糊查找用户
    @Override
    public Results<SysUser> findUsersByUsername(String username, Integer offset, Integer limit) {
        // 1, 返回 count userlist, 总条数
        Long count = userDao.countAllUsersByFindUser(username);
        // 2, data 数据
        List<SysUser> datas = userDao.findUsersByUsername(username, offset, limit);
        return Results.success(count.intValue(), datas);
    }

    // 修改密码
    @Override
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        SysUser user = userDao.getUserByUsername(username);
        if (user == null) {
            return Results.failure(1, "用户名不存在");
        }
        if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            return Results.failure(1, "旧密码错误");
        }
        userDao.changePassword(user.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }

    // 添加用户
    @Override
    public Results saveUser(SysUser user, Integer roleId) {
        if (roleId != null) {
            // 1, add user in sys_user table
            userDao.save(user);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(user.getId().intValue());
            // 2, add role-user in sys_role_user table
            roleUserDao.save(sysRoleUser);
            return Results.success();
        } else return Results.failure();
    }

    @Override
    public SysUser getUserByPhone(String telePhone) {
        return userDao.getUserByPhone(telePhone);
    }

    @Override
    public SysUser getUserById(Long id) {
        return userDao.getUserById(id);
    }

    // 更新用户信息
    @Override
    public Results<SysUser> updateUser(SysUser user, Integer roleId) {
        if (roleId != null) {
            // 1, update user in sys_user table
            userDao.updateUser(user);
            // 2, update role-user in sys_role_user table
            SysRoleUser sru = roleUserDao.getSysRoleUserByUserId(user.getId());
            // 根据 userId 查询 用户的角色
            // 查询的角色不为空, 与原角色比较, 有改动则修改, 无改动不修改
            if (sru != null) {
                if (!roleId.equals(sru.getRoleId())) {
                    sru.setRoleId(roleId);
                    roleUserDao.updateSysRoleUser(sru);
                }
            } else {
                // 查询的角色为空, 则新增用户对应的角色
                SysRoleUser addSru = new SysRoleUser();
                addSru.setUserId(user.getId().intValue());
                addSru.setRoleId(roleId);
                roleUserDao.save(addSru);
            }
            return Results.success();
        } else return Results.failure();
    }

    @Override
    public Results delUserById(Long id) {
        // delete role_user
        int row1 = roleUserDao.delRoleUserByUserId(id);
        // delete user
        int row2 = userDao.deleteUserById(id);
        if (row1 + row2 >= 2) {
            return Results.success();
        } else {
            return Results.failure();
        }
    }
}
