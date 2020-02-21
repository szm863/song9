package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;


    public  void clearImg(){
        //SETMEAL_PIC_RESOURCES这是大的，放在前面
        Set<String> diffSet = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (diffSet!=null){
            for (String picName : diffSet) {
                //删七牛中的除了数据库mysql以外的图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //将redis的set集合中的mysql以外的图片名删除
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
            }
        }


    }

    public void sout(){
        System.out.println("13213123");
    }
}
