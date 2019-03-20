package com.hyjf.am.user.service.impl;

import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.mapper.auto.CustomerTaskConfigMapper;
import com.hyjf.am.user.dao.mapper.auto.ScreenConfigMapper;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfigExample;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import com.hyjf.am.user.service.admin.vip.content.OperService;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperServiceImpl implements OperService {

    @Autowired
    private ScreenConfigMapper screenConfigMapper;
    @Autowired
    private CustomerTaskConfigMapper customerTaskConfigMapper;

    /**
     * 大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @Override
    public List<ScreenConfig> operList(ScreenConfigRequest request) {
        // 页码
        int currentPage = request.getCurrPage();
        // 每页展示数
        int pageSize = request.getPageSize();
        ScreenConfigExample example = new ScreenConfigExample();
        // 任务时间,精确到月 yyyy-mm
        if(StringUtils.isNotBlank(request.getTaskTime())){
            ScreenConfigExample.Criteria cra = example.createCriteria();
            cra.andTaskTimeEqualTo(request.getTaskTime());
        }
        example.setLimitStart(currentPage == 0 ? 0 : (currentPage - 1) * pageSize);
        example.setLimitEnd(pageSize);
        return screenConfigMapper.selectByExample(example);
    }

    /**
     * 大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @Override
    public int operAdd(ScreenConfigVO screenConfigVO) {
        ScreenConfig screenConfig = new ScreenConfig();
        BeanUtils.copyProperties(screenConfigVO, screenConfig);
        return screenConfigMapper.insertSelective(screenConfig);
    }

    /**
     * 大屏运营部数据配置数据编辑/启用/禁用
     * @param screenConfigVO
     * @return
     */
    @Override
    public int operUpdate(ScreenConfigVO screenConfigVO) {
        ScreenConfig screenConfig = new ScreenConfig();
        BeanUtils.copyProperties(screenConfigVO, screenConfig);
        return screenConfigMapper.updateByPrimaryKeySelective(screenConfig);
    }

    /**
     * 坐席月任务配置列表查询
     * @param request
     * @return
     */
    @Override
    public List<CustomerTaskConfig> taskList(CustomerTaskConfigRequest request) {
        CustomerTaskConfigExample example = new CustomerTaskConfigExample();
        CustomerTaskConfigExample.Criteria cra = example.createCriteria();
        // 任务时间,精确到月 yyyy-mm
        if(StringUtils.isNotBlank(request.getTaskTime())){
            cra.andTaskTimeEqualTo(request.getTaskTime());
        }
        // 坐席分组 1:新客组,2:老客组
        if(null != request.getCustomerGroup()){
            cra.andCustomerGroupEqualTo(request.getCustomerGroup());
        }
        // 坐席姓名 1:新客组,2:老客组
        if(StringUtils.isNotBlank(request.getCustomerName())){
            cra.andCustomerNameEqualTo(request.getCustomerName());
        }
        // 是否有效 1:有效,2:无效
        if(null != request.getStatus()){
            cra.andStatusEqualTo(request.getStatus());
        }
        return customerTaskConfigMapper.selectByExample(example);
    }

    /**
     * 坐席月任务配置数据新增
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public int taskAdd(CustomerTaskConfigVO customerTaskConfigVO) {
        CustomerTaskConfig customerTaskConfig = new CustomerTaskConfig();
        BeanUtils.copyProperties(customerTaskConfigVO, customerTaskConfig);
        return customerTaskConfigMapper.insertSelective(customerTaskConfig);
    }

    /**
     * 坐席月任务配置数据编辑/启用/禁用
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public int taskUpdate(CustomerTaskConfigVO customerTaskConfigVO) {
        CustomerTaskConfig customerTaskConfig = new CustomerTaskConfig();
        BeanUtils.copyProperties(customerTaskConfigVO, customerTaskConfig);
        return customerTaskConfigMapper.updateByPrimaryKeySelective(customerTaskConfig);
    }


}
