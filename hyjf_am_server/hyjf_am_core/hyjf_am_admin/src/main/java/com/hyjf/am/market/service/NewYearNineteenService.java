package com.hyjf.am.market.service;

import com.hyjf.am.vo.admin.NewYearActivityRewardVO;
import com.hyjf.am.vo.admin.NewYearActivityVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
public interface NewYearNineteenService {

    /**
     * 查询累计年化出借金额 条数
     * @param map
     * @return
     */
    int selectInvestCount(Map<String, Object> map);

    /**
     * 查询累计年化出借金额
     * @param map
     * @return
     */
    List<NewYearActivityVO> selectInvestList(Map<String, Object> map);


    int selectAwardCount(Map<String,Object> map);


    List<NewYearActivityRewardVO> selectAwardList(Map<String,Object> map);
    void updateAwardStatus(NewYearActivityRewardVO from);
}
