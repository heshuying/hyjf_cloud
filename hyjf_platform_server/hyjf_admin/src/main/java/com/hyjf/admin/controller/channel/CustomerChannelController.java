package com.hyjf.admin.controller.channel;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CustomerChannelService;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * am-admin渠道来源配置
 * @author by dzs on 2019/5/29.
 */
@Api(value = "配置中心-电销配置-客户中心配置开发",tags = "配置中心-电销配置-客户中心配置开发")
@RestController
@RequestMapping("/hyjf-admin/channel/")
public class CustomerChannelController extends BaseController {
	public static final String PERMISSIONS = "customerChanne";
    @Autowired
    private CustomerChannelService customerChannelService;


    /**
     * 渠道来源列表
     * @return
     */
	@ApiOperation(value = "渠道来源列表", notes = "渠道来源列表")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @PostMapping("/getCustomerChannelList")
    public CustomerChannelResponse getAuthConfigList(@RequestBody CustomerChannelRequest request) {
        return customerChannelService.getCustomerChannelList(request);
    }


    /**
     * 插入数据
     * @param 
     * @return
     */
	@ApiOperation(value = "插入数据", notes = "插入数据")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    @PostMapping("/insetCustomerChannel")
    public CustomerChannelResponse getAuthConfigById(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){

        return customerChannelService.insetCustomerChannel(cRequest);
    }

    /**
     * 修改授权配置
     * @param form
     * @return
     */
	@ApiOperation(value = "修改授权配置", notes = "修改授权配置")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @PostMapping("/updateCustomerChannel")
    public CustomerChannelResponse updateCustomerChannel(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){
      return customerChannelService.updateCustomerChannel(cRequest);
    }

}
