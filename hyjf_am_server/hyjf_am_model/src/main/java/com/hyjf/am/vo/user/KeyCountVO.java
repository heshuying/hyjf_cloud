package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class KeyCountVO extends BaseVO implements Serializable {


    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 渠道
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
     * 投资人数
     */
    private String tenderNumber;

    /**
     * 关键字
     */
    private String keyWord;



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
     * keyWord
     *
     * @return the keyWord
     */

    public String getKeyWord() {
        return keyWord;
    }

    /**
     * @param keyWord
     *            the keyWord to set
     */

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }



}