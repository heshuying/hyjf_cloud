/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.Link;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.config.LinkVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.ContentEnvironment;
import com.hyjf.am.config.service.ContentLinksService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.resquest.admin.ContentEnvironmentRequest;
import com.hyjf.am.resquest.admin.ContentLinksRequest;
import com.hyjf.am.vo.config.ContentEnvironmentVO;
import com.hyjf.am.vo.config.ContentQualifyVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author fuqiang
 * @version ContentLinksController, v0.1 2018/7/14 14:12
 */
@RestController
@RequestMapping("/hyjf-admin/content/contentlinks")
public class ContentLinksController extends BaseConfigController {
    @Autowired
    private ContentLinksService contentLinksService;

    /**
     * 根据条件查询友情链接
     *
     * @param request
     * @return
     */
    @RequestMapping("/searchaction")
    public LinkResponse searchAction(@RequestBody ContentLinksRequest request) {
        logger.info("查询内容中心-友情链接开始......");
        LinkResponse response = new LinkResponse();
        List<Link> list = contentLinksService.searchAction(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<LinkVO> voList = CommonUtils.convertBeanList(list, LinkVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 添加友情链接
     *
     * @param request
     * @return
     */
    @RequestMapping("/insertaction")
    public LinkResponse insertAction(@RequestBody ContentLinksRequest request) {
        LinkResponse response = new LinkResponse();
        contentLinksService.insertAction(request);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 修改友情链接
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateaction")
    public LinkResponse updateAction(@RequestBody ContentLinksRequest request) {
        LinkResponse response = new LinkResponse();
        contentLinksService.updateAction(request);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 根据id查询友情链接
     *
     * @param id
     * @return
     */
    @RequestMapping("/getrecord/{id}")
    public LinkResponse getRecord(@PathVariable Integer id) {
        LinkResponse response = new LinkResponse();
        Link link = contentLinksService.getRecord(id);
        if (link != null) {
            LinkVO vo = new LinkVO();
            BeanUtils.copyProperties(link, vo);
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
    public LinkResponse delete(@PathVariable Integer id) {
        LinkResponse response = new LinkResponse();
        contentLinksService.deleteById(id);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }
}
