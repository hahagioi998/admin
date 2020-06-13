package com.hzlei.admin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 角色-权限关联
 * @Author hzlei
 * @Date 2019/12/7 17:05
 */
@Data
public class RolePermission implements Serializable {
    private Integer roleId;
    private Integer permissionId;
}
