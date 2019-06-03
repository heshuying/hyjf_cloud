package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.SmsCountCustomizeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/5/8 11:51
 * @Version 1.0
 */
public class ListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> list;

    private List<SmsCountCustomizeVO> smsCountCustomizeVOList;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<SmsCountCustomizeVO> getSmsCountCustomizeVOList() {
        return smsCountCustomizeVOList;
    }

    public void setSmsCountCustomizeVOList(List<SmsCountCustomizeVO> smsCountCustomizeVOList) {
        this.smsCountCustomizeVOList = smsCountCustomizeVOList;
    }
}
