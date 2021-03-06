package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.DebtConfigService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigLogVO;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<DebtConfigVO> getDebtConfig() {
        DebtConfigResponse response = amConfigClient.getDebtConfig();
        return response.getResultList();
    }


    /**
     * 修改债转配置表配置
     *
     * @param record
     */
    @Override
    public DebtConfigResponse updateDebtConfig(DebtConfigRequest record){
        return amConfigClient.updateDebtConfig(record);
    }
    @Override
    public int countDebtConfigLogTotal(){
        return amConfigClient.countDebtConfigLogTotal();

    }
    @Override
    public List<DebtConfigLogVO> getDebtConfigLogList(DebtConfigRequest req){
        Page page = Page.initPage(req.getCurrPage(), req.getPageSize());
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        return amConfigClient.getDebtConfigLogList(req);

    }


}
