package com.hzlei.admin.dto;

import com.hzlei.admin.model.SysRole;

import java.util.List;

/**
 * @Description: TODO:
 * @Author hzlei
 * @Date 2019/12/7 18:25
 */
public class RoleDto extends SysRole {
    private static final long serialVersionUID = -5784234789156935003L;
    private List<Long> permissionIds;
    public List<Long> getPermissionIds() {
        return permissionIds;
    }
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
