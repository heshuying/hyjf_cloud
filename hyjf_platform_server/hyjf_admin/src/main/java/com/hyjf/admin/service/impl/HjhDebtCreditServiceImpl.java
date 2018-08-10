package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:
 */
@Service
public class HjhDebtCreditServiceImpl extends BaseServiceImpl implements HjhDebtCreditService{

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 查询汇计划转让列表
     * @param request
     * @return
     */
    @Override
    public HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request) {

        HjhDebtCreditReponse reponse = amTradeClient.queryHjhDebtCreditList(request);
        return reponse;
    }

    /**
     * 组装汇计划转让列表显示状态名称
     * @param hjhDebtCreditVoList
     */
    @Override
    public void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList) {


        //转让状态
        Map<String, String> creditMap = CacheUtil.getParamNameMap(CustomConstants.HJH_DEBT_CREDIT_STATUS);
        Map<String, String> repayMap = CacheUtil.getParamNameMap(CustomConstants.HJH_DEBT_REPAY_STATUS);
        //汇计划债转还款状态
        for (HjhDebtCreditVo vo: hjhDebtCreditVoList
             ) {
            vo.setRepayStatusName(repayMap.get(vo.getRepayStatus()));
            vo.setCreditStatusName(creditMap.get(vo.getCreditStatus()));
        }
    }




}
