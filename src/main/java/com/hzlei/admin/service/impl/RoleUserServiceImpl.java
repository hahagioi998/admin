package com.hzlei.admin.service.impl;

import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dao.RoleUserDao;
import com.hzlei.admin.model.SysRoleUser;
import com.hzlei.admin.service.RoleUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 16:36
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Resource
    private RoleUserDao roleUserDao;

    @Override
    public Results getSysRoleUserByUserId(Long userId) {
        SysRoleUser sysRoleUser = roleUserDao.getSysRoleUserByUserId(userId);
        if (sysRoleUser != null) return Results.success(sysRoleUser);
        else return Results.success();
    }
}
