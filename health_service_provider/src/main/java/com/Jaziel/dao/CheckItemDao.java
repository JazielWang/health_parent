package com.Jaziel.dao;

import com.Jaziel.entity.PageResult;
import com.Jaziel.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author 王杰
 * @date 2021/1/22 14:26
 *
 * 持久层Dao接口
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);

    Page<CheckItem> selectByQuery(String queryString);

    long countByCheckItemId(Integer id);

    void deleteCheckItemById(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);

    List<CheckItem> findAll();
}
