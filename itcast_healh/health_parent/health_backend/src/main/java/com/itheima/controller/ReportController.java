package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.BusinessData;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.ReportService;
import com.itheima.service.StemealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 数据展示
 * @author Administrator
 */

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;
/**
* 运营数据
**/
    @RequestMapping("/getBusinessReportData")
    public  Result getBusinessReportData(){

        try {
            BusinessData list=reportService.getBusinessReportData();
            return   new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }


    /**
     * 套餐占比
     **/
    @RequestMapping("/getSetmealReport")
    public  Result getSetmealReport(){

        try {
         Map<String,Object>  list=reportService.getSetmealReport();
         return   new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }


    @RequestMapping("/getMemberReport")
    public  Result getMemberReport(){

        try {
            Map<String,Object>  list=reportService.getMemberReport();
            return   new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }
}
