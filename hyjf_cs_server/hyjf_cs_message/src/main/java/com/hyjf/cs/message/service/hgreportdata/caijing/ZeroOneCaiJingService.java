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
     * 借款记录接口报送
     */
    void investRecordSub();

    /**
     * 提前还款接口报送
     */
    void advancedRepay();

}
