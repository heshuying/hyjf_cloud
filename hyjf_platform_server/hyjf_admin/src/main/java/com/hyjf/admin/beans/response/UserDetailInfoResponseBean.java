/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.*;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BindUserVo;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.UserBankOpenAccountVO;
import com.hyjf.am.vo.user.UserEvalationResultVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version UserDetailInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class UserDetailInfoResponseBean {

    @ApiModelProperty(value = "用户详情模块")
    private UserManagerDetailCustomizeVO userManagerDetailVO;
    //测评信息
    @ApiModelProperty(value = "测评信息模块")
    private UserEvalationResultShowVO userEvalationResultInfo;
    //已过期需要重新评测
    @ApiModelProperty(value = "已过期需要重新评测 2已过期、1有效")
    private String isEvalation;
    //用户开户信息
    @ApiModelProperty(value = "用户开户信息")
    private UserBankOpenAccountCustomizeVO userBankOpenAccountVO;
    //第三方平台绑定信息
    @ApiModelProperty(value = "第三方平台绑定信息")
    private BindUserCustomizeVO bindUserVo;
    //电子签章
    @ApiModelProperty(value = "电子签章")
    private CertificateAuthorityCustomizeVO certificateAuthorityVO;

    //公司信息
    @ApiModelProperty(value = "公司信息")
    private CorpOpenAccountRecordCustomizeVO enterpriseInformation;

    public UserManagerDetailCustomizeVO getUserManagerDetailVO() {
        return userManagerDetailVO;
    }

    public void setUserManagerDetailVO(UserManagerDetailCustomizeVO userManagerDetailVO) {
        this.userManagerDetailVO = userManagerDetailVO;
    }

    public UserEvalationResultShowVO getUserEvalationResultInfo() {
        return userEvalationResultInfo;
    }

    public void setUserEvalationResultInfo(UserEvalationResultShowVO userEvalationResultInfo) {
        this.userEvalationResultInfo = userEvalationResultInfo;
    }

    public String getIsEvalation() {
        return isEvalation;
    }

    public void setIsEvalation(String isEvalation) {
        this.isEvalation = isEvalation;
    }

    public UserBankOpenAccountCustomizeVO getUserBankOpenAccountVO() {
        return userBankOpenAccountVO;
    }

    public void setUserBankOpenAccountVO(UserBankOpenAccountCustomizeVO userBankOpenAccountVO) {
        this.userBankOpenAccountVO = userBankOpenAccountVO;
    }

    public CorpOpenAccountRecordCustomizeVO getEnterpriseInformation() {
        return enterpriseInformation;
    }

    public void setEnterpriseInformation(CorpOpenAccountRecordCustomizeVO enterpriseInformation) {
        this.enterpriseInformation = enterpriseInformation;
    }

    public BindUserCustomizeVO getBindUserVo() {
        return bindUserVo;
    }

    public void setBindUserVo(BindUserCustomizeVO bindUserVo) {
        this.bindUserVo = bindUserVo;
    }

    public CertificateAuthorityCustomizeVO getCertificateAuthorityVO() {
        return certificateAuthorityVO;
    }

    public void setCertificateAuthorityVO(CertificateAuthorityCustomizeVO certificateAuthorityVO) {
        this.certificateAuthorityVO = certificateAuthorityVO;
    }
}
