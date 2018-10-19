package com.hyjf.am.config.service;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.DebtConfigLogResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
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
    public void updateDebtConfig(DebtConfigRequest req) ;

    /**
     * 债权配置日志表查询总条数
     *
     */
    IntegerResponse countDebtConfigLogTotal();


    DebtConfigLogResponse getDebtConfigLogList(DebtConfigRequest form);

    }
