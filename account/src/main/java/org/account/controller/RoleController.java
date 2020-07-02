package org.account.controller;

import org.account.entity.Role;
import org.account.service.RoleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/roles",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 查询所有角色信息列表
     */
    @GetMapping
    public List<Role> findAllRole() {
        return roleService.findAllRole();
    }

    /**
     * 查询角色信息
     */
    @GetMapping("/{role_id}")
    public Role roleInfo(@PathVariable("role_id") Long roleId) {
        return roleService.findRoleById(roleId);
    }

}
