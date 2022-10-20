package com.bettorlogic.victoryexch.middlelayer.common.utils;


import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.Objects;


@Component
public class SportsBookOutputGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SportsBookOutputGenerator.class);

    public SportsBookOutput getSuccessResponse(Object data) {
        SportsBookOutput output = new SportsBookOutput();
        try {
            output.setDescription(HttpStatus.OK.getReasonPhrase())
                    .setStatusCode(HttpStatus.OK.value())
                    .setData(data)
                    .setSuccess(true);
            return output;
        } catch (Exception ex) {
            return this.getFailureResponse(ex, SportsBookOutputGenerator.class.getName());
        }
    }


    public SportsBookOutput getFailureResponse(Exception ex, String serviceName) {
        LOGGER.error("Raised Exception in " + serviceName + " service: ", ex);
        SportsBookOutput output = new SportsBookOutput();
        FieldError invalidInput;
        if (ex instanceof MethodArgumentNotValidException) {
            invalidInput = Objects.requireNonNull(((MethodArgumentNotValidException) ex).getBindingResult().getFieldError());
            output.setStatusCode(HttpStatus.BAD_REQUEST.value());
            output.setDescription(invalidInput.getDefaultMessage());
        } else if (ex instanceof AddressException) {
            output.setStatusCode(HttpStatus.BAD_REQUEST.value());
            output.setDescription(ex.getMessage());
        } else if (ex instanceof PSQLException) {
            LOGGER.warn("Invalid User");
            output.setStatusCode(HttpStatus.BAD_REQUEST.value());
            output.setDescription(ExceptionConstants.INVALID_USER);
        } else if (ex instanceof IOException) {
            LOGGER.warn("GeoLiteCity.datfFile not found in the location classpath:/locationfiles");
            output.setStatusCode(HttpStatus.FAILED_DEPENDENCY.value());
            output.setDescription(ExceptionConstants.GEO_FILE_NOT_FOUND);
        } else {
            switch (ex.getMessage()) {
                case ExceptionConstants.INVALID_ADMIN_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_ADMIN_ROLE);
                    break;
                case ExceptionConstants.INVALID_DATE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_DATE);
                    break;
                case ExceptionConstants.INVALID_USER:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_USER);
                    break;
                case ExceptionConstants.INVALID_SUPER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_SUPER_ROLE);
                    break;
                case ExceptionConstants.INVALID_MASTER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_MASTER_ROLE);
                    break;
                case ExceptionConstants.INVALID_PLAYER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_PLAYER_ROLE);
                    break;
                case ExceptionConstants.WRONG_TOKEN:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.WRONG_TOKEN);
                    break;
                case ExceptionConstants.INVALID_EMAIL_ID_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_EMAIL_ID_ENTERED);
                    break;
                case ExceptionConstants.INVALID_EMAILID:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_EMAILID);
                    break;
                case ExceptionConstants.INVALID_USER_NAME_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_USER_NAME_ENTERED);
                    break;
                case ExceptionConstants.INVALID_CHANGE_ADMIN_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_CHANGE_ADMIN_ROLE);
                    break;
                case ExceptionConstants.INVALID_CHANGE_SUPER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_CHANGE_SUPER_ROLE);
                    break;
                case ExceptionConstants.IINVALID_CHANGE_MASTER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.IINVALID_CHANGE_MASTER_ROLE);
                    break;
                case ExceptionConstants.INVALID_CHANGE_PLAYER_ROLE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_CHANGE_PLAYER_ROLE);
                    break;
                case ExceptionConstants.INVALID_ODD_TYPE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_ODD_TYPE);
                    break;
                case ExceptionConstants.ACCOUNT_LOCKED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.ACCOUNT_LOCKED);
                    break;
                case ExceptionConstants.INVALID_PASSWORD:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_PASSWORD);
                    break;
                case ExceptionConstants.TIME_INTERVAL_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.TIME_INTERVAL_NULL);
                    break;
                case ExceptionConstants.LEAGUE_ID_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.LEAGUE_ID_NULL);
                    break;
                case ExceptionConstants.SPORT_ID_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.SPORT_ID_NULL);
                    break;
                case ExceptionConstants.INVALID_OPTION_ID:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_OPTION_ID);
                    break;
                case ExceptionConstants.FIELDS_EMPTY:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.FIELDS_EMPTY);
                    break;
                case ExceptionConstants.SESSION_EXPIRED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.SESSION_EXPIRED);
                    break;
                case ExceptionConstants.PAGE_ID_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.PAGE_ID_NULL);
                    break;
                case ExceptionConstants.SPORT_ID_INVALID:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.SPORT_ID_INVALID);
                    break;
                case ExceptionConstants.LEAGUE_ID_INVALID:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.LEAGUE_ID_INVALID);
                    break;
                case ExceptionConstants.INVALID_REQUEST:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_REQUEST);
                    break;
                case ExceptionConstants.INVALID_LOGIN_TOKEN:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_LOGIN_TOKEN);
                    break;
                case ExceptionConstants.UNAUTHORIZED:
                    output.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                    output.setDescription(SportsBookConstants.EmailVerificationConstants.NOT_VERIFIED);
                    break;
                case ExceptionConstants.INVALID_PHONE_NUMBER_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_PHONE_NUMBER_ENTERED);
                    break;
                case ExceptionConstants.ID_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.ID_NULL);
                    break;
                case ExceptionConstants.INVALID_USER_ID:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_USER_ID);
                    break;
                case ExceptionConstants.BET_ID_STAUS_ID_NULL:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.BET_ID_STAUS_ID_NULL);
                    break;
                case ExceptionConstants.HOME_PROFIT_LOSS_EMPTY:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.HOME_PROFIT_LOSS_EMPTY);
                    break;
                case ExceptionConstants.AWAY_PROFIT_LOSS_EMPTY:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.AWAY_PROFIT_LOSS_EMPTY);
                    break;
                case ExceptionConstants.INVALID_EVENT_ID_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_EVENT_ID_ENTERED);
                    break;
                case ExceptionConstants.INVALID_MARKET_ID_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.INVALID_MARKET_ID_ENTERED);
                    break;
                case ExceptionConstants.EMPTY_PROVIDER_ID_ENTERED:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.EMPTY_PROVIDER_ID_ENTERED);
                    break;
                case ExceptionConstants.DRAW_PROFIT_LOSS_EMPTY:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.DRAW_PROFIT_LOSS_EMPTY);
                    break;
                case SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE_LIMIT);
                    break;
                case SportsBookConstants.BetSlipConstants.SUSPENDED_LOCKED_USER:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.SUSPENDED_LOCKED_USER);
                    break;
                case SportsBookConstants.BetSlipConstants.USER_NOT_PLAYER:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.USER_NOT_PLAYER);
                    break;
                case SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.INSUFFICIENT_BALANCE);
                    break;
                case SportsBookConstants.BetSlipConstants.INVALID_USER:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.INVALID_USER);
                    break;
                case SportsBookConstants.BetSlipConstants.EXPOSURE_ZERO:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.EXPOSURE_ZERO);
                    break;
                case SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(SportsBookConstants.BetSlipConstants.EXCEEDED_EXPOSURE);
                    break;
                case ExceptionConstants.SUSPEND:
                    output.setStatusCode(HttpStatus.BAD_REQUEST.value());
                    output.setDescription(ExceptionConstants.SUSPEND);
                    break;
                default:
                    output.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    output.setDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
                    break;
            }
        }
        return output;
    }
}