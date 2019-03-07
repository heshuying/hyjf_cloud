package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import com.hyjf.am.trade.service.admin.hjhplan.HjhPlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 汇计划-资金计划
 * @Author : huanghui
 */
@Service
public class HjhPlanCapitalServiceImpl extends BaseServiceImpl implements HjhPlanCapitalService {


    /**
     * 复投原始标的 条数
     * @param request
     * @return
     */
    @Override
    public Integer queryReInvestDetailCount(HjhReInvestDetailRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("date", request.getDate() + " 00:00:00");
        // 结束时间
        param.put("datee", request.getDate() + " 23:59:59");
        param.put("planNid", request.getPlanNid());
        param.put("username", request.getUserNameSrch());
        param.put("accede_order_id", request.getAccedeOrderIdSrch());
        param.put("borrow_nid", request.getBorrowNidSrch());
        param.put("borrow_period", request.getLockPeriodSrch());
        param.put("invest_type", request.getInvestTypeSrch());
        param.put("name", request.getBorrowStyleSrch());

        return this.hjhReInvestDetailCustomizeMapper.queryReInvestDetailCount(param);
    }

    /**
     * 复投原始标的 列表
     * @param request
     * @return
     */
    @Override
    public List<HjhReInvestDetailVO> getReinvestInfo(HjhReInvestDetailRequest request) {
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("date", request.getDate() + " 00:00:00");
        param.put("datee", request.getDate() + " 23:59:59");
        param.put("planNid", request.getPlanNid());
        param.put("userNameSrch", request.getUserNameSrch());
        param.put("accedeOrderIdSrch", request.getAccedeOrderIdSrch());
        param.put("borrowNidSrch", request.getBorrowNidSrch());
        param.put("lockPeriodSrch", request.getLockPeriodSrch());
        param.put("investTypeSrch", request.getInvestTypeSrch());
        param.put("borrowStyleSrch", request.getBorrowStyleSrch());

        List<HjhReInvestDetailVO> recordList = hjhReInvestDetailCustomizeMapper.queryReInvestDetails(param);
        return recordList;
    }

    /**
     * 取合计值
     * @param request
     * @return
     */
    @Override
    public String queryReInvestDetailTotal(HjhReInvestDetailRequest request){
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("date", request.getDate() + " 00:00:00");
        // 结束时间
        param.put("datee", request.getDate() + " 23:59:59");
        param.put("planNid", request.getPlanNid());
        param.put("username", request.getUserNameSrch());
        param.put("accede_order_id", request.getAccedeOrderIdSrch());
        param.put("borrow_nid", request.getBorrowNidSrch());
        param.put("borrow_period", request.getLockPeriodSrch());
        param.put("invest_type", request.getInvestTypeSrch());
        param.put("name", request.getBorrowStyleSrch());

        String sumAccount = this.hjhReInvestDetailCustomizeMapper.queryReInvestDetailTotal(param) != null ? this.hjhReInvestDetailCustomizeMapper.queryReInvestDetailTotal(param) : "0.00";
        return sumAccount;
    }
}
