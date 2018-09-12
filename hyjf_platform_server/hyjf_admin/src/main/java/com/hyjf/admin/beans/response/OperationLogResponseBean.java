package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2018/9/6.
 */
public class OperationLogResponseBean  extends Response<FeerateModifyLogVO> {

    // 资产来源  inst_code机构编号 inst_name机构名称
    private List<HjhInstConfigVO> hjhInstConfigList;
    //产品类型   asset_type  asset_type_name资产类型名称
    private List<HjhAssetTypeVO> hjhAssetTypes;
    //修改类型
    private List<Map<String,String>> updateTypes;
    private List<DropDownVO> updateType;
    private int recordTotal;

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public List<HjhAssetTypeVO> getHjhAssetTypes() {
        return hjhAssetTypes;
    }

    public List<Map<String, String>> getUpdateTypes() {
        return updateTypes;
    }

    public void setUpdateTypes(List<Map<String, String>> updateTypes) {
        this.updateTypes = updateTypes;
    }

    public void setHjhAssetTypes(List<HjhAssetTypeVO> hjhAssetTypes) {
        this.hjhAssetTypes = hjhAssetTypes;
    }

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public List<DropDownVO> getUpdateType() {
        return updateType;
    }

    public void setUpdateType(List<DropDownVO> updateType) {
        this.updateType = updateType;
    }

}
