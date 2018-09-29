/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.Impl;

import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;
import com.hyjf.cs.message.mongo.ic.HjhPlanCapitalDao;
import com.hyjf.cs.message.service.HjhPlanCapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 汇计划资本预估统计(每日)任务
 * @author liubin
 * @version HjhPlanCapitalServiceImpl, v0.1 2018/8/1 22:07
 */
@Service
public class HjhPlanCapitalServiceImpl implements HjhPlanCapitalService {
    @Autowired
    private HjhPlanCapitalDao hjhPlanCapitalDao;
    @Autowired
    private BaseClient baseClient;

    /**
     * 删除汇计划资本按天统计及预估表的昨天今天及之后9天的记录
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public Boolean deleteHjhPlanCapital(Date fromDate, Date toDate) {
         this.hjhPlanCapitalDao.deleteHjhPlanCapital(fromDate, toDate);
         return true;
    }

    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    @Override
    public List<HjhPlanCapitalVO> getPlanCapitalForActList(Date date) {
        HjhPlanCapitalResponse response = this.baseClient.getExe("http://AM-TRADE/am-trade/planCapitalController/getPlanCapitalForActList/" + date, HjhPlanCapitalResponse.class);
        return response.getResultList();
    }

    /**
     * 插入更新汇计划资本按天统计及预估表
     * @param hjhPlanCapital
     * @return
     */
    @Override
    public Boolean updatePlanCapital(HjhPlanCapitalVO hjhPlanCapital) {
        Boolean result = false;
        // 判断数据是否已存在
        List<HjhPlanCapitalVO> list = this.hjhPlanCapitalDao.selectHjhPlanCapitalList(hjhPlanCapital);
        if (list == null ) {
            throw new RuntimeException("取得汇计划资本按天统计及预估表的数据失败。");
        }
        if (list.size()>0){
            // 存在，更新记录（汇计划资本按天统计及预估表）
            result = this.hjhPlanCapitalDao.updateHjhPlanCapital(hjhPlanCapital);
        }else{
            // 不存在，插入记录（汇计划资本按天统计及预估表）
            result = this.hjhPlanCapitalDao.insertHjhPlanCapital(hjhPlanCapital);
        }
        return result;
    }

    /**
     * 获取该期间的预估债转和复投金额
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public List<HjhPlanCapitalVO> getPlanCapitalForProformaList(Date fromDate, Date toDate) {
        HjhPlanCapitalResponse response = this.baseClient.getExe("http://AM-TRADE/am-trade/planCapitalController/getPlanCapitalForProformaList/" + fromDate + "/" + toDate
                , HjhPlanCapitalResponse.class);
        return response.getResultList();
    }

    /**
     * 汇计划 - 资金计划count
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public Integer getPlanCapitalCount(HjhPlanCapitalRequest request) {
        return this.hjhPlanCapitalDao.getCount(request);
    }

    /**
     * 汇计划 - 获取资金计划类表
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public List<HjhPlanCapital> getPlanCapitalList(HjhPlanCapitalRequest request) {
        return this.hjhPlanCapitalDao.findAllList(request);
    }
}
