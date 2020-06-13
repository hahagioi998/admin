package com.hzlei.admin.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 角色
 * @Author hzlei
 * @Date 2019/12/7 17:00
 */
@Data
public class SysRole extends BaseEntity<Integer> implements Serializable {
    private static final long serialVersionUID = -6525908145032868837L;

    private String name;
    private String description;

    @Override
    public String toString() {
        return "SysRole{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
