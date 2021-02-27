package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.entity.Result;
import com.Jaziel.pojo.Role;
import com.Jaziel.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/15 16:11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    // 通过 spring security 提供的 securityContextHolder 获得用户名称
    @RequestMapping("/getUsername")
    public Result getUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        if (username != null) {
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        } else {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    // 用户的分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return userService.findPage(queryPageBean);
    }

    // 添加用户
    @RequestMapping("/add")
    public Result add(@RequestBody com.Jaziel.pojo.User user) {
        try {
            userService.add(user);
            return new Result(true, MessageConstant.USER_ADD_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.USER_ADD_FAIL);
        }
    }

    @RequestMapping("/findRoleIdsByUserIds")
    public Result findRoleIdsByUserIds(Integer id){
        try {
            List<Integer> roleIds = userService.findRoleIdsByUserIds(id);
            return new Result(true, MessageConstant.GET_ROLE_SUCCESS, roleIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_ROLE_FAIL);
        }
    }


    @RequestMapping("/changeRole")
    public Result changeRole(@RequestBody Integer[] roleIds, Integer id){
        System.out.println(id);
        System.out.println(Arrays.toString(roleIds));
        try {
            userService.changeRole(roleIds, id);
            return new Result(true, MessageConstant.CHANGE_ROLE_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.CHANGE_ROLE_FAIL);
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 10; i++){
            map.put("wj", i);
            map.put("lj", i+1);
            System.out.println(map);
        }
    }
}
