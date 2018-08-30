/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.GetDate;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yaoyong
 * @version CouponCheckVO, v0.1 2018/7/4 10:39
 */
public class CouponCheckVO extends BaseVO implements Serializable {
    private Integer id;

    private String fileName;

    private String filePath;

    private Integer status;

    private String remark;

    private Integer deFlag;

    private String createName;

    private String updateName;

    private Date createTime;

    private Date updateTime;

    private String createtime;

    private String updatetime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(Integer deFlag) {
        this.deFlag = deFlag;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
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

    public String getCreatetime() {
        return GetDate.getDateTimeMyTimeInMillis(createTime);
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return GetDate.getDateTimeMyTimeInMillis(updateTime);
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
