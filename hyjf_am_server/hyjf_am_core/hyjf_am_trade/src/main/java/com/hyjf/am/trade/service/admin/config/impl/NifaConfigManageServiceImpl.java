/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.config.impl;

import com.hyjf.am.trade.dao.mapper.auto.NifaFieldDefinitionMapper;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinition;
import com.hyjf.am.trade.dao.model.auto.NifaFieldDefinitionExample;
import com.hyjf.am.trade.service.admin.config.NifaConfigManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nxl
 * @version NifaConfigManageServiceImpl, v0.1 2018/8/15 14:41
 */
@Service
public class NifaConfigManageServiceImpl extends BaseServiceImpl implements NifaConfigManageService{
    @Autowired
    private NifaFieldDefinitionMapper nifaFieldDefinitionMapper;
    /**
     * 添加胡进字段定义
     * @param nifaFieldDefinition
     * @return
     */
    @Override
    public int insertNifaFieldDefinition(NifaFieldDefinition nifaFieldDefinition){
        return nifaFieldDefinitionMapper.insertSelective(nifaFieldDefinition);
    }
    /**
     * 显示字段定义列表
     * @param limtStart
     * @param limtEnd
     * @return
     */
    @Override
    public List<NifaFieldDefinition> selectNifaFieldDefinition(int limtStart, int limtEnd){
        NifaFieldDefinitionExample example = new NifaFieldDefinitionExample();
        example.setOrderByClause("create_time DESC");
        if (limtStart != -1) {
            example.setLimitStart(limtStart);
            example.setLimitEnd(limtEnd);
        }
        List<NifaFieldDefinition> nifaFieldDefinitionInterfaceList = nifaFieldDefinitionMapper.selectByExample(example);
        return nifaFieldDefinitionInterfaceList;
    }

    /**
     * 统计字段定义总数
     * @return
     */
    @Override
    public int countNifaFieldDefinition(){
        NifaFieldDefinitionExample example = new NifaFieldDefinitionExample();
        return nifaFieldDefinitionMapper.countByExample(example);
    }
    /**
     * 根据id查找互金字段表
     * @param nifaId
     * @return
     */
    @Override
    public NifaFieldDefinition selectFieldDefinitionById(String nifaId){
        if(StringUtils.isNotBlank(nifaId)){
            int id = Integer.parseInt(nifaId);
            NifaFieldDefinitionExample example = new NifaFieldDefinitionExample();
            example.createCriteria().andIdEqualTo(id);
            List<NifaFieldDefinition> nifaFieldDefinitionList = nifaFieldDefinitionMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(nifaFieldDefinitionList)){
                return nifaFieldDefinitionList.get(0);
            }
        }
        return null;
    }
    /**
     * 修改互金字段定义
     * @param nifaFieldDefinition
     * @return
     */
    @Override
    public int updateNifaFieldDefinition(NifaFieldDefinition nifaFieldDefinition){
        return nifaFieldDefinitionMapper.updateByPrimaryKeySelective(nifaFieldDefinition);
    }
}
