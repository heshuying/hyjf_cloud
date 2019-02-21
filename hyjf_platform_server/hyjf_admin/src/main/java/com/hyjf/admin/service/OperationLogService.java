package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminOperationLogResponse;
import com.hyjf.am.response.message.UserOperationLogResponse;
import com.hyjf.am.resquest.admin.UserOperationLogRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface OperationLogService {

    /**
     * 查询配置中心操作日志配置
     * @param map
     * @return
     */
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map, int limitStart, int limitEnd);

    /**
     * 获取资产来源
     *
     * @return
     */
    public List<HjhInstConfigVO> getHjhInstConfig();


    /**
     * 获取资产类型列表
     *
     * @return
     */
    public List<HjhAssetTypeVO> getHjhAssetType();

    /**
     * 查询会员操作日志列表
     * @param request
     * @return
     */
    UserOperationLogResponse  getOperationLogList(UserOperationLogRequest request);

    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode);
}
