/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author nxl
 * @version InitCompanyInfoResponseBean, v0.1 2018/7/16 13:51
 */
public class InitUserBaseInfoResponseBean {

    //用户信息
    @ApiModelProperty(value = "用户信息")
    private UserManagerUpdateCustomizeVO UserManagerDetailCustomizeVO;
    //更改记录
    @ApiModelProperty(value = "更改记录")
    private List<UserChangeLogCustomizeVO> usersChangeLogForm;
    //更改记录
    @ApiModelProperty(value = "银行卡信息")
    private BankCardCustomizeVO bankCardInfo;
    //修改类型
    @ApiModelProperty(value = "修改类型(mobile->修改手机号,email->修改邮箱,userRole->修改用户角色,bankCard->修改银行卡)")
    private String updType;

    public List<UserChangeLogCustomizeVO> getUsersChangeLogForm() {
        return usersChangeLogForm;
    }

    public void setUsersChangeLogForm(List<UserChangeLogCustomizeVO> usersChangeLogForm) {
        this.usersChangeLogForm = usersChangeLogForm;
    }

    public BankCardCustomizeVO getBankCardInfo() {
        return bankCardInfo;
    }

    public void setBankCardInfo(BankCardCustomizeVO bankCardInfo) {
        this.bankCardInfo = bankCardInfo;
    }

    public String getUpdType() {
        return updType;
    }

    public void setUpdType(String updType) {
        this.updType = updType;
    }

    public UserManagerUpdateCustomizeVO getUserManagerDetailCustomizeVO() {
        return UserManagerDetailCustomizeVO;
    }

    public void setUserManagerDetailCustomizeVO(UserManagerUpdateCustomizeVO userManagerDetailCustomizeVO) {
        UserManagerDetailCustomizeVO = userManagerDetailCustomizeVO;
    }
}
