package com.Jaziel.service;

import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/1/29 20:52
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkGroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    Setmeal findSetmealById(int id);

    List<Map<String, Object>> getSetmealReport();
}
