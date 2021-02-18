package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.constant.RedisMessageConstant;
import com.Jaziel.entity.Result;
import com.Jaziel.pojo.Order;
import com.Jaziel.service.OrderService;
import com.Jaziel.utils.TXSMSUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/9 17:53
 * 体检预约控制器
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String, Object> map) {
        // 获取表单中的验证码与 redis 中的进行比较
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.ORDER_CODE);
        // 如果验证码比对成功，将表单数据加入
        if (validateCodeInRedis != null && validateCodeInRedis.equals(validateCode)) {
            // 预约类型，如何预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = null;
            // 调用体检预约服务
            try {
                result = orderService.submit(map);
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if (result.isFlag()) {
                try {
                    // 预约成功，给用户发送短信
                    TXSMSUtils.sendShortMessage(TXSMSUtils.ORDER_NOTICE, telephone, (String) map.get("orderDate"));
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;
        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @RequestMapping("/findById")
    public Result findById(int id){
        try{
            //查询预约信息成功
            Map<String, Object> map = orderService.findByid(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
