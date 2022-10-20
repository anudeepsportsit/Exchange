package com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service.impl;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dao.FancyMarketDao;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.dto.*;
import com.bettorlogic.victoryexch.middlelayer.adminpanel.fancymarkets.service.FancyMarketService;
import com.bettorlogic.victoryexch.middlelayer.common.utils.SportsBookOutputGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FancyMarketServiceImpl implements FancyMarketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SportsBookOutputGenerator.class);
    @Autowired
    private FancyMarketDao fancyMarketDao;

    @Override
    public FancyMarketListTO getFancyMarket(FancyMarketInputTO input) {
        return fancyMarketDao.getFancyMarket(input);
    }

    @Override
    public FancySportsListTO getSportList() {
        return fancyMarketDao.getSports();
    }

    @Override
    public FancyLeaguesTO getLeaguesList(Integer sportId) {
        return fancyMarketDao.getLeagues(sportId);
    }

    @Override
    public FancyEventsTO getEventsList(Integer leagueId) {
        return fancyMarketDao.getEvents(leagueId);
    }

    @Override
    public void addFancyMarket(List<FancyMarketsTO> fancyMarketsTO) {
        for (FancyMarketsTO markets : fancyMarketsTO) {
            fancyMarketDao.addMarkets(markets);
            LOGGER.debug("completed");
        }
    }

    @Override
    public void addAdminOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS) {
        AdminMatchOddsDetailsTO homeBack = new AdminMatchOddsDetailsTO();
        AdminMatchOddsDetailsTO homelay = new AdminMatchOddsDetailsTO();
        AdminMatchOddsDetailsTO drawBack = new AdminMatchOddsDetailsTO();
        AdminMatchOddsDetailsTO drawlay = new AdminMatchOddsDetailsTO();
        AdminMatchOddsDetailsTO awayBack = new AdminMatchOddsDetailsTO();
        AdminMatchOddsDetailsTO awaylay = new AdminMatchOddsDetailsTO();
        AdminMatchOddsTO adminMatchOddsTO = new AdminMatchOddsTO();
        StringBuilder sb = new StringBuilder();

        if (adminMatchOddsDetailsTOS.get(0).getSportId() == 1) {
            footballAdminOdds(adminMatchOddsDetailsTOS, homeBack, homelay, drawBack, drawlay, awayBack, awaylay);
            sb.append(parseMatchOdds(homeBack));
            sb.append(",");
            sb.append(parseMatchOdds(drawBack));
            sb.append(",");
            sb.append(parseMatchOdds(awayBack));
            sb.append(",");
            sb.append(parseMatchOdds(homelay));
            sb.append(",");
            sb.append(parseMatchOdds(drawlay));
            sb.append(",");
            sb.append(parseMatchOdds(awaylay));
            adminMatchOddsTO.setEventId(adminMatchOddsDetailsTOS.get(0).getEventId());
            adminMatchOddsTO.setSportId(adminMatchOddsDetailsTOS.get(0).getSportId());
            adminMatchOddsTO.setOddsList(sb);
            fancyMarketDao.addFootballMatchOdds(adminMatchOddsTO);
        }
        if (adminMatchOddsDetailsTOS.get(0).getSportId() == 2) {
            tennisAdminOdds(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay);
            fetchOtherMatchOdds(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay, adminMatchOddsTO, sb);
        }
        if (adminMatchOddsDetailsTOS.get(0).getSportId() == 4) {
            cricketAdminOdds(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay);
            fetchOtherMatchOdds(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay, adminMatchOddsTO, sb);
        }
    }

    private void fetchOtherMatchOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS, AdminMatchOddsDetailsTO homeBack, AdminMatchOddsDetailsTO homelay, AdminMatchOddsDetailsTO awayBack, AdminMatchOddsDetailsTO awaylay, AdminMatchOddsTO adminMatchOddsTO, StringBuilder sb) {
        sb.append(parseMatchOdds(homeBack));
        sb.append(",");
        sb.append(parseMatchOdds(awayBack));
        sb.append(",");
        sb.append(parseMatchOdds(homelay));
        sb.append(",");
        sb.append(parseMatchOdds(awaylay));
        adminMatchOddsTO.setEventId(adminMatchOddsDetailsTOS.get(0).getEventId());
        adminMatchOddsTO.setSportId(adminMatchOddsDetailsTOS.get(0).getSportId());
        adminMatchOddsTO.setOddsList(sb);
        fancyMarketDao.addOtherMatchOdds(adminMatchOddsTO);
    }

    @Override
    public List<AdminMatchOddsDetailsTO> getAdminMatchOdds(FancyMarketInputTO input) {
        if (input.getSportId() == 1) {
            return fancyMarketDao.getMatchOddsFootball(input);
        } else {
            return fancyMarketDao.getMatchOddsOther(input);
        }
    }

    @Override
    public String updateStatus(FancyMarketsSuspendDetailsTO suspendDetails) {
        return fancyMarketDao.updateFancyStatus(suspendDetails);
    }

    @Override
    public String updateSuspention(FancyMarketsSuspendDetailsTO suspendDetails) {
        return fancyMarketDao.updateFancySuspention(suspendDetails);
    }


    public String parseMatchOdds(AdminMatchOddsDetailsTO adminMatchOddsTO) {
        return "( " + adminMatchOddsTO.getEventId() + "," + adminMatchOddsTO.getOddDictionaryId() + "," + adminMatchOddsTO.getBackOdds() + "," + adminMatchOddsTO.getLayOdds() + "," + adminMatchOddsTO.getBackSize() + "," + adminMatchOddsTO.getLaySize() + "," + adminMatchOddsTO.getMinStake() +
                "," + adminMatchOddsTO.getMaxStake() + "," + adminMatchOddsTO.getMarketGroupId() + "," + adminMatchOddsTO.getProviderId() + ")";
    }

    private void cricketAdminOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS, AdminMatchOddsDetailsTO homeBack, AdminMatchOddsDetailsTO homelay,
                                  AdminMatchOddsDetailsTO awayBack, AdminMatchOddsDetailsTO awaylay) {

        homeBack.setOddDictionaryId(30);
        homelay.setOddDictionaryId(30);
        awayBack.setOddDictionaryId(31);
        awaylay.setOddDictionaryId(31);
        structurizeCode(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay, 1);
    }

    private void structurizeCode(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS, AdminMatchOddsDetailsTO homeBack, AdminMatchOddsDetailsTO homelay, AdminMatchOddsDetailsTO awayBack, AdminMatchOddsDetailsTO awaylay, int i) {
        homeBack.setEventId(adminMatchOddsDetailsTOS.get(0).getEventId());
        homeBack.setBackOdds(adminMatchOddsDetailsTOS.get(0).getBackOdds());
        homeBack.setLayOdds(null);
        homeBack.setBackSize(adminMatchOddsDetailsTOS.get(0).getBackSize());
        homeBack.setLaySize(null);
        homeBack.setMinStake(adminMatchOddsDetailsTOS.get(0).getMinStake());
        homeBack.setMaxStake(adminMatchOddsDetailsTOS.get(0).getMaxStake());
        homeBack.setMarketGroupId(1);
        homeBack.setProviderId(1);

        homelay.setEventId(adminMatchOddsDetailsTOS.get(0).getEventId());
        homelay.setBackOdds(null);
        homelay.setLayOdds(adminMatchOddsDetailsTOS.get(0).getLayOdds());
        homelay.setBackSize(null);
        homelay.setLaySize(adminMatchOddsDetailsTOS.get(0).getLaySize());
        homelay.setMinStake(adminMatchOddsDetailsTOS.get(0).getMinStake());
        homelay.setMaxStake(adminMatchOddsDetailsTOS.get(0).getMaxStake());
        homelay.setMarketGroupId(1);
        homelay.setProviderId(1);

        awayBack.setEventId(adminMatchOddsDetailsTOS.get(i).getEventId());
        awayBack.setBackOdds(adminMatchOddsDetailsTOS.get(i).getBackOdds());
        awayBack.setLayOdds(null);
        awayBack.setBackSize(adminMatchOddsDetailsTOS.get(i).getBackSize());
        awayBack.setLaySize(null);
        awayBack.setMinStake(adminMatchOddsDetailsTOS.get(i).getMinStake());
        awayBack.setMaxStake(adminMatchOddsDetailsTOS.get(i).getMaxStake());
        awayBack.setMarketGroupId(1);
        awayBack.setProviderId(1);

        awaylay.setEventId(adminMatchOddsDetailsTOS.get(i).getEventId());
        awaylay.setBackOdds(null);
        awaylay.setLayOdds(adminMatchOddsDetailsTOS.get(i).getLayOdds());
        awaylay.setBackSize(null);
        awaylay.setLaySize(adminMatchOddsDetailsTOS.get(i).getLaySize());
        awaylay.setMinStake(adminMatchOddsDetailsTOS.get(i).getMinStake());
        awaylay.setMaxStake(adminMatchOddsDetailsTOS.get(i).getMaxStake());
        awaylay.setMarketGroupId(1);
        awaylay.setProviderId(1);
    }

    private void tennisAdminOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS, AdminMatchOddsDetailsTO homeBack, AdminMatchOddsDetailsTO homelay, AdminMatchOddsDetailsTO awayBack,
                                 AdminMatchOddsDetailsTO awaylay) {

        homeBack.setOddDictionaryId(32);
        homelay.setOddDictionaryId(32);
        awayBack.setOddDictionaryId(33);
        awaylay.setOddDictionaryId(33);
        structurizeCode(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay, 1);
    }

    private void footballAdminOdds(List<AdminMatchOddsDetailsTO> adminMatchOddsDetailsTOS, AdminMatchOddsDetailsTO homeBack, AdminMatchOddsDetailsTO homelay, AdminMatchOddsDetailsTO drawBack,
                                   AdminMatchOddsDetailsTO drawlay, AdminMatchOddsDetailsTO awayBack, AdminMatchOddsDetailsTO awaylay) {
        homeBack.setOddDictionaryId(27);
        homelay.setOddDictionaryId(27);
        awayBack.setOddDictionaryId(29);
        awaylay.setOddDictionaryId(29);
        structurizeCode(adminMatchOddsDetailsTOS, homeBack, homelay, awayBack, awaylay, 2);

        drawBack.setOddDictionaryId(28);
        drawBack.setEventId(adminMatchOddsDetailsTOS.get(1).getEventId());
        drawBack.setBackOdds(adminMatchOddsDetailsTOS.get(1).getBackOdds());
        drawBack.setLayOdds(null);
        drawBack.setBackSize(adminMatchOddsDetailsTOS.get(1).getBackSize());
        drawBack.setLaySize(null);
        drawBack.setMinStake(adminMatchOddsDetailsTOS.get(1).getMinStake());
        drawBack.setMaxStake(adminMatchOddsDetailsTOS.get(1).getMaxStake());
        drawBack.setMarketGroupId(1);
        drawBack.setProviderId(1);
        drawlay.setOddDictionaryId(28);
        drawlay.setEventId(adminMatchOddsDetailsTOS.get(1).getEventId());
        drawlay.setBackOdds(null);
        drawlay.setLayOdds(adminMatchOddsDetailsTOS.get(1).getLayOdds());
        drawlay.setBackSize(null);
        drawlay.setLaySize(adminMatchOddsDetailsTOS.get(1).getLaySize());
        drawlay.setMinStake(adminMatchOddsDetailsTOS.get(1).getMinStake());
        drawlay.setMaxStake(adminMatchOddsDetailsTOS.get(1).getMaxStake());
        drawlay.setMarketGroupId(1);
        drawlay.setProviderId(1);
    }
}
