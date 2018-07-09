/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.batch;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.producer.FddCertificateProducer;
import com.hyjf.am.user.service.batch.FddCertificateService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;

/**
 * @author zhangqingqing
 * @version FddCertificateServiceImpl, v0.1 2018/6/27 17:00
 */
@Service
public class FddCertificateServiceImpl extends BaseServiceImpl implements FddCertificateService{
    private static final Logger logger = LoggerFactory.getLogger(FddCertificateServiceImpl.class);

    @Autowired
    FddCertificateProducer fddProducer;

    @Override
    public void fddCertificateAuthority() throws ParseException, MQException {
        List<User> usersList = findAllUser();
        if (usersList != null && usersList.size() > 0) {
            logger.info("用户批量CA认证数:[" + usersList.size() + "].");
            String startTime = GetDate.dateToString(new Date());
            // 循环去做CA认证
            for (User user : usersList) {
                FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
                fddCertificateAuthorityVO.setUserId( user.getUserId());
                fddProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(fddCertificateAuthorityVO)));
            }
            // 处理结束时间
            String endTime = GetDate.dateToString(new Date());
            // 处理用时
            String consumeTime = GetDate.countTime(GetDate.stringToDate(startTime), GetDate.stringToDate(endTime));
            logger.info("处理用时:" + startTime + "减去" + endTime + "等于" + consumeTime);
        }
    }

    @Override
    public void updateUserCAInfo(Integer userId, User user, UserInfo userInfo) throws Exception {
        logger.info("CA认证用户ID:[" + userId + "].");
        // 根据用户ID查询用户CA认证信息
        CertificateAuthority certificateAuthority = selectCAInfoByUserId(userId);
        // 用户类型 0普通用户 1企业用户
        Integer userType = user.getUserType();
        // 手机号
        String mobile = user.getMobile();
        // 邮箱
        String email = user.getEmail();
        // 普通用户CA认证
        if (userType == 0) {
            // 参数生成
            DzqzCallBean bean = new DzqzCallBean();
            bean.setUserId(userId);
            bean.setLogordid(GetOrderIdUtils.getOrderId0(userId));
            bean.setTxCode("syncPerson_auto");
            bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
            bean.setV(DzqzConstant.HYJF_FDD_VERSION);
            bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
            // 客户姓名
            bean.setCustomer_name(userInfo.getTruename());
            bean.setEmail(StringUtils.isNotBlank(user.getEmail()) ? user.getEmail() : "");
            // 证件类型
            bean.setIdent_type("0");
            // 身份证号
            bean.setIdCard(userInfo.getIdcard());
            bean.setMobile(user.getMobile());
            // 调用接口
            DzqzCallBean result = DzqzCallUtil.callApiBg(bean);
            if (result != null) {
                logger.info("CA认证成功:用户ID:[" + userId + "].");
                if ("success".equals(result.getResult())) {
                    user.setIsCaFlag(1);
                    boolean isUserUpdateFlag = this.userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
                    if (!isUserUpdateFlag) {
                        logger.info("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                    }
                }
                //  如果用户没有进行过CA认证
                if (certificateAuthority == null) {
                    CertificateAuthority newCertificateAuthority = new CertificateAuthority();
                    newCertificateAuthority.setUserId(userId);
                    newCertificateAuthority.setUserName(user.getUsername());
                    newCertificateAuthority.setMobile(mobile);
                    newCertificateAuthority.setTrueName(userInfo.getTruename());
                    newCertificateAuthority.setIdType(0);
                    newCertificateAuthority.setIdNo(userInfo.getIdcard());
                    newCertificateAuthority.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());
                    newCertificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    // 状态码
                    newCertificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());
                    newCertificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());
                    // 描述
                    newCertificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());
                    newCertificateAuthority.setCreateTime(new Date());
                    newCertificateAuthority.setCreateUserId(userId);
                    newCertificateAuthority.setUpdateTime(new Date());
                    newCertificateAuthority.setUpdateUserId(userId);
                    boolean isInsertFlag = this.certificateAuthorityMapper.insertSelective(newCertificateAuthority) > 0 ? true : false;
                    if (!isInsertFlag) {
                        logger.info("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                    }
                } else {
                    // 如果用户已进行过CA认证,更新用户CA认证记录
                    certificateAuthority.setMobile(mobile);
                    certificateAuthority.setTrueName(userInfo.getTruename());
                    certificateAuthority.setIdType(0);
                    certificateAuthority.setIdNo(userInfo.getIdcard());
                    certificateAuthority.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());
                    certificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    certificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());
                    certificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());
                    certificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());
                    certificateAuthority.setUpdateTime(new Date());
                    certificateAuthority.setUpdateUserId(userId);
                    boolean isUpdateFlag = this.certificateAuthorityMapper.updateByPrimaryKeySelective(certificateAuthority) > 0 ? true : false;
                    if (!isUpdateFlag) {
                        logger.info("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                    }
                }
            }
        } else {
            // 企业用户CA认证
            CorpOpenAccountRecord corpOpenAccountRecord = getCorpOpenAccountRecord(userId);
            if (corpOpenAccountRecord == null) {
                logger.info("获取企业信息失败,企业用户ID:[" + userId + "].");
                throw new Exception("获取企业信息失败,企业用户ID:[" + userId + "].");
            }
            // 组织机构代码
            String busiCode = corpOpenAccountRecord.getBusiCode();
            // 调用法大大企业CA认证接口
            // 参数生成
            DzqzCallBean bean = new DzqzCallBean();
            bean.setUserId(userId);
            bean.setLogordid(GetOrderIdUtils.getOrderId0(userId));
            bean.setTxCode("syncCompany_auto");
            bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
            bean.setV(DzqzConstant.HYJF_FDD_VERSION);
            bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
            bean.setCustomer_name(userInfo.getTruename());
            bean.setEmail(StringUtils.isNotBlank(user.getEmail()) ? user.getEmail() : "");
            bean.setIdCard(busiCode);
            bean.setMobile(user.getMobile());
            // 调用接口
            DzqzCallBean result = DzqzCallUtil.callApiBg(bean);
            if (result != null) {
                // CA认证成功后,更新用户表
                if ("success".equals(result.getResult())) {
                    logger.info("CA认证成功:用户ID:[" + userId + "].");
                    user.setIsCaFlag(1);
                    boolean isUserUpdateFlag = this.userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
                    if (!isUserUpdateFlag) {
                        logger.info("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                    }
                }
                // 如果没有做过CA认证
                if (certificateAuthority == null) {
                    // 插入CA认证记录
                    CertificateAuthority newCertificateAuthority = new CertificateAuthority();
                    newCertificateAuthority.setUserId(userId);
                    newCertificateAuthority.setUserName(user.getUsername());
                    newCertificateAuthority.setMobile(mobile);
                    newCertificateAuthority.setTrueName(corpOpenAccountRecord.getBusiName());
                    newCertificateAuthority.setIdType(1);
                    newCertificateAuthority.setIdNo(corpOpenAccountRecord.getBusiCode());
                    newCertificateAuthority.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());
                    newCertificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    newCertificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());
                    newCertificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());
                    newCertificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());
                    newCertificateAuthority.setCreateTime(new Date());
                    newCertificateAuthority.setCreateUserId(userId);
                    newCertificateAuthority.setUpdateTime(new Date());
                    newCertificateAuthority.setUpdateUserId(userId);
                    boolean isInsertFlag = this.certificateAuthorityMapper.insertSelective(newCertificateAuthority) > 0 ? true : false;
                    if (!isInsertFlag) {
                        logger.info("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                    }
                } else {
                    // 如果已经做过CA认证,更新CA认证记录表
                    certificateAuthority.setMobile(mobile);
                    certificateAuthority.setTrueName(userInfo.getTruename());
                    certificateAuthority.setIdType(1);
                    certificateAuthority.setIdNo(corpOpenAccountRecord.getBusiCode());
                    certificateAuthority.setEmail(StringUtils.isBlank(user.getEmail()) ? "" : user.getEmail());
                    certificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    certificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());
                    certificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());
                    certificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());
                    certificateAuthority.setUpdateTime(new Date());
                    certificateAuthority.setUpdateUserId(userId);
                    boolean isUpdateFlag = this.certificateAuthorityMapper.updateByPrimaryKeySelective(certificateAuthority) > 0 ? true : false;
                    if (!isUpdateFlag) {
                        logger.info("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                        throw new Exception("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                    }
                }
            }
        }
    }
}
