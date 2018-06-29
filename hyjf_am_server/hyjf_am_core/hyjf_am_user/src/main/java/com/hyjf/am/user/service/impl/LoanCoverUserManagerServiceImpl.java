/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.user.dao.mapper.auto.LoanSubjectCertificateAuthorityMapper;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthority;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthorityExample;
import com.hyjf.am.user.service.LoanCoverUserManagerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class LoanCoverUserManagerServiceImpl implements LoanCoverUserManagerService {

    @Autowired
    public LoanSubjectCertificateAuthorityMapper loanSubjectCertificateAuthorityMapper;

    private static Logger logger = LoggerFactory.getLogger(LoanCoverUserManagerServiceImpl.class);


    /**
     * 根据筛选条件查找借款盖章用户
     *
     * @param lsca
     * @param limitStart
     * @param limitEnd
     * @param createStart
     * @param createEnd
     * @return
     */
    @Override
    public List<LoanSubjectCertificateAuthority> getRecordList(LoanCoverUserRequest lsca, int limitStart,
                                                               int limitEnd, int createStart, int createEnd) {
        LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
        example.setOrderByClause(" id desc");
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        LoanSubjectCertificateAuthorityExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (StringUtils.isNotEmpty(lsca.getName())) {
            criteria.andNameEqualTo(lsca.getName());
        }
        if (StringUtils.isNotEmpty(lsca.getMobile())) {
            criteria.andMobileEqualTo(lsca.getMobile());
        }
        if (StringUtils.isNotEmpty(lsca.getIdNo())) {
            criteria.andIdNoEqualTo(lsca.getIdNo());
        }
        if (createStart != 0 || createEnd != 0) {
          //  criteria.andCreateTimeBetween(createStart, createEnd);
        }
        if (lsca.getIdType() != null) {
            criteria.andIdTypeEqualTo(lsca.getIdType());
        }
        if (StringUtils.isNotEmpty(lsca.getCustomerId())) {
            criteria.andCustomerIdEqualTo(lsca.getCustomerId());
        }
        if (StringUtils.isNotEmpty(lsca.getCode())) {
            if ("2001".equals(lsca.getCode())) {
                List list = new ArrayList();
                list.add("2001");
                list.add("2002");
                list.add("2003");
                criteria.andCodeIn(list);
            }
            if ("2002".equals(lsca.getCode())) {
                criteria.andCodeEqualTo("");
            }
            if ("1000".equals(lsca.getCode())) {
                criteria.andCodeEqualTo(lsca.getCode());
            }

        }

        return loanSubjectCertificateAuthorityMapper.selectByExample(example);
    }

    /**
     * 根据条件获取记录数
     * @param lsca
     * @param createStart
     * @param createEnd
     * @return
     */
    @Override
    public int countLoanSubjectCertificateAuthority (LoanCoverUserRequest lsca,int createStart, int createEnd){
        LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();

        LoanSubjectCertificateAuthorityExample.Criteria criteria = example.createCriteria();
        // 条件查询
        if (StringUtils.isNotEmpty(lsca.getName())) {
            criteria.andNameEqualTo(lsca.getName());
        }
        if (StringUtils.isNotEmpty(lsca.getMobile())) {
            criteria.andMobileEqualTo(lsca.getMobile());
        }
        if (StringUtils.isNotEmpty(lsca.getIdNo())) {
            criteria.andIdNoEqualTo(lsca.getIdNo());
        }
        if (createStart != 0 || createEnd != 0) {
//            criteria.andCreateTimeBetween(createStart, createEnd);
        }
        if (lsca.getIdType() != null) {
            criteria.andIdTypeEqualTo(lsca.getIdType());
        }
        if (StringUtils.isNotEmpty(lsca.getCustomerId())) {
            criteria.andCustomerIdEqualTo(lsca.getCustomerId());
        }
        if (StringUtils.isNotEmpty(lsca.getCode())) {
            if ("2001".equals(lsca.getCode())) {
                List list = new ArrayList();
                list.add("2001");
                list.add("2002");
                list.add("2003");
                criteria.andCodeIn(list);
            }
            if ("2002".equals(lsca.getCode())) {
                criteria.andCodeEqualTo("");
            }
            if ("1000".equals(lsca.getCode())) {
                criteria.andCodeEqualTo(lsca.getCode());
            }

        }
        int intCount = loanSubjectCertificateAuthorityMapper.countByExample(example);
        return intCount;
    }

    /**
     * 保存借款盖章用户
     *
     * @param request
     * @return
     */
    @Override
    public int insertLoanCoverUserRecord(LoanCoverUserRequest request){
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = new LoanSubjectCertificateAuthority();
        BeanUtils.copyProperties(request, loanSubjectCertificateAuthority);
        int intInsertFlg = loanSubjectCertificateAuthorityMapper.insertSelective(loanSubjectCertificateAuthority);
        if(intInsertFlg> 0){
            logger.info("==================用户表变更保存成功!======");
        }else{
            throw new RuntimeException("============借款主体CA认证记录表插入失败!========");
        }
        return intInsertFlg;
    }
    /**
     * 根据证件号码查找借款主体CA认证记录表
     */
    @Override
    public LoanSubjectCertificateAuthority selectIsExistsRecordByIdNo(String record) {
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = new LoanSubjectCertificateAuthority();
        if (record == null) {
            return null;
        }
        LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
        example.or().andIdNoEqualTo(record);
        List<LoanSubjectCertificateAuthority> lll = loanSubjectCertificateAuthorityMapper.selectByExample(example);
        if(null!=lll&&lll.size()>0) {
            loanSubjectCertificateAuthority = lll.get(0);
            return new LoanSubjectCertificateAuthority();
        }
        return null;
    }
    /**
     * 根据证id查找借款主体CA认证记录表
     */
    @Override
    public LoanSubjectCertificateAuthority selectIsExistsRecordById(String id) {
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = new LoanSubjectCertificateAuthority();
        if (StringUtils.isBlank(id)) {
            return null;
        }
        int intId = Integer.parseInt(id);
        LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
        example.or().andIdEqualTo(intId);
        List<LoanSubjectCertificateAuthority> lll = loanSubjectCertificateAuthorityMapper.selectByExample(example);
        if(null!=lll&&lll.size()>0) {
            loanSubjectCertificateAuthority = lll.get(0);
            return loanSubjectCertificateAuthority;
        }
        return null;
    }

    /**
     * 更新借款盖章用户
     *
     * @param request
     * @return
     */
    @Override
    public int updateLoanCoverUserRecord(LoanCoverUserRequest request){
        LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = new LoanSubjectCertificateAuthority();
        BeanUtils.copyProperties(request, loanSubjectCertificateAuthority);
        int intInsertFlg = loanSubjectCertificateAuthorityMapper.updateByPrimaryKeySelective(loanSubjectCertificateAuthority);
        if(intInsertFlg> 0){
            logger.info("==================用户表变更保存成功!======");
        }else{
            throw new RuntimeException("============借款主体CA认证记录表更新失败!========");
        }
        return intInsertFlg;
    }

}
