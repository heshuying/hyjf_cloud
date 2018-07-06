/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.CustomerTransferService;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.AccountChinapnrVO;
import com.hyjf.am.vo.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 9:15
 */
@Service
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 根据筛选条件查询Transfer列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public Integer getTransferCount(CustomerTransferListRequest request) {
        return amTradeClient.getUserTransferCount(request);
    }

    /**
     * 根据筛选条件查询userTransfer列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<UserTransferVO> searchUserTransferList(CustomerTransferListRequest request) {
        return amTradeClient.searchUserTransferList(request);
    }

    /**
     * 根据nameClass获取数据字典表的下拉列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<ParamNameVO> searchParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }

    /**
     * 根据用户名查询账户余额
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject searchBalanceByUsername(String userName) {
        JSONObject jsonObject = new JSONObject();
        List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
        logger.info("admin 项目 查询出来的userList.size=[{}]",userVOList.size());
        if(userVOList != null && userVOList.size() == 1){
            UserVO userVO = userVOList.get(0);
            List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
            if(accountChinapnrVOList != null && accountChinapnrVOList.size() == 1){
                List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
                if (accountVOList != null && accountVOList.size() == 1) {
                    AccountVO accountVO = accountVOList.get(0);
                    jsonObject.put("status","0");
                    jsonObject.put("result",accountVO.getBalance().toString());
                } else {
                    jsonObject.put("status","error");
                    jsonObject.put("result","未查询到正确的余额信息");
                }
            }else{
                jsonObject.put("status","error");
                jsonObject.put("result","用户未开户，无法转账");
            }
        }else{
            jsonObject.put("status","error");
            jsonObject.put("result","未查询到正确的用户信息");
        }
        return jsonObject;
    }

    /**
     * 参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject checkCustomerTransferParam(CustomerTransferRequest request) {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotEmpty(request.getOutUserName())){
            String userName = request.getOutUserName();
            List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
            if(userVOList != null && userVOList.size() == 1){
                UserVO userVO = userVOList.get(0);
                List<AccountChinapnrVO> accountChinapnrVOList = amUserClient.searchAccountChinapnrByUserId(userVO.getUserId());
                if(accountChinapnrVOList != null && accountChinapnrVOList.size() == 1){
                    List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(userVO.getUserId());
                    if(accountVOList != null && accountVOList.size() == 1){
                        jsonObject.put("status","0");
                        jsonObject.put("result","校验参数成功");
                    }else{
                        jsonObject.put("status","error");
                        jsonObject.put("result","未查询到正确的余额信息");
                    }
                }else{
                    jsonObject.put("status","error");
                    jsonObject.put("result","用户未开户，无法转账");
                }
            }else{
                jsonObject.put("status","error");
                jsonObject.put("result","未查询到正确的用户信息");
            }
        }else{
            jsonObject.put("status","error");
            jsonObject.put("result","用户名不能为空");
        }
        return jsonObject;
    }

    /**
     * 根据header中的userId获取登录admin用户信息
     * @auth sunpeikai
     * @param userId 当前admin登录用户的userId
     * @return
     */
    @Override
    public AdminSystemVO getAdminSystemByUserId(Integer userId) {
        return amConfigClient.getUserInfoById(userId);
    }

    /**
     * 向数据库的ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 用户转账-发起转账的参数
     * @return
     */
    @Override
    public boolean insertTransfer(CustomerTransferRequest request) {
        return amTradeClient.insertUserTransfer(request);
    }

    /**
     * 根据transferId向用户发送转账邮件
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JSONObject transferSendMail(Integer transferId) {
        JSONObject jsonObject = new JSONObject();
        // 如果id不为空
        if(transferId != null){
            UserTransferVO userTransferVO = amTradeClient.searchUserTransferById(transferId);
            if(userTransferVO != null){
                UserVO userVO = amUserClient.searchUserByUserId(userTransferVO.getOutUserId());
                if (null != userVO && StringUtils.isNotBlank(userVO.getEmail())) {
                    Map<String, String> replaceMap = new HashMap<String, String>();
                    replaceMap.put("val_name", userTransferVO.getOutUserName());
                    replaceMap.put("val_amount", userTransferVO.getTransferAmount().toString());
                    replaceMap.put("remark", userTransferVO.getRemark());
                    replaceMap.put("val_url", userTransferVO.getTransferUrl());
                    String[] email = { userVO.getEmail() };
                    //MailMessage messageMail = new MailMessage(null, replaceMap, "用户转账",null,null, email,CustomConstants.PARAM_TPL_TRANSFER, MessageDefine.MAILSENDFORMAILINGADDRESS);
                    //TODO:发送email
                    //mailMessageProcesser.gather(messageMail);
                    jsonObject.put("status", "0");
                    jsonObject.put("result", "邮件发送成功");
                }else{
                    jsonObject.put("status", "0");
                    jsonObject.put("result", "用户邮箱为空不能发送邮件");
                }
            }else{
                jsonObject.put("status", "error");
                jsonObject.put("result", "未查询到转账信息");
            }
        }else{
            // 如果id为空
            jsonObject.put("status", "error");
            jsonObject.put("result", "参数错误无法发送邮件");
        }
        return jsonObject;
    }
}
