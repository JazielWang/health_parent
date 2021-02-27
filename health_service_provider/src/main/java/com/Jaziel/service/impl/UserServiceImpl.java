package com.Jaziel.service.impl;

import com.Jaziel.dao.PermissionDao;
import com.Jaziel.dao.RoleDao;
import com.Jaziel.dao.UserDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Permission;
import com.Jaziel.pojo.Role;
import com.Jaziel.pojo.User;
import com.Jaziel.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 王杰
 * @date 2021/2/15 10:20
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return null;
        }
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if (permissions != null && permissions.size() > 0) {
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage, pageSize);

        Page<User> users = userDao.queryByCondition(queryString);
        long total = users.getTotal();
        List<User> result = users.getResult();

        return new PageResult(total, result);
    }

    @Override
    public void add(User user) {
        String telephone = user.getTelephone();
        User user1 = userDao.findByTelephone(telephone);
        if (user1 == null) {
            userDao.add(user);
        }
    }

    @Override
    public List<Integer> findRoleIdsByUserIds(Integer id) {
        return userDao.findRoleIdsByUserIds(id);
    }

    @Override
    public void changeRole(Integer[] roleIds, Integer userId) {
        System.out.println(userId);
        System.out.println(Arrays.toString(roleIds));
        // 根据用户ID清除原有数据
        userDao.deleteByUser(userId);
        // 将新的关系重新添加到表格中
        Map<String, Object> map = new HashMap<>();
        for (Integer roleId : roleIds) {
            map.put("roleId", roleId);
            map.put("userId", userId);
            userDao.setUserAndRoleRel(map);
        }
    }
}