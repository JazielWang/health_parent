package com.Jaziel.service.impl;

import com.Jaziel.dao.RoleDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Role;
import com.Jaziel.service.RoleService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/26 16:56
 */
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();

        PageHelper.startPage(currentPage, pageSize);
        Page<Role> roles = roleDao.QueryByCondition(queryString);

        return new PageResult(roles.getTotal(), roles.getResult());
    }

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public List<Integer> findRelRAndP(Integer id) {
        return roleDao.findRelRAndP(id);
    }

    @Override
    public void changePermission(Integer[] permissionIds, Integer roleid) {
        System.out.println(roleid);
        System.out.println(Arrays.toString(permissionIds));
        // 根据 role 的 ID值，删除所有 role 和 permission 的关联消息
        roleDao.deleteAll(roleid);
        Map<String, Object> map = new HashMap<>();
        for (Integer permissionId : permissionIds) {
            map.put("permissionId", permissionId);
            map.put("roleid", roleid);
            // 重现写入 role 和 permission 的关系表
            roleDao.editRoleAndPermission(map);
        }
    }
}
