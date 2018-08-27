package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
/*
 *还款方式
 */
@ApiModel(value = "还款方式")
public class BorrowStyleVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "标示名")
    private String nid;
    @ApiModelProperty(value = "状态：是否启用")
    private Integer status;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "前端页面显示用")
    private String label;
    @ApiModelProperty(value = "名称，可改")
    private String title;

    private String contents;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getLabel() {
        return name;
    }

    public void setLabel(String label) {
        this.name = label;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}