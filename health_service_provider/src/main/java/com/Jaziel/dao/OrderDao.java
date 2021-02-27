package com.Jaziel.dao;

import com.Jaziel.entity.Result;
import com.Jaziel.pojo.Order;
import com.Jaziel.pojo.OrderList;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void add(Order order);
    public List<Order> findByCondition(Order order);
    public Map findById4Detail(Integer id);
    public Integer findOrderCountByDate(String date);
    public Integer findOrderCountAfterDate(String date);
    public Integer findVisitsCountByDate(String date);
    public Integer findVisitsCountAfterDate(String date);
    public List<Map> findHotSetmeal();
    Page<Map> findByQuery(String queryString);
    void confirm(Integer id);

    void delete(Integer id);

}
