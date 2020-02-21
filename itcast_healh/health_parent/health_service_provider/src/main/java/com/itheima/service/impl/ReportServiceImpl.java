package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;

import com.itheima.dao.MemberDao;
import com.itheima.dao.ReportDao;
import com.itheima.dao.StemealDao;
import com.itheima.pojo.BusinessData;
import com.itheima.service.ReportService;

import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.Cleaner;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;


/**
 * @author  stq
 * 数据展示
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;
    @Autowired
    private StemealDao stemealDao;
    @Autowired
    private MemberDao memberDao;

    /**
     * 套餐预约占比饼形图
    * */
    @Override
    public Map<String, Object> getSetmealReport() {
        Map<String, Object> data =new HashMap<>();
        List<Map<String, Object>> setmealCounts= stemealDao.getSetmealReport();
        data.put("setmealCount",setmealCounts);
        ArrayList<String> names = new ArrayList<>();
        for (Map<String, Object> list : setmealCounts) {
        //套餐名称
         String name= (String) list.get("name");
            names.add(name);
        }
        data.put("setmealNames",names);

        return data;
    }
/**
 * 查询进一个月的新增成员人数
 */
    @Override
    public Map<String, Object> getMemberReport() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> months = new ArrayList<>();
        List<Integer> monthsCount= new ArrayList<>();
                //获得日历对象，模拟时间就是当前时间
        Calendar calendar = Calendar.getInstance();
        //        过去1年的时间 这时Calendar时间就变为12月以前时间
        calendar.add(Calendar.MONTH,-12);

        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            Date time = calendar.getTime();
            String timeMount = new SimpleDateFormat("yyyy.MM").format(time);
            months.add(timeMount);
            System.out.println(timeMount);
//            2019.02.31 当月这个数据,每次查一次
             Integer count= memberDao.findMemberCountByMonths(timeMount+".31");
            monthsCount.add(count);

        }
        map.put("months",months);
        map.put("memberCount",monthsCount);


        return map;
    }

    /**
     * 查询运营数据
     */
    @Override
    public BusinessData getBusinessReportData() {
        BusinessData businessData = new BusinessData();
//        当前日期
        Date date = new Date();
        try {
            String todayDate = DateUtils.parseDate2String(date, "yyyy-MM-dddd");
            businessData.setReportDate(todayDate);
        } catch (Exception e) {

        }
//        新增会员数


        return businessData;
    }
}
