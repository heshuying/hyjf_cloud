package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.trade.service.admin.DayCreditDetailService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情 ServiceImpl
 * @Author : huanghui
 */
@Service
public class DayCreditDetailServiceImpl extends BaseServiceImpl implements DayCreditDetailService {

    @Override
    public List<DayCreditDetailVO> selectDebtCreditList(DayCreditDetailRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();

        if (StringUtils.isNotEmpty(request.getPlanNidSrch())){
            param.put("planNid", request.getPlanNidSrch());
        }

        List<DayCreditDetailVO> recordList = hjhDayCreditDetailCustomizeMapper.selectDebtCreditList(param);
        return recordList;
    }
}
