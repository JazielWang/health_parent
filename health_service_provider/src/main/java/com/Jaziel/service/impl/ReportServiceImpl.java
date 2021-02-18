package com.Jaziel.service.impl;

import com.Jaziel.dao.MemberDao;
import com.Jaziel.dao.OrderDao;
import com.Jaziel.service.ReportService;
import com.Jaziel.utils.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王杰
 * @date 2021/2/16 19:44
 * 获得运营统计数据
 *  Map数据格式：
 *  todayNewMember -> number
 *  totalMember -> number
 *  thisWeekNewMember -> number
 *  thisMonthNewMember -> number
 *  todayOrderNumber -> number
 *  todayVisitsNumber -> number
 *  thisWeekOrderNumber -> number
 *  thisWeekVisitsNumber -> number
 *  thisMonthOrderNumber -> number
 *  thisMonthVisitsNumber -> number
 *  hotSetmeal -> List<Setmeal>
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        // 获得今天的日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        // 获得本周一的月份日期
        String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        // 获得本月第一天
        String firstMonths = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        // 今天新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);
        // 总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        // 本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(weekMonday);
        // 本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstMonths);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(weekMonday);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstMonths);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(weekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstMonths);

        //热门套餐查询
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
}
