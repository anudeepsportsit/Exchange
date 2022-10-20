package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.util.ArrayList;
import java.util.List;

public class HeadersTO {

    private List<HeaderDetailsTO> headersList;

    public List<HeaderDetailsTO> getHeadersList() {
        if (headersList == null) {
            return new ArrayList<>();
        }
        return headersList;
    }

    public void setHeadersList(List<HeaderDetailsTO> headersList) {
        if (headersList != null) {
            this.headersList = new ArrayList<>();
        }
        this.headersList = headersList;
    }
}