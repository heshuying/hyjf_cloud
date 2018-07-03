package com.hyjf.admin.beans;

import com.hyjf.admin.Utils.Page;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRecoverBean  {

    private Map<String, String> loanStarusList;
    private List<HjhInstConfigVO> hjhInstConfigList;
    private BorrowRecoverCustomizeVO sumAccount;
    private List<BorrowRecoverCustomizeVO> recordList;
    private Integer total;

    public Map<String, String> getLoanStarusList() {
        return loanStarusList;
    }

    public void setLoanStarusList(Map<String, String> loanStarusList) {
        this.loanStarusList = loanStarusList;
    }

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public BorrowRecoverCustomizeVO getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(BorrowRecoverCustomizeVO sumAccount) {
        this.sumAccount = sumAccount;
    }

    public List<BorrowRecoverCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRecoverCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
