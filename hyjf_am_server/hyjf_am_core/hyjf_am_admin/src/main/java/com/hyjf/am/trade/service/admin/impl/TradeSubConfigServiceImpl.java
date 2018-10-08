package com.hyjf.am.trade.service.admin.impl;

import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.trade.dao.mapper.customize.SubCommissionListConfigCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.SubCommissionListConfig;
import com.hyjf.am.trade.service.admin.TradeSubConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/9.
 */
@Service
public class TradeSubConfigServiceImpl implements TradeSubConfigService {
    @Autowired
    private SubCommissionListConfigCustomizeMapper subCommissionListConfigCustomizeMapper;
    /*
    * 查询分账名单记录总数
    */
    @Override
    public Integer getSubConfigListCountByPage(Map<String, Object> conditionMap){
        return subCommissionListConfigCustomizeMapper.getSubConfigListCountByPage(conditionMap);
    }
    /*
    * 分页查询分账名单记录
    */
    @Override
    public List<SubCommissionListConfig> getSubConfigListByPage(Map<String, Object> conditionMap, int limitStart, int limitEnd){
        if (limitStart == 0 || limitStart > 0) {
            conditionMap.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            conditionMap.put("limitEnd", limitEnd);
        }
        return subCommissionListConfigCustomizeMapper.getSubConfigListByPage(conditionMap);
    }
    /*
      * 根据id查询分账名单记录
      * */
    @Override
    public SubCommissionListConfig getSubConfigRecordById(Integer id){
        return subCommissionListConfigCustomizeMapper.getSubConfigRecordById(id);
    }

    /**
     *
     * 插入操作
     * @param form
     * @return
     */
    @Override
    public int insertSubConfigRecord(AdminSubConfigRequest form){
        SubCommissionListConfig record = new SubCommissionListConfig();
        form.setCreateUserId(form.getUserId());
//       用户角色1,'投资人'     2, '借款人'    3,'垫付机构'
        if(StringUtils.isNotBlank(form.getRoleName())){
            if (form.getRoleName().equals("1")){
                form.setRoleName("投资人");
            }else if(form.getRoleName().equals("2")){
                form.setRoleName("借款人");
            }else{
                form.setRoleName("垫付机构");
            }
        }
        //       用户类型0,'个人用户'     1, '企业用户'
        if(StringUtils.isNotBlank(form.getUserType())){
            if (form.getUserType().equals("0")){
                form.setUserType("个人用户");
            }else{
                form.setUserType("企业用户");
            }
        }
        BeanUtils.copyProperties(form, record);
        record.setCreateTime(new Date());
        return subCommissionListConfigCustomizeMapper.insertSubConfig(record);
    }
    /**
     *
     * 更新操作
     * @return
     */
    @Override
    public int updateSubConfigRecord(AdminSubConfigRequest form){
        SubCommissionListConfig record = new SubCommissionListConfig();
        form.setUpdateUserId(form.getUserId());
        //       用户角色1,'投资人'     2, '借款人'    3,'垫付机构'
        if(StringUtils.isNotBlank(form.getRoleName())){
            if (form.getRoleName().equals("1")){
                form.setRoleName("投资人");
            }else if(form.getRoleName().equals("2")){
                form.setRoleName("借款人");
            }else{
                form.setRoleName("垫付机构");
            }
        }
        //       用户类型0,'个人用户'     1, '企业用户'
        if(StringUtils.isNotBlank(form.getUserType())){
            if (form.getUserType().equals("0")){
                form.setUserType("个人用户");
            }else{
                form.setUserType("企业用户");
            }
        }
        BeanUtils.copyProperties(form, record);
        record.setUpdateTime(new Date());
        return subCommissionListConfigCustomizeMapper.updateSubConfigByPrimaryKeySelective(record);
    }

    /**
     * 删除
     */
    @Override
    public void deleteSubConfigRecord(Integer id){
        if (id != null &&id.intValue()>0) {
             subCommissionListConfigCustomizeMapper.deleteByPrimaryKey(id);
        }
    }

}
