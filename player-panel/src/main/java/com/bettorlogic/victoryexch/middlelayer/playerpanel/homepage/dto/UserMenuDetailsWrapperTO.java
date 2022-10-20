package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.io.Serializable;
import java.util.List;

public class UserMenuDetailsWrapperTO implements Serializable {
    private List<UserMenuDetailsTO> userMenu;

    public List<UserMenuDetailsTO> getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(List<UserMenuDetailsTO> userMenu) {
        this.userMenu = userMenu;
    }
}
