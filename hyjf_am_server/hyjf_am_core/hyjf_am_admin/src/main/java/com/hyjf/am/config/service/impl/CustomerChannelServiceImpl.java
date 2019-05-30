package com.hyjf.am.config.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.CustomerServiceChannelMapper;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannelExample;
import com.hyjf.am.config.service.CustomerChannelService;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;
import com.hyjf.common.paginator.Paginator;

@Service
public class CustomerChannelServiceImpl implements  CustomerChannelService{
    @Autowired
    CustomerServiceChannelMapper customerServiceChannelMapper;
	@Override
	public int getCustomerChannelCount() {
		CustomerServiceChannelExample example=new CustomerServiceChannelExample();
		
		return customerServiceChannelMapper.countByExample(example);
	}

	@Override
	public List<CustomerServiceChannel> getCustomerChannelList(CustomerChannelRequest request,int count) {
		CustomerServiceChannelExample example=new CustomerServiceChannelExample();
		if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
			Paginator paginator = new Paginator(request.getCurrPage(), (int) count,request.getPageSize());
            example.setLimitStart(paginator.getOffset());
            example.setLimitEnd(paginator.getLimit());
		}
		return customerServiceChannelMapper.selectByExample(example);
	}

	@Override
	public int insetCustomerChannel(CustomerChannelRequest request) {
 
    	CustomerServiceChannel record=new CustomerServiceChannel();
		BeanUtils.copyProperties(request,record );
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		return customerServiceChannelMapper.insert(record);
	}

	@Override
	public int updateAuthConfig(CustomerChannelRequest request) {
    	CustomerServiceChannel record=new CustomerServiceChannel();
    	record.setId(request.getId());
    	record.setStatus(request.getStatus());
    	record.setUpdateUser(request.getUpdateUser());
    	record.setUpdateTime(new Date());
		return customerServiceChannelMapper.updateByPrimaryKey(record);
	}



}
