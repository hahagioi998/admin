package com.hzlei.admin.base.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 分页参数
 * @Author hzlei
 * @Date 2019/12/7 21:15
 */
@Data
public class PageTableRequest implements Serializable {
    // 当前页
    private Integer page;
    // 页数
    private Integer limit;
    private Integer offset;

    public void countOffset() {
        if (null == this.page || null == this.limit)
            this.offset = 0;
        this.offset = (this.page - 1) * limit;
    }
}
