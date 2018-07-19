package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.OperationLogClient;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class OperationLogClientImpl implements OperationLogClient {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    @Override
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map, int limitStart, int limitEnd){
        AdminOperationLogResponse amConfigResponse = restTemplate.postForEntity("http://AM-CONFIG/am-config/config/operationlog/list",map,AdminOperationLogResponse.class).getBody();
        List<FeerateModifyLogVO> configList = amConfigResponse.getResultList();
        //查询 资产来源 instCode 和 assetType的值
        AdminOperationLogRequest adminRequest = new AdminOperationLogRequest();
        if(!CollectionUtils.isEmpty(configList)) {
            List<String> instCodes = new ArrayList<>(configList.size());
            List<Integer> assertTypes = new ArrayList<>(configList.size());
            for (int i=0;i<configList.size();i++){
                instCodes.add(configList.get(i).getInstCode());
                assertTypes.add(configList.get(i).getAssetType());
            }
            adminRequest.setInstCodes(instCodes);
            adminRequest.setAssetTypes(assertTypes);
            List<FeerateModifyLogVO> tradeList = restTemplate.postForEntity("http://AM-TRADE/am-trade/config/operationlog/selectInstAndAssertType", adminRequest, List.class).getBody();
            if (!CollectionUtils.isEmpty(configList)) {
                for (int i = 0; i < configList.size(); i++) {
                    FeerateModifyLogVO vO = configList.get(i);
                    for (int j = 0; j < tradeList.size(); j++) {
                        if (vO.getInstCode().equals(tradeList.get(i).getInstCode()) && vO.getAssetType().equals(tradeList.get(i).getAssetType())) {
                            vO.setInstName(tradeList.get(i).getInstName());
                            vO.setAssetTypeName(tradeList.get(i).getAssetTypeName());
                        }
                    }

                }
                amConfigResponse.setResultList(configList);
            }
        }
        return amConfigResponse;
    }
    /**
     * 产品类型   asset_type  asset_type_name资产类型名称
     *
     * @param
     * @return List<HjhAssetTypeVO>
     */
    @Override
    public List<HjhAssetTypeVO> getHjhAssetType(){
        String url = "http://AM-TRADE/am-trade/config/operationlog/getHjhAssetType";
        return restTemplate.getForEntity(url,List.class).getBody();
    }

}
