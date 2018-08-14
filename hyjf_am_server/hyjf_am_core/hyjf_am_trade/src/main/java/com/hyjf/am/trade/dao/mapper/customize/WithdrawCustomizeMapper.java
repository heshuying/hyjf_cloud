package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.WithdrawBeanRequest;
import com.hyjf.am.trade.dao.model.customize.UserWithdrawRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.WithdrawCustomize;

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
    /**
     * todo 接口用到时再做实现
     * LEFT JOIN hyjf_param_name statusname ON statusname.name_class = 'WITHDRAW_STATUS' AND statusname.name_cd = haw.`status` AND statusname.del_flag = '0'
     */
}