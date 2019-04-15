package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.WorkNameMapper;
import com.hyjf.am.config.dao.model.auto.WorkName;
import com.hyjf.am.config.dao.model.auto.WorkNameExample;
import com.hyjf.am.config.service.BusinessNameMgAmService;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:37
 * @Version 1.0
 */
@Service
public class BusinessNameMgAmServiceImpl implements BusinessNameMgAmService {

    @Autowired
    private WorkNameMapper workNameMapper;

    @Override
    public int getTotalCount(BusinessNameMgRequest request){
        WorkNameExample example = new WorkNameExample();
        WorkNameExample.Criteria criteria = example.createCriteria();

        //银行名称
        if(StringUtils.isNotBlank(request.getBsname())){
            criteria.andWorkNameEqualTo(request.getBsname());
        }
        return workNameMapper.countByExample(example);
    }

    @Override
    public List<WorkName> getListBs(BusinessNameMgRequest request, int limitStart, int limitEnd){
        WorkNameExample example = new WorkNameExample();
        WorkNameExample.Criteria criteria = example.createCriteria();

        //业务名称
        if(StringUtils.isNotBlank(request.getBsname())){
            criteria.andWorkNameEqualTo(request.getBsname());
        }
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        example.setOrderByClause("update_time desc");
        return workNameMapper.selectByExample(example);
    }

    @Override
    public int insertBs(BusinessNameMgRequest request){

        WorkName workName = new WorkName();
        workName.setWorkName(request.getBsname());
        workName.setChargeName(request.getBscharge());
        workName.setChargeMail(request.getMail());
        workName.setStatus(1);//1可用，2禁用
        workName.setUpdateUser(request.getUsername());
        workName.setCreateUser(request.getUsername());
        return workNameMapper.insert(workName);
    }

    @Override
    public WorkName findListBsById(int id){
        return workNameMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateBs(BusinessNameMgRequest request){

        WorkName workName = new WorkName();
        workName.setId(request.getId());
        workName.setWorkName(request.getBsname());
        workName.setChargeName(request.getBscharge());
        workName.setChargeMail(request.getMail());
        workName.setUpdateUser(request.getUsername());
        return workNameMapper.updateByPrimaryKeySelective(workName);
    }

    @Override
    public int updateStatusBs(BusinessNameMgRequest request){

        WorkName workName = new WorkName();
        workName.setId(request.getId());
        workName.setStatus(request.getStatus());
        workName.setUpdateUser(request.getUsername());
        return workNameMapper.updateByPrimaryKeySelective(workName);
    }
}
