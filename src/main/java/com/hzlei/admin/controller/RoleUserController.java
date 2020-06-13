package com.hzlei.admin.controller;

import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.service.RoleUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 用户的角色 role-user
 * @Author hzlei
 * @Date 2019/12/7 16:32
 */
@Slf4j
@RestController
@RequestMapping("/roleuser")
public class RoleUserController {
    @Resource
    private RoleUserService roleUserService;

    @PostMapping("/getRoleUserByUserId")
    @ApiOperation(value = "获取当前用户角色", notes = "获取当前用户角色")//描述
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true)
    public Results getRoleUserByUserId(Long userId) {
        log.info("getRoleUserByUserId.getRoleUserByUserId(): param :(" + userId + ")");
        return roleUserService.getSysRoleUserByUserId(userId);
    }
}
