package com.Jaziel.service;

import com.Jaziel.pojo.User;

/**
 * @author 王杰
 * @date 2021/2/15 10:05
 */
public interface UserService {
    User findByUsername(String username);
}
