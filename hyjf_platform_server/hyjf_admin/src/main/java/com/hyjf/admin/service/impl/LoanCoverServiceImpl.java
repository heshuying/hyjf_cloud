/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.LoanCoverClient;
import com.hyjf.admin.service.LoanCoverService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverServiceImpl, v0.1 2018/6/26 17:11
 */
@Service
public class LoanCoverServiceImpl implements LoanCoverService {
    @Autowired
    private LoanCoverClient loanCoverClient;
    /**
     * 查找借款盖章用户信息
     *
     * @param request
     * @return
     */
    @Override
    public List<LoanCoverUserVO> selectUserMemberList(LoanCoverUserRequest request){
        List<LoanCoverUserVO> loanCoverUserVOList = loanCoverClient.selectUserMemberList(request);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null!=loanCoverUserVOList&&loanCoverUserVOList.size()>0){
            for(LoanCoverUserVO loanCoverUserVO :loanCoverUserVOList){
                loanCoverUserVO.setStrCreateTime(sdf.format(loanCoverUserVO.getCreateTime()));
                loanCoverUserVO.setStrUpdateTime(sdf.format(loanCoverUserVO.getUpdateTime()));
            }
        }
        return loanCoverUserVOList;
    }
    /**
     * 保存记录
     */
    @Override
    public int insertLoanCoverUser(LoanCoverUserRequest request){
        int intFlg = loanCoverClient.insertLoanCoverUser(request);
        return intFlg;
    }
    /**
     * 根据id查找记录是否存在
     */
    @Override
    public boolean selectIsExistsRecordByIdNo(String strIdNo){
        LoanCoverUserVO loanCoverUserVO = loanCoverClient.selectIsExistsRecordByIdNo(strIdNo);
        if(null!=loanCoverUserVO){
            return false;
        }
        return true;
    }
    /**
     * 根据id查找记录
     */
    @Override
    public LoanCoverUserVO selectRecordByIdNo(String strIdNo){
        LoanCoverUserVO loanCoverUserVO = loanCoverClient.selectIsExistsRecordByIdNo(strIdNo);
        if(null!=loanCoverUserVO){
            return loanCoverUserVO;
        }
        return null;
    }
    /**
     * 更新记录
     */
    @Override
    public JSONObject updateLoanCoverUser(Map<String,Object> mapParam){
        JSONObject result = new JSONObject();
        if(StringUtils.isNotBlank(mapParam.get("id").toString())){
            LoanCoverUserVO loanCoverUserVO = loanCoverClient.selectIsExistsRecordById(mapParam.get("id").toString());
            if (StringUtils.isNotBlank(loanCoverUserVO.getStatus())&&loanCoverUserVO.getStatus().equals("success")) {
                if (!loanCoverUserVO.getMobile().equals(mapParam.get("mobile"))) {
                    DzqzCallBean bean = new DzqzCallBean();
                    bean.setUserId(0);
                    bean.setTxCode("infochange");
                    bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
                    bean.setV(DzqzConstant.HYJF_FDD_VERSION);
                    bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
                    bean.setCustomer_id(loanCoverUserVO.getCustomerId());// 客户编号
                    if(mapParam.containsKey("email")&&!loanCoverUserVO.getEmail().equals(mapParam.get("emai"))){
                        bean.setEmail(mapParam.get("emai").toString());// 电子邮箱
                    }
                    bean.setMobile(mapParam.get("mobile").toString());// 手机号
                    DzqzCallBean resultt = DzqzCallUtil.callApiBg(bean);
                    if (resultt != null && "success".equals(resultt.getResult())) {
                        // 更新
                        LoanCoverUserRequest loanCoverUserRequest = serParamRequest(mapParam);
                        loanCoverClient.updateLoanCoverUserRecord(loanCoverUserRequest);
                        result.put("status", Response.SUCCESS);
                        return result;
                    } else {
                        result.put("status", Response.FAIL);
                        result.put("msg", "更新失败");
                        return result;
                    }
                }
            }

        }else{
            result.put("statu", Response.FAIL);
            result.put("msg", "参数错误");
        }
        return result;
    }
    private LoanCoverUserRequest serParamRequest(Map<String, Object> mapParam) {
        LoanCoverUserRequest request = new LoanCoverUserRequest();
        if (null != mapParam && mapParam.size() > 0) {

            if (mapParam.containsKey("Name")) {
                request.setName(mapParam.get("Name").toString());
            }

            if (mapParam.containsKey("IdNo")) {
                request.setIdNo(mapParam.get("IdNo").toString());
            }
            if (mapParam.containsKey("Mobile")) {
                request.setMobile(mapParam.get("Mobile").toString());
            }
            if (mapParam.containsKey("Code")) {
                request.setCode(mapParam.get("Code").toString());
            }
            if (mapParam.containsKey("CustomerId")) {
                request.setCustomerId(mapParam.get("CustomerId").toString());
            }
            if (mapParam.containsKey("IdType")) {
                request.setIdType(Integer.parseInt(mapParam.get("IdType").toString()));
            }
            if (mapParam.containsKey("EndCreate")) {
                request.setEndCreate(mapParam.get("EndCreate").toString());
            }
            if (mapParam.containsKey("StartCreate")) {
                request.setStartCreate(mapParam.get("StartCreate").toString());
            }

            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setLimit((Integer) mapParam.get("limit"));
            }
        }
        return request;
    }

}
