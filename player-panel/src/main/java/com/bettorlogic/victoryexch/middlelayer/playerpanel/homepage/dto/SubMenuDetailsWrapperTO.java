package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.util.ArrayList;
import java.util.List;

public class SubMenuDetailsWrapperTO {

    private List<HeaderDetailsTO> subMenuDetailsTO = new ArrayList<>();

    public List<HeaderDetailsTO> getSubMenuDetailsTO() {
        return subMenuDetailsTO;
    }

    public void setSubMenuDetailsTO(List<HeaderDetailsTO> subMenuDetailsTO) {
        this.subMenuDetailsTO = subMenuDetailsTO;
    }
}

