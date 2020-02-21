package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.*;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submit(@RequestBody OrderForm orderForm) {
        Result result = null;
        //检验验证码输入是否正确
        String validateCode = orderForm.getValidateCode();//用户输入的验证码
        String telephone = orderForm.getTelephone();
        String code = jedisPool.getResource().get(telephone + validateCode);
        if (!validateCode.equals("8888")) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //验证码通过
            orderForm.setOrderType(Order.ORDERTYPE_WEIXIN);//设置预约类型，分为微信预约、电话预约

            try {
                result = orderService.order(orderForm);

            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
            if(result.isFlag()){//发送成功预约短信短信
                try{
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderForm.getOrderDate());
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            return result;
        }



    }
    //预约成功后的的信息查询
    @RequestMapping(value="/findById", method = RequestMethod.POST)
    public Result findById(String id){
        try {
//            OrderSuccess orderSuccess = orderService.findById(id);
            SuperPojo orderSuccess = orderService.findById2(id);
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,orderSuccess);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }


    }
}
