package com.bettorlogic.victoryexch.middlelayer.playerpanel.homepage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BannerDetailsTO implements Serializable {
    private Integer id;
    private String name;
    private String text;

    @JsonProperty("buttonText")
    private String buttonText;

    @JsonProperty("isButton")
    private boolean isButton;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("linkUrl")
    private String linkUrl;
    @JsonProperty("buttonType")
    private Integer buttonType;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getButtonType() {
        return buttonType;
    }

    public void setButtonType(Integer buttonType) {
        this.buttonType = buttonType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
