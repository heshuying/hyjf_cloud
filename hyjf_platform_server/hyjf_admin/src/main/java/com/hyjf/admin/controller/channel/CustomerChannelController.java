package com.hyjf.admin.controller.channel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;
import com.hyjf.am.vo.admin.CustomerServiceChannelVO;

import com.hyjf.common.util.CommonUtils;

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
    public AdminAuthConfigResponse getAuthConfigById(@RequestBody CustomerChannelRequest request){

        return customerChannelService.insetCustomerChannel(request);
    }

    /**
     * 修改授权配置
     * @param form
     * @return
     */
	@ApiOperation(value = "修改授权配置", notes = "修改授权配置")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @PostMapping("/updateAuthConfig")
    public AdminAuthConfigResponse updateAuthConfig(@RequestBody CustomerChannelRequest request){
      return customerChannelService.updateAuthConfig(request);
    }

}
