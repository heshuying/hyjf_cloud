package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoRequsetBean, v0.1 2018/7/7 14:18
 */
public class BorrowLogRequsetBean extends BaseRequest implements Serializable {
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String sort;
    /**
     * 排序列
     */
    @ApiModelProperty(value = "排序列")
    private String col;

    /**
     * 检索条件 借款编号
     */
    @ApiModelProperty(value = "借款编号")
    private String borrowNidSrch;

    /**
     * 检索条件 项目状态名称
     */
    @ApiModelProperty(value = "项目状态名称")
    private String borrowStatusSrch;
    /**
     * 检索条件 修改类型
     */
    @ApiModelProperty(value = "修改类型")
    private String typeSrch;

    /**检索条件 操作人
     * */
    @ApiModelProperty(value = "操作人")
    private String createUserNameSrch;

    /**
     * 检索条件 时间开始
     */
    @ApiModelProperty(value = "时间开始")
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    @ApiModelProperty(value = "时间结束")
    private String timeEndSrch;


    // EXCEL 导出用
    @ApiModelProperty(value = "项目编号（导出用）")
    private String borrowNid;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getBorrowStatusSrch() {
        return borrowStatusSrch;
    }

    public void setBorrowStatusSrch(String borrowStatusSrch) {
        this.borrowStatusSrch = borrowStatusSrch;
    }

    public String getTypeSrch() {
        return typeSrch;
    }

    public void setTypeSrch(String typeSrch) {
        this.typeSrch = typeSrch;
    }

    public String getCreateUserNameSrch() {
        return createUserNameSrch;
    }

    public void setCreateUserNameSrch(String createUserNameSrch) {
        this.createUserNameSrch = createUserNameSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
}
