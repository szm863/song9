package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 检查组管理
 */

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference//查找服务
    private CheckGroupService checkGroupService;
    //编辑完成后添加数据
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        System.out.println(checkitemIds);
        System.out.println(checkGroup);
        try {
            checkGroupService.edit(checkitemIds,checkGroup);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }



    @RequestMapping("/selectCheckGroupIdToCheckItemIds")
    public Result selectCheckGroupIdToCheckItemIds(Integer id){

        try{
            Integer[] ids= checkGroupService.selectCheckGroupIdToCheckItemIds(id);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,ids);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        CheckGroup checkGroup= new CheckGroup();
        try{
            checkGroup= checkGroupService.findById(id);
            return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }

    //检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return pageResult;
    }

//添加新的检查组：基本信息+检查项信息
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup , Integer[] checkitemIds ){

        try {
            checkGroupService.add(checkitemIds,checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

}
