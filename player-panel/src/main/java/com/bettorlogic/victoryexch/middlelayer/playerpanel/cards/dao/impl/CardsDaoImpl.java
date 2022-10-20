package com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dao.impl;

import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dao.CardsDao;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardDetailsInputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsData;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.dto.CardsOutputTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.cards.utils.CardsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CardsDaoImpl implements CardsDao {

    private static final String GET_CARDS_IMAGES = "select id,image_url from cards order by id";
    private static final String PROC_SAVE_CARDS_DETAILS = "select * from save_card_details(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String PROC_GET_COMBINATIONS = "select * from get_selected_cards_combinations(?,?,?,?,?,?)";
    private static final String PROC_SAVE_POKER_DETAILS = "select * from save_poker_card_details(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String PROC_GET_TEENPATI_VIEW_ODDS = "select * from get_all_teenpati_combinations()";
    private static final String PROC_UPDATE_TEENPATI_ODDS = "select * from update_teenpati_odds(?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CardsOutputTO> getAllCards() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(GET_CARDS_IMAGES,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> extractCards(cs.executeQuery()));
    }

    private List<CardsOutputTO> extractCards(ResultSet rs) throws SQLException {
        List<CardsOutputTO> cardList = new ArrayList<>();
        CardsOutputTO card;
        while (rs.next()) {
            card = new CardsOutputTO();
            card.setCardId(rs.getInt(CardsConstants.CARD_ID));
            card.setCardImage(rs.getString(CardsConstants.CARD_IMAGE));
            cardList.add(card);
        }
        return cardList;
    }

    public boolean saveCardDetails(CardDetailsInputTO cardDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_CARDS_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, cardDetails.getPlayerAFirstCard() != null ? cardDetails.getPlayerAFirstCard() : 0);
            cs.setInt(2, cardDetails.getPlayerASecondCard() != null ? cardDetails.getPlayerASecondCard() : 0);
            cs.setInt(3, cardDetails.getPlayerAThirdCard() != null ? cardDetails.getPlayerAThirdCard() : 0);
            cs.setInt(4, cardDetails.getPlayerBFirstCard() != null ? cardDetails.getPlayerBFirstCard() : 0);
            cs.setInt(5, cardDetails.getPlayerBSecondCard() != null ? cardDetails.getPlayerBSecondCard() : 0);
            cs.setInt(6, cardDetails.getPlayerBThirdCard() != null ? cardDetails.getPlayerBThirdCard() : 0);
            cs.setBigDecimal(7, cardDetails.getPlayerAbackOdds() != null ? cardDetails.getPlayerAbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(8, cardDetails.getPlayerAlayOdds() != null ? cardDetails.getPlayerAlayOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(9, cardDetails.getPlayerBbackOdds() != null ? cardDetails.getPlayerBbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(10, cardDetails.getPlayerBlayOdds() != null ? cardDetails.getPlayerBlayOdds() : BigDecimal.ZERO);
            cs.setString(11, cardDetails.getWinner());
            return cs;
        }, (CallableStatement cs) -> this.extractCardDetails(cs.executeQuery()));
    }

    public boolean updateTeenpatiOdds(CardDetailsInputTO cardDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_UPDATE_TEENPATI_ODDS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setBigDecimal(1, cardDetails.getPlayerAbackOdds() != null ? cardDetails.getPlayerAbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(2, cardDetails.getPlayerAlayOdds() != null ? cardDetails.getPlayerAlayOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(3, cardDetails.getPlayerBbackOdds() != null ? cardDetails.getPlayerBbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(4, cardDetails.getPlayerBlayOdds() != null ? cardDetails.getPlayerBlayOdds() : BigDecimal.ZERO);
            cs.setInt(5,cardDetails.getId());
            return cs;
        }, (CallableStatement cs) -> this.extractCardDetails(cs.executeQuery()));
    }

    public boolean savePokerDetails(CardDetailsInputTO cardDetails) {
        return jdbcTemplate.execute(connection -> {
            CallableStatement cs = connection.prepareCall(PROC_SAVE_POKER_DETAILS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setInt(1, cardDetails.getPlayerAFirstCard() != null ? cardDetails.getPlayerAFirstCard() : 0);
            cs.setInt(2, cardDetails.getPlayerASecondCard() != null ? cardDetails.getPlayerASecondCard() : 0);
            cs.setInt(3, cardDetails.getPlayerBFirstCard() != null ? cardDetails.getPlayerBFirstCard() : 0);
            cs.setInt(4, cardDetails.getPlayerBSecondCard() != null ? cardDetails.getPlayerBSecondCard() : 0);
            cs.setInt(5, cardDetails.getTheFirstFlopCard() != null ? cardDetails.getTheFirstFlopCard() : 0);
            cs.setInt(6, cardDetails.getTheSecondFlopCard() != null ? cardDetails.getTheSecondFlopCard() : 0);
            cs.setInt(7, cardDetails.getTheThirdFlopCard() != null ? cardDetails.getTheThirdFlopCard() : 0);
            cs.setInt(8, cardDetails.getTheTurnCard() != null ? cardDetails.getTheTurnCard() : 0);
            cs.setInt(9, cardDetails.getTheRiverCard() != null ? cardDetails.getTheRiverCard() : 0);
            cs.setBigDecimal(10, cardDetails.getPlayerAbackOdds() != null ? cardDetails.getPlayerAbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(11, cardDetails.getPlayerAlayOdds() != null ? cardDetails.getPlayerAlayOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(12, cardDetails.getPlayerBbackOdds() != null ? cardDetails.getPlayerBbackOdds() : BigDecimal.ZERO);
            cs.setBigDecimal(13, cardDetails.getPlayerBlayOdds() != null ? cardDetails.getPlayerBlayOdds() : BigDecimal.ZERO);
            cs.setString(14, cardDetails.getWinner());
            return cs;
        }, (CallableStatement cs) -> this.extractCardDetails(cs.executeQuery()));
    }

    private boolean extractCardDetails(ResultSet rs) throws SQLException {
        boolean validateCard = false;
        while (rs.next()) {
            validateCard = (rs.getBoolean(CardsConstants.VALIDATE_CARD));
        }
        return validateCard;
    }

    @Override
    public List<CardsData> getCombinations(CardDetailsInputTO input) {

        return jdbcTemplate.execute(connection -> {

            CallableStatement cs = connection.prepareCall(PROC_GET_COMBINATIONS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            cs.setInt(1, input.getPlayerAFirstCard() != null ? input.getPlayerAFirstCard() : 0);
            cs.setInt(2, input.getPlayerBFirstCard() != null ? input.getPlayerBFirstCard() : 0);
            cs.setInt(3, input.getPlayerASecondCard() != null ? input.getPlayerASecondCard() : 0);
            cs.setInt(4, input.getPlayerBSecondCard() != null ? input.getPlayerBSecondCard() : 0);
            cs.setInt(5, input.getPlayerAThirdCard() != null ? input.getPlayerAThirdCard() : 0);
            cs.setInt(6, input.getPlayerBThirdCard() != null ? input.getPlayerBThirdCard() : 0);

            return cs;

        }, (CallableStatement cs) -> this.getCombinationsData(cs.executeQuery()));

    }

    @Override
    public List<CardsData> getAllTeenpatiViewOdds() {
        return jdbcTemplate.execute(connection -> connection.prepareCall(PROC_GET_TEENPATI_VIEW_ODDS,
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                (CallableStatement cs) -> getCombinationsData(cs.executeQuery()));
    }

    private List<CardsData> getCombinationsData(ResultSet rs) throws SQLException {

        CardsData cardsData;
        List<CardsData> cardsDataList = new ArrayList<>();
        List<String> playerAList;
        List<String> playerBList;
        while (rs.next()) {
            playerAList = new ArrayList<>();
            playerBList = new ArrayList<>();
            cardsData = new CardsData();
            playerAList.add(rs.getString("playera_card_id_1"));
            playerAList.add(rs.getString("playera_card_id_2"));
            playerAList.add(rs.getString("playera_card_id_3"));
            cardsData.setPlayerAList(playerAList);
            playerBList.add(rs.getString("playerb_card_id_1"));
            playerBList.add(rs.getString("playerb_card_id_2"));
            playerBList.add(rs.getString("playerb_card_id_3"));
            cardsData.setPlayerBList(playerBList);
            cardsData.setPlayerAbackOdds(rs.getBigDecimal("playera_back_odds"));
            cardsData.setPlayerALayOdds(rs.getBigDecimal("playera_lay_odds"));
            cardsData.setPlayerBBackOdds(rs.getBigDecimal("playerb_back_odds"));
            cardsData.setPlayerBLayOdds(rs.getBigDecimal("playerb_lay_odds"));
            cardsData.setId(BigInteger.valueOf(rs.getInt("id")));
            cardsDataList.add(cardsData);
        }
        return cardsDataList;
    }


}
