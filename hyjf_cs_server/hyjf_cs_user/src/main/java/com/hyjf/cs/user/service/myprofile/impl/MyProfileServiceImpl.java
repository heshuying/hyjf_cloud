package com.hyjf.cs.user.service.myprofile.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.myprofile.MyProfileService;
import com.hyjf.cs.user.vo.MyProfileVO;
import com.hyjf.cs.user.vo.UserAccountInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账户总览service实现类
 * jijun 20180715
 */
@Service
public class MyProfileServiceImpl extends BaseUserServiceImpl implements MyProfileService {

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AmMarketClient amMarketClient;

    /**
     * 获取用户真实姓名
     *
     * @param userId
     * @return
     */
    @Override
    public String getUserCallName(Integer userId) {
        String username = "";
        UserInfoVO usersinfo = this.getUserInfo(userId);
        if (StringUtils.isNotEmpty(usersinfo.getTruename())) {
            username = usersinfo.getTruename().substring(0, 1);
            if (usersinfo.getSex() == 2) { //女
                username = username + "女士";
            } else {
                username = username + "先生";
            }
        } else {
            UserVO user = this.getUsersById(userId);
            username = user.getUsername();
            int len = username.length();
            if (isChineseChar(username)) {
                if (len > 4) {
                    username = username.substring(0, 4) + "...";
                }
            } else {
                if (len > 8) {
                    username = username.substring(0, 8) + "...";
                }
            }
        }
        return username;
    }

    /**
     * 中文的正则校验
     *
     * @param username
     * @return
     */
    private boolean isChineseChar(String username) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(username);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 构造userAccountInfo
     *
     * @param userId
     * @param userAccountInfo
     */
    @Override
    public void buildUserAccountInfo(Integer userId, UserAccountInfoVO userAccountInfo) {
        UserVO users = this.getUsersById(userId);
        CheckUtil.check(users != null, MsgEnum.ERR_USER_NOT_EXISTS, userId);
        //Preconditions.checkArgument(users != null, userId + "用户不存在！");
        //是否绑定邮箱
        userAccountInfo.setSetEmail(!Strings.isNullOrEmpty(users.getEmail()));
        if (users.getOpenAccount() != null) {
            //汇付账户？
            userAccountInfo.setChinapnrUser(users.getOpenAccount().intValue() == 1);
        }
        userAccountInfo.setChinapnrUser(false);
        if (users.getBankOpenAccount() != null) {
            //银行账户？
            userAccountInfo.setBankUser(users.getBankOpenAccount().intValue() == 1);
        }

        if (users.getIsSetPassword() != null) {
            //已设置支付密码？
            userAccountInfo.setSetPassword(users.getIsSetPassword().intValue() == 1);
        }

        try {
            if (users.getIsEvaluationFlag() == 1 && null != users.getEvaluationExpiredTime()) {
                //测评到期日
                Long lCreate = users.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                if (lCreate <= lNow) {
                    //已过期需要重新评测
                    userAccountInfo.setEvaluated("2");
                } else {
                    //未到一年有效期
                    userAccountInfo.setEvaluated("1");
                }
            } else {
                //未评测
                userAccountInfo.setEvaluated("0");
            }
        } catch (Exception e) {
            userAccountInfo.setEvaluated("0");
        }
        // 设置服务费授权
        if (users.getPaymentAuthStatus() != null) {
            userAccountInfo.setPaymentAuthStatus(users.getPaymentAuthStatus());
        }

        // 设置服务费授权开关 0未开启  1已开启
        userAccountInfo.setPaymentAuthOn(CommonUtils.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());

        if (users.getUserId() != null) {
            List<BankCardVO> bankCardList = this.amUserClient.getTiedCardForBank(users.getUserId());

            if (CollectionUtils.isNotEmpty(bankCardList) && bankCardList.size() > 0) {
                userAccountInfo.setIsBindCard(true);
            } else {
                userAccountInfo.setIsBindCard(false);
            }
        }

        userAccountInfo.setUserName(users.getUsername());

        userAccountInfo.setInvitationCode(users.getUserId());

    }

    /**
     * 装填我的账户数据
     *
     * @param userId
     * @param myProfileVO
     */
    @Override
    public void buildOutInfo(Integer userId, MyProfileVO myProfileVO) {

        AccountVO account = this.amTradeClient.getAccount(userId);
        CheckUtil.check(account != null, MsgEnum.ERR_BANK_ACCOUNT_NOT_EXIST, userId);
        //Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");

        //资产总额
        myProfileVO.setAccountTotle(account.getBankTotal() == null ? BigDecimal.ZERO : account.getBankTotal());

        //可用余额
        myProfileVO.setBankBalance(account.getBankBalance() == null ? BigDecimal.ZERO : account.getBankBalance());

        //累计收益
        myProfileVO.setInterestTotle(account.getBankInterestSum() == null ? BigDecimal.ZERO : account.getBankInterestSum());

        //待收收益
        if (account.getPlanInterestWait() != null && account.getBankAwaitInterest() != null) {
            myProfileVO.setWaitInterest(account.getBankAwaitInterest().add(account.getPlanInterestWait()));
        } else {
            myProfileVO.setWaitInterest(BigDecimal.ZERO);
        }

        //汇付帐户
        if (myProfileVO.getUserAccountInfo().isChinapnrUser()) {
            myProfileVO.setChinapnrBalance(account.getBalance() == null ? BigDecimal.ZERO : account.getBalance());
        } else {
            myProfileVO.setChinapnrBalance(BigDecimal.ZERO);
        }

        //我的散标
        myProfileVO.setBankAwait(account.getBankAwait() == null ? BigDecimal.ZERO : account.getBankAwait());
        //我的计划
        myProfileVO.setPlanAccountWait(account.getPlanAccountWait() == null ? BigDecimal.ZERO : account.getPlanAccountWait());

        //优惠卷数量
        Integer couponValidCount = this.amTradeClient.countCouponValid(userId);

        myProfileVO.setCouponValidCount(couponValidCount == null ? 0 : couponValidCount);

    }

