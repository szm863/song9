package com.itheima.pojo;

import java.io.Serializable;

public class OrderSuccess implements Serializable {
//     <p>体检人：{{orderInfo.member}}</p>
//                            <p>体检套餐：{{orderInfo.setmeal}}</p>
//                            <p>体检日期：{{orderInfo.orderDate}}</p>
//                            <p>预约类型：{{orderInfo.orderType}}</p>
//                        </div>
     private String  member;
     private String setmeal;
     private String orderDate;
     private String orderType;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(String setmeal) {
        this.setmeal = setmeal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
