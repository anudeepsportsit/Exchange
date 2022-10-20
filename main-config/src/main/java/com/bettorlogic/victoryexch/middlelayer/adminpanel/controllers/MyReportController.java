package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportDwnLineLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.dto.MyReportMarketLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.service.MyReportService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.myreport.utils.MyReportConstants;
import com.bettorlogic.victoryexch.middlelayer.common.service.SportsBookService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + MyReportConstants.MY_REPORT)
public class MyReportController {

    @Autowired
    private MyReportService myReportService;

    @Autowired
    private SportsBookOutputGenerator sportsBookOutputGenerator;

    @Autowired
    private SportsBookService sportsBookService;


    @RequestMapping(value = MyReportConstants.MY_REPORT_DOWN_LIST, method = RequestMethod.POST)
    public SportsBookOutput myReportDownLineList(@RequestBody @NotNull @Valid MyReportDwnLineLoginDetailsTO loginDetails) {
        try {
            if (!StringUtils.isEmpty(loginDetails.getUserToken())) {
                Boolean res = sportsBookService.validateUserToken(loginDetails.getUserToken());
                if (res) {
                    MyReportDetailsTO result = myReportService.getMyReportDownLineList(loginDetails);
                    return sportsBookOutputGenerator.getSuccessResponse(result);
                } else {

                    return sportsBookOutputGenerator.getSuccessResponse(MyReportConstants.USER_ID_NOT_EXIST);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception ex) {
            return sportsBookOutputGenerator.getFailureResponse(ex, MyReportConstants.MY_REPORT_DOWN_LIST);
        }
    }

    @RequestMapping(value = MyReportConstants.MY_REPORT_MARKET, method = RequestMethod.POST)
    public SportsBookOutput myReportMarket(@RequestBody @NotNull @Valid MyReportMarketLoginDetailsTO loginDetails) {
        try {
            if (!StringUtils.isEmpty(loginDetails.getUserToken())) {
                Boolean res = sportsBookService.validateUserToken(loginDetails.getUserToken());
                if (res) {
                    MyReportMarketDetailsTO myReportMarketDetailsTO = myReportService.getMyReportMarket(loginDetails);
                    return sportsBookOutputGenerator.getSuccessResponse(myReportMarketDetailsTO);

                } else {
                    return sportsBookOutputGenerator.getSuccessResponse(MyReportConstants.USER_ID_NOT_EXIST);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception ex) {
            return sportsBookOutputGenerator.getFailureResponse(ex, MyReportConstants.MY_REPORT_MARKET);
        }
    }

}
