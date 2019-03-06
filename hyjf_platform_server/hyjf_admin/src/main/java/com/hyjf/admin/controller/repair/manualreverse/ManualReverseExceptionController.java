package com.hyjf.admin.controller.repair.manualreverse;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.ManualReverseExceptionService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.resquest.admin.ManualReverseAddRequest;
import com.hyjf.am.resquest.admin.ManualReverseCustomizeRequest;
import com.hyjf.am.vo.admin.ManualReverseCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 手动冲正
 * @author hesy
 * @version ManualReverseExceptionController, v0.1 2018/7/13 14:20
 */
@Api(value = "异常中心-手动冲正", tags = "异常中心-手动冲正")
@RestController
@RequestMapping("/hyjf-admin/exception/manualreverse")
public class ManualReverseExceptionController extends BaseController {
    public static final String PERMISSIONS = "manualreverse";

    @Autowired
    ManualReverseExceptionService manualReverseExceptionService;

    /**
     * 手动冲正列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "手动冲正列表", notes = "手动冲正列表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<ManualReverseCustomizeVO>> getList(@RequestBody ManualReverseCustomizeRequest requestBean){
        Integer count = manualReverseExceptionService.getManualReverseCount(requestBean);
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());

        List<ManualReverseCustomizeVO> recordList = manualReverseExceptionService.getManualReverseList(requestBean);

        return new AdminResult<>(ListResult.build(recordList, count));
    }

    /**
     * 手动冲正更新
     * @auther: hesy
     * @date: 2018/7/14
     */
    @ApiOperation(value = "手动冲正更新", notes = "手动冲正更新")
    @PostMapping("/update_manualreverse")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_ADD)
    public AdminResult manualReverse(HttpServletRequest request, @RequestBody ManualReverseAddRequest requestBean){
        AdminSystemVO adminSystemVO = this.getUser(request);
        requestBean.setLoginUserId(adminSystemVO.getId());
        // 请求参数校验
        boolean checkResult = manualReverseExceptionService.checkForManualReverse(requestBean);
        if(!checkResult){
            return new AdminResult<>(FAIL, "请求参数错误");
        }

        boolean updateResult = manualReverseExceptionService.updateManualReverse(requestBean);
        if(!updateResult){
            return new AdminResult<>(FAIL, "手动冲正更新失败");
        }
        return new AdminResult<>();
    }

    /**
     * 根据用户名获取accountId
     * @param userName
     * @return
     */
    @ApiOperation(value = "根据用户名获取accountId", notes = "根据用户名获取accountId")
    @GetMapping("/get_accountid_byusername/{userName}")
    public AdminResult getAccountIdAction(@PathVariable String userName){
        logger.info("根据用户名获取accountId，{}", userName);
        if(StringUtils.isBlank(userName)){
            return new AdminResult<>(FAIL, "请求参数错误");
        }

        // 查询电子账号
        String accountId = manualReverseExceptionService.getAccountIdByUserName(userName);
        if(StringUtils.isBlank(accountId)){
            logger.info("未获取到用户的电子账号");
            return new AdminResult<>(FAIL, "未获取到用户的电子账号");
        }

        return new AdminResult(accountId);
    }
}
