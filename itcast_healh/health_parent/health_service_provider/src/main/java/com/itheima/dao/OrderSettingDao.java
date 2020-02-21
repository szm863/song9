package com.itheima.dao;

import com.itheima.pojo.LeftOb;
import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {

    long findCountByOrderDate(Date orderDate);

    void updataOrderSetting(OrderSetting orderSetting);

    void inserOrderSetting(OrderSetting orderSetting);

    List<LeftOb> getOrderSettingByMonth(String date);

    OrderSetting findByOrderDate(String orderDate);

    void editReservationsByOrderDate(OrderSetting os);
}
