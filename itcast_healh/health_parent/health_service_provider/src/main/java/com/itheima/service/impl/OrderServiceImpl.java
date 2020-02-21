package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.entity.Result;
import com.itheima.pojo.*;
import com.itheima.service.OrderService;
import com.itheima.dao.OrderSettingDao;
import com.itheima.service.StemealService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

/**
 * 订单服务服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    //注入DAO对象
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private JedisPool jedisPool;

    public Result order(OrderForm orderForm) throws Exception {
        String orderDate = orderForm.getOrderDate();
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting os = orderSettingDao.findByOrderDate(orderDate);
    //1 判断预约当天是否进行了预约设置
        if (os == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }


    //2 判断当天预约人数是否满了
        int reservations = os.getReservations();//已预约人数
        int number = os.getNumber();//可预约人数
        if (reservations >= number) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
    //3 判断是否为会员
        String telephone = orderForm.getTelephone();
        Member member = memberDao.findByTelephone(telephone);
    //4 判断是否重复(同一天,同是一人,同一个套餐) orderForm.getId();套餐id
        if (member != null) {
            Integer memberId = member.getId();
            List<Order> list = orderDao.findByCondition(new Order(memberId, date, orderForm.getId()));
            if (list!=null && list.size() > 0){
                  return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            //为空说明没有注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setName(orderForm.getName());
            member.setSex(orderForm.getSex());
            member.setIdCard(orderForm.getIdCard());
            member.setRegTime(date);
            memberDao.add(member);

        }


    //5 将用户添加到预约表中,并在预约人数+1更新;
        Order order = new Order();
        order.setMemberId(member.getId());//设置会员ID
        order.setOrderDate(date);//预约日期
        order.setOrderType(orderForm.getOrderType());//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(orderForm.getId());//套餐ID

        orderDao.add(order);


        os.setReservations(os.getReservations() + 1);//设置已预约人数+1
        orderSettingDao.editReservationsByOrderDate(os);


        return new Result(true, MessageConstant.ORDER_SUCCESS,order.getId());


    }

    //预约成功后的信息查询
    public OrderSuccess findById(String id) {
        return orderDao.findById(id);
    }

    @Override
    public SuperPojo findById2(String id) {
        return orderDao.findById2(id);
    }


}
