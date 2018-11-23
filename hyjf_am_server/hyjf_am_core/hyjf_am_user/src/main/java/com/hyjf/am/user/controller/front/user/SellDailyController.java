package com.hyjf.am.user.controller.front.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.crm.CrmDepartmentService;

/**
 * @author fuqiang
 * @version SellDailyController, v0.1 2018/11/21 10:47
 */
@RestController
@RequestMapping("/am-user/sell_daily")
public class SellDailyController extends BaseController {
    @Autowired
    private CrmDepartmentService crmDepartmentService;

    /**
     * 根据一级部门查询二级部门
     * @param primaryDivision
     * @return
     */
    @RequestMapping("/select_two_division_by_primary_division/{primaryDivision}")
    public StringResponse selectTwoDivisionByPrimaryDivision(@PathVariable String primaryDivision) {
        StringResponse response = new StringResponse();
        List<String> list = crmDepartmentService.selectTwoDivisionByPrimaryDivision(primaryDivision);
        response.setResultList(list);
        return response;
    }
}
