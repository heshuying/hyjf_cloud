/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.batch.Impl;

import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.cs.user.service.batch.ElectricitySalesDataPushService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电销数据推送Service实现类
 *
 * @author liuyang
 * @version ElectricitySalesDataPushServiceImpl, v0.1 2019/6/3 13:58
 */
@Service
public class ElectricitySalesDataPushServiceImpl extends BaseUserServiceImpl implements ElectricitySalesDataPushService {

    /**
     * 获取需要推送的电销数据
     *
     * @return
     */
    @Override
    public List<ElectricitySalesDataPushListVO> selectElectricitySalesDataPushDataList() {
        return this.amUserClient.selectElectricitySalesDataPushDataList();
    }

    /**
     * 上传数据成功后,更新数据状态
     *
     * @param updateList
     */
    @Override
    public void updateElectricitySalesDataPushList(List<ElectricitySalesDataPushListVO> updateList) {
        ElectricitySalesDataPushListRequest request = new ElectricitySalesDataPushListRequest();
        request.setElectricitySalesDataPushList(updateList);
        amUserClient.updateElectricitySalesDataPushList(request);
    }
}
