package com.Jaziel.dao;

import com.Jaziel.pojo.Role;
import com.Jaziel.pojo.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/15 10:21
 */
public interface UserDao {
    User findByUsername(String username);

    Page<User> queryByCondition(String queryString);

    void add(User user);

    User findByTelephone(String telephone);

    List<Integer> findRoleIdsByUserIds(Integer id);

    void deleteByUser(Integer userId);

    void setUserAndRoleRel(Map<String, Object> map);
}
