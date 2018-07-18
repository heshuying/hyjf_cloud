package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.resquest.admin.AdminOperationLogRequest;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/18.
 */
public interface OperationLogCustomizeMapper {

    /**
     * 查询 资产来源 instCode 和 assetType的值
     * @return
     */
    public List<String> selectInstCodeList(AdminOperationLogRequest adminRequest);
    /**
     * 查询 资产来源 instCode 和 assetType的值
     * @return
     */
    public List<String> selectAssertTypeList(AdminOperationLogRequest adminRequest);
}
