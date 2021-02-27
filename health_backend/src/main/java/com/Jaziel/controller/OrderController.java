package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.entity.Result;
import com.Jaziel.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/25 9:02
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    //预约列表的分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return orderService.findPage(queryPageBean);
    }

    // 电脑端添加预约
    @RequestMapping("/add")
    public Result add(@RequestBody Map<String, Object> formData, Integer[] orderIds){
        try {
            return orderService.add(formData, orderIds);
        }catch (Exception e){
            return new Result(false, "预约失败");
        }
    }

    // 更改预约状态
    @RequestMapping("/confirm")
    public Result confirm(Integer id){
        try {
            orderService.confirm(id);
            return new Result(true, MessageConstant.ORDER_CONFIRM_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ORDER_CONFIRM_FAIL);
        }
    }

    // 删除表单
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            orderService.delete(id);
            return new Result(true, MessageConstant.ORDER_DELETE_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ORDER_DELETE_FAIL);
        }
    }
}
