package com.hyjf.am.market.service;

import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
public interface NewYearNineteenService {

    int selectInvestCount(Map<String, Object> map);
    List<NewYearActivityVO> selectInvestList(Map<String, Object> map);


    int selectAwardCount(Map<String,Object> map);


    List<NewYearActivityRewardVO> selectAwardList(Map<String,Object> map);
    void updateAwardStatus(NewYearActivityRewardVO from);
}
