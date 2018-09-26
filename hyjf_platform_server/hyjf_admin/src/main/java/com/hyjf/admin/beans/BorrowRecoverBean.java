package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRecoverBean  {

    private List<DropDownVO> loanStarusList;
    private List<HjhInstConfigVO> hjhInstConfigList;
    private BorrowRecoverCustomizeVO sumAccount;
    private List<BorrowRecoverCustomizeVO> recordList;
    private Integer total;

    public List<DropDownVO> getLoanStarusList() {
        return loanStarusList;
    }

    public void setLoanStarusList(List<DropDownVO> loanStarusList) {
        this.loanStarusList = loanStarusList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }
    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return this.hjhInstConfigList ;
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
