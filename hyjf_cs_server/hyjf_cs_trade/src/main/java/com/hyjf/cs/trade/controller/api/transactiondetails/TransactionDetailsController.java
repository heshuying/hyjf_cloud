package com.hyjf.cs.trade.controller.api.transactiondetails;

import com.hyjf.am.vo.trade.ApiTransactionDetailsCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.ResultApiBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
import com.hyjf.cs.trade.service.transactiondetails.TransactionDetailsService;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 第三方接口 交易明细查询
 * @Author : huanghui
 */
@Api(value = "api端 - 第三方交易明细查询",tags = "api端 - 第三方交易明细查询")
@RestController
@RequestMapping(value = "/hyjf-api/server/tradelist")
public class TransactionDetailsController extends BaseTradeController {

    @Autowired
    private TransactionDetailsService transactionDetailsService;
    @Autowired
    private CommonSvrChkService commonSvrChkService;

    @ApiOperation(value = "第三方同步余额",httpMethod = "POST", notes = "同步余额")
    @PostMapping(value = "/tradelist.do")
    @ResponseBody
    public ResultApiBean srchTradeList(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody TransactionDetailsResultBean resultBean){

        /**必传为空校验 start*/
        // 常规参数验证
        CheckUtil.check(Validator.isNotNull(resultBean.getTimestamp()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(resultBean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(resultBean.getChkValue()), MsgEnum.STATUS_CE000001);
        // 自定义参数验证(暂时)
        CheckUtil.check(Validator.isNotNull(resultBean.getStartDate()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(resultBean.getEndDate()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(resultBean.getPhone()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(resultBean.getAccountId()), MsgEnum.STATUS_CE000001);

        /**传入参数逻辑校验 start*/
        // 传入时间格式校验:yyyy-MM-dd
        CheckUtil.check(isValidDate(resultBean.getStartDate()), MsgEnum.STATUS_CE000008);
        CheckUtil.check(isValidDate(resultBean.getEndDate()), MsgEnum.STATUS_CE000008);
        // 日期比較
        CheckUtil.check(compareDate(resultBean.getStartDate(),resultBean.getEndDate()), MsgEnum.STATUS_CE000009);
        //手機號格式
        CheckUtil.check(Validator.isMobile(resultBean.getPhone()), MsgEnum.STATUS_CE000010);
        //手機號不存在
        CheckUtil.check(this.transactionDetailsService.existPhone(resultBean.getPhone()), MsgEnum.STATUS_CE000011);
        //用戶電子賬號不存在
        CheckUtil.check(this.transactionDetailsService.existAccountId(resultBean.getPhone(),resultBean.getAccountId()), MsgEnum.STATUS_CE000012);

        commonSvrChkService.checkLimit(resultBean.getLimitStart(), resultBean.getLimitEnd());
        if(StringUtils.isNotEmpty(resultBean.getTradeStatus())){
            CheckUtil.check(isTradeStatus(resultBean.getTradeStatus().trim()), MsgEnum.ERR_OBJECT_UNMATCH, "交易状态");
        }
        if(StringUtils.isNotEmpty(resultBean.getTypeSearch())){
            CheckUtil.check(isTypeSearch(resultBean.getTypeSearch().trim()), MsgEnum.ERR_OBJECT_UNMATCH, "收支类型");
        }
        if(StringUtils.isNotEmpty(resultBean.getTradeTypeSearch())){
            CheckUtil.check(isGetTradeTypeSearch(resultBean.getTradeTypeSearch().trim()), MsgEnum.ERR_OBJECT_UNMATCH, "交易类型ID");
        }
        // 验证
        CheckUtil.check(SignUtil.verifyRequestSign(resultBean, "/server/tradelist/tradelist"),MsgEnum.ERR_SIGN);
        List<ApiTransactionDetailsCustomizeVO> result = transactionDetailsService.searchTradeList(resultBean);
        // 返回查询结果
        return new ResultApiBean<List<ApiTransactionDetailsCustomizeVO>>(result);
    }

    /**
     * 判断时间格式 格式必须为“yyyy-MM-dd”
     * 2004-2-30 是无效的
     * 2003-2-29 是无效的
     * @param str
     * @return
     */
    public boolean isValidDate(String str) {
        //String str = "2017-10-01";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 判断时间先后
     * @return
     */
    public boolean compareDate(String startDate, String endDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date dt1 = df.parse(startDate);
            Date dt2 = df.parse(endDate);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("开始日期大于结束日期");
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("开始日期小于结束日期");
                return true;
            } else {
                return true;
            }
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 判断交易状态
     * @return
     */
    public boolean isTradeStatus(String str) {
        if(StringUtils.isNotEmpty(str)){
            if(str.equals("0") || str.equals("1") || str.equals("2")){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断收支类型
     * @return
     */
    public boolean isTypeSearch(String str) {
        if(StringUtils.isNotEmpty(str)){
            if(str.equals("1") || str.equals("2") || str.equals("3")|| str.equals("4")){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 交易类型ID
     * @return
     */
    public boolean isGetTradeTypeSearch(String str) {
        if(StringUtils.isNotEmpty(str)){
            if(str.equals("6") || str.equals("228") || str.equals("233")|| str.equals("237")
                    || str.equals("297")|| str.equals("723")|| str.equals("724")|| str.equals("727")
                    || str.equals("763")|| str.equals("485")|| str.equals("706")|| str.equals("757")){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
