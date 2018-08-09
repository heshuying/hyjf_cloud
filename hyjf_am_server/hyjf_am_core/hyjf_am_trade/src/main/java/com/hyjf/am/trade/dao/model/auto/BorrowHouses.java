package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowHouses implements Serializable {
    private Integer id;

    private String housesType;

    private String housesLocation;

    private String housesArea;

    private String housesPrice;

    private String housesToprice;

    private String borrowNid;

    private String borrowPreNid;

    private String housesBelong;

    private Integer housesCnt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHousesType() {
        return housesType;
    }

    public void setHousesType(String housesType) {
        this.housesType = housesType == null ? null : housesType.trim();
    }

    public String getHousesLocation() {
        return housesLocation;
    }

    public void setHousesLocation(String housesLocation) {
        this.housesLocation = housesLocation == null ? null : housesLocation.trim();
    }

    public String getHousesArea() {
        return housesArea;
    }

    public void setHousesArea(String housesArea) {
        this.housesArea = housesArea == null ? null : housesArea.trim();
    }

    public String getHousesPrice() {
        return housesPrice;
    }

    public void setHousesPrice(String housesPrice) {
        this.housesPrice = housesPrice == null ? null : housesPrice.trim();
    }

    public String getHousesToprice() {
        return housesToprice;
    }

    public void setHousesToprice(String housesToprice) {
        this.housesToprice = housesToprice == null ? null : housesToprice.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(String borrowPreNid) {
        this.borrowPreNid = borrowPreNid == null ? null : borrowPreNid.trim();
    }

    public String getHousesBelong() {
        return housesBelong;
    }

    public void setHousesBelong(String housesBelong) {
        this.housesBelong = housesBelong == null ? null : housesBelong.trim();
    }

    public Integer getHousesCnt() {
        return housesCnt;
    }

    public void setHousesCnt(Integer housesCnt) {
        this.housesCnt = housesCnt;
    }
}