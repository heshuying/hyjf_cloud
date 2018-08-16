package com.hyjf.am.trade.dao.mapper.customize;


import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

}