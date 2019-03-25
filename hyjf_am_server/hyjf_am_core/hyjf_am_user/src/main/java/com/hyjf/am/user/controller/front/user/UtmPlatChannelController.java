/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.response.user.UtmVOResponse;
import com.hyjf.am.resquest.user.UtmPlatRequest;
import com.hyjf.am.resquest.user.UtmRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.UtmPlatChannelService;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.user.UtmPlatVO;

/**
 * 用户渠道相关
 *
 * @author liuyang
 * @version UtmPlatChannelController, v0.1 2018/10/18 16:34
 */
@RestController
@RequestMapping("/am-user/utm")
public class UtmPlatChannelController extends BaseController {

    @Autowired
    private UtmPlatChannelService utmPlatChannelService;

    /**
     * 根据utmId查询Utm
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectUtmByUtmId")
    public UtmVOResponse selectUtmByUtmId(@RequestBody @Valid UtmRequest request) {
        UtmVOResponse response = new UtmVOResponse();
        try {
            Integer utmId = request.getUtmId();
            UtmVO utm = utmPlatChannelService.selectUtmByUtmId(utmId);
            response.setResult(utm);
            response.setRtn(UtmResponse.SUCCESS);
        } catch (Exception e) {
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }

    @RequestMapping("/selectUtmBySourceId/{sourceId}")
    public UtmVOResponse selectUtmBySourceId(@PathVariable String sourceId) {
        UtmVOResponse response = new UtmVOResponse();
        try {
            Integer soutceID = Integer.valueOf(sourceId);
            UtmVO utm = utmPlatChannelService.selectUtmBySourceId(soutceID);
            response.setResult(utm);
            response.setRtn(UtmResponse.SUCCESS);
        } catch (Exception e) {
            response.setRtn(UtmResponse.FAIL);
        }
        return response;
    }


    /**
     * 根据source_id查询UtmPlat
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectUtmPlatBySourceId")
    public UtmPlatResponse selectUtmPlatBySourceId(@RequestBody @Valid UtmPlatRequest request) {
        UtmPlatResponse response = new UtmPlatResponse();
        try {
            Integer sourceId = request.getSourceId();
            UtmPlatVO utmPlatVO = utmPlatChannelService.selectUtmPlatBySourceId(sourceId);
            response.setResult(utmPlatVO);
            response.setRtn(UtmPlatResponse.SUCCESS);
        } catch (Exception e) {
            response.setRtn(UtmPlatResponse.FAIL);
        }
        return response;
    }

}
