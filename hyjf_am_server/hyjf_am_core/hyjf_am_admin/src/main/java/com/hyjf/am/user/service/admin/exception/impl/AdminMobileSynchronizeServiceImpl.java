/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.exception.impl;

import com.hyjf.am.admin.config.SystemConfig;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserChangeLog;
import com.hyjf.am.user.dao.model.auto.UserChangeLogExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.customize.MobileSynchronizeCustomize;
import com.hyjf.am.user.dao.model.customize.UserInfoForLogCustomize;
import com.hyjf.am.user.service.admin.exception.AdminMobileSynchronizeService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: AdminMobileSynchronizeServiceImpl, v0.1 2018/8/13 14:32
 */
@Service(value = "userAdminMobileSynchronizeServiceImpl")
public class AdminMobileSynchronizeServiceImpl extends BaseServiceImpl implements AdminMobileSynchronizeService {

    public static final Integer CHANGELOG_TYPE_IDCARD = 3;
    public static final Integer CHANGELOG_TYPE_USERINFO = 2;
    public static final Integer CHANGELOG_TYPE_RECOMMEND = 1;

    private static final Logger logger = LoggerFactory.getLogger(AdminMobileSynchronizeServiceImpl.class);

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 查询手机号同步数量  用于前端分页显示
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int countBankOpenAccountUser(MobileSynchronizeRequest request) {
        return mobileSynchronizeCustomizeMapper.countBankOpenAccountUser(request);
    }

    /**
     * 查询手机号同步列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<MobileSynchronizeCustomize> selectBankOpenAccountUserList(MobileSynchronizeRequest request) {
        return mobileSynchronizeCustomizeMapper.selectBankOpenAccountUserList(request);
    }

    /**
     * 同步手机号
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean updateMobile(MobileSynchronizeRequest request) throws Exception {

        String userId = request.getUserId();
        String accountId = request.getAccountId();

        // 根据用户ID查询用户当前手机号
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(userId));
        if (user == null) {
            throw new Exception("获取用户信息失败,用户ID:" + userId);
        }
        String mobile = user.getMobile();
        // 调用银行接口查询电子账户手机号
        // 获取共同参数
        String bankCode = systemConfig.getBankBankcode();
        String instCode = systemConfig.getBankInstcode();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderId = GetOrderIdUtils.getOrderId2(Integer.parseInt(userId));
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_MOBILE_MAINTAINACE);// 消息类型(用户开户)
        bean.setInstCode(instCode);// 机构代码
        bean.setBankCode(bankCode);
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setAccountId(accountId);
        bean.setOption("0");
        bean.setLogUserId(userId);
        bean.setLogOrderId(orderId);
        bean.setLogOrderDate(orderDate);
        bean.setLogRemark("电子账户手机号查询");
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (Validator.isNotNull(resultBean)) {
                String retCode = StringUtils.isNotBlank(resultBean.getRetCode()) ? resultBean.getRetCode() : "";
                // 如果返回是成功
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    // 获取银行返回的手机号
                    String bankMobile = resultBean.getMobile();
                    // 如果本地记录的手机号跟银行的手机号不一致,更新本地记录的手机号
                    if (!bankMobile.equals(mobile)) {
                        user.setMobile(bankMobile);
                        boolean isMobileUpdateFlag = this.userMapper.updateByPrimaryKeySelective(user) > 0;
                        if (!isMobileUpdateFlag) {
                            throw new Exception("更新用户手机号失败,用户ID:" + userId);
                        }
                        // 获取用户详情
                        UserInfo usersInfo = this.findUsersInfo(Integer.parseInt(userId));
                        // 插入手机号修改记录表
                        UserChangeLog changeLog = new UserChangeLog();
                        List<UserInfoForLogCustomize> userInfoForLog = userCustomizeMapper.selectUserByUserId(Integer.parseInt(userId));
                        if (userInfoForLog != null && userInfoForLog.size() == 1) {
                            UserInfoForLogCustomize logRecord = userInfoForLog.get(0);
                            changeLog.setUserId(logRecord.getUserId());
                            changeLog.setUsername(logRecord.getUserName());
                            changeLog.setAttribute(logRecord.getAttribute());
                            changeLog.setIs51(logRecord.getIs51());
                            changeLog.setRealName(logRecord.getRealName());
                            changeLog.setRecommendUser(logRecord.getRecommendName());
                            changeLog.setUpdateType(CHANGELOG_TYPE_USERINFO);
                            changeLog.setMobile(logRecord.getMobile());
                            changeLog.setRole(logRecord.getUserRole());
                            changeLog.setStatus(logRecord.getUserStatus());
                            UserChangeLogExample logExample = new UserChangeLogExample();
                            UserChangeLogExample.Criteria logCriteria = logExample.createCriteria();
                            logCriteria.andUserIdEqualTo(Integer.parseInt(userId));
                            int count = userChangeLogMapper.countByExample(logExample);
                            if (count <= 0) {
                                // 如果从来没有添加过操作日志，则将原始信息插入修改日志中
                                if (userInfoForLog != null && !userInfoForLog.isEmpty()) {
                                    changeLog.setRemark("初始注册");
                                    // 这里是什么
                                    changeLog.setUpdateUserId(1);
                                    changeLog.setUpdateUser("system");
                                    changeLog.setUpdateTime(logRecord.getRegTime());
                                    boolean isInsertFlag = userChangeLogMapper.insertSelective(changeLog) > 0;
                                    if (!isInsertFlag) {
                                        throw new Exception("插入修改记录失败");
                                    }
                                }
                            }
                            // 保存用户信息修改日志
                            AdminSystemVO adminSystemVO = request.getAdminSystemVO();
                            // 插入一条用户信息修改日志
                            changeLog.setMobile(user.getMobile());
                            changeLog.setStatus(user.getStatus());
                            changeLog.setRole(usersInfo.getRoleId());
                            changeLog.setUpdateUser(adminSystemVO.getUsername());
                            changeLog.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));
                            changeLog.setRemark("江西银行手机号同步");
                            changeLog.setUpdateTime(GetDate.getNowTime());
                            boolean isUsersChangeLogFlag = userChangeLogMapper.insertSelective(changeLog) > 0;
                            if (!isUsersChangeLogFlag) {
                                throw new Exception("插入修改日志失败");
                            }
                            return isUsersChangeLogFlag;
                        }
                    } else {
                        // 如果手机号相同,不用同步
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }
}
