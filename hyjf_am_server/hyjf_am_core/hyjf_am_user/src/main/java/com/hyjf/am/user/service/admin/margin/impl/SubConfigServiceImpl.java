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
                vo.setUserName((String)map.get("username"));
                vo.setTrueName((String)map.get("truename"));
                vo.setRoleId((Integer)map.get("role_id"));
                if((Boolean) map.get("user_type")){
                    vo.setStatus(1);
                }
                if(!(Boolean) map.get("user_type")){
                    vo.setStatus(0);
                }
                vo.setCooperateNum((String)map.get("cooperateNum"));
                vo.setAccount((String)map.get("account"));
                vo.setOpen((String)map.get("OPEN"));
                if((Boolean) map.get("status")){
                    vo.setStatus(1);
                }
                if(!(Boolean) map.get("status")){
                    vo.setStatus(0);
                }
                res.setResult(vo);
                return res;
            }
            return null;
        }
        return null;
    }
}
