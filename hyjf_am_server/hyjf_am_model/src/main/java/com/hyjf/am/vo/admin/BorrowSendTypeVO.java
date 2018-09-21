package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by xiehuili on 2018/8/1.
 */
@ApiModel(value = "发标复审")
public class BorrowSendTypeVO extends BaseVO implements Serializable {

    @ApiModelProperty(value = "唯一标识")
    private String sendCd;
    @ApiModelProperty(value = "名称(自动发标时间间隔,自动复审时间间隔)")
    private String sendName;
    @ApiModelProperty(value = "发表时间")
    private Integer afterTime;
    @ApiModelProperty(value = "备注说明")
    private String remark;
    @ApiModelProperty(value = "删除状态")
    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

    private String modifyFlag;

    private static final long serialVersionUID = 1L;

    public String getSendCd() {
        return sendCd;
    }

    public void setSendCd(String sendCd) {
        this.sendCd = sendCd == null ? null : sendCd.trim();
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName == null ? null : sendName.trim();
    }

    public Integer getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(Integer afterTime) {
        this.afterTime = afterTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }
}
