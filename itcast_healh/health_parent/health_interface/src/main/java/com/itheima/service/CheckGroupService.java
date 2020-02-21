package com.itheima.service;


import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

//服务接口
public interface CheckGroupService {

    PageResult pageQuery(QueryPageBean queryPageBean);

    void add(Integer[] checkitemIds, CheckGroup checkGroup);

    CheckGroup findById(Integer id);

    Integer[] selectCheckGroupIdToCheckItemIds(Integer id);

    void edit(Integer[] checkitemIds, CheckGroup checkGroup);
}
