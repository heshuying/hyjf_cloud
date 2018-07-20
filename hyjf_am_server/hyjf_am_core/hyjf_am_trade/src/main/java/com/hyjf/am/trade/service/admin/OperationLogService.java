package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface OperationLogService {


    /**
     * 产品类型
     * @return
     */
    public List<HjhAssetTypeVO> getHjhAssetType() ;
    /**
     * 查询 资产来源 instCode 和 assetType的值
     * @return
     */
    public List<FeerateModifyLogVO> selectInstAndAssertType(AdminOperationLogRequest adminRequest);
}
