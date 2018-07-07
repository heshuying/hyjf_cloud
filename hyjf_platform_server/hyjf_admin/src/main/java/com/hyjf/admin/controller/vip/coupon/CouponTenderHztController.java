package com.hyjf.admin.controller.vip.coupon;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponTenderHztService;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/03 16:17
 */
@Api(value = "VIP管理汇直投列表")
@RestController
@RequestMapping("/hyjf-admin/coupon/tender/hzt")
public class CouponTenderHztController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponTenderHztController.class);

    @Autowired
    private CouponTenderHztService couponTenderHztService;

    @ApiOperation(value = "页面初始化", notes = "汇直投使用列表")
    @PostMapping("/init")
    public AdminResult<ListResult<CouponTenderVo>> couponListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        Integer count = this.couponTenderHztService.countRecord(couponTenderRequest);
        lrs.setCount(count);
        if (count != null && count > 0) {
            String investTotal=this.couponTenderHztService.queryInvestTotalHzt(couponTenderRequest);
            List<CouponTenderCustomize>  recordList = this.couponTenderHztService.getRecordList(couponTenderRequest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面详情", notes = "汇直投页面详情")
    @PostMapping("/coupondetail")
    public AdminResult<ListResult<CouponTenderVo>> couponDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderHRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        if(null != couponTenderHRequest.getCouponUserId()){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            // 优惠券发放编号
            paramMap.put("couponUserId", couponTenderHRequest.getCouponUserId());
            paramMap.put("userFlag", 1);

            //详情
            CouponTenderDetailVo detail=new CouponTenderDetailVo();
            detail=couponTenderHztService.getCouponTenderDetailCustomize(paramMap);
            //回款列表
            List<CouponRecoverVo> list=
                    couponTenderHztService.getCouponRecoverCustomize(paramMap);
            //操作平台
            Map<String, String> map =  CacheUtil.getParamNameMap("CLIENT");
            for (String key : map.keySet()) {
                System.out.println("key= "+ key + " and value= " + map.get(key));
            }
            //被选中操作平台
            String clientString = "";
            if(null != detail){

                //被选中操作平台
                String clientSed[] = StringUtils.split(detail.getCouponSystem(), ",");
                for(int i=0 ; i< clientSed.length;i++){
                    if("-1".equals(clientSed[i])){
                        clientString=clientString+"不限";
                        break;
                    }else{
                        for (String key : map.keySet()) {
                            logger.info("key= "+ key + " and value= " + map.get(key));
                            if(clientSed[i].equals(key)){
                                if(i!=0&&clientString.length()!=0){
                                    clientString=clientString+"/";
                                }
                                clientString=clientString+map.get(key);

                            }
                        }
                    }
                }
                detail.setCouponSystem(clientString);
                //被选中项目类型  新逻辑 pcc20160715
                String projectString = "";
                //被选中项目类型
                String projectSed[] = StringUtils.split(detail.getProjectType(), ",");
                if(detail.getProjectType().indexOf("-1")!=-1){
                    projectString="所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
                }else{
                    projectString="所有";
                    for (String project : projectSed) {
                        if("1".equals(project)){
                            projectString=projectString+"汇直投/";
                        }
                        if("2".equals(project)){
                            projectString=projectString+"汇消费/";
                        }
                        if("3".equals(project)){
                            projectString=projectString+"新手汇/";
                        }
                        if("4".equals(project)){
                            projectString=projectString+"尊享汇/";
                        }
                        if("5".equals(project)){
                            projectString=projectString+"汇添金/";
                        }
                        if("6".equals(project)){
                            projectString=projectString+"汇计划/";
                        }
                    }
                    projectString = StringUtils.removeEnd(
                            projectString, "/");
                    projectString=projectString+"项目";
                }
                detail.setProjectType("适用"+projectString);
            }


            couponTenderHztVo.setDetail(detail);
            couponTenderHztVo.setCouponRecoverlist(list);
            return new AdminResult<>(lrs);
        }else{
            return new AdminResult(BaseResult.FAIL,"请选择用户优惠券");
        }
    }
}
