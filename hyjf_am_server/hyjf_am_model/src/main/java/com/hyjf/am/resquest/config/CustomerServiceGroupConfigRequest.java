package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;

/**
 * @author wgx
 * @date 2019/5/29
 */
public class CustomerServiceGroupConfigRequest extends BasePage{
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 客组名称
     *
     * @mbggenerated
     */
    private String groupName;

    /**
     * 第三方用户唯一凭证
     *
     * @mbggenerated
     */
    private String serviceUserCode;

    /**
     * 第三方用户账户编号
     *
     * @mbggenerated
     */
    private String serviceUserNo;

    /**
     * 第三方用户唯一凭证密钥
     *
     * @mbggenerated
     */
    private String serviceUserKey;

    /**
     * 启用状态 1.启用2.禁用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getServiceUserCode() {
        return serviceUserCode;
    }

    public void setServiceUserCode(String serviceUserCode) {
        this.serviceUserCode = serviceUserCode;
    }

    public String getServiceUserNo() {
        return serviceUserNo;
    }

    public void setServiceUserNo(String serviceUserNo) {
        this.serviceUserNo = serviceUserNo;
    }

    public String getServiceUserKey() {
        return serviceUserKey;
    }

    public void setServiceUserKey(String serviceUserKey) {
        this.serviceUserKey = serviceUserKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}
