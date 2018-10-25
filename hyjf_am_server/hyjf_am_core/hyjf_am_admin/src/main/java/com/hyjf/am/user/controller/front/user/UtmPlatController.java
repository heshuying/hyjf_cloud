package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.vo.user.UtmPlatVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/10/23  16:03
 */
@RestController
@RequestMapping("/am-user/utmplat")
public class UtmPlatController {

    @Autowired
    private UtmPlatService utmPlatService;

    /**
     * 根据userId检索渠道是否存在
     * @return
     */
    @RequestMapping("/getUtmPlat/{sourceId}")
    public UtmPlatResponse getUtmPlat(@PathVariable Integer sourceId) {
        UtmPlat utmPlat = utmPlatService.getUtmPlat(sourceId);
        UtmPlatResponse response = new UtmPlatResponse();
        if (null != utmPlat) {
            UtmPlatVO utmPlatVO = new UtmPlatVO();
            BeanUtils.copyProperties(utmPlat, utmPlatVO);
            response.setResult(utmPlatVO);
        }
        return response;
    }

    /**
     *  查询所有非主单的用户
     *
     * @return
     */
    @RequestMapping("/getUsersInfoList")
    public IntegerResponse getUsersInfoList() {
        List<Integer> utmPlat = utmPlatService.getUsersInfoList();
        IntegerResponse response = new IntegerResponse();
        if (null != utmPlat) {
            response.setResult(utmPlat);
        }
        return response;
    }

    /**
     *  app渠道Ios、pc、wechat、android开户数
     *
     * @return
     */
    @RequestMapping("/getUsersList/{sourceIdSrch}")
    public IntegerResponse getUsersList(@PathVariable String sourceIdSrch) {
        List<Integer> utmPlat = utmPlatService.getUsersList(sourceIdSrch);
        IntegerResponse response = new IntegerResponse();
        if (null != utmPlat) {
            response.setResult(utmPlat);
        }
        return response;
    }
}
