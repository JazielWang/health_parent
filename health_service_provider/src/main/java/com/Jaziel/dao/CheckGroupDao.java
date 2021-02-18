package com.Jaziel.dao;

import com.Jaziel.pojo.CheckGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/1/26 17:33
 * 持久层Dao接口
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setGroupAndItem(Map<String, Integer> map);

    Page<CheckGroup> findByString(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteRelation(Integer id);

    List<CheckGroup> findAll();
}
