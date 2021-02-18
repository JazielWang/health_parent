package com.Jaziel.dao;

import com.Jaziel.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/1/29 20:57
 */
public interface SetmealDao {
    void add(Setmeal setmeal);

    void setGroupAndMeal(Map<String, Integer> map);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    Setmeal findSetmealById(int id);

    List<Map<String, Object>> getSetmealReport();
}
