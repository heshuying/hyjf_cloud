

package com.hyjf.am.vo.trade.hjh;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.common.util.CustomConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 
 * 资产管理用户投资计划包含项目
 */
public class UserHjhInvistListCustomizeVO extends BaseVO {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 5748630051215873837L;
	// 标的自动投标订单号
	private String nid;
	// 项目编号(原标标号)
    private String borrowNid;
    // 投资金额
    private String account;
    // 计划加入订单号
    private String accedeOrderId;
    // 项目期限
    private String borrowPeriod;
    // 投资时间
    private String addTime;
    // 项目回款时间
    private String recoverTime;
    // 项目类型
    private String type;
    
    // new added
	// 债转编号
    private String creditNid;
	// 债转投标单号
    private String investOrderId;
    // 认购单号
    private String assignOrderId;
    
    
    /**法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功*/
    private int fddStatus;
    
    
    public int getFddStatus() {
        return fddStatus;
    }
    public void setFddStatus(int fddStatus) {
        this.fddStatus = fddStatus;
    }
    
    public String getNid() {
        return nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getAccedeOrderId() {
        return accedeOrderId;
    }
    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getRecoverTime() {
        return recoverTime;
    }
    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
	public String getCreditNid() {
		return creditNid;
	}
	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}
	public String getInvestOrderId() {
		return investOrderId;
	}
	public void setInvestOrderId(String investOrderId) {
		this.investOrderId = investOrderId;
	}
	public String getAssignOrderId() {
		return assignOrderId;
	}
	public void setAssignOrderId(String assignOrderId) {
		this.assignOrderId = assignOrderId;
	}
	
}
