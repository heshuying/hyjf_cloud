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
     * 出借记录报送接口
     */
    void investRecordSub();

    /**
     * 借款记录报送接口
     */
    void borrowRecordSub();
    /**
     * 提前还款接口报送
     */
    void advancedRepay();

}
