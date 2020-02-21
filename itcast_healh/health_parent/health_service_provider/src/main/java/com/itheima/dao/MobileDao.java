package com.itheima.dao;

import com.itheima.pojo.Setmeal;

import java.util.List;

public interface MobileDao {

    List<Setmeal> findAllSetmeal();



//    int[] setmealIdFindGroupIds(String id);
//
//    int[] groupIdFindItemIds(int groupId);
//
//    CheckItem itemIdFindAll(int itemId);
//
//    CheckGroup findByGroupId(int groupId);

    Setmeal findById(String id);

    Setmeal findBySetmealId(String id);
}
