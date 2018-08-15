/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author fq
 * @version SmsCodeCustomizeVO, v0.1 2018/8/15 10:37
 */
public class SmsCodeCustomizeVO extends BaseVO {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 用户手机号码，以英文逗号间隔
     */
    private String user_phones;

    /**
     * 短信内容
     */
    private String message;

    /**
     * 是否开户 0 未开 1 已开 3 所有用户
     */
    private Integer open_account;

    /**
     * 注册时间日期段---开始时间
     */
    private Integer re_time_begin;

    /**
     * 注册时间日期段---结束时间
     */
    private Integer re_time_end;

    /**
     * 投资日期段开始时间
     */
    private Integer add_time_begin;

    /**
     * 投资日期段结束时间
     */
    private Integer add_time_end;

    /**
     * 累计投资金额
     */
    private BigDecimal add_money_count;

    /**
     * 用户ID
     */
    private Integer user_id;

    /**
     * @return the user_id
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * @param user_id
     *            the user_id to set
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the user_phones
     */
    public String getUser_phones() {
        return user_phones;
    }

    /**
     * @param user_phones
     *            the user_phones to set
     */
    public void setUser_phones(String user_phones) {
        this.user_phones = user_phones;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the open_account
     */
    public Integer getOpen_account() {
        return open_account;
    }

    /**
     * @param open_account
     *            the open_account to set
     */
    public void setOpen_account(Integer open_account) {
        this.open_account = open_account;
    }

    /**
     * @return the re_time_begin
     */
    public Integer getRe_time_begin() {
        return re_time_begin;
    }

    /**
     * @param re_time_begin
     *            the re_time_begin to set
     */
    public void setRe_time_begin(Integer re_time_begin) {
        this.re_time_begin = re_time_begin;
    }

    /**
     * @return the re_time_end
     */
    public Integer getRe_time_end() {
        return re_time_end;
    }

    /**
     * @param re_time_end
     *            the re_time_end to set
     */
    public void setRe_time_end(Integer re_time_end) {
        this.re_time_end = re_time_end;
    }

    /**
     * @return the add_time_begin
     */
    public Integer getAdd_time_begin() {
        return add_time_begin;
    }

    /**
     * @param add_time_begin
     *            the add_time_begin to set
     */
    public void setAdd_time_begin(Integer add_time_begin) {
        this.add_time_begin = add_time_begin;
    }

    /**
     * @return the add_time_end
     */
    public Integer getAdd_time_end() {
        return add_time_end;
    }

    /**
     * @param add_time_end
     *            the add_time_end to set
     */
    public void setAdd_time_end(Integer add_time_end) {
        this.add_time_end = add_time_end;
    }

    /**
     * @return the add_money_count
     */
    public BigDecimal getAdd_money_count() {
        return add_money_count;
    }

    /**
     * @param add_money_count
     *            the add_money_count to set
     */
    public void setAdd_money_count(BigDecimal add_money_count) {
        this.add_money_count = add_money_count;
    }
}
