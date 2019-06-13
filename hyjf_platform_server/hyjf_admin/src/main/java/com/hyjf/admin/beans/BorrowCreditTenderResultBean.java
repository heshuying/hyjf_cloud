package com.hyjf.admin.beans;


import com.hyjf.admin.beans.vo.DropDownVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 承接信息返回实体bean
 * @author zhangyk
 * @date 2018/7/12 21:06
 */
public class BorrowCreditTenderResultBean implements Serializable {


    private List<BorrowCreditTenderBean> recordList;

    private Map<String,Object> sumData;

    List<DropDownVO> creditendStateList;

    private Integer total;

    public List<BorrowCreditTenderBean> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditTenderBean> recordList) {
        this.recordList = recordList;
    }

    public Map<String, Object> getSumData() {
        return sumData;
    }

    public void setSumData(Map<String, Object> sumData) {
        this.sumData = sumData;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DropDownVO> getCreditendStateList() {
        return creditendStateList;
    }

    public void setCreditendStateList(List<DropDownVO> creditendStateList) {
        this.creditendStateList = creditendStateList;
    }
}
