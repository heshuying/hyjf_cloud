package com.hyjf.am.trade.controller.admin.productcenter.hjhcredit;

import com.hyjf.am.common.ConvertUtils;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.admin.AdminHjhDebtCreditCustomize;
import com.hyjf.am.trade.service.admin.productcenter.hjhcredit.AdminHjhDebtCreditService;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/4
 * @Description:    后台列表查询汇计划-转让列表
 */
@Api(value = "汇计划-转让列表")
@RestController
@RequestMapping("/am-trade/adminHjhDebtCredit")
public class AdminHjhDebtCreditController extends BaseController{

    @Autowired
    private AdminHjhDebtCreditService adminHjhDebtCreditService;

    /**
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "转让列表查询总件数")
    @PostMapping("/getListTotal")
    public Integer getListTotal(HjhDebtCreditListRequest request) {
        Map map = ConvertUtils.convertObjectToMap(request);
        Integer count = adminHjhDebtCreditService.getListTotal(map);
        return count;
    }

    @ApiOperation(value = "转让列表查询")
    @PostMapping("/getList")
    public HjhDebtCreditReponse getList(HjhDebtCreditListRequest request){

        HjhDebtCreditReponse reponse = new HjhDebtCreditReponse();
        Integer total = getListTotal(request);
        Paginator paginator = new Paginator(request.getCurrPage(), total,request.getPageSize());
        if(request.getPageSize() ==0){
            paginator = new Paginator(request.getCurrPage(), total);
        }
        int limitStart = paginator.getOffset();
        int limitEnd = paginator.getLimit();

        if(request.getCurrPage() == -1){
            limitStart = -1;
            limitEnd = -1;
        }
        Map map = ConvertUtils.convertObjectToMap(request);
        List<AdminHjhDebtCreditCustomize> list =  adminHjhDebtCreditService.getList(map,limitStart,limitEnd);
        List<HjhDebtCreditVo> voList = null;
        if(!CollectionUtils.isEmpty(list)){
            voList = CommonUtils.convertBeanList(list, HjhDebtCreditVo.class);
        }
        reponse.setRecordTotal(total);
        reponse.setResultList(voList);
        return reponse;
    }


}
