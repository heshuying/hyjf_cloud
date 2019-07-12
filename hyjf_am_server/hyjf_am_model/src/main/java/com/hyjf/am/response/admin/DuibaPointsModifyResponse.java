/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyResponse, v0.1 2019/5/30 10:26
 */
public class DuibaPointsModifyResponse extends AdminResponse<DuibaPointsModifyVO> {
    private List<ParamNameVO> integralTypeList;
    private List<ParamNameVO> integralBusinessNameList;
    private List<ParamNameVO> auditStatusList;
    private List<ParamNameVO> operationTypeList;

    public List<ParamNameVO> getIntegralTypeList() {
        return integralTypeList;
    }

    public void setIntegralTypeList(List<ParamNameVO> integralTypeList) {
        this.integralTypeList = integralTypeList;
    }

    public List<ParamNameVO> getIntegralBusinessNameList() {
        return integralBusinessNameList;
    }

    public void setIntegralBusinessNameList(List<ParamNameVO> integralBusinessNameList) {
        this.integralBusinessNameList = integralBusinessNameList;
    }

    public List<ParamNameVO> getAuditStatusList() {
        return auditStatusList;
    }

    public void setAuditStatusList(List<ParamNameVO> auditStatusList) {
        this.auditStatusList = auditStatusList;
    }

    public List<ParamNameVO> getOperationTypeList() {
        return operationTypeList;
    }

    public void setOperationTypeList(List<ParamNameVO> operationTypeList) {
        this.operationTypeList = operationTypeList;
    }
}
