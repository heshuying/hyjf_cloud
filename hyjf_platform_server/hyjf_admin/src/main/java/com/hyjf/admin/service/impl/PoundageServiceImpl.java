/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.PoundageListRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: PoundageServiceImpl, v0.1 2018/9/3 15:00
 */
@Service
public class PoundageServiceImpl extends BaseAdminServiceImpl implements PoundageService {

    @Autowired
    private SystemConfig systemConfig;
    /**
     * 查询手续费分账count
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getPoundageCount(PoundageListRequest request) {
        return amAdminClient.getPoundageCount(request);
    }

    /**
     * 查询手续费分账list
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<PoundageCustomizeVO> searchPoundageList(PoundageListRequest request) {
        return amAdminClient.searchPoundageList(request);
    }

    /**
     * 获取手续费分账数额总计
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageSum(PoundageListRequest request) {
        return amAdminClient.getPoundageSum(request);
    }

    /**
     * 根据id查询手续费分账信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public PoundageCustomizeVO getPoundageById(Integer loginUserId,Integer id) {
        PoundageCustomizeVO result = amAdminClient.getPoundageById(id);
        if(result != null){
            // 转出方用户电子账户号
            result.setAccountId(systemConfig.BANK_MERS_ACCOUNT);
            // 余额
            BigDecimal balance = this.getBankBalance(loginUserId,systemConfig.BANK_MERS_ACCOUNT);
            if (balance != null) {
                result.setBalance(balance.toString());
            }
        }

        return result;
    }

    /**
     * 审核-更新poundage表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean updatePoundage(PoundageCustomizeVO poundageCustomizeVO) {
        return amAdminClient.updatePoundage(poundageCustomizeVO)>0;
    }

    /**
     * 检查参数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public void checkParams(PoundageCustomizeVO poundageCustomizeVO) {
        CheckUtil.check(new BigDecimal(poundageCustomizeVO.getBalance()).compareTo(poundageCustomizeVO.getAmount()) < 0,MsgEnum.ERR_AMT_NO_MONEY);
        String password = systemConfig.SUB_COMMISSION_PASSWORD;
        CheckUtil.check(!password.equals(MD5Utils.MD5(poundageCustomizeVO.getPassword())),MsgEnum.ERR_TRADE_PASSWORD);
    }

    /**
     * 佣金分账
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject poundageTransfer(PoundageCustomizeVO poundageCustomizeVO) {
        return null;
    }
}
