package com.Jaziel.dao;

import com.Jaziel.pojo.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 王杰
 * @date 2021/2/15 10:21
 */
public interface RoleDao {
    Set<Role> findByUserId(Integer userId);

    List<Role> findAll();

    Page<Role> QueryByCondition(String queryString);

    void add(Role role);

    List<Integer> findRelRAndP(Integer id);

    void deleteAll(Integer roleid);

    void editRoleAndPermission(Map<String, Object> map);
}
