/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.pointsshop.duiba.points;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.vo.admin.DuibaPointsUserVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping("/selectDuibaPointsUser")
    public DuibaPointsUserResponse selectDuibaPointsUser(@RequestBody DuibaPointsRequest request) {
        DuibaPointsUserResponse response = new DuibaPointsUserResponse();
        //查询条数
        Integer recordTotal = this.duibaPointsService.selectDuibaPointsUserCount(request);

        if (recordTotal > 0) {
            // 查询列表传入分页
            Paginator paginator;
            if(request.getPageSize() == 0){
                // 前台传分页
                paginator = new Paginator(request.getCurrPage(), recordTotal);
            } else {
                // 前台未传分页那默认 10
                paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            }
            request.setPaginator(paginator);
            List<DuibaPointsUserVO> recordList = duibaPointsService.selectDuibaPointsUser(request);
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(recordList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
