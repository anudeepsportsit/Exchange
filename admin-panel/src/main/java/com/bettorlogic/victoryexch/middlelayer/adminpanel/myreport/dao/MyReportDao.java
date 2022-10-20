package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDwnLineLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketLoginDetailsTO;

public interface MyReportDao {
    MyReportDetailsTO extractMyReportDownLineList(MyReportDwnLineLoginDetailsTO myReportLoginDetails);

    MyReportMarketDetailsTO extractMyReportMarket(MyReportMarketLoginDetailsTO loginDetails);
}