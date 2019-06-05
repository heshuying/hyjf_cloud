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

import javax.validation.Valid;
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
     * 电销数据生成
     *
     * @param result
     * @return
     */
    @Override
    public Integer generateElectricitySalesData(List<ElectricitySalesDataPushList> result) {
        Integer insertCount = 0;
        for (int i = 0; i < result.size(); i++) {
            insertCount = insertCount + this.electricitySalesDataPushListMapper.insertSelective(result.get(i));
        }
        return insertCount;
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
        // status.add(2);
        // 状态 0:初始 1:成功 2:失败
        cra.andStatusIn(status);
        example.setOrderByClause("id ASC");
        return this.electricitySalesDataPushListMapper.selectByExample(example);
    }

    /**
     * 电销数据上送后,更新电销数据
     *
     * @param result
     */
    @Override
    public Integer updateElectricitySalesDataPushList(List<ElectricitySalesDataPushList> result) {
        Integer count = 0;
        for (int i = 0; i < result.size(); i++) {
            count = count + this.electricitySalesDataPushListMapper.updateByPrimaryKey(result.get(i));
        }
        return count;
    }


    /**
     * 根据用户ID查询电销数据是否存在
     *
     * @param userId
     * @return
     */
    @Override
    public ElectricitySalesDataPushList selectElectricitySalesDataPushListByUserId(@Valid Integer userId) {
        ElectricitySalesDataPushListExample example = new ElectricitySalesDataPushListExample();
        ElectricitySalesDataPushListExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<ElectricitySalesDataPushList> list = this.electricitySalesDataPushListMapper.selectByExample(example);
        if (list != null && list.size() >= 0) {
            return list.get(0);
        }
        return null;
    }
}
