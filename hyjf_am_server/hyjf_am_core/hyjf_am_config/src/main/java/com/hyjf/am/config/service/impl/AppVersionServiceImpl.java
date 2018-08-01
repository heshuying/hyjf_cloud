package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.VersionMapper;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.dao.model.auto.VersionExample;
import com.hyjf.am.config.dao.model.customize.VersionConfigBean;
import com.hyjf.am.config.service.AppVersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version AppVersionServiceImpl, v0.1 2018/7/14 11:46
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    VersionMapper versionMapper;
    /**
     * 获取记录数
     */
    @Override
    public Integer countRecord(VersionConfigBean version) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(version.getNameSrh())){
            cra.andTypeEqualTo(Integer.parseInt(version.getNameSrh()));
        }
        if(StringUtils.isNotEmpty(version.getVersionSrh())){
            cra.andVersionLike(version.getVersionSrh());
        }
        return versionMapper.countByExample(example);
    }

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<Version> getRecordList(VersionConfigBean version, int limitStart, int limitEnd) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(version.getNameSrh())){
            cra.andTypeEqualTo(Integer.parseInt(version.getNameSrh()));
        }
        if(StringUtils.isNotEmpty(version.getVersionSrh())){
            cra.andVersionLike(version.getVersionSrh());
        }
        example.setOrderByClause("id desc");
        if (limitStart != -1) {
            example.setLimitStart(limitStart);
            example.setLimitEnd(limitEnd);
        }
        return versionMapper.selectByExample(example);
    }

    /**
     * 获取单个信息
     *
     * @return
     */
    @Override
    public Version getRecord(Integer id) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(id);
        List<Version> list = versionMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new Version();
    }


    /**
     * 插入记录
     */
    @Override
    public void insertRecord(VersionConfigBean form) {
        Version record = new Version();
        BeanUtils.copyProperties(form, record);
        record.setIsUpdate(form.getIsUpdate());
        record.setType(form.getType());
        record.setVersion(form.getVersion());
        record.setUrl(form.getUrl());
        record.setContent(form.getContent());
        versionMapper.insertSelective(record);
    }

    /**
     * 更新记录
     */
    @Override
    public void updateRecord(VersionConfigBean form) {
        Version record = new Version();
        BeanUtils.copyProperties(form, record);
        versionMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 批量删除
     */
    @Override
    public void deleteRecord(List<Integer> ids) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        cra.andIdIn(ids);
        versionMapper.deleteByExample(example);
    }

    @Override
    public Version getNewVersionByType(Integer type) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        cra.andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        List<Version> list = versionMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new Version();
    }

    @Override
    public Version getUpdateversion(Integer type, Integer isupdate, String versionStr) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cri = example.createCriteria();
        cri.andTypeEqualTo(type);
        if(isupdate!=null){
            cri.andIsUpdateEqualTo(isupdate);
        }

        if(versionStr!=null&&!versionStr.equals("null")&&!versionStr.equals("")){
            cri.andVersionEqualTo(versionStr);
        }
        example.setOrderByClause(" id desc ");
        List<Version> list = versionMapper.selectByExample(example);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null ;
    }
}
