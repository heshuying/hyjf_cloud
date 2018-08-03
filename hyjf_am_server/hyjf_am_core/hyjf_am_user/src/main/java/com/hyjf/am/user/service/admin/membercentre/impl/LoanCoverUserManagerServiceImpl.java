/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.membercentre.impl;

import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.user.dao.mapper.auto.CertificateAuthorityMapper;
import com.hyjf.am.user.dao.mapper.auto.LoanSubjectCertificateAuthorityMapper;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CertificateAuthorityExample;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthority;
import com.hyjf.am.user.dao.model.auto.LoanSubjectCertificateAuthorityExample;
import com.hyjf.am.user.service.admin.membercentre.LoanCoverUserManagerService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author nxl
 * @version UserManagerServiceImpl, v0.1 2018/6/20 9:49
 *          后台管理系统 ：会员中心->会员管理 接口实现
 */
@Service
public class LoanCoverUserManagerServiceImpl implements LoanCoverUserManagerService {

    @Autowired
    private LoanSubjectCertificateAuthorityMapper loanSubjectCertificateAuthorityMapper;
    @Autowired
    private CertificateAuthorityMapper certificateAuthorityMapper;

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
                                                               int limitEnd, Date createStart, Date createEnd) {
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
        if (createStart != null || createEnd != null) {
            criteria.andCreateTimeBetween(createStart, createEnd);
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
    public int countLoanSubjectCertificateAuthority (LoanCoverUserRequest lsca, Date createStart, Date createEnd){
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        if (createStart!=null||createEnd!=null) {
           criteria.andCreateTimeBetween(createStart, createEnd);
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
            logger.info("==================借款主体CA认证记录表保存成功!======");
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
        example.createCriteria().andIdEqualTo(Integer.parseInt(record));
        List<LoanSubjectCertificateAuthority> lll = loanSubjectCertificateAuthorityMapper.selectByExample(example);
        if(null!=lll&&lll.size()>0) {
            loanSubjectCertificateAuthority = lll.get(0);
            return loanSubjectCertificateAuthority;
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

    /**
     * 根据证件号码和姓名查找用户CA认证记录表
     * @param idno
     * @param tureName
     * @return
     */
    @Override
    public CertificateAuthority selectCertificateAuthorityByIdNoName(String idno, String tureName) {
        CertificateAuthorityExample example=new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria criteria = example.createCriteria();
        criteria.andTrueNameEqualTo(tureName);
        criteria.andIdNoEqualTo(idno);
        List<CertificateAuthority> cam = certificateAuthorityMapper.selectByExample(example);
        if(!cam.isEmpty()&&cam.get(0).getTrueName().equals(tureName)&&cam.get(0).getIdNo().equals(idno)&&!cam.get(0).getCustomerId().isEmpty()) {
            return cam.get(0);
        }else {
            return null;
        }
    }
  	/**
  	 * 根据社会统一信用代码或身份证号查询用户是否做过CA认证
  	 * @param idNo
  	 * @param name
  	 * @return
  	 */
  	@Override
  	public boolean isCAIdNoCheck(String idNo, String name) {

  		LoanSubjectCertificateAuthorityExample example = new LoanSubjectCertificateAuthorityExample();
  		LoanSubjectCertificateAuthorityExample.Criteria cra = example.createCriteria();
  		cra.andIdNoEqualTo(idNo);
  		List<LoanSubjectCertificateAuthority> loanSubjectlist = this.loanSubjectCertificateAuthorityMapper.selectByExample(example);

  		CertificateAuthorityExample caExample = new CertificateAuthorityExample();
  		CertificateAuthorityExample.Criteria caCra = caExample.createCriteria();
  		caCra.andIdNoEqualTo(idNo);
  		List<CertificateAuthority> caList = this.certificateAuthorityMapper.selectByExample(caExample);

  		LoanSubjectCertificateAuthority loanSubjectCertificateAuthority = null;
  		if(loanSubjectlist!=null && loanSubjectlist.size()>0){
  			loanSubjectCertificateAuthority = loanSubjectlist.get(0);
  		}
  		CertificateAuthority certificateAuthority = null;
  		if (caList != null && caList.size()>0){
  			certificateAuthority = caList.get(0);
  		}
  		if (loanSubjectCertificateAuthority == null && certificateAuthority == null ){
  			return false;
  		}
  		return true;
  	}
}
