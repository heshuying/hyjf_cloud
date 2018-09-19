package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
public class AdminBorrowFlowResponse extends Response<HjhAssetBorrowTypeVO> {
    private int total;
    // 项目列表
    List<BorrowProjectTypeVO> borrowProjectTypeList;
    // 资金来源
    List<HjhInstConfigVO> hjhInstConfigList;
    // 产品类型
    List<HjhAssetTypeVO> assetTypeList;
    // 状态
    List<ParamNameVO> statusList;

    public List<BorrowProjectTypeVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<BorrowProjectTypeVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public List<HjhAssetTypeVO> getAssetTypeList() {
        return assetTypeList;
    }

    public void setAssetTypeList(List<HjhAssetTypeVO> assetTypeList) {
        this.assetTypeList = assetTypeList;
    }

    public List<ParamNameVO> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ParamNameVO> statusList) {
        this.statusList = statusList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
