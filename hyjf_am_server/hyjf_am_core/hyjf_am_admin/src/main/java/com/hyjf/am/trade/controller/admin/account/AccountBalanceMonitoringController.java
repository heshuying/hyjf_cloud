package com.hyjf.am.trade.controller.admin.account;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAccountBalanceMonitoringResponse;
import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.service.admin.account.AccountBalanceMonitoringService;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@RestController
@RequestMapping("/am-admin/config/accountbalance")
public class AccountBalanceMonitoringController {

    private static Logger logger = LoggerFactory.getLogger(AdminAccountDetailController.class);
    @Autowired
    private AccountBalanceMonitoringService accountBalanceMonitoringService;
    /**
     * 分页查询配置中心平台账户配置 余额监控
     * @return
     */
    @RequestMapping("/selectAccountBalanceMonitoringByPage")
    public AdminAccountBalanceMonitoringResponse selectAccountBalanceMonitoringByPage(@RequestBody AdminAccountBalanceMonitoringRequest adminRequest) {
        logger.info("平台账户配置 余额监控列表..." + JSONObject.toJSON(adminRequest));
        AdminAccountBalanceMonitoringResponse  response =new AdminAccountBalanceMonitoringResponse();
        //查询平台账户配置 余额监控条数
        int recordTotal = this.accountBalanceMonitoringService.getAccountBalanceMonitoringCount(adminRequest);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordTotal,adminRequest.getPageSize() ==0?10:adminRequest.getPageSize());
            //查询平台账户配置 余额监控记录
            List<MerchantAccount> recordList =accountBalanceMonitoringService.getAccountBalanceMonitoringByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<MerchantAccountVO> merchantAccountVOList = CommonUtils.convertBeanList(recordList, MerchantAccountVO.class);
                merchantAccountVOList=this.forback(merchantAccountVOList);
                response.setResultList(merchantAccountVOList);
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 配置中心平台账户配置 余额监控详情页面
     * @return
     */
    @RequestMapping("/selectaccountBalanceMonitoringById")
    public AdminAccountBalanceMonitoringResponse selectAccountBalanceMonitoringById(@RequestBody AdminAccountBalanceMonitoringRequest adminRequest) {
        logger.info("平台账户配置 余额监控详情页面..." + JSONObject.toJSON(adminRequest));
        AdminAccountBalanceMonitoringResponse  response = new AdminAccountBalanceMonitoringResponse();
        try{
            List<MerchantAccount> merchantAccounts= accountBalanceMonitoringService.selectAccountBalanceMonitoringById(adminRequest);
            if(!CollectionUtils.isEmpty(merchantAccounts)){
                List<MerchantAccountVO> merchantAccountVOList = CommonUtils.convertBeanList(merchantAccounts, MerchantAccountVO.class);
//                merchantAccountVOList=this.forback(merchantAccountVOList);
                response.setResultList(merchantAccountVOList);
                response.setRecordTotal(merchantAccountVOList.size());
                response.setRtn(Response.SUCCESS);
                return response;
            }
            response.setRtn(Response.SUCCESS);
            response.setMessage("查询到的数据为空！");
            return response;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return response;
    }

    /**
     * 修改配置中心平台账户配置 余额监控
     * @param adminRequest
     */
    @RequestMapping("/updateMerchantAccountList")
    public AdminAccountBalanceMonitoringResponse updateMerchantAccountList(@RequestBody List<AdminAccountBalanceMonitoringRequest> adminRequest) {
        AdminAccountBalanceMonitoringResponse resp = new AdminAccountBalanceMonitoringResponse();
        try{
            // 修改
            int cot = this.accountBalanceMonitoringService.updateMerchantAccountList(adminRequest);
            if(cot > 0 ){
                resp.setRtn(Response.SUCCESS);
            }else{
                resp.setMessage("修改失败！");
                resp.setRtn(Response.FAIL);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return resp;
    }

    /**
     *
     * 画面项目反馈
     * @param recordList
     * @return
     */
    private List<MerchantAccountVO> forback(List<MerchantAccountVO> recordList) {
        List<MerchantAccountVO> result = new ArrayList<MerchantAccountVO>();
        for (int i = 0; i < recordList.size(); i++) {
            MerchantAccountVO record = new MerchantAccountVO();
            BeanUtils.copyProperties(recordList.get(i), record);
//            record.setIds(String.valueOf(recordList.get(i).getId()));
            result.add(record);
        }
        return result;
    }

}
