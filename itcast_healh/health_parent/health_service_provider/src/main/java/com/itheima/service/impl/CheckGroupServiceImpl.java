package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 检查组项服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    //注入DAO对象
    @Autowired
    private CheckGroupDao checkGroupDao;


    //查询
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage, pageSize);
        //按照条件查询

        Page<CheckGroup> page = checkGroupDao.checkGroupByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> rows = page.getResult();
        return new PageResult(total, rows);
    }

    @Override
    public void add(Integer[] checkitemIds, CheckGroup checkGroup) {
        //先添加checkGroup到t_checkgroup表
        if (checkGroup != null) {
            checkGroupDao.addCheckgroup(checkGroup);
        }
        //获取添加后的id，并将这个t_checkgroup的id和 checkitemIds的id存在t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId, checkitemIds);

    }

    //查t_checkgroup表
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    //查组ID查询关联的多个检查项ID
    public Integer[] selectCheckGroupIdToCheckItemIds(Integer id) {

        return checkGroupDao.selectCheckGroupIdToCheckItemIds(id);
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //1 先将检查组中修改的内容重新设置
        checkGroupDao.edit(checkGroup);
        //2 将中间表的数据中有checkGroupId都删了
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteById(checkGroupId);
        //3  把修改后的关联数据重新添加
        if (checkitemIds != null && checkitemIds.length > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            for (Integer checkitemId : checkitemIds) {
                map.put("checkGroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }

    }


    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {

        HashMap<String, Integer> map = new HashMap<>();
        if (checkGroupId != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                map.put("checkGroupId", checkGroupId);
                map.put("checkitemId", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);

            }

        }

    }
}
