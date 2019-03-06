/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.LinkMapper;
import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.config.dao.model.auto.LinkExample;
import com.hyjf.am.config.service.ContentLinksService;
import com.hyjf.am.resquest.admin.ContentLinksRequest;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version ContentLinksServiceImpl, v0.1 2018/7/14 14:30
 */
@Service
public class ContentLinksServiceImpl implements ContentLinksService {

    private static final Logger logger = LoggerFactory.getLogger(ContentLinksServiceImpl.class);
    @Autowired
    private LinkMapper linkMapper;

    /**
     * 获取文章列表列表
     *
     * @return
     */
    @Override
    public List<Link> getRecordList(ContentLinksRequest bean, int limitStart, int limitEnd) {
        LinkExample example = new LinkExample();

        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        LinkExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(bean.getType());
        // 条件查询
        if (StringUtils.isNotEmpty(bean.getSearchWebname())) {
            criteria.andWebnameLike("%" + bean.getSearchWebname() + "%");
        }
        if (bean.getStatus() != null) {
            criteria.andStatusEqualTo(bean.getStatus());
        }
        if (StringUtils.isNotEmpty(bean.getStartCreate())) {
            try {
                criteria.andCreateTimeGreaterThanOrEqualTo
                        (GetDate.parseDate(GetDate.getDayStart(bean.getStartCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        if (StringUtils.isNotEmpty(bean.getEndCreate())) {
            try {
                criteria.andCreateTimeLessThanOrEqualTo
                (GetDate.parseDate(GetDate.getDayEnd(bean.getEndCreate()),GetDate.datetimeFormat_key));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }
        }
        example.setOrderByClause("`partner_type` ASC,`order` Asc ,`create_time` Desc");
        return linkMapper.selectByExample(example);
    }

    @Override
    public List<Link> searchAction(ContentLinksRequest request) {
        LinkExample example = new LinkExample();
        LinkExample.Criteria criteria = example.createCriteria();
        if (request.getType() != null) {
            criteria.andTypeEqualTo(request.getType());
        }
        if (StringUtils.isNotEmpty(request.getWebname())) {
            criteria.andWebnameLike("%" + request.getWebname() + "%");
        }
        if (request.getStatus() != null) {
            criteria.andStatusEqualTo(request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getStartTime())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
        }
        if (StringUtils.isNotBlank(request.getEndTime())) {
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
        }
        example.setOrderByClause("`partner_type` ASC,`order` Asc,`create_time` Desc");
        return linkMapper.selectByExample(example);
    }

    @Override
    public void insertAction(ContentLinksRequest request) {
        Link record = new Link();
        BeanUtils.copyProperties(request, record);
        // 友情链接1
        record.setType(1);
        record.setOrder(request.getOrder().intValue());
        record.setStatus(request.getStatus().intValue());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        fillEmpty(record);
        linkMapper.insertSelective(record);
    }

    @Override
    public void updateAction(ContentLinksRequest request) {
        Link record = new Link();
        BeanUtils.copyProperties(request, record);
        // 友情链接1
        record.setType(1);
        record.setUpdateTime(new Date());
        fillEmpty(record);
        linkMapper.updateByPrimaryKey(record);
    }

    @Override
    public Link getRecord(Integer id) {
        return linkMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        linkMapper.deleteByPrimaryKey(id);
    }

    /**
     * 填充空值
     *
     * @param record
     */
    private void fillEmpty(Link record) {
        if (StringUtils.isEmpty(record.getPhone())) {
            record.setPhone("");
        }
        if (StringUtils.isEmpty(record.getAddress())) {
            record.setAddress("");
        }
        if (StringUtils.isEmpty(record.getSetupTime())) {
            record.setSetupTime("");
        }
        if (StringUtils.isEmpty(record.getCooperationTime())) {
            record.setCooperationTime("");
        }
        if (record.getPartnerType() == null) {
            record.setPartnerType(0);
        }
        if (record.getHits() == null) {
            record.setHits(0);
        }
        if (record.getIsindex() == null) {
            record.setIsindex(0);
        }
    }
    /**
     * 合作机构
     * @return
     */
    @Override
    public List<Link> getLinks() {
        LinkExample example = new LinkExample();
        LinkExample.Criteria cra = example.createCriteria();
        cra.andTypeEqualTo(2);
        cra.andPartnerTypeEqualTo(7);

        List<Link> links = linkMapper.selectByExample(example);

        if (links != null && links.size() > 0) {
            return links;
        }
        return null;
    }
}
