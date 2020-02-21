package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.LeftOb;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单服务服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    //注入DAO对象
    @Autowired
    private OrderSettingDao orderSettingDao;


   //批量导入数据
    public void upload(ArrayList<OrderSetting> data) {

       if (data!=null&&data.size()>0){

           for (OrderSetting orderSetting : data) {
               //检查之前没有有插入数据
           long  countByOrderDate =  orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
              if (countByOrderDate>0){
                  //说明已经是有过第一次数据插入
                  orderSettingDao.updataOrderSetting(orderSetting);
              }else {

                  //之前没有数据插入
                  orderSettingDao.inserOrderSetting(orderSetting);
              }

           }

       }
    }

    //获取当前月信息
    public List<LeftOb> getOrderSettingByMonth(String date) {


        int i = date.lastIndexOf("-")+1;
        String s = date.substring(i).toString();
        int i1 = Integer.parseInt(s);
        if (i1>0&&i1<10&&s.length()<2){
            StringBuilder sb = new StringBuilder(date);
            int i2 = date.substring(i).indexOf("0");
            date= sb.insert(i,"0").toString();
        }


        List<LeftOb>  orderSettingByMonth = orderSettingDao.getOrderSettingByMonth(date);
        for (LeftOb leftOb : orderSettingByMonth) {

        System.out.println(leftOb+"2123121");
        }
        return orderSettingByMonth;

    }

  //编辑预约人数
    public void orderSettingService(OrderSetting orderSetting) {
        //判断数据库中有没有能被编辑的时间
        long time = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (time>0){
        orderSettingDao.updataOrderSetting(orderSetting);
        }
    }


}
