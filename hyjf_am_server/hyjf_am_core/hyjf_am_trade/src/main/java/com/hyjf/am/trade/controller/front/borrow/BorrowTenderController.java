package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WrbTenderNotifyResponse;
import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.BorrowTenderUpdRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderService;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version BorrowTenderController, v0.1 2018/6/23 11:42
 */
@RestController
@RequestMapping("/am-trade/borrowTender")
public class BorrowTenderController extends BaseController {

    @Autowired
    private BorrowTenderService borrowTenderService;


    /**
     * 获取出借笔数
     * @author zhangyk
     * @date 2018/6/26 9:31
     */
    @GetMapping("/countUserInvest/{borrowNid}/{userId}")
    public BorrowTenderResponse countUserInvest(@PathVariable String borrowNid,@PathVariable Integer userId) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer count  = borrowTenderService.getCountBorrowTenderService(userId, borrowNid);
        response.setTenderCount(count);
        return response;
    }


    @PostMapping("/selectBorrowTender")
    public BorrowTenderResponse selectBorrowTender(@RequestBody BorrowTenderRequest request){
        BorrowTenderResponse response = new BorrowTenderResponse();
        BorrowTender borrowTender =borrowTenderService.selectBorrowTender(request);
        logger.info("获取投标成功结果查询数据库："+borrowTender);
        if (Validator.isNotNull(borrowTender)){
            response.setResult(CommonUtils.convertBean(borrowTender,BorrowTenderVO.class));
        }
        return response;
    }

    @GetMapping("/getFddTempletList/{protocolType}")
    public FddTempletResponse getFddTempletList(@PathVariable Integer protocolType){
        FddTempletResponse response = new FddTempletResponse();
        List<FddTemplet> fddTempletList = borrowTenderService.getFddTempletList(protocolType);
        if (CollectionUtils.isNotEmpty(fddTempletList)){
            response.setResultList(CommonUtils.convertBeanList(fddTempletList,FddTempletVO.class));
        }
        return response;
    }


    @PostMapping("/saveTenderAgreement")
    public int saveTenderAgreement(@RequestBody TenderAgreementVO info){
        TenderAgreement tenderAgreement=CommonUtils.convertBean(info,TenderAgreement.class);
        return borrowTenderService.saveTenderAgreement(tenderAgreement);
    }

    @PostMapping("/updateTenderAgreement")
    public int updateTenderAgreement(@RequestBody TenderAgreementVO tenderAgreement){
        TenderAgreement ta = CommonUtils.convertBean(tenderAgreement,TenderAgreement.class);
        return borrowTenderService.updateTenderAgreement(ta);
    }


    @GetMapping("/getBorrowTenderListByNid/{nid}")
    public BorrowTenderResponse getBorrowTenderListByNid(@PathVariable String nid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.getBorrowTenderListByNid(nid);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResultList(CommonUtils.convertBeanList(tenderList,BorrowTenderVO.class));
        }
        return response;
    }

    /**
     * 根据nid获取一条记录
     * @auther: hesy
     * @date: 2018/7/13
     */
    @GetMapping("/getBorrowTenderByNid/{nid}")
    public BorrowTenderResponse getBorrowTenderByNid(@PathVariable String nid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.getBorrowTenderListByNid(nid);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResult(CommonUtils.convertBean(tenderList.get(0),BorrowTenderVO.class));
        }
        return response;
    }

    /**
     * 根据出借订单号查询已承接金额
     * @param tenderNid
     * @return
     */
    @GetMapping("/get_assign_capital_by_tender_nid/{tenderNid}")
    public BigDecimal getAssignCapital(@PathVariable String tenderNid){
        return borrowTenderService.getAssignCapitalByTenderNid(tenderNid);
    }

    /**
     * 保存债转日志
     * @param creditTenderLogVO
     * @return
     */
    @PostMapping("/save_credit_tender_assign_log")
    public IntegerResponse saveCreditTenderAssignLog(@RequestBody CreditTenderLogVO creditTenderLogVO){
        CreditTenderLog bean = CommonUtils.convertBean(creditTenderLogVO,CreditTenderLog.class);
        logger.info("保存债转数据：{} ",JSONObject.toJSONString(creditTenderLogVO));
        IntegerResponse response = new IntegerResponse();
        try {
            response.setResultInt(borrowTenderService.saveCreditTenderAssignLog(bean));
        }catch (Exception e){
            logger.error(e.getMessage());
            response.setResultInt(0);
        }

        return response;
    }

    /**
     * 获取utm注册用户出借次数
     * @param list utm注册用户userid集合
     * @return
     */
	@PostMapping("/getutmtendernum")
	public BorrowTenderResponse getUtmTenderNum(@RequestBody List<Integer> list) {
		BorrowTenderResponse response = new BorrowTenderResponse();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list, null);
		if (tenderNum != null) {
			response.setTenderCount(tenderNum);
		}
		return response;
	}

    /**
     * 获取utm用户hzt金额
     * @param list utm注册用户userid集合
     * @return
     */
	@PostMapping("/gethzttenderprice")
	public BorrowTenderResponse getHztTenderPrice(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        BigDecimal hztTenderPrice = borrowTenderService.getHztTenderPrice(list);
        if (hztTenderPrice != null) {
            response.setHztTenderPrice(hztTenderPrice);
        }
        return response;
    }

    /**
     * 获取utm用户hxf金额
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gethxftenderprice")
    public BorrowTenderResponse getHxfTenderPrice(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        BigDecimal hxfTenderPrice = borrowTenderService.getHxfTenderPrice(list);
        if (hxfTenderPrice != null) {
            response.setHxfTenderPrice(hxfTenderPrice);
        }
        return response;
    }

    /**
     * 获取utm用户rtb金额
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/getrtbtenderprice")
    public BorrowTenderResponse getRtbTenderPrice(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        BigDecimal rtbTenderPrice = borrowTenderService.getRtbTenderPrice(list);
        if (rtbTenderPrice != null) {
            response.setRtbTenderPrice(rtbTenderPrice);
        }
        return response;
    }

    /**
     * 查询相应的app渠道用户Android出借数
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gettendernumberandroid")
    public BorrowTenderResponse getTenderNumberAndroid(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list, "2");
        if (tenderNum != null) {
            response.setTenderCount(tenderNum);
        }
        return response;
    }

    /**
     * 查询相应的app渠道用户ios出借数
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gettendernumberios")
    public BorrowTenderResponse getTenderNumberIos(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list, "3");
        if (tenderNum != null) {
            response.setTenderCount(tenderNum);
        }
        return response;
    }

    /**
     * 查询相应的app渠道用户pc出借数
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gettendernumberpc")
    public BorrowTenderResponse getTenderNumberPc(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list, "0");
        if (tenderNum != null) {
            response.setTenderCount(tenderNum);
        }
        return response;
    }

    /**
     * 查询相应的app渠道用户wechat出借数
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gettendernumberwechat")
    public BorrowTenderResponse getTenderNumberWechat(@RequestBody List<Integer> list) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list, "1");
        if (tenderNum != null) {
            response.setTenderCount(tenderNum);
        }
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  取得优惠券出借信息
     * @Date 17:24 2018/7/17
     * @Param couponTenderNid
     * @return
     */
    @GetMapping("/getcoupontenderinfo/{couponTenderNid}")
    public BorrowTenderCpnResponse getCouponTenderInfo(@PathVariable String couponTenderNid){
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn = borrowTenderService.getCouponTenderInfo(couponTenderNid);
        response.setResult(CommonUtils.convertBean(borrowTenderCpn,BorrowTenderCpnVO.class));
        return response;
    }

    @GetMapping("/getcurrentcouponrecover/{couponTenderNid}/{periodNow}")
    public CouponRecoverCustomizeResponse getCurrentCouponRecover(@PathVariable String couponTenderNid, @PathVariable Integer periodNow){
        CouponRecoverCustomizeResponse response = new CouponRecoverCustomizeResponse();
        CouponRecoverCustomizeVO couponRecoverCustomizeVO = borrowTenderService.getCurrentCouponRecover(couponTenderNid,periodNow);
        response.setResult(couponRecoverCustomizeVO);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新borrowTenderCpn表
     * @Date 11:12 2018/7/18
     * @Param
     * @return
     */
    @PostMapping("/updateborrowtendercn")
    public BorrowTenderCpnResponse updateBorrowTenderCpn(@RequestBody BorrowTenderCpnVO borrowTenderCpn){
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        Integer flag = borrowTenderService.updateBorrowTenderCpn(borrowTenderCpn);
        response.setFlag(flag);
        return response;
    }

    /**
     * 通过borrowId获取对应的总钱数
     * @param params
     * @return
     */
    @PostMapping("/countMoneyByBorrowId")
    public String countMoneyByBorrowId(@RequestBody Map<String, Object> params){
        return borrowTenderService.countMoneyByBorrowId(params);
    }

    /**
     * 查询固定时间间隔的用户出借列表
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    @GetMapping("/selectBorrowTenderListByDate/{repairStartDate}/{repairStartDate}")
    public BorrowTenderResponse selectBorrowTenderListByDate(@PathVariable String repairStartDate, @PathVariable String repairEndDate){
        BorrowTenderResponse response = new BorrowTenderResponse();
        response.setRtn(Response.FAIL);
        List<BorrowTender> borrowTenderList = borrowTenderService.selectBorrowTenderList(repairStartDate,repairEndDate);
        if(CollectionUtils.isNotEmpty(borrowTenderList)){
            List<BorrowTenderVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList,BorrowTenderVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(borrowTenderVOList);
        }
        return response;
    }
    @PostMapping(value = "/updateBorrowTender")
    public BooleanResponse updateBorrowTender(@RequestBody @Valid BorrowTenderUpdRequest request){
        BorrowTender borrowTender = new BorrowTender();
        BeanUtils.copyProperties(request,borrowTender);
        boolean bol = borrowTenderService.updateBorrowTender(borrowTender);
        return new BooleanResponse(bol);
    }

    /**
     * 根据放款编号获取该标的的出借信息 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    @GetMapping("/getBorrowTenderListByBorrowNid/{borrowNid}")
    public BorrowTenderResponse getBorrowTenderListByBorrowNid(@PathVariable String borrowNid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.getBorrowTenderListByBorrowNid(borrowNid);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResultList(CommonUtils.convertBeanList(tenderList,BorrowTenderVO.class));
        }
        return response;
    }

    /**
     * 查询用户投次数
     *
     * @param userId
     * @return
     */
    @RequestMapping("/countNewUserTotal/{userId}")
    public Integer countNewUserTotal(@PathVariable Integer userId) {
        logger.info("countNewUserTotal...userId is :{}", userId);
        Integer count = borrowTenderService.selectTenderCount(userId);
        logger.info("countNewUserTotal...count is :{}", count);
        return count;
    }


    /**
     * 根据用户ID查询用户出借记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectBorrowTenderByUserId/{userId}")
    public BorrowTenderResponse selectBorrowTenderByUserId(@PathVariable Integer userId){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.selectBorrowTenderByUserId(userId);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResultList(CommonUtils.convertBeanList(tenderList,BorrowTenderVO.class));
        }
        return response;
    }


    /**
     * 根据出借订单号查询出借记录
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/selectBorrowTenderByOrderId/{orderId}")
    public BorrowTenderResponse selectBorrowTenderByOrderId(@PathVariable String orderId){
        BorrowTenderResponse response = new BorrowTenderResponse();
        BorrowTenderRequest request = new BorrowTenderRequest();
        request.setTenderNid(orderId);
        BorrowTender borrowTender = this.borrowTenderService.selectBorrowTender(request);
        if (Validator.isNotNull(borrowTender)){
            response.setResult(CommonUtils.convertBean(borrowTender,BorrowTenderVO.class));
        }
        return response;
    }

    /**
     * app渠道统计- 汇直投出借总额
     * @Param
     * @return
     */
    @PostMapping("/getBorrowTenderByAddtime")
    public WrbTenderNotifyResponse getBorrowTenderByAddtime(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getBorrowTenderByAddtime(request.getSourceIdSrch(),request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道统计-汇转让出借总额
     * @Param
     * @return
     */
    @PostMapping("/getCreditTenderByAddtime")
    public WrbTenderNotifyResponse getCreditTenderByAddtime(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getCreditTenderByAddtime(request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道-用户充值金额
     * @Param
     * @return
     */
    @PostMapping("/getAccountRechargeByAddtime")
    public WrbTenderNotifyResponse getAccountRechargeByAddtime(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getAccountRechargeByAddtime(request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道统计-1
     * @Param
     * @return
     */
    @PostMapping("/getBorrowTenderByClient")
    public WrbTenderNotifyResponse getBorrowTenderByClient(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getBorrowTenderByClient(request.getSourceIdSrch(),request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道统计-2
     * @Param
     * @return
     */
    @PostMapping("/getProductListByClient")
    public WrbTenderNotifyResponse getProductListByClient(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getProductListByClient(request.getSourceIdSrch(),request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道统计-3
     * @Param
     * @return
     */
    @PostMapping("/getDebtPlanAccedeByClient")
    public WrbTenderNotifyResponse getDebtPlanAccedeByClient(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getDebtPlanAccedeByClient(request.getSourceIdSrch(),request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * app渠道统计-4
     * @Param
     * @return
     */
    @PostMapping("/getCreditTenderByClient")
    public WrbTenderNotifyResponse getCreditTenderByClient(@RequestBody AppChannelStatisticsRequest request){
        WrbTenderNotifyResponse response = new WrbTenderNotifyResponse();
        List<WrbTenderNotifyCustomizeVO> flag = borrowTenderService.getCreditTenderByClient(request.getSourceIdSrch(),request.getTimeStartSrch(),request.getTimeEndSrch());
        if(flag.size() != 0){
            response.setResultList(flag);
        }

        return response;
    }

    /**
     * 根据计划订单号查找投资详情
     *
     * @param accedeOrderId
     * @return
     */
    @RequestMapping("/getBorrowTenderByAccede/{accedeOrderId}")
    public BorrowTenderResponse getBorrowTenderByAccede(@PathVariable String accedeOrderId){
        BorrowTenderResponse response = new BorrowTenderResponse();
        response.setRtn(Response.FAIL);
        List<BorrowTender> borrowTenderList = this.borrowTenderService.getBorrowTenderByAccede(accedeOrderId);
        if (CollectionUtils.isNotEmpty(borrowTenderList)){
            List<BorrowTenderVO> borrowTenderVOList = CommonUtils.convertBeanList(borrowTenderList,BorrowTenderVO.class);
            response.setRtn(Response.SUCCESS);
            response.setResultList(borrowTenderVOList);
        }
        return response;
    }
}
