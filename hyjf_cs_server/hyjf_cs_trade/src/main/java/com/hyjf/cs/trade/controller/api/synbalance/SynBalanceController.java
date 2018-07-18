package com.hyjf.cs.trade.controller.api.synbalance;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DateDistance;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.BaseResultBean;
import com.hyjf.cs.trade.bean.ResultBean;
import com.hyjf.cs.trade.bean.SynBalanceResultBean;
import com.hyjf.cs.trade.bean.assetpush.SynBalanceRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.SynBalanceService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author pangchengchao
 * @version BankWithdrawController, v0.1 2018/6/12 18:32
 */
@Api(value = "api端同步余额信息接口",description = "api端同步余额信息接口")
@Controller
@RequestMapping("/api/synbalance")
public class SynBalanceController extends BaseTradeController {
    @Autowired
    private SynBalanceService synBalanceService;
    @ApiOperation(value = "用户银行提现", notes = "用户提现")
    @PostMapping("/synbalance")
    public SynBalanceResultBean synBalance(@RequestBody SynBalanceRequestBean synBalanceRequestBean, HttpServletRequest request) {
        logger.info(synBalanceRequestBean.getAccountId()+"第三方同步余额开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(synBalanceRequestBean));
        SynBalanceResultBean resultBean = new SynBalanceResultBean();
        //验证请求参数
        if (Validator.isNull(synBalanceRequestBean.getAccountId())||Validator.isNull(synBalanceRequestBean.getInstCode())) {
            logger.info("-------------------请求参数非法--------------------");
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
            resultBean.setStatusDesc("请求参数非法");
            return resultBean;
        }
        //验签
        if(SignUtil.verifyRequestSign(synBalanceRequestBean, "/synbalance")){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
            logger.info("-------------------验签失败！--------------------");
            resultBean.setStatusDesc("验签失败！");
            return resultBean;
        }
        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = synBalanceService.getBankOpenAccount(synBalanceRequestBean.getAccountId());
        if(bankOpenAccount == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000004);
            logger.info("没有根据电子银行卡找到用户 "+synBalanceRequestBean.getAccountId());
            resultBean.setStatusDesc("没有根据电子银行卡找到用户 ");
            return resultBean;
        }

        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        //用户ID
        UserVO user = synBalanceService.getUsers(bankOpenAccount.getUserId());
        if(user == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000007);
            logger.info("-------------------未找到用户--------------------");
            resultBean.setStatusDesc("未找到用户");

            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        //用户可用余额
        AccountVO accountUser = synBalanceService.getAccount(user.getUserId());
        BigDecimal accountBalance = accountUser.getBankBalance();
        //客户号
        // TODO 从配置文件获取
        if("true".equals("false")){
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankBalance(df.format(accountUser.getBankBalance()));
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc("成功");

            return resultBean;
        }

        //查询时间段 (只查当天)
        //上线需放开
        String startDate = getTxDate(bankOpenAccount);
        String endDate = GetOrderIdUtils.getTxDate();

        // 测试环境
        /*String startDate = "20161226";
        String endDate = "20161226";*/



        //页码定义
        int pageNum = 1;
        int pageSize = 10;
        // 查询参数定义
        String inpDate = "";
        String inpTime = "";
        String relDate = "";
        String traceNo = "";
        //调用查询明细接口 查线下充值数据
        BankCallBean retBean = synBalanceService.queryAccountDetails(user.getUserId(), bankOpenAccount.getAccount(),
                startDate, endDate, "1", "", String.valueOf(pageNum), String.valueOf(pageSize),inpDate,inpTime,relDate,traceNo);

        if(retBean == null){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info("-------------------同步余额失败--------------------");
            resultBean.setStatusDesc("同步余额失败");

            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        //返回失败
        if(!BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())){
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            logger.info("-------------------调用查询接口失败，失败原因：" + synBalanceService.getBankRetMsg(retBean.getRetCode())+"--------------------");
            resultBean.setStatusDesc("调用查询接口失败，失败原因：" + synBalanceService.getBankRetMsg(retBean.getRetCode()));

            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }

        //解析返回数据(记录为空)
        String content = retBean.getSubPacks();
        if(StringUtils.isEmpty(content)){
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setBankBalance(df.format(accountBalance));

            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        //返回结果记录数
        //转换结果
        List<ResultBean> recordList = new ArrayList<ResultBean>();
        List<ResultBean> list = new ArrayList<ResultBean>();
        list = JSONArray.parseArray(retBean.getSubPacks(), ResultBean.class);
        if(list==null|| list.size()==0){
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setBankBalance(df.format(accountBalance));
            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }else{
            ResultBean lastResult = list.get(list.size()-1);
            inpDate = lastResult.getInpDate();
            inpTime = lastResult.getInpTime();
            relDate = lastResult.getRelDate();
            traceNo = String.valueOf(lastResult.getTraceNo());
        }
        recordList.addAll(list);

        while (list.size()==pageSize) {
            pageNum ++;
            //调用查询明细接口 查线下充值数据
            BankCallBean bean = synBalanceService.queryAccountDetails(user.getUserId(), bankOpenAccount.getAccount(),
                    startDate, endDate, "1", "", String.valueOf(pageNum), String.valueOf(pageSize),inpDate,inpTime,relDate,traceNo);
            if(bean == null){
                continue;
            }
            list = JSONArray.parseArray(bean.getSubPacks(), ResultBean.class);
            recordList.addAll(list);
            if(list!=null&&list.size()>0){
                ResultBean lastResult = list.get(list.size()-1);
                inpDate = lastResult.getInpDate();
                inpTime = lastResult.getInpTime();
                relDate = lastResult.getRelDate();
                traceNo = String.valueOf(lastResult.getTraceNo());
            }
        }
        logger.info("-------------------"+recordList.size()+"同步余额总条数--------------------");
        logger.info("-------------------"+pageNum+"同步余额请求次数userid:"+user.getUserId()+"--------------------");
        /**redis 锁 */
        boolean reslut = RedisUtils.tranactionSet("synBalance:"+user.getUserId(),30);
        // 如果没有设置成功，说明有请求来设置过
        if(!reslut){
            //成功???
            resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
            resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
            resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
            resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
            resultBean.setBankBalance(df.format(accountBalance));
            logger.info(this.getClass().getName(), "/synbalance");
            return resultBean;
        }
        for (int i = 0; i < recordList.size(); i++) {
            ResultBean record = recordList.get(i);
            //是否冲正订单
            if(record != null && ("O".equals(record.getOrFlag()) || "0".equals(record.getOrFlag()))){ //原始订单，冲正订单不处理
                //是否是线下充值交易类型
                boolean isType = isRechargeTransType(record.getTranType());
                if(isType){
                    SynBalanceVO synBalanceBean=new SynBalanceVO();
                    // 如果江西银行不返回电子账户号  就设置本地的电子帐户号
                    if(record.getAccountId()==null){
                        record.setAccountId(bankOpenAccount.getAccount());
                    }
                    BeanUtils.copyProperties(record, synBalanceBean);

                    boolean flag=false;

                    try {
                        flag = synBalanceService.insertAccountDetails(accountUser,synBalanceBean,user.getUsername(),GetCilentIP.getIpAddr(request));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(flag){
                        // TODO 活动如需使用重新编写
                        /*CommonSoaUtils.listedTwoRecharge(user.getUserId(), record.getTxAmount());*/
                        //更新金额
                        accountBalance = accountBalance.add(record.getTxAmount());
                    }
                    logger.info("-------------------更新线下充值，电子帐户号："+synBalanceBean.getAccountId()+"完毕--------------------");
                }
            }
        }
        logger.info("-------------------"+synBalanceRequestBean.getUserId()+"同步余额结束--------------------");
        accountUser = synBalanceService.getAccount(user.getUserId());
        resultBean.setOriginalBankTotal(accountUser.getBankTotal().toString());
        resultBean.setOriginalBankBalance(accountUser.getBankBalance().toString());
        resultBean.setBankBalance(df.format(accountUser.getBankBalance()));
        resultBean.setBankTotal(df.format(accountUser.getBankTotal()));
        resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
        resultBean.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);

        logger.info(this.getClass().getName(),"/synbalance");
        return resultBean;
    }
    /**
     * 是否属于线下充值类型
     * @param tranType
     * @return
     */
    private boolean isRechargeTransType(String tranType) {

        if(BankCallConstant.TRANS_TYPE_7617.equals(tranType)||BankCallConstant.TRANS_TYPE_7820.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7821.equals(tranType)||BankCallConstant.TRANS_TYPE_7823.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7826.equals(tranType)||BankCallConstant.TRANS_TYPE_7938.equals(tranType)
                || BankCallConstant.TRANS_TYPE_7939.equals(tranType)){
            return true;
        }
        return false;
    }

    private String getTxDate(BankOpenAccountVO bankOpenAccount) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 2);

        String startDate = dft.format(date.getTime());
        if(bankOpenAccount==null){
            return GetOrderIdUtils.getTxDate();
        }
        Date createTime=bankOpenAccount.getCreateTime();
        String startDate1 ="";
        startDate1=dft.format(createTime);

        try {
            if((DateDistance.getDistanceDays2(GetOrderIdUtils.getTxDate(),startDate1))>=2){
                return startDate;
            }else{
                return startDate1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GetOrderIdUtils.getTxDate();
    }
}
