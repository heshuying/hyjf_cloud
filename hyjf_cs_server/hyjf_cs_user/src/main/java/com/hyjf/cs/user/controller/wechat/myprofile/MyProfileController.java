/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.myprofile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.wechat.annotation.SignValidate;
import com.hyjf.cs.user.service.myprofile.MyProfileService;
import com.hyjf.cs.user.util.RequestUtil;
import com.hyjf.cs.user.vo.MyProfileVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Api(value = "wechat端账户总览",tags = "wechat端账户总览")
@Controller
@RequestMapping("/hyjf-wechat/myprofile")
public class MyProfileController extends BaseUserController {

    @Autowired
    private MyProfileService myProfileService;
    @Autowired
    private RequestUtil requestUtil;
    @Autowired
    private SystemConfig systemConfig;


    @SignValidate
    @RequestMapping("/profile")
    @ResponseBody
    public WeChatResult myProfile(HttpServletRequest request) {
        WeChatResult result = new WeChatResult();
        MyProfileVO myProfileVO = new MyProfileVO();
        Integer userId = requestUtil.getRequestUserId(request);
        if(userId==null){
            result.buildErrorResponse(MsgEnum.ERR_USER_NOT_LOGIN);
            return result;
        }

        String trueUserName = myProfileService.getUserTrueName(userId);

        MyProfileVO.UserAccountInfo userAccountInfo = myProfileVO.new UserAccountInfo();

        userAccountInfo.setTrueUserName(trueUserName);

        myProfileService.buildUserAccountInfo(userId, userAccountInfo);

        myProfileVO.setUserAccountInfo(userAccountInfo);

        myProfileService.buildOutInfo(userId, myProfileVO);

        result.setData(myProfileVO);

        this.getIconUrl(userId, myProfileVO);

        return result;
    }


    private void getIconUrl(Integer userId, MyProfileVO myProfileVO) {
        UserVO user = myProfileService.getUsers(Integer.valueOf(userId));
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


    @SignValidate
    @GetMapping("/couponlist")
    @ResponseBody
    public WeChatResult getCouponList(HttpServletRequest request) {
        WeChatResult resultBean = new WeChatResult();
        Integer userId = requestUtil.getRequestUserId(request);
        String resultStr = myProfileService.getUserCouponsData("0", 1, 100, userId, "");
        JSONObject resultJson = JSONObject.parseObject(resultStr);
        JSONArray data = resultJson.getJSONArray("data");
        List<CouponUserForAppCustomizeVO> configs = JSON.parseArray(data.toJSONString(), CouponUserForAppCustomizeVO.class);
        List<CouponUserListCustomizeVO> lstCoupon =createCouponUserListCustomize(configs);
        resultBean.setData(lstCoupon);
        return resultBean;
    }


    private List<CouponUserListCustomizeVO> createCouponUserListCustomize(
            List<CouponUserForAppCustomizeVO> configs) {
        List<CouponUserListCustomizeVO> list=new ArrayList<CouponUserListCustomizeVO>();
        DecimalFormat df = new DecimalFormat(",###");
        df.setRoundingMode(RoundingMode.FLOOR);
        for (CouponUserForAppCustomizeVO config : configs) {
            CouponUserListCustomizeVO customize=new CouponUserListCustomizeVO();
            customize.setId(config.getId());
            String[] time=config.getTime().split("-");
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
