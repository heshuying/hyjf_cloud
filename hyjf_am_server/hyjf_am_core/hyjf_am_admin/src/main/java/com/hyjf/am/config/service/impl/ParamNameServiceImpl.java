package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.ParamNameMapper;
import com.hyjf.am.config.dao.mapper.customize.ParamNameCustomizeMapper;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.config.dao.model.auto.ParamNameExample;
import com.hyjf.am.config.dao.model.auto.ParamNameKey;
import com.hyjf.am.config.service.ParamNameService;
import com.hyjf.am.resquest.admin.AdminParamNameRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class ParamNameServiceImpl implements ParamNameService {
    @Autowired
    private ParamNameMapper paramNameMapper;
    @Autowired
    private ParamNameCustomizeMapper paramNameCustomizeMapper;
    /**
     * 子账户类型 查询
     * @return
     */
    @Override
    public List<ParamName> getParamNameList(String code){
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(code);
        cra.andDelFlagEqualTo(0);
        example.setOrderByClause(" sort ASC ");
        return paramNameMapper.selectByExample(example);
    }
    @Override
    public List<ParamName> getNameCd(String code){
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameClassEqualTo(code);
        return paramNameMapper.selectByExample(example);
    }
    /**
     *（条件）列表查询--其他相关字段
     * @return
     */
    @Override
    public List<ParamNameVO>  selectProjectTypeParamList(){
        List<ParamNameVO> paramNameVOS =new ArrayList<>();
        List<ParamName> borrowProjectTypes=  paramNameCustomizeMapper.selectProjectTypeParamList();
        if(!CollectionUtils.isEmpty(borrowProjectTypes)){
            paramNameVOS= CommonUtils.convertBeanList(borrowProjectTypes,ParamNameVO.class);
        }
        return paramNameVOS;
    }

    /**
     * 查询数据字典数量
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int getParamNamesCount(AdminParamNameRequest request) {
        ParamNameExample example = convertExample(request);
        return paramNameMapper.countByExample(example);
    }
    /**
     * 查询数据字典列表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<ParamName> searchParamNamesList(AdminParamNameRequest request) {
        ParamNameExample example = convertExample(request);
        return paramNameMapper.selectByExample(example);
    }

    private ParamNameExample convertExample(AdminParamNameRequest request){
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(request.getNameClassSrch())){
            criteria.andNameClassLike("%"+request.getNameClassSrch()+"%");
        }
        if(StringUtils.isNotBlank(request.getNameCdSrch())){
            criteria.andNameCdLike("%"+request.getNameCdSrch()+"%");
        }
        if(StringUtils.isNotBlank(request.getNameSrch())){
            criteria.andNameLike("%"+request.getNameSrch()+"%");
        }
        // 数据字典列表不需要排序
        //example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return example;
    }
    /**
     * 检查数据库是否已存在该数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public boolean isExistsParamName(ParamNameVO paramNameVO) {
        ParamNameExample example = new ParamNameExample();
        ParamNameExample.Criteria cra = example.createCriteria();
        cra.andNameCdEqualTo(paramNameVO.getNameCd());
        cra.andNameClassEqualTo(paramNameVO.getNameClass());
        List<ParamName> list = paramNameMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }
    /**
     * 插入数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int insertParamName(ParamNameVO paramNameVO) {
        // 如果重新生成mapper，这里可能会有问题。因为auto生成的mapper把nameCd当成是主键，所以不会插入nameCd，数据库会报错：name_cd没有默认值
        ParamName paramName = CommonUtils.convertBean(paramNameVO,ParamName.class);
        return paramNameMapper.insertSelective(paramName);
    }
    /**
     * 修改数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateParamName(ParamNameVO paramNameVO) {
        ParamName paramName = CommonUtils.convertBean(paramNameVO,ParamName.class);
        paramName.setCreateTime(null);
        return paramNameMapper.updateByPrimaryKeySelective(paramName);
    }
    /**
     * 根据联合主键查询数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ParamName searchParamNameByKey(ParamNameVO paramNameVO) {
        ParamNameKey paramNameKey = new ParamNameKey();
        paramNameKey.setNameClass(paramNameVO.getNameClass());
        paramNameKey.setNameCd(paramNameVO.getNameCd());
        return paramNameMapper.selectByPrimaryKey(paramNameKey);
    }
    /**
     * 删除数据字典
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int deleteParamName(ParamNameVO paramNameVO) {
        ParamNameKey paramNameKey = new ParamNameKey();
        paramNameKey.setNameClass(paramNameVO.getNameClass());
        paramNameKey.setNameCd(paramNameVO.getNameCd());
        return paramNameMapper.deleteByPrimaryKey(paramNameKey);
    }
}
