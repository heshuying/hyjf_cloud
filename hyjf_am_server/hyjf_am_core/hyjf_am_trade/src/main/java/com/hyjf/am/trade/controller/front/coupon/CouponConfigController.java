/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.admin.CouponRecoverResponse;
import com.hyjf.am.response.admin.TransferExceptionLogResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponTenderCustomizeResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.customize.CouponConfigCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponConfigService;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponConfigController, v0.1 2018/6/19 19:20
 */
@RestController
@RequestMapping("/am-trade/couponConfig")
public class CouponConfigController extends BaseController {
    @Autowired
    private CouponConfigService couponConfigService;

    /**
     * 根据优惠券编码查询优惠券配置信息
     * @param couponCode
     * @return
     */
    @RequestMapping("/selectCouponConfig/{couponCode}")
    public CouponConfigResponse selectCouponConfig(@PathVariable String couponCode) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponConfig couponConfig = couponConfigService.selectCouponConfig(couponCode);
        if (couponConfig != null) {
            CouponConfigVO couponConfigVO = new CouponConfigVO();
            BeanUtils.copyProperties(couponConfig, couponConfigVO);
            response.setResult(couponConfigVO);
        }
        return response;
    }

    /**
     * 后台admin优惠券发行列表
     *
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/getRecordList")
    public CouponConfigCustomizeResponse getRecordList(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        String returnCode = Response.FAIL;
        Map<String, Object> mapParam = paramSet(couponConfigRequest);
        int recordTotal = couponConfigService.countRecord(mapParam);
        Paginator paginator = new Paginator(couponConfigRequest.getCurrPage(), recordTotal, couponConfigRequest.getPageSize());
        if (couponConfigRequest.getPageSize() == 0) {
            paginator = new Paginator(couponConfigRequest.getCurrPage(), recordTotal);
        }
        List<CouponConfigCustomize> recordList = couponConfigService.getRecordList(mapParam, paginator.getOffset(), paginator.getLimit());
        if (recordTotal > 0) {
            if (!CollectionUtils.isEmpty(recordList)) {
                List<CouponConfigCustomizeVO> cvo = CommonUtils.convertBeanList(recordList, CouponConfigCustomizeVO.class);
                response.setResultList(cvo);
                response.setCount(recordTotal);
                response.setRtn(Response.SUCCESS);
            }else {
                 response.setRtn(returnCode);
            }
        }
        return response;
    }

    /**
     * 通过id查询优惠券信息
     * @param couponConfigRequest
     * @return
     */
    @PostMapping("/getCouponConfig")
    public CouponConfigResponse getCouponConfig(@RequestBody CouponConfigRequest couponConfigRequest) {
        CouponConfigResponse ccr = new CouponConfigResponse();
        if (!StringUtils.isEmpty(couponConfigRequest.getId())) {
            CouponConfig ccf = couponConfigService.getCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
            if (ccf != null) {
                CouponConfigVO configVO = new CouponConfigVO();
                BeanUtils.copyProperties(ccf, configVO);
                ccr.setResult(configVO);
            }
            return ccr;
        }
        return ccr;
    }

    /**
     * 加载优惠券配置列表
     * @return
     */
    @PostMapping("/getConfigCustomizeList")
    public CouponConfigCustomizeResponse getConfigCustomizeList() {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        List<CouponConfigCustomize> customizeList = couponConfigService.getConfigCustomizeList(new CouponConfigCustomize());
        if (!CollectionUtils.isEmpty(customizeList)) {
            List<CouponConfigCustomizeVO> customizeVOS = CommonUtils.convertBeanList(customizeList,CouponConfigCustomizeVO.class);
            response.setResultList(customizeVOS);
        }
        return response;
    }

//    /**
//     * 保存优惠券配置信息
//     * @param configRequest
//     * @return
//     */
//    @PostMapping("/saveCouponConfig")
//    public CouponConfigResponse saveCouponConfig(@RequestBody @Valid CouponConfigRequest configRequest) {
//        CouponConfigResponse ccr = new CouponConfigResponse();
//        try {
//            if (StringUtils.isNotEmpty(configRequest.getId())) {
//                CouponConfig couponConfig = new CouponConfig();
//                BeanUtils.copyProperties(configRequest, couponConfig);
//                couponConfig.setId(Integer.valueOf(configRequest.getId()));
//                couponConfig.setUpdateTime(GetDate.getDate());
//                int result = couponConfigService.saveCouponConfig(couponConfig);
//                if (result > 0) {
//                    ccr.setRtn(Response.SUCCESS);
//                } else {
//                    ccr.setRtn(Response.FAIL);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            ccr.setRtn(Response.FAIL);
//            ccr.setMessage(Response.FAIL_MSG);
//        }
//        return ccr;
//    }


    /**
     * 插入优惠券配置信息
     * @param couponConfigRequest
     * @return
     */
