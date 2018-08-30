/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CustomerTransferListRequest;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.admin.UserTransferVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferService, v0.1 2018/7/6 9:15
 */
public interface CustomerTransferService {

    /**
     * 根据筛选条件查询Transfer列表
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getTransferCount(CustomerTransferListRequest request);

    /**
     * 根据筛选条件查询userTransfer列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<UserTransferVO> searchUserTransferList(CustomerTransferListRequest request);

    /**
     * 根据nameClass获取数据字典表的下拉列表
     * @auth sunpeikai
     * @param
     * @return
     */
    List<ParamNameVO> searchParamNameList(String nameClass);

    /**
     * 根据userName查询用户余额
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject searchBalanceByUsername(String userName);

    /**
     * 参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    void checkCustomerTransferParam(CustomerTransferRequest request);

    /**
     * 根据header中的userId获取登录admin用户信息
     * @auth sunpeikai
     * @param userId 当前admin登录用户的userId
     * @return
     */
    AdminSystemVO getAdminSystemByUserId(Integer userId);

    /**
     * 向数据库的ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 用户转账-发起转账的参数
     * @return
     */
    boolean insertTransfer(CustomerTransferRequest request);

    /**
     * 根据transferId向用户发送转账邮件
     * @auth sunpeikai
     * @param
     * @return
     */
    void transferSendMail(Integer transferId);
}
