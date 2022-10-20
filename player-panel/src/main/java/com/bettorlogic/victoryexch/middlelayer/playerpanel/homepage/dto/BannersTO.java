package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.io.Serializable;
import java.util.List;


public class BannersTO implements Serializable {
    private List<BannerDetailsTO> bannersList;

    public List<BannerDetailsTO> getBannersList() {
        return bannersList;
    }

    public void setBannersList(List<BannerDetailsTO> bannersList) {
        this.bannersList = bannersList;
    }
}
