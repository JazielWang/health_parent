package com.Jaziel.service;

import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Role;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/26 16:55
 */
public interface RoleService {

    List<Role> findAll();

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Role role);

    List<Integer> findRelRAndP(Integer id);

    void changePermission(Integer[] permissionIds, Integer roleid);
}
