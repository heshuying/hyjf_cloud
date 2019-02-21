package com.hyjf.am.user.service.front.ca.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CertificateAuthorityExample;
import com.hyjf.am.user.service.front.ca.CertificateAuthorityExceptionService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * CA认证异常Service实现类
 *
 * @author dongzeshan
 */
@Service
public class CertificateAuthorityExceptionServiceImpl extends BaseServiceImpl implements CertificateAuthorityExceptionService {
	  private static final Logger logger = LoggerFactory.getLogger(CertificateAuthorityExceptionServiceImpl.class);

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 检索CA异常件数
     *
     * @param form
     * @return
     */
    @Override
    public Integer countCANOExceptionList(CertificateAuthorityExceptionRequest form) {

    	CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        // 用户名不为空
        if (StringUtils.isNotBlank(form.getUserNameSrch())) {
            cra.andUserNameLike("%" + form.getUserNameSrch() + "%");
        }
        // 用户手机号
        if (StringUtils.isNotBlank(form.getMobileSrch())) {
            cra.andMobileLike("%" + form.getMobileSrch() + "%");
        }
        // 姓名
        if (StringUtils.isNotBlank(form.getTrueNameSrch())) {
            cra.andTrueNameLike("%" + form.getTrueNameSrch() + "%");
        }
        // 用户类型
        if (StringUtils.isNotBlank(form.getIdTypeSrch())) {
            cra.andIdTypeEqualTo(Integer.valueOf(form.getIdTypeSrch()));
        }
        // 客户编号
        if (StringUtils.isNotBlank(form.getCustomerIdSrch())) {
            cra.andCustomerIdEqualTo(form.getCustomerIdSrch());
        }
        // 检索条件转账时间开始
        if (StringUtils.isNotBlank(form.getStartTimeSrch())) {
            try {
				cra.andCreateTimeGreaterThanOrEqualTo(GetDate.parseDate(form.getStartTimeSrch() + " 00:00:00","yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        // 检索条件转账时间结束
        if (StringUtils.isNotBlank(form.getEndTimeSrch())) {
            try {
				cra.andCreateTimeLessThanOrEqualTo(GetDate.parseDate(form.getEndTimeSrch() + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }

        cra.andCodeEqualTo("1000");
        return this.certificateAuthorityMapper.countByExample(example);
    }


    /**
     * 检索CA认证列表
     *
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<CertificateAuthority> getCANOExceptionList(CertificateAuthorityExceptionRequest form, int limitStart, int limitEnd) {

        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        // 用户名不为空
        if (StringUtils.isNotBlank(form.getUserNameSrch())) {
            cra.andUserNameLike("%" + form.getUserNameSrch() + "%");
        }
        // 用户手机号
        if (StringUtils.isNotBlank(form.getMobileSrch())) {
            cra.andMobileLike("%" + form.getMobileSrch() + "%");
        }
        // 姓名
        if (StringUtils.isNotBlank(form.getTrueNameSrch())) {
            cra.andTrueNameLike("%" + form.getTrueNameSrch() + "%");
        }
        // 用户类型
        if (StringUtils.isNotBlank(form.getIdTypeSrch())) {
            cra.andIdTypeEqualTo(Integer.valueOf(form.getIdTypeSrch()));
        }
        // 客户编号
        if (StringUtils.isNotBlank(form.getCustomerIdSrch())) {
            cra.andCustomerIdEqualTo(form.getCustomerIdSrch());
        }
        // 检索条件转账时间开始
        if (StringUtils.isNotBlank(form.getStartTimeSrch())) {
            try {
				cra.andCreateTimeGreaterThanOrEqualTo(GetDate.parseDate(form.getStartTimeSrch() + " 00:00:00","yyyy-MM-dd HH:mm:ss"));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        // 检索条件转账时间结束
        if (StringUtils.isNotBlank(form.getEndTimeSrch())) {
            try {
				cra.andCreateTimeLessThanOrEqualTo(GetDate.parseDate(form.getEndTimeSrch() + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
		
				e.printStackTrace();
			}
        }
        cra.andCodeEqualTo("1000");
        //1000正常 其他的不正常
        if (limitStart >= 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return this.certificateAuthorityMapper.selectByExample(example);
    }


	/**
	 * 发送CA认证MQ
	 * 
	 * @param userId
	 */
	@Override
	public void updateUserCAMQ(int userId) throws ParseException, MQException {
		// add by liuyang 20180209 开户成功后,将用户ID加入到CA认证消息队列 start
		// 加入到消息队列

		String startTime = GetDate.dateToString(new Date());
		// 循环去做CA认证

		FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
		fddCertificateAuthorityVO.setUserId(userId);
        commonProducer.messageSend(new MessageContent(MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC,
				UUID.randomUUID().toString(), fddCertificateAuthorityVO));

		// 处理结束时间
		String endTime = GetDate.dateToString(new Date());
		// 处理用时
		String consumeTime = GetDate.countTime(GetDate.stringToDate(startTime), GetDate.stringToDate(endTime));
		logger.info("处理用时:" + startTime + "减去" + endTime + "等于" + consumeTime);
	}

    /**
     * 获取CA认证异常count
     * @param form
     * @return
     */
    @Override
    public Integer countCAExceptionList(CertificateAuthorityExceptionRequest form) {
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        // 用户名不为空
        if (StringUtils.isNotBlank(form.getUserNameSrch())) {
            cra.andUserNameLike("%" + form.getUserNameSrch() + "%");
        }
        // 用户手机号
        if (StringUtils.isNotBlank(form.getMobileSrch())) {
            cra.andMobileLike("%" + form.getMobileSrch() + "%");
        }
        // 姓名
        if (StringUtils.isNotBlank(form.getTrueNameSrch())) {
            cra.andTrueNameLike("%" + form.getTrueNameSrch() + "%");
        }
        // 用户类型
        if (StringUtils.isNotBlank(form.getIdTypeSrch())) {
            cra.andIdTypeEqualTo(Integer.valueOf(form.getIdTypeSrch()));
        }
        // 客户编号
        if (StringUtils.isNotBlank(form.getCustomerIdSrch())) {
            cra.andCustomerIdEqualTo(form.getCustomerIdSrch());
        }
        // 检索条件转账时间开始
        if (StringUtils.isNotBlank(form.getStartTimeSrch())) {
            try {
                cra.andCreateTimeGreaterThanOrEqualTo(GetDate.parseDate(form.getStartTimeSrch() + " 00:00:00","yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 检索条件转账时间结束
        if (StringUtils.isNotBlank(form.getEndTimeSrch())) {
            try {
                cra.andCreateTimeLessThanOrEqualTo(GetDate.parseDate(form.getEndTimeSrch() + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //检索条件（状态）
        if(StringUtils.isNotBlank(form.getStatusSrch())){
            cra.andCodeEqualTo(form.getStatusSrch());
        }

        cra.andCodeNotEqualTo("1000");
        return this.certificateAuthorityMapper.countByExample(example);
    }

    /**
     * 获取CA认证异常列表
     * @param form
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<CertificateAuthority> getCAExceptionList(CertificateAuthorityExceptionRequest form, int limitStart, int limitEnd) {
        CertificateAuthorityExample example = new CertificateAuthorityExample();
        CertificateAuthorityExample.Criteria cra = example.createCriteria();
        // 用户名不为空
        if (StringUtils.isNotBlank(form.getUserNameSrch())) {
            cra.andUserNameLike("%" + form.getUserNameSrch() + "%");
        }
        // 用户手机号
        if (StringUtils.isNotBlank(form.getMobileSrch())) {
            cra.andMobileLike("%" + form.getMobileSrch() + "%");
        }
        // 姓名
        if (StringUtils.isNotBlank(form.getTrueNameSrch())) {
            cra.andTrueNameLike("%" + form.getTrueNameSrch() + "%");
        }
        // 用户类型
        if (StringUtils.isNotBlank(form.getIdTypeSrch())) {
            cra.andIdTypeEqualTo(Integer.valueOf(form.getIdTypeSrch()));
        }
        // 客户编号
        if (StringUtils.isNotBlank(form.getCustomerIdSrch())) {
            cra.andCustomerIdEqualTo(form.getCustomerIdSrch());
        }
        // 检索条件转账时间开始
        if (StringUtils.isNotBlank(form.getStartTimeSrch())) {
            try {
                cra.andCreateTimeGreaterThanOrEqualTo(GetDate.parseDate(form.getStartTimeSrch() + " 00:00:00","yyyy-MM-dd HH:mm:ss"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // 检索条件转账时间结束
        if (StringUtils.isNotBlank(form.getEndTimeSrch())) {
            try {
                cra.andCreateTimeLessThanOrEqualTo(GetDate.parseDate(form.getEndTimeSrch() + " 23:59:59","yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

        //检索条件（状态）
        if(StringUtils.isNotBlank(form.getStatusSrch())){
            cra.andCodeEqualTo(form.getStatusSrch());
        }

        cra.andCodeNotEqualTo("1000");
        //1000正常 其他的不正常
        if (limitStart >= 0) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return this.certificateAuthorityMapper.selectByExample(example);
    }


}
