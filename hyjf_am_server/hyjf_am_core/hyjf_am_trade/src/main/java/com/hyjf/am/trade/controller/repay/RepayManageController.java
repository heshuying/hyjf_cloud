package com.hyjf.am.trade.controller.repay;

import java.util.List;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.RepayListResponse;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.repay.RepayManageService;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/26 17:41
 */
@RestController
@RequestMapping("/am-trade/repay")
public class RepayManageController extends BaseController {
    @Autowired
    RepayManageService repayManageService;

    /**
     * 检索还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/repaylist")
    public RepayListResponse repayList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectRepayList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/repaycount")
    public Integer repayCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectRepayCount(requestBean);
        return result;
    }

    /**
     * 检索垫付机构待还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepaylist")
    public RepayListResponse orgRepayList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计垫付机构待还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepaycount")
    public Integer orgRepayCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectOrgRepayCount(requestBean);
        return result;
    }

    /**
     * 检索垫付机构已还款列表
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepayedlist")
    public RepayListResponse orgRepayedList(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        List<RepayListCustomizeVO> resultList = repayManageService.selectOrgRepayedList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 统计垫付机构已还款总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/orgrepayedcount")
    public Integer orgRepayedCount(@RequestBody @Valid RepayListRequest requestBean) {
        RepayListResponse responseBean = new RepayListResponse();
        Integer result = repayManageService.selectOrgRepayedCount(requestBean);
        return result;
    }

    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @RequestMapping(value = "/update")
    public Boolean repayRequestUpdate(@RequestBody @Valid RepayRequestUpdateRequest requestBean){
        logger.info("还款申请更新开始，repuestBean: " + JSON.toJSONString(requestBean));

        if (requestBean == null || StringUtils.isBlank(requestBean.getRepayBeanData()) || StringUtils.isBlank(requestBean.getBankCallBeanData())){
            return false;
        }

        // 更新相关数据库表
        try {
            RepayBean repayBean = JSON.parseObject(requestBean.getRepayBeanData(), RepayBean.class);
            BankCallBean bankCallBean = JSON.parseObject(requestBean.getBankCallBeanData(), BankCallBean.class);

            return repayManageService.updateRepayMoney(repayBean, bankCallBean);
        } catch (Exception e) {
            logger.error("还款申请更新数据库失败", e);
            return false;
        }
    }

}
