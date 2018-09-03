package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.trade.service.admin.hjhplan.HjhReInvestDebtService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhPlanCapitalCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;
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

    /**
     * 债权承接列表条数
     * @param request
     * @return
     */
    @Override
    public Integer getReinvestDebtCount(HjhReInvestDebtRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
//        param.put("date", request.getDate() + " 00:00:00");
        // 结束时间
//        param.put("datee", request.getDate() + " 23:59:59");
        param.put("date", request.getDate());
        if (StringUtils.isNotEmpty(request.getPlanNid())){
            param.put("planNid", request.getPlanNid());
        }

        return hjhReInvestDebtCustomizeMapper.queryReInvestDebtCount(param);
    }

    /**
     * 债权承接列表
     * @param request
     * @return
     */
    @Override
    public List<HjhReInvestDebtVO> getReinvestDebtList(HjhReInvestDebtRequest request) {

        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("date", request.getDate() + " 00:00:00");
        // 结束时间
        // TODO : 列表查询Sqlwhere条件 1=1 应替换为 ct.tender_type = 1
        //TODO : 为方便前端联调 屏蔽条件, 联调完应做删除
//        param.put("datee", request.getDate() + " 23:59:59");
        param.put("datee", "2018-09-30 23:59:59");
        if (StringUtils.isNotEmpty(request.getPlanNid())){
            param.put("planNid", request.getPlanNid());
        }

        List<HjhReInvestDebtVO> recordList = hjhReInvestDebtCustomizeMapper.queryReinvestDebtList(param);
        return recordList;
    }
}
