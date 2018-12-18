package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author : huanghui
 */
public class WebUserRepayTransferCustomizeVO extends BaseVO implements Serializable {
    // 请求处理是否成功
    private boolean status = false;

    /** 出让人ID */
    private Integer creditUserId;

    /** 出让人用户名 */
    private String creditUserName;

    /** 承接人用户名 */
    private String undertakerUserName;

    /** 承接日期 */
    private String assignOrderDate;

    /** 承接金额 */
    private BigDecimal assignCapital;

    /** 承接金额 格式化后的*/
    private String assignCapitalString;

    // web服务地址
    private String host;

    private List<WebUserRepayTransferCustomizeVO> projectList;

    // 分页信息
    private Paginator paginator;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getUndertakerUserName() {
        return undertakerUserName;
    }

    public void setUndertakerUserName(String undertakerUserName) {
        this.undertakerUserName = undertakerUserName;
    }

    public String getAssignOrderDate() {
        return assignOrderDate;
    }

    public void setAssignOrderDate(String assignOrderDate) {
        this.assignOrderDate = assignOrderDate;
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignCapitalString() {
        return assignCapitalString;
    }

    public void setAssignCapitalString(String assignCapitalString) {
        this.assignCapitalString = assignCapitalString;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