//    @PostMapping("/insertCouponConfig")
//    public CouponConfigResponse insertAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
//        CouponConfigResponse ccr = new CouponConfigResponse();
//        try {
//            CouponConfig couponConfig = new CouponConfig();
//            BeanUtils.copyProperties(couponConfigRequest, couponConfig);
//            couponConfig.setCouponCode(GetCode.getCouponCode(couponConfigRequest
//                    .getCouponType()));
//            couponConfig.setStatus(1);
//            couponConfig.setDelFlag(0);
//            couponConfig.setUpdateTime(GetDate.getDate());
//            couponConfig.setCreateTime(GetDate.getDate());
//            int result = couponConfigService.insertAction(couponConfig);
//            if (result > 0) {
//                ccr.setRtn(Response.SUCCESS);
//            } else {
//                ccr.setRtn(Response.FAIL);
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
//        return ccr;
//    }

    /**
     * 根据id删除优惠券信息
     * @param couponConfigRequest
     * @return
     */
//    @PostMapping("/deleteCouponConfig")
//    public CouponConfigResponse deleteAction(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
//        CouponConfigResponse response = new CouponConfigResponse();
//        try {
//            if (StringUtils.isNotEmpty(couponConfigRequest.getId())) {
//                int result = couponConfigService.deleteCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
//                if (result > 0) {
//                    response.setRtn(Response.SUCCESS);
//                } else {
//                    response.setRtn(Response.FAIL);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return response;
//    }

    /**
     * 根据id获取要修改的优惠券信息
     * @param couponConfigRequest
     * @return
     */
//    @PostMapping("/getAuditInfo")
//    public CouponConfigResponse getAuditInfo(@RequestBody @Valid CouponConfigRequest couponConfigRequest) {
//        CouponConfigResponse ccr = new CouponConfigResponse();
//        if (!StringUtils.isEmpty(couponConfigRequest.getId())) {
//            CouponConfig ccf = couponConfigService.getCouponConfig(Integer.parseInt(couponConfigRequest.getId()));
//            if (ccf != null) {
//                CouponConfigVO configVO = new CouponConfigVO();
//                BeanUtils.copyProperties(ccf, configVO);
//                ccr.setResult(configVO);
//            }
//            ccr.setMessage("优惠券信息为空");
//        }
//        return ccr;
//    }


    /**
     * 审核
     * @param request
     * @return
     */
