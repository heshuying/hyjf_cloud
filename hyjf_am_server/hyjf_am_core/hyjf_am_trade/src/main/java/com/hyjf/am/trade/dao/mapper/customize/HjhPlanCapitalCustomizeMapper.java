package com.hyjf.am.trade.dao.mapper.customize;


import com.hyjf.am.vo.trade.HjhPlanCapitalActualVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 */
public interface HjhPlanCapitalCustomizeMapper {

    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    List<HjhPlanCapitalVO> selectPlanCapitalForActList(Date date);

    /**
     * 获取该期间的预估债转和复投金额
     * @param fromDate
     * @param toDate
     * @return
     */
    List<HjhPlanCapitalVO> selectPlanCapitalForProformaList(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    /**
     * 获取该期间的实际资金计划
     * @param date
     * @return
     */
    List<HjhPlanCapitalActualVO> getPlanCapitalActualformaList(@Param("date") String date);
}