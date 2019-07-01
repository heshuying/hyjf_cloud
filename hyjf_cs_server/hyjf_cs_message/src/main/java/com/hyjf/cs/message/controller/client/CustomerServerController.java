/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.CustomerServerResponse;
import com.hyjf.am.resquest.message.JcCustomerServerRequest;
import com.hyjf.am.vo.admin.JcCustomerServiceVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.JcCustomerService;
import com.hyjf.cs.message.service.JcCustomerServerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author yaoyong
 * @version CustomerServerController, v0.1 2019/6/24 9:40
 */
@ApiIgnore
@RestController
@RequestMapping("cs-message/customerServer")
public class CustomerServerController extends BaseController {

    @Autowired
    private JcCustomerServerService customerServerService;

    @RequestMapping("/serverList")
    public CustomerServerResponse getServerList(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = new CustomerServerResponse();
        long count = customerServerService.countServerList(request);
        List<JcCustomerService> serviceList = customerServerService.getServerList(request);
        if (!CollectionUtils.isEmpty(serviceList)) {
            List<JcCustomerServiceVO> serverList = CommonUtils.convertBeanList(serviceList, JcCustomerServiceVO.class);
            for (JcCustomerServiceVO customerService : serverList) {
                double complaint =(double) (customerService.getComplaintNum() * 100) / (customerService.getPhoneReception() + customerService.getQqReception() + customerService.getWxReception());
                customerService.setComplaint(String.format("%.2f", complaint) + "%");
            }
            response.setResultList(serverList);
            response.setCount((int) count);
        }
        return response;
    }


    @RequestMapping("/addCustomerServer")
    public CustomerServerResponse addCustomerServer(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = new CustomerServerResponse();
        JcCustomerService customerService = new JcCustomerService();
        if (request != null) {
            BeanUtils.copyProperties(request, customerService);
        }
        customerService.setCreateTime(GetDate.getNowTime10());
        customerService.setUpdateTime(GetDate.getNowTime10());
        Integer count = customerServerService.insertServer(customerService);
        response.setCount(count);
        return response;
    }

    @RequestMapping("/getCustomerServer/{id}")
    public CustomerServerResponse getCustomerServer(@PathVariable String id) {
        CustomerServerResponse response = new CustomerServerResponse();
        JcCustomerService customerService = customerServerService.getCustomerServer(id);
        JcCustomerServiceVO customerServiceVO = new JcCustomerServiceVO();
        BeanUtils.copyProperties(customerService, customerServiceVO);
        response.setResult(customerServiceVO);
        return response;
    }

    @RequestMapping("/updateCustomerServer")
    public CustomerServerResponse updateCustomerServer(@RequestBody JcCustomerServerRequest request) {
        CustomerServerResponse response = new CustomerServerResponse();
        JcCustomerService customerService = new JcCustomerService();
        BeanUtils.copyProperties(request, customerService);
        customerService.setUpdateTime(GetDate.getNowTime10());
        Integer count = customerServerService.updateCustomerServer(customerService);
        response.setCount(count);
        return response;
    }

    @RequestMapping("/getCustomerService")
    public CustomerServerResponse getCustomerService() {
        CustomerServerResponse response = new CustomerServerResponse();
        JcCustomerService customerService = customerServerService.getCustomerService();
        JcCustomerServiceVO customerServiceVO = new JcCustomerServiceVO();
        if (customerService != null) {
            BeanUtils.copyProperties(customerService, customerServiceVO);
            double complaint = (double) (customerService.getComplaintNum() * 100) / (customerService.getPhoneReception() + customerService.getQqReception() + customerService.getWxReception());
            customerServiceVO.setComplaint(String.format("%.2f", complaint));
            response.setResult(customerServiceVO);
        }
        return response;
    }

}
