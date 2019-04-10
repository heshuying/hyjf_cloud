package com.hyjf.cs.trade.service.coupon.impl;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.GetDateUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/23 14:08
 */
@Service
public class MyCouponListServiceImpl extends BaseTradeServiceImpl implements com.hyjf.cs.trade.service.coupon.MyCouponListService {
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 我的优惠券列表（已使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("1");
        List<MyCouponListCustomizeVO> resultList = amTradeClient.selectMyCouponList(requestBean);
        formatCoupon(resultList);
        return resultList;
    }

    /**
     * 我的优惠券列表（未使用）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListUnUsed(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("0");
        List<MyCouponListCustomizeVO> resultList = amTradeClient.selectMyCouponList(requestBean);
        formatCoupon(resultList);
        return resultList;
    }

    /**
     * 我的优惠券列表（已失效）
     * @param userId
     * @return
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponListInValid(String userId){
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag("4");
        List<MyCouponListCustomizeVO> resultList = amTradeClient.selectMyCouponList(requestBean);
        formatCoupon(resultList);
        return resultList;
    }

    public void formatCoupon(List<MyCouponListCustomizeVO> couponList){
        for(MyCouponListCustomizeVO coupon : couponList){
            //操作平台
            Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
            //被选中操作平台
            String clientString = "";
            String clientSed[] = StringUtils.split(coupon.getCouponSystem(), ",");
            for(int i=0 ; i< clientSed.length;i++){
                if("-1".equals(clientSed[i])){
                    clientString=clientString+"";
                    break;
                }else{
                    for (Map.Entry paramName : clients.entrySet()) {
                        if(clientSed[i].equals(paramName.getKey())){
                            if(i!=0&&clientString.length()!=0){
                                clientString=clientString+"、";
                            }
                            clientString=clientString+paramName.getValue();

                        }
                    }
                }
            }
            System.out.println("clientString:"+clientString);
            logger.info("clientString:"+clientString);
            if(clientString.length()==0){
                coupon.setCouponSystem("");
            }else if("Android、iOS".equals(clientString)){
                coupon.setCouponSystem("限APP可用");
            }else if("iOS、Android".equals(clientString)){
                coupon.setCouponSystem("限APP可用");
            }else if("微官网、Android、iOS".equals(clientString)){
                coupon.setCouponSystem("限移动端可用");
            }else if("微官网、iOS、Android".equals(clientString)){
                coupon.setCouponSystem("限移动端可用");
            }else{
                coupon.setCouponSystem("限"+clientString.replace("Android、iOS", "APP").
                        replace("iOS、Android", "APP")+"可用");
            }


            String projectType=coupon.getProjectType();
            String projectString = "";
            if (projectType.indexOf("-1") != -1) {
                projectString = "项目类型不限";
            } else {
                projectString = "限";
                //勾选汇直投，尊享汇，融通宝
                if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")!=-1) {
                    projectString = projectString + "散标/";
                }
                //勾选汇直投  未勾选尊享汇，融通宝
                if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")==-1) {
                    projectString = projectString + "散标/";
                }
                //勾选汇直投，融通宝  未勾选尊享汇
                if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "散标/";
                }
                //勾选汇直投，选尊享汇 未勾选融通宝
                if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")==-1){
                    projectString = projectString + "散标/";
                }
                //勾选尊享汇，融通宝  未勾选直投
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "散标/";
                }
                //勾选尊享汇  未勾选直投，融通宝
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")==-1){
                    projectString = projectString + "散标/";
                }
                //勾选尊享汇  未勾选直投，融通宝
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "散标/";
                }

                if (projectType.indexOf("3")!=-1) {
                    projectString = projectString + "新手/";
                }
                  /*if (projectType.indexOf("5")!=-1) {
                      projectString = projectString + "汇添金,";
                  }*/
                // mod by nxl 智投服务：修改汇计划->智投服务 start
                    /*if (projectType.indexOf("6")!=-1) {
                        projectString = projectString + "汇计划,";
                    }*/
                if (projectType.indexOf("6")!=-1) {
                    projectString = projectString + "智投/";
                }
                // mod by nxl 智投服务：修改汇计划->智投服务 end
                projectString = StringUtils.removeEnd(projectString, "/")+" 可用";
            }
            coupon.setProjectType(projectString);

            coupon.setTenderQuota(dealTenderQuota(coupon));

            long day=DateUtils.differentDaysByString(coupon.getEndTimeStamp(), GetDateUtils.getNowTime()+"");
            if(day>3){
                coupon.setTime(coupon.getAddTime() + "～" + coupon.getEndTime());
            }else{
                coupon.setTime("还有"+(day==0?1:day)+"天过期");
            }

        }
    }

    private String dealTenderQuota(MyCouponListCustomizeVO userCouponConfigCustomize) {
        String tenderQuota="";
        switch (userCouponConfigCustomize.getTenderQuotaType()){
            case 0:
                tenderQuota="不限";
                break;
            case 1:
                if(userCouponConfigCustomize.getTenderQuotaMin()>=10000&&userCouponConfigCustomize.getTenderQuotaMin()%10000==0){
                    tenderQuota=tenderQuota+userCouponConfigCustomize.getTenderQuotaMin().intValue()/10000+"万元~";
                }else{
                    tenderQuota=tenderQuota+userCouponConfigCustomize.getTenderQuotaMin().intValue()+"元~";
                }

                if(userCouponConfigCustomize.getTenderQuotaMax()>=10000&&userCouponConfigCustomize.getTenderQuotaMax()%10000==0){
                    tenderQuota=tenderQuota+userCouponConfigCustomize.getTenderQuotaMax().intValue()/10000+"万元可用";
                }else{
                    tenderQuota=tenderQuota+userCouponConfigCustomize.getTenderQuotaMax().intValue()+"元可用";
                }
                break;
            case 2:
                Double tenderQuotaAmountUp=new Double(userCouponConfigCustomize.getTenderQuotaAmount());
                if(tenderQuotaAmountUp>=10000&&tenderQuotaAmountUp%10000==0){
                    tenderQuota=tenderQuotaAmountUp.intValue()/10000+"万元及以上可用";
                }else{
                    tenderQuota=tenderQuotaAmountUp.intValue()+"元及以上可用";
                }
                break;
            case 3:
                Double tenderQuotaAmountDown=new Double(userCouponConfigCustomize.getTenderQuotaAmount());
                if(tenderQuotaAmountDown>=10000&&tenderQuotaAmountDown%10000==0){
                    tenderQuota=tenderQuotaAmountDown.intValue()/10000+"万元及以下可用";
                }else{
                    tenderQuota=tenderQuotaAmountDown.intValue()+"元及以下可用";
                }
                break;
        }
        return tenderQuota;
    }

    /**
     * 加载邀请页面统计数据
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> selectInvitePageData(String userId){
        Map<String,String> resultMap = new HashMap<String, String>();
        Integer inviteCount = 0;
        Integer couponCount = 0;
        BigDecimal rewardTotal = BigDecimal.ZERO;

        // 累计邀请数
        MyInviteListRequest requestBeanInvite = new MyInviteListRequest();
        requestBeanInvite.setUserId(userId);
        inviteCount = amUserClient.selectMyInviteCount(requestBeanInvite);
        // 累计优惠券数
        MyCouponListRequest requestBeanCoupon = new MyCouponListRequest();
        requestBeanCoupon.setUserId(userId);
        requestBeanCoupon.setUsedFlag("0");
        couponCount = amTradeClient.selectMyCouponCount(requestBeanCoupon);
        // 累计邀请数
        MyInviteListRequest requestBeanReward = new MyInviteListRequest();
        requestBeanReward.setUserId(userId);
        rewardTotal = amTradeClient.selectMyRewardTotal(requestBeanReward);

        resultMap.put("inviteCount", String.valueOf(inviteCount));
        resultMap.put("couponCount", String.valueOf(couponCount));
        resultMap.put("rewardRecordsSum", String.valueOf(rewardTotal));
        resultMap.put("userId", userId);
//        resultMap.put("inviteLink", systemConfig.getWebQrcodeUrl() + "refferUserId=" + userId);
        return resultMap;
    }

    @Override
    public List<MyCouponListCustomizeVO> selectWechatCouponList(String userId,Integer useFlag) {
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId);
        requestBean.setUsedFlag(String.valueOf(useFlag));
        return amTradeClient.selectWechatCouponList(requestBean);
    }


}
