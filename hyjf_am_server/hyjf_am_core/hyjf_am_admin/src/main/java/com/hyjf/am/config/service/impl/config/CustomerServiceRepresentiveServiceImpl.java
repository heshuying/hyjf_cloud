package com.hyjf.am.config.service.impl.config;

import com.hyjf.am.config.dao.mapper.auto.CustomerServiceChannelMapper;
import com.hyjf.am.config.dao.mapper.auto.CustomerServiceRepresentiveConfigMapper;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannelExample;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfigExample;
import com.hyjf.am.config.service.config.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 坐席配置
 * @author wgx
 * @date 2019/5/30
 */
@Service
public class CustomerServiceRepresentiveServiceImpl implements CustomerServiceRepresentiveConfigService {

    @Autowired
    private CustomerServiceRepresentiveConfigMapper customerServiceRepresentiveConfigMapper;
    @Autowired
    private CustomerServiceChannelMapper customerServiceChannelMapper;

    /**
     * 获取坐席配置总数
     * @param request
     * @return
     */
    @Override
    public int countCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        CustomerServiceRepresentiveConfigExample.Criteria criteria = example.createCriteria();
        String userName = request.getUserName(); //用户名
        Integer groupId = request.getGroupId();//客服组
        Integer status = request.getStatus();//状态
        if(StringUtils.isNotBlank(userName)){
            criteria.andUserNameLike("%" + userName + "%");
        }
        if(groupId != null){
            criteria.andGroupIdEqualTo(groupId);
        }
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        return customerServiceRepresentiveConfigMapper.countByExample(example);
    }

    /**
     * 获取坐席配置列表
     * @param request
     * @return
     */
    @Override
    public List<CustomerServiceRepresentiveConfig> getCustomerServiceRepresentiveConfigList(CustomerServiceRepresentiveConfigRequest request, int total) {
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        Paginator paginator = new Paginator(request.getCurrPage(), total, request.getPageSize() == 0 ? 10 : request.getPageSize());
        example.setLimitStart(paginator.getOffset());
        example.setLimitEnd(paginator.getLimit());
        example.setOrderByClause("id");
        CustomerServiceRepresentiveConfigExample.Criteria criteria = example.createCriteria();
        String userName = request.getUserName(); //用户名
        Integer groupId = request.getGroupId();//客服组
        Integer status = request.getStatus();//状态
        if(StringUtils.isNotBlank(userName)){
            criteria.andUserNameLike("%" + userName + "%");
        }
        if(groupId != null){
            criteria.andGroupIdEqualTo(groupId);
        }
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        return customerServiceRepresentiveConfigMapper.selectByExample(example);
    }

    /**
     * 添加坐席配置
     * @param config
     */
    @Override
    public void insertCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfig config) {
        customerServiceRepresentiveConfigMapper.insertSelective(config);
    }

    /**
     * 修改坐席配置
     * @param config
     */
    @Override
    public void updateCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfig config) {
        customerServiceRepresentiveConfigMapper.updateByPrimaryKeySelective(config);
    }

    /**
     * 删除坐席配置
     * @param id
     */
    @Override
    public void deleteCustomerServiceRepresentiveConfig(Integer id) {
        customerServiceRepresentiveConfigMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id修改冗余客组名称和是否新客
     *  @param groupId
     * @param groupName
     * @param isNew
     * @param updateUserId
     */
    @Override
    public void updateGroupNameAndIsNew(Integer groupId, String groupName, Integer isNew, Integer updateUserId) {
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        CustomerServiceRepresentiveConfigExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(0);// 客组名和是否新客未修改，不修改相应的坐席信息
        CustomerServiceRepresentiveConfig config = new CustomerServiceRepresentiveConfig();
        if (StringUtils.isNotBlank(groupName)) {
            config.setGroupName(groupName);
            config.setUpdateUserId(updateUserId);
            example.or().andGroupIdEqualTo(groupId).andGroupNameNotEqualTo(groupName);
        }
        if (isNew != null) {
            config.setIsNew(isNew);
            config.setUpdateUserId(updateUserId);
            example.or().andGroupIdEqualTo(groupId).andIsNewNotEqualTo(isNew);
        }
        customerServiceRepresentiveConfigMapper.updateByExampleSelective(config, example);
    }

    /**
     * 根据客组禁用坐席配置
     * @param groupId
     * @param updateUserId
     */
    @Override
    public void updateCustomerServiceRepresentiveConfigByGroup(Integer groupId, Integer updateUserId) {
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        CustomerServiceRepresentiveConfigExample.Criteria criteria = example.createCriteria();
        criteria.andGroupIdEqualTo(groupId);
        criteria.andStatusNotEqualTo(2);
        CustomerServiceRepresentiveConfig config = new CustomerServiceRepresentiveConfig();
        config.setStatus(2);
        config.setUpdateUserId(updateUserId);
        customerServiceRepresentiveConfigMapper.updateByExampleSelective(config, example);
    }

    @Override
    public Map<String, Object> checkCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("success","success");
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        CustomerServiceRepresentiveConfigExample.Criteria criteria = example.createCriteria();
        String userName = request.getUserName(); //用户名
        if (userName == null) {// 未修改姓名，不需校验
            return result;
        } else if ("".equals(userName)) {
            result.put("success", "fail");
            result.put("message", "姓名不能为空！");
            return result;
        }
        if (request.getId() != null) {
            criteria.andIdNotEqualTo(request.getId());
        }
        criteria.andUserNameEqualTo(userName);
        List<CustomerServiceRepresentiveConfig> representiveList = customerServiceRepresentiveConfigMapper.selectByExample(example);
        if (representiveList != null && representiveList.size() > 0) {
            CustomerServiceRepresentiveConfig representiveConfig = representiveList.get(0);
            result.put("success", "fail");
            result.put("message", "数据重复，请重新填写");
            if (StringUtils.isNotBlank(userName) && userName.equals(representiveConfig.getUserName())) {
                result.put("message", "该坐席已存在，请重新填写");
            }
            return result;
        }
        return result;
    }

	@Override
	public CustomerServiceRepresentiveConfig getCustomerServiceRepresentiveConfig(String username) {
		CustomerServiceRepresentiveConfigExample example=new CustomerServiceRepresentiveConfigExample();
		example.or().andUserNameEqualTo(username);
		 List<CustomerServiceRepresentiveConfig> list = customerServiceRepresentiveConfigMapper.selectByExample(example);
		if(list != null && !(list.size()==0)) {
			return null;
		}
		return list.get(0);
	}
    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    @Override
    public CustomerServiceChannel selectCustomerServiceChannelBySourceId(Integer sourceId) {
        CustomerServiceChannelExample example = new CustomerServiceChannelExample();
        CustomerServiceChannelExample.Criteria cra = example.createCriteria();
        cra.andChannelIdEqualTo(sourceId);
        List<CustomerServiceChannel> list = this.customerServiceChannelMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
