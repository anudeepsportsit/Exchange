package com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.service;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto.CurrencyDetailsTO;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDetailsTO> getCurrency();

    String saveCurrencyRate(List<CurrencyDetailsTO> currencyConversionDetailsList);

}
