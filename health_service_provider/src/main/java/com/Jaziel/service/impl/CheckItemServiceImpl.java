package com.Jaziel.service.impl;

import com.Jaziel.dao.CheckItemDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.CheckItem;
import com.Jaziel.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/1/22 14:24
 * <p>
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        // 完成分页查询，基于mybatis提供的分页助手实现
        PageHelper.startPage(currentPage, pageSize);

        Page<CheckItem> checkItems = checkItemDao.selectByQuery(queryString);
        long total = checkItems.getTotal();
        List<CheckItem> rows = checkItems.getResult();
        return new PageResult(total, rows);
    }

    @Override
    public void deleteById(Integer id) {
        long count = checkItemDao.countByCheckItemId(id);
        if (count > 0) {
            // 检查项已经和检查组绑定，无法删除
            throw new RuntimeException("检查项已经和检查组绑定，无法删除");
        }
        checkItemDao.deleteCheckItemById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> list = checkItemDao.findAll();
        return list;
    }
}
