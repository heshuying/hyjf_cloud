package com.hyjf.am.config.controller.admin.manager;

import com.hyjf.am.config.dao.model.auto.WorkName;
import com.hyjf.am.config.service.BusinessNameMgAmService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.BusinessNameMgResponse;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.vo.config.WorkNameVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 业务名称管理的am
 * @Author: yinhui
 * @Date: 2019/4/15 11:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/am-config/businessNameMg")
public class BusinessNameMgAmController extends BaseController {

    @Autowired
    private BusinessNameMgAmService bsService;

    /**
     * 查找
     */
    @PostMapping("/find")
    public BusinessNameMgResponse findBusinessNameMg(@RequestBody BusinessNameMgRequest request){
        BusinessNameMgResponse response = new BusinessNameMgResponse();

        int recordTotal = bsService.getTotalCount(request);
        response.setCount(recordTotal);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            List<WorkName> recordList = this.bsService.getListBs(request, paginator.getOffset(), paginator.getLimit());
            // 更新数据
            if (recordList != null) {
                List<WorkNameVO> voList = CommonUtils.convertBeanList(recordList, WorkNameVO.class);
                response.setResultList(voList);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 查找名称是否相同
     */
    @PostMapping("/findNameUq")
    public BusinessNameMgResponse findNameUq(@RequestBody BusinessNameMgRequest request){
        BusinessNameMgResponse response = new BusinessNameMgResponse();

        List<WorkName> recordList = this.bsService.findNameUq(request);
        // 更新数据
        if (recordList != null) {
            List<WorkNameVO> voList = CommonUtils.convertBeanList(recordList, WorkNameVO.class);
            response.setResultList(voList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 添加
     */
    @PostMapping("/insert")
    public IntegerResponse insertBusinessNameMg(@RequestBody BusinessNameMgRequest request){
        if (request == null ) {
            return new IntegerResponse(0);
        }
        logger.info("insertBusinessNameMg run... is :{}", request.toString());
        return new IntegerResponse(bsService.insertBs(request));
    }

    /**
     * 编辑业务名称管理详情界面
     */
    @GetMapping("/info/{ids}")
    public BusinessNameMgResponse infoBusinessNameMg(@PathVariable int ids){
        BusinessNameMgResponse response = new BusinessNameMgResponse();
        WorkName workName = bsService.findListBsById(ids);
        // 更新数据
        if (workName != null) {
            WorkNameVO voList = CommonUtils.convertBean(workName, WorkNameVO.class);
            response.setResult(voList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public IntegerResponse updateBusinessNameMg(@RequestBody BusinessNameMgRequest request){
        if (request == null ) {
            return new IntegerResponse(0);
        }
        logger.info("updateBusinessNameMg run... is :{}", request.toString());
        return new IntegerResponse(bsService.updateBs(request));
    }

    /**
     * 修改状态
     */
    @PostMapping("/updateStatus")
    public IntegerResponse updateStatusBusinessNameMg(@RequestBody BusinessNameMgRequest request){
        if (request == null ) {
            return new IntegerResponse(0);
        }
        logger.info("updateStatusBusinessNameMg run... is :{}", request.toString());
        return new IntegerResponse(bsService.updateStatusBs(request));
    }

    /**
     * 查询业务名称
     */
    @PostMapping("/searchbusinessname")
    public BusinessNameMgResponse searchBusinessName(@PathVariable BusinessNameMgRequest request){
        String businessName =request.getBsname();
        logger.info("查询业务名称,businessName:"+businessName);
        BusinessNameMgResponse response = new BusinessNameMgResponse();
        List<WorkNameVO> workNameVOs = bsService.searchBusinessName(businessName);
        logger.info("查询业务名称,workNameVOs:"+businessName);
        if(!CollectionUtils.isEmpty(workNameVOs)){
            response.setResultList(workNameVOs);
            response.setCount(workNameVOs.size());
        }
        return response;
    }
}
