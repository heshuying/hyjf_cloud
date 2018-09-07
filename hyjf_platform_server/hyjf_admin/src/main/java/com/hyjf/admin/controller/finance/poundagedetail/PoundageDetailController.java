/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.poundagedetail;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.PoundageDetailService;
import com.hyjf.admin.service.PoundageService;
import com.hyjf.am.resquest.admin.AdminPoundageDetailRequest;
import com.hyjf.am.vo.admin.PoundageCustomizeVO;
import com.hyjf.am.vo.admin.PoundageDetailVO;
import com.hyjf.am.vo.admin.PoundageLedgerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: PoundageDetailController, v0.1 2018/9/7 9:53
 */
@Api(value = "资金中心-手续费分账详细信息(二级页面)",tags = "资金中心-手续费分账详细信息(二级页面)")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/poundagedetail")
public class PoundageDetailController extends BaseController {
    @Autowired
    private PoundageDetailService poundageDetailService;
    @Autowired
    private PoundageService poundageService;
    @Autowired
    private AdminCommonService adminCommonService;

    @ApiOperation(value = "手续费分账详细信息列表",notes = "手续费分账详细信息列表")
    @GetMapping(value = "/poundagedetaillist")
    public AdminResult poundageDetailList(HttpServletRequest request, @RequestBody AdminPoundageDetailRequest poundageDetailRequest){
        Map<String,Object> result = new HashMap<>();
        Integer loginUserId = Integer.valueOf(getUser(request).getId());
        PoundageCustomizeVO poundageCustomizeVO = poundageService.getPoundageById(loginUserId,poundageDetailRequest.getPoundageId());
        result.put("poundage",poundageCustomizeVO);
        // 查询明细对应的手续费配置项
        PoundageLedgerVO poundageLedgerVO = new PoundageLedgerVO();
        if(poundageCustomizeVO.getLedgerId()!=null) {
            poundageLedgerVO = poundageDetailService.getPoundageLedgerById(poundageCustomizeVO.getLedgerId());
        }
        result.put("poundageLedger", poundageLedgerVO);
        // 设置明细查询条件
        poundageDetailRequest.setLedgerIdSer(poundageCustomizeVO.getLedgerId());
        if(poundageDetailRequest.getLedgerTimeSer()==null) {
            poundageDetailRequest.setLedgerTimeSer(Integer.parseInt(poundageCustomizeVO.getPoundageTime()));
        }
        Integer count = poundageDetailService.getPoundageDetailCount(poundageDetailRequest);
        count = (count == null)?0:count;
        List<PoundageDetailVO> poundageDetailVOList = poundageDetailService.searchPoundageDetailList(poundageDetailRequest);
        result.put("count",count);
        result.put("poundageDetail",poundageDetailVOList);
        return new AdminResult<>();
    }

    /**
     * 获取项目类型下拉框数据
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "项目类型下拉框数据",notes = "项目类型下拉框数据")
    @PostMapping(value = "/getprojecttype")
    public AdminResult<ListResult<DropDownVO>> getSelectorData(){
        List<DropDownVO> projectType = adminCommonService.selectProjectType();
        return new AdminResult<>(ListResult.build(projectType,0));
    }
}
