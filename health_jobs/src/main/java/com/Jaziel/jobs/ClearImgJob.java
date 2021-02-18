package com.Jaziel.jobs;

import com.Jaziel.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisPool;
import com.Jaziel.constant.RedisConstant;

import java.util.Set;

/**
 * @author 王杰
 * @date 2021/1/30 16:11
 * 自定义Job，实现定时清理垃圾图片
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        System.out.println("clear...");
        //根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null){
            for (String fileName : set) {
                // 从千牛云中删除垃圾图片
                QiniuUtils.deleteFileFromQiniu(fileName);
                //从Redis集合中删除图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
                System.out.println("定时任务，清除图片" + fileName);
            }
        }
    }
}
