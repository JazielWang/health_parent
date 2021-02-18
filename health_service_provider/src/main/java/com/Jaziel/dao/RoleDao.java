package com.Jaziel.dao;

import com.Jaziel.pojo.Role;

import java.util.Set;

/**
 * @author 王杰
 * @date 2021/2/15 10:21
 */
public interface RoleDao {
    Set<Role> findByUserId(Integer userId);
}
