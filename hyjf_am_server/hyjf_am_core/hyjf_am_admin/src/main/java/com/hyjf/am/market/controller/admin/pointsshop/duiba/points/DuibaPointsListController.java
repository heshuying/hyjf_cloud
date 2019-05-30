/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.points;

import com.hyjf.am.market.controller.admin.activity.ActivityController;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 兑吧积分明细表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListController, v0.1 2019/5/29 9:46
 */
@RestController
@RequestMapping("/am-admin/duibapoints")
public class DuibaPointsListController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    DuibaPointsListService duibaPointsListService;

    /**
     * 根据筛选条件查找
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectDuibaPointsList")
    public DuibaPointsResponse selectDuibaPointsList(@RequestBody DuibaPointsRequest request) {
        DuibaPointsResponse response = new DuibaPointsResponse();
        //查询条数
        Integer recordTotal = this.duibaPointsListService.selectDuibaPointsCount(request);

        if (recordTotal > 0) {
            // 查询列表传入分页
            Paginator paginator;
            if (request.getPageSize() == 0) {
                // 前台传分页
                paginator = new Paginator(request.getCurrPage(), recordTotal);
            } else {
                // 前台未传分页那默认 10
                paginator = new Paginator(request.getCurrPage(), recordTotal, request.getPageSize());
            }
            request.setPaginator(paginator);
            List<DuibaPointsVO> recordList = duibaPointsListService.selectDuibaPointsList(request);
            if (CollectionUtils.isNotEmpty(recordList)) {
                response.setResultList(recordList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
