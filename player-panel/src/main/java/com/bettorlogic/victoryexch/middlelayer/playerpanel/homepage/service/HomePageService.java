package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.service;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.HighlightsSportTO;
import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.SportWrapperTO;
import com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto.*;

import java.util.List;
import java.util.Map;

public interface HomePageService {

    BannersTO retriveBanners(Integer pageId, String userToken);

    SportTO retriveLeftMenuSports();

    SportWrapperTO getUpcomingEvents(Integer sportId, Integer optionId, Integer timeInterval, String userToken) throws Exception;

    HeadersTO retriveHeaders();

    Map<String, Object> getUpcomingOptions(Integer timeInterval);

    HighlightsSportTO getHighlightsDetails(int sportId, String userToken);

    HighlightsSportTO getAdminInplayDetails(int sportId, String userToken);

    HighlightsSportTO getInplayDetails(int sportId, String userToken);

    UserMenuDetailsWrapperTO getMenuDetails();

    HighlightsSportsDetailsTO getHighlightsSports();

    HighlightsSportsDetailsTO getInplaySports();

    SearchDetailsTO getSearchResults(String name);

    SubMenuDetailsWrapperTO retrieveNavigationBar(Integer sportId);

    ProfitLossWrapperTO getResults(ProfitLossInputDetailsTO profitLossInputDetails);

    List<Timezones> getTimezones();


}
