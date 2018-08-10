package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    public AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    @Override
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map, int limitStart, int limitEnd){
        AdminOperationLogResponse amConfigResponse = amConfigClient.selectOperationLogList(map);
        List<FeerateModifyLogVO> configList = amConfigResponse.getResultList();
        //查询 资产来源 instCode 和 assetType的值
        AdminOperationLogRequest adminRequest = new AdminOperationLogRequest();
        List<FeerateModifyLogVO> tradeList = amTradeClient.selectInstAndAssertType(adminRequest);
        if (!CollectionUtils.isEmpty(tradeList)) {
            for (int i = 0; i < configList.size(); i++) {
                FeerateModifyLogVO vO = configList.get(i);
                for (int j = 0; j < tradeList.size(); j++) {
                    if (vO.getInstCode().equals(tradeList.get(j).getInstCode())) {
                        vO.setInstName(tradeList.get(j).getInstName());
                        if(vO.getAssetType().equals(tradeList.get(j).getAssetType())){
                            vO.setAssetTypeName(tradeList.get(j).getAssetTypeName());
                        }
                    }
                }

            }
            amConfigResponse.setResultList(configList);
        }
        return amConfigResponse;
    }

    /**
     * 获取资产来源
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> getHjhInstConfig(){
        // 资产来源  inst_code机构编号 inst_name机构名称
        return amTradeClient.findHjhInstConfigList();
    }


    /**
     * 获取资产类型列表
     *
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> getHjhAssetType(){
        return this.amTradeClient.getHjhAssetType();
    }
}
