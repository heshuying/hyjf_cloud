/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentArticle;
import com.hyjf.am.config.service.ContentArticleService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.admin.ContentArticleResponse;
import com.hyjf.am.resquest.config.ContentArticleRequest;
import com.hyjf.am.vo.config.ContentArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 内容管理 - 文章管理
 * @author yinhui
 * @version ContentArticleController, v0.1 2018/7/18 11:15
 */
@RestController
@RequestMapping("/am-config/content/contentarticle")
public class ContentArticleManageController extends BaseConfigController{

    @Autowired
    private ContentArticleService contentArticleService;

    /**
     * 根据条件查询内容管理 - 文章管理
     *
     * @return
     */
    @RequestMapping("/searchaction")
    public ContentArticleResponse searchAction(@RequestBody ContentArticleRequest requestBean){
        logger.info("查询内容管理 - 文章管理开始......");
        return contentArticleService.getContentArticleListPage(requestBean);
    }

    /**
     * 添加文章
     * @param requestBean
     * @return
     */
    @RequestMapping("/insertaction")
    public ContentArticleResponse insertAction(@RequestBody ContentArticleRequest requestBean){
        ContentArticleResponse response = new ContentArticleResponse();
        contentArticleService.insertAction(requestBean);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 通过id查询数据
     * @return
     */
    @RequestMapping("/findbyId/{id}")
    public ContentArticleResponse findById(@PathVariable Integer id){
        ContentArticleResponse response = new ContentArticleResponse();
        ContentArticleVO vo = new ContentArticleVO();
        ContentArticle contentArticle = contentArticleService.getArticleById(id);
        if(contentArticle == null){
            response.setRtn(AdminResponse.ERROR);
            return response;
        }
        BeanUtils.copyProperties(contentArticle,vo);
        response.setResult(vo);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 修改文章
     * @param requestBean
     * @return
     */
    @RequestMapping("/updateaction")
    public ContentArticleResponse updateAction(@RequestBody ContentArticleRequest requestBean){
        ContentArticleResponse response = new ContentArticleResponse();
        contentArticleService.updateAction(requestBean);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 删除文章
     * @return
     */
    @RequestMapping("/delete/{id}")
    public ContentArticleResponse updateAction(@PathVariable Integer id){
        ContentArticleResponse response = new ContentArticleResponse();
        contentArticleService.delectAction(id);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }
}
