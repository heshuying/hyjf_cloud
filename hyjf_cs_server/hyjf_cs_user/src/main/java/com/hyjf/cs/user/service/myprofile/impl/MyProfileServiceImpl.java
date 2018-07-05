package com.hyjf.cs.user.service.myprofile.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.myprofile.MyProfileService;
import com.hyjf.cs.user.vo.MyProfileVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 账户总览
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


    @Value("${aop.interface.accesskey}")
    private String AOP_INTERFACE_ACCESSKEY;

    @Value("${hyjf.api.web.url}")
    private String HYJF_API_WEB_URL;


    @Override
    public String getUserTrueName(Integer userId) {
        String username = "";
        UserInfoVO usersinfo=this.getUserInfo(userId);
        if (StringUtils.isNotEmpty(usersinfo.getTruename())) {
            username = usersinfo.getTruename().substring(0, 1);
            if (usersinfo.getSex() == 2) { //女
                username = username + "女士";
            } else {
                username = username + "先生";
            }
        } else {
            UserVO user=this.getUsersById(userId);
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

    private boolean isChineseChar(String username) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(username);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void buildUserAccountInfo(Integer userId, MyProfileVO.UserAccountInfo userAccountInfo) {
        UserVO users=this.getUsersById(userId);
        Preconditions.checkArgument(users != null, userId + "用户不存在！");
        //是否绑定邮箱
        userAccountInfo.setSetEmail(!Strings.isNullOrEmpty(users.getEmail()));
        if (users.getOpenAccount() != null) {
            //汇付账户？
            userAccountInfo.setChinapnrUser(users.getOpenAccount().intValue() == 1);
        }

        if (users.getBankOpenAccount() != null) {
            //银行账户？
            userAccountInfo.setBankUser(users.getBankOpenAccount().intValue() == 1);
        }

        if (users.getIsSetPassword() != null) {
            //已设置支付密码？
            userAccountInfo.setSetPassword(users.getIsSetPassword().intValue() == 1);
        }

        try {
			if(users.getIsEvaluationFlag()==1 && null != users.getEvaluationExpiredTime()){
				//测评到期日
				Long lCreate = users.getEvaluationExpiredTime().getTime();
				//当前日期
				Long lNow = new Date().getTime();
				if (lCreate <= lNow) {
					//已过期需要重新评测
					userAccountInfo.setEvaluated("2");
				} else {
					//未到一年有效期
					userAccountInfo.setEvaluated("1");
				}
			}else{
				//未评测
				userAccountInfo.setEvaluated("0");
			}
		} catch (Exception e) {
			userAccountInfo.setEvaluated("0");
		}

        if (users.getPaymentAuthStatus() != null) {
            userAccountInfo.setPaymentAuthStatus(users.getPaymentAuthStatus());
        }

        if (users.getUserId()!=null){
            List<BankCardVO> bankCardList= this.amUserClient.getBankOpenAccountById(users);

            if(CollectionUtils.isNotEmpty(bankCardList) && bankCardList.size()>0){
                userAccountInfo.setIsBindCard(true);
            }else{
            	userAccountInfo.setIsBindCard(false);
            }
        }

        userAccountInfo.setUserName(users.getUsername());

        userAccountInfo.setInvitationCode(users.getUserId());

    }

    @Override
    public void buildOutInfo(Integer userId, MyProfileVO myProfileVO) {

        AccountVO account=this.amTradeClient.getAccount(userId);

        Preconditions.checkArgument(account != null, "userId=【" + userId + "】没有账户信息！");

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
        Integer couponValidCount=this.amTradeClient.countCouponValid(userId);

        myProfileVO.setCouponValidCount(couponValidCount == null ? 0 : couponValidCount);

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
        mapParameter.put("limitStart",-1);

        List<CouponUserListCustomizeVO> lstResult=this.amTradeClient.selectCouponUserList(mapParameter);

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
                    Preconditions.checkArgument(chinesePlat != null, "字典表中没有值=" + system + "的平台");
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
                projectString = projectString + "债权,";
            }
            //勾选汇直投  未勾选尊享汇，融通宝
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "债权(尊享,优选除外),";
            }
            //勾选汇直投，融通宝  未勾选尊享汇
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "债权(尊享除外),";
            }
            //勾选汇直投，选尊享汇 未勾选融通宝
            if (projectType.indexOf("1") != -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "债权(优选除外),";
            }
            //勾选尊享汇，融通宝  未勾选直投
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "债权(仅限尊享,优选),";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") != -1 && projectType.indexOf("7") == -1) {
                projectString = projectString + "债权(仅限尊享),";
            }
            //勾选尊享汇  未勾选直投，融通宝
            if (projectType.indexOf("1") == -1 && projectType.indexOf("4") == -1 && projectType.indexOf("7") != -1) {
                projectString = projectString + "债权(仅限优选),";
            }

            if (projectType.indexOf("3") != -1) {
                projectString = projectString + "新手,";
            }
                  /*if (projectType.indexOf("5")!=-1) {
                      projectString = projectString + "汇添金,";
                  }*/
            if (projectType.indexOf("6") != -1) {
                projectString = projectString + "汇计划,";
            }
            projectString = StringUtils.removeEnd(projectString, ",");
        }
        return projectString;
    }

	@Override
	public String getUserCouponsData(String couponStatus, Integer page,
			Integer pageSize, Integer userId, String host) {
		String SOA_INTERFACE_KEY = AOP_INTERFACE_ACCESSKEY;
        String GET_USERCOUPONS = "coupon/getUserCoupons.json";

        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_INTERFACE_KEY + userId + couponStatus + page + pageSize + timestamp + SOA_INTERFACE_KEY));

        Map<String, String> params = new HashMap<String, String>();
        // 时间戳
        params.put("timestamp", timestamp);
        // 签名
        params.put("chkValue", chkValue);
        // 用户id
        params.put("userId", String.valueOf(userId));
        // 商品id
        params.put("couponStatus", couponStatus);
        params.put("page", String.valueOf(page));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("host", host);

        // 请求路径
        String requestUrl = HYJF_API_WEB_URL + GET_USERCOUPONS;
        // 0:成功，1：失败
        String date = HttpClientUtils.post(requestUrl, params);
        return date;
	}

    @Override
    public UserVO getUsers(Integer userId) {
        return this.amUserClient.findUserById(userId);
    }
}
