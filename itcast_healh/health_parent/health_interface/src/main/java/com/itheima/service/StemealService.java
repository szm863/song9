package com.itheima.service;


import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;

//上传图片接口
public interface StemealService {

    void add(Setmeal setmeal, Integer[] checkgroupIds);

    List<CheckGroup> findAll();

    PageResult findPage(QueryPageBean pageBean);
}
