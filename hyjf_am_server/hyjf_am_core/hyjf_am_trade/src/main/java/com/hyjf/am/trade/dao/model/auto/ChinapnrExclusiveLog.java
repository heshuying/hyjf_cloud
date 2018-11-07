package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class ChinapnrExclusiveLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 唯一id，即将废弃
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * 订单号
     *
     * @mbggenerated
     */
    private String ordid;

    /**
     * 消息类型
     *
     * @mbggenerated
     */
    private String cmdid;

    /**
     * 2.0异步对账返回消息类型
     *
     * @mbggenerated
     */
    private String resptype;

    /**
     * 接口类型
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 状态 0:处理成功,1:请求中,2:处理中,3:验签成功,4:眼前失败,9;处理失败
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 返回编码
     *
     * @mbggenerated
     */
    private String respcode;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 删除FLAG
     *
     * @mbggenerated
     */
    private String delFlag;

    /**
     * 添加时间
     *
     * @mbggenerated
     */
    private String createtime;

    /**
     * 添加者
     *
     * @mbggenerated
     */
    private String updatetime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private String createuser;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private String updateuser;

    /**
     * 交易日期
     *
     * @mbggenerated
     */
    private Integer txDate;

    /**
     * 交易时间
     *
     * @mbggenerated
     */
    private Integer txTime;

    /**
     * 交易流水号
     *
     * @mbggenerated
     */
    private String seqNo;

    /**
     * 交易渠道
     *
     * @mbggenerated
     */
    private String channel;

    /**
     * 平台 0PC 1微官网 2Android 3IOS 4其他
     *
     * @mbggenerated
     */
    private Integer client;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid == null ? null : ordid.trim();
    }

    public String getCmdid() {
        return cmdid;
    }

    public void setCmdid(String cmdid) {
        this.cmdid = cmdid == null ? null : cmdid.trim();
    }

    public String getResptype() {
        return resptype;
    }

    public void setResptype(String resptype) {
        this.resptype = resptype == null ? null : resptype.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode == null ? null : respcode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public Integer getTxDate() {
        return txDate;
    }

    public void setTxDate(Integer txDate) {
        this.txDate = txDate;
    }

    public Integer getTxTime() {
        return txTime;
    }

    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }
}