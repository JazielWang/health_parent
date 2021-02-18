package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.constant.RedisConstant;
import com.Jaziel.constant.RedisMessageConstant;
import com.Jaziel.entity.Result;
import com.Jaziel.utils.TXSMSUtils;
import com.Jaziel.utils.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author 王杰
 * @date 2021/2/9 15:58
 * 发送验证码控制器
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    // 发送用户预约验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        System.out.println(telephone);
        // 获得四位数的验证码
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
        // 发送短信
        try {
            TXSMSUtils.sendShortMessage(TXSMSUtils.TEMPLATE_ID, telephone, code);

        } catch (ClientException e) {
            e.printStackTrace();
            // 验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 将验证码 code 储存在 redis（5min）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.ORDER_CODE, 3000, code.toString());
        // 验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //用户手机快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //随机生成6位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        //给用户发送验证码
        try{
            TXSMSUtils.sendShortMessage(TXSMSUtils.TEMPLATE_ID,telephone,validateCode.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //将验证码保存到redis（5分钟）
        jedisPool.getResource().setex(telephone + RedisMessageConstant.ORDER_CODE,3000,validateCode.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
