package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.OperationLogService;
import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.response.message.UserOperationLogResponse;
import com.hyjf.am.resquest.admin.UserOperationLogRequest;
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
    public AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private CsMessageClient csMessageClient;
    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    @Override
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map, int limitStart, int limitEnd){
        AdminOperationLogResponse amConfigResponse = amConfigClient.selectOperationLogList(map);
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


    @Override
    public UserOperationLogResponse  getOperationLogList(UserOperationLogRequest request)
    {
        return csMessageClient.getOperationLogList(request);
    }

    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    @Override
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode){
        return amTradeClient.hjhAssetTypeList(instCode);
    }
}
