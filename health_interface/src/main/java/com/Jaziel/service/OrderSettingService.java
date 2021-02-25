package com.Jaziel.service;

import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/4 16:21
 * 预约
 */
public interface OrderSettingService {
    public void add(List<OrderSetting> data);

    List<Map> getOrderSettingByDate(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
