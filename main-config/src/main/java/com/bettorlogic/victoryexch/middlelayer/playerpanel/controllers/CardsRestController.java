package com.bettorlogic.victoryexch.middlelayer.playerpanel.controllers;

import com.bettorlogic.victoryexch.middlelayer.common.utils.ExceptionConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookConstants;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutput;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsData;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsOutputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.service.CardsService;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.utils.CardsConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(SportsBookConstants.BASE_URL + CardsConstants.CARDS_PANEL)
public class CardsRestController {

    private static final Logger LOGGER = LogManager.getLogger(CardsRestController.class);

    @Autowired
    private SportsBookOutputGenerator outputGenerator;

    @Autowired
    private CardsService cardsService;

    @RequestMapping(value = CardsConstants.GET_ALL_CARDS, method = RequestMethod.GET)
    public SportsBookOutput getAllCards() {
        try {
            List<CardsOutputTO> cardsOutputTOList = cardsService.getAllCards();
            return outputGenerator.getSuccessResponse(cardsOutputTOList);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.GET_ALL_CARDS);
        }
    }

    @RequestMapping(value = CardsConstants.SAVE_CARD_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput saveCardDetails(@RequestBody @Valid @NotNull CardDetailsInputTO cardDetails) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            boolean isSaved = false;
            if (!StringUtils.isEmpty(cardDetails)) {
                isSaved = cardsService.saveCardDetails(cardDetails);
                result.put(CardsConstants.IS_SAVED, isSaved);
                return outputGenerator.getSuccessResponse(result);
            } else {
                throw new Exception(ExceptionConstants.INVALID_CARD_DETAILS);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.SAVE_CARD_DETAILS);
        }
    }

    @RequestMapping(value = CardsConstants.GET_CARDS_COMBINATIONS, method = RequestMethod.POST)
    public SportsBookOutput getCombinations(@RequestBody CardDetailsInputTO input) {
        try {
            List<CardsData> cards = cardsService.getCombinations(input);
            return outputGenerator.getSuccessResponse(cards);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.GET_CARDS_COMBINATIONS);
        }

    }

    @RequestMapping(value = CardsConstants.SAVE_POKER_DETAILS, method = RequestMethod.POST)
    public SportsBookOutput savePokerDetails(@RequestBody @Valid @NotNull CardDetailsInputTO cardDetails) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            boolean isSaved = false;
            if (!StringUtils.isEmpty(cardDetails)) {
                isSaved = cardsService.savePokerDetails(cardDetails);
                result.put(CardsConstants.IS_SAVED, isSaved);
                return outputGenerator.getSuccessResponse(result);
            } else {
                throw new Exception(ExceptionConstants.INVALID_CARD_DETAILS);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.SAVE_POKER_DETAILS);
        }
    }

    @RequestMapping(value = CardsConstants.GET_ALL_TEENPATI_VIEW_ODDS, method = RequestMethod.GET)
    public SportsBookOutput getAllTeenpatiViewOdds() {
        try {
            List<CardsData> cards = cardsService.getAllTeenpatiViewOdds();
            return outputGenerator.getSuccessResponse(cards);
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.GET_ALL_TEENPATI_VIEW_ODDS);
        }
    }

    @RequestMapping(value = CardsConstants.UPDATE_TEENPATI_ODDS, method = RequestMethod.POST)
    public SportsBookOutput updateTeenpatiOdds(@RequestBody @Valid @NotNull CardDetailsInputTO cardDetails) {
        try {
            Map<String, Boolean> result = new HashMap<>();
            boolean isUpdated = false;
            if (!StringUtils.isEmpty(cardDetails)) {
                isUpdated = cardsService.updateTeenpatiOdds(cardDetails);
                result.put(CardsConstants.IS_UPDATED, isUpdated);
                return outputGenerator.getSuccessResponse(result);
            } else {
                throw new Exception(ExceptionConstants.INVALID_CARD_DETAILS);
            }
        } catch (Exception ex) {
            return outputGenerator.getFailureResponse(ex, CardsConstants.UPDATE_TEENPATI_ODDS);
        }
    }




}