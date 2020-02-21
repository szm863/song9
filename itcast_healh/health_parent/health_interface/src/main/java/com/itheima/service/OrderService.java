package com.itheima.service;

import com.itheima.entity.Result;
import com.itheima.pojo.OrderForm;
import com.itheima.pojo.OrderSuccess;
import com.itheima.pojo.SuperPojo;

public interface OrderService {
    Result order(OrderForm orderForm) throws Exception;

    OrderSuccess findById(String id);

    SuperPojo findById2(String id);
}
