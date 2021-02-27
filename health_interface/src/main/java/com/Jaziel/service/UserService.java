package com.Jaziel.service;

import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Role;
import com.Jaziel.pojo.User;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/2/15 10:05
 */
public interface UserService {
    User findByUsername(String username);

    PageResult findPage(QueryPageBean queryPageBean);

    void add(User user);


    List<Integer> findRoleIdsByUserIds(Integer id);

    void changeRole(Integer[] roleIds, Integer userId);
}
