package com.hyjf.am.market.controller.admin.content;

import com.hyjf.am.market.service.ContentAdsService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容中心-广告管理
 * @author：yinhui
 * @Date: 2018/7/19  14:39
 */
@RestController
@RequestMapping("/am-market/content/contentads")
public class ContentAdsController {
    private static final Logger logger = LoggerFactory.getLogger(ContentAdsController.class);

    @Autowired
    private ContentAdsService contentAdsService;

    /**
     * 根据条件查询广告管理
     *
     * @param request
     * @return
     */
    @RequestMapping("/searchaction")
    public ContentAdsResponse searchAction (@RequestBody ContentAdsRequest request){
        logger.info("查询内容中心 - 广告管理开始......");
        return contentAdsService.searchActionPage(request);
    }

    /**
     * 添加广告管理
     *
     * @param request
     * @return
     */
    @RequestMapping("/insertaction")
    public ContentAdsResponse insertAction (@RequestBody ContentAdsRequest request){
        ContentAdsResponse response = new ContentAdsResponse();
        boolean result = contentAdsService.insertAction(request);
        if(result){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }else{
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }
        return response;
    }

    /**
     * 修改广告管理
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateaction")
    public ContentAdsResponse updateaction (@RequestBody ContentAdsRequest request){
        ContentAdsResponse response = new ContentAdsResponse();
        boolean result = contentAdsService.updateAction(request);
        if(result){
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }else{
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }
        return response;
    }

    /**
     * 删除友情链接
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ContentAdsResponse delete(@PathVariable Integer id) {
        ContentAdsResponse response = new ContentAdsResponse();
        contentAdsService.deleteById(id);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }
}
