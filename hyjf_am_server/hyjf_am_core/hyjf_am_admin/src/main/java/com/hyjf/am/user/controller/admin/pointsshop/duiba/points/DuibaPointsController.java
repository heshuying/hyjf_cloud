/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.pointsshop.duiba.points;

import com.hyjf.am.market.dao.model.auto.DuibaPoints;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 兑吧积分账户列表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsController, v0.1 2019/5/29 9:46
 */
@RestController
@RequestMapping("/am-user/duiba")
public class DuibaPointsController extends BaseController {

    @Autowired
    DuibaPointsService duibaPointsService;

    /**
     * 根据筛选条件查找
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectDuibaPoints")
    public DuibaPointsResponse selectDuibaPoints(@RequestBody DuibaPointsRequest request) {
        DuibaPointsResponse response = new DuibaPointsResponse();
        //查询条数
        int recordTotal = this.duibaPointsService.selectDuibaPointsCount(request);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), recordTotal, request.getPageSize() == 0 ? 10 : request.getPageSize());
            //查询记录
            List<DuibaPoints> recordList = duibaPointsService.selectDuibaPoints(request, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(recordList)) {
                List<DuibaPointsVO> voList = CommonUtils.convertBeanList(recordList, DuibaPointsVO.class);
                response.setResultList(voList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
