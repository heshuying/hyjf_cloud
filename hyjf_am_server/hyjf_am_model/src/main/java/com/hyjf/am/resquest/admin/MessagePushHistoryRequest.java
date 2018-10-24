package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author lisheng
 * @version MessagePushHistoryRequest, v0.1 2018/8/14 20:10
 */

public class MessagePushHistoryRequest extends MessagePushMsgHistoryVO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 标签类型查询
     */
    @ApiModelProperty(value = "标签类型查询")
    private Integer historyTagIdSrch;
    /**
     * 标题查询
     */
    @ApiModelProperty(value = "标题查询")
    private String historyTitleSrch;
    /**
     * 消息编码查询
     */
    @ApiModelProperty(value = "消息编码查询")
    private String historyCodeSrch;
    /**
     * 作者查询
     */
    @ApiModelProperty(value = "作者查询")
    private String historyCreateUserNameSrch;
    /**
     * 推送平台查询
     */
    @ApiModelProperty(value = "推送平台查询")
    private String historyTerminalSrch;
    /**
     * 首次阅读终端查询
     */
    @ApiModelProperty(value = "首次阅读终端查询")
    private String historyFirstReadTerminalSrch;
    /**
     * 状态查询
     */
    @ApiModelProperty(value = "状态查询")
    private Integer historySendStatusSrch;
    /**
     * 指定的原生界面
     */
    @ApiModelProperty(value = "指定的原生界面")
    private String historyActionUrl1;
    /**
     * 指定的原生界面
     */
    @ApiModelProperty(value = "指定的原生界面")
    private String historyActionUrl2;
    /**
     * 发送开始时间查询
     */
    @ApiModelProperty(value = "发送开始时间查询")
    private String startSendTimeSrch;
    /**
     * 发送截止时间查询
     */
    @ApiModelProperty(value = "发送截止时间查询")
    private String endSendTimeSrch;
    /**
     * 发送时间
     */
    @ApiModelProperty(value = "发送时间")
    private String historyPreSendTimeStr;

    private String ids;

    private List<MessagePushMsgHistoryVO> recordList;

    /**
     * 更新或是转发,0为更新1为转发
     */
    @ApiModelProperty(value = "更新或是转发,0为更新1为转发")
    private String updateOrReSend;


    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    private int currPage;

    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private int pageSize;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getHistoryTagIdSrch() {
        return historyTagIdSrch;
    }

    public void setHistoryTagIdSrch(Integer historyTagIdSrch) {
        this.historyTagIdSrch = historyTagIdSrch;
    }

    public String getHistoryTitleSrch() {
        return historyTitleSrch;
    }

    public void setHistoryTitleSrch(String historyTitleSrch) {
        this.historyTitleSrch = historyTitleSrch;
    }

    public String getHistoryCodeSrch() {
        return historyCodeSrch;
    }

    public void setHistoryCodeSrch(String historyCodeSrch) {
        this.historyCodeSrch = historyCodeSrch;
    }

    public String getHistoryCreateUserNameSrch() {
        return historyCreateUserNameSrch;
    }

    public void setHistoryCreateUserNameSrch(String historyCreateUserNameSrch) {
        this.historyCreateUserNameSrch = historyCreateUserNameSrch;
    }

    public String getHistoryTerminalSrch() {
        return historyTerminalSrch;
    }

    public void setHistoryTerminalSrch(String historyTerminalSrch) {
        this.historyTerminalSrch = historyTerminalSrch;
    }

    public String getHistoryFirstReadTerminalSrch() {
        return historyFirstReadTerminalSrch;
    }

    public void setHistoryFirstReadTerminalSrch(String historyFirstReadTerminalSrch) {
        this.historyFirstReadTerminalSrch = historyFirstReadTerminalSrch;
    }

    public Integer getHistorySendStatusSrch() {
        return historySendStatusSrch;
    }

    public void setHistorySendStatusSrch(Integer historySendStatusSrch) {
        this.historySendStatusSrch = historySendStatusSrch;
    }

    public String getHistoryActionUrl1() {
        return historyActionUrl1;
    }

    public void setHistoryActionUrl1(String historyActionUrl1) {
        this.historyActionUrl1 = historyActionUrl1;
    }

    public String getHistoryActionUrl2() {
        return historyActionUrl2;
    }

    public void setHistoryActionUrl2(String historyActionUrl2) {
        this.historyActionUrl2 = historyActionUrl2;
    }

    public String getStartSendTimeSrch() {
        return startSendTimeSrch;
    }

    public void setStartSendTimeSrch(String startSendTimeSrch) {
        this.startSendTimeSrch = startSendTimeSrch;
    }

    public String getEndSendTimeSrch() {
        return endSendTimeSrch;
    }

    public void setEndSendTimeSrch(String endSendTimeSrch) {
        this.endSendTimeSrch = endSendTimeSrch;
    }

    public String getHistoryPreSendTimeStr() {
        return historyPreSendTimeStr;
    }

    public void setHistoryPreSendTimeStr(String historyPreSendTimeStr) {
        this.historyPreSendTimeStr = historyPreSendTimeStr;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<MessagePushMsgHistoryVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<MessagePushMsgHistoryVO> recordList) {
        this.recordList = recordList;
    }

    public String getUpdateOrReSend() {
        return updateOrReSend;
    }

    public void setUpdateOrReSend(String updateOrReSend) {
        this.updateOrReSend = updateOrReSend;
    }
}
