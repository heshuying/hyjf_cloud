/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 投资明细共用返回bean
 * @author wangjun
 * @version BorrowInvestResponseBean, v0.1 2018/7/10 17:15
 */
public class BorrowInvestResponseBean {
    @ApiModelProperty(value = "投资明细列表")
    private List<BorrowInvestCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    @ApiModelProperty(value = "操作平台")
    private Map<String,String> clientList;

    @ApiModelProperty(value = "还款方式")
    private List<BorrowStyleVO> borrowStyleList;

    @ApiModelProperty(value = "投资方式")
    private Map<String,String> investTypeList;

    @ApiModelProperty(value = "投资人债券明细")
    List<InvestorDebtBean> detailList;

    @ApiModelProperty(value = "PDF脱敏图片")
    List<String> imgList;

    @ApiModelProperty(value = "文件服务器")
    String fileDomainUrl;

    public List<BorrowInvestCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowInvestCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, String> getClientList() {
        return clientList;
    }

    public void setClientList(Map<String, String> clientList) {
        this.clientList = clientList;
    }

    public List<BorrowStyleVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<BorrowStyleVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public Map<String, String> getInvestTypeList() {
        return investTypeList;
    }

    public void setInvestTypeList(Map<String, String> investTypeList) {
        this.investTypeList = investTypeList;
    }

    public List<InvestorDebtBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InvestorDebtBean> detailList) {
        this.detailList = detailList;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }
}
