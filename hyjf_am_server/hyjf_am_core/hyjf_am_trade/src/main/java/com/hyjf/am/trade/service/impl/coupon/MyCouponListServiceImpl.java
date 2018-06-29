package com.hyjf.am.trade.service.impl.coupon;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.customize.coupon.MyCouponListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.BorrowProjectType;
import com.hyjf.am.trade.service.BorrowInfoService;
import com.hyjf.am.trade.service.BorrowProjectTypeService;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/22 19:15
 */
@Service
public class MyCouponListServiceImpl implements com.hyjf.am.trade.service.coupon.MyCouponListService {
    @Resource
    MyCouponListCustomizeMapper myCouponListCustomizeMapper;

    @Resource
    BorrowMapper borrowMapper;

    @Resource
    BorrowService borrowService;

    @Resource
    BorrowProjectTypeService borrowProjectTypeService;

    @Resource
    BorrowInfoService borrowInfoService;

    /**
     * 检索我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/22
     */
    @Override
    public List<MyCouponListCustomizeVO> selectUserCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        if (limitStart != null) {
            param.put("limitStart", limitStart);
        }else {
            param.put("limitStart", -1);
        }
        if (limitEnd != null) {
            param.put("limitEnd", limitEnd);
        }else {
            param.put("limitEnd", -1);
        }

