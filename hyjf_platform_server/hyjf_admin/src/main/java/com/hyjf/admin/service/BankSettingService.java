/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dangzw
 * @version BankSettingService, v0.1 2018/7/24 22:51
 */
public interface BankSettingService {

    /**
     *（条件）列表查询 江西银行 银行卡配置
     * @param request
     * @return
     */
    AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request);

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    AdminBankSettingResponse getRecord(AdminBankSettingRequest request);

    /**
     * （条件）数据查询 江西银行 银行卡配置
     * @param bank
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<JxBankConfigVO> getRecordList(JxBankConfigVO bank,int limitStart,int limitEnd);

    /**
     * 添加 江西银行 银行卡配置
     * @param request
     * @return
     */
    AdminBankSettingResponse insertRecord(AdminBankSettingRequest request);

    /**
     * 修改 江西银行 银行卡配置
     * @param request
     * @return
     */
    AdminBankSettingResponse updateRecord(AdminBankSettingRequest request);

    /**
     * 删除 江西银行 银行卡配置
     * @param request
     * @return
     */
    AdminBankSettingResponse deleteRecord(AdminBankSettingRequest request);

}
