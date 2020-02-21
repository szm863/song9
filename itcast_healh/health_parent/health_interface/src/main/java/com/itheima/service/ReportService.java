package com.itheima.service;

import com.itheima.pojo.BusinessData;

import java.util.List;
import java.util.Map;

public interface ReportService {
    Map<String, Object> getSetmealReport();

    Map<String, Object> getMemberReport();

    BusinessData getBusinessReportData();
}
