/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.hgdatareport.nifa;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.NifaFileDualService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepairController, v0.1 2019/3/13 18:24
 */
@RestController
@ApiIgnore
@RequestMapping("/hyjf-api/nifaRepair")
public class NifaRepairController {

    @Autowired
    private NifaFileDualService nifaFileDealService;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    /**
     * 查询当天未生成数据的标的号
     *
     * @return
     */
    @GetMapping("/selectNifaNoMongo/{historyData}")
    public List<String> selectNifaNoMongo(@PathVariable String historyData) {

        //-----------------查询数据------------------------------------
        // 查询该天日期所有放款标的
        List<BorrowApicronVO> loanList = this.nifaFileDealService.selectBorrowApicron(historyData);
        // 查询该天日期所有还款标的
        List<BorrowRepayVO> repayList = this.nifaFileDealService.selectBorrowRepayByHistoryData(historyData);
        List<BorrowRepayPlanVO> repayPlanList = this.nifaFileDealService.selectBorrowRepayPlanByHistoryData(historyData);
        // 查询该天日期插入mongo的放还款标的
        List<NifaBorrowInfoVO> nifaList = this.nifaFileDealService.selectNifaBorrowInfoByHistoryData(historyData);

        //-----------------校验数据------------------------------------
        // 当天数据未生成mongo的放到re中返回
        List<String> re = new ArrayList<>();
        // 处理mongo查询出的数据
        Set<String> mongoLoanList = new TreeSet<>();
        Set<String> mongoRepayList = new TreeSet<>();
        // 先把mongo设值、放还款成功的设值正确说明没有生成数据、为空的是一条数据都没生成
        if(CollectionUtils.isNotEmpty(nifaList)) {
            for (NifaBorrowInfoVO vo : nifaList) {
                JSONObject json = JSONObject.parseObject(vo.getMessage());
                if(null != json) {
                    if (StringUtils.isNotBlank(json.getString("repayPeriod"))) {
                        // 还款数据将期数放到标的编号前
                        mongoRepayList.add(json.getString("repayPeriod").concat(vo.getOrganizationNo()));
                    } else {
                        // 放款数据
                        mongoLoanList.add(vo.getOrganizationNo());
                    }
                } else {
                    re.add(vo.getId() + "数据有问题。");
                }
            }
        }
        // 线上有放款数据
        if(CollectionUtils.isNotEmpty(loanList)) {
            // 判断是否生成mongo数据
            for (BorrowApicronVO vo : loanList) {
                // 该日放款成功数据
                if (mongoLoanList.add(vo.getBorrowNid())) {
                    re.add(vo.getBorrowNid());
                }
            }
        }
        Set<String> tmp = new TreeSet<>();
        if(CollectionUtils.isNotEmpty(repayPlanList)) {
            for (BorrowRepayPlanVO vo : repayPlanList) {
                // 分期的borrowNid数据先存一下
                tmp.add(vo.getBorrowNid());
                // 该日分期还款成功数据
                if (mongoRepayList.add(vo.getRepayPeriod().toString().concat(vo.getBorrowNid()))) {
                    re.add(vo.getRepayPeriod().toString().concat(vo.getBorrowNid()));
                }
            }
        }
        // 分期表里存在数据的、此处不再处理  线上有还款数据
        if(CollectionUtils.isNotEmpty(repayList)) {
            for (BorrowRepayVO vo : repayList) {
                // 该日还款成功数据、到期还款期数为1
                if (tmp.add(vo.getBorrowNid()) && mongoRepayList.add("1".concat(vo.getBorrowNid()))) {
                    re.add("1".concat(vo.getBorrowNid()));
                }
            }
        }
        return re;
    }

