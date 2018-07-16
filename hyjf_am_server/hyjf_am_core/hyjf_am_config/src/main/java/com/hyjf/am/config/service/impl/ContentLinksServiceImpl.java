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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version ContentLinksServiceImpl, v0.1 2018/7/14 14:30
 */
@Service
public class ContentLinksServiceImpl implements ContentLinksService {
    @Autowired
    private LinkMapper linkMapper;

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
        record.setOrder(record.getOrder().intValue());
        record.setStatus(record.getStatus().intValue());
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
        record.setOrder(record.getOrder().intValue());
        record.setStatus(record.getStatus().intValue());
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
}
