package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.user.VipAuthVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 16:58
 * @Description: VipAuthService
 */
public interface VipAuthService extends BaseService {
    /**
     * @Author walter.limeng
     * @Description  根据vipID获取所有vipauth对象
     * @Date 17:03 2018/7/16
     * @Param vipId
     * @return
     */
    List<VipAuthVO> getVipAuthList(String vipId);
}
