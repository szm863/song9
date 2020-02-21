package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.StemealService;
import com.itheima.utils.QiniuUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 检查组管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference//修改套餐服务
    private StemealService  stemealService;
    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean pageBean) {
        try {
            PageResult page = stemealService.findPage(pageBean);

            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }




//这是上传七牛的所有图片必经之路
     @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String filename = imgFile.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        String lastName = filename.substring(index- 1).toString();//.jpg
        String vitryFilename = UUID.randomUUID().toString() + lastName;//为了防止重复

        //文件上传
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), vitryFilename);
            //使用缓存将图所有的的图片信息存到大的set集合中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, vitryFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,vitryFilename);
    }
    //添加图片和检查组的信息
    @RequestMapping("/add")
    public  Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){

        try {
            stemealService.add(setmeal,checkgroupIds);
         return   new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }
    //查检查组所有信息
    @RequestMapping("/findAll")
    public  Result findAll(){
        try {
            List<CheckGroup>  checkGroup= stemealService.findAll();
            return   new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return   new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}
