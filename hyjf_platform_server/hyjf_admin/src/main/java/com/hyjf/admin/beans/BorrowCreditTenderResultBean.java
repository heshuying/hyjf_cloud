package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditTenderVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 承接信息返回实体bean
 * @author zhangyk
 * @date 2018/7/12 21:06
 */
public class BorrowCreditTenderResultBean implements Serializable {


    private List<BorrowCreditTenderVO> recordList;

    private Map<String,Object> sumData;

    private Integer total;

    public List<BorrowCreditTenderVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditTenderVO> recordList) {
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
}
