package com.hyjf.am.trade.controller.admin.trade;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.service.admin.finance.MerchantAccountService;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@RestController
@RequestMapping("/am-admin/config/accountconfig")
public class AdminMerchantAccountController {
    @Autowired
    MerchantAccountService merchantAccountService;
    private static Logger logger = LoggerFactory.getLogger(AdminMerchantAccountController.class);

    /**
     * 分页查询平台设置账户列表
     * @return
     */
    @RequestMapping("/selectMerchantAccountListByPage")
    public MerchantAccountResponse selectMerchantAccountListByPage(@RequestBody AdminMerchantAccountRequest adminRequest) {
        logger.info("平台设置账户列表..." + JSONObject.toJSON(adminRequest));
        MerchantAccountResponse  result=new MerchantAccountResponse();
        //查询平台设置账户列表条数
        int recordTotal = merchantAccountService.getMerchantAccountListCountByPage(adminRequest);
        if (recordTotal > 0) {
            Paginator paginator = new Paginator(adminRequest.getCurrPage(), recordTotal,adminRequest.getPageSize());
            //查询记录
            List<MerchantAccount> recordList =merchantAccountService.getMerchantAccountListByPage(adminRequest,paginator.getOffset(), paginator.getLimit());
            if(!CollectionUtils.isEmpty(recordList)){
                List<MerchantAccountVO> configList = CommonUtils.convertBeanList(recordList, MerchantAccountVO.class);
                for (int i = 0; i < configList.size(); i++) {
                    MerchantAccountVO merchantAccountVO = configList.get(i);
                    Date updatetime = merchantAccountVO.getUpdateTime();
                    int time10 = GetDate.getTime10(updatetime);
                    merchantAccountVO.setUpdatetime(time10);
                }
                result.setResultList(configList);
                result.setRecordTotal(recordTotal);
            }
        }
        return result;
    }

    /**
     * 查询详情页面
     * @return
     */
    @RequestMapping("/searchAccountConfigInfo")
    public MerchantAccountResponse searchAccountConfigInfo(@RequestBody AdminMerchantAccountRequest request) {
        logger.info("平台账户设置详情页面..." + JSONObject.toJSON(request));
        MerchantAccountResponse  result=new MerchantAccountResponse();
        MerchantAccount record = new MerchantAccount();
        // 设置子账户自动转入 默认值:支持
        record.setTransferIntoFlg(1);
        // 设置子账户转出 默认值:支持
        record.setTransferOutFlg(1);
        if (request.getId() != null&&request.getId().intValue()>0) {
            record = merchantAccountService.selectAccountConfigInfo(request.getId());
            MerchantAccountVO recordVo = new MerchantAccountVO();
            if(null != record){
                BeanUtils.copyProperties(record, recordVo);
                result.setResult(recordVo);
            }
        }
        return result;
    }
    /**
     * 添加平台设置账户配置
     * @param adminRequest
     */
    @RequestMapping("/saveAccountConfig")
    public MerchantAccountResponse saveAccountConfig(@RequestBody AdminMerchantAccountRequest adminRequest) {
        MerchantAccountResponse resp = new MerchantAccountResponse();
        // 插入
        int cot = merchantAccountService.saveAccountConfig(adminRequest);
        if(cot > 0 ){
            resp.setRtn(Response.SUCCESS);
        }else{
            resp.setRtn(Response.FAIL);
            resp.setMessage(Response.FAIL_MSG);
        }
        return resp;
    }
    /**
     * 修改平台设置账户配置
     * @param adminRequest
     */
    @RequestMapping("/updateAccountConfig")
    public MerchantAccountResponse updateAccountConfig(@RequestBody AdminMerchantAccountRequest adminRequest) {
        MerchantAccountResponse resp = new MerchantAccountResponse();
        // 插入
        int cot = merchantAccountService.updateAccountConfig(adminRequest);
        if(cot > 0 ){
            resp.setRtn(Response.SUCCESS);
        }else{
            resp.setRtn(Response.FAIL);
            resp.setMessage(Response.FAIL_MSG);
        }
        return resp;
    }
    /**
     * 根据子账户名称检索
     * @return
     */
    @RequestMapping("/countAccountListInfoBySubAccountName")
    public int countAccountListInfoBySubAccountName(@RequestBody HashMap<String,String> map){
        return merchantAccountService.countAccountListInfoBySubAccountName(map);
    }
    /**
     * 根据子账户代号检索
     * @return
     */
    @RequestMapping("/countAccountListInfoBySubAccountCode")
    public int countAccountListInfoBySubAccountCode(@RequestBody HashMap<String,String> map){
        return merchantAccountService.countAccountListInfoBySubAccountCode(map);
    }



}
