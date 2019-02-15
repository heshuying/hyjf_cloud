package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    @RequestMapping("/getRegistNumberCount")
    public IntegerResponse getRegistNumberCount(@RequestBody AppChannelStatisticsRequest request) {
        IntegerResponse response = new IntegerResponse();
        int count = appUtmRegService.getAppUtmRegVOCount(request);
        response.setResultInt(count);
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
        request.setSourceIdSrch("hxfTenderPrice");
        List<AppUtmReg> list1 = appUtmRegService.getAppUtmRegVO(request);
        if (!CollectionUtils.isEmpty(list1)) {
            List<AppUtmRegVO> voList = null;
            voList = CommonUtils.convertBeanList(list1, AppUtmRegVO.class);
            response.setHxfTenderPriceList(voList);
        }
        request.setSourceIdSrch("hztTenderPrice");
        List<AppUtmReg> list2 = appUtmRegService.getAppUtmRegVO(request);
        if (!CollectionUtils.isEmpty(list2)) {
            List<AppUtmRegVO> voList = null;
            voList = CommonUtils.convertBeanList(list2, AppUtmRegVO.class);
            response.setHztTenderPriceList(voList);
        }
        request.setSourceIdSrch("openAccountTime");
        List<AppUtmReg> list3 = appUtmRegService.getAppUtmRegVO(request);
        if (!CollectionUtils.isEmpty(list3)) {
            List<AppUtmRegVO> voList = null;
            voList = CommonUtils.convertBeanList(list3, AppUtmRegVO.class);
            response.setOpenAccountTimeList(voList);
        }
        return response;
    }

    /**
     * 查询相应的app渠道无主单开户数
     * @return
     */
    @RequestMapping("/getOpenAccountAttrCount")
    public IntegerResponse getOpenAccountAttrCount(@RequestBody AppChannelStatisticsRequest request) {
        IntegerResponse response = new IntegerResponse();
        int count = appUtmRegService.getOpenAccountAttrCount(request);
        response.setResultInt(count);
        return response;
    }

    /**
     *插入app渠道注册统计数据
     * @return
     */
    @RequestMapping("/insertAppChannelStatisticsDetail")
    public BooleanResponse insertAppChannelStatisticsDetail(@RequestBody WrbRegisterRequest request) {
        UtmPlatVO utmPlat = request.getUtmPlat();
        BooleanResponse booleanResponse = new BooleanResponse();
        AppUtmReg detail = new AppUtmReg();
        detail.setSourceId(utmPlat.getSourceId());
        detail.setSourceName(utmPlat.getSourceName() != null ? utmPlat.getSourceName() : "");
        detail.setUserId(request.getUserId());
        detail.setRegisterTime(new Date());
        detail.setCumulativeInvest(BigDecimal.ZERO);
        appUtmRegService.insert(detail);
        return booleanResponse;
    }

    /**
     * 分页查询所有渠道出借信息
     *
     * @return
     */
    @RequestMapping("/getstatisticsList")
    public AppUtmRegResponse getstatisticsList(@RequestBody  AppChannelStatisticsDetailRequest request) {
        AppUtmRegResponse response = new AppUtmRegResponse();
        Integer count = appUtmRegService.countAppUtmReg(request);
        if (count>0) {
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            List<AppUtmReg> list = appUtmRegService.findAppUtmReg(request,paginator);
            if (!CollectionUtils.isEmpty(list)) {
                List<AppUtmRegVO> voList = CommonUtils.convertBeanList(list, AppUtmRegVO.class);
                response.setResultList(voList);
            }
        }
        response.setCount(count);
        return response;
    }

}
