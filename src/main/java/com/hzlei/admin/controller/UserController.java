package com.hzlei.admin.controller;

import com.hzlei.admin.base.result.PageTableRequest;
import com.hzlei.admin.base.result.ResponseCode;
import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dto.UserDto;
import com.hzlei.admin.model.SysUser;
import com.hzlei.admin.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 用户
 * @Author hzlei
 * @Date 2019/12/7 21:01
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/{username}")
    public SysUser user(@PathVariable String username) {
        log.info("UserController.user() : param (username = " + username + " )");
        return userService.getUserByUsername(username);
    }

    @GetMapping(value = "/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "用户新增页面", notes = "跳转到新增用户信息页面")//描述
    public String addUser(Model model) {
        model.addAttribute("sysUser",new SysUser());
        return "user/user-add";
    }
    @GetMapping(value = "/updateUser")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")//描述
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String editUser(Model model, SysUser user) {
        model.addAttribute("sysUser", userService.getUserById(user.getId()));
        return "user/user-edit";
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request", value = "分页查询实体类", required = false)
    public Results<SysUser> getUsersByPage(PageTableRequest pageTableRequest) {
        pageTableRequest.countOffset();
        log.info("UserController.getUsersByPage() : param (pageTableRequest = " + pageTableRequest + " )");
        return userService.getAllUsersByPage(pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    @GetMapping("/findUsersByUsername")
    @ResponseBody
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "username",value = "模糊搜索的用户名", required = true),
    })
    public Results<SysUser> findUsersByUsername(PageTableRequest pageTableRequest, String username) {
        pageTableRequest.countOffset();
        log.info("UserController.findUsersByUsername() : param (pageTableRequest = " + pageTableRequest + ", username = " + username + " )");
        return userService.findUsersByUsername(username, pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    /**
     * Field error in object 'userDto' on field 'birthday':rejected value [2020-04-05];
     * codes [typeMismatch.userDto.birthday,typeMismatch.birthday,typeMismatch.java.util.Date,typeMismatch];
     * arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [userDto.birthday,birthday]; arguments [];
     * default message [birthday]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'java.util.Date' for property 'birthday';
     * nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [@com.fasterxml.jackson.annotation.JsonFormat java.util.Date] for value '2020-04-05';
     * nested exception is java.lang.IllegalArgumentException]]
     *
     * 说明: 添加用户的时候, 从页面获取的时间 时字符串类型, 不能自动转换为 date, 需要手动转换一下
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "保存用户信息", notes = "保存新增的用户信息")//描述
    public Results<SysUser> saveUser(UserDto user, Integer roleId) {
        // 验证用户名是否唯一
        SysUser s1 = userService.getUserByUsername(user.getUsername());
        if (s1 != null && !(s1.getId().equals(user.getId())))
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(), ResponseCode.USERNAME_REPEAT.getMessage());
        // 验证手机号是否存在
        SysUser s2 = userService.getUserByPhone(user.getTelephone());
        if (s2 != null && !(s2.getId().equals(user.getId())))
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        // 状态默认为启动
        user.setStatus(1);
        // 密码 MD 加密
        // user.setPassword(MD5.crypt(user.getPassword()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user, roleId);
    }

    @PostMapping("/updateUser")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存用户信息", notes = "保存编辑完的用户信息")//描述
    public Results<SysUser> updateUser(UserDto user, Integer roleId) {
        // 验证用户名是否唯一
        SysUser s1 = userService.getUserByUsername(user.getUsername());
        if (s1 != null && !(s1.getId().equals(user.getId())))
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(), ResponseCode.USERNAME_REPEAT.getMessage());
        // 验证手机号是否存在
        SysUser s2 = userService.getUserByPhone(user.getTelephone());
        if (s2 != null && !(s2.getId().equals(user.getId())))
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(), ResponseCode.PHONE_REPEAT.getMessage());
        return userService.updateUser(user, roleId);
    }

    @ResponseBody
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")//描述
    @ApiImplicitParam(name = "id", value = "要删除的用户id", required = true, dataType = "Integer")
    public Results delUser(Long id) {
        return userService.delUserById(id);
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }


}
