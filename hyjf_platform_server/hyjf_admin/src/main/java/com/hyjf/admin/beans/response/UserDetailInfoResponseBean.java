/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class UserDetailInfoResponseBean {
    private UserManagerDetailVO userManagerDetailVO;
    //测评信息
    private UserEvalationResultVO userEvalationResultInfo;
    //已过期需要重新评测
    private String isEvalation;
    //用户开户信息
    private UserBankOpenAccountVO userBankOpenAccountVO;
    //第三方平台绑定信息
    private BindUserVo bindUserVo;
    //电子签章
    private CertificateAuthorityVO certificateAuthorityVO;

    //公司信息
    private CorpOpenAccountRecordVO enterpriseInformation;
    public UserManagerDetailVO getUserManagerDetailVO() {
        return userManagerDetailVO;
    }

    public void setUserManagerDetailVO(UserManagerDetailVO userManagerDetailVO) {
        this.userManagerDetailVO = userManagerDetailVO;
    }

    public UserEvalationResultVO getUserEvalationResultInfo() {
        return userEvalationResultInfo;
    }

    public void setUserEvalationResultInfo(UserEvalationResultVO userEvalationResultInfo) {
        this.userEvalationResultInfo = userEvalationResultInfo;
    }

    public String getIsEvalation() {
        return isEvalation;
    }

    public void setIsEvalation(String isEvalation) {
        this.isEvalation = isEvalation;
    }

    public UserBankOpenAccountVO getUserBankOpenAccountVO() {
        return userBankOpenAccountVO;
    }

    public void setUserBankOpenAccountVO(UserBankOpenAccountVO userBankOpenAccountVO) {
        this.userBankOpenAccountVO = userBankOpenAccountVO;
    }

    public CorpOpenAccountRecordVO getEnterpriseInformation() {
        return enterpriseInformation;
    }

    public void setEnterpriseInformation(CorpOpenAccountRecordVO enterpriseInformation) {
        this.enterpriseInformation = enterpriseInformation;
    }

    public BindUserVo getBindUserVo() {
        return bindUserVo;
    }

    public void setBindUserVo(BindUserVo bindUserVo) {
        this.bindUserVo = bindUserVo;
    }

    public CertificateAuthorityVO getCertificateAuthorityVO() {
        return certificateAuthorityVO;
    }

    public void setCertificateAuthorityVO(CertificateAuthorityVO certificateAuthorityVO) {
        this.certificateAuthorityVO = certificateAuthorityVO;
    }
}
