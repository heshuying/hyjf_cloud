package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsDetailRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.service.admin.promotion.UtmRegService;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version AppUtmRegController, v0.1 2018/11/9 9:22
 */
@RestController
@RequestMapping("/am-admin/app_utm_reg")
public class AppUtmRegController extends BaseController {
    @Autowired
    private AppUtmRegService appUtmRegService;

    @Autowired
    private UtmRegService utmRegService;

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
     * 新增app渠道信息
     * @param appUtmReg
     * @return
     */
    @RequestMapping("/insertAppUtmReg")
    public boolean insertAppUtmReg(@RequestBody  AppUtmRegVO appUtmReg) {
        AppUtmReg appUtmRegConvert = CommonUtils.convertBean(appUtmReg,AppUtmReg.class);
        appUtmRegService.insert(appUtmRegConvert);
        return true;
    }

    /**
     * 修改app渠道信息
     * @param appUtmReg
     * @return
     */
    @RequestMapping("/updateAppUtmReg")
    public boolean updateAppUtmReg(@RequestBody  AppUtmRegVO appUtmReg) {
        AppUtmReg appUtmRegConvert = CommonUtils.convertBean(appUtmReg,AppUtmReg.class);
        appUtmRegService.updateByPrimaryKeySelective(appUtmRegConvert);
        return true;
    }

    /**
     * 新增pc渠道信息
     * @param appUtmReg
     * @return
     */
    @RequestMapping("/insertPcUtmReg")
    public boolean insertPcUtmReg(@RequestBody UtmRegVO appUtmReg) {
        UtmReg appUtmRegConvert = CommonUtils.convertBean(appUtmReg,UtmReg.class);
        utmRegService.insertPcUtmReg(appUtmRegConvert);

        return true;
    }

    /**
     * 修改pc渠道信息
     * @param appUtmReg
     * @return
     */
    @RequestMapping("/updatePcUtmReg")
    public boolean updatePcUtmReg(@RequestBody  UtmRegVO appUtmReg) {
        UtmReg appUtmRegConvert = CommonUtils.convertBean(appUtmReg,UtmReg.class);
        utmRegService.updatePcUtmReg(appUtmRegConvert);
        return true;
    }

}
