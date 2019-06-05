package com.hyjf.am.trade.utils.coupon;

import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class BestCouponUtil {
    public static String dealTenderQuota(MyCouponListCustomizeVO userCouponConfigCustomize) {
        String tenderQuota="";
        switch (userCouponConfigCustomize.getTenderQuotaType()){
            case 0:
                tenderQuota="出借金额不限";
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
     * 处理选中的操作平台
     *
     * @param operationPlatform
     * @param platform 优惠券平台
     * @return
     */
    public static String dealOperationNew(String operationPlatform, String platform) {
        String clientString = "";
        // 操作平台
        //操作平台
        Map<String, String> map = CacheUtil.getParamNameMap("CLIENT");
        // 被选中操作平台
        String clientSed[] = StringUtils.split(operationPlatform, ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "";
                break;
            } else {
                for (String key : map.keySet()) {
                    if (clientSed[i].equals(key)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "、";
                        }
                        clientString = clientString + map.get(key);
                    }
                }
            }
        }
        // app4.0 后台勾选PC、微官网、Android、iOS时，前台不显示
        if ("2".equals(platform) || "3".equals(platform)){
            if(clientString.contains("Android") && clientString.contains("iOS") &&
                    clientString.contains("微官网") && clientString.contains("PC")){
                clientString = "";
            }
        }
        if("Android、iOS".equals(clientString)){
            clientString ="限APP可用";
        }
        if("微官网、Android、iOS".equals(clientString)){
            clientString ="限移动端可用";
        }
        return clientString;
    }

    /**
     * 根据项目类型转换中文项目类型
     *
     * @param projectType 项目类型
     * @return
     */
    public static String dealProjectType(String projectType) {
        String projectString = ",";
        //勾选汇直投，尊享汇，融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标,";
        }
        //勾选汇直投  未勾选尊享汇，融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标,";
        }
        //勾选汇直投，融通宝  未勾选尊享汇
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标,";
        }
        //勾选汇直投，选尊享汇 未勾选融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标,";
        }
        //勾选尊享汇，融通宝  未勾选直投
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标,";
        }
        //勾选尊享汇  未勾选直投，融通宝
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标,";
        }
        //勾选尊享汇  未勾选直投，融通宝
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标,";
        }

        if (projectType.indexOf("3") != -1) {
            projectString = projectString + "新手,";
        }
    		/*if (projectType.indexOf("5")!=-1) {
            projectString = projectString + "汇添金,";
        }*/
        // mod by nxl 智投服务：修改汇计划->智投 start
       /* if (projectType.indexOf("6") != -1) {
            projectString = projectString + "汇计划,";
        }*/
        if (projectType.indexOf("6")!=-1) {
            projectString = projectString + "智投,";
        }
        // mod by nxl 智投服务：修改汇计划->智投 end

        return projectString.substring(1, projectString.length() - 1);
    }

    /**
     * 根据项目类型转换中文项目类型
     *
     * @param projectType 项目类型
     * @return
     */
    public static String dealProjectTypeNew(String projectType) {
        String projectString = "";
        //勾选汇直投，尊享汇，融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标/";
        }
        //勾选汇直投  未勾选尊享汇，融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标/";
        }
        //勾选汇直投，融通宝  未勾选尊享汇
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标/";
        }
        //勾选汇直投，选尊享汇 未勾选融通宝
        if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标/";
        }
        //勾选尊享汇，融通宝  未勾选直投
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标/";
        }
        //勾选尊享汇  未勾选直投，融通宝
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
            projectString = projectString + "散标/";
        }
        //勾选尊享汇  未勾选直投，融通宝
        if (projectType.indexOf("1") == -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
            projectString = projectString + "散标/";
        }
        if (projectType.indexOf("3") != -1) {
            projectString = projectString + "新手/";
        }
        if (projectType.indexOf("6")!=-1) {
            projectString = projectString + "智投/";
        }
        if("散标/新手/智投/".equals(projectString)){
            projectString="项目类型不限";
        }else{
            projectString=projectString.substring(0, projectString.length() - 1);
        }

        return projectString;
    }

    /**
     * 处理优惠券面额
     *
     * @param couponType 优惠券类型
     * @return String
     */
    public static String dealCouponQuota(String couponType) {
        String couponQuota = "";
        if ("2".equals(couponType)) {
            couponQuota = "%";
        }else{
            couponQuota = "元";
        }
        return couponQuota;
    }

    /**
     * 处理选中的操作平台
     *
     * @param operationPlatform
     * @return
     */
    public static String dealOperation(String operationPlatform) {
        String clientString = "";
        // 操作平台
        //操作平台
        Map<String, String> map = CacheUtil.getParamNameMap("CLIENT");
        // 被选中操作平台
        String clientSed[] = StringUtils.split(operationPlatform, ",");
        for (int i = 0; i < clientSed.length; i++) {
            if ("-1".equals(clientSed[i])) {
                clientString = clientString + "全部平台";
                break;
            } else {
                for (String key : map.keySet()) {
                    if (clientSed[i].equals(key)) {
                        if (i != 0 && clientString.length() != 0) {
                            clientString = clientString + "、";
                        }
                        clientString = clientString + map.get(key);
                    }
                }
            }
        }
        if (clientString.contains("Android、iOS")) {
            clientString = clientString.replace("Android、iOS", "APP");
        }
        return clientString;
    }

    /**
     * 处理校验优惠券平台
     * @param couponSystem 优惠券适用平台
     * @param platform 出借平台
     * @return
     */
    public static boolean dealCheckCouponSystem(String couponSystem,String platform){
        String[] couponSystemArr = couponSystem.split(",");
        boolean ifcouponSystem = true;
        for (String couponSystemString : couponSystemArr) {
            if ("-1".equals(couponSystemString)) {
                ifcouponSystem = false;
                break;
            }

            if (platform.equals(couponSystemString)) {
                ifcouponSystem = false;
                break;
            }
        }
        return ifcouponSystem;
    }

    /**
     * 处理项目期限
     * @param style
     * @param type
     * @param projectExpirationLength
     * @param projectExpirationLengthMin
     * @param projectExpirationLengthMax
     * @param borrowPeriod
     * @return
     */
    public static boolean dealProjectExpiration(String style,Integer type,Integer projectExpirationLength,Integer projectExpirationLengthMin,Integer projectExpirationLengthMax,Integer borrowPeriod){
        boolean flag = false;
        if ("endday".equals(style)) {
            if (type == 1) {
                if ((projectExpirationLength * 30) != borrowPeriod) {
                    flag = true;
                }
            } else if (type == 3) {
                if ((projectExpirationLength * 30) > borrowPeriod) {
                    flag = true;
                }
            } else if (type == 4) {
                if ((projectExpirationLength * 30) < borrowPeriod) {
                    flag = true;
                }
            } else if (type == 2) {
                if ((projectExpirationLengthMin * 30) > borrowPeriod
                        || (projectExpirationLengthMax * 30) < borrowPeriod) {
                    flag = true;
                }
            }
        } else {
            if (type == 1) {
                if (!projectExpirationLength.equals(borrowPeriod)) {
                    flag = true;
                }
            } else if (type == 3) {
                if (projectExpirationLength > borrowPeriod) {
                    flag = true;
                }
            } else if (type == 4) {
                if (projectExpirationLength < borrowPeriod) {
                    flag = true;
                }
            } else if (type == 2) {
                if (projectExpirationLengthMin > borrowPeriod || projectExpirationLengthMax < borrowPeriod) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 验证优惠券适用的项目
     * @param projectType 项目类型
     * @param borrowClass 标的类型
     * @return
     */
    public static boolean dealBorrowClass(String projectType,String borrowClass){
        boolean ifprojectType = true;
        if (projectType.indexOf("-1") != -1) {
            if (!"RTB".equals(borrowClass)) {
                ifprojectType = false;
            }
        } else {
            if ("HXF".equals(borrowClass)) {
                if (projectType.indexOf("2") != -1) {
                    ifprojectType = false;
                }
            } else if ("NEW".equals(borrowClass)) {
                if (projectType.indexOf("3") != -1) {
                    ifprojectType = false;
                }
            } else if ("ZXH".equals(borrowClass)) {
                if (projectType.indexOf("4") != -1) {
                    ifprojectType = false;
                }
            } else {
                if (projectType.indexOf("1") != -1) {
                    if (!"RTB".equals(borrowClass)) {
                        ifprojectType = false;
                    }
                }
            }
        }
        return ifprojectType;
    }

    /**
     * 校验体验金是否可用
     * @param addFlag
     * @param money
     * @return
     */
    public static boolean tasteMoneyCheck(Integer addFlag, String money){
        if (addFlag!=null && addFlag == 1 && (!"0".equals(money) && !"".equals(money))) {
            return false;
        }
       return true;
    }
}
