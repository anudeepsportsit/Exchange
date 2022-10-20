package com.bettorlogic.victoryexch.middlelayer.adminpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto.CurrencyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.service.CurrencyService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + AdminPanelConstants.ADMIN_PANEL + AdminPanelConstants.CURRENCY)
public class CurrencyRestController {

    @Autowired
    CurrencyService currencyService;
    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @RequestMapping(value = AdminPanelConstants.GET_CURRENCY_LIST, method = RequestMethod.GET)
    public SportsBookOutput getCurrencyList() {
        try {
            List<CurrencyDetailsTO> currencyList = currencyService.getCurrency();
            return outputGenerator.getSuccessResponse(currencyList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.GET_CURRENCY_LIST);
        }
    }

    @RequestMapping(value = AdminPanelConstants.SAVE_CURRENCY_CONVERSION, method = RequestMethod.POST)
    public SportsBookOutput saveCurrencyRate(@RequestBody @Valid @NotNull List<CurrencyDetailsTO> currencyConversionDetailsList) {
        try {
            String message = currencyService.saveCurrencyRate(currencyConversionDetailsList);
            return outputGenerator.getSuccessResponse(message);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, AdminPanelConstants.SAVE_CURRENCY_CONVERSION);
        }
    }
}
