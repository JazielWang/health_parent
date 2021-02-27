package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.entity.Result;
import com.Jaziel.pojo.Role;
import com.Jaziel.service.RoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/26 16:51
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    // 获得所有角色对象
    @RequestMapping("/findAllRole")
    public Result findAllRole() {
        try {
            List<Role> roles = roleService.findAll();
            return new Result(true, MessageConstant.GET_ROLE_SUCCESS, roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ROLE_FAIL);
        }
    }

    // 分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return roleService.findPage(queryPageBean);
    }

    // 添加角色
    @RequestMapping("/add")
    public Result add(@RequestBody Role role) {
        try {
            roleService.add(role);
            return new Result(true, MessageConstant.ROLE_ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ROLE_ADD_FAIL);
        }
    }

    // 获得角色和权限之间的关系
    @RequestMapping("/findRelRAndP")
    public Result findRelRAndP(Integer id){
        try {
            List<Integer> permissionIds = roleService.findRelRAndP(id);
            return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS, permissionIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_PERMISSION_FAIL);
        }
    }

    // 更改角色权限
    @RequestMapping("/changePermission")
    public Result changePermission(@RequestBody Integer[] permissionIds, Integer id){
        System.out.println(id);
        System.out.println(Arrays.toString(permissionIds));
        try {
            roleService.changePermission(permissionIds, id);
            return new Result(true, MessageConstant.PERMISSION_ADD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.PERMISSION_ADD_FAIL);
        }
    }
}
