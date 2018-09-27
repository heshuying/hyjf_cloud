/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsMailTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplate;
import com.hyjf.am.config.dao.model.auto.SmsMailTemplateExample;
import com.hyjf.am.config.service.SmsMailTemplateService;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version SmsMailTemplateServiceImpl, v0.1 2018/5/8 16:58
 */
@Service
public class SmsMailTemplateServiceImpl implements SmsMailTemplateService {

    @Autowired
    private SmsMailTemplateMapper smsMailTemplateMapper;

    @Override
    public SmsMailTemplate findSmsMailTemplateByCode(String mailCode) {
        SmsMailTemplate smsMailTemplate = RedisUtils.getObj(RedisConstants.SMS_MAIL_TEMPLATE + mailCode, SmsMailTemplate.class);
        if (smsMailTemplate == null) {
            SmsMailTemplateExample example = new SmsMailTemplateExample();
            example.createCriteria().andMailValueEqualTo(mailCode);
            List<SmsMailTemplate> smsMailTemplateList = smsMailTemplateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(smsMailTemplateList)) {
                smsMailTemplate = smsMailTemplateList.get(0);
                RedisUtils.setObjEx(RedisConstants.SMS_MAIL_TEMPLATE + mailCode, smsMailTemplate, 24*60*60);
                return smsMailTemplate;
            }
        }
        return smsMailTemplate;
    }

    @Override
    public List<SmsMailTemplate> findAll() {
        return smsMailTemplateMapper.selectByExample(new SmsMailTemplateExample());
    }

    @Override
    public List<SmsMailTemplate> findSmsTemplate(MailTemplateRequest request) {
        SmsMailTemplateExample example = new SmsMailTemplateExample();
        SmsMailTemplateExample.Criteria criteria = example.createCriteria();
        if (request != null) {
            if (StringUtils.isNotBlank(request.getMailName())) {
                criteria.andMailNameEqualTo(request.getMailName());
            }
            if (request.getMailStatus() != null) {
                criteria.andMailStatusEqualTo(request.getMailStatus());
            }
            if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
                int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
                int limitEnd = request.getPageSize();
                example.setLimitStart(limitStart);
                example.setLimitEnd(limitEnd);
            }
            return smsMailTemplateMapper.selectByExample(example);
        }
        return null;
    }

    @Override
    public int insertMailTemplate(MailTemplateRequest request) {
        if (request != null) {
            SmsMailTemplate smsMailTemplate = new SmsMailTemplate();
            BeanUtils.copyProperties(request, smsMailTemplate);
            return smsMailTemplateMapper.insertSelective(smsMailTemplate);
        }
        return 0;
    }

    @Override
    public int updateMailTemplate(MailTemplateRequest request) {
        if (request != null) {
            SmsMailTemplate smsMailTemplate = new SmsMailTemplate();
            BeanUtils.copyProperties(request, smsMailTemplate);
            return smsMailTemplateMapper.updateByPrimaryKeySelective(smsMailTemplate);
        }
        return 0;
    }

    @Override
    public int updateStatus(MailTemplateRequest request) {
        if (request.getId() != null && request.getMailStatus() != null) {
            SmsMailTemplate smsMailTemplate = smsMailTemplateMapper.selectByPrimaryKey(request.getId());
            smsMailTemplate.setMailStatus(request.getMailStatus());
            return smsMailTemplateMapper.updateByPrimaryKeySelective(smsMailTemplate);
        }
        return 0;
    }

    @Override
    public void openMailTemplate(MailTemplateRequest request) {
        SmsMailTemplate smt = new SmsMailTemplate();
        smt.setMailValue(request.getMailValue());
        smt.setId(request.getId());
        smt.setMailStatus(1);
        smsMailTemplateMapper.updateByPrimaryKey(smt);
    }

    @Override
    public int selectCount(MailTemplateRequest request) {
        SmsMailTemplateExample example = new SmsMailTemplateExample();
        SmsMailTemplateExample.Criteria criteria = example.createCriteria();
        if (request != null) {
            if (StringUtils.isNotBlank(request.getMailName())) {
                criteria.andMailNameEqualTo(request.getMailName());
            }
            if (request.getMailStatus() != null) {
                criteria.andMailStatusEqualTo(request.getMailStatus());
            }
            return smsMailTemplateMapper.countByExample(example);
        }
        return 0;
    }

    @Override
    public SmsMailTemplate findByid(Integer id) {
        if (id != null) {
            return smsMailTemplateMapper.selectByPrimaryKey(id);
        }
        return null;
    }
}
