package com.hyjf.wbs.trade.dao.mapper.customize;

import com.hyjf.wbs.qvo.FundDetailsQO;
import com.hyjf.wbs.qvo.FundDetailsVO;
import com.hyjf.wbs.trade.dao.model.customize.FundDetailsCustomize;

import java.util.List;

/**
 * @author cui
 * @version FundDetailsCustomizeMapper, v0.1 2019/7/1 11:28
 */
public interface FundDetailsCustomizeMapper {

    /**
     * 查询提现资金明细
     * @param qo
     * @return
     */
    List<FundDetailsVO> queryWithdrawFundDetails(FundDetailsCustomize qo);

    /**
     * 查询充值资金明细
     * @param qo
     * @return
     */
    List<FundDetailsVO> queryRechargeFundDetails(FundDetailsCustomize qo);

}
