/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.LoanCoverUserRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.LoanCoverService;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverServiceImpl, v0.1 2018/6/26 17:11
 */
@Service
public class LoanCoverServiceImpl implements LoanCoverService {
    @Autowired
    private AmUserClient loanCoverClient;
    /**
     * 查找借款盖章用户信息
     *
     * @param request
     * @return
     */
    @Override
    public LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request){
        LoanCoverUserResponse loanCoverUserResponse = loanCoverClient.selectUserMemberList(request);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null!=loanCoverUserResponse){
            for(LoanCoverUserVO loanCoverUserVO :loanCoverUserResponse.getResultList()){
                loanCoverUserVO.setStrCreateTime(sdf.format(loanCoverUserVO.getCreateTime()));
                loanCoverUserVO.setStrUpdateTime(sdf.format(loanCoverUserVO.getUpdateTime()));
            }
        }
        return loanCoverUserResponse;
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
     * 根据证件号码查找记录
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
     * 根据id查找借款主体CA认证记录表
     * @param id
     * @return
     */
    @Override
    public LoanCoverUserResponse getLoanCoverUserById(String id){
        LoanCoverUserResponse response = loanCoverClient.selectIsExistsRecordById(id);
        return response;
    }

    /**
     * 更新借款主体CA认证记录表
     * @param loanCoverUserRequest
     * @return
     */
    @Override
    public boolean updateLoanCoverUserRecord(LoanCoverUserRequest loanCoverUserRequest){
        int intUpd = loanCoverClient.updateLoanCoverUserRecord(loanCoverUserRequest);
        if(intUpd>0){
            return true;
        }
        return false;
    }
    /**
     * 更新记录
     */
    @Override
    public AdminResult updateLoanCoverUser(LoanCoverUserRequestBean loanCoverUserRequestBean){
        if(StringUtils.isNotBlank(String.valueOf(loanCoverUserRequestBean.getId()))){
            LoanCoverUserResponse loanCoverUserResponse = loanCoverClient.selectIsExistsRecordById(String.valueOf(loanCoverUserRequestBean.getId()));
            if(null!=loanCoverUserResponse){
                LoanCoverUserVO loanCoverUserVO = loanCoverUserResponse.getResult();
                if (StringUtils.isNotBlank(loanCoverUserVO.getStatus())&& "success".equals(loanCoverUserVO.getStatus())) {
                    if (!loanCoverUserVO.getMobile().equals(loanCoverUserRequestBean.getMobile())) {
                        DzqzCallBean bean = new DzqzCallBean();
                        bean.setUserId(0);
                        bean.setTxCode("infochange");
                        bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
                        bean.setV(DzqzConstant.HYJF_FDD_VERSION);
                        bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
                        bean.setCustomer_id(loanCoverUserVO.getCustomerId());// 客户编号

                        if(!loanCoverUserVO.getEmail().equals(loanCoverUserRequestBean.getEmail())){
                            bean.setEmail(loanCoverUserRequestBean.getEmail());// 电子邮箱
                        }
                        bean.setMobile(loanCoverUserRequestBean.getMobile());// 手机号
                        DzqzCallBean resultt = DzqzCallUtil.callApiBg(bean);
                        if (resultt != null && "success".equals(resultt.getResult())) {
                            // 更新
                            LoanCoverUserRequest loanCoverUserRequest = new LoanCoverUserRequest();
                            BeanUtils.copyProperties(loanCoverUserRequestBean, loanCoverUserRequest);
                            loanCoverClient.updateLoanCoverUserRecord(loanCoverUserRequest);
                            return new AdminResult<>();

                        } else {
                            return new AdminResult<>("99", "更新失败");
                        }
                    }
                }
            }

        }else{
            return new AdminResult<>("99", "参数错误");
        }

        return new AdminResult<>();
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

            /*if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                request.setLimit((Integer) mapParam.get("limit"));
            }*/
        }
        return request;
    }

    /**
     * 根据证件号码和姓名查找用户CA认证记录表
     * @param strIdNo
     * @param tureName
     * @return
     */
    @Override
    public CertificateAuthorityVO selectCertificateAuthorityByIdNoName(String strIdNo, String tureName){
        CertificateAuthorityResponse response = loanCoverClient.selectCertificateAuthorityByIdNoName(strIdNo,tureName);
        if(null!=response){
            return  response.getResult();
        }
        return null;
    }
}
