package com.itheima.service;

import com.itheima.pojo.LeftOb;
import com.itheima.pojo.OrderSetting;

import java.util.ArrayList;
import java.util.List;

public interface OrderSettingService {
    void upload(ArrayList<OrderSetting> data);

    List<LeftOb> getOrderSettingByMonth(String date);

    void orderSettingService(OrderSetting orderSetting);
}
