package com.hyjf.admin.controller.channel;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CustomerChannelService;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;

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
    @Autowired
    private ChannelService channelService;


    /**
     * 渠道来源列表
     * @return
     */
	@ApiOperation(value = "渠道来源列表", notes = "渠道来源列表")
	@ResponseBody
//	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @PostMapping("/getCustomerChannelList")
    public  AdminResult<CustomerChannelResponse> getAuthConfigList(@RequestBody CustomerChannelRequest request) {
        return  new AdminResult<CustomerChannelResponse>(customerChannelService.getCustomerChannelList(request));
    }


    /**
     * 插入数据
     * @param 
     * @return
     */
	@ApiOperation(value = "插入数据", notes = "插入数据")
	@ResponseBody
//	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    @PostMapping("/insetCustomerChannel")
    public AdminResult<CustomerChannelResponse> getAuthConfigById(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){
		cRequest.setUpdateUser(this.getUser(request).getUsername());
		cRequest.setCreateUser(this.getUser(request).getUsername());
		cRequest.setCreateTime(new Date());
		cRequest.setUpdateTime(new Date());
		CustomerChannelResponse re = customerChannelService.insetCustomerChannel(cRequest);
		if(!Response.isSuccess(re)) {
			return new AdminResult<>(AdminResult.FAIL, re.getMessage());
		}
      return new AdminResult<CustomerChannelResponse>();
    }
    /**
     * 插入数据
     * @param 
     * @return
     */
	@ApiOperation(value = "查询渠道", notes = "查询渠道")
	@ResponseBody
//	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    @PostMapping("/getUtmPlat")
    public AdminResult<List<UtmPlatVO>> getUtmPlat(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){
		 List<UtmPlatVO> utmPlatVOList = channelService.getUtmPlat();
        return new AdminResult<List<UtmPlatVO>>(utmPlatVOList);
    }
    /**
     * 修改授权配置
     * @param form
     * @return
     */
	@ApiOperation(value = "修改授权配置", notes = "修改授权配置")
	@ResponseBody
//	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @PostMapping("/updateCustomerChannel")
    public AdminResult<CustomerChannelResponse> updateCustomerChannel(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){
		cRequest.setUpdateUser(this.getUser(request).getUsername());
		cRequest.setUpdateTime(new Date());
		CustomerChannelResponse re = customerChannelService.updateCustomerChannel(cRequest);
		if(!Response.isSuccess(re)) {
			return new AdminResult<>(AdminResult.FAIL, re.getMessage());
		}
      return new AdminResult<CustomerChannelResponse>();
    }
    /**
     * 老带新配置
     * @param form
     * @return
     */
	@ApiOperation(value = "老带新配置", notes = "老带新配置")
	@ResponseBody
    @PostMapping("/updateStatus")
    public AdminResult<CustomerChannelResponse> updateStatus(HttpServletRequest request,@RequestBody CustomerChannelRequest cRequest){
		if(cRequest.getStatus()!=null) {
			RedisUtils.set(RedisConstants.CUSTOMER_SERVICE_SWITCH, cRequest.getStatus().toString());
		}
		CustomerChannelResponse ccr=new CustomerChannelResponse();
		ccr.setStatus(Integer.valueOf(RedisUtils.get(RedisConstants.CUSTOMER_SERVICE_SWITCH)));
      return new AdminResult<CustomerChannelResponse>(ccr);
    }

}
