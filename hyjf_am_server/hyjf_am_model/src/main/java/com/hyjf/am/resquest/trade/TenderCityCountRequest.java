package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;

import java.util.Date;

/**
 * @author：yinhui
 * @Date: 2018/9/1  13:44
 */
public class TenderCityCountRequest  extends Request {

    public Date createTime;

    //年龄下限
    public int firstAge;

    //年龄上限
    public int endAge;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getFirstAge() {
        return firstAge;
    }

    public void setFirstAge(int firstAge) {
        this.firstAge = firstAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }
}
