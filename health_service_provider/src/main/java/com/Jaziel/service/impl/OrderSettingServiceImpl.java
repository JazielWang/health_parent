package com.Jaziel.service.impl;

import com.Jaziel.dao.OrderSettingDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.OrderSetting;
import com.Jaziel.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 王杰
 * @date 2021/2/4 17:26
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> data) {
        if (data.size() > 0 && data != null){
            for (OrderSetting orderSetting : data) {
                long count = orderSettingDao.getCountByOrderDate(orderSetting.getOrderDate());
                if (count > 0){
                    // 预约信息已经存在，进行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    // 不存在预约信息，执行插入操作
                    orderSettingDao.addOrderSetting(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByDate(String date) {
        String begin = date + "-1", end = date + "-31";
        Map<String, String> mapDate = new HashMap<>();
        mapDate.put("begin", begin);
        mapDate.put("end", end);
        List<OrderSetting> map = orderSettingDao.getOrderSettingByDate(mapDate);
        List<Map> res = new ArrayList<>();
        for (OrderSetting orderSetting : map) {
            Map<String, Object> m = new HashMap<>();
            m.put("date", orderSetting.getOrderDate().getDate());
            m.put("number", orderSetting.getNumber());
            m.put("reservations", orderSetting.getReservations());
            res.add(m);
        }
        return res;
    }

    // 根据日期设置可预约人数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        long count = orderSettingDao.getCountByOrderDate(orderDate);
        if (count > 0){
            // 已经有了预约人数设置，则更改
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            // 没有预约信息，则添加
            orderSettingDao.addOrderSetting(orderSetting);
        }
    }
}
