/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.bank;

import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.service.BankSettingService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dangzw
 * @version BankSettingController, v0.1 2018/7/24 23:58
 */
@RestController
@RequestMapping("/am-config/banksetting")
public class BankSettingController {

    private static final Logger logger = LoggerFactory.getLogger(BankSettingController.class);

    @Autowired
    private BankSettingService bankSettingService;

    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    /**
     *(条件)列表查询
     * @return
     */
    @RequestMapping("/list")
    public AdminBankSettingResponse selectBankSettingListByPage(@RequestBody AdminBankSettingRequest adminRequest) {
        Integer totalCount = 0;
        List<JxBankConfig> recordList = null;
        JxBankConfig bc = new JxBankConfig();
        bc.setBankName(adminRequest.getBankName());
        bc.setPayAllianceCode(adminRequest.getPayAllianceCode());
        AdminBankSettingResponse response = new AdminBankSettingResponse();

        try {
            // 数据查询
            totalCount = this.bankSettingService.getTotalCount(bc);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", bc.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据查询异常！具体原因详见日志");
            return response;
        }

        response.setRecordTotal(totalCount);
        Paginator paginator = new Paginator(adminRequest.getCurrPage(), totalCount, adminRequest.getPageSize() == 0 ? 10 : adminRequest.getPageSize());

        try {
            // 数据查询
            recordList = this.bankSettingService.getRecordList(bc, paginator.getOffset(), paginator.getLimit());
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", bc.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据查询异常！具体原因详见日志");
            return response;
        }

        if(CollectionUtils.isNotEmpty(recordList)){
            List<JxBankConfigVO> jxBankConfigList = CommonUtils.convertBeanList(recordList, JxBankConfigVO.class);
            response.setPaginator(paginator);
            response.setResultList(jxBankConfigList);
            response.setBanksettingForm(adminRequest);
            String fileDomainUrl = UploadFileUtils.getDoPath(DOMAIN_URL);
            response.setFileDomainUrl(fileDomainUrl);
            return response;
        }
        return response;
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param adminRequest
     * @return
     */
    @RequestMapping("/info")
    public AdminBankSettingResponse bankSettingInfo(@RequestBody AdminBankSettingRequest adminRequest) {
        JxBankConfig jxBankConfigList = null;
        AdminBankSettingResponse  response = new AdminBankSettingResponse();

        try {
            // 数据查询
            jxBankConfigList = bankSettingService.bankSettingInfo(adminRequest);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", adminRequest.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据查询异常！具体原因详见日志");
            return response;
        }

        if(jxBankConfigList != null){
            JxBankConfigVO jxBankConfigVO = CommonUtils.convertBean(jxBankConfigList, JxBankConfigVO.class);
            response.setResult(jxBankConfigVO);
        }
        return response;
    }

    /**
     *(条件)列表查询
     * @return
     */
    @RequestMapping("/searchForInsert")
    public AdminBankSettingResponse searchForInsert(@RequestBody AdminBankSettingRequest adminRequest) {
        List<JxBankConfig> recordList = null;
        JxBankConfig bc = new JxBankConfig();
        bc.setBankName(adminRequest.getBankName());
        AdminBankSettingResponse response = new AdminBankSettingResponse();

        try {
            // 数据查询
            recordList = this.bankSettingService.getRecordList(bc, -1, -1);
        } catch (Exception e) {
            logger.info("Admin江西银行数据查询异常！requestParam:{}", adminRequest.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据查询异常！具体原因详见日志");
            return response;
        }

        if (CollectionUtils.isNotEmpty(recordList)){
            List<JxBankConfigVO> jxBankConfigVO = CommonUtils.convertBeanList(recordList, JxBankConfigVO.class);
            response.setResultList(jxBankConfigVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 添加
     * @param adminRequest
     */
    @RequestMapping("/insert")
    public AdminBankSettingResponse insertBankSetting(@RequestBody AdminBankSettingRequest adminRequest) {
        int result = 0;
        AdminBankSettingResponse response = new AdminBankSettingResponse();

        try {
            // 数据添加
            result = this.bankSettingService.insertBankSetting(adminRequest);
        } catch (Exception e) {
            logger.info("Admin江西银行数据添加异常！requestParam:{}", adminRequest.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据添加异常！具体原因详见日志");
            return response;
        }

        if(result <= 0 ){
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据添加异常！具体原因详见日志");
        }
        return response;
    }

    /**
     * 修改 江西银行 银行卡配置
     * @param adminRequest
     * @return
     */
    @RequestMapping("/update")
    public AdminBankSettingResponse updateBankSetting(@RequestBody AdminBankSettingRequest adminRequest) {
        int result = 0;
        AdminBankSettingResponse  response = new AdminBankSettingResponse();

        try {
            // 数据修改
            result = this.bankSettingService.updateBankSetting(adminRequest);
        } catch (Exception e) {
            logger.info("Admin江西银行数据修改异常！requestParam:{}", adminRequest.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据修改异常！具体原因详见日志");
            return response;
        }

        if(result <= 0 ){
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }
        return response;
    }

    /**
     * 删除 江西银行 银行卡配置
     * @param adminRequest
     * @return
     */
    @RequestMapping("/delete")
    public AdminBankSettingResponse deleteFeeConfig(@RequestBody AdminBankSettingRequest adminRequest) {
        AdminBankSettingResponse response = new AdminBankSettingResponse();
        if(adminRequest.getId() == null){
            response.setRtn(Response.FAIL);
            response.setMessage("id字段为必传！");
            return response;
        }
        try {
            // 数据删除
            bankSettingService.deleteFeeConfig(adminRequest.getId());
        } catch (Exception e) {
            logger.info("Admin江西银行数据删除异常！requestParam:{}", adminRequest.toString(), e);
            response.setRtn(Response.FAIL);
            response.setMessage("Admin江西银行数据删除异常！具体原因详见日志");
            return response;
        }
        return response;
    }
}
