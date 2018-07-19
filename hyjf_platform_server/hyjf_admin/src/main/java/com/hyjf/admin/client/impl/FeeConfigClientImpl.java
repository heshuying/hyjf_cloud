package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.FeeConfigClient;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class FeeConfigClientImpl implements FeeConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询手续费配置列表
     * @param request
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeConfig/list",
                request, AdminFeeConfigResponse.class);
    }
    /**
     * 查询银行列表
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigList(BankConfigVO bank){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/config/getBankConfigListByStatus",
                bank, List.class);
    }
    /**
     * 获取手续费列表列表
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank,int limitStart,int limitEnd){
        //查詢所有
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/selectBankConfigByBankName",
                bank.getName(), List.class);
    }
    /**
     * 查询手续费配置详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/info",
                adminRequest, AdminFeeConfigResponse.class);
    }
    /**
     * 编辑保存手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/insert",
                req, AdminFeeConfigResponse.class);
    }
    /**
     * 修改手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/update",
                req, AdminFeeConfigResponse.class);
    }
    /**
     * 删除手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req){
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/delete",
                req, AdminFeeConfigResponse.class);
    }

}
