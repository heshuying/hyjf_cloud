/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.admin.beans.vo.AdminBorrowInvestCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 出借明细共用返回bean
 * @author wangjun
 * @version BorrowInvestResponseBean, v0.1 2018/7/10 17:15
 */
public class BorrowInvestResponseBean {
    @ApiModelProperty(value = "出借明细列表")
    private List<AdminBorrowInvestCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    @ApiModelProperty(value = "操作平台")
    private List<DropDownVO> clientList;

    @ApiModelProperty(value = "还款方式")
    private List<DropDownVO> borrowStyleList;

    @ApiModelProperty(value = "出借方式")
    private List<DropDownVO> investTypeList;

    @ApiModelProperty(value = "资产来源下拉框")
    private List<DropDownVO> hjhInstConfigList;

    @ApiModelProperty(value = "产品类型下拉框")
    private List<DropDownVO> borrowProjectTypeList;
    @ApiModelProperty(value = "债权结束状态下拉框")
    private List<DropDownVO> creditendStateList;

    @ApiModelProperty(value = "出借人债券明细")
    List<InvestorDebtBean> detailList;

    @ApiModelProperty(value = "PDF脱敏图片(PDF脱敏图片预览接口)")
    List<String> imgList;

    @ApiModelProperty(value = "文件服务器(PDF脱敏图片预览接口)")
    String fileDomainUrl;

    public List<AdminBorrowInvestCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminBorrowInvestCustomizeVO> recordList) {
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

    public List<DropDownVO> getClientList() {
        return clientList;
    }

    public void setClientList(List<DropDownVO> clientList) {
        this.clientList = clientList;
    }

    public List<DropDownVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<DropDownVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public List<DropDownVO> getInvestTypeList() {
        return investTypeList;
    }

    public void setInvestTypeList(List<DropDownVO> investTypeList) {
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

    public List<DropDownVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<DropDownVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public List<DropDownVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<DropDownVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<DropDownVO> getCreditendStateList() {
        return creditendStateList;
    }

    public void setCreditendStateList(List<DropDownVO> creditendStateList) {
        this.creditendStateList = creditendStateList;
    }
}
