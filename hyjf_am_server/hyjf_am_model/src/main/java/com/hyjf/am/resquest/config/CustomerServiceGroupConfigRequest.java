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
     * 是否为新客 1新客组 0老客组
     *
     * @mbggenerated
     */
    private Integer isNew;

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

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

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

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
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

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