//    @PostMapping("/updateAuditInfo")
//    public CouponConfigResponse updateAuditInfo(@RequestBody @Valid CouponConfigRequest request) {
//        CouponConfigResponse configResponse = new CouponConfigResponse();
//        CouponConfig couponConfig = new CouponConfig();
//        BeanUtils.copyProperties(request,couponConfig);
//        couponConfig.setId(Integer.parseInt(request.getId()));
//        long nowTime = System.currentTimeMillis() / 1000;
//        couponConfig.setAuditUser(request.getAuditUser());
//        couponConfig.setUpdateUserId(Integer.parseInt(request.getAuditUser()));
//        couponConfig.setAuditTime((int)nowTime);
//        couponConfig.setUpdateTime(GetDate.getDate());
//        int result = couponConfigService.saveCouponConfig(couponConfig);
//        if (result > 0) {
//            configResponse.setRtn(Response.SUCCESS);
//        } else {
//            configResponse.setRtn(Response.FAIL);
//            configResponse.setMessage(Response.FAIL_MSG);
//        }
//        return configResponse;
//    }


    /**
     * 根据优惠券编号查询已发行数量
     * @param couponCode
     * @return
     */
    @GetMapping("/checkCouponSendExcess/{couponCode}")
    public CouponConfigCustomizeResponse checkCouponSendExcess(@PathVariable String couponCode) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        int remain = couponConfigService.checkCouponSendExcess(couponCode);
        if (remain > 0) {
            response.setCount(remain);
            return response;
        }
        response.setMessage(Response.FAIL_MSG);
        return response;
    }


    /**
     * 查询条件设置
     *
     * @param couponConfigRequest
     * @return
     */
    private Map<String, Object> paramSet(CouponConfigRequest couponConfigRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("couponName", couponConfigRequest.getCouponName());
        map.put("couponCode", couponConfigRequest.getCouponCode());
        map.put("couponType", couponConfigRequest.getCouponType());
        map.put("status", couponConfigRequest.getStatus());
        map.put("timeStartSrch", couponConfigRequest.getTimeStartSrch());
        map.put("timeEndSrch", couponConfigRequest.getTimeEndSrch());
        return map;
    }

    /**
     * @Author walter.limeng
     * @Description  根据订单ID查询对象
     * @Date 14:19 2018/7/17
     * @Param ordId
     * @return 
     */
    @RequestMapping("/getcouponconfigbyorderid/{ordId}")
    public CouponConfigResponse getCouponConfigByOrderId(@PathVariable String ordId) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponConfigVO couponConfigVO = couponConfigService.getCouponConfigByOrderId(ordId);
        response.setResult(couponConfigVO);
        return response;
    }

    /**
     * @Description  根据优惠券id查询对象
     */
    @RequestMapping("/getCouponConfigById/{couponId}")
    public CouponConfigResponse getCouponConfigById(@PathVariable String couponId) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponConfigVO couponConfigVO = couponConfigService.getCouponConfigById(couponId);
        response.setResult(couponConfigVO);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 14:15 2018/7/17
     * @Param tenderNid 出借订单编号
     * @Param currentRecoverFlg 0:非还款期，1;还款期
     * @Param period 期数
     * @return
     */
    @RequestMapping("/crrecoverperiod/{tenderNid}/{currentRecoverFlg}/{period}")
    public CouponResponse crRecoverPeriod(@PathVariable String tenderNid,@PathVariable Integer currentRecoverFlg,@PathVariable Integer period) {
        CouponResponse response = new CouponResponse();
        Integer count = couponConfigService.crRecoverPeriod(tenderNid,currentRecoverFlg,period);
        response.setTotalRecord(count);
        return response;
    }
    
    /**
     * @Author walter.limeng
     * @Description  根据nid 取得体验金收益期限
     * @Date 14:33 2018/7/17
     * @Param tenderNid
     * @return 
     */
    @RequestMapping("/getcouponprofittime/{tenderNid}")
    public CouponConfigResponse getCouponProfitTime(@PathVariable String tenderNid) {
        CouponConfigResponse response = new CouponConfigResponse();
        Integer count = couponConfigService.getCouponProfitTime(tenderNid);
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:44 2018/7/17
     * @Param CouponRecoverVO
     * @return
     */
    @RequestMapping("/insertcouponrecover")
    public CouponConfigResponse insertCouponRecover(@RequestBody CouponRecoverVO cr) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponRecover couponRecover=CommonUtils.convertBean(cr,CouponRecover.class);
        try{
            if (!"null".equals(cr.getRecoverPeriod()) && StringUtils.isNotBlank(cr.getRecoverPeriod())){
                couponRecover.setRecoverPeriod(Integer.parseInt(cr.getRecoverPeriod()));
            }
            if (cr.getRecoverTime() != null) {
                couponRecover.setRecoverTime(cr.getRecoverTime());
            }
        }catch(Exception e){
            logger.error("insertcouponrecover->格式化异常,tenderId"+cr.getTenderId(),e);
        }
        Integer count = couponConfigService.insertCouponRecover(couponRecover);
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:44 2018/7/17
     * @Param CouponRecoverVO
     * @return
     */
    @RequestMapping("/updateloanstenderhjh")
    public CouponConfigResponse updateOfLoansTenderHjh(@RequestBody AccountVO account) {
        CouponConfigResponse response = new CouponConfigResponse();
        Integer count = couponConfigService.updateOfLoansTenderHjh(account);
        response.setCount(count);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据orderId查询所有待还款优惠券
     * @Date 17:02 2018/7/17
     * @Param orderId
     * @return
     */
    @RequestMapping("/getcoupontenderlisthjh/{orderId}")
    public CouponTenderCustomizeResponse getCouponTenderListHjh(@PathVariable String orderId) {
        CouponTenderCustomizeResponse response = new CouponTenderCustomizeResponse();
        List<CouponTenderCustomizeVO> list = couponConfigService.getCouponTenderListHjh(orderId);
        response.setResultList(list);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新couponRecover对象
     * @Date 9:35 2018/7/18
     * @Param CouponRecoverVO
     * @return
     */
    @RequestMapping("/updatecouponrecoverhjh")
    public CouponRecoverResponse updateCouponRecover(@RequestBody CouponRecoverVO cr) {
        CouponRecoverResponse response = new CouponRecoverResponse();
        couponConfigService.updateCouponRecover(cr);
        response.setResult(cr);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据recoverId查询交易记录
     * @Date 9:44 2018/7/18
     * @Param recoverId
     * @return
     */
    @RequestMapping("/selectbyrecoverid/{recoverId}")
    public TransferExceptionLogResponse selectByRecoverId(@PathVariable Integer recoverId) {
        TransferExceptionLogResponse response = new TransferExceptionLogResponse();
        List<TransferExceptionLogVO> list = couponConfigService.selectByRecoverId(recoverId);
        response.setResultList(list);
        return response;
    }
    /**
     * @Author walter.limeng
     * @Description  新增交易日志数据
     * @Date 9:56 2018/7/18
     * @Param transferExceptionLog
     * @return
     */
    @RequestMapping("/insertTransferexloghjh")
    public TransferExceptionLogResponse insertTransferExLog(@RequestBody TransferExceptionLogVO transferExceptionLog) {
        TransferExceptionLogResponse response = new TransferExceptionLogResponse();
        Integer flag = couponConfigService.insertTransferExLog(transferExceptionLog);
        response.setFlag(flag);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid查询所有优惠券散标还款
     * @Date 16:47 2018/7/18
     * @Param borrowNid
     * @return
     */
    @RequestMapping("/getcoupontenderlist/{borrowNid}")
    public CouponTenderCustomizeResponse getCouponTenderList(@PathVariable String borrowNid) {
        CouponTenderCustomizeResponse response = new CouponTenderCustomizeResponse();
        List<CouponTenderCustomizeVO> list = couponConfigService.getCouponTenderList(borrowNid);
        response.setResultList(list);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 17:09 2018/7/18
     * @Param tenderNid
     * @Param period
     * @return
     */
    @RequestMapping("/updaterecoverperiod/{tenderNid}/{period}")
    public CouponRecoverResponse updateRecoverPeriod(@PathVariable String tenderNid,@PathVariable Integer period) {
        CouponRecoverResponse response = new CouponRecoverResponse();
        Integer flag = couponConfigService.updateRecoverPeriod(tenderNid,period);
        response.setRecordTotal(flag);
        return response;
    }

    @RequestMapping("/selectCouponInterestTotal/{userId}")
    public String selectCouponInterestTotal(@PathVariable Integer userId){
        String total = couponConfigService.selectCouponInterestTotal(userId);
        return total;
    }

    @RequestMapping("/selectCouponReceivedInterestTotal/{userId}")
    public String selectCouponReceivedInterestTotal(@PathVariable Integer userId){
        String total = couponConfigService.selectCouponReceivedInterestTotal(userId);
        return total;
    }


    /**
     * 获取admin优惠券发放配置
     *
     * @param request
     * @return
     */
    @PostMapping("/adminCouponConfig")
    public CouponConfigCustomizeResponse getCouponConfigList(@RequestBody CouponConfigRequest request) {
        CouponConfigCustomizeResponse response = new CouponConfigCustomizeResponse();
        //加载优惠券配置列表
        CouponConfigCustomize configCustomize = new CouponConfigCustomize();
        configCustomize.setStatus(2);
        configCustomize.setLimitStart(-1);
        configCustomize.setLimitEnd(-1);
        List<CouponConfigCustomize> couponConfigCustomizes = couponConfigService.getCouponConfigList(request);
        if (!CollectionUtils.isEmpty(couponConfigCustomizes)) {
            List<CouponConfigCustomizeVO> couponConfigCustomizeVOS = CommonUtils.convertBeanList(couponConfigCustomizes, CouponConfigCustomizeVO.class);
            response.setResultList(couponConfigCustomizeVOS);
        }
        return response;
    }

    /**
     * 查询导出列表
     * @param request
     * @return
     */
//    @RequestMapping("/getExportConfigList")
//    public CouponConfigExportCustomizeResponse getExportConfigList(@RequestBody CouponConfigRequest request) {
//        CouponConfigExportCustomizeResponse response = new CouponConfigExportCustomizeResponse();
//        CouponConfigCustomize configCustomize = new CouponConfigCustomize();
//        BeanUtils.copyProperties(request,configCustomize);
//        List<CouponConfigExportCustomize> configExportCustomizes = couponConfigService.exoportRecordList(configCustomize);
//        if (!CollectionUtils.isEmpty(configExportCustomizes)) {
//            List<CouponConfigExportCustomizeVO> configExportCustomizeVOS = CommonUtils.convertBeanList(configExportCustomizes,CouponConfigExportCustomizeVO.class);
//            response.setResultList(configExportCustomizeVOS);
//        }
//        return response;
//    }

    /**
     * 根据tenderNid查询总数
     * @param tenderNid
     * @return
     */
    @RequestMapping("/countbytendernid/{tenderNid}")
    public CouponResponse countByTenderNid(@PathVariable String tenderNid) {
        CouponResponse response = new CouponResponse();
        Integer count = couponConfigService.countByTenderNid(tenderNid);
        response.setTotalRecord(count);
        return response;
    }
}
