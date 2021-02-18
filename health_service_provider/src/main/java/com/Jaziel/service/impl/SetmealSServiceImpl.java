package com.Jaziel.service.impl;

import com.Jaziel.constant.RedisConstant;
import com.Jaziel.dao.SetmealDao;
import com.Jaziel.entity.PageResult;
import com.Jaziel.entity.QueryPageBean;
import com.Jaziel.pojo.Setmeal;
import com.Jaziel.service.SetmealService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/1/29 20:56
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealSServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private JedisPool jedisPool;

    private final String CLASS_PATH = "E:\\CS\\B&H\\health_parent\\health_mobile\\src\\main\\webapp\\pages";

    // 添加预约项
    @Override
    public void add(Setmeal setmeal, Integer[] checkGroupIds) {
        setmealDao.add(setmeal);
        System.out.println(setmeal.getId());
        this.setGroupAndMeal(checkGroupIds, setmeal.getId());
        // 获得添加图片名称
        String img = setmeal.getImg();
        //将上传图片名称存入Redis，基于Redis的Set集合存储
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);

        //新增套餐后需要重新生成静态页面
        generateMobileStaticHtml();
    }

    // 生成静态页面
    public void generateMobileStaticHtml() {
        // 准备模板文件中所需的数据
        List<Setmeal> setmealList = setmealDao.findAll();
        // 生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        // 生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    // 生成套餐详情静态页面（多个）
    private void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        Map<String, Object> map = null;
        for (Setmeal setmeal : setmealList) {
            map = new HashMap<>();
            // 此处map的key必须和.ftl 文件中定义的变量名称一致，否则会无法访问到
            map.put("setmeal", setmealDao.findById(setmeal.getId()));
            generateStaticHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", map);
        }
    }

    // 生成套餐列表静态页面
    private void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> map = new HashMap<>();
        map.put("setmealList", setmealList);
        generateStaticHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

    private void generateStaticHtml(String templateName, String htmlName, Map<String, Object> dataMap) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File file = new File(CLASS_PATH + "\\" + htmlName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            // 输出文件
            template.process(dataMap, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null){
                    out.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public Setmeal findSetmealById(int id) {
        return setmealDao.findSetmealById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }

    // 向中间表(t_setmeal_checkgroup)插入数据（建立检查组和检查项关联关系）
    public void setGroupAndMeal(Integer[] checkGroupIds, Integer setmealId) {
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            Map<String, Integer> map = new HashMap<>();
            for (Integer checkGroupId : checkGroupIds) {
                map.put("setmealId", setmealId);
                map.put("checkGroupIds", checkGroupId);
                setmealDao.setGroupAndMeal(map);

            }
        }
    }
}
