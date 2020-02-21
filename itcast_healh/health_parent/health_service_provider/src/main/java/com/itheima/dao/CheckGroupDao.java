package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.HashMap;

public interface CheckGroupDao {

    Page<CheckGroup> checkGroupByCondition(String queryString);

    void addCheckgroup(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(HashMap<String, Integer> map);

    CheckGroup findById(Integer id);

    Integer[] selectCheckGroupIdToCheckItemIds(Integer id);

    void deleteById(Integer checkGroupId);

    void edit(CheckGroup checkGroup);

    void addCheckGroupAndCheckItem(HashMap<String, Integer> map);
}
