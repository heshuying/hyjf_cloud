package com.hyjf.admin.service;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 * *  银行配置 手续费配置
 */
public interface FeeConfigService {
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
    /**
     * 校验
     * @return
     */
    public AdminFeeConfigResponse validateBeforeAction(AdminFeeConfigRequest request) ;
}
