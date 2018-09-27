package com.hyjf.cs.trade.service.assetmanage.impl;

import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.vo.config.DebtConfigVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.service.assetmanage.DebtConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DebtConfigServiceImpl implements DebtConfigService {



    @Autowired
    private AmConfigClient amConfigClient;


    /**
     * 查询债转配置表配置
     *
     * @param
     */
    @Override
    public DebtConfigVO getDebtConfig() {
        DebtConfigResponse response = amConfigClient.getDebtConfig();
        return response.getResult();
    }
}
