package com.hyjf.cs.message.service.hgreportdata.caijing;

import com.hyjf.cs.message.bean.hgreportdata.caijing.ZeroOneDataEntity;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/6/3 10:32
 * @Version 1.0
 */
public interface ZeroOneCaiJingService {

    /**
     * 投资记录报送接口
     */
    void investRecordSub(String startDate ,String endDate);

    /**
     * 借款记录报送接口
     */
    void borrowRecordSub(String startDate ,String endDate);
    /**
     * 提前还款接口报送
     */
    void advancedRepay(String startDate ,String endDate);

}
