package com.hyjf.am.config.service.impl.config;

import com.hyjf.am.config.dao.mapper.auto.CustomerServiceGroupConfigMapper;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfigExample;
import com.hyjf.am.config.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客组配置
 *
 * @author wgx
 * @date 2019/5/29
 */
@Service
public class CustomerServiceGroupConfigServiceImpl implements CustomerServiceGroupConfigService {

    @Autowired
    private CustomerServiceGroupConfigMapper customerServiceGroupConfigMapper;

    /**
     * 获取客组配置总数
     *
     * @param request
     * @return
     */
    @Override
    public int countCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        Integer status = request.getStatus();
        if (status != null) {
            CustomerServiceGroupConfigExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo(status);
        }
        return customerServiceGroupConfigMapper.countByExample(example);
    }

    /**
     * 获取客组配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfig> getCustomerServiceGroupConfigList(CustomerServiceGroupConfigRequest request, int total) {
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        if (request.getPageSize() >= 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), total, request.getPageSize() == 0 ? 10 : request.getPageSize());
            example.setLimitStart(paginator.getOffset());
            example.setLimitEnd(paginator.getLimit());
        }
        Integer status = request.getStatus();
        if (status != null) {
            CustomerServiceGroupConfigExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo(status);
        }
        return customerServiceGroupConfigMapper.selectByExample(example);
    }

    /**
     * 添加客组配置
     *
     * @param config
     */
    @Override
    public void insertCustomerServiceGroupConfig(CustomerServiceGroupConfig config) {
        customerServiceGroupConfigMapper.insertSelective(config);
    }

    /**
     * 修改客组配置
     *
     * @param config
     */
    @Override
    public void updateCustomerServiceGroupConfig(CustomerServiceGroupConfig config) {
        customerServiceGroupConfigMapper.updateByPrimaryKeySelective(config);
    }

    /**
     * 删除客组配置
     *
     * @param id
     */
    @Override
    public void deleteCustomerServiceGroupConfig(Integer id) {
        customerServiceGroupConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询客组配置
     *
     * @param id
     */
    @Override
    public CustomerServiceGroupConfig getCustomerServiceGroupConfigById(Integer id) {
        return customerServiceGroupConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> checkCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", "success");
        String groupName = request.getGroupName();
        String serviceUserCode = request.getServiceUserCode();
        String serviceUserKey = request.getServiceUserKey();
        String serviceUserNo = request.getServiceUserNo();
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        boolean needCheck = false;
        if (StringUtils.isNotBlank(groupName)) {
            needCheck = true;
            CustomerServiceGroupConfigExample.Criteria groupNameCriteria = example.or();
            groupNameCriteria.andGroupNameEqualTo(groupName);
            if (request.getId() != null) {
                groupNameCriteria.andIdNotEqualTo(request.getId());
            }
        }
        if (StringUtils.isNotBlank(serviceUserCode)) {
            needCheck = true;
            CustomerServiceGroupConfigExample.Criteria serviceUserCodeCriteria = example.or();
            serviceUserCodeCriteria.andServiceUserCodeEqualTo(serviceUserCode);
            if (request.getId() != null) {
                serviceUserCodeCriteria.andIdNotEqualTo(request.getId());
            }
        }
        if (StringUtils.isNotBlank(serviceUserKey)) {
            needCheck = true;
            CustomerServiceGroupConfigExample.Criteria serviceUserKeyCriteria = example.or();
            serviceUserKeyCriteria.andServiceUserKeyEqualTo(serviceUserKey);
            if (request.getId() != null) {
                serviceUserKeyCriteria.andIdNotEqualTo(request.getId());
            }
        }
        if (StringUtils.isNotBlank(serviceUserNo)) {
            needCheck = true;
            CustomerServiceGroupConfigExample.Criteria serviceUserNoCriteria = example.or();
            serviceUserNoCriteria.andServiceUserNoEqualTo(serviceUserNo);
            if (request.getId() != null) {
                serviceUserNoCriteria.andIdNotEqualTo(request.getId());
            }
        }
        if (!needCheck) {
            return result;
        }
        List<CustomerServiceGroupConfig> groupList = customerServiceGroupConfigMapper.selectByExample(example);
        if (groupList != null && groupList.size() > 0) {
            CustomerServiceGroupConfig groupConfig = groupList.get(0);
            result.put("success", "fail");
            result.put("message", "数据重复，请重新输入");
            if (StringUtils.isNotBlank(groupName) && groupName.equals(groupConfig.getGroupName())) {
                result.put("message", "客组名称已存在，请重新输入");
            } else if (StringUtils.isNotBlank(serviceUserCode) && serviceUserCode.equals(groupConfig.getServiceUserCode())) {
                result.put("message", "第三方用户唯一凭证已存在，请重新输入");
            } else if (StringUtils.isNotBlank(serviceUserKey) && serviceUserKey.equals(groupConfig.getServiceUserKey())) {
                result.put("message", "第三方用户唯一凭证秘钥已存在，请重新输入");
            } else if (StringUtils.isNotBlank(serviceUserNo) && serviceUserNo.equals(groupConfig.getServiceUserNo())) {
                result.put("message", "第三方用户账户编号已存在，请重新输入");
            }
            return result;
        }
        return result;
    }


}
