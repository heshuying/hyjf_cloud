/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.chinapnr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.vo.trade.ChinapnrBeanVO;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.URLCodec;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.chinapnr.ChinapnrService;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WebChinapnrWithdrawController, v0.1 2018/9/5 15:23
 */
@Api(tags = "web端-用户汇付提现接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/chinapnr/withdraw")
public class WebChinapnrWithdrawController extends BaseTradeController {

    @Autowired
    ChinapnrService chinapnrService;

    private String WITHDRAW_SUCCESS = "/user/withdraw/withdraw_success";
    private String WITHDRAW_INFO = "/user/withdraw/withdraw_info";


    /**
     * 检查参数
     * @param userId
     * @param request
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "检查参数")
    @PostMapping("/check")
    public WebResult check(@RequestHeader(value = "userId") Integer userId,HttpServletRequest request) {
        // 交易金额
        String transAmt = request.getParameter("getcash");
        // 提现银行卡号
        String bankId = request.getParameter("card");
        // 检查参数
        chinapnrService.checkParam( userId, transAmt, bankId);
        return new WebResult();
    }

    /**
     * 跳转到提现页面
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "跳转到提现页面")
    @PostMapping("/toWithdraw")
    public WebResult toWithdraw(@RequestHeader(value = "userId") Integer userId) {
        WebResult result = new WebResult();
        Map<String, Object> map = chinapnrService.toWithdraw(userId);
        result.setData(map);
        return result;
    }

    /**
     * 用户提现
     *
     * @param userId
     * @param map
     * @return
     */
    @ApiOperation(value = "用户提现")
    @ApiImplicitParam(name = "map", value = "{withdrawmoney:String,widCard:String,cashchl:String}", dataType = "Map")
    @PostMapping("/cash")
    public WebResult cash(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> map, HttpServletRequest request) {
        WebResult result = new WebResult();
        UserVO user = chinapnrService.getUserByUserId(userId);
        // 用户名
        String userName = user.getUsername();
        // 交易金额
        String transAmt = map.get("withdrawmoney");
        // 提现银行卡号
        String bankId = map.get("widCard");
        // 取现渠道(暂时无用)
        String cashchl = map.get("cashchl");
        // 检查参数
        chinapnrService.checkParam(userId, transAmt, bankId);
        //汇付提现
        Map<String, Object> resultMap = chinapnrService.cash(userId, transAmt, bankId, cashchl, userName, CustomUtil.getIpAddr(request));
        result.setData(resultMap);
        return result;
    }

