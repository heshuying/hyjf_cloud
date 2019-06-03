/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.response.user.UserEvalationQuestionResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.resquest.user.EvalationRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.dao.model.customize.ChangeLogCustomize;
import com.hyjf.am.user.service.front.electricitysalesdata.ElectricitySalesDataService;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.user.UserEvalationQuestionVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 电销数据生成Controller
 *
 * @author liuyang
 * @version ElectricitySalesDataPushListController, v0.1 2019/6/3 9:51
 */
@RestController
@RequestMapping("/am-user/electricitySalesDataPushList")
public class ElectricitySalesDataPushListController extends BaseController {


    @Autowired
    private ElectricitySalesDataService electricitySalesDataService;

    /**
     * 生成电销推送数据
     *
     * @param request
     * @return
     */
    @PostMapping("/generateElectricitySalesData")
    public void generateElectricitySalesData(@RequestBody @Valid ElectricitySalesDataPushListRequest request) {
        List<ElectricitySalesDataPushListVO> list = request.getElectricitySalesDataPushList();
        if (list != null && list.size() > 0) {
            List<ElectricitySalesDataPushList> result = CommonUtils.convertBeanList(list, ElectricitySalesDataPushList.class);
            // 插入数据
            this.electricitySalesDataService.generateElectricitySalesData(result);
        }
    }


    /**
     * 获取需要推送的电销数据
     *
     * @return
     */
    @PostMapping("/selectElectricitySalesDataPushDataList")
    public ElectricitySalesDataPushListResponse selectElectricitySalesDataPushDataList() {
        ElectricitySalesDataPushListResponse response = new ElectricitySalesDataPushListResponse();
        List<ElectricitySalesDataPushList> recordList = this.electricitySalesDataService.selectElectricitySalesDataPushDataList();
        if (recordList != null && recordList.size() > 0) {
            List<ElectricitySalesDataPushListVO> dataPushDataList = CommonUtils.convertBeanList(recordList, ElectricitySalesDataPushListVO.class);
            response.setResultList(dataPushDataList);
            response.setRtn(Response.SUCCESS);
            return response;
        }
        return response;
    }
}
