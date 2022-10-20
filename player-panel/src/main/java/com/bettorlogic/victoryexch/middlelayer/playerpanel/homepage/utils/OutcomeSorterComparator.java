package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.utils;

import com.bettorlogic.victoryexch.middlelayer.common.dto.sporthierarchy.OutcomeDetailsTO;

import java.util.Comparator;

public class OutcomeSorterComparator implements Comparator<OutcomeDetailsTO> {
    @Override
    public int compare(OutcomeDetailsTO outcomeDetails1, OutcomeDetailsTO outcomeDetails2) {
        if (outcomeDetails1.getOutcomeOrder() < outcomeDetails2.getOutcomeOrder()) {
            return -1;
        } else if (outcomeDetails1.getOutcomeOrder() > outcomeDetails2.getOutcomeOrder()) {
            return 1;
        }
        return 0;
    }
}