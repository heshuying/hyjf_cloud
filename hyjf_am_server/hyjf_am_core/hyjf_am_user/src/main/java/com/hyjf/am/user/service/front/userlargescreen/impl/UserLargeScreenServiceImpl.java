/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.userlargescreen.impl;

import com.hyjf.am.response.trade.ScreenTransferResponse;
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
import com.hyjf.am.vo.api.MonthDataStatisticsVO;
import com.hyjf.am.vo.screen.ScreenTransferVO;
import com.hyjf.am.vo.user.CustomerTaskConfigVO;
import com.hyjf.am.vo.user.ScreenConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        CustomerTaskConfigExample example = new CustomerTaskConfigExample();
        CustomerTaskConfigExample.Criteria criteria = example.createCriteria();
        criteria.andTaskTimeEqualTo(request.getTaskTime());
        criteria.andStatusEqualTo(1);
        List<CustomerTaskConfig> list = customerTaskConfigMapper.selectByExample(example);
        List<CustomerTaskConfigVO> vos = new ArrayList<>();
        for(CustomerTaskConfig customerTaskConfig:list){
            CustomerTaskConfigVO vo = new CustomerTaskConfigVO();
            BeanUtils.copyProperties(customerTaskConfig,vo);
            vos.add(vo);

        }
        response.setResultList(vos);
        return response;
    }

    /**
     * 所有坐席和坐席下用户查询
     * @return
     */
    @Override
    public List<MonthDataStatisticsVO> getCurrentOwnersAndUserIds() {
        List<MonthDataStatisticsVO> list = userLargeScreenTwoCustomizeMapper.getCurrentOwners();
        for (MonthDataStatisticsVO param : list) {
            List<Integer> userIds = userLargeScreenTwoCustomizeMapper.getCurrentOwnerUserIds(param.getCurrentOwner());
            if (!CollectionUtils.isEmpty(userIds)){
                param.setUserIds(userIds);
            }
        }
        return list;
    }

    @Override
    public ScreenTransferResponse getScreenTransferData(List<ScreenTransferVO> userList) {
        List<ScreenTransferVO> list = userLargeScreenTwoCustomizeMapper.getScreenTransferData(userList);
        ScreenTransferResponse response = new ScreenTransferResponse();
        response.setResultList(list);
        return response;
    }
}
