package com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dao.CurrencyDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.currency.dto.CurrencyDetailsTO;
import com.bettorlogic.victoryexch.middlelayer.common.utils.ColumnLabelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    private static final String PROC_GET_CURRENCY_LIST = "select * from get_currency_list()";
    private static final String PROC_SAVE_CURRENCY_CONVERSION = "select * from update_currency_conversion_rates(?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CurrencyDetailsTO> getCurrencyList() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_CURRENCY_LIST,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY), (CallableStatement cs) -> getCurrencyDetails(cs.executeQuery()));
    }

    private List<CurrencyDetailsTO> getCurrencyDetails(ResultSet executeQuery) throws SQLException {
        List<CurrencyDetailsTO> currencyDetailsList = new ArrayList<>();
        while (executeQuery.next()) {
            CurrencyDetailsTO currencyDetails = new CurrencyDetailsTO();
            currencyDetails.setCurrencyId(executeQuery.getInt(ColumnLabelConstants.CURRENCY_ID));
            currencyDetails.setCurrencyName(executeQuery.getString(ColumnLabelConstants.CURRENCY_NAME));
            currencyDetails.setCurrencyCode(executeQuery.getString(ColumnLabelConstants.CURRENCY_CODE));
            currencyDetailsList.add(currencyDetails);
        }
        return currencyDetailsList;
    }

    @Override
    public String saveCurrencyConversion(String currencyConversionDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_CURRENCY_CONVERSION,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, currencyConversionDetails);
            return cs;
        }, (CallableStatement cs) -> this.saveCoversion(cs.executeQuery()));
    }

    private String saveCoversion(ResultSet executeQuery) {
        return ColumnLabelConstants.SUCCESS;
    }

}
