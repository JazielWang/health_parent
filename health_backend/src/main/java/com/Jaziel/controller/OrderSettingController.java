package com.Jaziel.controller;

import com.Jaziel.constant.MessageConstant;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.entity.Result;
import com.Jaziel.pojo.OrderSetting;
import com.Jaziel.service.OrderSettingService;
import com.Jaziel.utils.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/4 16:12
 * 预约控制类
 */
@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0){
                List<OrderSetting> data = new ArrayList<>();
                for (String[] strings : list) {
                    String date = strings[0];
                    String number = strings[1];
                    OrderSetting orderSetting = new OrderSetting(new Date(date), Integer.parseInt(number));
                    data.add(orderSetting);
                }
                orderSettingService.add(data);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            // 文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /*根据年月获得 ordersetting 数据*/
    @RequestMapping("/getOrderSettingByDate")
    public Result getOrderSettingByDate(String date){
        try{
            List<Map> list = orderSettingService.getOrderSettingByDate(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_SUCCESS);
        }
    }

    // 可预约人数设置
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            // 预约设置成功
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            // 预约设置失败
            return new Result(false, MessageConstant.ORDERSETTING_SUCCESS);
        }
    }
}
