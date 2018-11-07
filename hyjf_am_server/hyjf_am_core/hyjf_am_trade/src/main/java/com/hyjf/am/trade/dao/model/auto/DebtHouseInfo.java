package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class DebtHouseInfo implements Serializable {
    private Integer id;

    /**
     * 房产类型
     *
     * @mbggenerated
     */
    private String housesType;

    /**
     * 房产位置
     *
     * @mbggenerated
     */
    private String housesLocation;

    /**
     * 建筑面积
     *
     * @mbggenerated
     */
    private String housesArea;

    /**
     * 市值
     *
     * @mbggenerated
     */
    private String housesPrice;

    /**
     * 抵押价值（元）
     *
     * @mbggenerated
     */
    private String housesToprice;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 借款预编号
     *
     * @mbggenerated
     */
    private Integer borrowPreNid;

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

    public Integer getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(Integer borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
    }
}