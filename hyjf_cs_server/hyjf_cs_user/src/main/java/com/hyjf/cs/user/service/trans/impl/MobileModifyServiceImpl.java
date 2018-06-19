/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.trans.impl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.utils.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version MobileModifyServiceImpl, v0.1 2018/6/14 16:48
 */
@Service
public class MobileModifyServiceImpl extends BaseUserServiceImpl implements MobileModifyService {

    @Autowired
    AmUserClient amUserClient;
    /**
     * 更换手机号条件校验
     * @param newMobile
     * @param smsCode
     */
    @Override
    public boolean checkForMobileModify(String newMobile, String smsCode) {
        String verificationType = CommonConstant.PARAM_TPL_BDYSJH;
        int cnt = amUserClient.checkMobileCode(newMobile, smsCode, verificationType, CommonConstant.CLIENT_PC,
                CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        CheckUtil.check(cnt > 0, MsgEnum.ERR_SMSCODE_INVALID);
        return true;
    }


    /**
     * 用户手机号修改信息查询
     * @param userId
     * @return
     */
    @Override
    public MobileModifyResultBean queryForMobileModify(Integer userId) {
        MobileModifyResultBean result = new MobileModifyResultBean();
        UserVO user = amUserClient.findUserById(userId);
        if(user != null && StringUtils.isNotBlank(user.getMobile())) {
            String hideMobile = user.getMobile().substring(0,user.getMobile().length()-(user.getMobile().substring(3)).length())+"****"+user.getMobile().substring(7);
            result.setMobile(user.getMobile());
            result.setHideMobile(hideMobile);
        }

        return result;
    }

}
