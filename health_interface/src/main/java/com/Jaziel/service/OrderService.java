package com.Jaziel.service;

import com.Jaziel.entity.Result;

import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/9 18:15
 * 体检预约服务接口
 */
public interface OrderService {
    public Result submit(Map<String, Object> map) throws Exception;

    Map<String, Object> findByid(int id) throws Exception;
}
