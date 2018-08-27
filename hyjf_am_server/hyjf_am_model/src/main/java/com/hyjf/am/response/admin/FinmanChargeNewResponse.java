package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author xiehuili on 2018/8/13.
 */
public class FinmanChargeNewResponse extends Response<BorrowFinmanNewChargeVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
    // 汇直投项目列表
    public List<BorrowProjectTypeVO> borrowProjectTypeList;

    public List<ParamNameVO>   paramNames;

    // 资金来源
    public List<HjhInstConfigVO> hjhInstConfigList;

    // 产品类型
    public List<HjhAssetTypeVO> assetTypeList;

    public List<BorrowProjectTypeVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<BorrowProjectTypeVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<ParamNameVO> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<ParamNameVO> paramNames) {
        this.paramNames = paramNames;
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
}
