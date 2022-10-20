package com.bettorlogic.victoryexch.middlelayer.jbetfairng.controllers;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.BetRequestTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.BetResponseTO;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.service.BetPlacement;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetFairConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL)
public class BetPlacementController implements Callable {

    @Autowired
    private BetRequestTO betRequest;
    @Autowired
    private BetResponseTO betResponse;
    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    public BetPlacementController(BetRequestTO betRequestTO) {
        this.betRequest = betRequestTO;
    }

    @RequestMapping(value = BetFairConstants.BET_PLACE_BET_FAIR, method = RequestMethod.POST)
    public SportsBookOutput placeBetFairPlacement(@RequestBody @NotNull @Valid BetRequestTO betRequestTO) {
        try {
            if (StringUtils.isEmpty(betRequestTO.getToken())) {
                throw new Exception(ExceptionConstants.INVALID_LOGIN_TOKEN);
            }
            if (StringUtils.isEmpty(betRequestTO.getAppKey())) {
                throw new Exception(ExceptionConstants.APP_KEY);
            }
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Callable<BetResponseTO> callable = new BetPlacementController(betRequestTO);
            Future<BetResponseTO> future = executorService.submit(callable);
            return outputGenerator.getSuccessResponse(future.get());
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, BetFairConstants.BET_PLACE_BET_FAIR);
        }

    }

    @Override
    public BetResponseTO call() throws Exception {
        Thread.sleep(1000);
        BetPlacement betPlacement = new BetPlacement();
        return betResponse;
    }
}

