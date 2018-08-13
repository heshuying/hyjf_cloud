/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.exception;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MobileSynchronizeResponse;
import com.hyjf.am.resquest.admin.MobileSynchronizeRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.MobileSynchronizeCustomize;
import com.hyjf.am.user.service.admin.exception.AdminMobileSynchronizeService;
import com.hyjf.am.vo.admin.MobileSynchronizeCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: MobileSynchronizeController, v0.1 2018/8/13 14:19
 */
@Api(value = "手机号同步",tags = "手机号同步")
@RestController
@RequestMapping(value = "/am-user/mobilesynchronize")
public class MobileSynchronizeController extends BaseController {

    @Autowired
    private AdminMobileSynchronizeService adminMobileSynchronizeService;

    @PostMapping(value = "/countBankOpenAccountUser")
    public MobileSynchronizeResponse countBankOpenAccountUser(@RequestBody MobileSynchronizeRequest request){
        MobileSynchronizeResponse response = new MobileSynchronizeResponse();
        int count = adminMobileSynchronizeService.countBankOpenAccountUser(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @PostMapping(value = "/selectBankOpenAccountUserList")
    public MobileSynchronizeResponse selectBankOpenAccountUserList(@RequestBody MobileSynchronizeRequest request){
        MobileSynchronizeResponse response = new MobileSynchronizeResponse();
        Integer count = adminMobileSynchronizeService.countBankOpenAccountUser(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("selectBankOpenAccountUserList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<MobileSynchronizeCustomize> mobileSynchronizeCustomizeList = adminMobileSynchronizeService.selectBankOpenAccountUserList(request);
        if(!CollectionUtils.isEmpty(mobileSynchronizeCustomizeList)){
            List<MobileSynchronizeCustomizeVO> mobileSynchronizeCustomizeVOList = CommonUtils.convertBeanList(mobileSynchronizeCustomizeList,MobileSynchronizeCustomizeVO.class);
            response.setResultList(mobileSynchronizeCustomizeVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @PostMapping(value = "/updateMobile")
    public MobileSynchronizeResponse updateMobile(@RequestBody MobileSynchronizeRequest request) throws Exception {
        MobileSynchronizeResponse response = new MobileSynchronizeResponse();
        boolean update = adminMobileSynchronizeService.updateMobile(request);
        response.setUpdate(update);
        response.setRtn(Response.SUCCESS);
        return response;
    }

}
