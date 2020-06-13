package com.hzlei.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dao.PermissionDao;
import com.hzlei.admin.model.SysPermission;
import com.hzlei.admin.service.PermissionService;
import com.hzlei.admin.util.TreeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 13:18
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    PermissionDao permissionDao;

    @Override
    public Results<JSONArray> listAllPermission() {
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();

        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }

    @Override
    public Results<SysPermission> listAllPermissionByRoleId(Integer roleId) {
        List<SysPermission> datas = permissionDao.listAllPermissionByRoleId(roleId);
        return Results.success(0, datas);
    }

    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0, permissionDao.findAll());
    }

    @Override
    public Results<SysPermission> save(SysPermission permission) {
        return permissionDao.save(permission) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public SysPermission getSysPermissionById(Integer id) {
        return permissionDao.getSysPermissionById(id);
    }

    @Override
    public Results updatePermission(SysPermission permission) {
        return permissionDao.updatePermission(permission) > 0 ? Results.success() : Results.failure();
    }

    @Override
    public Results deletePermission(Integer id) {
        permissionDao.deletePermissionById(id);
        permissionDao.deletePermissionByPaarentId(id);
        return Results.success();
    }

    @Override
    public Results getMenu(Long userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        datas = datas.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());

        JSONArray array = new JSONArray();

        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }
}
