package com.Jaziel.dao;

import com.Jaziel.pojo.OrderList;
import com.Jaziel.pojo.OrderSetting;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/4 17:28
 */
public interface OrderSettingDao {

    void addOrderSetting(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    long getCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByDate(Map<String, String> mapDate);
    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(Date orderDate);
}
