/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author fq
 * @version PlatformCountRequest, v0.1 2018/8/10 9:50
 */
public class PlatformCountRequest extends BasePage implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 平台
     */
    private String sourceName;

    /**
     * 访问数
     */
    private String accessNumber;

    /**
     * 注册数
     */
    private String registNumber;

    /**
     * 开户数
     */
    private String accountNumber;

    /**
     * 出借人数
     */
    private String tenderNumber;

    /**
     * 累计充值
     */
    private String rechargePrice;

    /**
     * 累计出借
     */
    private String tenderPrice;

    /**
     * 汇直投出借金额
     */
    private String hztTenderPrice;

    /**
     * 汇消费出借金额
     */
    private String hxfTenderPrice;

    /**
     * 汇天利出借金额
     */
    private String htlTenderPrice;
    /**
     * 汇天金出借金额
     */
    private String htjTenderPrice;
    /**
     * 汇计划出借金额
     */
    private String hjhTenderPrice;

    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * sourceName
     *
     * @return the sourceName
     */

    public String getSourceName() {
        return sourceName;
    }

    /**
     * @param sourceName
     *            the sourceName to set
     */

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * accessNumber
     *
     * @return the accessNumber
     */

    public String getAccessNumber() {
        return accessNumber;
    }

    /**
     * @param accessNumber
     *            the accessNumber to set
     */

    public void setAccessNumber(String accessNumber) {
        this.accessNumber = accessNumber;
    }

    /**
     * registNumber
     *
     * @return the registNumber
     */

    public String getRegistNumber() {
        return registNumber;
    }

    /**
     * @param registNumber
     *            the registNumber to set
     */

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }

    /**
     * accountNumber
     *
     * @return the accountNumber
     */

    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber
     *            the accountNumber to set
     */

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * tenderNumber
     *
     * @return the tenderNumber
     */

    public String getTenderNumber() {
        return tenderNumber;
    }

    /**
     * @param tenderNumber
     *            the tenderNumber to set
     */

    public void setTenderNumber(String tenderNumber) {
        this.tenderNumber = tenderNumber;
    }

    /**
     * rechargePrice
     *
     * @return the rechargePrice
     */

    public String getRechargePrice() {
        return rechargePrice;
    }

    /**
     * @param rechargePrice
     *            the rechargePrice to set
     */

    public void setRechargePrice(String rechargePrice) {
        this.rechargePrice = rechargePrice;
    }

    /**
     * tenderPrice
     *
     * @return the tenderPrice
     */

    public String getTenderPrice() {
        return tenderPrice;
    }

    /**
     * @param tenderPrice
     *            the tenderPrice to set
     */

    public void setTenderPrice(String tenderPrice) {
        this.tenderPrice = tenderPrice;
    }

    /**
     * hztTenderPrice
     *
     * @return the hztTenderPrice
     */

    public String getHztTenderPrice() {
        return hztTenderPrice;
    }

    /**
     * @param hztTenderPrice
     *            the hztTenderPrice to set
     */

    public void setHztTenderPrice(String hztTenderPrice) {
        this.hztTenderPrice = hztTenderPrice;
    }

    /**
     * hxfTenderPrice
     *
     * @return the hxfTenderPrice
     */

    public String getHxfTenderPrice() {
        return hxfTenderPrice;
    }

    /**
     * @param hxfTenderPrice
     *            the hxfTenderPrice to set
     */

    public void setHxfTenderPrice(String hxfTenderPrice) {
        this.hxfTenderPrice = hxfTenderPrice;
    }

    /**
     * htlTenderPrice
     *
     * @return the htlTenderPrice
     */

    public String getHtlTenderPrice() {
        return htlTenderPrice;
    }

    /**
     * @param htlTenderPrice
     *            the htlTenderPrice to set
     */

    public void setHtlTenderPrice(String htlTenderPrice) {
        this.htlTenderPrice = htlTenderPrice;
    }

    /**
     * timeStartSrch
     *
     * @return the timeStartSrch
     */

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    /**
     * @param timeStartSrch
     *            the timeStartSrch to set
     */

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    /**
     * timeEndSrch
     *
     * @return the timeEndSrch
     */

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    /**
     * @param timeEndSrch
     *            the timeEndSrch to set
     */

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    /**
     * limitStart
     *
     * @return the limitStart
     */

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart
     *            the limitStart to set
     */

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * limitEnd
     *
     * @return the limitEnd
     */

    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd
     *            the limitEnd to set
     */

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getHtjTenderPrice() {
        return htjTenderPrice;
    }

    public void setHtjTenderPrice(String htjTenderPrice) {
        this.htjTenderPrice = htjTenderPrice;
    }

    public String getHjhTenderPrice() {
        return hjhTenderPrice;
    }

    public void setHjhTenderPrice(String hjhTenderPrice) {
        this.hjhTenderPrice = hjhTenderPrice;
    }
}
