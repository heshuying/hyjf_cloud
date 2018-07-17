/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.handle;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.util.DzqzCallUtil;
import com.hyjf.pay.lib.fadada.util.DzqzConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: sunpeikai
 * @version: FddCertificateAuthorityMessageHandle, v0.1 2018/7/17 9:48
 */
@Component
public class FddCertificateAuthorityMessageHandle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmUserClient amUserClient;

    public UserVO getUserByUserId(Integer userId){
        return amUserClient.findUserById(userId);
    }

    public UserInfoVO getUserInfoByUserId(Integer userId){
        return amUserClient.findUsersInfoById(userId);
    }

    public boolean updateUserCAInfo(Integer userId){
        boolean isUpdate = false;

        logger.info("CA认证用户ID:[" + userId + "].");
        // 当前时间
        Date nowTime = GetDate.getNowTime();
        // 根据用户ID获取用户信息
        UserVO userVO = amUserClient.findUserById(userId);
        if (userVO == null) {
            logger.info("根据用户ID获取用户信息失败,用户ID:[" + userId + "].");
            return isUpdate;
        }
        // 根据用户ID获取用户详情信息
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        if (userInfoVO == null) {
            logger.info("根据用户ID获取用户详情信息失败,用户ID:[" + userId + "].");
            return isUpdate;
        }

        // 根据用户ID查询用户CA认证信息
        CertificateAuthorityVO certificateAuthorityVO = amUserClient.selectCertificateAuthorityByUserId(String.valueOf(userId));
        // 用户类型 0普通用户 1企业用户
        Integer userType = userVO.getUserType();
        // 手机号
        String mobile = userVO.getMobile();
        // 邮箱
        String email = userVO.getEmail();
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
            bean.setCustomer_name(userInfoVO.getTruename());// 客户姓名
            bean.setEmail(StringUtils.isNotBlank(userVO.getEmail()) ? userVO.getEmail() : "");// 电子邮箱
            bean.setIdent_type("0");// 证件类型
            bean.setIdCard(userInfoVO.getIdcard());// 身份证号
            bean.setMobile(userVO.getMobile());// 手机号
            // 调用接口
            DzqzCallBean result = DzqzCallUtil.callApiBg(bean);
            if (result != null) {
                logger.info("CA认证成功:用户ID:[" + userId + "].");
                if ("success".equals(result.getResult())) {
                    userVO.setIsCaFlag(1);
                    isUpdate  = amUserClient.updateByPrimaryKeySelective(userVO);
                    if (!isUpdate) {
                        logger.info("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                        return isUpdate;
                    }
                }
                //  如果用户没有进行过CA认证
                if (certificateAuthorityVO == null) {
                    CertificateAuthorityVO newCertificateAuthority = new CertificateAuthorityVO();
                    newCertificateAuthority.setUserId(userId);
                    newCertificateAuthority.setUserName(userVO.getUsername());
                    newCertificateAuthority.setMobile(mobile);
                    newCertificateAuthority.setTrueName(userInfoVO.getTruename());
                    newCertificateAuthority.setIdType(0);
                    newCertificateAuthority.setIdNo(userInfoVO.getIdcard());
                    newCertificateAuthority.setEmail(StringUtils.isBlank(userVO.getEmail()) ? "" : userVO.getEmail());
                    newCertificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    newCertificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());// 状态码
                    newCertificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());// 客户编号
                    newCertificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());// 描述
                    newCertificateAuthority.setCreateTime(nowTime);
                    newCertificateAuthority.setCreateUserId(userId);
                    newCertificateAuthority.setUpdateTime(nowTime);
                    newCertificateAuthority.setUpdateUserId(userId);
                    isUpdate = amUserClient.insertCertificateAuthority(newCertificateAuthority) > 0;
                    if (!isUpdate) {
                        logger.info("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                        return isUpdate;
                    }
                } else {
                    // 如果用户已进行过CA认证,更新用户CA认证记录
                    certificateAuthorityVO.setMobile(mobile);
                    certificateAuthorityVO.setTrueName(userInfoVO.getTruename());
                    certificateAuthorityVO.setIdType(0);
                    certificateAuthorityVO.setIdNo(userInfoVO.getIdcard());
                    certificateAuthorityVO.setEmail(StringUtils.isBlank(userVO.getEmail()) ? "" : userVO.getEmail());
                    certificateAuthorityVO.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    certificateAuthorityVO.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());// 状态码
                    certificateAuthorityVO.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());// 客户编号
                    certificateAuthorityVO.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());// 描述
                    certificateAuthorityVO.setUpdateTime(nowTime);
                    certificateAuthorityVO.setUpdateUserId(userId);
                    isUpdate = amUserClient.updateCertificateAuthority(certificateAuthorityVO) > 0;
                    if (!isUpdate) {
                        logger.info("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                        return isUpdate;
                    }
                }
            }
        } else {
            // 企业用户CA认证
            CorpOpenAccountRecordVO corpOpenAccountRecordVO = amUserClient.selectCorpOpenAccountRecordByUserId(userId);
            if (corpOpenAccountRecordVO == null) {
                logger.info("获取企业信息失败,企业用户ID:[" + userId + "].");
                return false;
            }
            // 组织机构代码
            String busiCode = corpOpenAccountRecordVO.getBusiCode();
            // 调用法大大企业CA认证接口
            // 参数生成
            DzqzCallBean bean = new DzqzCallBean();
            bean.setUserId(userId);
            bean.setLogordid(GetOrderIdUtils.getOrderId0(userId));
            bean.setTxCode("syncCompany_auto");
            bean.setApp_id(DzqzConstant.HYJF_FDD_APP_ID);
            bean.setV(DzqzConstant.HYJF_FDD_VERSION);
            bean.setTimestamp(GetDate.getDate("yyyyMMddHHmmss"));
            bean.setCustomer_name(userInfoVO.getTruename());// 客户姓名
            bean.setEmail(StringUtils.isNotBlank(userVO.getEmail()) ? userVO.getEmail() : "");// 电子邮箱
            bean.setIdCard(busiCode);// 组织机构代码
            bean.setMobile(userVO.getMobile());// 手机号
            // 调用接口
            DzqzCallBean result = DzqzCallUtil.callApiBg(bean);
            if (result != null) {
                // CA认证成功后,更新用户表
                if ("success".equals(result.getResult())) {
                    logger.info("CA认证成功:用户ID:[" + userId + "].");
                    userVO.setIsCaFlag(1);
                    boolean isUserUpdateFlag = amUserClient.updateByPrimaryKeySelective(userVO);
                    if (!isUserUpdateFlag) {
                        logger.info("CA认证成功后,更新用户表CA标识失败,用户ID:[" + userId + "].");
                        return isUserUpdateFlag;
                    }
                }

                // 如果没有做过CA认证
                if (certificateAuthorityVO == null) {
                    // 插入CA认证记录
                    CertificateAuthorityVO newCertificateAuthority = new CertificateAuthorityVO();
                    newCertificateAuthority.setUserId(userId);
                    newCertificateAuthority.setUserName(userVO.getUsername());
                    newCertificateAuthority.setMobile(mobile);
                    newCertificateAuthority.setTrueName(corpOpenAccountRecordVO.getBusiName());
                    newCertificateAuthority.setIdType(1);
                    newCertificateAuthority.setIdNo(corpOpenAccountRecordVO.getBusiCode());
                    newCertificateAuthority.setEmail(StringUtils.isBlank(userVO.getEmail()) ? "" : userVO.getEmail());
                    newCertificateAuthority.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    newCertificateAuthority.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());// 状态码
                    newCertificateAuthority.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());// 客户编号
                    newCertificateAuthority.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());// 描述
                    newCertificateAuthority.setCreateTime(nowTime);
                    newCertificateAuthority.setCreateUserId(userId);
                    newCertificateAuthority.setUpdateTime(nowTime);
                    newCertificateAuthority.setUpdateUserId(userId);
                    boolean isInsertFlag = amUserClient.insertCertificateAuthority(newCertificateAuthority) > 0;
                    if (!isInsertFlag) {
                        logger.info("CA认证成功后,插入用户CA认证记录表失败,用户ID:[" + userId + "].");
                        return isInsertFlag;
                    }
                } else {
                    // 如果已经做过CA认证,更新CA认证记录表
                    certificateAuthorityVO.setMobile(mobile);
                    certificateAuthorityVO.setTrueName(userInfoVO.getTruename());
                    certificateAuthorityVO.setIdType(1);
                    certificateAuthorityVO.setIdNo(corpOpenAccountRecordVO.getBusiCode());
                    certificateAuthorityVO.setEmail(StringUtils.isBlank(userVO.getEmail()) ? "" : userVO.getEmail());
                    certificateAuthorityVO.setStatus(StringUtils.isBlank(result.getResult()) ? "" : result.getResult());
                    certificateAuthorityVO.setCode(StringUtils.isBlank(result.getCode()) ? "" : result.getCode());// 状态码
                    certificateAuthorityVO.setCustomerId(StringUtils.isBlank(result.getCustomer_id()) ? "" : result.getCustomer_id());// 客户编号
                    certificateAuthorityVO.setRemark(StringUtils.isBlank(result.getMsg()) ? "" : result.getMsg());// 描述
                    certificateAuthorityVO.setUpdateTime(nowTime);
                    certificateAuthorityVO.setUpdateUserId(userId);
                    boolean isUpdateFlag = amUserClient.updateCertificateAuthority(certificateAuthorityVO) > 0;
                    if (!isUpdateFlag) {
                        logger.info("CA认证成功后,更新用户CA认证记录表失败,用户ID:[" + userId + "].");
                        return isUpdateFlag;
                    }
                }
            }
        }
        return isUpdate;
    }

}
