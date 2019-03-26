package com.hyjf.am.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CustomerTaskConfigRequest;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.mapper.auto.CustomerTaskConfigMapper;
import com.hyjf.am.user.dao.mapper.auto.ScreenConfigMapper;
import com.hyjf.am.user.dao.mapper.customize.CustomerTaskConfigMapperCustomize;
import com.hyjf.am.user.dao.mapper.customize.ScreenConfigMapperCustomize;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfigExample;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import com.hyjf.am.user.dao.model.customize.CustomerTaskConfigExampleCustomize;
import com.hyjf.am.user.service.admin.vip.content.OperService;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperServiceImpl implements OperService {
    private static final Logger logger = LoggerFactory.getLogger(OperServiceImpl.class);
    @Autowired
    private ScreenConfigMapper screenConfigMapper;
    @Autowired
    private CustomerTaskConfigMapper customerTaskConfigMapper;
    @Autowired
    private ScreenConfigMapperCustomize screenConfigMapperCustomize;
    @Autowired
    private CustomerTaskConfigMapperCustomize customerTaskConfigMapperCustomize;

    /**
     * 大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @Override
    public List<ScreenConfigVO> operList(ScreenConfigRequest request) {
        ScreenConfigExample example = new ScreenConfigExample();
        // 当前页
        int currentPage = request.getCurrPage();
        // 当前页条数
        int pageSize = request.getPageSize();
        example.setLimitStart(currentPage == 0 ? 0 : (currentPage - 1) * pageSize);
        example.setLimitEnd(pageSize);

        // 任务时间,精确到月 yyyy-mm
        if(StringUtils.isNotBlank(request.getTaskTime())){
            ScreenConfigExample.Criteria cra = example.createCriteria();
            cra.andTaskTimeEqualTo(request.getTaskTime());
        }

        return screenConfigMapperCustomize.selectByExample(example);
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
     * 大屏运营部数据配置数据详情
     * @param id
     * @return
     */
    @Override
    public ScreenConfig operInfo(Integer id) {
        return screenConfigMapper.selectByPrimaryKey(id);
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
    public List<CustomerTaskConfigVO> taskList(CustomerTaskConfigRequest request) {
        logger.info("坐席月任务配置列表查询请求数据:{}", JSONObject.toJSONString(request));
        CustomerTaskConfigExampleCustomize example = new CustomerTaskConfigExampleCustomize();
        CustomerTaskConfigExampleCustomize.Criteria cra = example.createCriteria();
        // 当前页
        int currentPage = request.getCurrPage();
        // 当前页条数
        int pageSize = request.getPageSize();
        example.setLimitStart(currentPage == 0 ? 0 : (currentPage - 1) * pageSize);
        example.setLimitEnd(pageSize);

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
            cra.andCustomerNameLike(request.getCustomerName());
        }
        // 是否有效 1:有效,2:无效
        if(null != request.getStatus()){
            cra.andTaskStatusEqualTo(request.getStatus());
        }
        return customerTaskConfigMapperCustomize.selectByExample(example);
    }

    /**
     * 坐席月任务配置数据新增
     * @param customerTaskConfigVO
     * @return
     */
    @Override
    public int taskAdd(CustomerTaskConfigVO customerTaskConfigVO) {
        int flag = 0;
        CustomerTaskConfig customerTaskConfig = new CustomerTaskConfig();
        BeanUtils.copyProperties(customerTaskConfigVO, customerTaskConfig);
        int addFlag = customerTaskConfigMapper.insertSelective(customerTaskConfig);

        CustomerTaskConfig updateParam = new CustomerTaskConfig();
        updateParam.setCustomerGroup(customerTaskConfigVO.getCustomerGroup());
        CustomerTaskConfigExample example = new CustomerTaskConfigExample();
        CustomerTaskConfigExample.Criteria cra = example.createCriteria();
        cra.andCustomerNameEqualTo(customerTaskConfigVO.getCustomerName());
        int updateFlag = customerTaskConfigMapper.updateByExampleSelective(updateParam, example);
        if (addFlag == 1 && updateFlag ==1){
            flag = 1;
        }
        return flag;
    }

    /**
     * 坐席月任务配置数据详情
     * @param id
     * @return
     */
    @Override
    public CustomerTaskConfig taskInfo(Integer id) {
        return customerTaskConfigMapper.selectByPrimaryKey(id);
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
        int flag = customerTaskConfigMapper.updateByPrimaryKeySelective(customerTaskConfig);

        if(StringUtils.isNotBlank(customerTaskConfigVO.getCustomerName())){
            CustomerTaskConfig updateParam = new CustomerTaskConfig();
            updateParam.setCustomerGroup(customerTaskConfigVO.getCustomerGroup());
            CustomerTaskConfigExample example = new CustomerTaskConfigExample();
            CustomerTaskConfigExample.Criteria cra = example.createCriteria();
            cra.andCustomerNameEqualTo(customerTaskConfigVO.getCustomerName());
            customerTaskConfigMapper.updateByExampleSelective(updateParam, example);
        }
        return flag;
    }


}
