package com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dao;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsData;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsOutputTO;
import java.util.List;

public interface CardsDao {
    List<CardsOutputTO> getAllCards();
    boolean saveCardDetails(CardDetailsInputTO cardDetails);
    List<CardsData> getCombinations(CardDetailsInputTO cardDetailsInputTO);
    boolean savePokerDetails(CardDetailsInputTO cardDetails);
    List<CardsData> getAllTeenpatiViewOdds();
    boolean updateTeenpatiOdds(CardDetailsInputTO cardDetails);

}
