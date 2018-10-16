/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorRequest, v0.1 2018/8/14 22:01
 */
public class MessagePushErrorRequest extends BasePage implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 标题查询
     */
    @ApiModelProperty("标题查询")
    private String msgTitleSrch;
    /**
     * 标签查询
     */
    @ApiModelProperty("标签查询")
    private String tagIdSrch;
    /**
     * 消息编码查询
     */
    @ApiModelProperty("消息编码查询")
    private String msgCodeSrch;

    /**
     * 时间查询
     */
    @ApiModelProperty("开始时间查询")
    private String startDateSrch;
    /**
     * 时间查询
     */
    @ApiModelProperty("结束时间查询")
    private String endDateSrch;

    @ApiModelProperty("存放多个id")
    private String ids;

    /**
     * 翻页机能用的隐藏变量
     */
    @ApiModelProperty("翻页机能用的隐藏变量")
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    @ApiModelProperty("列表画面自定义标签上的用翻页对象")
    private Paginator paginator;

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("消息标签,外键,消息标签表的id")
    private String tagId;

    @ApiModelProperty("消息标签编码,消息标签表的编码")
    private String tagCode;

    @ApiModelProperty("消息编码")
    private String msgCode;

    @ApiModelProperty("消息标题")
    private String msgTitle;

    @ApiModelProperty("图片url")
    private String msgImageUrl;

    @ApiModelProperty("消息内容")
    private String msgContent;

    @ApiModelProperty("推送终端")
    private String msgTerminal;

    @ApiModelProperty("后续动作,0打开APP,1打开H5页面,2指定原生页面")
    private Integer msgAction;

    @ApiModelProperty("后续动作url,后续动作为0时此字段无效,1时为填写的url,2时为原生页面的url")
    private String msgActionUrl;

    @ApiModelProperty("推送用户类型,0为所有人,1为单个或多个用户")
    private Integer msgDestinationType;

    @ApiModelProperty("推送用户,推送用户类型为0时无效")
    private String msgDestination;

    @ApiModelProperty("用户user_id")
    private Integer msgUserId;

    @ApiModelProperty("发送状态,0待发送,1发送成功,2发送失败")
    private Integer msgSendStatus;

    @ApiModelProperty("android阅读次数,累加,android阅读次数和ios阅读次数加起来大于1代表阅读过了")
    private Integer msgReadCountAndroid;

    @ApiModelProperty("ios阅读次数,累加,android阅读次数和ios阅读次数加起来大于1代表阅读过了")
    private Integer msgReadCountIos;

    @ApiModelProperty("首次阅读平台,根据程序中的平台来记")
    private Integer msgFirstreadPlat;

    @ApiModelProperty("极光推送返回的ID")
    private String msgJpushId;

    @ApiModelProperty("极光推送PRO返回的ID")
    private String msgJpushProId;

    @ApiModelProperty("极光推送专业版返回的ID")
    private String msgJpushZybId;

    @ApiModelProperty("极光推送至尊版返回id")
    private String msgJpushZzbId;

    @ApiModelProperty("极光推送悦享版返回id")
    private String msgJpushYxbId;

    @ApiModelProperty("极光推送周年版返回id")
    private String msgJpushZnbId;

    @ApiModelProperty("极光推送测试id")
    private String msgJpushTestId;

    @ApiModelProperty("发送成功失败备注")
    private String msgRemark;

    @ApiModelProperty("实际发送时间")
    private Integer sendTime;

    @ApiModelProperty("ios目标推送数")
    private Integer msgDestinationCountIos;

    @ApiModelProperty("安卓目标推送数")
    private Integer msgDestinationCountAndroid;

    @ApiModelProperty("创建时间")
    private Integer createTime;

    @ApiModelProperty("创建人用户id")
    private Integer createUserId;

    @ApiModelProperty("标签查询")
    private String createUserName;

    @ApiModelProperty("标签查询")
    private Integer lastupdateTime;

    @ApiModelProperty("创建人用户id")
    private Integer lastupdateUserId;

    @ApiModelProperty("创建人用户名")
    private String lastupdateUserName;

    @ApiModelProperty("极光推送专业版返回的ID2")
    private String msgJpushZyb2Id;

    @ApiModelProperty("开始页")
    private int limitStart;

    @ApiModelProperty("结束页")
    private int limitEnd;

    private List<MessagePushMsgHistoryVO> recordList;

    public List<MessagePushMsgHistoryVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<MessagePushMsgHistoryVO> recordList) {
        this.recordList = recordList;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode == null ? null : tagCode.trim();
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode == null ? null : msgCode.trim();
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgImageUrl() {
        return msgImageUrl;
    }

    public void setMsgImageUrl(String msgImageUrl) {
        this.msgImageUrl = msgImageUrl == null ? null : msgImageUrl.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
    }

    public String getMsgTerminal() {
        return msgTerminal;
    }

    public void setMsgTerminal(String msgTerminal) {
        this.msgTerminal = msgTerminal == null ? null : msgTerminal.trim();
    }

    public Integer getMsgAction() {
        return msgAction;
    }

    public void setMsgAction(Integer msgAction) {
        this.msgAction = msgAction;
    }

    public String getMsgActionUrl() {
        return msgActionUrl;
    }

    public void setMsgActionUrl(String msgActionUrl) {
        this.msgActionUrl = msgActionUrl == null ? null : msgActionUrl.trim();
    }

    public Integer getMsgDestinationType() {
        return msgDestinationType;
    }

    public void setMsgDestinationType(Integer msgDestinationType) {
        this.msgDestinationType = msgDestinationType;
    }

    public String getMsgDestination() {
        return msgDestination;
    }

    public void setMsgDestination(String msgDestination) {
        this.msgDestination = msgDestination == null ? null : msgDestination.trim();
    }

    public Integer getMsgUserId() {
        return msgUserId;
    }

    public void setMsgUserId(Integer msgUserId) {
        this.msgUserId = msgUserId;
    }

    public Integer getMsgSendStatus() {
        return msgSendStatus;
    }

    public void setMsgSendStatus(Integer msgSendStatus) {
        this.msgSendStatus = msgSendStatus;
    }

    public Integer getMsgReadCountAndroid() {
        return msgReadCountAndroid;
    }

    public void setMsgReadCountAndroid(Integer msgReadCountAndroid) {
        this.msgReadCountAndroid = msgReadCountAndroid;
    }

    public Integer getMsgReadCountIos() {
        return msgReadCountIos;
    }

    public void setMsgReadCountIos(Integer msgReadCountIos) {
        this.msgReadCountIos = msgReadCountIos;
    }

    public Integer getMsgFirstreadPlat() {
        return msgFirstreadPlat;
    }

    public void setMsgFirstreadPlat(Integer msgFirstreadPlat) {
        this.msgFirstreadPlat = msgFirstreadPlat;
    }

    public String getMsgJpushId() {
        return msgJpushId;
    }

    public void setMsgJpushId(String msgJpushId) {
        this.msgJpushId = msgJpushId == null ? null : msgJpushId.trim();
    }

    public String getMsgJpushProId() {
        return msgJpushProId;
    }

    public void setMsgJpushProId(String msgJpushProId) {
        this.msgJpushProId = msgJpushProId == null ? null : msgJpushProId.trim();
    }

    public String getMsgJpushZybId() {
        return msgJpushZybId;
    }

    public void setMsgJpushZybId(String msgJpushZybId) {
        this.msgJpushZybId = msgJpushZybId == null ? null : msgJpushZybId.trim();
    }

    public String getMsgJpushZzbId() {
        return msgJpushZzbId;
    }

    public void setMsgJpushZzbId(String msgJpushZzbId) {
        this.msgJpushZzbId = msgJpushZzbId == null ? null : msgJpushZzbId.trim();
    }

    public String getMsgJpushYxbId() {
        return msgJpushYxbId;
    }

    public void setMsgJpushYxbId(String msgJpushYxbId) {
        this.msgJpushYxbId = msgJpushYxbId == null ? null : msgJpushYxbId.trim();
    }

    public String getMsgJpushZnbId() {
        return msgJpushZnbId;
    }

    public void setMsgJpushZnbId(String msgJpushZnbId) {
        this.msgJpushZnbId = msgJpushZnbId == null ? null : msgJpushZnbId.trim();
    }

    public String getMsgRemark() {
        return msgRemark;
    }

    public void setMsgRemark(String msgRemark) {
        this.msgRemark = msgRemark == null ? null : msgRemark.trim();
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getMsgDestinationCountIos() {
        return msgDestinationCountIos;
    }

    public void setMsgDestinationCountIos(Integer msgDestinationCountIos) {
        this.msgDestinationCountIos = msgDestinationCountIos;
    }

    public Integer getMsgDestinationCountAndroid() {
        return msgDestinationCountAndroid;
    }

    public void setMsgDestinationCountAndroid(Integer msgDestinationCountAndroid) {
        this.msgDestinationCountAndroid = msgDestinationCountAndroid;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Integer lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public Integer getLastupdateUserId() {
        return lastupdateUserId;
    }

    public void setLastupdateUserId(Integer lastupdateUserId) {
        this.lastupdateUserId = lastupdateUserId;
    }

    public String getLastupdateUserName() {
        return lastupdateUserName;
    }

    public void setLastupdateUserName(String lastupdateUserName) {
        this.lastupdateUserName = lastupdateUserName == null ? null : lastupdateUserName.trim();
    }

    public String getMsgJpushZyb2Id() {
        return msgJpushZyb2Id;
    }

    public void setMsgJpushZyb2Id(String msgJpushZyb2Id) {
        this.msgJpushZyb2Id = msgJpushZyb2Id == null ? null : msgJpushZyb2Id.trim();
    }

    public String getMsgJpushTestId() {
        return msgJpushTestId;
    }

    public void setMsgJpushTestId(String msgJpushTestId) {
        this.msgJpushTestId = msgJpushTestId == null ? null : msgJpushTestId.trim();
    }

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getMsgTitleSrch() {
        return msgTitleSrch;
    }

    public void setMsgTitleSrch(String msgTitleSrch) {
        this.msgTitleSrch = msgTitleSrch;
    }

    public String getTagIdSrch() {
        return tagIdSrch;
    }

    public void setTagIdSrch(String tagIdSrch) {
        this.tagIdSrch = tagIdSrch;
    }

    public String getMsgCodeSrch() {
        return msgCodeSrch;
    }

    public void setMsgCodeSrch(String msgCodeSrch) {
        this.msgCodeSrch = msgCodeSrch;
    }

    public String getStartDateSrch() {
        return startDateSrch;
    }

    public void setStartDateSrch(String startDateSrch) {
        this.startDateSrch = startDateSrch;
    }

    public String getEndDateSrch() {
        return endDateSrch;
    }

    public void setEndDateSrch(String endDateSrch) {
        this.endDateSrch = endDateSrch;
    }
}
