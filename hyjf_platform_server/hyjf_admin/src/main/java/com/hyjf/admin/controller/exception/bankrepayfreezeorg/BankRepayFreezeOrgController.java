package com.hyjf.admin.controller.exception.bankrepayfreezeorg;

import com.hyjf.admin.beans.request.RepayFreezeOrgRequestBean;
import com.hyjf.admin.beans.response.UserManagerInitResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.exception.BankRepayFreezeOrgService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.vo.admin.BankRepayFreezeOrgCustomizeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 担保机构还款冻结异常处理
 * @author hesy
 * @version BankRepayFreezeOrgController, v0.1 2018/7/11 11:32
 */
@Api(value = "异常中心-担保机构还款冻结异常处理", tags = "异常中心-担保机构还款冻结异常处理")
@RestController
@RequestMapping("/hyjf-admin/exception/bankRepayFreezeOrg")
public class BankRepayFreezeOrgController extends BaseController {
    @Autowired
    private AdminCommonService adminCommonService;
    @Autowired
    private BankRepayFreezeOrgService bankRepayFreezeOrgService;

    @ApiOperation(value = "担保机构还款冻结异常页面初始化", notes = "担保机构还款冻结异常页面初始化")
    @PostMapping(value = "/init")
    public AdminResult<Map<String,Object>> init() {
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();

        Map<String,Object> data = new HashMap<>();
        data.put("instList", hjhInstConfigList);

        return new AdminResult<>(data);
    }

    @ApiOperation(value = "担保机构还款冻结异常列表", notes = "担保机构还款冻结异常列表")
    @PostMapping(value = "/list")
    public AdminResult<ListResult<BankRepayFreezeOrgCustomizeVO>> list(@RequestBody RepayFreezeOrgRequestBean requestBean) {
        RepayFreezeOrgRequest repayFreezeOrgRequest = new RepayFreezeOrgRequest();
        BeanUtils.copyProperties(requestBean,repayFreezeOrgRequest);
        Integer count = bankRepayFreezeOrgService.selectCount(repayFreezeOrgRequest);

        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);
        repayFreezeOrgRequest.setLimitStart(page.getOffset());
        repayFreezeOrgRequest.setLimitEnd(page.getLimit());

        List<BankRepayFreezeOrgCustomizeVO> resultList = bankRepayFreezeOrgService.selectList(repayFreezeOrgRequest);
        return new AdminResult<>(ListResult.build(resultList, count));
    }
}
