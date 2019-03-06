package com.hyjf.admin.controller.repair.bankrepayfreeze;

import com.hyjf.admin.beans.request.BankRepayFreezeRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.exception.BankRepayFreezeService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 还款冻结异常撤销
 * @author hesy
 * @version BankRepayFreezeController, v0.1 2018/7/11 11:32
 */
@Api(value = "异常中心-还款冻结异常撤销", tags = "异常中心-还款冻结异常撤销")
@RestController
@RequestMapping("/hyjf-admin/exception/repayfreeze_cancel")
public class BankRepayFreezeExceptionController extends BaseController {

    public static final String PERMISSIONS = "bankrepayFreeze";

    @Autowired
    BankRepayFreezeService bankRepayFreezeService;

    /**
     * 冻结异常列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "冻结异常列表", notes = "冻结异常列表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BankRepayFreezeLogVO>> getList(@RequestBody BankRepayFreezeRequest requestBean){
        Integer count = bankRepayFreezeService.getFreezeLogCount();
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);

        List<BankRepayFreezeLogVO> recordList = bankRepayFreezeService.getFreezeLogList(page.getOffset(),page.getLimit());

        return new AdminResult<>(ListResult.build(recordList, count));
    }

    /**
     * 冻结撤销
     * @param orderId
     * @return
     */
    @ApiOperation(value = "冻结撤销", notes = "冻结撤销")
    @GetMapping("/cancel/{orderId}")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult repayFreezeCancel(@PathVariable String orderId){
        logger.info("还款冻结撤销开始，orderId：" + orderId);
        if(StringUtils.isBlank(orderId)){
            return new AdminResult<>(FAIL, "请求参数错误");
        }

        BankRepayFreezeLogVO freezeLogVO = bankRepayFreezeService.getFreezeLogByOrderId(orderId);
        if(freezeLogVO == null){
            logger.info("还款解冻失败，还款冻结可能已经解冻！");
            return new AdminResult<>(FAIL, "还款解冻失败，还款冻结可能已经解冻！");
        }
        boolean repayUnFreezeFlag = this.bankRepayFreezeService.repayUnfreeze(freezeLogVO);
        if(repayUnFreezeFlag){
            boolean unfreezeFlag = this.bankRepayFreezeService.updateBankRepayFreeze(freezeLogVO);
            if (unfreezeFlag) {
                logger.info("还款冻结撤销成功");
                return new AdminResult<>();
            }
        }

        return new AdminResult<>(FAIL, "还款解冻失败，请联系客服！");
    }
}
