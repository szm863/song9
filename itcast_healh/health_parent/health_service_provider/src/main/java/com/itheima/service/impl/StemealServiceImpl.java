package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.MobileDao;
import com.itheima.dao.StemealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.StemealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查组项服务
 */
@Service(interfaceClass = StemealService.class)
@Transactional
public class StemealServiceImpl implements StemealService {
    //注入DAO对象
    @Autowired
    private StemealDao stemealDao;
    @Autowired
    private MobileDao mobileDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfig;
    @Value("${out_put_path}")
    private  String outputpath;


    public void add(Setmeal setmeal, Integer[] checkgroupIds) {

        stemealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        System.out.println(setmealId);
        if (checkgroupIds.length>0&&checkgroupIds!=null){
            for (Integer checkgroupId : checkgroupIds) {
                HashMap<String, Integer> map = new HashMap<>();
                map.put("setmeal_id",setmealId);
                map.put("checkgroup_id",checkgroupId);
                stemealDao.setSetmealAndCheckGroup(map);

            }
        }
        //存到数据库的图片名称放到redies
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);

        // 生成所有的数据静态页面
       this.createStaticPage();



    }
    //生成静态页面方法
    public void createStaticPage() {
        //先将数据查出来
        List<Setmeal> list = mobileDao.findAllSetmeal();
//       生成套餐展示页
        createStaticShowPage(list);
//       生成套餐详情静态页面（可能有多个）
        createStaticDetailPage(list);



    }
    //       生成套餐展示页
    private void createStaticShowPage(List<Setmeal> list) {
        Map map = new HashMap();
        map.put("setmealList" ,list);
        //生成静态页面
        createPage("mobile_setmeal.ftl","m_mobile_setmeal.html",map);
    }

    //生成详情静态页面
    public void createStaticDetailPage(List<Setmeal> list) {


        for (Setmeal setmeal : list) {
            HashMap map = new HashMap();
            map.put("setmeal",mobileDao.findById(setmeal.getId().toString()));
        //生成静态页面
            createPage("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);



        }
    }
    //单独生成静态页面
    public  void  createPage(String templateName,String htmlPageName, Map map){
        Configuration configuration = freeMarkerConfig.getConfiguration();//获得配置对象
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构造输出流
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputpath + "/" + htmlPageName)),"UTF-8"));
            //输出文件
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public List<CheckGroup> findAll() {
        return  stemealDao.findAll();

    }
//
//    public PageResult pageQuery(QueryPageBean queryPageBean) {
//        Integer currentPage = queryPageBean.getCurrentPage();
//        Integer pageSize = queryPageBean.getPageSize();
//        String queryString = queryPageBean.getQueryString();
//
//        PageHelper.startPage(currentPage, pageSize);
//        //按照条件查询
//
//        Page<CheckGroup> page = checkGroupDao.checkGroupByCondition(queryString);
//        long total = page.getTotal();
//        List<CheckGroup> rows = page.getResult();
//        return new PageResult(total, rows);
//    }

    //分页
    public PageResult findPage(QueryPageBean pageBean) {
        String queryString = pageBean.getQueryString();
        Integer currentPage = pageBean.getCurrentPage();
        Integer pageSize = pageBean.getPageSize();
        //pageSize,currentPage这个顺序不对会导致查不出来数据
        PageHelper.startPage(currentPage,pageSize);
        //Page是sqring封装好的对象
        Page<Setmeal>  page= stemealDao.findPage(queryString);
        long total = page.getTotal();
        List<Setmeal> rows = page.getResult();
        return new PageResult(total,rows);
    }


}
