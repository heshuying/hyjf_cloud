package com.hyjf.am.trade.controller.front.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.FinmanChargeNewResponse;
import com.hyjf.am.resquest.admin.FinmanChargeNewRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowFinmanNewCharge;
import com.hyjf.am.trade.service.front.config.FinmanChargeNewService;
import com.hyjf.am.vo.trade.borrow.BorrowFinmanNewChargeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiehuili on 2018/8/14.
 */
@RestController
@RequestMapping("/am-admin/config/finmanchargenew")
public class FinmanChargeNewController extends BaseController {
    @Autowired
    private FinmanChargeNewService finmanChargeNewService;
    /**
     * 分页费率配置 列表
     * @author xiehuili
     * @return
     */
    @RequestMapping("/list")
    public FinmanChargeNewResponse selectFinmanChargeNewList(@RequestBody FinmanChargeNewRequest adminRequest) {
        logger.info("费率配置 列表..." + JSONObject.toJSON(adminRequest));
        FinmanChargeNewResponse response =new FinmanChargeNewResponse();
        int total = this.finmanChargeNewService.countRecordTotal(adminRequest);
        if(total > 0){
            Paginator paginator = new Paginator(adminRequest.getCurrPage(),total, adminRequest.getPageSize() == 0?10:adminRequest.getPageSize());
            List<BorrowFinmanNewChargeVO> recordList =
                    this.finmanChargeNewService.getRecordList(adminRequest,paginator.getOffset(),paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                response=new FinmanChargeNewResponse();
                response.setResultList(recordList);
                response.setRecordTotal(total);
            }
        }
        return response;
    }

    /**
     * 费率配置 详情
     *  @author xiehuili
     * @return
     */
    @RequestMapping("/getRecordInfo/{manChargeCd}")
    public FinmanChargeNewResponse getRecordInfo(@PathVariable String manChargeCd) {
        logger.info("费率配置 详情..." + JSONObject.toJSON(manChargeCd));
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        if (StringUtils.isNotBlank(manChargeCd)) {
            BorrowFinmanNewCharge charge = this.finmanChargeNewService.getRecordInfo(manChargeCd);
            if (null != charge) {
                BorrowFinmanNewChargeVO chargeVO = CommonUtils.convertBean(charge, BorrowFinmanNewChargeVO.class);
                response.setResult(chargeVO);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 添加费率配置
     *  @author xiehuili
     * @return
     */
    @RequestMapping("/insert")
    public FinmanChargeNewResponse insertFinmanChargeNew(@RequestBody FinmanChargeNewRequest adminRequest) {
        logger.info("费率配置 添加..." + JSONObject.toJSON(adminRequest));
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        String borrowClass = getBorrowClass(adminRequest.getProjectType());
        if(StringUtils.isBlank(borrowClass)){
            logger.debug("标识符是："+adminRequest.getProjectType()+"的项目类型不存在。");
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        adminRequest.setProjectType(borrowClass);
        int count = this.finmanChargeNewService.insertFinmanChargeNew(adminRequest);
        if (count > 0) {
            response.setRtn(Response.SUCCESS);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 修改费率配置
     *  @author xiehuili
     * @return
     */
    @RequestMapping("/update")
    public FinmanChargeNewResponse updateFinmanChargeNew(@RequestBody FinmanChargeNewRequest adminRequest) {
        logger.info("费率配置 修改..." + JSONObject.toJSON(adminRequest));
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        String borrowClass = getBorrowClass(adminRequest.getProjectType());
        if(StringUtils.isBlank(borrowClass)){
            logger.debug("标识符是："+adminRequest.getProjectType()+"的项目类型不存在。");
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
            return response;
        }
        adminRequest.setProjectType(borrowClass);
        int count = this.finmanChargeNewService.updateFinmanChargeNew(adminRequest);
        if (count > 0) {
            response.setRtn(Response.SUCCESS);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 删除费率配置
     *  @author xiehuili
     * @return
     */
    @RequestMapping("/delete")
    public FinmanChargeNewResponse deleteFinmanChargeNew(@RequestBody FinmanChargeNewRequest adminRequest) {
        logger.info("费率配置 删除..." + JSONObject.toJSON(adminRequest));
        FinmanChargeNewResponse response = new FinmanChargeNewResponse();
        int count = this.finmanChargeNewService.deleteFinmanChargeNew(adminRequest);
        if (count > 0) {
            response.setRtn(Response.SUCCESS);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    @RequestMapping("/countRecordByProjectType")
    public int countRecordByProjectType(@RequestBody  FinmanChargeNewRequest adminRequest){
        return finmanChargeNewService.countRecordByProjectType(adminRequest);
    }

    /**
     *
     * 根据borrowCd查询borrowClass
     * @author xiehuili
     * @return
     */
    public String getBorrowClass(String borrowCd){
        return finmanChargeNewService.getBorrowClass(borrowCd);
    }

}