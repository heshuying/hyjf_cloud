package com.hyjf.am.trade.controller.admin.increaseinterest;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayService;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestInvestDetailController, v0.1 2018/8/30
 */
@Api(value = "产品中心-汇直投-加息还款信息")
@RestController
@RequestMapping("/am-trade/increaseinterest")
public class IncreaseInterestRepayController extends BaseController {
    @Autowired
    IncreaseInterestRepayService increaseInterestRepayService;

    @RequestMapping(value = "/getIncreaseInterestRepayCount",method = RequestMethod.POST)
    public IncreaseInterestRepayResponse getIncreaseInterestRepayCount(@RequestBody IncreaseInterestRepayRequest request){
        IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
        int count = increaseInterestRepayService.getIncreaseInterestRepayCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping(value = "/getIncreaseInterestRepayList",method = RequestMethod.POST)
    public IncreaseInterestRepayResponse getIncreaseInterestRepayList(@RequestBody IncreaseInterestRepayRequest request){
        IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
        List<IncreaseInterestRepayVO> list = increaseInterestRepayService.getIncreaseInterestRepayList(request);
        /*--------add by LSY START-----------*/
        String sumAccount = this.increaseInterestRepayService.sumAccount(request);
        response.setSumAccount(sumAccount);
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

    @RequestMapping(value = "/getInterestRepaySumAccount",method = RequestMethod.POST)
    public IncreaseInterestRepayResponse getSumAccount(@RequestBody IncreaseInterestRepayRequest request){
        IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
        /*--------add by LSY START-----------*/
        String sumAccount = this.increaseInterestRepayService.sumAccount(request);
        //response.setSumAccount(sumAccount);
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != sumAccount) {
            response.setSumAccount(sumAccount);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

}
