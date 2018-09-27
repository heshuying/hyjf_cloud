package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fuqiang
 * 指定类型投资项目
 */
public class AppProjectListCsVO  extends BaseVO implements Serializable {
    private static final long serialVersionUID = -2592366387227548926L;

    //项目id
    private String borrowNid;
    //项目标题
    private String borrowName;
    //项目类型描述
    private String borrowDesc;
    //项目类型
    private String borrowType;
    //标的第一项
    private String borrowTheFirst;
    // 标的第一项描述
    private String borrowTheFirstDesc;
    // 标的第二项
    private String borrowTheSecond;
    // 标的第二项描述
    private String borrowTheSecondDesc;
    // 标的的第三项
    private String borrowTheThird;
    // 标的的第三项描述
    private String borrowTheThirdDesc;
    //计划状态名称
    private String statusName;
    // 计划状态名字描述
    private String statusNameDesc;
    //项目详情H5的url
    private String borrowUrl;
    //项目状态
    private String status;
    //开标时间
    private String onTime;
    //标记有空字符串，尊享，优选
    private String mark;
    /**
     * 产品加息率
     */
    private String borrowExtraYield;
    public String getBorrowTheThird() {
        return this.borrowTheThird;
    }

    public void setBorrowTheThird(String borrowTheThird) {
        this.borrowTheThird = borrowTheThird;
    }

    public String getBorrowTheThirdDesc() {
        return this.borrowTheThirdDesc;
    }

    public void setBorrowTheThirdDesc(String borrowTheThirdDesc) {
        this.borrowTheThirdDesc = borrowTheThirdDesc;
    }

    public AppProjectListCsVO() {
        super();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowDesc() {
        return borrowDesc;
    }

    public void setBorrowDesc(String borrowDesc) {
        this.borrowDesc = borrowDesc;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowTheFirst() {
        return borrowTheFirst;
    }

    public void setBorrowTheFirst(String borrowTheFirst) {
        this.borrowTheFirst = borrowTheFirst;
    }

    public String getBorrowTheFirstDesc() {
        return borrowTheFirstDesc;
    }

    public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
        this.borrowTheFirstDesc = borrowTheFirstDesc;
    }

    public String getBorrowTheSecond() {
        return borrowTheSecond;
    }

    public void setBorrowTheSecond(String borrowTheSecond) {
        this.borrowTheSecond = borrowTheSecond;
    }

    public String getBorrowTheSecondDesc() {
        return borrowTheSecondDesc;
    }

    public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
        this.borrowTheSecondDesc = borrowTheSecondDesc;
    }

    public String getStatusNameDesc() {
        return statusNameDesc;
    }

    public void setStatusNameDesc(String statusNameDesc) {
        this.statusNameDesc = statusNameDesc;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(String borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }
}
