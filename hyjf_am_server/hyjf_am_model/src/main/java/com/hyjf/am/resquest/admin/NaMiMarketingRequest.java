package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lisheng
 * @version NaMiMarketingRequest, v0.1 2018/12/26 14:39
 */

public class NaMiMarketingRequest extends BasePage {
    private Integer id;

    /**
     * 账户户名 检索条件
     */
    private String username;
    /**
     * 姓名 检索条件
     */
    private String truename;

    /**
     * 邀请人账户名  检索条件
     */
    private String refferName;

    /**
     * 产品类型  检索条件
     */
    private String productType;
    /**
     * 产品编号  检索条件
     */
    private String productNo;

    /**
     * 加入时间 开始 检索条件
     */
    private String joinTimeStart;
    /**
     * 加入时间 结束 检索条件
     */
    private String joinTimeEnd;
    /**
     * 月份  检索条件
     */
    private String month;

    private String sort;

    private String col;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getRefferName() {
        return refferName;
    }

    public void setRefferName(String refferName) {
        this.refferName = refferName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getJoinTimeStart() {
        return joinTimeStart;
    }

    public void setJoinTimeStart(String joinTimeStart) {
        this.joinTimeStart = joinTimeStart;
    }

    public String getJoinTimeEnd() {
        return joinTimeEnd;
    }

    public void setJoinTimeEnd(String joinTimeEnd) {
        this.joinTimeEnd = joinTimeEnd;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }
}
