/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.controller.admin.pointsshop.duiba.points;

import com.hyjf.am.market.controller.admin.activity.ActivityController;
import com.hyjf.am.market.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 兑吧积分调整表
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyController, v0.1 2019/5/29 9:47
 */
@RestController
@RequestMapping("/am-admin/duibapointsmodify")
public class DuibaPointsModifyController {

    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    DuibaPointsModifyService duibaPointsModifyService;

    /**
     * 根据筛选条件查找
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectDuibaPointsModifyList")
    public DuibaPointsModifyResponse selectDuibaPointsList(@RequestBody DuibaPointsRequest request) {
        DuibaPointsModifyResponse response = new DuibaPointsModifyResponse();
        //查询条数
        Integer recordTotal = this.duibaPointsModifyService.selectDuibaPointsModifyCount(request);

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
            List<DuibaPointsModifyVO> recordList = duibaPointsModifyService.selectDuibaPointsModifyList(request);
            if (CollectionUtils.isNotEmpty(recordList)) {
                response.setResultList(recordList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }



}
