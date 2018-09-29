package com.hyjf.pay.lib.fadada.call.impl;

import com.fadada.sdk.client.FddClientBase;
import com.fadada.sdk.client.FddClientExtra;
import com.fadada.sdk.client.nonpublic.SyncCompanyAuto;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.pay.lib.fadada.call.DzqzCallApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DzqzCallApiImpl implements DzqzCallApi{

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String generate_contract(DzqzCallBean bean) {

        FddClientBase clientbase = new FddClientBase(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());

        String response = clientbase.invokeGenerateContract(bean.getTemplate_id(), bean.getContract_id(),
                bean.getDoc_title(), bean.getFont_size(), bean.getFont_type(), bean.getParameter_map(), bean.getDynamic_tables());
        return response;
    }

    @Override
    public String uploadtemplate(DzqzCallBean bean) {
        FddClientBase clientbase = new FddClientBase(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());

        String response = clientbase.invokeUploadTemplate(bean.getTemplate_id(), bean.getFile(), bean.getDoc_url());
        return response;
    }

    /**
     * 个人CA申请接口
     * @param bean
     * @return
     */
    @Override
    public String syncPerson_auto(DzqzCallBean bean) {
        log.info("app_id:[{}],Secret:[{}],version:[{}],url:[{}]",bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        FddClientBase clientbase = new FddClientBase(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        log.info("customer_name:[{}],email:[{}],idCard:[{}],ident_type:[{}],mobile:[{}]",bean.getCustomer_name(),bean.getEmail(),bean.getIdCard(),bean.getIdent_type(),bean.getMobile());
        String response = clientbase.invokeSyncPersonAuto(bean.getCustomer_name(),bean.getEmail(),bean.getIdCard(),bean.getIdent_type(),bean.getMobile());
        return response;
    }

    /**
     * 客户信息修改接口
     * @param bean
     * @return
     */
    @Override
    public String infochange(DzqzCallBean bean) {
        FddClientExtra clientbase = new FddClientExtra(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        String response = clientbase.invokeInfoChange(bean.getCustomer_id(),bean.getEmail(),bean.getMobile());
        return response;
    }

    /**
     * 企业用户CA认证接口
     * @param bean
     * @return
     */
    @Override
    public String syncCompany_auto(DzqzCallBean bean) {
        SyncCompanyAuto clientbase = new SyncCompanyAuto(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        String response = clientbase.invoke(bean.getCustomer_name(),bean.getEmail(),bean.getIdCard(),bean.getMobile());
        return response;
    }

    /**
     * 自动签署合同接口
     * @param bean
     * @return
     */
    @Override
    public String extsign_auto(DzqzCallBean bean) {
        FddClientBase clientbase = new FddClientBase(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        String response = clientbase.invokeExtSignAuto(bean.getLogordid(),bean.getCustomer_id(),bean.getClient_role(),bean.getContract_id(),bean.getDoc_title(),
                bean.getSign_keyword(),bean.getKeywordStrategy(),bean.getNotify_url());
        return response;
    }

    /**
     * 客户签署状态查询接口
     * @param bean
     * @return
     */
    @Override
    public String query_signstatus(DzqzCallBean bean) {

        FddClientBase clientbase = new FddClientBase(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        String response = clientbase.invokeQuerySignStatus(bean.getContract_id(), bean.getCustomer_id());
        return response;
    }


    /**
     * 下载已签署合同
     * @param bean
     * @return
     */
    @Override
    public String downLoadContract(DzqzCallBean bean) {
        FddClientExtra clientextra = new FddClientExtra(bean.getApp_id(), bean.getSecret(), bean.getV(), bean.getUrl());
        String response = clientextra.invokeDownloadPdf(bean.getSavePath(), bean.getContract_id());
        return response;
    }


}
