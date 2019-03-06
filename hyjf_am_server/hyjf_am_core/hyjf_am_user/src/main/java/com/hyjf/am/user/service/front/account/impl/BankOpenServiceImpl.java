/**
 * 开户
 */
package com.hyjf.am.user.service.front.account.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankCardRequest;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.service.front.account.BankOpenService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.user.utils.IdCard15To18;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BankOpenServiceImpl extends BaseServiceImpl implements BankOpenService {
    private Logger logger = LoggerFactory.getLogger(BankOpenServiceImpl.class);
    
    @Autowired
    private CommonProducer commonProducer;

    @Override
    public boolean updateUserAccountLog(int userId, String userName, String mobile, String logOrderId, String clientPc, String name, String idno, String cardNo, String srvAuthCode) {
        Date date = new Date();
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(logOrderId);
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            BankOpenAccountLog openAccountLog = bankOpenAccountLogs.get(0);
            openAccountLog.setMobile(mobile);
            openAccountLog.setStatus(0);
            if(StringUtils.isNotBlank(srvAuthCode)){
                openAccountLog.setLastSrvAuthCode(srvAuthCode);
            }
            openAccountLog.setUpdateTime(date);
            openAccountLog.setUpdateUserId(userId);
            boolean updateFlag = this.bankOpenAccountLogMapper.updateByPrimaryKeySelective(openAccountLog) > 0 ? true
                    : false;
            if (updateFlag) {
                return true;
            } else {
                return false;
            }
        } else {
            BankOpenAccountLog bankOpenAccountLog = new BankOpenAccountLog();
            bankOpenAccountLog.setUserId(userId);
            bankOpenAccountLog.setUserName(userName);
            bankOpenAccountLog.setMobile(mobile);
            bankOpenAccountLog.setStatus(0);
            if(StringUtils.isNotBlank(srvAuthCode)){
                bankOpenAccountLog.setLastSrvAuthCode(srvAuthCode);
            }
            bankOpenAccountLog.setOrderId(logOrderId);
            bankOpenAccountLog.setCreateTime(date);
            bankOpenAccountLog.setCreateUserId(userId);
            bankOpenAccountLog.setName(name);
            bankOpenAccountLog.setIdNo(idno);
            bankOpenAccountLog.setCardNo(cardNo);
            bankOpenAccountLog.setClient(Integer.parseInt(clientPc));
            boolean flag = this.bankOpenAccountLogMapper.insertSelective(bankOpenAccountLog) > 0 ? true : false;
            return flag;
        }
    }

    /**
     * 更新开户日志表
     *
     * @param userId
     * @param logOrderId
     * @param status
     */
    @Override
    public void updateUserAccountLog(Integer userId, String logOrderId, int status , String retCode , String retMsg) {
        Date date = new Date();
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(logOrderId);
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            BankOpenAccountLog openAccountLog = bankOpenAccountLogs.get(0);
            // 更新开户状态
            openAccountLog.setStatus(status);
            openAccountLog.setUpdateTime(date);
            openAccountLog.setUpdateUserId(userId);
            openAccountLog.setRetCode(retCode);
            openAccountLog.setRetMsg(retMsg);
            this.bankOpenAccountLogMapper.updateByPrimaryKeySelective(openAccountLog);
        }

    }

    @Override
    public boolean updateUserAccount(Integer userId, String trueName, String orderId, String accountId, String idNo, Integer bankAccountEsb, String mobile, Integer roleId,Integer isSetPassword) {
        logger.info("开户成功后,更新用户账户信息 ");
        // 当前日期
        Date nowDate = new Date();
        // 查询开户记录表
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andOrderIdEqualTo(orderId);
        BankOpenAccountLog openAccountLog = new BankOpenAccountLog();
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            openAccountLog = bankOpenAccountLogs.get(0);
        }
        trueName = openAccountLog.getName();
        if(mobile==null|| "".equals(mobile)){
            mobile = openAccountLog.getMobile();
        }
        BankOpenAccountLogExample accountLogExample = new BankOpenAccountLogExample();
        accountLogExample.createCriteria().andUserIdEqualTo(userId);
        boolean deleteLogFlag = this.bankOpenAccountLogMapper.deleteByExample(accountLogExample) > 0 ? true : false;
        if (!deleteLogFlag) {
            throw new RuntimeException("删除用户开户日志表失败，用户开户订单号：" + orderId + ",用户userId:" + userId);
        }
        // 获取用户信息
        User user = this.getUsers(userId);
        // 用户名
        String userName = user.getUsername();
        logger.info("用户ID:" + userId + "],用户名:[" + userName + "],用户身份证号:[" + idNo + "]");
        // 根据身份证号获取用户相关信息
        String birthDayTemp = "";
        int sexInt = 1;
        if (null !=idNo && idNo.length() < 18) {
            try {
                idNo = IdCard15To18.getEighteenIDCard(idNo);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        if (null !=idNo){
            sexInt = Integer.parseInt(idNo.substring(16, 17));
        }
        if (sexInt % 2 == 0) {
            sexInt = 2;
        } else {
            sexInt = 1;
        }
        birthDayTemp = idNo.substring(6, 14);
        String birthDay = StringUtils.substring(birthDayTemp, 0, 4) + "-" + StringUtils.substring(birthDayTemp, 4, 6) + "-" + StringUtils.substring(birthDayTemp, 6, 8);
        user.setBankOpenAccount(1);
        user.setBankAccountEsb(bankAccountEsb);
        user.setRechargeSms(0);
        user.setWithdrawSms(0);
        user.setUserType(0);
        //user.setMobile(mobile);
        user.setIsSetPassword(isSetPassword);
        // 更新相应的用户表
        boolean usersFlag = usersMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
        if (!usersFlag) {
            logger.info("开户成功后,更新用户信息失败,用户ID:[" + userId + "]");
            throw new RuntimeException("更新用户表失败！");
        }
        // 根据用户ID查询用户信息表
        UserInfo userInfo = this.getUsersInfoByUserId(userId);
        if (userInfo == null) {
            logger.info("获取用户详情表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("根据用户ID,查询用户详情失败");
        }
        userInfo.setTruename(trueName);
        userInfo.setIdcard(idNo);
        userInfo.setSex(sexInt);
        userInfo.setBirthday(birthDay);
        userInfo.setTruenameIsapprove(1);
        userInfo.setMobileIsapprove(1);
        // 修改用户角色
        userInfo.setRoleId(roleId);
        // 更新用户详细信息表
        boolean userInfoFlag = usersInfoMapper.updateByPrimaryKeySelective(userInfo) > 0 ? true : false;
        if (!userInfoFlag) {
            logger.info("更新用户详细信息表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("更新用户详情表失败！");
        }
        // 插入银行账户关联表
        BankOpenAccount openAccount = new BankOpenAccount();
        openAccount.setUserId(userId);
        openAccount.setUserName(user.getUsername());
        openAccount.setAccount(accountId);
        openAccount.setCreateTime(nowDate);
        openAccount.setCreateUserId(userId);
        boolean openAccountFlag = this.bankOpenAccountMapper.insertSelective(openAccount) > 0 ? true : false;
        if (!openAccountFlag) {
            logger.info("开户成功后,插入用户银行账户关联表失败,用户ID:[" + userId + "]");
            throw new RuntimeException("插入用户银行账户关联表失败！");
        }

        // PC渠道统计表更新开户
        UtmReg utmReg = this.selectUtmRegByUserId(userId);
        if (utmReg != null) {
            utmReg.setBindCard(1);
            utmReg.setOpenAccount(1);
            this.utmRegMapper.updateByPrimaryKeySelective(utmReg);
        }

        /**
         * APP渠道统计明细更新-修改开户时间
         */
        try {
        	commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                    MQConstant.APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG,UUID.randomUUID().toString(), userId));
        } catch (MQException e) {
            logger.error("开户统计app渠道失败....", e);
        }

        // add 合规数据上报 埋点 liubin 20181122 start
        // 推送数据到MQ 开户 出借人
        if(roleId.compareTo(1) == 0){
            JSONObject params = new JSONObject();
            params.put("userId", userId);
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.OPEN_ACCOUNT_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                    MQConstant.HG_REPORT_DELAY_LEVEL);
        }
        // add 合规数据上报 埋点 liubin 20181122 end

        return openAccountFlag;
    }


    public User getUsers(Integer userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据用户ID取得用户信息
     *
     * @param userId
     * @return
     */
    public UserInfo getUsersInfoByUserId(Integer userId) {
        if (userId != null) {
            UserInfoExample example = new UserInfoExample();
            example.createCriteria().andUserIdEqualTo(userId);
            List<UserInfo> usersInfoList = this.usersInfoMapper.selectByExample(example);
            if (usersInfoList != null && usersInfoList.size() > 0) {
                return usersInfoList.get(0);
            }
        }
        return null;
    }

    /**
     * 根据用户ID检索PC渠道统计明细
     *
     * @param userId
     * @return
     */
    private UtmReg selectUtmRegByUserId(Integer userId) {
        UtmRegExample example = new UtmRegExample();
        UtmRegExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<UtmReg> list = this.utmRegMapper.selectByExample(example);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserInfo findUserInfoByCradId(String cardNo) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria cra = example.createCriteria();
        cra.andIdcardEqualTo(cardNo);

        List<UserInfo> list = usersInfoMapper.selectByExample(example);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 根据用户Id检索用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankCard selectBankCardByUserId(Integer userId) {
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        // 银行卡是否有效 0无效 1有效
        cra.andStatusEqualTo(1);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }

    /**
     * 根据用户Id检索用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<BankCard> selectBankCardByUserIdAndStatus(Integer userId) {
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        // 银行卡是否有效 0无效 1有效
        cra.andStatusEqualTo(0);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        return bankCardList;
    }

    /**
     * 根据银行卡号,用户Id检索用户银行卡信息
     *
     * @param userId
     * @param cardNo
     * @return
     */
    @Override
    public BankCard getBankCardByCardNo(Integer userId, String cardNo) {
        BankCardExample example = new BankCardExample();
        BankCardExample.Criteria cra = example.createCriteria();
        // 用户Id
        cra.andUserIdEqualTo(userId);
        // 银行卡号
        cra.andCardNoEqualTo(cardNo);
        // 银行卡是否有效 0无效 1有效
        cra.andStatusEqualTo(1);
        List<BankCard> bankCardList = bankCardMapper.selectByExample(example);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }



    /**
     * @Description 开户成功后保存银行卡信息
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/5 14:02
     */
    @Override
    public boolean saveCardNoToBank(BankCardRequest request) {
        BankCard card = new BankCard();
        BeanUtils.copyProperties(request, card);
        boolean bankCardFlag = this.bankCardMapper.insertSelective(card) > 0 ? true : false;
        return bankCardFlag;
    }

    /**
     * @param logOrdId
     * @Description 根据订单号查询失败原因
     * @Author sunss
     * @Date 2018/6/21 15:52
     */
    @Override
    public String getBankOpenAccountFiledMess(String logOrdId) {
        BankOpenAccountLogExample example = new BankOpenAccountLogExample();
        example.createCriteria().andOrderIdEqualTo(logOrdId);
        List<BankOpenAccountLog> bankOpenAccountLogs = this.bankOpenAccountLogMapper.selectByExample(example);
        if (bankOpenAccountLogs != null && bankOpenAccountLogs.size() == 1) {
            return bankOpenAccountLogs.get(0).getRetMsg();
        }
        return null;
    }

    /**
     * 获取银行卡信息
     * @param userId
     * @param status
     * @return
     */
    @Override
    public List<BankCard> selectBankCardByUserIdAndStatus(Integer userId, Integer status) {
        BankCardExample accountBankExample = new BankCardExample();
        BankCardExample.Criteria aCriteria = accountBankExample.createCriteria();
        aCriteria.andUserIdEqualTo(userId);
        aCriteria.andStatusEqualTo(1);
        return this.bankCardMapper.selectByExample(accountBankExample);
    }


}
