package com.hzlei.admin.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 权限
 * @Author hzlei
 * @Date 2019/12/7 17:02
 */
@Data
public class SysPermission extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = -6525908145032868837L;
    private Integer parentId;
    private String name;
    private String css;
    private String href;
    private Integer type;
    private String permission;
    private Integer sort;

    private List<SysPermission> child;

    @Override
    public String toString() {
        return "SysPermission{" +
                "parentId=" + parentId +
                ", name='" + name + '\'' +
                ", css='" + css + '\'' +
                ", href='" + href + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", sort=" + sort +
                '}';
    }
}
