package com.hyjf.am.trade.service.admin;

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
}
