package com.hyjf.am.trade.service.front.coupon.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowMapper;
import com.hyjf.am.trade.dao.mapper.customize.MyCouponListCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowInfoService;
import com.hyjf.am.trade.service.front.borrow.BorrowProjectTypeService;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.front.coupon.MyCouponListService;
import com.hyjf.am.trade.service.front.hjh.HjhPlanService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.coupon.CouponBeanVo;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDateUtils;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 我的优惠券列表
 *
 * @author hesy
 * @version MyCouponListServiceImpl, v0.1 2018/6/22 19:15
 */
@Service
public class MyCouponListServiceImpl extends BaseServiceImpl implements MyCouponListService {
    Logger logger = LoggerFactory.getLogger(MyCouponListServiceImpl.class);
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

    @Autowired
    private HjhPlanService hjhPlanService;

    /**
     * 检索我的优惠券列表
     *
     * @auther: hesy
     * @date: 2018/6/22
     */
    @Override
    public List<MyCouponListCustomizeVO> selectUserCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        if (limitStart != null) {
            param.put("limitStart", limitStart);
        } else {
            param.put("limitStart", -1);
        }
        if (limitEnd != null) {
            param.put("limitEnd", limitEnd);
        } else {
            param.put("limitEnd", -1);
        }

        return myCouponListCustomizeMapper.selectMyCouponList(param);
    }

    /**
     * 统计总记录数
     *
     * @param userId
     * @param usedFlag
     * @return
     */
    @Override
    public Integer countUserCouponList(String userId, String usedFlag) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        Integer result = myCouponListCustomizeMapper.countMyCouponList(param);
        if (result == null) {
            result = 0;
        }
        return result;
    }

    /**
     * 查询最优优惠券
     *
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
        Borrow borrow = borrowService.getBorrowByNid(borrowNid);
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
            if(dealProjectExpiration(borrow.getBorrowStyle(),type,bestCoupon.getProjectExpirationLength(),bestCoupon.getProjectExpirationLengthMin(),bestCoupon.getProjectExpirationLengthMax(),borrow.getBorrowPeriod())){
                continue;
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
            boolean ifprojectType = dealBorrowClass(bestCoupon.getProjectType(),borrowProjectType.getBorrowClass());
            if (ifprojectType) {
                continue;
            }
            //是否与本金公用
            boolean addFlg = false;
            if (bestCoupon.getAddFlag()!=null&&bestCoupon.getAddFlag() == 1 && (!"0".equals(money) && !"".equals(money))) {
                addFlg = true;
            }
            if (addFlg) {
                continue;
            }
            // 验证使用平台
            boolean ifcouponSystem = dealCheckCouponSystem(bestCoupon.getCouponSystem(),platform);
            if(!ifcouponSystem){
                return bestCoupon;
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
        Borrow borrow = borrowService.getBorrowByNid(borrowNid);
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
            if(dealProjectExpiration(borrow.getBorrowStyle(),type,bestCoupon.getProjectExpirationLength(),bestCoupon.getProjectExpirationLengthMin(),bestCoupon.getProjectExpirationLengthMax(),borrow.getBorrowPeriod())){
                continue;
            }
            // 验证可使用项目
            String projectType = bestCoupon.getProjectType();
            // String[] projectTypeArr=projectType.split(",");
            boolean ifprojectType = dealBorrowClass(bestCoupon.getProjectType(),borrowProjectType.getBorrowClass());
            if (ifprojectType) {
                continue;
            }

            /**************逻辑修改 pcc start***************/
            //是否与本金公用
            boolean addFlg = false;
            if (bestCoupon.getAddFlag()!=null&&bestCoupon.getAddFlag() == 1 && !"0".equals(money)) {
                addFlg = true;
            }
            if (addFlg) {
                continue;
            }
            /**************逻辑修改 pcc end***************/
            // 验证使用平台
            boolean ifcouponSystem = dealCheckCouponSystem(bestCoupon.getCouponSystem(),platform);
            if (ifcouponSystem) {
                continue;
            } else {
                //CouponBean couponBean = createCouponBean(userCouponConfigCustomize, new CouponBean(), null);
                // availableCouponList.add(couponBean);
                count++;
            }
        }
        // System.out.println("~~~~~~~~~~~~~~~~统计结束~~~~~~~~~~~~~~~~~~"+availableCouponList.size());
        return count;
    }

    /**
     * 查询汇计划最优优惠券
     *
     * @param requestBean
     * @return
     */
    @Override
    public BestCouponListVO selectHJHBestCoupon(MyCouponListRequest requestBean) {
        {
            Map<String, Object> map = new HashMap<String, Object>();
            String userId = requestBean.getUserId();
            String borrowNid = requestBean.getBorrowNid();
            String money = requestBean.getMoney();
            String platform = requestBean.getPlatform();
            map.put("userId", userId);
            // 查询项目信息
            HjhPlan plan = hjhPlanService.getHjhPlanByNid(borrowNid);
            String style = plan.getBorrowStyle();
            String couponConfig = plan.getCouponConfig();
            List<BestCouponListVO> couponConfigs = myCouponListCustomizeMapper.getCouponConfigList(map);
            // 排序
            Collections.sort(couponConfigs, new ComparatorCouponBean());
            for (BestCouponListVO bestCoupon : couponConfigs) {
                // 验证项目加息券或体验金是否可用
                if (couponConfig.indexOf("3") == -1) {
                    if (bestCoupon.getCouponType() == 3) {
                        continue;
                    }
                }
                if (couponConfig.indexOf("2") == -1) {
                    if (bestCoupon.getCouponType() == 2) {
                        continue;
                    }
                }
                if (couponConfig.indexOf("1") == -1) {
                    if (bestCoupon.getCouponType() == 1) {
                        continue;
                    }
                }
                // 验证项目期限
                Integer type = bestCoupon.getProjectExpirationType();
                if(dealProjectExpiration(plan.getBorrowStyle(),type,bestCoupon.getProjectExpirationLength(),bestCoupon.getProjectExpirationLengthMin(),bestCoupon.getProjectExpirationLengthMax(),plan.getLockPeriod())){
                    continue;
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
                if (projectType.indexOf("6") != -1) {
                    ifprojectType = false;
                }
                if (ifprojectType) {
                    continue;
                }
                //是否与本金公用
                boolean addFlg = false;
                if (bestCoupon.getAddFlag()!=null && bestCoupon.getAddFlag() == 1 && !"0".equals(money)) {
                    addFlg = true;
                }
                if (addFlg) {
                    continue;
                }
                // 验证使用平台
                boolean ifcouponSystem = dealCheckCouponSystem(bestCoupon.getCouponSystem(),platform);
                if(!ifcouponSystem){
                    return bestCoupon;
                }
            }
            return null;
        }
    }

    /**
     * 查询hjh可用优惠券数量
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer getHJHUserCouponAvailableCount(MyCouponListRequest requestBean) {
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = requestBean.getUserId();
        String borrowNid = requestBean.getBorrowNid();
        String money = requestBean.getMoney();
        if(money==null||"".equals(money)){
            money = "0";
        }
        String platform = requestBean.getPlatform();
        map.put("userId", userId);
        // 查询项目信息
        HjhPlan plan = hjhPlanService.getHjhPlanByNid(borrowNid);
        String style = plan.getBorrowStyle();
        String couponConfig = plan.getCouponConfig();
        List<BestCouponListVO> couponConfigs = myCouponListCustomizeMapper.getCouponConfigList(map);
        Integer allCount = 0;
        for (BestCouponListVO bestCoupon : couponConfigs) {
            // 验证项目加息券或体验金是否可用
            if (couponConfig.indexOf("3") == -1) {
                if (bestCoupon.getCouponType() == 3) {
                    continue;
                }
            }
            if (couponConfig.indexOf("2") == -1) {
                if (bestCoupon.getCouponType() == 2) {
                    continue;
                }
            }
            if (couponConfig.indexOf("1") == -1) {
                if (bestCoupon.getCouponType() == 1) {
                    continue;
                }
            }
            // 验证项目期限
            Integer type = bestCoupon.getProjectExpirationType();
            if(dealProjectExpiration(plan.getBorrowStyle(),type,bestCoupon.getProjectExpirationLength(),bestCoupon.getProjectExpirationLengthMin(),bestCoupon.getProjectExpirationLengthMax(),plan.getLockPeriod())){
                continue;
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
            if (projectType.indexOf("6") != -1) {
                ifprojectType = false;
            }
            if (ifprojectType) {
                continue;
            }
            //是否与本金公用
            boolean addFlg = false;
            if (bestCoupon.getAddFlag() !=null && bestCoupon.getAddFlag().equals(1) && !"0".equals(money)) {
                addFlg = true;
            }
            if (addFlg) {
                continue;
            }
            // 验证使用平台
            boolean ifcouponSystem = dealCheckCouponSystem(bestCoupon.getCouponSystem(),platform);
            if(!ifcouponSystem){
                allCount++;
                continue;
            }
        }
        return allCount;
    }

    @Override
    public List<CouponUserForAppCustomizeVO> getMyCouponByPage(MyCouponListRequest requestBean) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", requestBean.getUsedFlag());
        param.put("userId", requestBean.getUserId());

        if (null != requestBean.getLimitStart()) {
            param.put("limitStart", requestBean.getLimitStart());
        } else {
            param.put("limitStart", -1);
        }
        if (null != requestBean.getLimitEnd()) {
            param.put("limitEnd", requestBean.getLimitEnd());
        } else {
            param.put("limitEnd", -1);
        }
        List<MyCouponListCustomizeVO> list = myCouponListCustomizeMapper.selectMyCouponList(param);
        List<CouponUserForAppCustomizeVO> couponList = new ArrayList<>();
        for (MyCouponListCustomizeVO myCouponListCustomizeVO : list) {
            CouponUserForAppCustomizeVO couponUserForAppCustomizeVO = new CouponUserForAppCustomizeVO();

            //根据项目类型处理转换项目
            String projectString = this.dealProjectType(myCouponListCustomizeVO.getProjectType());
            couponUserForAppCustomizeVO.setProjectType(projectString);

            //处理选中的操作平台
            String clientString = dealOperation(myCouponListCustomizeVO.getCouponSystem());
            couponUserForAppCustomizeVO.setOperationPlatform(clientString);
            //处理优惠券面额
            couponUserForAppCustomizeVO.setCouponQuota(myCouponListCustomizeVO.getCouponQuota() + dealCouponQuota(myCouponListCustomizeVO.getCouponType()));
            couponUserForAppCustomizeVO.setId(myCouponListCustomizeVO.getId());
            couponUserForAppCustomizeVO.setCouponType(myCouponListCustomizeVO.getCouponTypeName());
            couponUserForAppCustomizeVO.setCouponTypeOrigin(myCouponListCustomizeVO.getCouponType());
            couponUserForAppCustomizeVO.setCouponQuotaOrigin(myCouponListCustomizeVO.getCouponQuota());
            couponUserForAppCustomizeVO.setEndTime(myCouponListCustomizeVO.getAddTime() + "-" + myCouponListCustomizeVO.getEndTime());
            couponUserForAppCustomizeVO.setTime(myCouponListCustomizeVO.getAddTime() + "-" + myCouponListCustomizeVO.getEndTime());
            couponUserForAppCustomizeVO.setInvestQuota(myCouponListCustomizeVO.getTenderQuota());
            couponUserForAppCustomizeVO.setInvestTime(myCouponListCustomizeVO.getProjectExpirationType());
            couponUserForAppCustomizeVO.setCouponName(myCouponListCustomizeVO.getCouponName());
            couponList.add(couponUserForAppCustomizeVO);
        }
        return couponList;
    }

    @Override
    public int updateCouponReadFlag(Integer userId, Integer readFlag){
        CouponUser couponUser = new CouponUser();
        couponUser.setReadFlag(readFlag);

        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId).andUsedFlagEqualTo(CustomConstants.USER_COUPON_STATUS_UNUSED).andDelFlagEqualTo(Integer.parseInt(CustomConstants.FLAG_NORMAL));

        return couponUserMapper.updateByExampleSelective(couponUser, example);
    }

    @Override
    public JSONObject getBorrowCoupon(MyCouponListRequest requestBean) {
        JSONObject jsonObject = new JSONObject();
        String userId = requestBean.getUserId();
        String borrowNid = requestBean.getBorrowNid();
        String money = requestBean.getMoney();
        String platform = requestBean.getPlatform();
        List<CouponBeanVo> availableCouponList=new ArrayList<CouponBeanVo>();
        List<CouponBeanVo> notAvailableCouponList=new ArrayList<CouponBeanVo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("usedFlag", "0");
        // 查询项目信息
        Borrow borrow = borrowService.getBorrowByNid(borrowNid);
        BorrowInfo borrowInfo = borrowInfoService.getBorrowInfo(borrowNid);
        BorrowProjectType borrowProjectType = borrowProjectTypeService.getProjectTypeByBorrowNid(borrowNid);
        //principal: 等额本金,  month:等额本息, endmonth:先息后本,endday: 按天计息, end:按月计息
        String style = borrow != null?borrow.getBorrowStyle():null;
        // 加息券是否启用 0禁用 1启用
        Integer couponFlg = borrowInfo.getBorrowInterestCoupon();
        // 体验金是否启用 0禁用 1启用
        Integer moneyFlg = borrowInfo.getBorrowTasteMoney();
        List<MyCouponListCustomizeVO> list = myCouponListCustomizeMapper.selectMyCouponList(map);
        for (MyCouponListCustomizeVO bestCoupon : list) {
            // 验证项目加息券或体验金是否可用
            if (couponFlg != null && couponFlg == 0) {
                if ("2".equals(bestCoupon.getCouponType())) {
                    CouponBeanVo couponBean=createCouponBean(bestCoupon,null,"该项目加息券不能使用",platform);
                    notAvailableCouponList.add(couponBean);
                    continue;
                }
            }
            if (moneyFlg != null && moneyFlg == 0) {
                if ("1".equals(bestCoupon.getCouponType())) {
                    CouponBeanVo couponBean=createCouponBean(bestCoupon,null,"该项目优惠券不能使用",platform);
                    notAvailableCouponList.add(couponBean);
                    continue;
                }
            }
            // 验证项目期限、
            Integer type = bestCoupon.getExpirationType();
            if(dealProjectExpiration(style,type,bestCoupon.getProjectExpirationLength(),bestCoupon.getProjectExpirationLengthMin(),bestCoupon.getProjectExpirationLengthMax(),borrow.getBorrowPeriod())){
                continue;
            }
            // 验证项目金额
            Integer tenderQuotaType = bestCoupon.getTenderQuotaType();
            if (tenderQuotaType == 1) {
                if (bestCoupon.getTenderQuotaMin() > new Double(money) || bestCoupon.getTenderQuotaMax() < new Double(money)) {
                    CouponBeanVo couponBean=createCouponBean(bestCoupon,null,
                            bestCoupon.getTenderQuota(),platform);
                    notAvailableCouponList.add(couponBean);
                    continue;
                }
            } else if (tenderQuotaType == 2) {
                if (bestCoupon.getTenderQuotaAmount() != null && (new Double(bestCoupon.getTenderQuotaAmount()) > new Double(money))) {
                    CouponBeanVo couponBean=createCouponBean(bestCoupon,null,bestCoupon.getTenderQuota(),platform);
                    notAvailableCouponList.add(couponBean);
                    continue;
                }
            }

            //是否与本金公用
            boolean addFlg = false;
            if (null != bestCoupon.getAddFlag() && bestCoupon.getAddFlag() == 1 && !"0".equals(money)) {
                addFlg = true;
            }
            if (addFlg) {
                /*CouponBean couponBean =
                        createCouponBean(userCouponConfigCustomize, "不能与本金共用");*/
//                notAvailableCouponList.add(couponBean);
                continue;
            }
            /**************逻辑修改 pcc end***************/

            // 验证优惠券适用的项目 新逻辑 pcc20160715
            boolean ifprojectType = dealBorrowClass(bestCoupon.getProjectType(),borrowProjectType.getBorrowClass());
            if (ifprojectType) {
                continue;
            }
            // 验证使用平台
            boolean ifcouponSystem = dealCheckCouponSystem(bestCoupon.getCouponSystem(),platform);
            if(!ifcouponSystem){
                CouponBeanVo couponBean=createCouponBean(bestCoupon,null,"",platform);
                availableCouponList.add(couponBean);
            }
        }

        jsonObject.put("availableCouponList", availableCouponList);
        jsonObject.put("notAvailableCouponList", notAvailableCouponList);
        jsonObject.put("availableCouponListCount", availableCouponList.size());
        jsonObject.put("notAvailableCouponListCount", notAvailableCouponList.size());
        return jsonObject;
    }

    @Override
    public JSONObject getPlanCouponoupon(MyCouponListRequest requestBean) {
        JSONObject jsonObject = new JSONObject();
        String userId = requestBean.getUserId();
        String planNid = requestBean.getBorrowNid();
        String money = requestBean.getMoney();
        String platform = requestBean.getPlatform();
        List<CouponBeanVo> availableCouponList=new ArrayList<CouponBeanVo>();
        List<CouponBeanVo> notAvailableCouponList=new ArrayList<CouponBeanVo>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("usedFlag", "0");
        // 查询项目信息(汇计划)
        HjhPlan hjhPlan = hjhPlanService.getHjhPlanByNid(planNid);
        //是否可以用劵：0 不可用 1 体验金 2 加息券 3 代金券
        String couponConfig = hjhPlan.getCouponConfig();
        List<MyCouponListCustomizeVO> list = myCouponListCustomizeMapper.selectMyCouponList(map);
        for (MyCouponListCustomizeVO userCouponConfigCustomize : list) {
            CouponBeanVo couponBeanVo = new CouponBeanVo();
            couponBeanVo.setCouponSystem(userCouponConfigCustomize.getCouponSystem());
            couponBeanVo.setProjectType(dealProjectType(userCouponConfigCustomize.getProjectType()));
            couponBeanVo.setProjectExpiration(userCouponConfigCustomize.getProjectExpirationType());
            couponBeanVo.setInvestTime(userCouponConfigCustomize.getProjectExpirationType());
            // 验证优惠券适用的项目 新逻辑 pcc20160715
            String projectType = userCouponConfigCustomize.getProjectType();
            boolean ifprojectType = true;

            if (projectType.indexOf("6") != -1) {
                ifprojectType = false;
            }
            if (ifprojectType) {
                continue;
            }/**************逻辑修改 pcc start***************/

            // 验证项目加息券或体验金是否可用
            if (couponConfig.indexOf("3") == -1) {
                if ("3".equals(userCouponConfigCustomize.getCouponType())) {
                    continue;
                }
            }
            if (couponConfig.indexOf("2") == -1) {
                if ("2".equals(userCouponConfigCustomize.getCouponType())) {
                    continue;
                }
            }
            if (couponConfig.indexOf("1") == -1) {
                if ("1".equals(userCouponConfigCustomize.getCouponType())) {
                    continue;
                }
            }

            // 验证项目期限、
            Integer type = userCouponConfigCustomize.getExpirationType();
            if(dealProjectExpiration(hjhPlan.getBorrowStyle(),type,userCouponConfigCustomize.getProjectExpirationLength(),userCouponConfigCustomize.getProjectExpirationLengthMin(),userCouponConfigCustomize.getProjectExpirationLengthMax(),hjhPlan.getLockPeriod())){
                continue;
            }
            //是否与本金公用
            boolean addFlg = false;
            if(userCouponConfigCustomize.getAddFlag()!=null&&userCouponConfigCustomize.getAddFlag()==1&&!"0".equals(money)){
                addFlg = true;
            }
            if(addFlg){
                continue;
            }
            /**************逻辑修改 pcc end***************/
            // 验证使用平台
            boolean ifcouponSystem = dealCheckCouponSystem(userCouponConfigCustomize.getCouponSystem(),platform);

            if (!ifcouponSystem) {
                // 验证项目金额
                Integer tenderQuotaType = userCouponConfigCustomize.getTenderQuotaType();
                if (1 == tenderQuotaType) {
                    if (userCouponConfigCustomize.getTenderQuotaMin() > new Double(money)
                            || userCouponConfigCustomize.getTenderQuotaMax() < new Double(money)) {

                        couponBeanVo = createCouponBean(userCouponConfigCustomize,couponBeanVo,userCouponConfigCustomize.getTenderQuota(),platform);
                        notAvailableCouponList.add(couponBeanVo);
                        continue;
                    }
                } else if (2 == tenderQuotaType) {
                    if (!"不限".equals(userCouponConfigCustomize.getTenderQuota()) && null != userCouponConfigCustomize.getTenderQuotaAmount()) {
                        if(new Double(userCouponConfigCustomize.getTenderQuotaAmount()) > new Double(money)){
                            couponBeanVo = createCouponBean(userCouponConfigCustomize,couponBeanVo,userCouponConfigCustomize.getTenderQuota(),platform);
                            notAvailableCouponList.add(couponBeanVo);
                            continue;
                        }
                    }
                }
                couponBeanVo = createCouponBean(userCouponConfigCustomize,couponBeanVo, "",platform);
                availableCouponList.add(couponBeanVo);
            }
        }
        // 排序
        jsonObject.put("availableCouponList", availableCouponList);
        jsonObject.put("notAvailableCouponList", notAvailableCouponList);
        jsonObject.put("availableCouponListCount", availableCouponList.size());
        jsonObject.put("notAvailableCouponListCount", notAvailableCouponList.size());

        return jsonObject;
    }

    @Override
    public List<MyCouponListCustomizeVO> wechatCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("usedFlag", usedFlag);
        param.put("userId", userId);

        if (limitStart != null) {
            param.put("limitStart", limitStart);
        } else {
            param.put("limitStart", -1);
        }
        if (limitEnd != null) {
            param.put("limitEnd", limitEnd);
        } else {
            param.put("limitEnd", -1);
        }
        List<MyCouponListCustomizeVO> list = myCouponListCustomizeMapper.selectMyCouponList(param);
        List<MyCouponListCustomizeVO> wechatList = new ArrayList<>();
        for (MyCouponListCustomizeVO myCouponCustomizeVO:list) {
            String couponSystem = myCouponCustomizeVO.getCouponSystem();
            //处理使用平台
            if (StringUtils.isNotBlank(couponSystem)){
                couponSystem = dealOperation(couponSystem);
                myCouponCustomizeVO.setCouponSystem(couponSystem);
            }
            //处理项目类型
            String projectType = myCouponCustomizeVO.getProjectType();
            if (StringUtils.isNotBlank(projectType)){
                projectType = dealProjectType(projectType);
                myCouponCustomizeVO.setProjectType(projectType);
            }
            wechatList.add(myCouponCustomizeVO);
        }

        return wechatList;
    }

    /**
     * 处理校验优惠券平台
     * @param couponSystem 优惠券适用平台
     * @param platform 出借平台
     * @return
     */
    public boolean dealCheckCouponSystem(String couponSystem,String platform){
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
    public boolean dealProjectExpiration(String style,Integer type,Integer projectExpirationLength,Integer projectExpirationLengthMin,Integer projectExpirationLengthMax,Integer borrowPeriod){
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
    public boolean dealBorrowClass(String projectType,String borrowClass){
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
     * 处理优惠券对象
     * @param userCouponConfigCustomize 查询到的优惠券
     * @param remarks 备注
     * @return CouponBeanVo
     */
    private CouponBeanVo createCouponBean(MyCouponListCustomizeVO userCouponConfigCustomize,CouponBeanVo couponBean, String remarks,String platform) {
        if(null == couponBean){
            couponBean=new CouponBeanVo();
        }
        couponBean.setUserCouponId(userCouponConfigCustomize.getId()+"");
        couponBean.setRemarks(remarks);
        try{
            couponBean.setCouponTypeStr(Integer.parseInt(userCouponConfigCustomize.getCouponType()));
            couponBean.setCouponName(userCouponConfigCustomize.getCouponName());
            couponBean.setEndTime(userCouponConfigCustomize.getAddTime() + "-" + userCouponConfigCustomize.getEndTime());
            couponBean.setCouponAddTime(userCouponConfigCustomize.getAddTime());
            couponBean.setCouponEndTime(userCouponConfigCustomize.getEndTime());
        }catch (Exception e){
            logger.error("处理优惠券类型失败！",e);
        }

        //处理优惠券面额
        couponBean.setCouponQuota(userCouponConfigCustomize.getCouponQuota() + dealCouponQuota(userCouponConfigCustomize.getCouponType()));
        couponBean.setCouponQuotaStr(userCouponConfigCustomize.getCouponQuota());
        couponBean.setCouponType(userCouponConfigCustomize.getCouponTypeName());

        couponBean.setInvestTime(userCouponConfigCustomize.getProjectExpirationType());
        couponBean.setProjectExpiration(userCouponConfigCustomize.getProjectExpirationType());
        if(platform.equals(CacheUtil.getParamName("CLIENT","0"))){
            //处理优惠券适用项目
            String projectString = this.dealProjectTypeNew(userCouponConfigCustomize.getProjectType());
            couponBean.setProjectType(projectString);
            //处理优惠券使用平台
            String clientString = dealOperationNew(userCouponConfigCustomize.getCouponSystem());
            couponBean.setOperationPlatform(clientString);
            long day=DateUtils.differentDaysByString(userCouponConfigCustomize.getEndTimeStamp(), GetDateUtils.getNowTime()+"");
            if(day>3){
                couponBean.setTime(userCouponConfigCustomize.getAddTime() + "～" + userCouponConfigCustomize.getEndTime());
            }else{
                couponBean.setTime("还有"+(day==0?1:day)+"天过期");
            }

        }else{
            //处理优惠券适用项目
            String projectString = this.dealProjectType(userCouponConfigCustomize.getProjectType());;
            couponBean.setProjectType(projectString);
            //处理优惠券使用平台
            String clientString = dealOperation(userCouponConfigCustomize.getCouponSystem());;
            couponBean.setOperationPlatform(clientString);
            couponBean.setTime(userCouponConfigCustomize.getAddTime() + "～" + userCouponConfigCustomize.getEndTime());
        }
        couponBean.setInvestQuota(userCouponConfigCustomize.getTenderQuota());
        couponBean.setTenderQuota(userCouponConfigCustomize.getTenderQuota());
        couponBean.setCouponUserCode(userCouponConfigCustomize.getCouponUserCode());
        return couponBean;
    }

    /**
     * 处理优惠券面额
     *
     * @param couponType 优惠券类型
     * @return String
     */
    public String dealCouponQuota(String couponType) {
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
    public String dealOperation(String operationPlatform) {
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
     * 处理选中的操作平台
     *
     * @param operationPlatform
     * @return
     */
    public String dealOperationNew(String operationPlatform) {
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
    public String dealProjectType(String projectType) {
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
    public String dealProjectTypeNew(String projectType) {
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
            projectString=projectString.substring(1, projectString.length() - 1);
        }

        return projectString;
    }

    /**
     * 比较器
     *
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
