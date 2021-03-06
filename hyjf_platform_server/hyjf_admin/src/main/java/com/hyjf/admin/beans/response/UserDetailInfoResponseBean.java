/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

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
    private CompanyInfoCompanyInfoVO enterpriseInformation;
    //文件服务器
    @ApiModelProperty(value = "文件服务器")
    private String hostUrl;

    // 邀请信息
    @ApiModelProperty(value = "邀请信息")
    private Map<String, String> invitationInformation;

    public Map<String, String> getInvitationInformation() {
        return invitationInformation;
    }

    public void setInvitationInformation(Map<String, String> invitationInformation) {
        this.invitationInformation = invitationInformation;
    }

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

    public CompanyInfoCompanyInfoVO getEnterpriseInformation() {
        return enterpriseInformation;
    }

    public void setEnterpriseInformation(CompanyInfoCompanyInfoVO enterpriseInformation) {
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

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
