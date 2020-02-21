package com.itheima.dao;


import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StemealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(HashMap<String, Integer> map);

    List<CheckGroup> findAll();

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> findAllSetmeal();

    List<Map<String, Object>> getSetmealReport();
}
