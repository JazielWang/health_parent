package com.Jaziel.service.impl;

import com.Jaziel.dao.PermissionDao;
import com.Jaziel.pojo.Permission;
import com.Jaziel.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/27 11:13
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findAll() {
        return permissionDao.findALl();
    }
}
