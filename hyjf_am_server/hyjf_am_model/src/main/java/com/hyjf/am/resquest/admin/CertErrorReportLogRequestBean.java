/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogBean, v0.1 2018/7/6 15:35
 */
public class CertErrorReportLogRequestBean extends BasePage implements Serializable {

    private List<CertLogVO> recordList;
    //id
    private Integer id;
    // 批次号
    private String logOrdId;
    // 请求类型
    private Integer infType;
    private String sendStartTimeStr;
    private String sendEndtTimeStr;
    // 发送状态
    private Integer sendStatus;
    // 对账状态
    private Integer queryStatus;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
    }

    public Integer getInfType() {
        return infType;
    }

    public void setInfType(Integer infType) {
        this.infType = infType;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public List<CertLogVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CertLogVO> recordList) {
        this.recordList = recordList;
    }

    public String getSendStartTimeStr() {
        return sendStartTimeStr;
    }

    public void setSendStartTimeStr(String sendStartTimeStr) {
        this.sendStartTimeStr = sendStartTimeStr;
    }

    public String getSendEndtTimeStr() {
        return sendEndtTimeStr;
    }

    public void setSendEndtTimeStr(String sendEndtTimeStr) {
        this.sendEndtTimeStr = sendEndtTimeStr;
    }

    public Integer getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(Integer queryStatus) {
        this.queryStatus = queryStatus;
    }
}
