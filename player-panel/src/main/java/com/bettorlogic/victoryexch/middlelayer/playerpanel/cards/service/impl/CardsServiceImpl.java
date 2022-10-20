package com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.service.impl;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dao.CardsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsData;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsOutputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsServiceImpl implements CardsService {
    @Autowired
    private CardsDao cardsDao;

    @Override
    public List<CardsOutputTO> getAllCards() {
        return cardsDao.getAllCards();
    }

    public boolean saveCardDetails(CardDetailsInputTO cardDetails) {
        return cardsDao.saveCardDetails(cardDetails);
    }

    public boolean savePokerDetails(CardDetailsInputTO cardDetails) {
        return cardsDao.savePokerDetails(cardDetails);
    }

    @Override
    public List<CardsData> getCombinations(CardDetailsInputTO cardDetailsInputTO) {
        return cardsDao.getCombinations(cardDetailsInputTO);
    }

    @Override
    public List<CardsData> getAllTeenpatiViewOdds() {
        return cardsDao.getAllTeenpatiViewOdds();
    }

    public boolean updateTeenpatiOdds(CardDetailsInputTO cardDetails) {
        return cardsDao.updateTeenpatiOdds(cardDetails);
    }
}
