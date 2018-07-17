package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.OperationLogClient;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    public AdminOperationLogResponse selectOperationLogListByPage(Map<String, Object> map){
        String url = "http://AM-CONFIG/am-config/config/operationlog/list";
        AdminOperationLogResponse response = restTemplate.postForEntity(url,map,AdminOperationLogResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 配置中心操作日志配置-导出查询
     * @param map
     * @return
     */
    @Override
    public List<FeerateModifyLogVO> selectOperationLogListExport(Map<String, Object> map, int limitStart, int limitEnd){
        String url = "http://AM-CONFIG/am-config/config/operationlog/export";
        List<FeerateModifyLogVO> response = restTemplate.postForEntity(url,map,List.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
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
        List<HjhAssetTypeVO> response = restTemplate.getForEntity(url,List.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

}
