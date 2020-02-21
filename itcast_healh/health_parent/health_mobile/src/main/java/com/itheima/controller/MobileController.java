package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.OrderForm;
import com.itheima.pojo.Setmeal;
import com.itheima.service.MobileService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/mobile")
public class MobileController{
    @Reference
    private MobileService mobileService;

    //只查询一张表
    @RequestMapping(value="/findBySetmealId", method = RequestMethod.POST)
    public Result findBySetmealId(String id){
        try {
           Setmeal setmeal = mobileService.findBySetmealId(id);

            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }


    }
    @RequestMapping(value="/getAllSetmeal", method = RequestMethod.POST)
    public Result getSetmeal(){
        try {
            List<Setmeal> list = mobileService.findAllSetmeal();

            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }


    }

    @RequestMapping(value="/findById", method = RequestMethod.POST)
    public Result findById(String id){
        try {
            Setmeal setmeal = mobileService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }


    }



}
