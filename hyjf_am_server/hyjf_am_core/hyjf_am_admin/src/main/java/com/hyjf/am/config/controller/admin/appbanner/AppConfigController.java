package com.hyjf.am.config.controller.admin.appbanner;

import com.hyjf.am.config.constant.AdsTypeEnum;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.market.dao.model.auto.AdsType;
import com.hyjf.am.market.service.AppConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.AppBannerResponse;
import com.hyjf.am.resquest.market.AppBannerRequest;
import com.hyjf.am.vo.market.AdsTypeVO;
import com.hyjf.am.vo.market.AdsVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lisheng
 * @version AppConfigController, v0.1 2018/7/11 15:09
 */
@RestController
@RequestMapping("/am-market/appconfig")
public class AppConfigController {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigController.class);
    @Autowired
    AppConfigService appConfigService;

    /**
     * 查询广告列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getRecordList")
    public AppBannerResponse findAppBannerData(@RequestBody AppBannerRequest request) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        Integer count = appConfigService.countRecordList(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
            List<Ads> recordList = appConfigService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            List<AdsVO> appBannerVos = CommonUtils.convertBeanList(recordList, AdsVO.class);
            appBannerResponse.setResultList(appBannerVos);
        }
        List<AdsType> adsTypeList = appConfigService.getAdsTypeList();
        List<AdsTypeVO> adsTypeVOS = CommonUtils.convertBeanList(adsTypeList, AdsTypeVO.class);
        List<AdsTypeVO> adsType = getAdsType(request.getPlatformType(), adsTypeVOS);
        appBannerResponse.setAdsTypeList(adsType);
        appBannerResponse.setRecordTotal(count);
        return appBannerResponse;
    }
    /**
     * 根据id查询广告
     *
     * @param ads
     * @return
     */
    @PostMapping("/getRecordById")
    public AppBannerResponse getRecordById(@RequestBody AdsVO ads) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        Ads a = appConfigService.getRecordById(ads.getId());
        AdsVO adsVO = CommonUtils.convertBean(a, AdsVO.class);
        List<AdsType> adsTypeList = appConfigService.getAdsTypeList();
        List<AdsTypeVO> adsTypeVOS = CommonUtils.convertBeanList(adsTypeList, AdsTypeVO.class);
        List<AdsTypeVO> adsType = getAdsType(ads.getPlatformType(), adsTypeVOS);
        appBannerResponse.setAdsTypeList(adsType);
        appBannerResponse.setResult(adsVO);
        return appBannerResponse;
    }
    /**
     * 插入广告列表
     *
     * @param adsVO
     * @return
     */
    @PostMapping("/insertRecord")
    public AppBannerResponse insertBannerData(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        Ads ads = new Ads();
        BeanUtils.copyProperties(adsVO, ads);
        Boolean result = appConfigService.insertRecord(ads);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }
        return appBannerResponse;
    }

    /**
     * 修改活动维护信息
     *
     * @param adsVO
     * @return
     */
    @PostMapping("/updateRecord")
    public AppBannerResponse updateAction(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 更新
        Boolean result = appConfigService.updateRecord(adsVO);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }
        return appBannerResponse;
    }

    /**
     * 修改状态
     *
     * @param adsVO
     * @return
     */
    @PostMapping("/updateStatus")
    public AppBannerResponse updateStatus(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 修改状态
        if (StringUtils.isNotEmpty(adsVO.getIds())) {
            Integer id = Integer.valueOf(adsVO.getIds());
            Ads record = appConfigService.getRecord(id);
            if (record.getStatus() == 1) {
                adsVO.setStatus(0);
            } else {
                adsVO.setStatus(1);
            }
            Boolean result = appConfigService.updateRecord(adsVO);
            if(result){
                appBannerResponse.setRtn(Response.SUCCESS);
                appBannerResponse.setMessage(Response.SUCCESS_MSG);
            }else{
                appBannerResponse.setRtn(Response.FAIL);
                appBannerResponse.setMessage(Response.FAIL_MSG);
            }
        }
        return appBannerResponse;
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("deleteAppBanner")
    public AppBannerResponse deleteRecordAction(@RequestBody AdsVO adsVO) {
        AppBannerResponse appBannerResponse = new AppBannerResponse();
        // 解析json字符串
        Integer id = Integer.valueOf(adsVO.getIds());
        boolean result = appConfigService.deleteRecord(id);
        if(result){
            appBannerResponse.setRtn(Response.SUCCESS);
            appBannerResponse.setMessage(Response.SUCCESS_MSG);
        }else{
            appBannerResponse.setRtn(Response.FAIL);
            appBannerResponse.setMessage(Response.FAIL_MSG);
        }

        return appBannerResponse;
    }

    /**
     * 获取广告类型列表
     * @param platformType
     * @param adsTypeList
     * @return
     */
    private List<AdsTypeVO> getAdsType(Integer platformType,List<AdsTypeVO> adsTypeList) {
        // 获取广告类型列表
        List<AdsTypeVO> returnNewList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(adsTypeList)){
            if(platformType.intValue()==1){
                for(AdsTypeVO type:adsTypeList){
                    if(AdsTypeEnum.startpage.toString().equals(type.getCode())) {
                        returnNewList.add(type);
                    }/*else if(AdsTypeEnum.popup.toString().equals(type.getCode())){
						returnNewList.add(type);
					}*/else if(AdsTypeEnum.android_banner.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_regist_888.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_open_888.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_module1.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_module2.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_module3.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_module4.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_find_module1.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_find_module2.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_find_module3.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_find_module4.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.android_find_banner.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }
                }
            }else if(platformType.intValue()==2){
                for(AdsTypeVO type:adsTypeList){
                    if(AdsTypeEnum.startpage.toString().equals(type.getCode())) {
                        returnNewList.add(type);
                    }/*else if(AdsTypeEnum.popup.toString().equals(type.getCode())){
						returnNewList.add(type);
					}*/else if(AdsTypeEnum.ios_banner.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_regist_888.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_open_888.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_module1.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_module2.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_module3.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_module4.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_find_module1.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_find_module2.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_find_module3.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_find_module4.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.ios_find_banner.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }
                }
            }else if(platformType.intValue()==3){
                for(AdsTypeVO type:adsTypeList){
                    if(AdsTypeEnum.startpage.toString().equals(type.getCode())) {
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.weixin.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_module1.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_module2.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_module3.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_module4.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_regist_888.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }else if(AdsTypeEnum.wechat_banner.toString().equals(type.getCode())){
                        returnNewList.add(type);
                    }
                }
            }
        }
        return returnNewList;
    }
}
