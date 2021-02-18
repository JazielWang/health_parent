package com.Jaziel.dao;

import com.Jaziel.pojo.User;

/**
 * @author 王杰
 * @date 2021/2/15 10:21
 */
public interface UserDao {
    User findByUsername(String username);
}
