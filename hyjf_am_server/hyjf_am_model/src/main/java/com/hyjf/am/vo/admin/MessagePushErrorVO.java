/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;

/**
 * @author dangzw
 * @version MessagePushErrorVO, v0.1 2018/8/14 21:55
 */
public class MessagePushErrorVO extends BaseVO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 标题查询
     */
    private String msgTitleSrch;
    /**
     * 标签查询
     */
    private String tagIdSrch;
    /**
     * 消息编码查询
     */
    private String msgCodeSrch;
    /**
     * 时间查询
     */
    private String startDateSrch;
    /**
     * 时间查询
     */
    private String endDateSrch;


    private String ids;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    /*
	 * 用户id
	 */
    private Integer userId;
    /**
     * 设备唯一标识码（极光别名）
     */
    private String mobileCode;
    /**
     * 手机号
     * @return
     */
    private String mobile;
    /**
     * 手机号
     * @return
     */
    private String mobiles[];
    /**
     * 包号，39 新极光 79老极光 推送
     */
    private String packageCode;

    /**
     * 所属平台（2 安卓 3 ios）
     */
    private String client;


    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getMobileCode() {
        return mobileCode;
    }
    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String[] getMobiles() {
        return mobiles;
    }
    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }
    public String getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
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

    private String id;

    private Integer tagId;

    private String tagCode;

    private String msgCode;

    private String msgTitle;

    private String msgImageUrl;

    private String msgContent;

    private String msgTerminal;

    private Integer msgAction;

    private String msgActionUrl;

    private Integer msgDestinationType;

    private String msgDestination;

    private Integer msgUserId;

    private Integer msgSendStatus;

    private Integer msgReadCountAndroid;

    private Integer msgReadCountIos;

    private Integer msgFirstreadPlat;

    private String msgJpushId;

    private String msgJpushProId;

    private String msgJpushZybId;

    private String msgJpushZzbId;

    private String msgJpushYxbId;

    private String msgJpushZnbId;

    private String msgJpushTestId;

    private String msgRemark;

    private Integer sendTime;

    private Integer msgDestinationCountIos;

    private Integer msgDestinationCountAndroid;

    private Integer createTime;

    private Integer createUserId;

    private String createUserName;

    private Integer lastupdateTime;

    private Integer lastupdateUserId;

    private String lastupdateUserName;

    private String msgJpushZyb2Id;

    private String nameClass;

    private String nameCd;

    private String name;

    private String other1;

    private String other2;

    private String other3;

    private Integer sort;

    private String delFlag;

    private String createtime;

    private String updatetime;

    private String createuser;

    private String updateuser;

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getNameCd() {
        return nameCd;
    }

    public void setNameCd(String nameCd) {
        this.nameCd = nameCd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
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
