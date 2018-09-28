/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service;

import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.cs.message.bean.ic.HjhPlanCapital;

import java.util.Date;
import java.util.List;

/**
 * 汇计划资本预估统计(每日)任务
 * @author liubin
 * @version HjhPlanCapitalService, v0.1 2018/8/1 22:03
 */
public interface HjhPlanCapitalService {

    /**
     * 删除汇计划资本按天统计及预估表的昨天今天及之后9天的记录
     * @param fromDate
     * @param toDate
     * @return
     */
    Boolean deleteHjhPlanCapital(Date fromDate, Date toDate);

    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    List<HjhPlanCapitalVO> getPlanCapitalForActList(Date date);

    /**
     * 插入更新汇计划资本按天统计及预估表
     * @param hjhPlanCapital
     * @return
     */
    Boolean updatePlanCapital(HjhPlanCapitalVO hjhPlanCapital);

    /**
     * 获取该期间的预估债转和复投金额
     * @param fromDate
     * @param toDate
     * @return
     */
    List<HjhPlanCapitalVO> getPlanCapitalForProformaList(Date fromDate, Date toDate);


    /**
     * 获取汇计划 - 计划资金 条数
     * @param request
     * @return
     * @Author : huanghui
     */
    Integer getPlanCapitalCount(HjhPlanCapitalRequest request);
    /**
     * 获取资金计划列表
     * @param request
     * @return
     * @Author : huanghui
     */
    List<HjhPlanCapital> getPlanCapitalList(HjhPlanCapitalRequest request);
}
