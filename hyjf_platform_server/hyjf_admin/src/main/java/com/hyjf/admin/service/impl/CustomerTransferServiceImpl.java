/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 9:15
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    CommonProducer commonProducer;

    @Autowired
    private AdminCommonService adminCommonService;

    /**
     * 根据筛选条件查询Transfer列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getTransferCount(CustomerTransferListRequest request) {
        return amTradeClient.getUserTransferCount(request);
    }

    /**
     * 根据筛选条件查询userTransfer列表
     *
     * @param request 筛选条件
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<UserTransferVO> searchUserTransferList(CustomerTransferListRequest request) {
        List<UserTransferVO> userTransferVOList = amTradeClient.searchUserTransferList(request);
        for(UserTransferVO userTransferVO:userTransferVOList){
            List<DropDownVO> status = adminCommonService.getParamNameList("TRANSFER_STATUS");
            for(DropDownVO dropDownVO:status){
                if(dropDownVO.getKey().equals(String.valueOf(userTransferVO.getStatus()))){
                    userTransferVO.setStatusStr(dropDownVO.getValue());
                }
            }
            List<DropDownVO> types = adminCommonService.getParamNameList("TRANSFER_TYPE");
            for(DropDownVO type:types){
                if(type.getKey().equals(String.valueOf(userTransferVO.getTransferType()))){
                    userTransferVO.setTransferTypeStr(type.getValue());
                }
            }
            userTransferVO.setCreateTimeStr(GetDate.dateToString(userTransferVO.getCreateTime()));
            userTransferVO.setUpdateTimeStr(GetDate.dateToString(userTransferVO.getUpdateTime()));
        }
        return userTransferVOList;
    }

    /**
     * 根据nameClass获取数据字典表的下拉列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<ParamNameVO> searchParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }

    /**
     * 根据用户名查询账户余额
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject searchBalanceByUsername(String userName) {
        JSONObject jsonObject = new JSONObject();
        List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
        if (userVOList != null && userVOList.size() == 1) {
            logger.info("admin 项目 查询出来的userList.size=[{}]", userVOList.size());
            UserVO userVO = userVOList.get(0);
            List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
            if (accountChinapnrVOList != null && accountChinapnrVOList.size() == 1) {
                List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
                if (accountVOList != null && accountVOList.size() == 1) {
                    AccountVO accountVO = accountVOList.get(0);
                    jsonObject.put("status", "0");
                    jsonObject.put("result", accountVO.getBalance().toString());
                } else {
                    throw new ReturnMessageException(MsgEnum.ERR_AMT_MONEY);
                }
            } else {
                throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
            }
        } else {
            throw new ReturnMessageException(MsgEnum.STATUS_ZT000001);
        }
        return jsonObject;
    }

    /**
     * 参数校验
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public void checkCustomerTransferParam(CustomerTransferRequest request) {
        CheckUtil.check(StringUtils.isNotEmpty(request.getOutUserName()),MsgEnum.ERR_OBJECT_REQUIRED,"用户名");
        String userName = request.getOutUserName();
        List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
        CheckUtil.check(userVOList != null && userVOList.size() == 1,MsgEnum.STATUS_ZT000001);
        UserVO userVO = userVOList.get(0);
        List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
        CheckUtil.check(accountChinapnrVOList != null && accountChinapnrVOList.size() == 1,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
        CheckUtil.check(accountVOList != null && accountVOList.size() == 1,MsgEnum.ERR_AMT_MONEY);
    }

    /**
     * 根据header中的userId获取登录admin用户信息
     *
     * @param userId 当前admin登录用户的userId
     * @return
     * @auth sunpeikai
     */
    @Override
    public AdminSystemVO getAdminSystemByUserId(Integer userId) {
        return amConfigClient.getUserInfoById(userId);
    }

    /**
     * 向数据库的ht_user_transfer表中插入数据
     *
     * @param request 用户转账-发起转账的参数
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertTransfer(CustomerTransferRequest request) {
        UserVO userVO = amUserClient.getUserByUserName(request.getOutUserName());
        request.setOutUserId(userVO.getUserId());
        return amTradeClient.insertUserTransfer(request);
    }

    /**
     * 根据transferId向用户发送转账邮件
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public void transferSendMail(Integer transferId) {
        // 如果id不为空
        CheckUtil.check(transferId != null, MsgEnum.STATUS_CE000001);
        UserTransferVO userTransferVO = amTradeClient.searchUserTransferById(transferId);
        CheckUtil.check(userTransferVO != null, MsgEnum.ERR_OBJECT_GET);
        UserVO userVO = amUserClient.searchUserByUserId(userTransferVO.getOutUserId());
        CheckUtil.check(null != userVO && StringUtils.isNotBlank(userVO.getEmail()), MsgEnum.ERR_OBJECT_REQUIRED, "用户邮箱");

        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("val_name", userTransferVO.getOutUserName());
        replaceMap.put("val_amount", userTransferVO.getTransferAmount().toString());
        replaceMap.put("remark", userTransferVO.getRemark());
        replaceMap.put("val_url", userTransferVO.getTransferUrl());
        String[] email = {userVO.getEmail()};
        MailMessage mailMessage = new MailMessage(null, replaceMap, "用户转账",null,null, email, CustomConstants.PARAM_TPL_TRANSFER, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
        // 发送
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));
        } catch (MQException e) {
            logger.error(e.getMessage());
        }
    }
}
