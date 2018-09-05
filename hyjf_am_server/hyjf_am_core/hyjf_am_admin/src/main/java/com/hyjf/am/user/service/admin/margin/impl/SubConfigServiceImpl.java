package com.hyjf.am.user.service.admin.margin.impl;

import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.user.service.admin.margin.SubConfigService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.SubCommissionListConfigVo;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/10.
 */
@Service("amUserSubConfigServiceImpl")
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
                vo.setOpen((String)map.get("OPEN"));
                vo.setStatus((Integer)map.get("OPEN"));
                res.setResult(vo);
                return res;
            }
            return null;
        }
        return null;
    }

    /**
     * 根据用户名查询分账名单是否存在
     * @param username
     * @return
     */
    @Override
    public AdminSubConfigResponse selectByExampleUsername(String username){
        AdminSubConfigResponse response = new AdminSubConfigResponse();
        SubCommissionListConfigVo subCommissionListConfigCustomize=new SubCommissionListConfigVo();
        subCommissionListConfigCustomize.setUsername(username);
        List<SubCommissionListConfigVo> configVo=this.subConfigCustomizeMapper.selectByExampleUsername(subCommissionListConfigCustomize);
        if(!CollectionUtils.isEmpty(configVo)){
            response.setResultList(configVo);
            return response;
        }
        response.setMessage("查询的数据为空！");
        return response;
    }
}
