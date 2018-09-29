package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.coupon.CouponTenderHjhService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 11:17
 */
@Service
public class CouponTenderHjhServiceImpl implements CouponTenderHjhService {
    private Logger logger = LoggerFactory.getLogger(CouponTenderHjhServiceImpl.class);
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public Integer countRecord(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public String queryInvestTotalHjh(CouponTenderRequest couponTenderRequest) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryInvestTotalHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public List<CouponTenderCustomize> getRecordList(CouponTenderRequest couponTenderRequest) {
        couponTenderRequest.setLimitStart(couponTenderRequest.getLimitStart());
        couponTenderRequest.setLimitEnd(couponTenderRequest.getLimitEnd());
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjh(couponTenderRequest);
        if(null != couponTenderResponse){
            return couponTenderResponse.getResultList();
        }
        return null;
    }

    @Override
    public CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getHjhCouponTenderDetailCustomize(paramMap);
        if(null != couponTenderResponse){
            CouponTenderDetailVo couponTenderDetailVo = couponTenderResponse.getDetail();
            if(null != couponTenderDetailVo){
                if("1".equals(couponTenderDetailVo.getCouponContent())){
                    couponTenderDetailVo.setCouponContent(couponTenderDetailVo.getUserContent());
                }else if("2".equals(couponTenderDetailVo.getCouponContent())){
                    Integer activityId = couponTenderDetailVo.getActivityId();
                    if(null != activityId){
                        CouponTenderResponse couponTenderResponse1 = amMarketClient.getActivityById(activityId);
                        couponTenderDetailVo.setCouponContent(couponTenderResponse1.getAttrbute());
                    }
                }else if("3".equals(couponTenderDetailVo.getCouponContent())){
                    couponTenderDetailVo.setCouponContent("欢迎礼包");
                }else{
                    couponTenderDetailVo.setCouponContent("");
                }
                String userId = couponTenderDetailVo.getGrantWay();
                if(null != userId && "40".equals(userId)){
                    couponTenderDetailVo.setGrantWay("系统");
                }else{
                    CouponTenderResponse couponTenderResponse2 = amConfigClient.getAdminUserByUserId(userId);
                    couponTenderDetailVo.setGrantWay(couponTenderResponse2.getAttrbute());
                }
            }
            return couponTenderDetailVo;
        }
        return null;
    }

    @Override
    public List<CouponRecoverVO> getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getHjhCouponRecoverCustomize(paramMap);
        if(null != couponTenderResponse){
            return couponTenderResponse.getCouponRecoverList();
        }
        return null;
    }

    @Override
    public CouponTenderDetailVo dealDetail(CouponTenderDetailVo detail, Map<String, String> map) {

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
            //被选中项目类型
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
        return detail;
    }
}
