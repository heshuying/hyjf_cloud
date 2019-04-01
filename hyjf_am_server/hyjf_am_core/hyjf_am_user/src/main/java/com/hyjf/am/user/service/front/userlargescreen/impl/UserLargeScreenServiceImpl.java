/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.userlargescreen.impl;

import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;
import com.hyjf.am.user.dao.mapper.auto.CustomerTaskConfigMapper;
import com.hyjf.am.user.dao.mapper.auto.ScreenConfigMapper;
import com.hyjf.am.user.dao.mapper.customize.UserLargeScreenTwoCustomizeMapper;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfigExample;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.dao.model.auto.ScreenConfigExample;
import com.hyjf.am.user.service.front.userlargescreen.UserLargeScreenService;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: tanyy
 * @version: UserWithdrawServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserLargeScreenServiceImpl  implements UserLargeScreenService {

    @Resource
    ScreenConfigMapper screenConfigMapper;
    @Resource
    CustomerTaskConfigMapper customerTaskConfigMapper;
    @Resource
    UserLargeScreenTwoCustomizeMapper userLargeScreenTwoCustomizeMapper;

    @Override
   public UserScreenConfigResponse getScreenConfig(UserLargeScreenRequest request){
        UserScreenConfigResponse response = new UserScreenConfigResponse();
        ScreenConfigVO vo = null;
        ScreenConfigExample example = new ScreenConfigExample();
        ScreenConfigExample.Criteria criteria = example.createCriteria();
        criteria.andTaskTimeEqualTo(request.getTaskTime());
        criteria.andStatusEqualTo(1);
        List<ScreenConfig> list = screenConfigMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
            vo = new ScreenConfigVO();
            BeanUtils.copyProperties(list.get(0),vo);
        }
        response.setResult(vo);
        return response;
   }

    @Override
    public UserCustomerTaskConfigResponse getCustomerTaskConfig(UserLargeScreenRequest request){
        UserCustomerTaskConfigResponse response = new UserCustomerTaskConfigResponse();
        CustomerTaskConfigVO vo = new CustomerTaskConfigVO();
        CustomerTaskConfigExample example = new CustomerTaskConfigExample();
        CustomerTaskConfigExample.Criteria criteria = example.createCriteria();
        criteria.andTaskTimeEqualTo(request.getTaskTime());
        List<CustomerTaskConfig> list = customerTaskConfigMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(list)){
            BeanUtils.copyProperties(list.get(0),vo);
        }
        response.setResult(vo);
        return response;
    }

    @Override
    public List<Integer> getOperUserIds() {
        return userLargeScreenTwoCustomizeMapper.getOperUserIds();
    }


}
