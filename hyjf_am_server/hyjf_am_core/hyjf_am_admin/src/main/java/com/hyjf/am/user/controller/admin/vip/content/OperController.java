package com.hyjf.am.user.controller.admin.vip.content;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.ScreenConfigRequest;
import com.hyjf.am.user.dao.model.auto.ScreenConfig;
import com.hyjf.am.user.service.admin.vip.content.OperService;
import com.hyjf.am.vo.user.ScreenConfigVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "会员中心-内容中心-大屏运营部数据配置 am-admin")
@RequestMapping("/am-user/vip/content")
@RestController
public class OperController {

    @Autowired
    private OperService operService;

    /**
     * 大屏运营部数据配置列表查询
     * @param request
     * @return
     */
    @PostMapping("/oper/list")
    @ResponseBody
    private Response<ScreenConfigVO> list(ScreenConfigRequest request){
        Response<ScreenConfigVO> response = new Response<>();

        List<ScreenConfig> list = operService.list(request);
        if (null == list){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        List<ScreenConfigVO> screenConfigVOs = CommonUtils.convertBeanList(list, ScreenConfigVO.class);
        response.setResultList(screenConfigVOs);
        return response;
    }

    /**
     * 大屏运营部数据配置数据新增
     * @param screenConfigVO
     * @return
     */
    @PostMapping("/oper/add")
    @ResponseBody
    private IntegerResponse insert(ScreenConfigVO screenConfigVO){
        IntegerResponse response = new IntegerResponse();

        int insertFlag = operService.insert(screenConfigVO);
        if (insertFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(insertFlag);
        return response;
    }

    /**
     * 大屏运营部数据配置数据编辑/启用/禁用
     * @param screenConfigVO
     * @return
     */
    @PostMapping("/oper/update")
    @ResponseBody
    private IntegerResponse update(ScreenConfigVO screenConfigVO){
        IntegerResponse response = new IntegerResponse();

        int updatetFlag = operService.update(screenConfigVO);
        if (updatetFlag != 1){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        response.setResultInt(updatetFlag);
        return response;
    }
}
