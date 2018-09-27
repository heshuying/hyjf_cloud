package com.hyjf.am.config.service;

import com.hyjf.am.vo.config.DebtConfigVO;

import java.util.List;

public interface DebtConfigService {


    /**
     * 查询债转配置表配置
     * @param
     */
    List<DebtConfigVO> getDebtConfig();


}