    /**
     * //活动集合转成 <id,title>格式的map
     *
     * @param activityListVOs
     * @return
     */
    private Map<Integer, String> convertToIdTitleMap(List<ActivityListVO> activityListVOs) {
        Map<Integer, String> result = new HashMap<Integer, String>();
        if (CollectionUtils.isNotEmpty(activityListVOs)) {
            for (ActivityListVO obj : activityListVOs) {
                result.put(obj.getId(), obj.getTitle());
            }
        }
        return result;
    }


    /**
     * 查询用户优惠卷列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<CouponUserListCustomizeVO> queryUserCouponList(Integer userId) {
        List<CouponUserListCustomizeVO> lstCoupon = Lists.newArrayList();

        Map<String, Object> mapParameter = Maps.newHashMap();
        //未使用
        mapParameter.put("usedFlag", "0");
        mapParameter.put("userId", userId);
        mapParameter.put("limitStart", -1);

        List<CouponUserListCustomizeVO> lstResult = this.amTradeClient.selectCouponUserList(mapParameter);
        if (CollectionUtils.isNotEmpty(lstResult)) {
            List<ActivityListVO> actlist = amMarketClient.getActivityList();
            //活动集合转成 <id,title>格式的map
            Map<Integer, String> dicMap = this.convertToIdTitleMap(actlist);
            for (CouponUserListCustomizeVO obj : lstResult) {
                if ("1".equals(obj.getCouponFrom())) {
                    if (StringUtils.isNotEmpty(dicMap.get(obj.getActivityId()))) {
                        obj.setCouponFrom(dicMap.get(obj.getActivityId()));
                    } else {
                        obj.setCouponFrom(obj.getContent());
                    }
                } else if ("2".equals(obj.getCouponFrom())) {
                    if (StringUtils.isNotEmpty(dicMap.get(obj.getActivityId()))) {
                        obj.setCouponFrom(dicMap.get(obj.getActivityId()));
                    } else {
                        obj.setCouponFrom("活动发放");
                    }
                } else if ("3".equals(obj.getCouponFrom())) {
                    obj.setCouponFrom("会员礼包");

                } else {
                    obj.setCouponFrom("");
                }
            }
        }


        //平台
        List<ParamNameVO> clients = this.getParamNameList("CLIENT");
        Map<String, String> mapPlatform = Maps.newHashMap();
        for (ParamNameVO client : clients) {
            mapPlatform.put(client.getNameCd(), client.getName());
        }

        //平台中文转换
        for (CouponUserListCustomizeVO result : lstResult) {
            String systemStr = result.getCouponSystem();
            List<String> lstSystem = Lists.newArrayList(Splitter.on(",").omitEmptyStrings().trimResults().split(systemStr));

            if (lstSystem.contains("-1")) {
                result.setCouponSystem("不限");
            } else {
                List<String> lstTemp = Lists.newArrayList();
                for (String system : lstSystem) {
                    String chinesePlat = mapPlatform.get(system);
                    CheckUtil.check(chinesePlat == null, MsgEnum.ERR_DIC_NO_MATCH, system);
                    //Preconditions.checkArgument(chinesePlat != null, "字典表中没有值=" + system + "的平台");
                    lstTemp.add(chinesePlat);
                }
                String couponSystem = Joiner.on("/").join(lstTemp);
                result.setCouponSystem(couponSystem);
            }

            String projectType = result.getProjectType();
            String projectString = getProjectString(projectType);
            result.setProjectType(projectString);
        }

        lstCoupon.addAll(lstResult);
        return lstCoupon;
    }


    /**
     * 获取数据字典表的下拉列表
     *
     * @return
     */
    private List<ParamNameVO> getParamNameList(String nameClass) {
        return this.amConfigClient.getParamNameList(nameClass);
    }

    /**
     * 项目类型转换
     *
     * @param projectType
     * @return
     */
    private String getProjectString(String projectType) {
        String projectString = "";
        if (projectType.indexOf("-1") != -1) {
            projectString = "不限";
        } else {
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
            // mod by nxl 智投服务：修改汇计划->智投服务 start
            /*if (projectType.indexOf("6") != -1) {
                projectString = projectString + "汇计划,";
            }*/
            if (projectType.indexOf("6")!=-1) {
                projectString = projectString + "智投,";
            }
            // mod by nxl 智投服务：修改汇计划->智投服务 end
            projectString = StringUtils.removeEnd(projectString, ",");
        }
        return projectString;
    }

    /**
     * 获取用户优惠券数据
     *
     * @param couponStatus
     * @param page
     * @param pageSize
     * @param userId
     * @param host
     * @return
     */
    @Override
    public List<CouponUserForAppCustomizeVO> getUserCouponsData(String couponStatus, Integer page,
                                                                Integer pageSize, Integer userId, String host) {

        List<CouponUserForAppCustomizeVO> couponList = this.getMyCoupon(userId, page, pageSize, couponStatus);
        return couponList;
    }

    /**
     * 获取我的优惠券
     *
     * @param userId
     * @param page
     * @param pageSize
     * @param couponStatus
     * @return
     */
    private List<CouponUserForAppCustomizeVO> getMyCoupon(Integer userId, Integer page, Integer pageSize, String couponStatus) {
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId + "");
        requestBean.setUsedFlag(couponStatus);
        requestBean.setLimitStart((page - 1) * pageSize);
        requestBean.setLimitEnd(page * pageSize);
        return amTradeClient.getMyCoupon(requestBean);
    }


}
