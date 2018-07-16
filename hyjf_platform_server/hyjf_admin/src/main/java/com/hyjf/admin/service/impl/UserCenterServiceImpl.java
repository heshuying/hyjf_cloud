/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.StringUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nixiaoling
 * @version UserCenterServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {


    @Autowired
    private UserCenterClient userCenterClient;


    private static Logger logger = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    @Override
    public JSONObject initUserManaget(){
        JSONObject jsonObject = new JSONObject();
       /* // 用户角色
        Map<String, String> userRoles = CacheUtil.getParamNameMap("USER_ROLE");
        // 用户属性
        Map<String, String> userPropertys = CacheUtil.getParamNameMap("USER_PROPERTY");
        // 开户状态
        Map<String, String> accountStatus = CacheUtil.getParamNameMap("ACCOUNT_STATUS");
        // 用户状态
        Map<String, String> userStatus = CacheUtil.getParamNameMap("USER_STATUS");
        // 注册平台
        Map<String, String> registPlat = CacheUtil.getParamNameMap("CLIENT");
        // 用户类型
        Map<String, String> userTypes = CacheUtil.getParamNameMap("USER_TYPE");
        // 借款人类型
        Map<String, String> borrowTypes = CacheUtil.getParamNameMap("BORROWER_TYPE");
        List<HjhInstConfigVO> listHjhInstConfig =  userCenterClient.selectInstConfigAll();
         jsonObject.put("userRoles", userRoles);
        jsonObject.put("userPropertys", userPropertys);
        jsonObject.put("accountStatus", accountStatus);
        jsonObject.put("userStatus", userStatus);
        jsonObject.put("registPlat", registPlat);
        jsonObject.put("userTypes", userTypes);
        jsonObject.put("borrowTypes", borrowTypes);
        jsonObject.put("hjhInstConfigList", listHjhInstConfig);*/
        return jsonObject;
    }
    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    @Override
    public UserManagerResponse selectUserMemberList(UserManagerRequest request) {
        // 初始化分页参数，并组合到请求参数
        // 关联hyjf_trade库的ht_hjh_inst_config表
        List<HjhInstConfigVO> listHjhInstConfig = userCenterClient.selectInstConfigAll();
        // 查询列表
        UserManagerResponse userManagerResponse = userCenterClient.selectUserMemberList(request);
        if (null != userManagerResponse) {
            List<UserManagerVO> listUserMember = userManagerResponse.getResultList();
            if (!CollectionUtils.isEmpty(listUserMember)) {
                if (!CollectionUtils.isEmpty(listHjhInstConfig)) {
                    setUserInstName(listUserMember, listHjhInstConfig);
                }
            }
        }
        return userManagerResponse;
    }

    private void setUserInstName(List<UserManagerVO> listUserMember, List<HjhInstConfigVO> listHjhInstConfig) {
        for (UserManagerVO userManager : listUserMember) {
            for (HjhInstConfigVO hjhinst : listHjhInstConfig) {
                if (hjhinst.getInstCode().equals(userManager.getInstCode())) {
                    userManager.setInstName(hjhinst.getInstName());
                }
            }
        }
    }

    /**
     * 根据机构编号获取机构列表
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectInstConfigAll() {
        List<HjhInstConfigVO> listHjhInstConfig = userCenterClient.selectInstConfigAll();
        return listHjhInstConfig;
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerDetailVO selectUserDetail(String userId) {
        UserManagerDetailVO userManagerDetailVO = userCenterClient.selectUserDetailById(userId);
        return userManagerDetailVO;
    }

    /**
     * 根据用户id获取测评信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO getUserEvalationResult(String userId) {
        UserEvalationResultVO userEvalationResultVO = userCenterClient.getUserEvalationResult(userId);
        return userEvalationResultVO;
    }

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId) {
        UserBankOpenAccountVO userBankOpenAccountVO = userCenterClient.selectBankOpenAccountByUserId(userId);
        return userBankOpenAccountVO;
    }

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId) {
        CorpOpenAccountRecordVO corpOpenAccountRecordVO = userCenterClient.selectCorpOpenAccountRecordByUserId(userId);
        return corpOpenAccountRecordVO;
    }

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    @Override
    public BindUserVo selectBindeUserByUserI(String userId) {
        BindUserVo bindUserVo = userCenterClient.selectBindeUserByUserId(userId);
        return bindUserVo;
    }

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    @Override
    public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
        CertificateAuthorityVO certificateAuthorityVO = userCenterClient.selectCertificateAuthorityByUserId(userId);
        return certificateAuthorityVO;
    }

    /**
     * 根据用户id获取用户修改信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerUpdateVO selectUserUpdateInfoByUserId(String userId) {
        UserManagerUpdateVO userManagerUpdateVo = userCenterClient.selectUserUpdateInfoByUserId(userId);
        return userManagerUpdateVo;
    }

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    @Override
    public int updataUserInfo(UserManagerUpdateRequest request) {
        int intUpdFlg = userCenterClient.updataUserInfo(request);
        return intUpdFlg;
    }

    /**
     * 根据用户id获取推荐人信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserRecommendCustomizeVO selectUserRecommendByUserId(String userId) {
        UserRecommendCustomizeVO userRecommendVO = userCenterClient.selectUserRecommendByUserId(userId);
        return userRecommendVO;
    }

    /**
     * 获取某一用户的信息修改列表
     * @param request
     * @return
     */
    @Override
    public List<UserChangeLogVO> selectUserChageLog(UserChangeLogRequest request){
//        List<UserChangeLogVO> userChangeLogVO = userCenterClient.selectUserChageLog(request);
        List<UserChangeLogVO> userChangeLogVO = new ArrayList<UserChangeLogVO>();
        return userChangeLogVO;
    }
    /**
     * 校验手机号
     *
     * @param userId
     * @param mobile
     * @return
     */
    @Override
    public int countUserByMobile(int userId, String mobile) {
        int checkFlg = userCenterClient.countUserByMobile(userId, mobile);
        return checkFlg;
    }

    /**
     * 校验推荐人
     *
     * @param userId
     * @param recommendName
     * @return
     */
    @Override
    public int checkRecommend(String userId, String recommendName) {
        int checkFlg = userCenterClient.checkRecommend(userId, recommendName);
        return checkFlg;
    }

    /**
     * @Description 根据accountid调用接口查找企业信息
     */
    @Override
    public CompanyInfoVO queryCompanyInfoByAccoutnId(Integer userId, String accountId) {
        JSONObject ret = new JSONObject();
        CompanyInfoVO companyInfoVO = null;
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_CORPRATION_QUERY);// 交易代码
        bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(accountId);// 电子账号
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean != null) {
                if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                    companyInfoVO.setAccount(resultBean.getCaccount());
                    companyInfoVO.setBusId(resultBean.getBusId());
                    companyInfoVO.setIdType(resultBean.getIdType());
                    companyInfoVO.setIdNo(resultBean.getIdNo());
                    companyInfoVO.setMobile(resultBean.getMobile());
                    companyInfoVO.setName(resultBean.getName());
                    companyInfoVO.setTaxId(resultBean.getTaxId());
                    ret.put("status", "success");
                    ret.put("result", "查询成功!");
                } else {
                    ret.put("status", "error");
                    ret.put("result", "查询企业信息错误,请重新查询!");
                    /*if (ManageUsersDefine.RESPCODE_CORPRATION_QUERY_EXIST.equals(callBackBean.getRetCode())) {
                        ret.put("result", "卡号不存在!");
                    }else if (ManageUsersDefine.RESPCODE_CORPRATION_QUERY_NOT_CORPRATION.equals(callBackBean.getRetCode())) {
                        ret.put("result", "非企业账户!");
                    }else if (ManageUsersDefine.RESPCODE_CORPRATION_QUERY_CHECEK_ERROR.equals(callBackBean.getRetCode())) {
                        ret.put("result", "平台交易验证未通过!");
                    }else if (ManageUsersDefine.RESPCODE_CORPRATION_QUERY_CORPRATION_MORE.equals(callBackBean.getRetCode())) {
                        ret.put("result", "访问频率超限!");
                    }*/
                    ;
                }
            } else {
                ret.put("status", "error");
                ret.put("result", "银行接口返回异常!");
            }

        } catch (Exception e) {
            ret.put("status", "error");
            ret.put("result", "银行接口查询异常!");
        }
        return companyInfoVO;
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserVO selectUserByUserId(String userId) {
        if (StringUtils.isNotEmpty(userId)) {
            int userIdInt = Integer.parseInt(userId);
            return userCenterClient.selectUserByUserId(userIdInt);
        }
        return null;
    }

    @Override
    public CompanyInfoVO selectCompanyInfoByUserId(String userId) {
        CompanyInfoVO info = new CompanyInfoVO();
        if (StringUtils.isNotEmpty(userId)) {
            CorpOpenAccountRecordVO corpOpenAccountRecordVO = userCenterClient.selectCorpOpenAccountRecordByUserId(userId);
            int userIdInt = Integer.parseInt(userId);
            List<BankCardVO> bankCardList = userCenterClient.selectBankCardByUserId(userIdInt);
            String cardNo = null;
            if (bankCardList != null && bankCardList.size() > 0) {
                cardNo = bankCardList.get(0).getCardNo();
            }
            String idType = null;
            Integer cardType = corpOpenAccountRecordVO.getCardType();

            if (20 == cardType) {//组织机构代码
                idType = "组织机构代码";
            } else if (25 == cardType) {
                idType = "社会信用号";
            }
            info.setCardType(cardType + "");
            info.setIdType(idType);
            info.setIdNo(corpOpenAccountRecordVO.getBusiCode());
            info.setName(corpOpenAccountRecordVO.getBusiName());
            info.setAccount(cardNo);
            info.setBusId(corpOpenAccountRecordVO.getBuseNo());
            info.setTaxId(corpOpenAccountRecordVO.getTaxRegistrationCode());
            info.setRemark(corpOpenAccountRecordVO.getRemark());
            return info;
        }
        return null;
    }

    /**
     * 保存企业开户信息
     * @param updCompanyRequest
     * @return
     */
    @Override
    public Response saveCompanyInfo(UpdCompanyRequest updCompanyRequest) {
        UserVO user = this.selectUserByUserId(updCompanyRequest.getUserId());
        String bankId = userCenterClient.queryBankIdByCardNo(updCompanyRequest.getAccount());
        if (StringUtils.isNotBlank(bankId)) {
            String bankName = getBankNameById(bankId);
            String payAllianceCode = null;
            BankCallBean callBean = payAllianceCodeQuery(updCompanyRequest.getAccount(), user.getUserId());
            if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(callBean.getRetCode())) {
                payAllianceCode = callBean.getPayAllianceCode();
                if (StringUtils.isBlank(payAllianceCode)) {
                    payAllianceCode = getPayAllianceCodeByBankId(bankId);
                }
            }
            updCompanyRequest.setBankName(bankName);
            updCompanyRequest.setPayAllianceCode(payAllianceCode);
            updCompanyRequest.setBankId(bankId);
        }
        Response response = userCenterClient.saveCompanyInfo(updCompanyRequest);
        return response;
    }

    /**
     * 根据银行Id查询所属银行名称
     *
     * @param bankId
     * @return
     */
    public String getBankNameById(String bankId) {
        String bankName = "";
        if (StringUtils.isNotBlank(bankId)) {
            int bankIdInt = Integer.parseInt(bankId);
            BanksConfigVO banksConfigVO = userCenterClient.getBanksConfigByBankId(bankIdInt);
            if (null != banksConfigVO) {
                bankName = banksConfigVO.getBankName();
            }
        }
        return bankName;
    }

    /**
     * 根据银行Id查询本地存的银联行号
     *
     * @param bankId
     * @return
     */
    public String getPayAllianceCodeByBankId(String bankId) {
        String payAllianceCode = "";
        if (StringUtils.isNotBlank(bankId)) {
            BanksConfigVO banksConfigVO = userCenterClient.getBanksConfigByBankId(Integer.parseInt(bankId));
            if (null != banksConfigVO) {
                payAllianceCode = banksConfigVO.getPayAllianceCode();
            }
        }
        return payAllianceCode;
    }

    /**
     * 调用江西银行查询联行号
     *
     * @param cardNo
     * @return
     */
    public BankCallBean payAllianceCodeQuery(String cardNo, Integer userId) {
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        bean.setVersion(BankCallConstant.VERSION_10);// 版本号
        bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(cardNo);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(orderDate);
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogRemark("联行号查询");
        bean.setLogClient(0);
        return BankCallUtils.callApiBg(bean);
    }


    /**
     * 获取推荐人信息
     * @param userId
     * @return
     */
    @Override
    public SpreadsUserVO selectSpreadsUsersByUserId(String userId){
        return userCenterClient.selectSpreadsUsersByUserId(userId);
    }
    /**
     * 根据推荐人姓名查找用户
     * @param recommendName
     * @return
     */
    @Override
    public UserVO selectUserByRecommendName(String recommendName){
        return userCenterClient.selectUserByRecommendName(recommendName);
    }
    /**
     * 修改推荐人信息
     * @param request
     * @return
     */
    @Override
    public int updateUserRecommend(AdminUserRecommendRequest request){
        return userCenterClient.updateUserRecommend(request);
    }

    /**
     * 修改用户身份证
     * @param request
     * @return
     */
    @Override
    public int updateUserIdCard(AdminUserRecommendRequest request){
        return userCenterClient.updateUserIdCard(request);
    }
    /**
     * 单表查询开户信息
     *
     * @return
     */
    @Override
    public BankOpenAccountVO queryBankOpenAccountByUserId(int userId){
        return userCenterClient.queryBankOpenAccountByUserId(userId);
    }

}
