package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import java.io.Serializable;
import java.util.List;

public class SportTO implements Serializable {

    private List<SportCategoriesTO> categories;

    public List<SportCategoriesTO> getCategories() {
        return categories;
    }

    public void setCategories(List<SportCategoriesTO> categories) {
        this.categories = categories;
    }
}