package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.OperationLogClient;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
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
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map, int limitStart, int limitEnd){
        return operationLogClient.selectOperationLogList(map,limitStart,limitEnd);
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
        return this.operationLogClient.getHjhAssetType();
    }
}
