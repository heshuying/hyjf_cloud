/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigResponse, v0.1 2018/6/19 18:35
 */
public class CouponConfigResponse extends Response<CouponConfigVO> {
    private int count;

    private String selectedClientDisplay;

    private String selectedProjectDisplay;

    private List<ParamNameVO> clients;

    private List<ParamNameVO> expType;

    private List<BorrowProjectTypeVO> projectTypes;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSelectedClientDisplay() {
        return selectedClientDisplay;
    }

    public void setSelectedClientDisplay(String selectdeClientDisplay) {
        this.selectedClientDisplay = selectdeClientDisplay;
    }

    public String getSelectedProjectDisplay() {
        return selectedProjectDisplay;
    }

    public void setSelectedProjectDisplay(String selectedProjectDisplay) {
        this.selectedProjectDisplay = selectedProjectDisplay;
    }

    public List<ParamNameVO> getClients() {
        return clients;
    }

    public void setClients(List<ParamNameVO> clients) {
        this.clients = clients;
    }

    public List<ParamNameVO> getExpType() {
        return expType;
    }

    public void setExpType(List<ParamNameVO> expType) {
        this.expType = expType;
    }

    public List<BorrowProjectTypeVO> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(List<BorrowProjectTypeVO> projectTypes) {
        this.projectTypes = projectTypes;
    }
}
