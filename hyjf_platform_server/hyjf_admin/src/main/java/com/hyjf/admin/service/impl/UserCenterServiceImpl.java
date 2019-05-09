/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.response.CompanyInfoSearchResponseBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.BankCancellationAccountResponse;
import com.hyjf.am.response.user.BankCardResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.config.IdCardCustomize;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author nixiaoling
 * @version UserCenterServiceImpl, v0.1 2018/6/20 15:36
 */
@Service
public class UserCenterServiceImpl extends BaseServiceImpl implements UserCenterService  {


    @Autowired
    private AmUserClient userCenterClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private CommonProducer commonProducer;
    /**卡号不存在*/
    public static final String RESPCODE_CORPRATION_QUERY_EXIST = "CA000054";

    /**平台交易验证未通过*/
    public static final String RESPCODE_CORPRATION_QUERY_CHECEK_ERROR = "CA101207";

    /**不是企业用户*/
    public static final String RESPCODE_CORPRATION_QUERY_NOT_CORPRATION = "CA110136";

    /**访问频率超限*/
    public static final String RESPCODE_CORPRATION_QUERY_CORPRATION_MORE = "JX900032";

    private static Logger logger = LoggerFactory.getLogger(UserCenterServiceImpl.class);

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
        List<HjhInstConfigVO> listHjhInstConfig = amTradeClient.selectInstConfigAll();
        //部门
        String [] strDepts = getDeptId(request.getCombotreeListSrch());
        request.setCombotreeListSrch(strDepts);
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
        List<HjhInstConfigVO> listHjhInstConfig = amTradeClient.selectInstConfigAll();
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
       List<UserChangeLogVO> userChangeLogVO = userCenterClient.selectUserChageLog(request);
//        List<UserChangeLogVO> userChangeLogVO = new ArrayList<UserChangeLogVO>();
        return userChangeLogVO;
    }
    /**
     * 校验手机号
     *
     * @param mobile
     * @return
     */
    @Override
    public int countUserByMobile(String mobile,int userId) {
        int checkFlg = userCenterClient.countUserByMobile(userId,mobile);
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
    public CompanyInfoSearchResponseBean queryCompanyInfoByAccoutnId(Integer userId, String accountId) {
        CompanyInfoSearchResponseBean companyInfoSearchVO = new CompanyInfoSearchResponseBean();
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
                    CompanyInfoVO companyInfoVO  = new CompanyInfoVO();
                    companyInfoVO.setAccount(resultBean.getCaccount());
                    companyInfoVO.setBusId(resultBean.getBusId());
                    companyInfoVO.setIdType(resultBean.getIdType());
                    companyInfoVO.setIdNo(resultBean.getIdNo());
                    if(StringUtils.isNotBlank(resultBean.getIdType())){
                        if (resultBean.getIdType().equals("20")) {//组织机构代码
                            companyInfoVO.setCardType("组织机构代码");
                        } else if (resultBean.getIdType().equals("25")) {
                            companyInfoVO.setCardType("社会信用号");
                        }
                    }
                    companyInfoVO.setMobile(resultBean.getMobile());
                    companyInfoVO.setName(resultBean.getName());
                    companyInfoVO.setTaxId(resultBean.getTaxId());
                    companyInfoSearchVO.setReturnCode("00");
                    companyInfoSearchVO.setCompanyInfoVO(companyInfoVO);
                } else {
                    if (RESPCODE_CORPRATION_QUERY_EXIST.equals(resultBean.getRetCode())) {
                        companyInfoSearchVO.setReturnMsg("卡号不存在!");
                        companyInfoSearchVO.setReturnCode("99");
                        return companyInfoSearchVO;
                    }else if (RESPCODE_CORPRATION_QUERY_NOT_CORPRATION.equals(resultBean.getRetCode())) {
                        //ret.put("result", "非企业账户!");
                        companyInfoSearchVO.setReturnMsg("非企业账户!");
                        companyInfoSearchVO.setReturnCode("99");
                        return companyInfoSearchVO;
                    }else if (RESPCODE_CORPRATION_QUERY_CHECEK_ERROR.equals(resultBean.getRetCode())) {
                       // ret.put("result", "平台交易验证未通过!");
                        companyInfoSearchVO.setReturnMsg("平台交易验证未通过!");
                        companyInfoSearchVO.setReturnCode("99");
                        return companyInfoSearchVO;
                    }else if (RESPCODE_CORPRATION_QUERY_CORPRATION_MORE.equals(resultBean.getRetCode())) {
                        //ret.put("result", "访问频率超限!");
                        companyInfoSearchVO.setReturnMsg("访问频率超限!");
                        companyInfoSearchVO.setReturnCode("99");
                        return companyInfoSearchVO;
                    }
                }
            } else {
                companyInfoSearchVO.setReturnMsg("银行接口返回异常!");
                companyInfoSearchVO.setReturnCode("99");
                return companyInfoSearchVO;
            }
        } catch (Exception e) {
            companyInfoSearchVO.setReturnMsg("银行接口返回异常!");
            companyInfoSearchVO.setReturnCode("99");
            return companyInfoSearchVO;
        }
        return companyInfoSearchVO;
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
            if(null!=corpOpenAccountRecordVO){
                String idType = null;
                Integer cardType = corpOpenAccountRecordVO.getCardType();
                if(null!=cardType){
                    if (20 == cardType) {//组织机构代码
                        idType = "组织机构代码";
                    } else if (25 == cardType) {
                        idType = "社会信用号";
                    }
                }
                info.setCardType(cardType + "");
                info.setIdType(idType);
                info.setIdNo(corpOpenAccountRecordVO.getBusiCode());
                info.setName(corpOpenAccountRecordVO.getBusiName());
                info.setAccount(cardNo);
                info.setBusId(corpOpenAccountRecordVO.getBuseNo());
                info.setTaxId(corpOpenAccountRecordVO.getTaxRegistrationCode());
                info.setRemark(corpOpenAccountRecordVO.getRemark());
            }
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
        Response response = userCenterClient.saveCompanyInfo(updCompanyRequest);
        return response;
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
     * 根据姓名查找用户
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

    /**
     * 发送CA认证信息修改MQ
     * @param form
     */
    public void sendCAChangeMQ(UserManagerUpdateRequest form) {
        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", String.valueOf(form.getUserId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.FDD_USERINFO_CHANGE_TOPIC, UUID.randomUUID().toString(),params));
        } catch (MQException e) {
            logger.error(e.getMessage());
            logger.error("修改手机号后 发送更新客户信息MQ失败...", e);
        }
    }
    @Override
    public void sendCAChangeMQ(AdminUserRecommendRequest form) {
        // 加入到消息队列
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("userId", String.valueOf(form.getUserId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(),params));
        } catch (MQException e) {
            logger.error(e.getMessage());
            logger.error(" 修改身份证号后 发送更新客户信息MQ失败...", e);
        }
        // add by liushouyi 20180228 修改身份证号后 发送更新客户信息MQ end
    }
    /**
     *
     * @Description:通过身份证号获取户籍所在地
     * @param idCard
     * @return String
     * @exception:
     */
    @Override
    public String getAreaByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard) || idCard.length() < 15) {
            return "";
        }
        IdCardCustomize idCardCustomize = new IdCardCustomize();
        idCardCustomize.setBm(idCard.substring(0, 6));
        IdCardCustomize idCardCustomizeResponse =  amConfigClient.getIdCardCustomize(idCardCustomize);
        if(null!=idCardCustomizeResponse){
            return idCardCustomizeResponse.getArea();
        }
        return "";
    }
    /**
     * 根据推荐人id查找用信息
     * @param userId
     * @return
     */
    @Override
    public List<SpreadsUserVO> selectSpreadsUserBySpreadUserId(int userId){
        return userCenterClient.selectSpreadsUserBySpreadUserId(userId);
    }
    /**
     * 部门树形结构
     * @return
     */
    @Override
    public JSONArray getCrmDepartmentList(String[] list) {
        List<OADepartmentCustomizeVO> departmentList = userCenterClient.queryDepartmentInfo(null);

        Map<String, String> map = new HashMap<String, String>();
        if (departmentList != null && departmentList.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartment : departmentList) {
                map.put(String.valueOf(oaDepartment.getId()), HtmlUtil.unescape(oaDepartment.getName()));
            }
        }
        return treeDepartmentList(departmentList, map, list, "0", "");
    }
    /**
     * 部门树形结构
     *
     * @param departmentTreeDBList
     * @param topParentDepartmentCd
     * @return
     */
    private JSONArray treeDepartmentList(List<OADepartmentCustomizeVO> departmentTreeDBList, Map<String, String> map, String[] selectedNode, String topParentDepartmentCd,
                                         String topParentDepartmentName) {
        JSONArray ja = new JSONArray();
        JSONObject joAttr = new JSONObject();
        if (departmentTreeDBList != null && departmentTreeDBList.size() > 0) {
            JSONObject jo = null;
            for (OADepartmentCustomizeVO departmentTreeRecord : departmentTreeDBList) {
                jo = new JSONObject();
                jo.put("title", departmentTreeRecord.getName());
                jo.put("key", UUID.randomUUID());
                jo.put("value", departmentTreeRecord.getId().toString());
                String departmentCd = String.valueOf(departmentTreeRecord.getId());
                String departmentName = String.valueOf(departmentTreeRecord.getName());
                String parentDepartmentCd = String.valueOf(departmentTreeRecord.getParentid());
                if (topParentDepartmentCd.equals(parentDepartmentCd)) {
                    JSONArray array = treeDepartmentList(departmentTreeDBList, map, selectedNode, departmentCd, departmentName);
                    if(null!=array&&array.size()>0){
                        jo.put("value", "P"+departmentTreeRecord.getId().toString());
                    }
                    jo.put("children", array);
                    ja.add(jo);
                }
            }
        }
        return ja;
    }

    /**
     * 对部门进行操作
     * @param deptParam
     * @return
     */
    public String[] getDeptId(String[] deptParam) {
        String[] strIds = null;
        List<String> stringList = new ArrayList<String>();
        if (Validator.isNotNull(deptParam)) {
            //判断部门选择里是否有父级部门
            for (int i = 0; i < deptParam.length; i++) {
                String st = deptParam[i];
                if (st.contains("P")) {
                    //选择的是父级部门的情况下
                    String strDe = st.split("P")[1];
                    if (StringUtils.isNotBlank(strDe)) {
                        stringList = getDeptInfoByDeptId(Integer.parseInt(strDe), stringList);
                    }
                } else {
                    //既选择了父级部门又选择子级菜单的情况下
                    stringList.add(st);
                }
                //其他
                if (("-10086").equals(st)) {
                    stringList.add("0");
                }
            }
        }
        if (null != stringList && stringList.size() > 0) {
            strIds = stringList.toArray(new String[stringList.size()]);
        }
        return strIds;
    }

    /**
     * 根据部门查找是否有子级部门并循环将部门id设置的list中
     */
    private List<String> getDeptInfoByDeptId(int deptId, List<String> keysList) {
        List<OADepartmentCustomizeVO> list = userCenterClient.getDeptInfoByDeptId(deptId);
        if (null != list && list.size() > 0) {
            for (OADepartmentCustomizeVO oaDepartmentCustomizeVO : list) {
                keysList.add(oaDepartmentCustomizeVO.getId().toString());
                getDeptInfoByDeptId(oaDepartmentCustomizeVO.getId(), keysList);
            }
        }
        return keysList;
    }
    /**
     * 根据用户id获取开户信息
     *
     * @auther: nxl
     * @param userId
     * @return
     */
    @Override
    public BankCardVO getBankCardByUserId(String userId) {
        if(StringUtils.isNotBlank(userId)){
            return userCenterClient.getBankCardByUserId(Integer.parseInt(userId));
        }
        return null;
    }


    /**
     * 调用江西银行查询联行号
     * @param cardNo
     * @return
     */
    @Override
    public BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
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
     * 根据银行名获取江西银行配置信息
     * @param bankName
     * @return
     * @auth nxl
     */
    @Override
    public JxBankConfigVO getBankConfigByBankName(String bankName){
        return amConfigClient.getBankConfigByBankName(bankName);
    }
    /**
     * 更新用户信息(基本信息,手机号,邮箱,用户角色)
     *
     * @param request
     * @auther: nxl
     * @return
     */
    @Override
    public int updateUserBaseInfo(UserInfosUpdCustomizeRequest request){
        return userCenterClient.updateUserBaseInfo(request);
    }
    /**
     * 更新银行卡信息
     *
     * @param request
     * @auther: nxl
     * @return
     */
    @Override
    public int updateUserBankInfo(UserInfosUpdCustomizeRequest request){
        return userCenterClient.updateUserBankInfo(request);
    }

    /**
     * 根据userId获取userInfo对象
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO selectUserInfoByUserId(String userId) {
        return userCenterClient.findUsersInfoById(Integer.parseInt(userId));
    }

    /**
     * 通过当前用户ID 查询用户所在一级分部,从而关联用户所属渠道
     * @param userId
     * @return
     * @Author : huanghui
     */
    @Override
    public UserUtmInfoCustomizeVO getUserUtmInfo(Integer userId) {
        UserUtmInfoCustomizeVO userUtmInfoCustomizeVO = userCenterClient.getUserUtmInfo(userId);
        return userUtmInfoCustomizeVO;
    }

    /**
     * 企业信息补录时查询，根据对公账号查找银行信息
     * @param account
     * @param userId
     * @return
     * @auther: nxl
     */
    @Override
    public BankCardResponse getBankInfoByAccount(String account,String userId) {
        UpdCompanyRequest updCompanyRequest = new  UpdCompanyRequest();
        updCompanyRequest.setAccount(account);
        updCompanyRequest.setUserId(userId);
        BankCardResponse bankCardResponse = userCenterClient.getBankInfoByAccount(updCompanyRequest);
        return bankCardResponse;
    }

    /**
     *  用户销户操作
     *
     * @param userId
     * @param bankOpenAccount
     * @return
     */
    @Override
    public int cancellationAccountAction(String userId, Integer bankOpenAccount) {
        return userCenterClient.cancellationAccountAction(userId, bankOpenAccount);
    }

    /**
     *
     * 用户销户成功后,删除用户账户表
     *
     * @param userId
     */
    @Override
    public int deleteUserAccountAction(String userId) {
        return this.amTradeClient.deleteUserAccountAction(userId);
    }

    /**
     *
     * 销户成功后,保存用户销户记录
     *
     * @param bankCancellationAccountRequest
     * @return
     */
    @Override
    public int saveCancellationAccountRecordAction(BankCancellationAccountRequest bankCancellationAccountRequest) {
        return this.userCenterClient.saveCancellationAccountRecordAction(bankCancellationAccountRequest);
    }

    /**
     * 查询销户记录列表
     *
     * @param bankCancellationAccountRequest
     * @return
     */
    @Override
    public BankCancellationAccountResponse getBankCancellationAccountList(BankCancellationAccountRequest bankCancellationAccountRequest) {
        BankCancellationAccountResponse response = this.userCenterClient.getBankCancellationAccountList(bankCancellationAccountRequest);
        return response;
    }

    @Override
    public boolean syncUserMobile(UserRequest userRequest) {
       /// UserResponse response =  this.userCenterClient.syncUserMobile(userRequest);
        return false;
    }
}
