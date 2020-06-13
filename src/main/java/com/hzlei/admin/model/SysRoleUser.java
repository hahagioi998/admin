package com.hzlei.admin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 用户-角色关联
 * @Author hzlei
 * @Date 2019/12/7 16:59
 */
@Data
public class SysRoleUser implements Serializable {
    private Integer userId;
    private Integer roleId;
}
