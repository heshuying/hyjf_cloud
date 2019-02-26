package com.hyjf.am.trade.controller.admin.pushmanage;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.AppPushManageRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AppPushManage;
import com.hyjf.am.trade.service.admin.pushmanage.AppPushManageService;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移动客户端 App 推送管理
 *
 * @Author : huanghui
 */
@Api(value = "App推送管理")
@RestController
@RequestMapping("/am-admin/appPushManage")
public class AppPushManageController extends BaseController {

    @Autowired
    AppPushManageService appPushManageService;

    /**
     * 获取 后台 推送信息列表
     * @return
     */
    @RequestMapping(value = "/selectPushManageList", method = RequestMethod.POST)
    public AppPushManageResponse selectPushManageList(@RequestBody AppPushManageRequest pushManageRequest){
        AppPushManageResponse pushManageResponse = new AppPushManageResponse();

        // 获取推送的总条数
        Integer count = this.appPushManageService.getCountList(pushManageRequest);
        if (count > 0){
//            Paginator paginator = new Paginator(pushManageRequest.getCurrPage(),count, pushManageRequest.getPageSize());
            Paginator paginator = new Paginator(pushManageRequest.getCurrPage(),count, pushManageRequest.getPageSize());
            // 获取所有的列表
            List<AppPushManage> pushManageList = this.appPushManageService.getAllList(pushManageRequest, paginator);

            if (!CollectionUtils.isEmpty(pushManageList)){
                pushManageResponse.setResultList(CommonUtils.convertBeanList(pushManageList, AppPushManageVO.class));
                pushManageResponse.setRtn(Response.SUCCESS);
            }
        }

        pushManageResponse.setCount(count);
        return pushManageResponse;
    }

    /**
     * 数据写入.
     * @param pushManageRequest
     * @return
     */
    @RequestMapping(value = "/insertPushManage", method = RequestMethod.POST)
    public AppPushManageResponse insertPushManage(@RequestBody AppPushManageRequest pushManageRequest){
        AppPushManageResponse pushManageResponse = new AppPushManageResponse();

        /**
         * 当选择原生时强制设置跳转内容值为 0
         * 原生和H5 URL 时,推送内容和缩略图为空
         * H5 自定义是url内容为空
         */
        if (pushManageRequest.getJumpType() == 0){
            pushManageRequest.setJumpContent(0);
            pushManageRequest.setContent("");
            pushManageRequest.setThumb("");
        }else {
            if (pushManageRequest.getJumpContent() == 1){
                pushManageRequest.setContent("");
                pushManageRequest.setThumb("");
            }else{
                pushManageRequest.setJumpUrl("");
            }
        }

        int rtnCode = appPushManageService.insertPushManage(pushManageRequest);
        if (rtnCode > 0){
            pushManageResponse.setRtn(AdminResponse.SUCCESS);
        }else {
            pushManageResponse.setRtn(AdminResponse.FAIL);
            pushManageResponse.setMessage(AdminResponse.FAIL_MSG);
        }
        return pushManageResponse;
    }

    /**
     * 更新单条记录
     * @param pushManageRequest
     * @return
     */
    @RequestMapping(value = "/updatePushManage", method = RequestMethod.POST)
    public boolean updatePushManage(@RequestBody AppPushManageRequest pushManageRequest){

        //当选择原生时强制设置跳转内容值为 0
        //原生和H5 URL 时,推送内容和缩略图为空
        //H5 自定义是url内容为空
        if (pushManageRequest.getJumpType() == 0){
            pushManageRequest.setJumpContent(0);
            pushManageRequest.setContent("");
            pushManageRequest.setThumb("");
        }else {
            if (pushManageRequest.getJumpContent() == 1){
                pushManageRequest.setContent("");
                pushManageRequest.setThumb("");
            }else{
                pushManageRequest.setJumpUrl("");
            }
        }

        // 更新返回状态
        int updateCode = appPushManageService.updatePushManage(pushManageRequest);

        if (updateCode > 0){
            return true;
        }
        return false;
    }

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/deletePushManage/{id}", method = RequestMethod.GET)
    public boolean deletePushManage(@PathVariable Integer id){
        int deleteCode = appPushManageService.deletePushManage(id);
        if (deleteCode > 0){
            return true;
        }
        return false;
    }

    /**
     * 根据ID 获取单条详细信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAppPushManageInfoById/{id}", method = RequestMethod.GET)
    public AppPushManageResponse getAppPushManageInfoById(@PathVariable Integer id){
        AppPushManageResponse pushManageResponse = new AppPushManageResponse();
        AppPushManageVO pushManageVO = new AppPushManageVO();
        AppPushManage pushManage = appPushManageService.getAppPushManageInfoById(id);

        if (null != pushManage){
            BeanUtils.copyProperties(pushManage, pushManageVO);
            pushManageVO.setCreateTimeStr(GetDate.formatDate(pushManage.getCreateTime()));
            pushManageVO.setTimeStartStr(GetDate.formatDate(pushManage.getTimeStart()));
            pushManageVO.setTimeEndStr(GetDate.formatDate(pushManage.getTimeEnd()));
            pushManageResponse.setResult(pushManageVO);
            pushManageResponse.setRtn(AdminResponse.SUCCESS);
        }else {
            pushManageResponse.setRtn(AdminResponse.ERROR);
        }
        return pushManageResponse;
    }

    /**
     * 根据ID 更新单条记录状态
     * @param pushManageRequest
     * @return
     */
    @RequestMapping(value = "/updatePushManageStatusById", method = RequestMethod.POST)
    public boolean updatePushManageStatusById(@RequestBody AppPushManageRequest pushManageRequest){

        AppPushManage pushManage = appPushManageService.getAppPushManageInfoById(pushManageRequest.getIds());

        if (pushManage == null){
            // 当前ID查询数据不存在时
            logger.info("根据ID 更新App推送单条记录状态时ID对应数据不存在,提交ID为:" + pushManageRequest.getIds());
            return false;
        }
        pushManageRequest.setId(pushManageRequest.getIds());

        // 根据当前信息的状态,判断应该讲状态更新到的状态
        if (pushManage.getStatus() == 1){
            pushManageRequest.setStatus(0);
        }else {
            pushManageRequest.setStatus(1);
        }

        int updateCode = appPushManageService.updatePushManage(pushManageRequest);

        if (updateCode > 0){
            return  true;
        }
        return false;
    }

}
