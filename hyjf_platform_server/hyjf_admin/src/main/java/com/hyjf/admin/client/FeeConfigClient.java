package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
public interface FeeConfigClient {

    /**
     * 查询手续费配置列表
     * @param request
     * @return
     */
    public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request);

    /**
     * 查询银行列表
     * @param bank
     * @return
     */
    public List<BankConfigVO> getBankConfigList(BankConfigVO bank);
    /**
     * 获取手续费列表列表
     * @param bank
     * @return
     */
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank,int limitStart,int limitEnd);
    /**
     * 查询手续费配置详情页面
     * @param adminRequest
     * @return
     */
    public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest);

    /**
     * 编辑保存手续费配置
     * @return
     */
    public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req);

    /**
     * 修改手续费配置
     * @return
     */
    public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req);

    /**
     * 删除手续费配置
     * @return
     */
    public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req);
}
