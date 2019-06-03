/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.electricitysalesdata.impl;

import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushListExample;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 电销推送数据生成Service实现类
 *
 * @author liuyang
 * @version ElectricitySalesDataServiceImpl, v0.1 2019/5/28 17:14
 */
@Service
public class ElectricitySalesDataServiceImpl extends BaseServiceImpl implements ElectricitySalesDataService {

    /**
     * 电销数数据生成
     */
    @Override
    public void generateElectricitySalesData(List<ElectricitySalesDataPushList> result) {
        for (int i = 0; i < result.size(); i++) {
            this.electricitySalesDataPushListMapper.insertSelective(result.get(i));
        }
    }


    /**
     * 获取需要推送的电销数据
     *
     * @return
     */
    @Override
    public List<ElectricitySalesDataPushList> selectElectricitySalesDataPushDataList() {
        ElectricitySalesDataPushListExample example = new ElectricitySalesDataPushListExample();
        ElectricitySalesDataPushListExample.Criteria cra = example.createCriteria();
        List<Integer> status = new ArrayList<>();
        status.add(0);
        status.add(2);
        // 状态 0:初始 1:成功 2:失败
        cra.andStatusIn(status);
        example.setOrderByClause("id ASC");
        return this.electricitySalesDataPushListMapper.selectByExample(example);
    }
}
