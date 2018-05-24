package com.hyjf.pay.lib.fadada.call;

import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;

/**
 * 电子签章接口API
 */
public interface DzqzCallApi {

    /**
     * 合同生成接口
     * @param bean
     * @return
     */
    public String generate_contract(DzqzCallBean bean);

    /**
     * 6.3. 合同模板传输接口
     * @param bean
     * @return
     */
    public String uploadtemplate(DzqzCallBean bean);

    /**
     * 个人CA申请接口
     * @param bean
     * @return
     */
    public String syncPerson_auto(DzqzCallBean bean);

    /**
     * 客户信息修改接口
     * @param bean
     * @return
     */
    public String infochange(DzqzCallBean bean);


    /**
     * 企业用户CA认证接口
     * @param bean
     * @return
     */
    public String  syncCompany_auto(DzqzCallBean bean);

    /**
     * 自动签署合同接口
     * @param bean
     * @return
     */
    public String  extsign_auto(DzqzCallBean bean);

    /**
     * 客户签署状态查询接口
     * @param bean
     * @return
     */
    public String query_signstatus(DzqzCallBean bean);

    /**
     * 下载已签署合同接口
     * @param bean
     * @return
     */
    public String downLoadContract(DzqzCallBean bean);
}
