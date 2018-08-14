package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.user.VipAuthResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.VipAuthService;
import com.hyjf.am.vo.user.VipAuthVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 16:54
 * @Description: VipAuthController
 * VipAuth查询
 */
@RestController
@RequestMapping("/am-user/vipauth")
public class VipAuthController extends BaseController {

    @Autowired
    private VipAuthService vipAuthService;

    /**
     * 根据用户id查找用户图像
     */
    @RequestMapping("/getvipauthlist/{vipId}")
    public VipAuthResponse getVipAuthList(@PathVariable String vipId){
        if(StringUtils.isNotBlank(vipId)){
            List<VipAuthVO> vipAuthList = vipAuthService.getVipAuthList(vipId);
            VipAuthResponse response = new VipAuthResponse();
            response.setResultList(vipAuthList);
            return response;
        }
        return null;
    }
}
