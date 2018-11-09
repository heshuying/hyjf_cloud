package com.hyjf.am.user.controller.front.user;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author fuqiang
 * @version AppUtmRegController, v0.1 2018/11/9 9:22
 */
@RestController
@RequestMapping("/am-user/app_utm_reg")
public class AppUtmRegController extends BaseController {
    @Autowired
    private AppUtmRegService appUtmRegService;

    /**
     * 导出app渠道数据
     * @return
     */
    @RequestMapping("/exportStatisticsList")
    public AppUtmRegResponse exportStatisticsList(@RequestBody AppChannelStatisticsDetailRequest request){
        AppUtmRegResponse response = new AppUtmRegResponse();
        List<AppUtmReg> list = appUtmRegService.exportStatisticsList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<AppUtmRegVO> voList = CommonUtils.convertBeanList(list, AppUtmRegVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询app渠道信息
     * @param userId
     * @return
     */
    @RequestMapping("/findByUserId/{userId}")
    public AppUtmRegResponse findByUserId(@PathVariable Integer userId) {
        AppUtmRegResponse response = new AppUtmRegResponse();
        AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
        if (appUtmReg != null) {
            AppUtmRegVO vo = new AppUtmRegVO();
            BeanUtils.copyProperties(appUtmReg, vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 查询所有app渠道注册信息
     */
    @RequestMapping("/getappchannelstatisticsdetail")
    public AppUtmRegResponse getappchannelstatisticsdetail() {
        AppUtmRegResponse response = new AppUtmRegResponse();
        List<AppUtmReg> list = appUtmRegService.findAll();
        if (!CollectionUtils.isEmpty(list)) {
            List<AppUtmRegVO> voList = null;
            voList = CommonUtils.convertBeanList(list, AppUtmRegVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     *根据开始时间、结束时间和来源查询数据
     * @param request
     * @return
     */
    @RequestMapping("/getRegistNumber")
    public AppUtmRegResponse getRegistNumber(@RequestBody AppChannelStatisticsRequest request) {
        AppUtmRegResponse response = new AppUtmRegResponse();
        List<AppUtmReg>  list = appUtmRegService.getAppUtmRegVO(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<AppUtmRegVO> voList = null;
            voList = CommonUtils.convertBeanList(list, AppUtmRegVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
