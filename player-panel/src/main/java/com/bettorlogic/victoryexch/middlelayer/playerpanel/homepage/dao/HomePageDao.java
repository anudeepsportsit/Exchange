package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dao;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.HighlightsSportTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto.*;

import java.util.List;

public interface HomePageDao {

    BannersTO getBannersDao(Integer pageId, String userToken);

    SportWrapperTO getUpcomingEvents(Integer sportId, Integer optionId, Integer timeInterval, String userToken);

    SportTO getSportsDao();

    HeadersTO getHeadersDao();

    List<UpcomingOptionsTO> getUpcomingOptions();

    List<SportDetailsTO> getUpcomingSportButtonDetails(Integer timeInterval);

    HighlightsSportTO retriveHighlights(int sportId, String userToken);

    HighlightsSportTO retriveInplay(int sportId, String userToken);

    UserMenuDetailsWrapperTO getMenuDetails();

    HighlightsSportsDetailsTO retriveHighlightsSports();

    HighlightsSportsDetailsTO retriveInplaySports();

    SearchDetailsTO getResults(String team);

    SubMenuDetailsWrapperTO retrieveNavigationBar(Integer sportId);

    ProfitLossWrapperTO retriveResults(ProfitLossInputDetailsTO profitLossInputDetails);

    List<Timezones> getTimezones();

    HighlightsSportTO retriveAdminInplay(int sportId, String userToken);
}