package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.*;
import com.itheima.service.OrderSettingService;
import com.itheima.service.StemealService;
import com.itheima.utils.POIUtils;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 预约设置
 */

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference//修改套餐服务
    private OrderSettingService orderSettingService;
    //编辑数据
    @RequestMapping("/editNumberByDate")
    public  Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.orderSettingService(orderSetting);
            return new Result(true,MessageConstant.ORDER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDER_FULL);
        }


    }



    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        System.out.println(excelFile.getOriginalFilename());
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            ArrayList<OrderSetting> data = new ArrayList<>();
            if (list.size()>0&&list!=null){
                for (String[] rows : list) {
                    String orderDate = rows[0];
                    String number = rows[1];
                    OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                    data.add(orderSetting);

                }
            }
            orderSettingService.upload(data);
             return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }//根据当前的月数和年来查数据
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("date")String date) {//日期
//        System.out.println(date);
        try {
           List<LeftOb> list = orderSettingService.getOrderSettingByMonth(date);
            return  new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }









}
