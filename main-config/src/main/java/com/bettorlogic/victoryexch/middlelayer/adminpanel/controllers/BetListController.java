package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListDetailsWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.dto.BetListLoginDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.service.BetListService;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.betlist.utils.BetListConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
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
import java.util.List;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + BetListConstants.BET_LIST)
public class BetListController {

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private BetListService betListService;
    @Autowired
    private SportsBookService sportsBookService;

    @RequestMapping(value = BetListConstants.GET_BET_LIST, method = RequestMethod.POST)
    public SportsBookOutput getBetList(@RequestBody @NotNull @Valid BetListLoginDetailsTO loginDetails) {
        try {
            if (!StringUtils.isEmpty(loginDetails.getUserToken())) {
                Integer res = sportsBookService.getUsersList(loginDetails.getUserToken());
                if (res != 0) {
                    List<Integer> users = betListService.getUserIds(res);
//                    if(loginDetails.getSportId() == 0){
//                        BetListDetailsWrapperTO betListDetailsWrapperTO = betListService.getBetListAllSports(loginDetails,users);
//                        return outputGenerator.getSuccessResponse(betListDetailsWrapperTO);
//                    }else{
//                        if(loginDetails.getSportId() == 5){
//                            loginDetails.setSportId(4);
//                            BetListDetailsWrapperTO betListDetailsWrapperTO = betListService.getBetListSport(loginDetails,users);
//                        }if(loginDetails.getSportId() == 3){
//                            loginDetails.setSportId(4);
//                            BetListDetailsWrapperTO betListDetailsWrapperTO = betListService.getBetListSport(loginDetails,users);
//                        }
//                    }
                    BetListDetailsWrapperTO betListDetailsWrapperTO = betListService.getBetList(loginDetails,users);
                    return outputGenerator.getSuccessResponse(betListDetailsWrapperTO);
                } else {
                    return outputGenerator.getSuccessResponse(MyReportConstants.USER_ID_NOT_EXIST);
                }
            } else {
                throw new Exception(ExceptionConstants.SESSION_EXPIRED);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, BetListConstants.GET_BET_LIST);

        }
    }
}
