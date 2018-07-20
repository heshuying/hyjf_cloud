package com.hyjf.admin.controller.exception.bankdebtend;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.exception.BankCreditEndService;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 异常中心-结束债权异常处理
 * @author hesy
 * @version BankCreditEndController, v0.1 2018/7/12 16:55
 */
@Api(value = "异常中心-结束债权异常处理", description = "异常中心-结束债权异常处理")
@RestController
@RequestMapping("/hyjf-admin/exception/creditend")
public class BankCreditEndExceptionController extends BaseController {
    @Autowired
    BankCreditEndService bankCreditEndService;

    /**
     * 结束债权列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权列表", notes = "结束债权列表")
    @PostMapping("/list")
    public JSONObject getList(@RequestBody BankCreditEndListRequest requestBean){
        Integer count = bankCreditEndService.getCreditEndCount(requestBean);
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());

        List<BankCreditEndVO> recordList = bankCreditEndService.getCreditEndList(requestBean);

        JSONObject jsonObject = this.success(String.valueOf(count),recordList);
        return jsonObject;
    }

    /**
     * 结束债权(新)同步
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权(新)同步", notes = "结束债权(新)同步")
    @PostMapping("/update_frombank")
    public JSONObject updateFromBank(@RequestBody BankCreditEndUpdateRequest requestBean){
        //请求参数校验
        boolean checkResult = bankCreditEndService.checkForUpdate(requestBean);
        if(!checkResult){
            return this.fail("请求参数错误");
        }

        List<BankCallBean> queryList = bankCreditEndService.queryBatchDetails(requestBean);
        if(queryList == null){
            return this.fail("请求银行接口失败");
        }

        boolean updateResult = bankCreditEndService.updateBankCreditEndFromBankQuery(queryList);
        if(!updateResult){
            return this.fail("更新数据库失败");
        }

        return this.success();
    }

    /**
     * 结束债权(新)更新为初始状态
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权(新)更新为初始状态", notes = "结束债权(新)更新为初始状态")
    @PostMapping("/update_forinitial")
    public JSONObject updateForInitial(@RequestBody BankCreditEndUpdateRequest requestBean){
        //请求参数校验
        boolean checkResult = bankCreditEndService.checkForUpdateInitial(requestBean);
        if(!checkResult){
            return this.fail("请求参数错误");
        }

        BankCreditEndVO creditEndVO = bankCreditEndService.getCreditEndByOrderId(requestBean.getOrderId());
        if(creditEndVO == null){
            return this.fail("更新状态失败，没有记录");
        }

        boolean updateResult = bankCreditEndService.updateCreditEndForInitial(creditEndVO);
        if(!updateResult){
            return this.fail("更新数据库失败");
        }

        return this.success();

    }
}
