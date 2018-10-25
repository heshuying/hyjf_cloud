package com.hyjf.am.resquest.admin;

import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tanyy
 * @date 2018/08/09 17:00
 * @version V1.0  
 */
public class DebtConfigRequest implements Serializable {

    private Integer id;

    private Integer hyjfDebtConfigId;

    private BigDecimal attornRate;

    private BigDecimal concessionRateUp;

    private BigDecimal concessionRateDown;

    private Integer toggle;

    private String closeDes;

    private Integer updateUser;

    private String updateUsername;

    private Date updateTime;

    private String ipAddress;

    private String macAddress;

    @ApiModelProperty("列表画面自定义标签上的用翻页对象")
    private Paginator paginator;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private int currPage;
    /**
     * 翻页机能用的隐藏变量
     */
    @ApiModelProperty("翻页机能用的隐藏变量")
    private int paginatorPage = 1;

    @ApiModelProperty("开始页")
    private int limitStart;

    @ApiModelProperty("结束页")
    private int limitEnd;
    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private int pageSize = 10;
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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHyjfDebtConfigId() {
        return hyjfDebtConfigId;
    }

    public void setHyjfDebtConfigId(Integer hyjfDebtConfigId) {
        this.hyjfDebtConfigId = hyjfDebtConfigId;
    }

    public BigDecimal getAttornRate() {
        return attornRate;
    }

    public void setAttornRate(BigDecimal attornRate) {
        this.attornRate = attornRate;
    }

    public BigDecimal getConcessionRateUp() {
        return concessionRateUp;
    }

    public void setConcessionRateUp(BigDecimal concessionRateUp) {
        this.concessionRateUp = concessionRateUp;
    }

    public BigDecimal getConcessionRateDown() {
        return concessionRateDown;
    }

    public void setConcessionRateDown(BigDecimal concessionRateDown) {
        this.concessionRateDown = concessionRateDown;
    }

    public Integer getToggle() {
        return toggle;
    }

    public void setToggle(Integer toggle) {
        this.toggle = toggle;
    }

    public String getCloseDes() {
        return closeDes;
    }

    public void setCloseDes(String closeDes) {
        this.closeDes = closeDes == null ? null : closeDes.trim();
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername == null ? null : updateUsername.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress == null ? null : macAddress.trim();
    }

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
}
