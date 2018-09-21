package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;

import java.util.List;

/**
 * @version ApiRepayListCustomizeMapper, v0.1 2018/9/1 15:20
 * @Author: Zha Daojian
 */
public interface ApiRepayListCustomizeMapper {

    /**
     * 获取投资详细信息
     *
     * @param {instCode：机构编号（必填）,startTime:开始时间（必填），endTime:结束时间（必填），account：电子账号（选填），borrowNid：标的编号}
     * @return
     */
    List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request);
}
