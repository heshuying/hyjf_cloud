package com.hyjf.am.trade.controller.api.aems.overdue;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.AemsOverdueCustomizeResponse;
import com.hyjf.am.trade.service.api.aems.overdue.AemsOverdueService;
import com.hyjf.am.trade.service.hgreportdata.nifa.NifaRepayInfoService;
import com.hyjf.am.vo.trade.AemsOverdueVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/12.
 */
@RestController
@RequestMapping("/am-trade/aems/overdue")
public class AemsOverdueController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NifaRepayInfoService nifaRepayInfoService;
    @Autowired
    private AemsOverdueService aemsOverdueService;


    public static void main(String[] args) {
        Integer t = GetDate.getDayStart10(GetDate.countDate(new Date(),5,-1));
        System.out.println(t);
    }
    /**
     * 查询当期还款是否逾期
     * @param params
     * @return
     */
    @RequestMapping("/selectRepayOverdue")
    public AemsOverdueCustomizeResponse selectRepayOverdue(@RequestBody Map<String, Object> params) {
        Integer t = GetDate.getDayStart10(GetDate.getNowTime());
        params.put("repayTime",t);
        params.put("repayTimeStart",GetDate.getDayStart10(GetDate.countDate(new Date(),5,-1)));

        logger.info("判断当期是否逾期请求参数为params:" + JSONObject.toJSONString(params));
        AemsOverdueCustomizeResponse response = new AemsOverdueCustomizeResponse();
        //查询borrowRepay表
        List<AemsOverdueVO> aemsOverdueVOs = aemsOverdueService.selectRepayOverdue(params);
        List<String> borrowPlanNids= new ArrayList<>();
        if(CollectionUtils.isEmpty(aemsOverdueVOs)){
            logger.info("查询到没有相应的还款信息!");
            return response;
        }
        List<AemsOverdueVO> aemsOverdues= new ArrayList<>();
        for(AemsOverdueVO aemsOverdueVO:aemsOverdueVOs){
            //判断是否是月标
            boolean isMonth = "principal".equals(aemsOverdueVO.getBorrowStyle()) || "month".equals(aemsOverdueVO.getBorrowStyle()) || "endmonth".equals(aemsOverdueVO.getBorrowStyle());
            //单期
            if(!isMonth){
                Integer days= isOverdueFlag(aemsOverdueVO,false);
                //逾期
                if(null != days &&(days ==1||days%30 == 0)){
                    logger.info("查询到相应的还款信息，逾期"+days);
                    aemsOverdueVO.setOverdueDays(String.valueOf(days));
                    //设置逾期数据
                    setOverdueDate(aemsOverdueVO,false);
                    aemsOverdues.add(aemsOverdueVO);
                }
            }else{
                borrowPlanNids.add(aemsOverdueVO.getBorrowNid());
            }
        }

        logger.info("查询到相应的还款计划的borrowNid："+borrowPlanNids);
        //查询分期
        if(!CollectionUtils.isEmpty(borrowPlanNids)){
            params.put("borrowPlanNids",borrowPlanNids);
            //多期
            List<AemsOverdueVO> aemsOverdueVOList = aemsOverdueService.selectRepayPlanOverdue(params);
            for(AemsOverdueVO aemsOverdueVO:aemsOverdueVOList){
                Integer days= isOverdueFlag(aemsOverdueVO,true);
                logger.info("查询到相应的还款信息，逾期"+days);
                //逾期
                if(null != days &&(days==1||days%30 == 0)){
                    aemsOverdueVO.setOverdueDays(String.valueOf(days));
                    //设置逾期数据
                    setOverdueDate(aemsOverdueVO,true);
                    aemsOverdues.add(aemsOverdueVO);
                }
            }
        }
        response.setResultList(aemsOverdues);
        logger.info( "查询逾期数据结果aemsOverdueVO:" + JSONObject.toJSONString(aemsOverdues));
        return response;
    }

    /**
     * 设置逾期总额和本期应还总额
     * @param aemsOverdueVO
     * @param isMonth
     * @return
     */
    public AemsOverdueVO setOverdueDate(AemsOverdueVO aemsOverdueVO,Boolean isMonth){
        // 计算用户逾期总额
        BigDecimal userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(aemsOverdueVO.getPlanRepayAmount(),Integer.valueOf(aemsOverdueVO.getOverdueDays()));
        BigDecimal targetAmount = BigDecimal.ZERO;
        targetAmount = aemsOverdueVO.getTargetAmount().add(userOverdueInterest);
        //设置逾期天数
        aemsOverdueVO.setOverdueDays("D"+aemsOverdueVO.getOverdueDays());
        aemsOverdueVO.setTargetAmount(targetAmount);
        return aemsOverdueVO;
    }

    /**
     * 获取逾期天数
     * @param aemsOverdueVO
     * @param isMonth
     * @return
     */
    public Integer isOverdueFlag(AemsOverdueVO aemsOverdueVO,Boolean isMonth) {

        //还款状态 0:未还款,1:已还款
        Integer status = Integer.valueOf(aemsOverdueVO.getRepayStatus());
        //应还时间
        Date repayTime=GetDate.stringToDate2(aemsOverdueVO.getRepayTime());

        // 延迟天数（所有逾期的未还款的）
        String delayDays = aemsOverdueVO.getDelayDays();
        logger.info("查询到相应的还款信息，还款状态status:" + status + "应还时间repayTime:" + repayTime + "延迟天数delayDays:" + delayDays);

        int distanceDays = 0;
        //未还款状态判断是否逾期
        try {
            distanceDays = GetDate.daysBetween(new Date(), repayTime);
        } catch (Exception e) {
            logger.info("请求：/am-trade/aems/overdue/getRepayOverdueFlag转换时间异常");
        }

        //distanceDays<0逾期或延期中
        if (distanceDays < 0) {
            int lateDay = Integer.valueOf(delayDays) + distanceDays;
            //逾期，返回true，
            if (lateDay < 0) {
                lateDay = -lateDay;
                logger.info("查询到相应的还款信息，borrowNid:"+ aemsOverdueVO.getBorrowNid()+"逾期了" + lateDay + "天");
                return lateDay;
            }
        }
        return null;
    }



}
