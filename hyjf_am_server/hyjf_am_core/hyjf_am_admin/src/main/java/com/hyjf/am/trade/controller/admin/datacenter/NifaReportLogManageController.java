/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.datacenter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.NifaReportLog;
import com.hyjf.am.trade.service.admin.datacenter.NifaReportLogService;
import com.hyjf.am.vo.admin.NifaReportLogVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogManageController, v0.1 2018/8/17 9:53
 */
@RestController
@RequestMapping("/am-trade/datacenter/nifareportlog")
public class NifaReportLogManageController extends BaseController {

    @Autowired
    private NifaReportLogService nifaReportLogService;


    @RequestMapping(value = "/selectNifaReportLogList", method = RequestMethod.POST)
    public NifaReportLogResponse selectNifaReportLogList(@RequestBody @Valid NifaReportLogRequest request) {
        logger.info("============互金协会报送日志列表显示===========参数为" + JSONObject.toJSONString(request));
        NifaReportLogResponse response = new NifaReportLogResponse();
        int countNifa = nifaReportLogService.countNifaReportLog(request);
        Paginator paginator = new Paginator(request.getCurrPage(), countNifa, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), countNifa);
        }
        response.setRecordTotal(countNifa);
        if (countNifa > 0) {
            List<NifaReportLog> listNifa = nifaReportLogService.selectNifaReportLogList(request, paginator.getOffset(), paginator.getLimit());
            if (CollectionUtils.isNotEmpty(listNifa)) {
                List<NifaReportLogVO> nifaFieldDefinitionVOList = CommonUtils.convertBeanList(listNifa, NifaReportLogVO.class);
                response.setRtn(Response.SUCCESS);
                response.setResultList(nifaFieldDefinitionVOList);
            }
        }
        return response;
    }
    /**
     * 根据id查找互金定义
     * @param logId
     * @return
     */
    @GetMapping(value = "/selectNifaReportLogById/{logId}")
    public NifaReportLogResponse selectNifaReportLogById(@PathVariable int logId) {
        logger.info("============根据id查找互金协会报送日志===========参数为"+ logId);
        NifaReportLogResponse response = new NifaReportLogResponse();
        NifaReportLog nifaReportLog = nifaReportLogService.selectNifaReportLogById(logId);
        NifaReportLogVO nifaReportLogVO = new NifaReportLogVO();
        if(null!=nifaReportLog) {
            BeanUtils.copyProperties(nifaReportLog,nifaReportLogVO);
            response.setResult(nifaReportLogVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

}
