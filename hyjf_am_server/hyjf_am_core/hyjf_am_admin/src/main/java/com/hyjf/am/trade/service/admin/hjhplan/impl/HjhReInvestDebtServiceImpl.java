package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.trade.service.admin.hjhplan.HjhReInvestDebtService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
@Service
public class HjhReInvestDebtServiceImpl extends BaseServiceImpl implements HjhReInvestDebtService {

    @Override
    public List<HjhPlanCapitalCustomizeVO> getReinvestDebtList(HjhReInvestDebtRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();

        if (StringUtils.isNotEmpty(request.getPlanNidSrch())){
            param.put("planNid", request.getPlanNidSrch());
        }

        List<HjhPlanCapitalCustomizeVO> recordList = hjhReInvestDebtCustomizeMapper.queryReinvestDebtList(param);
        return recordList;
    }
}
