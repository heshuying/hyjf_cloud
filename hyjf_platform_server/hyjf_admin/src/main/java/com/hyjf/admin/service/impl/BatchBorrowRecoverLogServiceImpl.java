package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BatchBorrowRecoverLogService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.response.admin.BatchBorrowRecoverLogReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverLogVo;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.hyjf.admin.controller.BaseController.*;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class BatchBorrowRecoverLogServiceImpl extends BaseServiceImpl implements BatchBorrowRecoverLogService {


    @Autowired
    private AmAdminClient amAdminClient;

    /**
     * 获取批次放款的显示列表
     * @param request
     * @return
     */
    @Override
    public JSONObject queryBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {

        JSONObject jsonObject = new JSONObject();
        BatchBorrowRecoverLogReponse batchBorrowRepayReponse = amAdminClient.getBatchBorrowRecoverLogList(request);
        if (null != batchBorrowRepayReponse) {
            List<BatchBorrowRecoverLogVo> listAccountDetail = batchBorrowRepayReponse.getResultList();
            Integer recordCount = batchBorrowRepayReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                this.queryBatchCenterStatusName(listAccountDetail,request.getNameClass());
            }
            if (null != listAccountDetail) {
                jsonObject.put(STATUS, SUCCESS);
                jsonObject.put(MSG, "成功");
                jsonObject.put(TRCORD, recordCount);
                jsonObject.put(LIST, listAccountDetail);
            } else {
                JSONObject info = new JSONObject();
                info.put(MSG, "暂无符合条件数据");
                info.put(STATUS, FAIL);
                return info;
            }
        }else{
            JSONObject info = new JSONObject();
            info.put(MSG, "暂无符合条件数据");
            info.put(STATUS, FAIL);
            return info;
        }
        return jsonObject;
    }

    /**
     * 批次中心-批次还款记录导出记录总数
     * @param request
     * @return
     */
    @Override
    public int getBatchBorrowRecoverCount(BatchBorrowRecoverRequest request) {
        return amAdminClient.getBatchBorrowRecoverLogCount(request);
    }


    /**
     * 获取批次内容的显示状态描述
     * @param listAccountDetail
     */
    @Override
    public void queryBatchCenterStatusName(List<BatchBorrowRecoverLogVo> listAccountDetail, String nameClass) {
        //获取放款相关状态描述
        Map<String, String> paramNameMap = CacheUtil.getParamNameMap(nameClass);
        for (BatchBorrowRecoverLogVo vo:
             listAccountDetail) {
            vo.setStatusStr(paramNameMap.get(vo.getStatus()));
            if("0".equals(vo.getIncreaseInterestFlag())){//不加息
                vo.setIncreaseInterestFlag("不加息");
                vo.setExtraYieldStatus("");
                vo.setExtraYieldRepayStatus("");
            }else if("1".equals(vo.getIncreaseInterestFlag())){
                vo.setIncreaseInterestFlag("加息");
                if("0".equals(vo.getExtraYieldStatus())){
                    vo.setExtraYieldStatus("待放款");
                }else if("1".equals(vo.getExtraYieldStatus())){
                    vo.setExtraYieldStatus("放款完成");
                }
                if("0".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("待还款");
                }else if("1".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款完成");
                }else if("2".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款执行中");
                }else if("9".equals(vo.getExtraYieldRepayStatus())){
                    vo.setExtraYieldRepayStatus("还款失败");
                }
            }
        }
    }

    @Override
    public JSONObject initPage(String nameClass) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",SUCCESS);
        //批次状态
        Map<String, String> paramNameMap = CacheUtil.getParamNameMap(nameClass);
        List<DropDownVO> paramMapList = ConvertUtils.convertParamMapToDropDown(paramNameMap);
        if(paramNameMap != null && paramNameMap.size() > 0){
            jsonObject.put("批次状态列表","recoverStatusList");
            jsonObject.put("recoverStatusList",paramMapList);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取转让状态列表失败！");
        }
        //资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.findHjhInstConfigList();
        List<DropDownVO> dropDownVOS = ConvertUtils.convertListToDropDown(hjhInstConfigList, "instCode", "instName");
        if(hjhInstConfigList != null && hjhInstConfigList.size() > 0){
            jsonObject.put("资金来源列表","hjhInstConfigList");
            jsonObject.put("hjhInstConfigList",dropDownVOS);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取资金来源列表失败！");
        }
        return jsonObject;
    }

    /**
     * 获取资金来源
     * @return
     */
    @Override
    public List<HjhInstConfigVO> findHjhInstConfigList() {

        List<HjhInstConfigVO> hjhInstConfigList = amAdminClient.selectHjhInstConfigList();
        if(hjhInstConfigList != null && hjhInstConfigList.size() > 0){
            return  hjhInstConfigList;
        }
        return null;
    }
}
