package com.hyjf.am.config.controller.admin.channel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.service.CustomerChannelService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CustomerChannelResponse;
import com.hyjf.am.resquest.admin.CustomerChannelRequest;
import com.hyjf.am.vo.admin.CustomerServiceChannelVO;

import com.hyjf.common.util.CommonUtils;

/**
 * am-admin渠道来源配置
 * @author by dzs on 2019/5/29.
 */
@RestController
@RequestMapping("/am-config/channel/")
public class CustomerChannelController extends BaseConfigController {

    @Autowired
    private CustomerChannelService customerChannelService;


    /**
     * 渠道来源列表
     * @return
     */
    @PostMapping("/getCustomerChannelList")
    public CustomerChannelResponse getAuthConfigList(@RequestBody CustomerChannelRequest request) {
    	CustomerChannelResponse response =new CustomerChannelResponse();
        //查询版本配置列表条数
        int recordTotal = customerChannelService.getCustomerChannelCount();
        if (recordTotal > 0) {
            List<CustomerServiceChannel> recordList = customerChannelService.getCustomerChannelList(request,recordTotal);
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(CommonUtils.convertBeanList(recordList, CustomerServiceChannelVO.class));
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


    /**
     * 插入数据
     * @param 
     * @return
     */
    @PostMapping("/insetCustomerChannel")
    public CustomerChannelResponse getAuthConfigById(@RequestBody CustomerChannelRequest request){
    	CustomerChannelResponse response = new CustomerChannelResponse();
        int i = customerChannelService.insetCustomerChannel(request);
        if (i==1){
            response.setMessage(Response.SUCCESS_MSG);
            response.setRtn(Response.SUCCESS);
        }else {
            response.setMessage("该渠道来源已经添加");
            response.setRtn(Response.FAIL);
        }
        return response;
    }

    /**
     * 修改授权配置
     * @param form
     * @return
     */
    @PostMapping("/updateCustomerChannel")
    public CustomerChannelResponse updateCustomerChannel(@RequestBody CustomerChannelRequest request){
    	CustomerChannelResponse response = new CustomerChannelResponse();
      int i = customerChannelService.updateCustomerChannel(request);
      if (i==1){
          response.setMessage(Response.SUCCESS_MSG);
          response.setRtn(Response.SUCCESS);
      }else {
          response.setMessage("该渠道来源已经添加");
          response.setRtn(Response.FAIL);
      }
      return response;
    }

}
