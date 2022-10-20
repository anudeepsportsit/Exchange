package com.bettorlogic.victoryexch.middlelayer.common.utils;

import com.bettorlogic.victoryexch.middlelayer.common.dto.eventview.OddDetailsTO;

import java.util.Comparator;

public class OddDetailsSizeComparator implements Comparator<OddDetailsTO> {
    @Override
    public int compare(OddDetailsTO oddDetails1, OddDetailsTO oddDetails2) {
        if (oddDetails1.getSize() > oddDetails2.getSize()) {
            return -1;
        } else if (oddDetails1.getSize() < oddDetails2.getSize()) {
            return 1;
        }
        return 0;
    }
}
