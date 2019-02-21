package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.beans.request.*;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.BankConfigResponse;
import com.hyjf.am.response.admin.promotion.AppChannelReconciliationResponse;
import com.hyjf.am.response.config.*;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.config.*;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.*;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//        String url = "http://AM-ADMIN/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        String url = "http://AM-ADMIN/am-config/adminSystem/get_admin_system_by_userid/" + loginUserId;
        AdminSystemResponse response = restTemplate.getForEntity(url, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
    @Override
    public DebtConfigResponse getDebtConfig(){
        String url = "http://AM-ADMIN/am-admin/debtconfig/init" ;
        DebtConfigResponse response = restTemplate.getForEntity(url, DebtConfigResponse.class).getBody();
        return response;
    }
    @Override
    public DebtConfigResponse updateDebtConfig(DebtConfigRequest request){
        String url = "http://AM-ADMIN/am-admin/debtconfig/update" ;
        DebtConfigResponse response = restTemplate.postForEntity(url,request,DebtConfigResponse.class).getBody();
        return response;
    }
    @Override
    public int countDebtConfigLogTotal(){
        String url = "http://AM-ADMIN/am-admin/debtconfig/countdebtconfiglogtotal" ;
        IntegerResponse response = restTemplate.getForEntity(url,IntegerResponse.class).getBody();
        return response.getResultInt();
    }
    @Override
    public List<DebtConfigLogVO> getDebtConfigLogList(DebtConfigRequest req){
        String url = "http://AM-ADMIN/am-admin/debtconfig/getdebtconfigloglist" ;
        DebtConfigLogResponse response = restTemplate.postForEntity(url,req,DebtConfigLogResponse.class).getBody();
        return response.getResultList();
    }

    @Override
    public List<ParamNameVO> getParamNameList(String nameClass) {
        String url = "http://AM-ADMIN/am-config/config/getParamNameList/" + nameClass;
        ParamNameResponse response = restTemplate.getForEntity(url, ParamNameResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<ParamNameVO> getParamName(String other1) {
        String url = "http://AM-ADMIN/am-config/config/getParamName/" + other1;
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
                .getForEntity("http://AM-ADMIN/am-config/siteSettings/findOne/", SiteSettingsResponse.class).getBody();
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
                .getForEntity("http://AM-ADMIN/am-config/smsMailTemplate/findSmsMailByCode/" + mailCode,
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
                .getForEntity("http://AM-ADMIN/am-config/config/getBankReturnCodeConfig/" + retCode,
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
        String url = "http://AM-ADMIN/am-config/config/getBankReturnCodeConfig/" + retCode;
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
                .getForEntity("http://AM-ADMIN/am-config/config/selectBankConfigList", BankConfigResponse.class)
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
                .getForEntity("http://AM-ADMIN/am-config/config/queryBankIdByCardNo/" + cardNo, String.class).getBody();
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
                .getForEntity("http://AM-ADMIN/am-config/config/getJXbankConfigByBankId/" + bankId, JxBankConfigResponse.class).getBody();
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
                .getForEntity("http://AM-ADMIN/am-config/content/contentlinks/getLinks", LinkResponse.class)
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
        ParamNameResponse amResponse = restTemplate.getForEntity("http://AM-ADMIN/am-admin/paramname/getNameCd/" + "FLOW_STATUS", ParamNameResponse.class)
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
                .getForEntity("http://AM-ADMIN/am-config/adminSystem/isexistsapplicant/" + applicant, AdminSystemResponse.class)
                .getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUtmReadPermissionsResponse searchAction(AdminUtmReadPermissionsRequest request) {
        AdminUtmReadPermissionsResponse response = restTemplate.postForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/searchaction",
                request, AdminUtmReadPermissionsResponse.class);
        return response;

    }
    @Override
    public UtmPlatResponse getUtmPlatList(){
        UtmPlatResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/preparedatas", UtmPlatResponse.class);
        return response;
    }
    @Override
    public IntegerResponse isExistsAdminUser(String userName){
        IntegerResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/checkaction/"+userName, IntegerResponse.class);
        return response;
    }

    @Override
    public AdminUtmReadPermissionsResponse insertAction(AdminUtmReadPermissionsRequest requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/insert",
                requestBean, AdminUtmReadPermissionsResponse.class);
    }
    @Override
    public AdminUtmReadPermissionsResponse getAdminUtmReadPermissions(AdminUtmReadPermissionsRequest requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/getrecord",
                requestBean, AdminUtmReadPermissionsResponse.class);
    }


    @Override
    public AdminUtmReadPermissionsResponse updateAction(AdminUtmReadPermissionsRequest requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/update",
                requestBean, AdminUtmReadPermissionsResponse.class);
    }


    @Override
    public AdminUtmReadPermissionsResponse deleteById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/delete/" + id,
                AdminUtmReadPermissionsResponse.class);
    }

    @Override
    public AdminUtmReadPermissionsVO selectAdminUtmReadPermissions(Integer userId) {
        AdminUtmReadPermissionsResponse response = restTemplate.getForObject("http://AM-ADMIN/am-admin/extensioncenter/adminutmreadpermissions/getadminutmreadpermissions/" + userId,
                AdminUtmReadPermissionsResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public AppChannelReconciliationResponse getReconciliationPage(AppChannelReconciliationRequest form) {
//		return restTemplate.postForObject("http://AM-ADMIN/am-config/extensioncenter/adminutmreadpermissions/getreconciliationpage",form,
//				AppChannelReconciliationResponse.class);
        return null;
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
                .postForEntity("http://AM-ADMIN/am-config/config/selectBankConfigListByPage", adminRequest, AdminBankConfigResponse.class).getBody();
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
                .getForEntity("http://AM-ADMIN/am-config/config/getBankConfigByBankId/" + bankId, com.hyjf.am.response.config.BankConfigResponse.class).getBody();
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
                .getForEntity("http://AM-ADMIN/am-config/config/selectBankConfigByBankName/"+bankName,  AdminBankConfigResponse.class).getBody();
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
                .postForEntity("http://AM-ADMIN/am-config/config/insertBankConfig", adminRequest, AdminBankConfigResponse.class).getBody();
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
                .postForEntity("http://AM-ADMIN/am-config/config/updadteBankConfig", adminRequest, AdminBankConfigResponse.class).getBody();
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
                .postForEntity("http://AM-ADMIN/am-config/config/deleteBankConfigById", id, AdminBankConfigResponse.class).getBody();
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
                .postForEntity("http://AM-ADMIN/am-config/config/deleteBankConfigById", request, AdminBankConfigResponse.class).getBody();
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
                .postForEntity("http://AM-ADMIN/am-config/config/validateFeildBeforeSave", adminBankConfigRequest, AdminBankConfigResponse.class).getBody();
    }

    /**
     * 查询配置中心接口切换列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/bankInterface/list", adminRequest, BankInterfaceResponse.class)
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/bankInterface/info", adminRequest, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 修改 接口切换
     *
     * @return
     */
    @Override
    public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/bankInterface/update", req, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 删除 接口切换
     *
     * @return
     */
    @Override
    public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/bankInterface/delete", req, BankInterfaceResponse.class)
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/list", adminRequest, AdminBankRechargeConfigResponse.class)
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/info", adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 编辑保存快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse saveBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/insert", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 修改快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse updateBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/update", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 删除快捷充值限额
     *
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse deleteBankRechargeConfig(AdminBankRechargeConfigRequest req) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/delete", req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 检查银行卡是否重复
     * @return
     */
    @Override
    public IntegerResponse bankIsExists(AdminBankRechargeConfigRequest adminRequest){
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/bankIsExists", adminRequest, IntegerResponse.class)
                .getBody();
    }

    /**
     * 导出查询快捷充值限额列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public List<BankRechargeLimitConfigVO> exportRecordList(BankRechargeLimitConfigVO adminRequest) {
        AdminBankRechargeConfigResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/bankrecharge/exportRecordList", adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取银行列表(快捷支付卡)
     *
     * @return
     */
    @Override
    public List<BankConfigVO> getBankRecordList() {
        BankConfigVO bank = new BankConfigVO();
        BankConfigResponse response= restTemplate.postForEntity("http://AM-ADMIN/am-config/config/getBankRecordListByQuickPayment", bank, BankConfigResponse.class)
                .getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询返回码配置列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest) {
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/list";
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
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/info";
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
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/insert";
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
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/update";
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
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/isExistsReturnCode";
        return restTemplate.postForEntity(url, record, BooleanResponse.class).getBody().getResultBoolean();
    }

    /**
     * 根据条件判断该条数据在数据库中是否存在
     *
     * @param adminRequest
     * @return
     */
    @Override
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest) {
        String url = "http://AM-ADMIN/am-admin/config/bankretcodeconfig/isExistsRecord";
        return restTemplate.postForEntity(url, adminRequest, BooleanResponse.class).getBody().getResultBoolean();
    }

    /**
     * （条件）列表查询
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/list",
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
        return restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/info",
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
        AdminBankSettingResponse response = restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/searchForInsert",
                bank, AdminBankSettingResponse.class);

        return response.getResultList();
    }

    /**
     * 添加
     *
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/insert",
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
        return restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/update",
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
        return restTemplate.postForObject("http://AM-ADMIN/am-config/banksetting/delete",
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
        ParamNameResponse amResponse = restTemplate.postForEntity("http://AM-ADMIN/am-admin/paramname/selectProjectTypeParamList", null, ParamNameResponse.class)
                .getBody();
        if (Response.isSuccess(amResponse)) {
            return amResponse.getResultList();
        }
        return null;
    }

    @Override
    public ContentArticleResponse searchAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/content/contentarticle/searchaction",
                contentArticleRequestBean, ContentArticleResponse.class).getBody();

    }

    @Override
    public ContentArticleResponse inserAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentarticle/insertaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse findById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentarticle/findbyId/" + id, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse updateAction(ContentArticleRequest contentArticleRequestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentarticle/updateaction",
                contentArticleRequestBean, ContentArticleResponse.class);
    }

    @Override
    public ContentArticleResponse deleteContentArticleById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentarticle/delete/" + id, ContentArticleResponse.class);
    }

    @Override
    public CategoryResponse getCategoryPage(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/searchaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse changeSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/changesubtype",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse infoTypeAction(Integer id) {
        /*
         * ResponseEntity<List<ProtocolTemplateVO>> response =
         * restTemplate.exchange("http://AM-TRADE/am-trade/protocol/getnewinfo",
         * HttpMethod.GET, null, new
         * ParameterizedTypeReference<CategoryResponse<CategoryVO>>() {});
         */
        ResponseEntity<CategoryResponse<CategoryVO>> response = restTemplate.exchange(
                "http://AM-ADMIN/am-config/content/help/infotypeaction/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<CategoryResponse<CategoryVO>>() {
                });
        if (null != response) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public CategoryResponse infoSubTypeAction(CategoryBeanRequest categoryBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/infosubtypeaction",
                categoryBeanRequest, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer insertCategory(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/insertcategory",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateAction(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/updateaction",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getCategoryCount(CategoryVO categoryVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/getcategorycount",
                categoryVO, CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer getCountByPcateIdAndcateId(Integer pid, Integer cid) {
        CategoryVO request = new CategoryVO();
        request.setPid(pid);
        request.setId(cid);
        CategoryResponse response = restTemplate.postForObject("http://AM-ADMIN/am-config/content/help/getbypcateidAandcateid", request,
                CategoryResponse.class);
        if (null != response) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public List<ContentHelpVO> getListByPcateIdAndcateId(CategoryVO categoryVO) {
        ParameterizedTypeReference<CategoryResponse<ContentHelpVO>> typeRef = new ParameterizedTypeReference<CategoryResponse<ContentHelpVO>>() {
        };
        ResponseEntity<CategoryResponse<ContentHelpVO>> responseEntity = restTemplate.exchange("http://AM-ADMIN/am-config/content/help/getlistbypcateidandcateid", HttpMethod.POST, new HttpEntity<>(categoryVO), typeRef);
        CategoryResponse<ContentHelpVO> responseBean = responseEntity.getBody();
        return responseBean.getRecordList();
    }

    @Override
    public Integer delContentHelp(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-config/content/help/delcontenthelp/" + id,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer delCategory(Integer id) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-config/content/help/delcategory/" + id,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public Integer updateContentHelp(ContentHelpVO contentHelpVO) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/updatecontenthelp", contentHelpVO,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/gethelppage", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse getHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/gethelpinfo", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse insertHelpInfo(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/inserthelpinfo", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public CategoryResponse updateHelpAction(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/updatehelpaction", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }

    @Override
    public Integer chanContentHelp(Integer contentId, Integer status, Integer zhiChiStatus) {
        CategoryResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-config/content/help/chancontenthelp/" + contentId + "/" + status + "/" + zhiChiStatus,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response.getFlag();
        }
        return null;
    }

    @Override
    public CategoryResponse getOftenInitPage(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/getofteninitpage", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }
    @Override
    public CategoryResponse getZhiChiInit(ContentHelpBeanRequest contentHelpBeanRequest) {
        CategoryResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-config/content/help/getzhichiinit", contentHelpBeanRequest,
                CategoryResponse.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }


    @Override
    public ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentenvironment/searchaction",
                requestBean, ContentEnvironmentResponse.class);
    }

    @Override
    public int insertAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentenvironment/insertaction",
                requestBean, IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(ContentEnvironmentRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentenvironment/updateaction",
                requestBean, IntegerResponse.class).getResultInt();
    }

    @Override
    public ContentEnvironmentVO getRecord(Integer id) {
        ContentEnvironmentResponse response = restTemplate.getForObject(
                "http://AM-ADMIN/am-config/content/contentenvironment/getrecord/" + id,
                ContentEnvironmentResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteContentEnvironmentById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentenvironment/delete/" + id,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/contentlandingpage/searchaction",
                requestBean, LandingPageResponse.class);
    }

    @Override
    public LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/contentlandingpage/insert",
                requestBean, LandingPageResponse.class);
    }

    @Override
    public LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/contentlandingpage/update",
                requestBean, LandingPageResponse.class);
    }


    @Override
    public LandingPageResponse deleteLandingPageById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-admin/content/contentlandingpage/delete/" + id,
                LandingPageResponse.class);
    }
    @Override
    public IntegerResponse countByPageName(ContentLandingPageRequestBean requestBean){
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/content/contentlandingpage/countByPageName",
                requestBean, IntegerResponse.class);
    }
    @Override
    public LandingPageResponse getLandingPageRecord(Integer id) {
        LandingPageResponse response = restTemplate.getForObject(
                "http://AM-ADMIN/am-admin/content/contentlandingpage/getrecord/" + id, LandingPageResponse.class);
        return response;
    }
    @Override
    public LinkResponse searchAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentpartner/searchaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public int insertAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentpartner/insertaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(ContentPartnerRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentpartner/updateaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public LinkVO getLinkRecord(Integer id) {
        LinkResponse response = restTemplate.getForObject(
                "http://AM-ADMIN/am-config/content/contentpartner/getrecord/" + id, LinkResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteLinkById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentpartner/delete/" + id,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public ContentQualifyResponse searchAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN//am-config/content/contentqualify/searchaction",
                requestBean, ContentQualifyResponse.class);
    }

    @Override
    public int insertAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentqualify/insertaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(ContentQualifyRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentqualify/updateaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public ContentQualifyVO getContentQualifyRecord(Integer id) {
        ContentQualifyResponse response = restTemplate.getForObject(
                "http://AM-ADMIN/am-config/content/contentqualify/getrecord/" + id, ContentQualifyResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteContentQualifyById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentqualify/delete/" + id,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public CouponCheckResponse getCouponList(AdminCouponCheckRequest adminCouponCheckRequest) {
        String url = "http://AM-ADMIN/am-config/checkList/getCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, adminCouponCheckRequest, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse deleteCouponList(AdminCouponCheckRequest acr) {
        String url = "http://AM-ADMIN/am-config/checkList/deleteCheckList";
        CouponCheckResponse response = restTemplate.postForEntity(url, acr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckResponse insert(AdminCouponCheckRequest accr) {
        String url = "http://AM-ADMIN/am-config/checkList/insertCoupon";
        CouponCheckResponse response = restTemplate.postForEntity(url, accr, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponCheckVO selectCoupon(Integer id) {
        String url = "http://AM-ADMIN/am-config/checkList/selectCoupon/" + id;
        CouponCheckResponse response = restTemplate.getForEntity(url, CouponCheckResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public CouponCheckResponse updateCoupon(AdminCouponCheckRequest request) {
        String url = "http://AM-ADMIN/am-config/checkList/updateCoupon";
        return restTemplate.postForEntity(url, request, CouponCheckResponse.class).getBody();
    }

    @Override
    public CouponTenderResponse getAdminUserByUserId(String userId) {
        String url = "http://AM-ADMIN/am-config/adminSystem/hztgetusername/" + userId;
        CouponTenderResponse response = restTemplate.getForEntity(url, CouponTenderResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public EventResponse searchAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentevent/searchaction",
                requestBean, EventResponse.class);
    }

    @Override
    public int insertAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentevent/insertaction",
                requestBean, IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(EventRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentevent/updateaction",
                requestBean, IntegerResponse.class).getResultInt();
    }

    @Override
    public EventVO getEventRecord(Integer id) {
        EventResponse response = restTemplate.getForObject(
                "http://AM-ADMIN/am-config/content/contentevent/getrecord/" + id,
                EventResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteEventById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentevent/delete/" + id,
                IntegerResponse.class).getResultInt();
    }

    /**
     * 查询手续费配置列表
     *
     * @param request
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/list",
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
        AdminBankConfigResponse response= restTemplate.postForObject("http://AM-ADMIN/am-config/config/getBankConfigListByStatus",
                bank, AdminBankConfigResponse.class);
        if(response != null){
            return response.getResultList();
        }
        return null;
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
        AdminBankConfigResponse response= restTemplate.getForObject("http://AM-ADMIN/am-config/config/selectBankConfigByBankName/"+bank.getName(),
                AdminBankConfigResponse.class);
        if(response != null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询手续费配置详情页面
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/info",
                adminRequest, AdminFeeConfigResponse.class);
    }

    /**
     * 编辑保存手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/insert",
                req, AdminFeeConfigResponse.class);
    }

    /**
     * 修改手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/update",
                req, AdminFeeConfigResponse.class);
    }

    /**
     * 删除手续费配置
     *
     * @return
     */
    @Override
    public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req) {
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/delete",
                req, AdminFeeConfigResponse.class);
    }
    /**
     * 手续费配置校验
     * @return
     */
    @Override
    public AdminFeeConfigResponse validateFeeConfigBeforeAction(AdminFeeConfigRequest request){
        return restTemplate.postForObject("http://AM-ADMIN/am-admin/feeConfig/validateFeeConfigBeforeAction",
                request, AdminFeeConfigResponse.class);
    }

    @Override
    public List<AdminSystemVO> getUserPermission(String userName) {
        AdminSystemResponse adminSystemResponse = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/adminSystem/getpermissions/" + userName,
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
                .postForEntity("http://AM-ADMIN/am-config/adminSystem/getuser", adminSystemRequest,
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
                .getForEntity("http://AM-ADMIN/am-config/adminSystem/selectLeftMenuTree/" + userId, TreeResponse.class)
                .getBody();
        if (treeResponse != null) {
            return treeResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<SmsMailTemplateVO> findMailAll() {
        SmsMailTemplateResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/smsMailTemplate/findAll", SmsMailTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public SmsMailTemplateResponse findMailTemplate(MailTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsMailTemplate/findMailTemplate", request,
                SmsMailTemplateResponse.class);
    }

    @Override
    public int insertMailTemplate(MailTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsMailTemplate/insertMailTemplate", request,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateMailTemplate(MailTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsMailTemplate/update_mail_template", request,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public MessagePushTemplateResponse findAll() {
        return restTemplate
                .getForEntity("http://AM-ADMIN/am-config/messagePushTemplate/getAllTemplates",
                        MessagePushTemplateResponse.class)
                .getBody();
    }

    @Override
    public MessagePushTemplateResponse findMsgPushTemplate(MsgPushTemplateRequest request) {
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/messagePushTemplate/findMsgPushTemplate", request,
                MessagePushTemplateResponse.class).getBody();
    }

    @Override
    public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
        restTemplate.postForEntity("http://AM-ADMIN/am-config/messagePushTemplate/insertMsgPushTemplate", IntegerResponse.class,
                Object.class);
    }

    @Override
    public MessagePushTagVO findMsgTagByTagName(String tagName) {
        MessagePushTagVO result = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/messagePushTag/findMsgTagByTagName/" + tagName,
                        MessagePushTagVO.class)
                .getBody();
        return result;
    }


    @Override
    public SiteSettingsResponse selectSiteSetting() {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/siteSettings/findOne", SiteSettingsResponse.class);
    }

    @Override
    public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/siteSettings/update", requestBean,
                SiteSettingsResponse.class);
    }

    @Override
    public List<SmsTemplateVO> findSmsAll() {
        SmsTemplateResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/smsTemplate/findAll", SmsTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public SmsTemplateResponse findSmsTemplate(SmsTemplateRequest request) {
        return restTemplate
                .postForEntity("http://AM-ADMIN/am-config/smsTemplate/findSmsTemplate", request,
                        SmsTemplateResponse.class)
                .getBody();
    }

    @Override
    public int insertSmsTemplate(SmsTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsTemplate/insertTemplate", request, IntegerResponse.class).getResultInt();
    }



    @Override
    public TeamResponse searchAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/team/searchaction", requestBean,
                TeamResponse.class);
    }

    @Override
    public int insertAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/team/insertaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/team/updateaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public TeamVO getTeamRecord(Integer id) {
        TeamResponse response = restTemplate.getForObject("http://AM-ADMIN/am-config/team/getrecord/" + id,
                TeamResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteTeamById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/team/delete/" + id, IntegerResponse.class).getResultInt();
    }

    /**
     * 查询版本设置列表
     *
     * @param adminRequest
     * @return
     */
    @Override
    public AdminVersionResponse versionConfigInit(AdminVersionRequest adminRequest) {
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/list";
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
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/info";
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
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/insert";
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
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/update";
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
    public AdminVersionResponse deleteVersionConfig(List<Integer> id) {
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/delete";
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
    public VersionVO getVersionByCode(Integer vid, Integer type, String version) {
        String url = "http://AM-ADMIN/am-admin/config/versionconfig/validationFeild";
        Map<String, Object> map = new HashMap<String, Object>();
        if (vid != null) {
            map.put("vid", vid);
        }
        map.put("type", type);
        map.put("version", version);
        AdminVersionResponse response = restTemplate.postForEntity(url, map, AdminVersionResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }




    /**
     * 获取所有问题
     *
     * @return
     */
    @Override
    public QuestionResponse getAllQuestion() {
        QuestionResponse mspResponse = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/quesiontAndAnswer/findAllQuestion", QuestionResponse.class)
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
                .getForEntity("http://AM-ADMIN/am-config/quesiontAndAnswer/findAllAnswer", AnswerResponse.class)
                .getBody();
        if (mspResponse != null) {
            return mspResponse;
        }
        return null;
    }

    @Override
    public JobResponse searchAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentjob/searchaction", requestBean,
                JobResponse.class);
    }

    @Override
    public int insertAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentjob/insertaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateAction(ContentJobRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentjob/updateaction", requestBean,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public JobsVo getJobsRecord(Integer id) {
        JobResponse response = restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentjob/getrecord/" + id,
                JobResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int deleteJobById(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN/am-config/content/contentjob/delete/" + id,
                IntegerResponse.class).getResultInt();
    }

    @Override
    public List<LinkVO> searchActions(ContentLinksRequest requestBean) {
        LinkResponse response = restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentlinks/searchaction", requestBean,
                LinkResponse.class);
        response.getResultList();

        return response.getResultList();
    }

    @Override
    public LinkResponse insertActions(ContentLinksRequest requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentlinks/insertaction", requestBean,
                LinkResponse.class);
    }

    @Override
    public LinkResponse infoInfoAction(Integer id) {
        return restTemplate.getForObject("http://AM-ADMIN//am-config/content/contentlinks/getrecord/" + id,
                LinkResponse.class);
    }

    @Override
    public LinkResponse updateActions(ContentLinksRequest requestBean) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/content/contentlinks/updateaction", requestBean,
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-admin/config/operationlog/list", map, AdminOperationLogResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/content/idcard/idcarddetail", idCardCustomize, IdCardCustomize.class).getBody();
    }

    /**
     * 根据条件查询消息推送标签管理表
     *
     * @param request
     * @return
     */
    @Override
    public MessagePushTagResponse getMessagePushTagList(MessagePushTagRequest request) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/searchList";
        MessagePushTagResponse response = restTemplate.postForEntity(url, request, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse getMessagePushTag(Integer id) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/getRecord/" + id;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse insretMessagePushTag(MessagePushTagRequest request) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/insertMessagePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, request, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse updateMessagePushTag(MessagePushTagRequest tagRequest) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/updateMessagePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, tagRequest, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse deleteMessagePushTag(Integer id) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/deleteMessagePushTag/" + id;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse updatePushTag(MessagePushTagVO record) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/updatePushTag";
        MessagePushTagResponse response = restTemplate.postForEntity(url, record, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTagResponse countByTagCode(Integer id, String tagCode) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/countByTagCode/" + id + "/" + tagCode;
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse getMessagePushTemplateList(MsgPushTemplateRequest request) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/searchList";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, request, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public List<MessagePushTagVO> getAllPushTagList() {
        String url = "http://AM-ADMIN/am-config/messagePushTag/getAllPushTagList";
        MessagePushTagResponse response = restTemplate.getForEntity(url, MessagePushTagResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse findMsgPushTemplateById(Integer id) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/findMsgPushTemplateById/" + id;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public List<MessagePushTagVO> getTagList() {
        String url = "http://AM-ADMIN/am-config/messagePushTag/getTagList";
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsConfig/initSmsConfig", request, SmsConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsConfig/insertSmsConfig", request, SmsConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsConfig/updateSmsConfig", request, SmsConfigResponse.class).getBody();
    }

    /**
     * 查询通知配置列表
     *
     * @return
     * @author xiehuili
     */
    @Override
    public SmsNoticeConfigResponse initSmsNoticeConfig() {
        return restTemplate.getForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/list", SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/info", request, SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/insert", request, SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/update", request, SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/close", request, SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.postForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/open", request, SmsNoticeConfigResponse.class).getBody();
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
        return restTemplate.getForEntity("http://AM-ADMIN/am-config/smsNoticeConfig/onlyName/" + name, Integer.class).getBody();
    }

    @Override
    public int openAction(MailTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsMailTemplate/update_status", request,
                IntegerResponse.class).getResultInt();
    }

    /**
     * 获取充值银行卡列表
     *
     * @return
     * @Author : huanghui
     */
    @Override
    public List<JxBankConfigVO> getBankcardList() {
        JxBankConfigResponse response = restTemplate.getForEntity("http://AM-ADMIN/am-config/jxBankConfig/selectBankConfigList",
                JxBankConfigResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse updateMsgPushTemplate(MsgPushTemplateRequest templateRequest) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/updateAction";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, templateRequest, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse deleteMessagePushTemplate(List<Integer> recordList) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/deleteAction/" + recordList;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse countByTemplateCode(Integer id, String templateCode) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/countByTemplateCode/" + id + "/" + templateCode;
        MessagePushTemplateResponse response = restTemplate.getForEntity(url, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public int updateStatus(SmsTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsTemplate/open_sms_template", request, IntegerResponse.class).getResultInt();
    }

    @Override
    public int updateSmsTemplate(SmsTemplateRequest request) {
        return restTemplate.postForObject("http://AM-ADMIN/am-config/smsTemplate/update_sms_template", request, IntegerResponse.class).getResultInt();
    }

    @Override
    public SmsTemplateVO selectSmsTemByTplCode(String tplCode) {
        SmsTemplateResponse response = restTemplate.getForObject("http://AM-ADMIN/am-config/smsTemplate/findSmsTemplateByCode/" + tplCode, SmsTemplateResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public SmsMailTemplateVO infoAction(MailTemplateRequest request) {
        SmsMailTemplateResponse response = restTemplate.getForObject("http://AM-ADMIN/am-config/smsMailTemplate/find_by_id/" + request.getId(),
                SmsMailTemplateResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public SmsTemplateVO findSmsTemById(Integer id) {
        SmsTemplateResponse response = restTemplate
                .getForEntity("http://AM-ADMIN/am-config/smsTemplate/find_by_id/" + id, SmsTemplateResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public MessagePushTemplateResponse insertMessageTemplate(MessagePushTemplateVO templateVO) {
        String url = "http://AM-ADMIN/am-config/messagePushTemplate/insertMessageTemplate";
        MessagePushTemplateResponse response = restTemplate.postForEntity(url, templateVO, MessagePushTemplateResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public AdminUserResponse adminUserSearch(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/searchAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUserMoveToInfoAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/infoAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUserInsertAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/insertAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUserUpdateAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/updateAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUserDeleteRecordAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/deleteAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUserResetPwdAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/resetPwdAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public AdminUserResponse adminUsercCheckAction(AdminRequest adminRequest) {
        String url = "http://AM-ADMIN/am-config/admin/checkAction";
        AdminUserResponse response = restTemplate.postForEntity(url, adminRequest, AdminUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据标签id查询标签
     * @param tagId
     * @return
     */
    @Override
    public MessagePushTagVO getTagByTagId(Integer tagId) {
        String url = "http://AM-ADMIN/am-config/messagePushTag/getTagByTagId/" + tagId;
        MessagePushTagResponse response = restTemplate.getForEntity(url,MessagePushTagResponse.class).getBody();
        if (null != response.getResult()) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据bankId查询江西银行配置
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public JxBankConfigVO selectJxBankConfigByBankId(Integer bankId) {
        String url = "http://AM-ADMIN/am-config/jxBankConfig/selectJxBankConfigByBankId/" + bankId;
        JxBankConfigResponse response = restTemplate.getForEntity(url,JxBankConfigResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }
	@Override
	public AdminRoleResponse search(AdminRoleRequest form) {
        String url = "http://AM-ADMIN/am-config/role/search";
        AdminRoleResponse response = restTemplate.postForEntity(url, form, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse moveToInfoAction(AdminRoleRequest form) {
        String url = "http://AM-ADMIN/am-config/role/moveToInfoAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, form, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse insertRecord(AdminRoleRequest form) {
        String url = "http://AM-ADMIN/am-config/role/insertAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, form, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse updateRecord(AdminRoleRequest form) {
        String url = "http://AM-ADMIN/am-config/role/updateAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, form, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse deleteRecord(AdminRoleRequest form) {
        String url = "http://AM-ADMIN/am-config/role/deleteRecordAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, form, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public JSONArray getAdminRoleMenu(String roleId) {
        String url = "http://AM-ADMIN/am-config/role/getAdminRoleMenu/" + roleId;
        JSONArray response = restTemplate.getForEntity(url,JSONArray.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse checkAction(AdminRoleRequest bean) {
        String url = "http://AM-ADMIN/am-config/role/checkAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, bean, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
	@Override
	public AdminRoleResponse modifyPermissionAction(UserRoleRequest bean) {
        String url = "http://AM-ADMIN/am-config/role/modifyPermissionAction";
        AdminRoleResponse response = restTemplate.postForEntity(url, bean, AdminRoleResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
    @Override
    public JSONArray selectLeftMenuTree(String id) {
        String url = "http://AM-ADMIN/am-config/menu/selectLeftMenuTree/" + id;
        JSONArray response = restTemplate.getForEntity(url,JSONArray.class).getBody();
        if (null != response) {
            return response;
        }
        return null;
    }
    @Override
    public AdminSystemResponse insertAction(AdminMenuRequest form) {
        String url = "http://AM-ADMIN/am-config/menu/insertAction";
        AdminSystemResponse response = restTemplate.postForEntity(url, form, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public AdminSystemResponse getuser(AdminMenuRequest form) {
        String url = "http://AM-ADMIN/am-config/menu/getuser";
        AdminSystemResponse response = restTemplate.postForEntity(url, form, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public AdminSystemResponse deleteRecordAction(AdminMenuRequest form) {
        String url = "http://AM-ADMIN/am-config/menu/deleteRecordAction";
        AdminSystemResponse response = restTemplate.postForEntity(url, form, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public AdminSystemResponse moveToAuthAction(AdminMenuRequest form) {
        String url = "http://AM-ADMIN/am-config/menu/moveToAuthAction";
        AdminSystemResponse response = restTemplate.postForEntity(url, form, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public AdminSystemResponse updateMenuPermissionsAction(AdminMenuRequest form) {
        String url = "http://AM-ADMIN/am-config/menu/updateMenuPermissionsAction";
        AdminSystemResponse response = restTemplate.postForEntity(url, form, AdminSystemResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }
    @Override
    public List<String> getPermissionId(String string) {
        String url = "http://AM-ADMIN/am-config/role/getPermissionId/"+string;
        List<String>  response = restTemplate.getForEntity(url, List.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
	}
    /**
     * 根据银行名获取江西银行配置信息
     * @param bankName
     * @return
     * @auth nxl
     */
    @Override
    public JxBankConfigVO getBankConfigByBankName(String bankName) {
        String url = "http://AM-ADMIN/am-config/config/getJxBankConfigByBankName/"+bankName;
        JxBankConfigResponse response = restTemplate.getForEntity(url,JxBankConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 授權配置
     * @return
     */
    @Override
    public AdminAuthConfigCustomizeResponse getAuthConfigList() {
        String url = "http://AM-ADMIN/am-config/configCenter/authConfig/getAuthConfigList";
        AdminAuthConfigCustomizeResponse response = restTemplate.getForEntity(url,AdminAuthConfigCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response;
        }
        return null;
    }

    /**
     * 授权操作记录
     * @return
     */
    @Override
    public AdminAuthConfigLogResponse getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request) {
        String url = "http://AM-ADMIN/am-config/configCenter/authConfig/getAuthConfigLogList";
        AdminAuthConfigLogResponse response = restTemplate.postForEntity(url,request,AdminAuthConfigLogResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response;
        }
        return null;
    }

    /**
     * 授权配置详情
     * @param id
     * @return
     */
    @Override
    public AdminAuthConfigResponse getAuthConfigById(Integer id) {
        String url = "http://AM-ADMIN/am-config/configCenter/authConfig/getAuthConfigById/"+id;
        AdminAuthConfigResponse response =  restTemplate.getForEntity(url,AdminAuthConfigResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response;
        }
        return null;
    }
	@Override
	public AdminSystemResponse updatePasswordAction(AdminSystemRequest map) {
        AdminSystemResponse adminSystemResponse = restTemplate
                .postForEntity("http://AM-ADMIN/am-config/adminSystem/updatePasswordAction", map,
                        AdminSystemResponse.class)
                .getBody();
        if (adminSystemResponse != null) {
            return adminSystemResponse;
        }
        return null;
	}

    /**
     * 根据用户名查询后台操作用户
     * @param auditUser
     * @return
     */
    @Override
    public Integer getAdminByUsername(String auditUser) {
        String url = "http://AM-ADMIN/am-config/admin/getAdminByUsername/" + auditUser;
        AdminResponse response = restTemplate.getForObject(url, AdminResponse.class);
        if (response != null) {
            Map<String, Object> map = (Map<String, Object>) response.getResult();
            return Integer.parseInt(map.get("id").toString());
        }
        return null;
    }

    /**
     * 修改授权配置
     * @param form
     * @return
     */
    @Override
    public int updateAuthConfig(HjhUserAuthConfigVO form) {
        String url = "http://AM-ADMIN/am-config/configCenter/authConfig/updateAuthConfig";
        return restTemplate.postForEntity(url,form,Integer.class).getBody();
    }

    /**
     * 应急中心 上报日志列表
     * @param requestBean
     * @return
     */
    @Override
    public CertReportLogResponse selectCertReportLogList(CertReportLogRequestBean requestBean) {
        String url = "http://AM-ADMIN/am-config/cert/selectCertReportLogList";
        return restTemplate.postForEntity(url,requestBean,CertReportLogResponse.class).getBody();
    }

    /**
     * 应急中心错误日志列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public CertErrorReportLogResponse selectCertErrorReportLogList(CertErrorReportLogRequestBean requestBean) {
        String url = "http://AM-ADMIN/am-config/cert/selectCertErrorReportLogList";
        return restTemplate.postForEntity(url,requestBean,CertErrorReportLogResponse.class).getBody();
    }

    /**
     * 修改应急中心错误
     *
     * @param id
     * @return
     */
    @Override
    public Integer updateCertErrorCount(Integer id) {
        String url = "http://AM-ADMIN/am-config/cert/updateCertErrorCount";
        IntegerResponse response = restTemplate
                .postForEntity(url, id, IntegerResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getResultInt())) {
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public ConfigApplicantResponse updateApplicantConfigList(ConfigApplicantRequest request) {
        String url = "http://AM-ADMIN/am-config/adminSystem/updateApplicantConfigList";
        return restTemplate.postForEntity(url, request, ConfigApplicantResponse.class).getBody();
    }

    @Override
    public ConfigApplicantResponse addApplicantConfigList(ConfigApplicantRequest request) {
        String url = "http://AM-ADMIN/am-config/adminSystem/addApplicantConfigList";
        return restTemplate.postForEntity(url, request, ConfigApplicantResponse.class).getBody();

    }

    @Override
    public ConfigApplicantResponse getApplicantConfigList(ConfigApplicantRequest request) {
        String url = "http://AM-ADMIN/am-config/adminSystem/getApplicantConfigList";
        return restTemplate.postForEntity(url, request, ConfigApplicantResponse.class).getBody();

    }

    @Override
    public ConfigApplicantResponse findConfigApplicant(ConfigApplicantRequest request) {
        String url = "http://AM-ADMIN/am-config/adminSystem/findConfigApplicant";
        return restTemplate.postForEntity(url, request, ConfigApplicantResponse.class).getBody();

    }
}
