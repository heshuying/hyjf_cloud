package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DebtCarInfo implements Serializable {
    private Integer id;

    /**
     * 品牌
     *
     * @mbggenerated
     */
    private String brand;

    /**
     * 型号
     *
     * @mbggenerated
     */
    private String model;

    /**
     * 车系
     *
     * @mbggenerated
     */
    private String carseries;

    /**
     * 车牌号
     *
     * @mbggenerated
     */
    private String number;

    /**
     * 颜色
     *
     * @mbggenerated
     */
    private String color;

    /**
     * 出厂年份
     *
     * @mbggenerated
     */
    private String year;

    /**
     * 产地
     *
     * @mbggenerated
     */
    private String place;

    /**
     * 排量
     *
     * @mbggenerated
     */
    private String volume;

    /**
     * 购买日期
     *
     * @mbggenerated
     */
    private Integer buytime;

    /**
     * 1有保险2无保险
     *
     * @mbggenerated
     */
    private Integer isSafe;

    /**
     * 购买价
     *
     * @mbggenerated
     */
    private BigDecimal price;

    /**
     * 评估价
     *
     * @mbggenerated
     */
    private BigDecimal toprice;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getCarseries() {
        return carseries;
    }

    public void setCarseries(String carseries) {
        this.carseries = carseries == null ? null : carseries.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume == null ? null : volume.trim();
    }

    public Integer getBuytime() {
        return buytime;
    }

    public void setBuytime(Integer buytime) {
        this.buytime = buytime;
    }

    public Integer getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(Integer isSafe) {
        this.isSafe = isSafe;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getToprice() {
        return toprice;
    }

    public void setToprice(BigDecimal toprice) {
        this.toprice = toprice;
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