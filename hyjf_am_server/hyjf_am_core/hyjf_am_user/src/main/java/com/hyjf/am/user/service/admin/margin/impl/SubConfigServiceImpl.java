package com.hyjf.am.user.service.admin.margin.impl;

import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.user.service.admin.margin.SubConfigService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author by xiehuili on 2018/7/10.
 */
@Service
public class SubConfigServiceImpl extends BaseServiceImpl implements SubConfigService {

    /**
     * 保证金配置，根据用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public UserInfoCustomizeResponse selectUserInfoByUserName(String userName){
        UserInfoCustomizeResponse res = new UserInfoCustomizeResponse();
        if (StringUtils.isNotBlank(userName)) {
            Map<String, Object> map= subConfigCustomizeMapper.selectUserInfoByUserName(userName);
            UserInfoCustomizeVO vo = new UserInfoCustomizeVO();
            if (map.get("user_id")!=null) {
                vo.setUserId(Integer.parseInt(String.valueOf(map.get("user_id"))));
                res.setResult(vo);
                return res;
            }
            return null;
        }
        return null;
    }
}
