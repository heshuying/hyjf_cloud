package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.customize.IdCardCustomize;
import com.hyjf.am.vo.trade.OperationReportJobVO;

import java.util.List;

/**
 * @Auther: walter.tanyy
 * @Date: 2018/7/31 16:37
 * @Description: IdCardService
 */
public interface IdCardService {


    /**
     * @Author walter.tanyy
     * @user walter.tanyy
     * @Description  根据条件查询
     * @Date 2018/7/31 16:37
     * @Param idCardCustomize
     * @return
     */
    IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize);

    List<IdCardCustomize> getIdCardList();

    /**
     * 按照省份统计投资人的分布  上个月的最后一天
     *
     * @param bms
     */
    List<OperationReportJobVO> getTenderCityGroupBy(List<OperationReportJobVO> bms);

}
