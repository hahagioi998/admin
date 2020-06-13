package com.hzlei.admin.controller;

import com.hzlei.admin.base.result.PageTableRequest;
import com.hzlei.admin.base.result.Results;
import com.hzlei.admin.dto.RoleDto;
import com.hzlei.admin.model.SysRole;
import com.hzlei.admin.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 角色
 * @Author hzlei
 * @Date 2019/12/7 23:16
 */
@Slf4j
@Controller
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/getAllRoles")
    @ResponseBody
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色信息")//描述
    public Results<SysRole> getAllRole() {
        log.info("RoleController.getAllRole()");
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "分页获取角色", notes = "用户分页获取角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true, dataType = "Integer"),
    })
    public Results<SysRole> getAllRole2(PageTableRequest pageTableRequest) {
        pageTableRequest.countOffset();
        log.info("RoleController.getAllRole2 : param (pageTableRequest = " + pageTableRequest + ")");
        return roleService.getAllRole2ByPage(pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "模糊查询角色信息", notes = "模糊搜索查询角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "roleName",value = "模糊搜索的角色名", required = true),
    })
    public Results<SysRole> findRoleByFuzzyRoleName(PageTableRequest pageTableRequest, String roleName) {
        pageTableRequest.countOffset();
        log.info("RoleController.findRoleByFuzzyRoleName() : param (pageTableRequest = " + pageTableRequest + ", roleName = " + roleName + " )");
        return roleService.findRoleByFuzzyRoleName(roleName, pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "新增角色信息页面", notes = "跳转到角色信息新增页面")//描述
    public String addRole(Model model) {
        model.addAttribute("sysRole",new SysRole());
        return "role/role-add";
    }
    @PostMapping("add")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "保存角色信息", notes = "保存新增的角色信息")//描述
    @ApiImplicitParam(name = "role",value = "角色信息实体类", required = true, dataType = "RoleDto")
    public Results saveRole(@RequestBody RoleDto role) {
        return roleService.save(role);
    }

    @GetMapping("/edit")
    @ApiOperation(value = "编辑角色信息页面", notes = "跳转到角色信息编辑页面")//描述
    @ApiImplicitParam(name = "sysRole", value = "角色信息实体类", required = true, dataType = "SysRole")
    public String editRole(Model model, SysRole sysRole) {
        model.addAttribute("sysRole", roleService.getRoleById(sysRole.getId().longValue()));
        return "role/role-edit";
    }
    @PostMapping("edit")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @ApiOperation(value = "保存角色信息", notes = "保存被编辑的角色信息")//描述
    @ApiImplicitParam(name = "role", value = "角色信息实体类", required = true, dataType = "RoleDto")
    public Results updateRole(@RequestBody RoleDto role) {
        roleService.update(role);
        return Results.success();
    }

    @GetMapping("/delRoleById")
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")//描述
    @ApiImplicitParam(name = "id", value = "要删除的角色id", required = true, dataType = "Long")
    public Results delRoleById(Long id) {
        log.info("RoleController.delRoleById : param (roleId = " + id + ")");
        return roleService.deleteRoleByID(id);
    }

}
