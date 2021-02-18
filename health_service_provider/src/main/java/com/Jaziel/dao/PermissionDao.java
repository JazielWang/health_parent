package com.Jaziel.dao;

import com.Jaziel.pojo.Permission;

import java.util.Set;

/**
 * @author 王杰
 * @date 2021/2/15 10:34
 */
public interface PermissionDao {
    Set<Permission> findByRoleId(Integer roleId);
}
