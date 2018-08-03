package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.mapper.customize.VipManageCustomizeMapper;
import com.hyjf.am.user.service.front.user.VipAuthService;
import com.hyjf.am.vo.user.VipAuthVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 16:58
 * @Description: VipAuthServiceImpl
 */
@Service
public class VipAuthServiceImpl implements VipAuthService {

    @Resource
    private VipManageCustomizeMapper vipManageCustomizeMapper;

    @Override
    public List<VipAuthVO> getVipAuthList(String vipId) {
        Map<String,Object> param = new HashMap<>();
        param.put("vipId",vipId);
        return vipManageCustomizeMapper.getVipAuthList(param);
    }
}
