package com.itheima.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * @author stq
 * */
public class BusinessData  implements Serializable {
    private String reportDate;
    private String todayNewMember;
    private String totalMember;
    private String thisWeekNewMember;
    private String thisMonthNewMember;
    private String todayOrderNumber;
    private String todayVisitsNumber;
    private String thisWeekOrderNumber;
    private String thisWeekVisitsNumber;
    private String thisMonthOrderNumber;
    private String thisMonthVisitsNumber;
    private List<HotSetmeal> hotSetmeal;

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getTodayNewMember() {
        return todayNewMember;
    }

    public void setTodayNewMember(String todayNewMember) {
        this.todayNewMember = todayNewMember;
    }

    public String getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(String totalMember) {
        this.totalMember = totalMember;
    }

    public String getThisWeekNewMember() {
        return thisWeekNewMember;
    }

    public void setThisWeekNewMember(String thisWeekNewMember) {
        this.thisWeekNewMember = thisWeekNewMember;
    }

    public String getThisMonthNewMember() {
        return thisMonthNewMember;
    }

    public void setThisMonthNewMember(String thisMonthNewMember) {
        this.thisMonthNewMember = thisMonthNewMember;
    }

    public String getTodayOrderNumber() {
        return todayOrderNumber;
    }

    public void setTodayOrderNumber(String todayOrderNumber) {
        this.todayOrderNumber = todayOrderNumber;
    }

    public String getTodayVisitsNumber() {
        return todayVisitsNumber;
    }

    public void setTodayVisitsNumber(String todayVisitsNumber) {
        this.todayVisitsNumber = todayVisitsNumber;
    }

    public String getThisWeekOrderNumber() {
        return thisWeekOrderNumber;
    }

    public void setThisWeekOrderNumber(String thisWeekOrderNumber) {
        this.thisWeekOrderNumber = thisWeekOrderNumber;
    }

    public String getThisWeekVisitsNumber() {
        return thisWeekVisitsNumber;
    }

    public void setThisWeekVisitsNumber(String thisWeekVisitsNumber) {
        this.thisWeekVisitsNumber = thisWeekVisitsNumber;
    }

    public String getThisMonthOrderNumber() {
        return thisMonthOrderNumber;
    }

    public void setThisMonthOrderNumber(String thisMonthOrderNumber) {
        this.thisMonthOrderNumber = thisMonthOrderNumber;
    }

    public String getThisMonthVisitsNumber() {
        return thisMonthVisitsNumber;
    }

    public void setThisMonthVisitsNumber(String thisMonthVisitsNumber) {
        this.thisMonthVisitsNumber = thisMonthVisitsNumber;
    }

    public List<HotSetmeal> getHotSetmeal() {
        return hotSetmeal;
    }

    public void setHotSetmeal(List<HotSetmeal> hotSetmeal) {
        this.hotSetmeal = hotSetmeal;
    }
}
