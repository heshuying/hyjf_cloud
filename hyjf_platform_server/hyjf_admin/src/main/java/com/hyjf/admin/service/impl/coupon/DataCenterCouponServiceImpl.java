/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.client.DataCenterCouponClient;
import com.hyjf.admin.service.DataCenterCouponService;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fq
 * @version DataCenterCouponServiceImpl, v0.1 2018/7/19 9:53
 */
@Service
public class DataCenterCouponServiceImpl implements DataCenterCouponService {
    @Autowired
    private DataCenterCouponClient client;

    @Override
    public DataCenterCouponResponse searchAction(DadaCenterCouponRequestBean requestBean, String type) {
        DataCenterCouponResponse response = new DataCenterCouponResponse();
        List<DataCenterCouponCustomizeVO> list = client.getDataCenterCouponList(requestBean, type);
        response.setCount(list.size());
        if(list.size() > 0){
            Paginator paginator = new Paginator(requestBean.getCurrPage(), list.size(),requestBean.getPageSize()==0?10:requestBean.getPageSize());
            requestBean.setLimitStart(paginator.getOffset());
            requestBean.setLimitEnd(paginator.getLimit());

            List<DataCenterCouponCustomizeVO>  response1list = client.getDataCenterCouponList(requestBean, type);
            response.setResultList(response1list);
        }

        return response;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        List<DataCenterCouponCustomizeVO> list = client.getRecordListJX(dataCenterCouponCustomize);
        for (DataCenterCouponCustomizeVO vo : list) {
            Integer activityId = vo.getActivityId();
            String title = client.getActivityTitle(activityId);
            vo.setTitle(title);
        }
        return list;
    }

    @Override
    public List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize) {
        List<DataCenterCouponCustomizeVO> list = client.getRecordListDJ(dataCenterCouponCustomize);
        for (DataCenterCouponCustomizeVO vo : list) {
            Integer activityId = vo.getActivityId();
            String title = client.getActivityTitle(activityId);
            vo.setTitle(title);
        }
        return list;
    }
}
