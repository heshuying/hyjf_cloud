package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.NewYearActivityCustomizeMapper;
import com.hyjf.am.market.service.NewYearNineteenService;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
@Service
public class NewYearNineteenServiceImpl implements NewYearNineteenService {

    @Autowired
    NewYearActivityCustomizeMapper newYearActivityCustomizeMapper;

    /**
     * 查询累计年化出借金额 条数
     * @param map
     * @return
     */
    @Override
    public int selectInvestCount(Map<String, Object> map){
        return newYearActivityCustomizeMapper.getNewYearActivityCount(map);
    }

    /**
     * 查询累计年化出借金额
     * @param map
     * @return
     */
    @Override
    public List<NewYearActivityVO> selectInvestList(Map<String, Object> map){
        return newYearActivityCustomizeMapper.getNewYearActivityList(map);
    }


    @Override
    public int selectAwardCount(Map<String,Object> map){
        return 0;
    }


    @Override
    public List<NewYearActivityRewardVO> selectAwardList(Map<String,Object> map){
        return null;
    }
    @Override
    public void updateAwardStatus(NewYearActivityRewardVO from){



    }
}
