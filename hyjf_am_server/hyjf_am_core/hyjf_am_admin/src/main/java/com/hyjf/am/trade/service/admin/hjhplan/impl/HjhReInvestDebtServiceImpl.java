package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDebtCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.HjhReInvestDebtService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
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
        param.put("dateStart", request.getDate() + " 00:00:00");
        // 结束时间
        param.put("dateEnd", request.getDate() + " 23:59:59");

        if (StringUtils.isNotEmpty(request.getPlanNid())){
            param.put("planNid", request.getPlanNid());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanOrderIdSrch())){
            param.put("assignPlanOrderIdSrch", request.getAssignPlanOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanNidSrch())){
            param.put("assignPlanNidSrch", request.getAssignPlanNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignOrderIdSrch())){
            param.put("assignOrderIdSrch", request.getAssignOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getUserNameSrch())){
            param.put("userNameSrch", request.getUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditUserNameSrch())){
            param.put("creditUserNameSrch", request.getCreditUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditNidSrch())){
            param.put("creditNidSrch", request.getCreditNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowNidSrch())){
            param.put("borrowNidSrch", request.getBorrowNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignTypeSrch())){
            param.put("assignTypeSrch", request.getAssignTypeSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())){
            param.put("borrowStyleSrch", request.getBorrowStyleSrch());
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
        param.put("dateStart", request.getDate() + " 00:00:00");
        // 结束时间
        param.put("dateEnd", request.getDate() + " 23:59:59");
        if (StringUtils.isNotEmpty(request.getPlanNid())){
            param.put("planNid", request.getPlanNid());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanOrderIdSrch())){
            param.put("assignPlanOrderIdSrch", request.getAssignPlanOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanNidSrch())){
            param.put("assignPlanNidSrch", request.getAssignPlanNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignOrderIdSrch())){
            param.put("assignOrderIdSrch", request.getAssignOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getUserNameSrch())){
            param.put("userNameSrch", request.getUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditUserNameSrch())){
            param.put("creditUserNameSrch", request.getCreditUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditNidSrch())){
            param.put("creditNidSrch", request.getCreditNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowNidSrch())){
            param.put("borrowNidSrch", request.getBorrowNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignTypeSrch())){
            param.put("assignTypeSrch", request.getAssignTypeSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())){
            param.put("borrowStyleSrch", request.getBorrowStyleSrch());
        }
        param.put("limitStart", request.getLimitStart());
        param.put("limitEnd", request.getLimitEnd());

        List<HjhReInvestDebtVO> recordList = hjhReInvestDebtCustomizeMapper.queryReinvestDebtList(param);
        return recordList;
    }

    @Override
    public HjhReInvestDebtCustomize queryReInvestDebtTotal(HjhReInvestDebtRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("dateStart", request.getDate() + " 00:00:00");
        // 结束时间
        param.put("dateEnd", request.getDate() + " 23:59:59");

        if (StringUtils.isNotEmpty(request.getPlanNid())){
            param.put("planNid", request.getPlanNid());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanOrderIdSrch())){
            param.put("assignPlanOrderIdSrch", request.getAssignPlanOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignPlanNidSrch())){
            param.put("assignPlanNidSrch", request.getAssignPlanNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignOrderIdSrch())){
            param.put("assignOrderIdSrch", request.getAssignOrderIdSrch());
        }
        if (StringUtils.isNotEmpty(request.getUserNameSrch())){
            param.put("userNameSrch", request.getUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditUserNameSrch())){
            param.put("creditUserNameSrch", request.getCreditUserNameSrch());
        }
        if (StringUtils.isNotEmpty(request.getCreditNidSrch())){
            param.put("creditNidSrch", request.getCreditNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowNidSrch())){
            param.put("borrowNidSrch", request.getBorrowNidSrch());
        }
        if (StringUtils.isNotEmpty(request.getAssignTypeSrch())){
            param.put("assignTypeSrch", request.getAssignTypeSrch());
        }
        if (StringUtils.isNotEmpty(request.getBorrowStyleSrch())){
            param.put("borrowStyleSrch", request.getBorrowStyleSrch());
        }
        HjhReInvestDebtCustomize total = hjhReInvestDebtCustomizeMapper.queryReInvestDebtTotal(param);
        return total;
    }
}
