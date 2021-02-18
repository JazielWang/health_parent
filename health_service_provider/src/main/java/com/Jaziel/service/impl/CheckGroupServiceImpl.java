package com.Jaziel.service.impl;

import com.Jaziel.dao.CheckGroupDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.CheckGroup;
import com.Jaziel.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/1/26 17:32
 * 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //添加检查组合，同时需要设置检查组合和检查项的关联关系
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        this.setGroupAndItem(checkItemIds, checkGroupId);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findByString(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //编辑检查组，同时需要更新和检查项的关联关系
        //更新检查组基本信息
        checkGroupDao.edit(checkGroup);
        //根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteRelation(checkGroup.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        Integer checkGroupId = checkGroup.getId();
        this.setGroupAndItem(checkitemIds, checkGroupId);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    // 向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
    public void setGroupAndItem(Integer[] checkitemIds, Integer checkGroupId){
        if (checkitemIds != null && checkitemIds.length > 0){
            /*直接使用Map<String , Integer> map = new HashMap<>()，可能在多次查询中有影响*/
            Map<String , Integer> map = null;
            for (Integer checkitemId : checkitemIds) {
                map = new HashMap<>();
                map.put("checkGroupId", checkGroupId);
                map.put("checkItemId", checkitemId);
                checkGroupDao.setGroupAndItem(map);
            }
        }
    }
}
