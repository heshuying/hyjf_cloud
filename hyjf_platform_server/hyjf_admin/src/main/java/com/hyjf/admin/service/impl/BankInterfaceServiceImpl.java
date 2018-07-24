package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankInterfaceClient;
import com.hyjf.admin.service.BankInterfaceService;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.resquest.admin.BankInterfaceRequest;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class BankInterfaceServiceImpl implements BankInterfaceService {
    @Autowired
    private BankInterfaceClient bankInterfaceClient;
    /**
     * 查询配置中心接口切换列表
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest){
        return bankInterfaceClient.bankInterfaceInit(adminRequest);
    }
    /**
     * 查询配置中心接口切换详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInfo(BankInterfaceRequest adminRequest){
        return bankInterfaceClient.bankInterfaceInfo(adminRequest);
    }
    /**
     * 修改 接口切换
     * @return
     */
    @Override
    public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req){
        return bankInterfaceClient.updateBankIntefaceAction(req);
    }

    /**
     * 删除快捷充值限额
     * @return
     */
    @Override
    public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req){
        return bankInterfaceClient.deleteBankInterfaceConfig(req);
    }
}
