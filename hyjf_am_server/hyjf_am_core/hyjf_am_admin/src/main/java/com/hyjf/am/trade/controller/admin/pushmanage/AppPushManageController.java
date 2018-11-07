package com.hyjf.am.trade.controller.admin.pushmanage;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.AppPushManageRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AppPushManage;
import com.hyjf.am.trade.service.admin.pushmanage.AppPushManageService;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/am-trade/appPushManage")
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

            // 分页配置
            if (pushManageRequest.getCurrPage() > 0){
                Paginator paginator = new Paginator(pushManageRequest.getCurrPage(), count);
                pushManageRequest.setLimitStart(paginator.getOffset());
                if (pushManageRequest.getPageSize() > 0){
                    pushManageRequest.setLimitEnd(pushManageRequest.getPageSize());
                }else {
                    pushManageRequest.setLimitEnd(paginator.getLimit());
                }
            }

            // 获取所有的列表
            List<AppPushManage> pushManageList = this.appPushManageService.getAllList(pushManageRequest);

            if (!CollectionUtils.isEmpty(pushManageList)){
                pushManageResponse.setResultList(CommonUtils.convertBeanList(pushManageList, AppPushManageVO.class));
                pushManageResponse.setCount(count);
                pushManageResponse.setRtn(Response.SUCCESS);
            }
        }
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

}
