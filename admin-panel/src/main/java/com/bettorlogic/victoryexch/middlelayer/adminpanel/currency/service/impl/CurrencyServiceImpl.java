package com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dao.CurrencyDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto.CurrencyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public List<CurrencyDetailsTO> getCurrency() {
        return currencyDao.getCurrencyList();
    }

    @Override
    public String saveCurrencyRate(List<CurrencyDetailsTO> currencyConversionDetailsList) {
        StringBuilder bulkData = new StringBuilder();

        currencyConversionDetailsList.forEach(conversionDetails -> bulkData.append(processBulkData(conversionDetails)));
        String bulkInsert = bulkData.substring(0, bulkData.length() - 1);
        return currencyDao.saveCurrencyConversion(bulkInsert);
    }

    private String processBulkData(CurrencyDetailsTO conversionDetails) {
        return "(" + conversionDetails.getCurrencyId() + "," + conversionDetails.getConversionRate() + "),";
    }

}
