package com.bettorlogic.victoryexch.middlelayer.common.dto;

import java.util.List;

public class SubHeaderDetailsWrapperTO {
    private List<SubHeaderDetailsTO> subHeadersList;

    public List<SubHeaderDetailsTO> getSubHeadersList() {
        return subHeadersList;
    }

    public void setSubHeadersList(List<SubHeaderDetailsTO> subHeadersList) {
        this.subHeadersList = subHeadersList;
    }
}