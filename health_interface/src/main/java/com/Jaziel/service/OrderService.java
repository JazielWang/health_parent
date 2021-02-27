package com.Jaziel.service;

import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
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

    PageResult findPage(QueryPageBean queryPageBean);

    Result add(Map<String, Object> formData, Integer[] orderIds) throws Exception;

    void confirm(Integer id);

    void delete(Integer id);
}
