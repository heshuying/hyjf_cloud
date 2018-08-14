/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.List;

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


    private Integer id;

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

    private static final long serialVersionUID = 1L;

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
