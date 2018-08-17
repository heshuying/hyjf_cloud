package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.*;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.BankConfigResponse;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.trade.HolidaysConfigResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.config.*;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpVO;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.BanksConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmConfigClientImpl, v0.1 2018/4/23 20:01
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用loginUserId去am-config查询登录的用户信息
     *
     * @param loginUserId 登录用户id
     * @return
     * @auth sunpeikai
     */
    @Override
    public AdminSystemVO getUserInfoById(Integer loginUserId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        AdminSystemResponse response = restTemplate.getForEntity(url, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        String url = "http://AM-CONFIG/am-config/config/getParamNameList/" + nameClass;
        ParamNameResponse response = restTemplate.getForEntity(url, ParamNameResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ParamNameVO> getParamName(String other1) {
        String url = "http://AM-CONFIG/am-config/config/getParamName/" + other1;
        ParamNameResponse response = restTemplate.getForEntity(url, ParamNameResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询邮件配置
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public SiteSettingsVO findSiteSetting() {

        SiteSettingsResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/siteSettings/findOne/", SiteSettingsResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询邮件模板
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {
        SmsMailTemplateResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findSmsMailByCode/" + mailCode,
                        SmsMailTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据银行错误码查询错误信息
     *
     * @param retCode 错误码
     * @return
     * @auth sunpeikai
     */
    @Override
    public String getBankRetMsg(String retCode) {
        BankReturnCodeConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/config/getBankReturnCodeConfig/" + retCode,
                        BankReturnCodeConfigResponse.class)
                .getBody();
        if (response != null) {
            BankReturnCodeConfigVO vo = response.getResult();
            if (vo == null) {
                return Response.ERROR_MSG;
            }
            return StringUtils.isNotBlank(vo.getRetMsg()) ? vo.getRetMsg() : Response.ERROR_MSG;
        }
        return Response.ERROR_MSG;
    }

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
        String url = "http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/" + retCode;
        BankReturnCodeConfigResponse response = restTemplate.getForEntity(url, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取银行列表
     *
     * @return
     * @author nixiaoling
     */
    @Override
    public List<BankConfigVO> selectBankConfigList() {
        BankConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/selectBankConfigList", BankConfigResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据银行卡号获取bankId
     *
     * @param cardNo
     * @return
     * @auther: nxl
     */
    @Override
    public String queryBankIdByCardNo(String cardNo) {
        String result = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
        return result;
    }

    /**
     * 根据bankId查找江西银行的银行卡配置表
     *
     * @param bankId
     * @return
     * @auther: nxl
     */
    @Override
    public JxBankConfigResponse getJXbankConfigByBankId(int bankId) {
        JxBankConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getJXbankConfigByBankId/" + bankId, JxBankConfigResponse.class).getBody();
        return response;
    }

    /**
     * 合作机构
     *
     * @return
     */
    @Override
    public LinkResponse getLinks() {
        LinkResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/content/contentlinks/getLinks", LinkResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return new LinkResponse();
    }

    /**
     * 子账户类型 查询
     */
    @Override
    public ParamNameResponse getNameCd(String code) {
        ParamNameResponse amResponse = restTemplate.getForEntity("http://AM-CONFIG/am-config/accountconfig/getNameCd/" + "FLOW_STATUS", ParamNameResponse.class)
                .getBody();
        if (amResponse != null && Response.SUCCESS.equals(amResponse.getRtn())) {
            return amResponse;
        }
        return null;
    }

    /**
     * 项目申请人是否存在
     *
     * @param applicant
     * @return
     */
    @Override
    public AdminSystemResponse isExistsApplicant(String applicant) {
        AdminSystemResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/adminSystem/isexistsapplicant/" + applicant, AdminSystemResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MspApplytResponse getRecordList(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/init", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse infoAction() {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/infoAction", null,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse insertAction(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/insertAction", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse updateAction(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/insertAction", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse deleteRecordAction(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/deleteRecordAction", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse validateBeforeAction(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/validateBeforeAction", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse applyInfo(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/applyInfo", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse shareUser(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/shareUser", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspApplytResponse download(MspApplytRequest mspApplytRequest) {
        MspApplytResponse mspApplytResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapply/download", mspApplytRequest,
                        MspApplytResponse.class)
                .getBody();
        if (mspApplytResponse != null) {
            return mspApplytResponse;
        }
        return null;
    }

    @Override
    public MspResponse searchAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/searchAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse infoAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/infoAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse insertAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/searchAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse updateAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/insertAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse configureNameError(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/configureNameError", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse deleteAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/deleteAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public MspResponse checkAction(MspRequest mspRequest) {
        MspResponse mspResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-user/mspapplyconfigure/checkAction", mspRequest,
                        MspResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request) {
        AdminUtmReadPermissionsResponse response = restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/searchaction",
                request, AdminUtmReadPermissionsResponse.class);
        return response;

    }

    @Override
    public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/insert",
                requestBean, AdminUtmReadPermissionsResponse.class);
    }

    @Override
    public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/update",
                requestBean, AdminUtmReadPermissionsResponse.class);
    }


    @Override
    public AdminUtmReadPermissionsResponse deleteById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/delete/" + id,
                AdminUtmReadPermissionsResponse.class);
    }

    @Override
    public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
        AdminUtmReadPermissionsResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/getadminutmreadpermissions/" + userId,
                AdminUtmReadPermissionsResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form) {
//		return restTemplate.postForObject("http://AM-CONFIG/am-config/extensioncenter/adminutmreadpermissions/getreconciliationpage",form,
//				AppChannelReconciliationResponse.class);
        return null;
    }

    @Override
    public AppBorrowImageResponse searchList(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/getRecordList", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse searchInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/infoAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse insertInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/insertAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse updateInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/updateAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    @Override
    public AppBorrowImageResponse deleteInfo(AppBorrowImageRequest appBorrowImageRequest) {
        AppBorrowImageResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appborrow/deleteAction", appBorrowImageRequest,
                        AppBorrowImageResponse.class)
                .getBody();
        return response;
    }

    /**
     * 查询银行配置列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest) {
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/selectBankConfigListByPage", adminRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 根据id查询银行配置
     *
     * @param bankId
     * @return
     */
    @Override
    public AdminBankConfigResponse selectBankConfigById(Integer bankId) {
        AdminBankConfigResponse res = new AdminBankConfigResponse();
        com.hyjf.am.response.config.BankConfigResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/config/getBankConfigByBankId/" + bankId, com.hyjf.am.response.config.BankConfigResponse.class).getBody();
        if (response != null) {
            BankConfigVO vo = response.getResult();
            if (vo != null) {
                res.setResult(vo);
                res.setRtn(Response.SUCCESS);
                return res;
            }
            return null;
        }
        return null;
    }

    /**
     * 根据银行名称查询银行配置
     *
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(String bankName) {
        AdminBankConfigResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/selectBankConfigByBankName", bankName, AdminBankConfigResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 添加银行配置
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest) {
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/insertBankConfig", adminRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 修改银行配置
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest) {
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/updadteBankConfig", adminRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 删除银行配置
     *
     * @param id
     * @return
     */
    @Override
    public AdminBankConfigResponse deleteBankConfigById(Integer id) {
        AdminBankConfigResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/deleteBankConfigById", id, AdminBankConfigResponse.class).getBody();
        return response;
    }

    /**
     * 上传文件
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public AdminBankConfigResponse uploadFile(HttpServletRequest request, HttpServletResponse response) {
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/deleteBankConfigById", request, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 保存之前的去重校验
     *
     * @param adminBankConfigRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest) {
        return restTemplate
                .postForEntity("http://AM-CONFIG/am-config/config/validateFeildBeforeSave", adminBankConfigRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 查询配置中心接口切换列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/list", adminRequest, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 查询配置中心接口切换详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInfo(BankInterfaceRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/info", adminRequest, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 修改 接口切换
     *
     * @return
     */
    @Override
    public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/update", req, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 删除快捷充值限额
     *
     * @return
     */
    @Override
    public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/delete", req, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 查询快捷充值限额列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse bankRechargeInit(AdminBankRechargeConfigRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/list", adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 查询快捷充值限额详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse selectBankRechargeConfigInfo(AdminBankRechargeConfigRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/info", adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 编辑保存快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse saveBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/insert", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 修改快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse updateBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/update", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 删除快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse deleteBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/delete", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 查询快捷充值限额列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public List<BankRechargeLimitConfigVO> exportRecordList(BankRechargeLimitConfigVO adminRequest) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/exportRecordList", adminRequest, List.class)
                .getBody();
    }

    /**
     * 获取银行列表(快捷支付卡)
     *
     * @return
     */
    @Override
    public List<BankConfigVO> getBankRecordList() {
        BankConfigVO bank = new BankConfigVO();
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/getBankRecordListByQuickPayment", bank, List.class)
                .getBody();
    }

    /**
     * 查询返回码配置列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/list";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url, adminRequest, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询返回码配置详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(AdminBankRetcodeConfigRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/info";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url, adminRequest, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 编辑保存返回码配置
     *
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/insert";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url, req, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改返回码配置
     *
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse updateBankReturnCodeConfig(AdminBankRetcodeConfigRequest req) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/update";
        BankReturnCodeConfigResponse response = restTemplate.postForEntity(url, req, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据主键判断维护中数据是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsReturnCode(AdminBankRetcodeConfigRequest record) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/isExistsReturnCode";
        return restTemplate.postForEntity(url, record, boolean.class).getBody();
    }

    /**
     * 根据条件判断该条数据在数据库中是否存在
     *
     * @param adminRequest
     * @return
     */
    @Override
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/config/bankretcodeconfig/isExistsRecord";
        return restTemplate.postForEntity(url, adminRequest, boolean.class).getBody();
    }

    /**
     * （条件）列表查询
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/list",
                request, AdminBankSettingResponse.class);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse getRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/info",
                request, AdminBankSettingResponse.class);
    }

    /**
     * （条件）数据查询
     *
     * @param bank
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<JxBankConfigVO> getRecordList(JxBankConfigVO bank, int limitStart, int limitEnd) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/searchForInsert",
                bank, List.class);
    }

    /**
     * 添加
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/insert",
                request, AdminBankSettingResponse.class);
    }

    /**
     * 修改 江西银行 银行卡配置
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse updateRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/update",
                request, AdminBankSettingResponse.class);
    }

    /**
     * 删除 江西银行 银行卡配置
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse deleteRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/banksetting/delete",
                request, AdminBankSettingResponse.class);
    }

    /**
     * 江西银行 资料上传
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse uploadFile(HttpServletRequest request) {
        return null;
    }

    /**
     * （条件）列表查询--其他相关字段
     *
     * @return
     */
    @Override
    public List<ParamNameVO> selectProjectTypeParamList() {
        List<ParamNameVO> paramNameVOS = new ArrayList<>();
        ParamNameResponse amResponse = restTemplate.postForEntity("http://AM-CONFIG/am-config/accountconfig/selectProjectTypeParamList", null, ParamNameResponse.class)
                .getBody();
        if (Response.isSuccess(amResponse)) {
            paramNameVOS = amResponse.getResultList();
            return paramNameVOS;
        }
        return null;
    }

    @Override
    public ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/content/contentarticle/searchaction",
                contentArticleRequestBean, ContentArticleResponse.class).getBody();

    }

    @Override
    public ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentarticle/insertaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse findById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentarticle/findbyId/" + id, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentarticle/updateaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse deleteContentArticleById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentarticle/delete/" + id, ContentArticleResponse.class);
    }

    @Override
    public CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/searchaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/changesubtype",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse infoTypeAction(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/infotypeaction/" + id,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/infosubtypeaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/insertcategory",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updateaction",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getCategoryCount(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/getcategorycount",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer getCountByPcateIdAndcateId(Integer pid, Integer cid) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/getbypcateidAandcateid/" + pid + "/" + cid,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(Integer pid, Integer cid) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/getlistbypcateidandcateid/" + pid + "/" + cid,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getRecordList();
        }
        return null;
    }

    @Override
    public Integer delContentHelp(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/delcontenthelp/" + id,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer delCategory(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/delcategory/" + id,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updatecontenthelp", contentHelpVO,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/gethelppage", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/gethelpinfo", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/inserthelpinfo", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/updatehelpaction", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/content/help/chancontenthelp/" + contentId + "/" + status + "/" + zhiChiStatus,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/help/getofteninitpage", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/searchaction",
                requestBean, ContentEnvironmentResponse.class);
    }

    @Override
    public ContentEnvironmentResponse insertAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/insertaction",
                requestBean, ContentEnvironmentResponse.class);
    }

    @Override
    public ContentEnvironmentResponse updateAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/updateaction",
                requestBean, ContentEnvironmentResponse.class);
    }

    @Override
    public ContentEnvironmentVO getRecord(Integer id) {
        ContentEnvironmentResponse response = restTemplate.getForObject(
                "http://AM-CONFIG/am-config/content/contentenvironment/getrecord/" + id,
                ContentEnvironmentResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ContentEnvironmentResponse deleteContentEnvironmentById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentenvironment/delete/" + id,
                ContentEnvironmentResponse.class);
    }

    @Override
    public LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/searchaction",
                requestBean, LandingPageResponse.class);
    }

    @Override
    public LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/insert",
                requestBean, LandingPageResponse.class);
    }

    @Override
    public LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/update",
                requestBean, LandingPageResponse.class);
    }


    @Override
    public LandingPageResponse deleteLandingPageById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentenvironment/delete/" + id,
                LandingPageResponse.class);
    }

    @Override
    public LinkResponse searchAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/searchaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkResponse insertAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/insertaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkResponse updateAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentpartner/updateaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkVO getLinkRecord(Integer id) {
        LinkResponse response = restTemplate.getForObject(
                "http://AM-CONFIG/am-config/content/contentpartner/getrecord/" + id, LinkResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public LinkResponse deleteLinkById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentpartner/delete/" + id,
                LinkResponse.class);
    }

    @Override
    public ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG//am-config/content/contentqualify/searchaction",
                requestBean, ContentQualifyResponse.class);
    }

    @Override
    public ContentQualifyResponse insertAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentqualify/insertaction", requestBean,
                ContentQualifyResponse.class);
    }

    @Override
    public ContentQualifyResponse updateAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentqualify/updateaction", requestBean,
                ContentQualifyResponse.class);
    }

    @Override
    public ContentQualifyVO getContentQualifyRecord(Integer id) {
        ContentQualifyResponse response = restTemplate.getForObject(
                "http://AM-CONFIG/am-config/content/contentqualify/getrecord/" + id, ContentQualifyResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ContentQualifyResponse deleteContentQualifyById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentqualify/delete/" + id,
                ContentQualifyResponse.class);
    }

    @Override
    public CouponCheckResponse getCouponList(AdminCouponCheckRequest adminCouponCheckRequest) {
        String url = "http://AM-CONFIG/am-config/checkList/getCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, adminCouponCheckRequest, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr) {
        String url = "http://AM-CONFIG/am-config/checkList/deleteCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, acr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse insert(AdminCouponCheckRequest accr) {
        String url = "http://AM-CONFIG/am-config/checkList/insertCoupon";
        CouponCheckResponse response = restTemplate.postForEntity(url, accr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckVO selectCoupon(Integer id) {
        String url = "http://AM-CONFIG/am-config/checkList/selectCoupon/" + id;
        CouponCheckVO couponCheckVO = restTemplate.getForEntity(url, CouponCheckVO.class).getBody();
        if (couponCheckVO != null) {
            return couponCheckVO;
        }
        return null;
    }

    @Override
    public CouponCheckResponse updateCoupon(AdminCouponCheckRequest request) {
        String url = "http://AM-CONFIG/am-config/checkList/updateCoupon";
        return restTemplate.postForEntity(url, request, CouponCheckResponse.class).getBody();
    }

    @Override
    public CouponTenderResponse getAdminUserByUserId(String userId) {
        String url = "http://AM-CONFIG/am-config/adminSystem/hztgetusername/" + userId;
        CouponTenderResponse response = restTemplate.getForEntity(url, CouponTenderResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public EventResponse searchAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/searchaction",
                requestBean, EventResponse.class);
    }

    @Override
    public EventResponse insertAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/insertaction",
                requestBean, EventResponse.class);
    }

    @Override
    public EventResponse updateAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentevent/updateaction",
                requestBean, EventResponse.class);
    }

    @Override
    public EventVO getEventRecord(Integer id) {
        EventResponse response = restTemplate.getForObject(
                "http://AM-CONFIG/am-config/content/contentevent/getrecord/" + id,
                EventResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public EventResponse deleteEventById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentevent/delete/" + id,
                EventResponse.class);
    }

    /**
     * 查询手续费配置列表
     *
     * @param request
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeConfig/list",
                request, AdminFeeConfigResponse.class);
    }

    /**
     * 查询银行列表
     *
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigList(BankConfigVO bank) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/config/getBankConfigListByStatus",
                bank, List.class);
    }

    /**
     * 获取手续费列表列表
     *
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank, int limitStart, int limitEnd) {
        //查詢所有
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/selectBankConfigByBankName",
                bank.getName(), List.class);
    }

    /**
     * 查询手续费配置详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/info",
                adminRequest, AdminFeeConfigResponse.class);
    }

    /**
     * 编辑保存手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/insert",
                req, AdminFeeConfigResponse.class);
    }

    /**
     * 修改手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/update",
                req, AdminFeeConfigResponse.class);
    }

    /**
     * 删除手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/feeconfig/delete",
                req, AdminFeeConfigResponse.class);
    }

    /**
     * 查询列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public HolidaysConfigResponse initHolidaysConfig(AdminHolidaysConfigRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/holidaysConfig/list";
        HolidaysConfigResponse response = restTemplate.postForEntity(url, adminRequest, HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询节假日配置详情页面
     *
     * @param id
     * @return
     */
    @Override
    public HolidaysConfigResponse getHolidaysConfigById(Integer id) {
        String url = "http://AM-CONFIG/am-config/holidaysConfig/info/" + id;
        HolidaysConfigResponse response = restTemplate.getForEntity(url, HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 编辑保存节假日配置
     *
     * @return
     */
    @Override
    public HolidaysConfigResponse saveHolidaysConfig(AdminHolidaysConfigRequest req) {
        String url = "http://AM-CONFIG/am-config/holidaysConfig/insert";
        HolidaysConfigResponse response = restTemplate.postForEntity(url, req, HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改节假日配置
     *
     * @return
     */
    @Override
    public HolidaysConfigResponse updateHolidaysConfig(AdminHolidaysConfigRequest req) {
        String url = "http://AM-CONFIG/am-config/holidaysConfig/update";
        HolidaysConfigResponse response = restTemplate.postForEntity(url, req, HolidaysConfigResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public List<AdminSystemVO> getUserPermission(String userName) {
        AdminSystemResponse adminSystemResponse = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/adminSystem/getpermissions/" + userName,
                        AdminSystemResponse.class)
                .getBody();
        if (adminSystemResponse != null) {
            return adminSystemResponse.getResultList();
        }
        return null;

    }

    @Override
    public AdminSystemResponse getUserInfo(AdminSystemRequest adminSystemRequest) {
        AdminSystemResponse adminSystemResponse = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/adminSystem/getuser", adminSystemRequest,
                        AdminSystemResponse.class)
                .getBody();
        if (adminSystemResponse != null) {
            return adminSystemResponse;
        }
        return null;
    }

    @Override
    public List<TreeVO> selectLeftMenuTree2(String userId) {
        TreeResponse treeResponse = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/adminSystem/selectLeftMenuTree/" + userId, TreeResponse.class)
                .getBody();
        if (treeResponse != null) {
            return treeResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<SmsMailTemplateVO> findMailAll() {
        SmsMailTemplateResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findAll", SmsMailTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<SmsMailTemplateVO> findMailTemplate(MailTemplateRequest request) {
        SmsMailTemplateResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/findMailTemplate", request,
                        SmsMailTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void insertMailTemplate(MailTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/insertMailTemplate", request,
                Object.class);
    }

    @Override
    public void updateMailTemplate(MailTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/update_mail_template", request,
                Object.class);
    }

    @Override
    public List<MessagePushTemplateVO> findAll() {
        MessagePushTemplateResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/messagePushTemplate/getAllTemplates",
                        MessagePushTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<MessagePushTemplateVO> findMsgPushTemplate(MsgPushTemplateRequest request) {
        MessagePushTemplateResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/messagePushTemplate/findMsgPushTemplate", request,
                        MessagePushTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-config/messagePushTemplate/insertMsgPushTemplate", request,
                Object.class);
    }

    @Override
    public MessagePushTagVO findMsgTagByTagName(String tagName) {
        MessagePushTagVO result = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/messagePushTemplate/findMsgTagByTagName/" + tagName,
                        MessagePushTagVO.class)
                .getBody();
        return result;
    }


    @Override
    public SiteSettingsResponse selectSiteSetting() {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/siteSettings/findOne", SiteSettingsResponse.class);
    }

    @Override
    public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/siteSettings/update", requestBean,
                SiteSettingsResponse.class);
    }

    @Override
    public List<SmsTemplateVO> findSmsAll() {
        SmsTemplateResponse response = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/smsTemplate/findAll", SmsTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<SmsTemplateVO> findSmsTemplate(SmsTemplateRequest request) {
        SmsTemplateResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/smsTemplate/findSmsTemplate", request,
                        SmsTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void insertSmsTemplate(SmsTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-conf/smsTemplate/insertTemplate", request, Object.class);
    }

    /**
     * 查询列表数据
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse findSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/getRecordList", form,
                SubmissionsResponse.class);
    }

    /**
     * 查询导出数据
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse exportSubmissionsList(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/getExportRecordList", form,
                SubmissionsResponse.class);
    }

    /**
     * 更新状态
     *
     * @param form
     * @return
     */
    @Override
    public SubmissionsResponse updateSubmissionsStatus(SubmissionsRequest form) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/submission/updateSubmissionsStatus", form,
                SubmissionsResponse.class);
    }

    @Override
    public TeamResponse searchAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/team/searchaction", requestBean,
                TeamResponse.class);
    }

    @Override
    public TeamResponse insertAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/team/insertaction", requestBean,
                TeamResponse.class);
    }

    @Override
    public TeamResponse updateAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/team/updateaction", requestBean,
                TeamResponse.class);
    }

    @Override
    public TeamVO getTeamRecord(Integer id) {
        TeamResponse response = restTemplate.getForObject("http//AM-CONFIG/am-config/team/getrecord/" + id,
                TeamResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public TeamResponse deleteTeamById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/team/delete/" + id, TeamResponse.class);
    }

    /**
     * 查询版本设置列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/list";
        AdminVersionResponse response = restTemplate.postForEntity(url, adminRequest, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse searchVersionConfigInfo(AdminVersionRequest adminRequest) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/info";
        AdminVersionResponse response = restTemplate.postForEntity(url, adminRequest, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 编辑保存版本设置
     *
     * @return
     */
    @Override
    public AdminVersionResponse saveVersionConfig(AdminVersionRequest req) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/insert";
        AdminVersionResponse response = restTemplate.postForEntity(url, req, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 修改版本设置
     *
     * @return
     */
    @Override
    public AdminVersionResponse updateVersionConfig(AdminVersionRequest req) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/update";
        AdminVersionResponse response = restTemplate.postForEntity(url, req, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 删除保证金配置
     *
     * @return
     */
    @Override
    public AdminVersionResponse deleteVersionConfig(Integer id) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/delete";
        AdminVersionResponse response = restTemplate.postForEntity(url, id, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 校验版本号是否唯一
     *
     * @return
     */
    @Override
    public com.hyjf.am.vo.admin.VersionVO getVersionByCode(Integer vid, Integer type, String version) {
        String url = "http://AM-CONFIG/am-config/config/versionconfig/validationFeild";
        Map<String, Object> map = new HashMap<String, Object>();
        if (vid != null) {
            map.put("vid", vid);
        }
        map.put("type", type);
        map.put("version", version);
        com.hyjf.am.vo.admin.VersionVO response = restTemplate.postForEntity(url, map, VersionVO.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public VersionConfigBeanResponse searchList(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/getRecordList", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;
    }

    @Override
    public VersionConfigBeanResponse searchInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/infoAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse insertInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/insertAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse updateInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/updateAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;

    }

    @Override
    public VersionConfigBeanResponse deleteInfo(VersionConfigBeanRequest request) {
        VersionConfigBeanResponse response = restTemplate
                .postForEntity("http://AM-CONFIG/am-config/appversion/deleteAction", request,
                        VersionConfigBeanResponse.class)
                .getBody();
        return response;
    }


    /**
     * 获取所有问题
     *
     * @return
     */
    @Override
    public QuestionResponse getAllQuestion() {
        QuestionResponse mspResponse = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/quesiontAndAnswer/findAllQuestion", QuestionResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    /**
     * 获取所有回答
     *
     * @return
     */
    @Override
    public AnswerResponse getAllAnswer() {
        AnswerResponse mspResponse = restTemplate
                .getForEntity("http://AM-CONFIG/am-config/quesiontAndAnswer/findAllAnswer", AnswerResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public JobResponse searchAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/searchaction", requestBean,
                JobResponse.class);
    }

    @Override
    public JobResponse insertAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/insertaction", requestBean,
                JobResponse.class);
    }

    @Override
    public JobResponse updateAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/insertaction", requestBean,
                JobResponse.class);
    }

    @Override
    public JobsVo getJobsRecord(Integer id) {
        JobResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentjob/getrecord/" + id,
                JobResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public JobResponse deleteJobById(Integer id) {
        return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentjob/delete/" + id,
                JobResponse.class);
    }

    @Override
    public LinkResponse searchAction(ContentLinksRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlinks/searchaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkResponse insertAction(ContentLinksRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlinks/insertaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkResponse updateAction(ContentLinksRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlinks/updateaction", requestBean,
                LinkResponse.class);
    }


    /**
     * 查询配置中心操作日志配置
     *
     * @param map
     * @return
     */
    @Override
    public AdminOperationLogResponse selectOperationLogList(Map<String, Object> map) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/operationlog/list", map, AdminOperationLogResponse.class).getBody();
    }

    /**
     * @param idCardCustomize
     * @return String
     * @Description:通过身份证号获取户籍所在地
     * @exception:
     * @author: nxl
     */
    @Override
    public IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/content/idcard/idcarddetail", idCardCustomize, IdCardCustomize.class).getBody();
    }

    /**
     * 根据条件查询消息推送标签管理表
     *
     * @param request
     * @return
     */
    @Override
    public MessagePushTagResponse getMessagePushTagList(MessagePushTagRequest request) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/searchList";
        MessagePushTagResponse response = restTemplate.postForEntity(url, request, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse getMessagePushTag(Integer id) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/getRecord/" + id;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse insretMessagePushTag(MessagePushTagRequest request) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/insertMessagePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, request, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse updateMessagePushTag(MessagePushTagRequest tagRequest) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/updateMessagePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, tagRequest, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse deleteMessagePushTag(Integer id) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/deleteMessagePushTag/" + id;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse updatePushTag(MessagePushTagVO record) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/updatePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, record, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse countByTagCode(Integer id, String tagCode) {
        String url = "http://AM-CONFIG/am-config/messagePushTag/countByTagCode/" + id + "/" + tagCode;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse getMessagePushTemplateList(MsgPushTemplateRequest request) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/serachList";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, request, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public List<MessagePushTagVO> getAllPushTagList() {
        String url = "http://AM-CONFIG/am-config/messagePushTag/getAllPushTagList";
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse findMsgPushTemplateById(Integer id) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/findMsgPushTemplateById/" + id;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public List<MessagePushTagVO> getTagList() {
        String url = "http://AM-CONFIG/am-config/messagePushTag/getTagList";
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsConfigResponse initSmsConfig(SmsConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsConfig/initSmsConfig", request, SmsConfigResponse.class).getBody();
    }

    /**
     * 添加短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsConfigResponse insertSmsConfig(SmsConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsConfig/insertSmsConfig", request, SmsConfigResponse.class).getBody();
    }

    /**
     * 修改短信加固数据
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsConfigResponse updateSmsConfig(SmsConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsConfig/updateSmsConfig", request, SmsConfigResponse.class).getBody();
    }

    /**
     * 查询通知配置列表
     *
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse initSmsNoticeConfig() {
        return restTemplate.getForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/list", SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 查询通知配置详情
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse smsNoticeConfigInfo(SmsNoticeConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/info", request, SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 添加通知配置详情
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse insertSmsNoticeConfig(SmsNoticeConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/insert", request, SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 修改通知配置详情
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse updateSmsNoticeConfig(SmsNoticeConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/update", request, SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 关闭通知配置详情
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse closeSmsNoticeConfig(SmsNoticeConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/close", request, SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 打开通知配置详情
     *
     * @param request
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse openSmsNoticeConfig(SmsNoticeConfigRequest request) {
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/open", request, SmsNoticeConfigResponse.class).getBody();
    }

    /**
     * 唯一性验证
     *
     * @param name
     * @return
     * @author xiehuili
     */
    @Override
    public Integer onlyName(String name) {
        return restTemplate.getForEntity("http://AM-CONFIG/am-config/smsNoticeConfig/onlyName/" + name, Integer.class).getBody();
    }

    @Override
    public void closeAction(MailTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/close_action", request,
                Object.class);
    }

    @Override
    public void openAction(MailTemplateRequest request) {
        restTemplate.postForEntity("http://AM-CONFIG/am-config/smsMailTemplate/open_action", request,
                Object.class);
    }

    /**
     * 获取充值银行卡列表
     *
     * @return
     * @Author : huanghui
     */
    @Override
    public List<BanksConfigVO> getBankcardList() {
        BanksConfigResponse response = restTemplate.getForEntity("http://AM-CONFIG/am-config/config/selectBankConfigList", BanksConfigResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse updateMsgPushTemplate(MsgPushTemplateRequest templateRequest) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/updateAction";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, templateRequest, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse deleteMessagePushTemplate(List<Integer> recordList) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/deleteAction/" + recordList;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse countByTemplateCode(Integer id, String templateCode) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/countByTemplateCode/" + id + "/" + templateCode;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public void openSmsTemplate(SmsTemplateRequest request) {
        restTemplate.postForObject("http://AM-CONFIG/am-config/smsTemplate/open_sms_template", request, SmsTemplateResponse.class);
    }

    @Override
    public void closeSmsTemplate(SmsTemplateRequest request) {
        restTemplate.postForObject("http://AM-CONFIG/am-config/smsTemplate/close_sms_template", request, SmsTemplateResponse.class);
    }

    @Override
    public void updateSmsTemplate(SmsTemplateRequest request) {
        restTemplate.postForObject("http://AM-CONFIG/am-config/smsTemplate/update_sms_template", request, SmsTemplateResponse.class);
    }

    @Override
    public MessagePushTemplateResponse insertMessageTemplate(MessagePushTemplateVO templateVO) {
        String url = "http://AM-CONFIG/am-config/messagePushTemplate/insertMessageTemplate";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, templateVO, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
}
