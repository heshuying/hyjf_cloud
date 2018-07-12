/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.trade.CorpOpenAccountRecordRequest;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
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
     * 根據accounId獲取開戶信息
     *
     * @param accountId
     * @return
     */
    @Override
    public BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId) {
        return userCenterClient.selectBankOpenAccountByAccountId(accountId);
    }

    @Override
    public JSONObject saveCompanyInfo(Map<String, String> mapParam) {
        JSONObject ret = new JSONObject();
        String accountId = mapParam.get("accountId");
        String userId = mapParam.get("userId");
        String idType = mapParam.get("idType");
        if (StringUtils.isBlank(userId)) {
            ret.put("status", "error");
            ret.put("result", "请先选择用户再进行操作!");
            return ret;
        }
        if (StringUtils.isBlank(accountId)) {
            ret.put("status", "error");
            ret.put("result", "请输入正确的电子账号!");
            return ret;
        }
        UserVO user = this.selectUserByUserId(userId);
        int bankOpenFlag = user.getBankOpenAccount();
        if (user != null && user.getUserType() != 1) {
            if (user.getBankOpenAccount() == 1) {//已开户
                ret.put("status", "error");
                ret.put("result", "用户已开户!");
                return ret;
            }
        }
        if (user.getUserType() != 1) {
            BankOpenAccountVO bankOpenAccountVO = this.selectBankOpenAccountByAccountId(accountId);
            if (null != bankOpenAccountVO) {
                String strUserId = String.valueOf(bankOpenAccountVO.getUserId());
                UserVO userBankOpen = this.selectUserByUserId(strUserId);
                Integer openFlag = userBankOpen.getBankOpenAccount();
                if (openFlag == 1) {
                    ret.put("status", "error");
                    ret.put("result", "该电子账号已被使用,无法进行企业信息补录!");
                    return ret;
                }
            }
        }
        CorpOpenAccountRecordRequest record = null;
        if (bankOpenFlag == 1) {//获取修改信息
            CorpOpenAccountRecordVO corpOpenAccountRecordVO = this.selectCorpOpenAccountRecordByUserId(String.valueOf(user.getUserId()));
            BeanUtils.copyProperties(corpOpenAccountRecordVO, record);
        } else {
            record = new CorpOpenAccountRecordRequest();
        }
        record.setUserId(user.getUserId());
        record.setUsername(user.getUsername());
        record.setBusiCode(mapParam.get("idNo"));
        record.setBusiName(mapParam.get("Name"));
        record.setStatus(6);//成功
        record.setAddTime(GetDate.getDate());
        record.setIsBank(1);//银行开户
        record.setCardType(Integer.parseInt(idType));
        record.setTaxRegistrationCode(mapParam.get("TaxId"));
        record.setBuseNo(mapParam.get("BusId"));
        record.setRemark(mapParam.get("Remark"));
        //保存企业信息
        if (bankOpenFlag == 1) {
            //修改信息
            int flag = userCenterClient.updateCorpOpenAccountRecord(record);
        } else {
            // 保存信息
            int insertFlag = userCenterClient.insertCorpOpenAccountRecord(record);
        }
        //保存银行卡信息
        BankCardRequest bankCard = null;
        if (bankOpenFlag == 1) {
            List<BankCardVO> bankCardList = userCenterClient.selectBankCardByUserId(user.getUserId());
            if (null != bankCardList && bankCardList.size() > 0) {
                BankCardVO bankCardVO = bankCardList.get(0);
                BeanUtils.copyProperties(bankCardVO, bankCard);
            }
        } else {
            bankCard = new BankCardRequest();
        }
        bankCard.setUserId(user.getUserId());
        bankCard.setUserName(user.getUsername());
        bankCard.setCardNo(mapParam.get("account"));
        bankCard.setCreateTime(GetDate.getDate());
        bankCard.setCreateUserId(user.getUserId());
        bankCard.setCreateUsername(user.getUsername());
        String bankId = userCenterClient.queryBankIdByCardNo(mapParam.get("account"));
        if (StringUtils.isNotBlank(bankId)) {
            String bankName = getBankNameById(bankId);
            String payAllianceCode = null;
            BankCallBean callBean = payAllianceCodeQuery(mapParam.get("account"), user.getUserId());
            if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(callBean.getRetCode())) {
                payAllianceCode = callBean.getPayAllianceCode();
                if (StringUtils.isBlank(payAllianceCode)) {
                    payAllianceCode = getPayAllianceCodeByBankId(bankId);
                }
            }
            bankCard.setBankId(Integer.parseInt(bankId));
            bankCard.setBank(bankName);
            bankCard.setPayAllianceCode(payAllianceCode);
            if (bankOpenFlag == 1) {
                int updateflag = userCenterClient.updateUserCard(bankCard);
                if (updateflag > 0) {
                    ret.put("status", "error");
                    ret.put("result", "银行卡信息修改保存异常!");
                    return ret;
                }
            } else {
                int insertcard = this.userCenterClient.insertUserCard(bankCard);
                if (insertcard > 0) {
                    ret.put("status", "error");
                    ret.put("result", "银行卡信息保存异常!");
                    return ret;
                }
            }
        }
        //保存开户信息
        BankOpenAccountRequest openAccount = new BankOpenAccountRequest();
        openAccount.setUserId(user.getUserId());
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(mapParam.get("accountId"));
        openAccount.setCreateTime(GetDate.getDate());
        openAccount.setCreateUserId(user.getUserId());
        openAccount.setCreateUserId(user.getUserId());
        int openFlag = 0;
        if (bankOpenFlag == 1) {
            BankOpenAccountVO bankOpenAccountVO = userCenterClient.queryBankOpenAccountByUserId(user.getUserId());
            openAccount.setId(bankOpenAccountVO.getId());
            openFlag = userCenterClient.updateBankOpenAccount(openAccount);
        } else {
            openFlag = userCenterClient.insertBankOpenAccount(openAccount);
        }
        if (openFlag > 0) {
            ret.put("status", "error");
            ret.put("result", "银行开户信息修改保存异常!");
            return ret;
        }
        //替换企业信息名称
        UserInfoVO userInfo = new UserInfoVO();
        userInfo = userCenterClient.findUserInfoById(user.getUserId());
        userInfo.setTruename(mapParam.get("Name"));
        int userInfoFlag = userCenterClient.updateUserInfoByUserInfo(userInfo);
        if (userInfoFlag > 0) {
            ret.put("status", "error");
            ret.put("result", "用户详细信息保存异常!");
            return ret;
        }
        if (bankOpenFlag != 1) {
            user.setBankAccountEsb(0);//开户平台,pc
            user.setUserType(1);//企业用户
            user.setBankOpenAccount(1);//已开户
            int userFlag = userCenterClient.updateUser(user);
            if (userInfoFlag > 0) {
                ret.put("status", "error");
                ret.put("result", "用户表信息保存异常!");
                return ret;
            }
        }
        ret.put("status", "success");
        ret.put("result", "企业用户信息补录成功!");
        return ret;
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

}
