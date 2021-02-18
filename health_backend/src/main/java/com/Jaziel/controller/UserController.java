package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王杰
 * @date 2021/2/15 16:11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // 通过 spring security 提供的 securityContextHolder 获得用户名称
    @RequestMapping("/getUsername")
    public Result getUsername() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        if (username != null) {
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
        }else {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
