package com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDwnLineLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketLoginDetailsTO;

public interface MyReportService {
    MyReportDetailsTO getMyReportDownLineList(MyReportDwnLineLoginDetailsTO loginDetails);

    MyReportMarketDetailsTO getMyReportMarket(MyReportMarketLoginDetailsTO loginDetails);
}
