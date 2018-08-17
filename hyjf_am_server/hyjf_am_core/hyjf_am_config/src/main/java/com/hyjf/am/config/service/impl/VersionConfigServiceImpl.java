package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.VersionMapper;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.dao.model.auto.VersionExample;
import com.hyjf.am.config.service.VersionConfigService;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/16.
 */
@Service
public class VersionConfigServiceImpl implements VersionConfigService {

    @Autowired
    private VersionMapper versionMapper;

    /**
     *  查询版本配置列表条数
     * @return
     */
    @Override
    public Integer getVersionConfigCount(AdminVersionRequest request){
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        if(StringUtils.isNotEmpty(request.getNameSrh())){
            cra.andTypeEqualTo(Integer.parseInt(request.getNameSrh()));
        }
        if(StringUtils.isNotEmpty(request.getVersionSrh())){
            cra.andVersionLike(request.getVersionSrh());
        }
        return versionMapper.countByExample(example);
    }
    /**
     *  查询版本配置列表
     * @return
     */
    @Override
    public List<Version> getVersionConfigListByPage(AdminVersionRequest version, int limitStart, int limitEnd){
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

    /*
     *根据id查询版本配置
     * */
    @Override
    public VersionVO getVersionConfigInfoById(Integer userId){
        VersionVO versionVO =new VersionVO();
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(userId);
        List<Version> list = versionMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            BeanUtils.copyProperties(list.get(0),versionVO);
            return versionVO;
        }
        return new VersionVO();
    }


    /**
     * 添加版本配置
     * @param req
     */
    @Override
    public Integer insertVersionConfig(AdminVersionRequest req) {
        Version record = new Version();
        BeanUtils.copyProperties(req, record);
        record.setIsUpdate(req.getIsupdate());
        record.setType(req.getType());
        record.setVersion(req.getVersion());
        record.setUrl(req.getUrl());
        record.setContent(req.getContent());
        return versionMapper.insertSelective(record);
    }

    /**
     * 修改版本配置
     * @param req
     */
    @Override
    public Integer updateVersionConfig( AdminVersionRequest req) {
        Version record = new Version();
        BeanUtils.copyProperties(req, record);
        return versionMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除版本配置
     * @param id
     */
    @Override
    public void deleteVersionConfig( Integer id) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        cra.andIdEqualTo(id);
        versionMapper.deleteByExample(example);
    }

    /**
     * 校验版本配置当前系统版本号是否唯一
     * @param map
     */
    @Override
    public VersionVO validationFeild(Map map) {
        VersionExample example = new VersionExample();
        VersionExample.Criteria cra = example.createCriteria();
        if(map.get("vid") != null){
            cra.andIdNotEqualTo((Integer)map.get("vid"));
        }
        cra.andTypeEqualTo((Integer)map.get("type"));
        cra.andVersionEqualTo((String) map.get("version"));
        List<Version> list = versionMapper.selectByExample(example);
        if(list != null && list.size() > 0){
            VersionVO record = new VersionVO();
            BeanUtils.copyProperties(list.get(0), record);
            return record;
        }
        return null;
    }
}
