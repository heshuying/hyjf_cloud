package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.user.VipAuthVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 16:47
 * @Description: VipAuthClient
 */
public interface VipAuthClient {
    /**
     * @Author walter.limeng
     * @Description  根据vipID获取所有vipauth对象
     * @Date 16:50 2018/7/16
     * @Param vipId
     * @return
     */
    List<VipAuthVO> getVipAuthList(int vipId);
}
