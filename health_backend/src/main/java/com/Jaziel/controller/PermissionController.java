package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.Result;
import com.Jaziel.pojo.Permission;
import com.Jaziel.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/27 11:08
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Permission> permissionList = permissionService.findAll();
            return new Result(true, MessageConstant.PERMISSION_QUERY_SUCCESS, permissionList);
        }catch (Exception e){
            return new Result(false, MessageConstant.PERMISSION_QUERY_FAIL);
        }
    }
}
