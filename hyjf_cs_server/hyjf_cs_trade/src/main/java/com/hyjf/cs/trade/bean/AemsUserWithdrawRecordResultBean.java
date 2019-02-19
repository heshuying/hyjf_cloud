package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.user.UserWithdrawRecordCustomizeVO;

import java.util.List;

/***
* @author Zha Daojian
* @date 2018/12/12 14:30
* @param 
* @return 
**/
public class AemsUserWithdrawRecordResultBean extends BaseResultBean {
	
	/**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 3709370958884607483L;
    private List<UserWithdrawRecordCustomizeVO> recordList;
    public List<UserWithdrawRecordCustomizeVO> getRecordList() {
        return recordList;
    }
    public void setRecordList(List<UserWithdrawRecordCustomizeVO> recordList) {
        this.recordList = recordList;
    }
    
	
	
}
