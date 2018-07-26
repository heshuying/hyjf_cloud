/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dangzw
 * @version BankSettingClient, v0.1 2018/7/24 22:54
 */
public interface BankSettingClient {

    /**
     *（条件）列表查询
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
     * （条件）数据查询
     * @param bank
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<JxBankConfigVO> getRecordList(JxBankConfigVO bank, int limitStart, int limitEnd);

    /**
     * 添加
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

    /**
     * 江西银行 资料上传
     * @param request
     * @return
     */
    AdminBankSettingResponse uploadFile(HttpServletRequest request);
}
