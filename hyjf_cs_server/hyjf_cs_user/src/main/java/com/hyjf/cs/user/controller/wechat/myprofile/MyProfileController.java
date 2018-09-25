/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.myprofile;

import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.myprofile.MyProfileService;
import com.hyjf.cs.user.vo.MyProfileVO;
import com.hyjf.cs.user.vo.UserAccountInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信端账户总览
 * @author jun
 * @version MyProfileController, v0.1 2018/7/3 15:52
 */
@Api(value = "weChat端-账户总览",tags = "weChat端-账户总览")
@RestController
@RequestMapping("/hyjf-wechat/myprofile")
public class MyProfileController extends BaseUserController {

    @Autowired
    private MyProfileService myProfileService;
    @Autowired
    private SystemConfig systemConfig;

    @ApiOperation(value = "查询用户账户信息", notes = "查询用户账户信息")
    @GetMapping("/profile.do")
    public WeChatResult myProfile(@RequestHeader(value = "userId") Integer userId) {
        WeChatResult result = new WeChatResult();
        MyProfileVO myProfileVO = new MyProfileVO();
        //用户真实姓名
        String trueUserName = myProfileService.getUserCallName(userId);

        UserAccountInfoVO userAccountInfo = new UserAccountInfoVO();

        userAccountInfo.setTrueUserName(trueUserName);
        //设置用户账户信息
        myProfileService.buildUserAccountInfo(userId, userAccountInfo);

        myProfileVO.setUserAccountInfo(userAccountInfo);
        //设置用户账户信息
        myProfileService.buildOutInfo(userId, myProfileVO);

        result.setObject(myProfileVO);

        this.getIconUrl(userId, myProfileVO);

        return result;
    }


    private void getIconUrl(Integer userId, MyProfileVO myProfileVO) {
        UserVO user = myProfileService.getUsersById(userId);
        String imghost = UploadFileUtils.getDoPath(systemConfig.getHeadUrl());
        String imagePath="";
        if (StringUtils.isNotEmpty(user.getIconUrl())) {
            // 实际物理路径前缀
            String fileUploadRealPath = UploadFileUtils.getDoPath(systemConfig.getUploadHeadPath());
            imagePath = imghost + fileUploadRealPath + user.getIconUrl();
        }
        myProfileVO.getUserAccountInfo().setIconUrl(imagePath);
        myProfileVO.getUserAccountInfo().setQrcodeUrl(systemConfig.getWechatQrcodeUrl().replace("{userId}", String.valueOf(userId)));
    }

    /**
     * 优惠券列表
     * @param request
     * @return
     */
    @ApiOperation(value = "查询优惠券列表", notes = "查询优惠券列表")
    @GetMapping("/couponlist")
    public WeChatResult getCouponList(HttpServletRequest request,@RequestHeader(value = "userId") Integer userId) {
        WeChatResult resultBean = new WeChatResult();

        if (userId==null){
            resultBean.setStatus(BaseResult.FAIL);
            resultBean.setStatusDesc("用户未登录!");
            return resultBean;
        }
        List<CouponUserForAppCustomizeVO> list = myProfileService.getUserCouponsData("0", 1, 100, userId, "");
        if (CollectionUtils.isEmpty(list)){
            resultBean.setStatus(BaseResult.SUCCESS);
            resultBean.setStatusDesc(BaseResult.SUCCESS_DESC);
            resultBean.setObject(list);
            return resultBean;
        }

        List<CouponUserListCustomizeVO> lstCoupon =createCouponUserListCustomize(list);
        resultBean.setObject(lstCoupon);
        return resultBean;
    }

    /**
     * 创建自定义的优惠券列表
     * @param configs
     * @return
     */
    private List<CouponUserListCustomizeVO> createCouponUserListCustomize(
            List<CouponUserForAppCustomizeVO> configs) {
        List<CouponUserListCustomizeVO> list=new ArrayList<CouponUserListCustomizeVO>();
        DecimalFormat df = new DecimalFormat(",###");
        df.setRoundingMode(RoundingMode.FLOOR);

        for (CouponUserForAppCustomizeVO config : configs) {
            CouponUserListCustomizeVO customize=new CouponUserListCustomizeVO();
            customize.setId(config.getId());
            String[] time=config.getEndTime().split("-");
            customize.setAddTime(time[0]);
            customize.setEndTime(time[1]);
            customize.setContent("1");
            customize.setCouponName(config.getCouponName());
            customize.setCouponProfitTime("");

            customize.setCouponSystem(config.getOperationPlatform());
            customize.setCouponType(config.getCouponType());
            customize.setCouponUserCode("");
            customize.setProjectExpirationType(config.getInvestTime());
            customize.setProjectType(config.getProjectType());
            customize.setRecoverTime("");
            customize.setRecoverStatus("");
            customize.setTenderQuota(config.getInvestQuota());
            // 如果有，就去掉  要不然下面报类型转换异常
            if(config.getCouponQuota().indexOf(",")>0){
                config.setCouponQuota(config.getCouponQuota().replaceAll(",", ""));
            }
            if(config.getCouponQuota().indexOf("元")!=-1||config.getCouponQuota().indexOf("%")!=-1){
                String couponQuota=config.getCouponQuota().substring(0, config.getCouponQuota().length()-1);
                config.setCouponQuota(couponQuota);
            }
            if(!"加息券".equals(customize.getCouponType())){
                customize.setCouponQuota(df.format(new BigDecimal(config.getCouponQuota())));
            }else{
                customize.setCouponQuota(config.getCouponQuota());
            }
            list.add(customize);
        }
        return list;
    }


}