    /**
     * 用户提现后处理
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping("/return")
    public ModelAndView cashReturn(HttpServletRequest request,@ModelAttribute ChinapnrBean bean) {
        logger.info("[交易完成后,回调开始]");
        ModelAndView modelAndView = new ModelAndView(WITHDRAW_SUCCESS);
        String callback = request.getParameter("callback");
        bean.convert();
        logger.info("参数: " + bean.getAllParams());
        // 取得更新用UUID
        boolean updateFlag = false;
        String uuid = request.getParameter("uuid");
        if (Validator.isNotNull(uuid)) {
            // 取得检证数据
            ChinapnrExclusiveLogWithBLOBsVO record = chinapnrService.selectChinapnrExclusiveLog(Long.parseLong(uuid));
            // 如果检证数据状态为未发送
            if (record != null && ChinaPnrConstant.STATUS_VERTIFY_OK.equals(record.getStatus())) {
                // 将状态更新成[2:处理中]
                record.setId(Long.parseLong(uuid));
                record.setStatus(ChinaPnrConstant.STATUS_RUNNING);
                int cnt = this.chinapnrService.updateChinapnrExclusiveLog(record);
                if (cnt > 0) {
                    updateFlag = true;
                }
            }
        } else {
            updateFlag = true;
        }
        String info = "";
        // 其他程序正在处理中,或者返回值错误
        // 其他程序正在处理中,或者返回值错误
        if (!updateFlag) {
            if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                modelAndView = new ModelAndView(WITHDRAW_SUCCESS);
                modelAndView.addObject("info", "恭喜您，提现成功");
                modelAndView.addObject("amt", bean.getTransAmt());
            } else if (ChinaPnrConstant.RESPCODE_CHECK.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                modelAndView = new ModelAndView(WITHDRAW_INFO);
                modelAndView.addObject("info", "汇付处理中，请稍后查询交易明细");
            } else {
                modelAndView = new ModelAndView(WITHDRAW_INFO);
                modelAndView.addObject("info", "汇付处理中，请稍后查询交易明细");
            }
            return modelAndView;
        }
        // 发送状态
        String status = ChinaPnrConstant.STATUS_VERTIFY_OK;
        // 失败时去汇付查询交易状态
        if (!ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
            String transStat = chinapnrService.checkCashResult(bean.getOrdId());
            if ("S".equals(transStat)) {
                // 取得成功时的信息
                JSONObject data = chinapnrService.getMsgData(bean.getOrdId());
                if (data != null) {
                    // 设置状态
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_RESPCODE))) {
                        bean.setRespCode(data.getString(ChinaPnrConstant.PARAM_RESPCODE));
                        bean.set(ChinaPnrConstant.PARAM_RESPCODE, data.getString(ChinaPnrConstant.PARAM_RESPCODE));
                    }
                    // 设置结果
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_RESPDESC))) {
                        bean.setRespDesc(data.getString(ChinaPnrConstant.PARAM_RESPDESC));
                        bean.set(ChinaPnrConstant.PARAM_RESPDESC, data.getString(ChinaPnrConstant.PARAM_RESPDESC));
                    }
                    // 设置手续费
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_FEEAMT))) {
                        bean.setFeeAmt(data.getString(ChinaPnrConstant.PARAM_FEEAMT));
                        bean.set(ChinaPnrConstant.PARAM_FEEAMT, data.getString(ChinaPnrConstant.PARAM_FEEAMT));
                    }
                    // 设置取现银行
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_OPENBANKID))) {
                        bean.setOpenBankId(data.getString(ChinaPnrConstant.PARAM_OPENBANKID));
                        bean.set(ChinaPnrConstant.PARAM_OPENBANKID, data.getString(ChinaPnrConstant.PARAM_OPENBANKID));
                    }
                }
            }
        }
        // 成功或审核中或提现失败
        if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE)) || ChinaPnrConstant.RESPCODE_CHECK.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))
                || ChinaPnrConstant.RESPCODE_WITHDRAW_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
            try {
                // 用户userId
                Integer userId = chinapnrService.selectUserIdByUsrcustid(bean.getLong(ChinaPnrConstant.PARAM_USRCUSTID));
                // 执行提现后处理
                ChinaPnrWithdrawRequest chinaPnrWithdrawRequest = new ChinaPnrWithdrawRequest();
                ChinapnrBeanVO beanVO = new ChinapnrBeanVO();
                BeanUtils.copyProperties(bean, beanVO);
                chinaPnrWithdrawRequest.setChinapnrBean(beanVO);
                chinaPnrWithdrawRequest.setUserId(userId);
                chinaPnrWithdrawRequest.setIp(CustomUtil.getIpAddr(request));
                UserVO user = chinapnrService.getUserByUserId(userId);
                UserInfoVO userInfo = chinapnrService.getUsersInfoByUserId(userId);
                chinaPnrWithdrawRequest.setUser(user);
                chinaPnrWithdrawRequest.setUserInfo(userInfo);
                boolean flag = chinapnrService.handlerAfterCash(chinaPnrWithdrawRequest);
                if (flag) {
                    // 执行结果(成功)
                    if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_SUCCESS;
                    } else if (ChinaPnrConstant.RESPCODE_CHECK.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_VERTIFY_OK;
                    } else if (ChinaPnrConstant.RESPCODE_WITHDRAW_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_FAIL;
                    }
                } else {
                    status = ChinaPnrConstant.STATUS_FAIL;
                }
                logger.debug("成功");
            } catch (Exception e) {
                // 执行结果(失败)
                status = ChinaPnrConstant.STATUS_FAIL;
                logger.error("执行结果(失败)" + e);
            }
        } else {
            // 执行结果(失败)
            status = ChinaPnrConstant.STATUS_FAIL;
            // 更新提现失败原因
            String reason = bean.getRespDesc();
            if (StringUtils.isNotEmpty(reason)) {
                if (reason.contains("%")) {
                    reason = URLCodec.decodeURL(reason);
                }
            }
            if (StringUtils.isNotEmpty(bean.getOrdId())) {
                chinapnrService.updateAccountWithdrawByOrdId(bean.getOrdId(), reason);
            }
            Map<String, String> map = new HashMap<String, String>();
            map.put("error", "2");
            map.put("errorDesc", "汇付处理中，请稍后查询交易明细！");
            info = JSON.toJSONString(map);
            try {
                info = URLEncoder.encode(info, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            logger.debug("失败");
            return new ModelAndView("redirect:" + callback + "backinfo/" + info);
        }
        // 更新状态记录
        if (updateFlag && Validator.isNotNull(uuid)) {
            chinapnrService.updateChinapnrExclusiveLogStatus(Long.parseLong(uuid), status);
        }
        if (ChinaPnrConstant.STATUS_SUCCESS.equals(status)) {
            modelAndView = new ModelAndView(WITHDRAW_SUCCESS);
            modelAndView.addObject("amt", bean.getTransAmt());
            modelAndView.addObject("info", "恭喜您，提现成功");
        } else if (ChinaPnrConstant.RESPCODE_CHECK.equals(status)) {
            modelAndView = new ModelAndView(WITHDRAW_INFO);
            modelAndView.addObject("info", "汇付处理中，请稍后查询交易明细");
        } else {
            modelAndView = new ModelAndView(WITHDRAW_INFO);
            modelAndView.addObject("info", "汇付处理中，请稍后查询交易明细");
        }
        return modelAndView;
    }


    /**
     * 用户提现后处理
     *
     * @param request
     * @return
     */
    @ApiIgnore
    @RequestMapping("/callback")
    public WebResult cashCallBack(HttpServletRequest request, @RequestBody ChinapnrBean bean) {
        WebResult result = new WebResult();
        Map<String,Object> map = new HashMap<>();
        logger.info("[交易完成后,回调开始]");
        logger.debug("参数1: " + bean == null ? "无" : bean.getAllParams() + "]");
        bean.convert();
        logger.info("参数bean："+bean.getAllParams());
        // 取得更新用UUID
        boolean updateFlag = false;
        String uuid = request.getParameter("uuid");
        if (Validator.isNotNull(uuid)) {
            // 取得检证数据
            ChinapnrExclusiveLogWithBLOBsVO record = chinapnrService.selectChinapnrExclusiveLog(Long.parseLong(uuid));
            // 如果检证数据状态为未发送
            if (record != null && ChinaPnrConstant.STATUS_VERTIFY_OK.equals(record.getStatus())) {
                // 将状态更新成[2:处理中]
                record.setId(Long.parseLong(uuid));
                record.setStatus(ChinaPnrConstant.STATUS_RUNNING);
                int cnt = this.chinapnrService.updateChinapnrExclusiveLog(record);
                if (cnt > 0) {
                    updateFlag = true;
                }
            }
        } else {
            updateFlag = true;
        }
        // 其他程序正在处理中,或者返回值错误
        if (!updateFlag) {
            map.put("amt", bean.getTransAmt());
            map.put("info", "恭喜您，提现成功");
            result.setData(map);
            return result;
        }
        // 发送状态
        String status = ChinaPnrConstant.STATUS_VERTIFY_OK;
        // 失败时去汇付查询交易状态
        if (!ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
            String transStat = chinapnrService.checkCashResult(bean.getOrdId());
            if ("S".equals(transStat)) {
                // 取得成功时的信息
                JSONObject data = chinapnrService.getMsgData(bean.getOrdId());
                if (data != null) {
                    // 设置状态
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_RESPCODE))) {
                        bean.setRespCode(data.getString(ChinaPnrConstant.PARAM_RESPCODE));
                        bean.set(ChinaPnrConstant.PARAM_RESPCODE, data.getString(ChinaPnrConstant.PARAM_RESPCODE));
                    }
                    // 设置结果
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_RESPDESC))) {
                        bean.setRespDesc(data.getString(ChinaPnrConstant.PARAM_RESPDESC));
                        bean.set(ChinaPnrConstant.PARAM_RESPDESC, data.getString(ChinaPnrConstant.PARAM_RESPDESC));
                    }

                    // 设置手续费
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_FEEAMT))) {
                        bean.setFeeAmt(data.getString(ChinaPnrConstant.PARAM_FEEAMT));
                        bean.set(ChinaPnrConstant.PARAM_FEEAMT, data.getString(ChinaPnrConstant.PARAM_FEEAMT));
                    }
                    // 设置取现银行
                    if (Validator.isNotNull(data.getString(ChinaPnrConstant.PARAM_OPENBANKID))) {
                        bean.setOpenBankId(data.getString(ChinaPnrConstant.PARAM_OPENBANKID));
                        bean.set(ChinaPnrConstant.PARAM_OPENBANKID, data.getString(ChinaPnrConstant.PARAM_OPENBANKID));
                    }
                }
            }
        }
        // 成功或审核中或提现失败
        if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE)) || ChinaPnrConstant.RESPCODE_CHECK.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))
                || ChinaPnrConstant.RESPCODE_WITHDRAW_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
            try {
                // 用户userId
                Integer userId = chinapnrService.selectUserIdByUsrcustid(bean.getLong(ChinaPnrConstant.PARAM_USRCUSTID));
                // 执行提现后处理
                ChinaPnrWithdrawRequest chinaPnrWithdrawRequest = new ChinaPnrWithdrawRequest();
                ChinapnrBeanVO beanVO = new ChinapnrBeanVO();
                BeanUtils.copyProperties(bean, beanVO);
                chinaPnrWithdrawRequest.setChinapnrBean(beanVO);
                chinaPnrWithdrawRequest.setUserId(userId);
                chinaPnrWithdrawRequest.setIp(CustomUtil.getIpAddr(request));
                UserVO user = chinapnrService.getUserByUserId(userId);
                UserInfoVO userInfo = chinapnrService.getUsersInfoByUserId(userId);
                chinaPnrWithdrawRequest.setUser(user);
                chinaPnrWithdrawRequest.setUserInfo(userInfo);
                boolean flag = this.chinapnrService.handlerAfterCash(chinaPnrWithdrawRequest);
                if (flag) {
                    // 执行结果(成功)
                    if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_SUCCESS;
                    } else if (ChinaPnrConstant.RESPCODE_CHECK.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_VERTIFY_OK;
                    } else if (ChinaPnrConstant.RESPCODE_WITHDRAW_FAIL.equals(bean.get(ChinaPnrConstant.PARAM_RESPCODE))) {
                        status = ChinaPnrConstant.STATUS_FAIL;
                    }
                } else {
                    status = ChinaPnrConstant.STATUS_FAIL;
                }
                logger.debug("成功");
            } catch (Exception e) {
                // 执行结果(失败)
                status = ChinaPnrConstant.STATUS_FAIL;
                logger.error("失败信息" + e);
            }
        } else {
            // 执行结果(失败)
            status = ChinaPnrConstant.STATUS_FAIL;
            // 更新提现失败原因
            String reason = bean.getRespDesc();
            if (StringUtils.isNotEmpty(reason)) {
                if (reason.contains("%")) {
                    reason = URLCodec.decodeURL(reason);
                }
            }
            if (StringUtils.isNotEmpty(bean.getOrdId())) {
                this.chinapnrService.updateAccountWithdrawByOrdId(bean.getOrdId(), reason);
            }
            logger.debug("失败");
        }
        // 更新状态记录
        if (updateFlag && Validator.isNotNull(uuid)) {
            this.chinapnrService.updateChinapnrExclusiveLogStatus(Long.parseLong(uuid), status);
        }
        if (ChinaPnrConstant.STATUS_SUCCESS.equals(status)) {
            map.put("amt", bean.getTransAmt());
            map.put("info", "恭喜您，提现成功");
            result.setData(map);
        } else if (ChinaPnrConstant.RESPCODE_CHECK.equals(status)) {
            map.put("info", "汇付处理中，请稍后查询交易明细");
            result.setData(map);
        } else {
            map.put("info", "汇付处理中，请稍后查询交易明细");
            result.setData(map);
        }
        return result;
    }

}
