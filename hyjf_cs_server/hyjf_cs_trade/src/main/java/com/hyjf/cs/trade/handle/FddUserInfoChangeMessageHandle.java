/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: sunpeikai
 * @version: FddUserInfoChangeMessageHandle, v0.1 2018/7/16 10:48
 */
@Component
public class FddUserInfoChangeMessageHandle {

    @Autowired
    private AmUserClient amUserClient;

    public UserVO getUserByUserId(Integer userId){
        return amUserClient.findUserById(userId);
    }

    public UserInfoVO getUserInfoByUserId(Integer userId){
        return amUserClient.findUsersInfoById(userId);
    }

    public CertificateAuthorityVO getCertificateAuthorityByUserId(Integer userId){
        return amUserClient.selectCertificateAuthorityByUserId(String.valueOf(userId));
    }

    public boolean updateUserCAInfo(Integer userId){
        boolean isUpdateFlag = false;
        // 根据用户ID获取用户信息
        UserVO userVO = amUserClient.findUserById(userId);
        // 获取用户CA认证相关信息
        CertificateAuthorityVO certificateAuthority = amUserClient.selectCertificateAuthorityByUserId(String.valueOf(userId));
        // 客户编号
        String customerId = certificateAuthority.getCustomerId();
        // 调用客户信息修改接口
        // 参数生成
        DzqzCallBean bean = new DzqzCallBean();
        bean.setUserId(userId);
        bean.setTxCode("infochange");
        bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
        bean.setV(DzqzConstant.HYJF_FDD_VERSION);
        bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
        bean.setCustomer_id(customerId);// 客户编号
        bean.setEmail(StringUtils.isNotBlank(userVO.getEmail()) ? userVO.getEmail() : "");// 电子邮箱
        bean.setMobile(userVO.getMobile());// 手机号
        // 调用接口
        DzqzCallBean result = DzqzCallUtil.callApiBg(bean);
        if (result != null && "success".equals(result.getResult())) {
            // 如果返回成功
            certificateAuthority.setMobile(userVO.getMobile());// 手机号
            certificateAuthority.setEmail(StringUtils.isNotBlank(userVO.getEmail()) ? userVO.getEmail() : "");// 电子邮箱
            // 更新用户认证信息表
            isUpdateFlag = amUserClient.updateCertificateAuthority(certificateAuthority) > 0;
        }
        return isUpdateFlag;
    }
}
