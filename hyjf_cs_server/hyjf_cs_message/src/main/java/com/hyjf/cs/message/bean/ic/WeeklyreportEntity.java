package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lisheng
 * @version WeeklyreportEntity, v0.1 2018/7/27 15:51
 */
@Document(collection = "ht_weeklyreport")
public class WeeklyreportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer userId;

    private String beginDate;

    private String endDate;

    private BigDecimal zongshouyi;

    private Integer baifenbi;

    private Integer zongtianshu;

    private BigDecimal zongjine;

    private BigDecimal touzie;

    private Integer bishu;

    private BigDecimal huankuanzonge;

    private BigDecimal shouyi;

    private Integer youhuiquan;

    private String huankuangaikuang;

    private String touzigaikuang;

    private String datestring;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public BigDecimal getZongshouyi() {
        return zongshouyi;
    }

    public void setZongshouyi(BigDecimal zongshouyi) {
        this.zongshouyi = zongshouyi;
    }

    public Integer getBaifenbi() {
        return baifenbi;
    }

    public void setBaifenbi(Integer baifenbi) {
        this.baifenbi = baifenbi;
    }

    public Integer getZongtianshu() {
        return zongtianshu;
    }

    public void setZongtianshu(Integer zongtianshu) {
        this.zongtianshu = zongtianshu;
    }

    public BigDecimal getZongjine() {
        return zongjine;
    }

    public void setZongjine(BigDecimal zongjine) {
        this.zongjine = zongjine;
    }

    public BigDecimal getTouzie() {
        return touzie;
    }

    public void setTouzie(BigDecimal touzie) {
        this.touzie = touzie;
    }

    public Integer getBishu() {
        return bishu;
    }

    public void setBishu(Integer bishu) {
        this.bishu = bishu;
    }

    public BigDecimal getHuankuanzonge() {
        return huankuanzonge;
    }

    public void setHuankuanzonge(BigDecimal huankuanzonge) {
        this.huankuanzonge = huankuanzonge;
    }

    public BigDecimal getShouyi() {
        return shouyi;
    }

    public void setShouyi(BigDecimal shouyi) {
        this.shouyi = shouyi;
    }

    public Integer getYouhuiquan() {
        return youhuiquan;
    }

    public void setYouhuiquan(Integer youhuiquan) {
        this.youhuiquan = youhuiquan;
    }

    public String getHuankuangaikuang() {
        return huankuangaikuang;
    }

    public void setHuankuangaikuang(String huankuangaikuang) {
        this.huankuangaikuang = huankuangaikuang == null ? null : huankuangaikuang.trim();
    }

    public String getTouzigaikuang() {
        return touzigaikuang;
    }

    public void setTouzigaikuang(String touzigaikuang) {
        this.touzigaikuang = touzigaikuang == null ? null : touzigaikuang.trim();
    }

    public String getDatestring() {
        return datestring;
    }

    public void setDatestring(String datestring) {
        this.datestring = datestring == null ? null : datestring.trim();
    }
}
