package com.bettorlogic.victoryexch.middlelayer.adminpanel.myaccount.dto;

import com.bettorlogic.victoryexch.middlelayer.adminpanel.common.utils.AdminPanelConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserActivityWrapperTO {

    @JsonProperty(AdminPanelConstants.ACTIVITY_LOG)
    private List<UserActivityDetailsTO> activityLogList;

    public List<UserActivityDetailsTO> getActivityLogList() {
        return activityLogList;
    }

    public void setActivityLogList(List<UserActivityDetailsTO> activityLogList) {
        this.activityLogList = activityLogList;
    }
}