    /**
     * 查询当天未生成数据的标的号并发送mq重新生成数据
     *
     * @return
     */
    @GetMapping("/selectNifaNoMongoSendMQ/{historyData}")
    public List<String> selectNifaNoMongoSendMQ(@PathVariable String historyData) {

        //-----------------查询数据------------------------------------
        // 查询该天日期所有放款标的
        List<BorrowApicronVO> loanList = this.nifaFileDealService.selectBorrowApicron(historyData);
        // 查询该天日期所有还款标的
        List<BorrowRepayVO> repayList = this.nifaFileDealService.selectBorrowRepayByHistoryData(historyData);
        List<BorrowRepayPlanVO> repayPlanList = this.nifaFileDealService.selectBorrowRepayPlanByHistoryData(historyData);
        // 查询该天日期插入mongo的放还款标的
        List<NifaBorrowInfoVO> nifaList = this.nifaFileDealService.selectNifaBorrowInfoByHistoryData(historyData);

        //-----------------校验数据------------------------------------
        // 当天数据未生成mongo的放到re中返回
        List<String> re = new ArrayList<>();
        // 处理mongo查询出的数据
        Set<String> mongoLoanList = new TreeSet<>();
        Set<String> mongoRepayList = new TreeSet<>();
        // 先把mongo设值、放还款成功的设值正确说明没有生成数据
        if(CollectionUtils.isNotEmpty(nifaList)) {
            for (NifaBorrowInfoVO vo : nifaList) {
                JSONObject json = JSONObject.parseObject(vo.getMessage());
                if(null != json) {
                    if (StringUtils.isNotBlank(json.getString("repayPeriod"))) {
                        // 还款数据将期数放到标的编号前
                        mongoRepayList.add(json.getString("repayPeriod").concat(vo.getOrganizationNo()));
                    } else {
                        // 放款数据
                        mongoLoanList.add(vo.getOrganizationNo());
                    }
                } else {
                    re.add(vo.getId() + "数据有问题。");
                }
            }
        }
        // 判断是否生成mongo数据
        // 放款未生成数据
        if(CollectionUtils.isNotEmpty(loanList)) {
            for (BorrowApicronVO vo : loanList) {
                // 该日放款成功数据
                if (mongoLoanList.add(vo.getBorrowNid())) {
                    re.add(vo.getBorrowNid());
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", vo.getBorrowNid());
                    params.put("historyData", historyData);
                    commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.LOAN_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                            MQConstant.HG_REPORT_DELAY_LEVEL);
                }
            }
        }
        Set<String> tmp = new TreeSet<>();
        // 分期还款未生成数据
        if(CollectionUtils.isNotEmpty(repayPlanList)) {
            for (BorrowRepayPlanVO vo : repayPlanList) {
                // 分期的borrowNid数据先存一下
                tmp.add(vo.getBorrowNid());
                // 该日分期还款成功数据
                if (mongoRepayList.add(vo.getRepayPeriod().toString().concat(vo.getBorrowNid()))) {
                    re.add(vo.getRepayPeriod().toString().concat(vo.getBorrowNid()));
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", vo.getBorrowNid());
                    params.put("repayPeriod", vo.getRepayPeriod());
                    params.put("historyData", historyData);
                    // 推送数据到MQ 还款（每期）
                    commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.REPAY_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                            MQConstant.HG_REPORT_DELAY_LEVEL);
                }
            }
        }
        // 还款未生成数据
        if(CollectionUtils.isNotEmpty(repayList)) {
            for (BorrowRepayVO vo : repayList) {
                // 分期表里存在数据的、此处不再处理  该日还款成功数据、到期还款期数为1
                if (tmp.add(vo.getBorrowNid()) && mongoRepayList.add("1".concat(vo.getBorrowNid()))) {
                    re.add("1".concat(vo.getBorrowNid()));
                    JSONObject params = new JSONObject();
                    params.put("borrowNid", vo.getBorrowNid());
                    params.put("repayPeriod", "1");
                    params.put("historyData", historyData);
                    // 推送数据到MQ 还款（每期）
                    commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.REPAY_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                            MQConstant.HG_REPORT_DELAY_LEVEL);
                }
            }
        }
        return re;
    }

    /**
     * 二期处理该天日期的放还款数据生成文件
     *
     * @return
     */
    @GetMapping("/selectNifaLoanRepayToFile/{historyData}/{endNo}")
    public String selectNifaLoanRepayToFile(@PathVariable String historyData,@PathVariable String endNo) {
        // 结尾数据为传值默认设置001
        if(StringUtils.isBlank(endNo)){
            endNo = "001";
        }
        // 统计系统 24 互联网债权类融资
        String businessZhaiqFileName = COM_SOCIAL_CREDIT_CODE.concat(historyData.replaceAll("-","")).concat("24").concat(endNo);
        // true说明文件已生成
        if(nifaFileDealService.selectNifaReportLogByFileName(businessZhaiqFileName)) {
            return "当前文件已生成:" + businessZhaiqFileName;
        }
        // 统计系统拉取数据生成文件并更新数据库、根据前一天日期生成数据
        boolean fileMakeResult = nifaFileDealService.insertMonitorMakeZhaiFileReportLog(businessZhaiqFileName, historyData);
        if(fileMakeResult) {
            return "当前文件生成成功:" + businessZhaiqFileName;
        }
        return "当前文件生成失败:" + businessZhaiqFileName;
    }

    /**
     * 二期处理该天日期的债款数据生成文件
     *
     * @return
     */
    @GetMapping("/selectNifaCreditToFile/{historyData}/{endNo}")
    public String selectNifaCreditToFile(@PathVariable String historyData,@PathVariable String endNo) {
        // 结尾数据为传值默认设置001
        if(StringUtils.isBlank(endNo)){
            endNo = "001";
        }
        // 26 互联网金融产品及收益权转让融资
        String businessJinrFileName = COM_SOCIAL_CREDIT_CODE.concat(historyData.replaceAll("-","")).concat("26").concat(endNo);
        // true说明文件已生成
        if(nifaFileDealService.selectNifaReportLogByFileName(businessJinrFileName)) {
            return "当前文件已生成:" + businessJinrFileName;
        }
        // 统计系统拉取数据生成文件并更新数据库、根据前一天日期生成数据
        boolean fileMakeResult = nifaFileDealService.insertMonitorMakeJinrFileReportLog(businessJinrFileName, historyData);
        if(fileMakeResult) {
            return "当前文件生成成功:" + businessJinrFileName;
        }
        return "当前文件生成失败:" + businessJinrFileName;
    }
}
