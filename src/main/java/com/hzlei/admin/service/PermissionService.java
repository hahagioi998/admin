package com.hzlei.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.model.SysPermission;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 13:18
 */
public interface PermissionService {

    Results<JSONArray> listAllPermission();

    Results<SysPermission> listAllPermissionByRoleId(Integer roleId);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission permission);

    SysPermission getSysPermissionById(Integer id);

    Results updatePermission(SysPermission permission);

    Results deletePermission(Integer id);

    Results getMenu(Long userId);
}
