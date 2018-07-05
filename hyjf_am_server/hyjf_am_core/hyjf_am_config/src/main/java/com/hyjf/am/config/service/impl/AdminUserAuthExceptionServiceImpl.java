/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.BankReturnCodeConfigMapper;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.service.AdminUserAuthExceptionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminUserAuthExceptionServiceImpl, v0.1 2018/7/2 17:01
 */
@Service
public class AdminUserAuthExceptionServiceImpl implements AdminUserAuthExceptionService {
    @Autowired
    private BankReturnCodeConfigMapper bankReturnCodeConfigMapper;

    /**
     * 根据银行返回的错误码查询错误信息
     * @auth sunpeikai
     * @param retCode 银行返回的错误码
     * @return retMsg 错误码对应的错误信息
     */
    @Override
    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
            example.createCriteria().andRetCodeEqualTo(retCode);
            List<BankReturnCodeConfig> retCodes = this.bankReturnCodeConfigMapper.selectByExample(example);
            if (retCodes != null && retCodes.size() == 1) {
                String retMsg = retCodes.get(0).getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }
}
