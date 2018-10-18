package com.hyjf.cs.trade.service.coupon.impl;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
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
                    clientString=clientString+"不限";
                    break;
                }else{
                    for (Map.Entry paramName : clients.entrySet()) {
                        if(clientSed[i].equals(paramName.getKey())){
                            if(i!=0&&clientString.length()!=0){
                                clientString=clientString+"/";
                            }
                            clientString=clientString+paramName.getValue();

                        }
                    }
                }
            }
            coupon.setCouponSystem(clientString.replace("Android/iOS", "APP"));



            String projectType=coupon.getProjectType();
            String projectString = "";
            if (projectType.indexOf("-1") != -1) {
                projectString = "不限";
            } else {
                //勾选汇直投，尊享汇，融通宝
                if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")!=-1) {
                    projectString = projectString + "债权,";
                }
                //勾选汇直投  未勾选尊享汇，融通宝
                if (projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")==-1) {
                    projectString = projectString + "债权(尊享,优选除外),";
                }
                //勾选汇直投，融通宝  未勾选尊享汇
                if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "债权(尊享除外),";
                }
                //勾选汇直投，选尊享汇 未勾选融通宝
                if(projectType.indexOf("1")!=-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")==-1){
                    projectString = projectString + "债权(优选除外),";
                }
                //勾选尊享汇，融通宝  未勾选直投
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "债权(仅限尊享,优选),";
                }
                //勾选尊享汇  未勾选直投，融通宝
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")!=-1&&projectType.indexOf("7")==-1){
                    projectString = projectString + "债权(仅限尊享),";
                }
                //勾选尊享汇  未勾选直投，融通宝
                if(projectType.indexOf("1")==-1&&projectType.indexOf("4")==-1&&projectType.indexOf("7")!=-1){
                    projectString = projectString + "债权(仅限优选),";
                }

                if (projectType.indexOf("3")!=-1) {
                    projectString = projectString + "新手,";
                }
                  /*if (projectType.indexOf("5")!=-1) {
                      projectString = projectString + "汇添金,";
                  }*/
                // mod by nxl 智投服务：修改汇计划->智投服务 start
                    /*if (projectType.indexOf("6")!=-1) {
                        projectString = projectString + "汇计划,";
                    }*/
                if (projectType.indexOf("6")!=-1) {
                    projectString = projectString + "智投,";
                }
                // mod by nxl 智投服务：修改汇计划->智投服务 end
                projectString = StringUtils.removeEnd(projectString, ",");
            }
            coupon.setProjectType(projectString);

        }
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
        resultMap.put("inviteLink", systemConfig.frontHost + "/register?from=" + userId);
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
