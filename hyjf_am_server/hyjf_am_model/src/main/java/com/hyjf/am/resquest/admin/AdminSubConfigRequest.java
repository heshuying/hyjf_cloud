package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author by xiehuili on 2018/7/9.
 */
@ApiModel(value="分账名单配置",description="分账名单配置")
public class AdminSubConfigRequest extends BasePage {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 8853785949572253967L;
    private String ids;

    @ApiModelProperty(value = "用户名")
    private String userNameSrch;

    @ApiModelProperty(value = "姓名")
    String trueNameSrch;

    @ApiModelProperty(value = "角色 ")
    String roleNameSrch;

    @ApiModelProperty(value = "用户类型 ")
    private String userTypeSrch;

    @ApiModelProperty(value = " 江西银行电子账号")
    private String accountSrch;

    @ApiModelProperty(value = "用户状态")
    private String statusSrch;

    @ApiModelProperty(value = "添加时间 ")
    private String recieveTimeStartSrch;

    @ApiModelProperty(value = "添加时间")
    private String recieveTimeEndSrch;
    private Integer id;

    private Integer userId;

    private String truename;

    private String username;

    private String roleName;

    private String userType;

    private String bankOpenAccount;

    private String account;

    private Integer status;

    private String remark;

    private Integer createUserId;

    private String createUserName;

    private Integer updateUserId;

    private String updateUserName;

    private String cooperateNum;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCooperateNum() {
        return cooperateNum;
    }

    public void setCooperateNum(String cooperateNum) {
        this.cooperateNum = cooperateNum;
    }

    public String getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(String bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    private int paginatorPage = 1;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getTrueNameSrch() {
        return trueNameSrch;
    }

    public void setTrueNameSrch(String trueNameSrch) {
        this.trueNameSrch = trueNameSrch;
    }

    public String getRoleNameSrch() {
        return roleNameSrch;
    }

    public void setRoleNameSrch(String roleNameSrch) {
        this.roleNameSrch = roleNameSrch;
    }

    public String getUserTypeSrch() {
        return userTypeSrch;
    }

    public void setUserTypeSrch(String userTypeSrch) {
        this.userTypeSrch = userTypeSrch;
    }

    public String getAccountSrch() {
        return accountSrch;
    }

    public void setAccountSrch(String accountSrch) {
        this.accountSrch = accountSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getRecieveTimeStartSrch() {
        return recieveTimeStartSrch;
    }

    public void setRecieveTimeStartSrch(String recieveTimeStartSrch) {
        this.recieveTimeStartSrch = recieveTimeStartSrch;
    }

    public String getRecieveTimeEndSrch() {
        return recieveTimeEndSrch;
    }

    public void setRecieveTimeEndSrch(String recieveTimeEndSrch) {
        this.recieveTimeEndSrch = recieveTimeEndSrch;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
