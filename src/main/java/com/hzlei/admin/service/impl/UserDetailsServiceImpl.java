package com.hzlei.admin.service.impl;

import com.hzlei.admin.dao.PermissionDao;
import com.hzlei.admin.dto.LoginUser;
import com.hzlei.admin.model.SysUser;
import com.hzlei.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2020/6/2 17:45
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;
    @Resource
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUsername(username);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (user.getStatus() == SysUser.Status.DISABLED) {
            throw new LockedException("用户被锁定");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);
        loginUser.setPermissions(permissionDao.listByUserId(user.getId()));

        return loginUser;
    }
}
