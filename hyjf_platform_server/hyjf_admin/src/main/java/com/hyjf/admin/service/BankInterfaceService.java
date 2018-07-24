package com.hyjf.admin.service;

import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.resquest.admin.BankInterfaceRequest;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;

/**
 * @author by xiehuili on 2018/7/19.
 */
public interface BankInterfaceService {
    /**
     * 查询配置中心接口切换列表
     * @param adminRequest
     * @return
     */
    public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest);
    /**
     * 查询配置中心接口切换详情页面
     * @param adminRequest
     * @return
     */
    public BankInterfaceResponse bankInterfaceInfo(BankInterfaceRequest adminRequest);
    /**
     * 修改 接口切换
     * @return
     */
    public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req);

    /**
     * 删除快捷充值限额
     * @return
     */
    public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req);

}
