package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface OperationLogClient {

    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map,int limitStart, int limitEnd);
    /**
     * 产品类型   asset_type  asset_type_name资产类型名称
     *
     * @param
     * @return List<HjhAssetTypeVO>
     */
    List<HjhAssetTypeVO> getHjhAssetType();
}
