package com.hyjf.am.trade.service.admin.hjhplan.impl;

import com.hyjf.am.trade.dao.model.customize.HjhReInvestDetailCustomize;
import com.hyjf.am.trade.service.admin.hjhplan.HjhPlanCapitalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDetailVO;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     * @param data
     * @param planNid
     * @return
     */
    @Override
    public Integer queryReInvestDetailCount(String data, String planNid) {
        Map<String, Object> param = new HashMap<String, Object>();

        //起始时间
        param.put("date", data + " 00:00:00");
        // 结束时间
        param.put("datee", data + " 23:59:59");
        param.put("planNid", planNid);

        return this.hjhReInvestDetailCustomizeMapper.queryReInvestDetailCount(param);
    }

    /**
     * 复投原始标的 列表
     * @param data
     * @param planNid
     * @return
     */
    @Override
    public List<HjhReInvestDetailVO> getReinvestInfo(String data, String planNid) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("date", data + " 00:00:00");
        param.put("datee", data + " 23:59:59");
        param.put("planNid", planNid);

        List<HjhReInvestDetailVO> recordList = hjhReInvestDetailCustomizeMapper.queryReInvestDetails(param);
        return recordList;
    }
}