        return myCouponListCustomizeMapper.selectMyCouponList(param);
    }

    /**
     * 统计总记录数
     * @param userId
     * @param usedFlag
     * @return
     */
    @Override
    public Integer countUserCouponList(String userId, String usedFlag){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        Integer result =  myCouponListCustomizeMapper.countMyCouponList(param);
        if(result == null){
            result = 0;
        }
        return result;
    }

    /**
     * 查询最优优惠券
     * @author zhangyk
     * @date 2018/6/25 9:58
     */
    @Override
    public BestCouponListVO selectBestCouponList(MyCouponListRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = request.getUserId();
        String borrowNid = request.getBorrowNid();
        String money = request.getMoney();
        String platform = request.getPlatform();
        map.put("userId", userId);
        // 查询项目信息
        Borrow borrow = borrowService.getBorrow(borrowNid);
        BorrowInfo borrowInfo = borrowInfoService.getBorrowInfo(borrowNid);
        BorrowProjectType borrowProjectType = borrowProjectTypeService.getProjectTypeByBorrowNid(borrowNid);
        String style = borrow.getBorrowStyle();
        // 加息券是否启用 0禁用 1启用
        Integer couponFlg = borrowInfo.getBorrowInterestCoupon();
        // 体验金是否启用 0禁用 1启用
        Integer moneyFlg = borrowInfo.getBorrowTasteMoney();
        List<BestCouponListVO> couponConfigs = myCouponListCustomizeMapper.getCouponConfigList(map);
        // 排序
        Collections.sort(couponConfigs, new ComparatorCouponBean());
        for (BestCouponListVO bestCoupon : couponConfigs) {
            // 验证项目加息券或体验金是否可用
            if (couponFlg != null && couponFlg == 0) {
                if (bestCoupon.getCouponType() == 2) {
                    continue;
                }
            }
            if (moneyFlg != null && moneyFlg == 0) {
                if (bestCoupon.getCouponType() == 1) {
                    continue;
                }
            }
            // 验证项目期限、
            Integer type = bestCoupon.getProjectExpirationType();
            if ("endday".equals(style)) {
                if (type == 1) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) != borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 3) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) > borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 4) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) < borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 2) {
                    if ((bestCoupon.getProjectExpirationLengthMin() * 30) > borrow.getBorrowPeriod()
                            || (bestCoupon.getProjectExpirationLengthMax() * 30) < borrow.getBorrowPeriod()) {
                        continue;
                    }
                }
            } else {
                if (type == 1) {
                    if (!bestCoupon.getProjectExpirationLength().equals(borrow.getBorrowPeriod())) {
                        continue;
                    }
                } else if (type == 3) {
                    if (bestCoupon.getProjectExpirationLength() > borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 4) {
                    if (bestCoupon.getProjectExpirationLength() < borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 2) {
                    if (bestCoupon.getProjectExpirationLengthMin() > borrow.getBorrowPeriod() || bestCoupon.getProjectExpirationLengthMax() < borrow.getBorrowPeriod()) {
                        continue;
                    }
                }
            }

            // 验证项目金额
            Integer tenderQuota = bestCoupon.getTenderQuotaType();
            if (tenderQuota == 1) {
                if (bestCoupon.getTenderQuotaMin() > new Double(money) || bestCoupon.getTenderQuotaMax() < new Double(money)) {
                    continue;
                }
            } else if (tenderQuota == 2) {
                if (bestCoupon.getTenderQuota() > new Double(money)) {
                    continue;
                }
            }
            // 验证优惠券适用的项目 新逻辑 pcc20160715
            String projectType = bestCoupon.getProjectType();
            boolean ifprojectType = true;
            if (projectType.indexOf("-1") != -1) {
                if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
                    ifprojectType = false;
                }
            } else {
                if ("HXF".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("2") != -1) {
                        ifprojectType = false;
                    }
                } else if ("NEW".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("3") != -1) {
                        ifprojectType = false;
                    }
                } else if ("ZXH".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("4") != -1) {
                        ifprojectType = false;
                    }
                } else {
                    if (projectType.indexOf("1") != -1) {
                        if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
                            ifprojectType = false;
                        }
                    }
                }
            }
            if (ifprojectType) {
                continue;
            }
            // 验证使用平台
            String couponSystem = bestCoupon.getCouponSystem();
            String[] couponSystemArr = couponSystem.split(",");
            for (String couponSystemString : couponSystemArr) {
                if ("-1".equals(couponSystemString)) {
                    return bestCoupon;
                }
                if ((couponSystemString).equals(platform)) {
                    return bestCoupon;
                }
            }
        }
        return null;
    }

    /**
     * 可用的优惠券数
     * @author zhangyk
     * @date 2018/6/25 13:54
     */
    @Override
    public Integer countAvaliableCoupon(MyCouponListRequest request) {

        String userId = request.getUserId();
        String borrowNid = request.getBorrowNid();
        String money = request.getMoney();
        String platform = request.getPlatform();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        // 查询项目信息
        Borrow borrow = borrowService.getBorrow(borrowNid);
        BorrowInfo borrowInfo = borrowInfoService.getBorrowInfo(borrowNid);
        BorrowProjectType borrowProjectType = borrowProjectTypeService.getProjectTypeByBorrowNid(borrowNid);
        Integer couponFlg = borrowInfo.getBorrowInterestCoupon();
        Integer moneyFlg = borrowInfo.getBorrowTasteMoney();
        List<BestCouponListVO> couponConfigs = myCouponListCustomizeMapper.getCouponConfigList(map);

        Integer count = 0;

        String style = borrow.getBorrowStyle();

        if (money == null || "".equals(money) || money.length() == 0) {
            money = "0";
        }
        for (BestCouponListVO bestCoupon : couponConfigs) {

            // 验证项目加息券或体验金是否可用
            if (couponFlg != null && couponFlg == 0) {
                if (bestCoupon.getCouponType() == 2) {
                    continue;
                }
            }
            if (moneyFlg != null && moneyFlg == 0) {
                if (bestCoupon.getCouponType() == 1) {
                    continue;
                }
            }
            // 验证项目金额
            Integer tenderQuota = bestCoupon.getTenderQuotaType();

            if (tenderQuota == 1) {
                if (bestCoupon.getTenderQuotaMin() > new Double(money) || bestCoupon.getTenderQuotaMax() < new Double(money)) {
                    continue;
                }
            } else if (tenderQuota == 2) {
                if (bestCoupon.getTenderQuota() > new Double(money)) {
                    continue;
                }
            }
            // 验证项目期限
            Integer type = bestCoupon.getProjectExpirationType();
            if ("endday".equals(style)) {
                if (type == 1) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) != borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 3) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) > borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 4) {
                    if ((bestCoupon.getProjectExpirationLength() * 30) < borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 2) {
                    if ((bestCoupon.getProjectExpirationLengthMin() * 30) > borrow.getBorrowPeriod()
                            || (bestCoupon.getProjectExpirationLengthMax() * 30) < borrow.getBorrowPeriod()) {
                        continue;
                    }
                }
            } else {
                if (type == 1) {
                    if (!bestCoupon.getProjectExpirationLength().equals(borrow.getBorrowPeriod())) {
                        continue;
                    }
                } else if (type == 3) {
                    if (bestCoupon.getProjectExpirationLength() > borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 4) {
                    if (bestCoupon.getProjectExpirationLength() < borrow.getBorrowPeriod()) {
                        continue;
                    }
                } else if (type == 2) {
                    if (bestCoupon.getProjectExpirationLengthMin() > borrow.getBorrowPeriod() || bestCoupon.getProjectExpirationLengthMax() < borrow.getBorrowPeriod()) {
                        continue;
                    }
                }

            }
            // 验证可使用项目
            String projectType = bestCoupon.getProjectType();
            // String[] projectTypeArr=projectType.split(",");
            boolean ifprojectType = true;

            if (projectType.indexOf("-1") != -1) {
                if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
                    ifprojectType = false;
                }
            } else {
                if ("HXF".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("2") != -1) {
                        ifprojectType = false;
                    }
                } else if ("NEW".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("3") != -1) {
                        ifprojectType = false;
                    }
                } else if ("ZXH".equals(borrowProjectType.getBorrowClass())) {
                    if (projectType.indexOf("4") != -1) {
                        ifprojectType = false;
                    }
                } else {
                    if (projectType.indexOf("1") != -1) {
                        if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
                            ifprojectType = false;
                        }
                    }
                }
            }
            if (ifprojectType) {
                continue;
            }
            /**************逻辑修改 pcc start***************/
            //是否与本金公用
            boolean addFlg = false;
            if(bestCoupon.getAddFlg()==1&&!"0".equals(money)){
                addFlg = true;
            }
            if(addFlg){
                continue;
            }
            /**************逻辑修改 pcc end***************/
            // 验证使用平台
            String couponSystem = bestCoupon.getCouponSystem();
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
            if (ifcouponSystem) {
                continue;
            } else {
                //CouponBean couponBean = createCouponBean(userCouponConfigCustomize, new CouponBean(), null);
               // availableCouponList.add(couponBean);
                count ++;
            }
        }
        // System.out.println("~~~~~~~~~~~~~~~~统计结束~~~~~~~~~~~~~~~~~~"+availableCouponList.size());
        return count;
    }


    /**
     * 比较器
     * @author zhangyk
     * @date 2018/6/25 11:18
     */
    public class ComparatorCouponBean implements Comparator<BestCouponListVO> {

        @Override
        public int compare(BestCouponListVO couponBean1, BestCouponListVO couponBean2) {
            if (1 == couponBean1.getCouponType()) {
                couponBean1.setCouponType(4);
            }
            if (1 == couponBean2.getCouponType()) {
                couponBean2.setCouponType(4);
            }
            int flag = couponBean1.getCouponType() - couponBean2.getCouponType();
            if (4 == couponBean1.getCouponType()) {
                couponBean1.setCouponType(1);
            }
            if (4 == couponBean2.getCouponType()) {
                couponBean2.setCouponType(1);
            }
            return flag;
        }
    }


}
