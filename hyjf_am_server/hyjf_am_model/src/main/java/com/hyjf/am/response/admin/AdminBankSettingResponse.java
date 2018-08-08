/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.paginator.Paginator;

/**
 * @author dangzw
 * @version AdminBankSettingResponse, v0.1 2018/7/24 22:44
 */
public class AdminBankSettingResponse extends Response<JxBankConfigVO> {

    private int recordTotal;

    private String fileDomainUrl;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    private AdminBankSettingRequest banksettingForm;

    public AdminBankSettingRequest getBanksettingForm() {
        return banksettingForm;
    }

    public void setBanksettingForm(AdminBankSettingRequest banksettingForm) {
        this.banksettingForm = banksettingForm;
    }

    public String getFileDomainUrl() {
        return fileDomainUrl;
    }

    public void setFileDomainUrl(String fileDomainUrl) {
        this.fileDomainUrl = fileDomainUrl;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
