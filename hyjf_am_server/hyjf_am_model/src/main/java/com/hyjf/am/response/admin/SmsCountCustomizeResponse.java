/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fq
 * @version SmsCountCustomizeResponse, v0.1 2018/8/20 16:14
 */
public class SmsCountCustomizeResponse extends Response<SmsCountCustomizeVO> {
    private int count;

    @ApiModelProperty(name = "总金额")
    private String sumMoney;

    @ApiModelProperty(name = "总条数")
    private int sumCount;

    List<OADepartmentCustomizeVO> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OADepartmentCustomizeVO> getList() {
        return list;
    }

    public void setList(List<OADepartmentCustomizeVO> list) {
        this.list = list;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getSumCount() {
        return sumCount;
    }

    public void setSumCount(int sumCount) {
        this.sumCount = sumCount;
    }
}
