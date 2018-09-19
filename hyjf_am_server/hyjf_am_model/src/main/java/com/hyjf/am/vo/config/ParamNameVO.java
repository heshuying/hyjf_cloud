/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun
 * @version ParamName, v0.1 2018/7/5 9:11
 */
@ApiModel(value = "数据字典返回值参数")
public class ParamNameVO extends BaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "字典名称")
    private String name;
    @ApiModelProperty(value = "字典区分")
    private String nameClass;
    @ApiModelProperty(value = "扩展项目1")
    private String other1;
    @ApiModelProperty(value = "扩展项目2")
    private String other2;
    @ApiModelProperty(value = "扩展项目3")
    private String other3;
    @ApiModelProperty(value = "排序")
    private Short sort;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;
    @ApiModelProperty(value = "字典编号")
    private String nameCd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getNameCd() {
        return nameCd;
    }

    public void setNameCd(String nameCd) {
        this.nameCd = nameCd;
    }
}
