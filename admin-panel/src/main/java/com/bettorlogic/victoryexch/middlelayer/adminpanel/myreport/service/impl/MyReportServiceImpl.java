package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dao.MyReportDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDwnLineLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.service.MyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyReportServiceImpl implements MyReportService {

    @Autowired
    private MyReportDao myReportDao;

    @Override
    public MyReportDetailsTO getMyReportDownLineList(MyReportDwnLineLoginDetailsTO loginDetails) {
        return myReportDao.extractMyReportDownLineList(loginDetails);

    }

    @Override
    public MyReportMarketDetailsTO getMyReportMarket(MyReportMarketLoginDetailsTO loginDetails) {
        return myReportDao.extractMyReportMarket(loginDetails);
    }
}