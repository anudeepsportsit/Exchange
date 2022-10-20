package com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dao;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto.CurrencyDetailsTO;

import java.util.List;

public interface CurrencyDao {

    List<CurrencyDetailsTO> getCurrencyList();

    String saveCurrencyConversion(String currencyConversion);
}
