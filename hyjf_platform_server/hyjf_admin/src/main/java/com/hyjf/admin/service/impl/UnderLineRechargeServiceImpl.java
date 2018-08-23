package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.UnderLineRechargeRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.UnderLineRechargeService;
import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 线下充值类型实现
 * @Author : huanghui
 */
@Service
public class UnderLineRechargeServiceImpl extends BaseServiceImpl implements UnderLineRechargeService {

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 获取列表
     * @param requestBean
     * @return
     */
    @Override
    public UnderLineRechargeResponse selectUnderLineList(UnderLineRechargeRequestBean requestBean) {
        return amTradeClient.selectUnderLineList(requestBean);
    }

    /**
     * 写入数据库
     * @param requestBean
     * @return
     */
    @Override
    public UnderLineRechargeResponse insertRecord(UnderLineRechargeRequestBean requestBean) {
        return amTradeClient.insterUnderRechargeCode(requestBean);
    }

    /**
     * 查询指定 Code
     * @param code
     * @return
     */
    @Override
    public boolean checkValidate(String code) {
        return amTradeClient.getUnderLineRecharge(code);
    }

    /**
     * 更新
     * @param requestBean
     * @return
     */
    @Override
    public boolean updateUnderLineRecharge(UnderLineRechargeRequestBean requestBean) {
        return amTradeClient.updateUnderLineRecharge(requestBean);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteUnderLineRecharge(Integer id) {
        return amTradeClient.deleteUnderLineRecharge(id);
    }
}
