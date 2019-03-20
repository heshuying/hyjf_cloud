package com.hyjf.admin.service.impl.vip.content;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.vip.content.OperService;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by future on 2019/3/18.
 */
@Service
public class OperServiceImpl implements OperService {

    private static final Logger logger = LoggerFactory.getLogger(OperServiceImpl.class);

    @Autowired
    private AmAdminClient amAdminClient;

    /**
     *大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @Override
    public List<ScreenConfigVO> operList(ScreenConfigRequest request) {
        return amAdminClient.getScreenConfigList(request);
    }

    /**
     *大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @Override
    public int operAdd(ScreenConfigVO screenConfigVO) {
        return amAdminClient.addScreenConfig(screenConfigVO);
    }

    /**
     *大屏运营部数据配置数据编辑/启用/禁用
     * @param screenConfigVO
     * @return
     */
    @Override
    public int operUpdate(ScreenConfigVO screenConfigVO) {
        return amAdminClient.updateScreenConfig(screenConfigVO);
    }

    /**
     * 坐席月任务配置列表查询
     * @param request
     * @return
     */
    @Override
    public List<CustomerTaskConfigVO> taskList(CustomerTaskConfigRequest request) {
        return amAdminClient.getCustomerTaskConfigList(request);
    }

    /**
     * 坐席月任务配置数据新增
     * @return
     */
    @Override
    public int taskAdd(CustomerTaskConfigVO customerTaskConfigVO) {
        return amAdminClient.addCustomerTaskConfig(customerTaskConfigVO);
    }

    /**
     * 坐席月任务配置数据编辑/启用/禁用
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public int taskUpdate(CustomerTaskConfigVO customerTaskConfigVO) {
        return amAdminClient.updateCustomerTaskConfig(customerTaskConfigVO);
    }

    /**
     * 添加/编辑重复检验
     * @param request
     * @return
     */
    @Override
    public Map saveCheck(CustomerTaskConfigRequest request) {
        Map resultMap = new HashMap<String, Object>();
        if (this.blankCheck(7, null, null, request)){
            resultMap.put("status", "3");
            resultMap.put("statusDesc", "必传字段未传");
            return resultMap;
        }
        boolean resultFlag = false;
        if(1 == request.getFlag()){
            if (this.blankCheck(8, null, null, request)){
                resultMap.put("status", "3");
                resultMap.put("statusDesc", "必传字段未传");
                return resultMap;
            }
            ScreenConfigRequest screenConfigRequest = new ScreenConfigRequest();
            screenConfigRequest.setTaskTime(request.getTaskTime());
            List<ScreenConfigVO> result = this.operList(screenConfigRequest);
            logger.info("返回值为:{}", JSON.toJSONString(result));
            if (null != result){
                resultFlag = true;
            }
        }else {
            if (this.blankCheck(9, null, null, request)){
                resultMap.put("status", "3");
                resultMap.put("statusDesc", "必传字段未传");
                return resultMap;
            }
            List<CustomerTaskConfigVO> result = this.taskList(request);
            if (null != result){
                resultFlag = true;
            }
        }
        resultMap.put("result", resultFlag);
        resultMap.put("status", BaseResult.SUCCESS);
        resultMap.put("statusDesc", BaseResult.SUCCESS_DESC);
        return resultMap;
    }

    /**
     * 数据非空校验
     * @param flag
     * @param screenConfigVO
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public boolean blankCheck(int flag, ScreenConfigVO screenConfigVO, CustomerTaskConfigVO customerTaskConfigVO, CustomerTaskConfigRequest request) {
        boolean resultFlag = false;
        switch (flag) {
            case 1:
                if(null == screenConfigVO ||
                        StringUtils.isBlank(screenConfigVO.getTaskTime()) ||
                        null == screenConfigVO.getNewPassengerGoal() ||
                        null == screenConfigVO.getOldPassengerGoal() ||
                        null == screenConfigVO.getOperationalGoal() ||
                        null == screenConfigVO.getCreateUserId()){
                    resultFlag = true;
                }
                return resultFlag;
            case 2:
                if(null == screenConfigVO ||
                        StringUtils.isBlank(screenConfigVO.getTaskTime()) ||
                        null == screenConfigVO.getNewPassengerGoal() ||
                        null == screenConfigVO.getOldPassengerGoal() ||
                        null == screenConfigVO.getOperationalGoal() ||
                        null == screenConfigVO.getUpdateUserId()||
                        null == screenConfigVO.getId()){
                    resultFlag = true;
                }
                return resultFlag;
            case 3:
                if(null == screenConfigVO ||
                        null == screenConfigVO.getUpdateUserId()||
                        null == screenConfigVO.getStatus() ||
                        null == screenConfigVO.getId()){
                    resultFlag = true;
                }
                return resultFlag;
            case 4:
                if(null == customerTaskConfigVO ||
                        StringUtils.isBlank(customerTaskConfigVO.getCustomerName()) ||
                        StringUtils.isBlank(customerTaskConfigVO.getTaskTime()) ||
                        null == customerTaskConfigVO.getCustomerGroup() ||
                        null == customerTaskConfigVO.getCreateUserId() ||
                        null == customerTaskConfigVO.getMonthGoal()
                        ){
                    resultFlag = true;
                }
                return resultFlag;
            case 5:
                if(null == customerTaskConfigVO ||
                        StringUtils.isBlank(customerTaskConfigVO.getCustomerName()) ||
                        StringUtils.isBlank(customerTaskConfigVO.getTaskTime()) ||
                        null == customerTaskConfigVO.getCustomerGroup() ||
                        null == customerTaskConfigVO.getUpdateUserId() ||
                        null == customerTaskConfigVO.getMonthGoal() ||
                        null == customerTaskConfigVO.getId()
                        ){
                    resultFlag = true;
                }
                return resultFlag;
            case 6:
                if(null == customerTaskConfigVO ||
                        null == customerTaskConfigVO.getUpdateUserId()||
                        null == customerTaskConfigVO.getStatus() ||
                        null == customerTaskConfigVO.getId()){
                    resultFlag = true;
                }
                return resultFlag;
            case 7:
                if(null == request || null == request.getFlag()){
                    resultFlag = true;
                }
                return resultFlag;
            case 8:
                if(null == request ||
                        StringUtils.isBlank(request.getTaskTime())){
                    resultFlag = true;
                }
                return resultFlag;
            case 9:
                if(null == request ||
                        StringUtils.isBlank(request.getCustomerName()) ||
                        StringUtils.isBlank(request.getTaskTime())){
                    resultFlag = true;
                }
                return resultFlag;
            default:
                break;
        }
        return resultFlag;
    }
}
