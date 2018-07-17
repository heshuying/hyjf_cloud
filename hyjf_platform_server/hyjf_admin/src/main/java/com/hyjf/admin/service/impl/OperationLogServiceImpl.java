package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.OperationLogClient;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.resquest.admin.AdminOperationLogRequest;
import com.hyjf.am.vo.admin.FeerateModifyLogVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
    private OperationLogClient operationLogClient;
    @Autowired
    public AmTradeClient amTradeClient;
    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    @Override
    public AdminOperationLogResponse selectOperationLogListByPage(Map<String, Object> map){
        AdminOperationLogResponse response = new AdminOperationLogResponse();
        // 资产来源  inst_code机构编号 inst_name机构名称
        List<HjhInstConfigVO> hjhInstConfigList = amTradeClient.findHjhInstConfigList();
        //产品类型   asset_type  asset_type_name资产类型名称
        List<HjhAssetTypeVO> hjhAssetTypes = this.operationLogClient.getHjhAssetType();
        response.setHjhInstConfigList(hjhInstConfigList);
        response.setHjhAssetTypes(hjhAssetTypes);
        AdminOperationLogResponse res=operationLogClient.selectOperationLogListByPage(map);
        BeanUtils.copyProperties(res,response);
        return response;
    }

    /**
     * 配置中心操作日志配置-导出查询
     * @param map
     * @return
     */
    @Override
    public List<FeerateModifyLogVO> selectOperationLogListExport(Map<String, Object> map, int limitStart, int limitEnd){
        return operationLogClient.selectOperationLogListExport(map,limitStart,limitEnd);
    }
}
