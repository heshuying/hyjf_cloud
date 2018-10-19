package com.hyjf.admin.service;

import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigLogVO;
import com.hyjf.am.vo.config.DebtConfigVO;

import java.util.List;

public interface DebtConfigService {


    /**
     * 查询债转配置表配置
     * @param
     */
    List<DebtConfigVO> getDebtConfig();

    /**
     * 修改债转配置表配置
     * @param  req
     */
     DebtConfigResponse updateDebtConfig(DebtConfigRequest req) ;

    /**
     * 债权配置日志表查询总条数
     *
     */
    int countDebtConfigLogTotal();

    /**
     * 债权配置日志表查询
     *
     */
    public List<DebtConfigLogVO> getDebtConfigLogList(DebtConfigRequest req);

}
