package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.MessagePushMsgVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author lisheng
 * @version MessagePushNoticesRequest, v0.1 2018/8/14 16:19
 */

public class MessagePushNoticesRequest extends MessagePushMsgVO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 标签类型查询
     */
    @ApiModelProperty(value = "标签类型查询")
    private Integer noticesTagIdSrch;
    /**
     * 标题查询
     */
    @ApiModelProperty(value = "标题查询")
    private String noticesTitleSrch;
    /**
     * 消息编码查询
     */
    @ApiModelProperty(value = "消息编码查询")
    private String noticesCodeSrch;
    /**
     * 作者查询
     */
    @ApiModelProperty(value = "作者查询")
    private String noticesCreateUserNameSrch;
    /**
     * 推送平台查询
     */
    @ApiModelProperty(value = "推送平台查询")
    private String noticesTerminalSrch;
    /**
     * 状态查询
     */
    @ApiModelProperty(value = "状态查询")
    private Integer noticesSendStatusSrch;
    /**
     * 指定的原生界面
     */
    @ApiModelProperty(value = "指定的原生界面")
    private String noticesActionUrl1;
    /**
     * 指定的原生界面
     */
    @ApiModelProperty(value = "指定的原生界面")
    private String noticesActionUrl2;

    @ApiModelProperty(value = "指定的原生界面")
    private String noticesActionUrl3;
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
    private String noticesPreSendTimeStr;
    private String ids;

    private List<MessagePushMsgVO> recordList;

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


    private String userName;
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

    public Integer getNoticesTagIdSrch() {
        return noticesTagIdSrch;
    }

    public void setNoticesTagIdSrch(Integer noticesTagIdSrch) {
        this.noticesTagIdSrch = noticesTagIdSrch;
    }

    public String getNoticesTitleSrch() {
        return noticesTitleSrch;
    }

    public void setNoticesTitleSrch(String noticesTitleSrch) {
        this.noticesTitleSrch = noticesTitleSrch;
    }

    public String getNoticesCodeSrch() {
        return noticesCodeSrch;
    }

    public void setNoticesCodeSrch(String noticesCodeSrch) {
        this.noticesCodeSrch = noticesCodeSrch;
    }

    public String getNoticesCreateUserNameSrch() {
        return noticesCreateUserNameSrch;
    }

    public void setNoticesCreateUserNameSrch(String noticesCreateUserNameSrch) {
        this.noticesCreateUserNameSrch = noticesCreateUserNameSrch;
    }

    public String getNoticesTerminalSrch() {
        return noticesTerminalSrch;
    }

    public void setNoticesTerminalSrch(String noticesTerminalSrch) {
        this.noticesTerminalSrch = noticesTerminalSrch;
    }

    public Integer getNoticesSendStatusSrch() {
        return noticesSendStatusSrch;
    }

    public void setNoticesSendStatusSrch(Integer noticesSendStatusSrch) {
        this.noticesSendStatusSrch = noticesSendStatusSrch;
    }

    public String getNoticesActionUrl1() {
        return noticesActionUrl1;
    }

    public void setNoticesActionUrl1(String noticesActionUrl1) {
        this.noticesActionUrl1 = noticesActionUrl1;
    }

    public String getNoticesActionUrl2() {
        return noticesActionUrl2;
    }

    public void setNoticesActionUrl2(String noticesActionUrl2) {
        this.noticesActionUrl2 = noticesActionUrl2;
    }

    public String getNoticesActionUrl3() {
        return noticesActionUrl3;
    }

    public void setNoticesActionUrl3(String noticesActionUrl3) {
        this.noticesActionUrl3 = noticesActionUrl3;
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

    public String getNoticesPreSendTimeStr() {
        return noticesPreSendTimeStr;
    }

    public void setNoticesPreSendTimeStr(String noticesPreSendTimeStr) {
        this.noticesPreSendTimeStr = noticesPreSendTimeStr;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<MessagePushMsgVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<MessagePushMsgVO> recordList) {
        this.recordList = recordList;
    }

    public String getUpdateOrReSend() {
        return updateOrReSend;
    }

    public void setUpdateOrReSend(String updateOrReSend) {
        this.updateOrReSend = updateOrReSend;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
