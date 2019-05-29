package com.hyjf.am.config.service.impl.config;

import com.hyjf.am.config.dao.mapper.auto.CustomerServiceGroupConfigMapper;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfigExample;
import com.hyjf.am.config.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wgx
 * @date 2019/5/29
 */
@Service
public class CustomerServiceGroupConfigServiceImpl implements CustomerServiceGroupConfigService {

    @Autowired
    private CustomerServiceGroupConfigMapper customerServiceGroupConfigMapper;

    /**
     * 获取客组配置总数
     * @param request
     * @return
     */
    @Override
    public int countCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        return customerServiceGroupConfigMapper.countByExample(example);
    }

    /**
     * 获取客组配置列表
     * @param request
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfig> getCustomerServiceGroupConfigList(CustomerServiceGroupConfigRequest request, int total) {
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        Paginator paginator = new Paginator(request.getCurrPage(), total, request.getPageSize() == 0 ? 10 : request.getPageSize());
        example.setLimitStart(paginator.getOffset());
        example.setLimitEnd(paginator.getLimit());
        return customerServiceGroupConfigMapper.selectByExample(example);
    }

    /**
     * 添加客组配置
     * @param config
     */
    @Override
    public void insertCustomerServiceGroupConfig(CustomerServiceGroupConfig config) {
        customerServiceGroupConfigMapper.insertSelective(config);
    }

    /**
     * 修改客组配置
     * @param config
     */
    @Override
    public void updateCustomerServiceGroupConfig(CustomerServiceGroupConfig config) {
        customerServiceGroupConfigMapper.updateByPrimaryKeySelective(config);
    }

    /**
     * 删除客组配置
     * @param id
     */
    @Override
    public void deleteCustomerServiceGroupConfig(Integer id) {
        customerServiceGroupConfigMapper.deleteByPrimaryKey(id);
    }
}
