package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.customize.admin.UserWithdrawRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.admin.WithdrawCustomize;

import java.util.List;
import java.util.Map;

public interface WithdrawCustomizeMapper {

    /**
     * 获取提现列表数量
     * @return
     */
    int selectWithdrawCount(WithdrawBeanRequest request);

    /**
     * 获取提现列表
     * @return
     */
    List<WithdrawCustomize> selectWithdrawList(WithdrawBeanRequest request);
    /**
     * 
     * 第三方获取提现列表
     * @author pcc
     * @param param
     * @return
     */
    List<UserWithdrawRecordCustomize> getThirdPartyUserWithdrawRecord(Map<String, Object> param);
}