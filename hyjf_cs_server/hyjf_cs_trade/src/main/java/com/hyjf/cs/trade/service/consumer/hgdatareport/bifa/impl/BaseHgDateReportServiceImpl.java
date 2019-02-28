/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 *
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:43:49
 * Modification History:
 * Modified by :
 */

package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BaseHgDataReportEntityVO;
import com.hyjf.am.vo.trade.bifa.BifaUserInfoSHA256EntityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.WebServiceUtil;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BaseHgDateReportService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCommonConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaReportResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author liubin
 */
@Service
public class BaseHgDateReportServiceImpl extends BaseServiceImpl implements BaseHgDateReportService {


    Logger logger = LoggerFactory.getLogger(BaseHgDateReportServiceImpl.class);

    private String thisMessName = "合规数据上送共通方法";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    /***
     * 北互金wsdl地址
     */
    @Value("${hyjf.bifa.end.point}")
    String BIFA_END_POINT;
    /**
     * 北互金namespace地址
     */
    @Value("${hyjf.bifa.name.space}")
    String BIFA_NAME_SPACE;
    /**
     * 北互金loginname
     */
    @Value("${hyjf.bifa.login.name}")
    String BIFA_LOGIN_NAME;
    /**
     * 北互金password
     */
    @Value("${hyjf.bifa.pass.word}")
    String BIFA_PASS_WORD;

    /**
     * 平台编号
     */
    @Value("${hyjf.source.code}")
    protected String SOURCE_CODE;
    /**
     * 散标链接
     */
    @Value("${hyjf.source.product.url.borrow}")
    protected String SOURCE_PRODUCT_URL_BORROW;
    /**
     * 智投链接
     */
    @Value("${hyjf.source.product.url.hjhplan}")
    protected String SOURCE_PRODUCT_URL_HJHPLAN;
    /**
     * 散标转让产品链接
     */
    @Value("${hyjf.source.product.url.borrow.credit}")
    protected String SOURCE_PRODUCT_URL_BORROW_CREDIT;
    /**
     * 智投产品链接
     */
    @Value("${hyjf.source.product.url.hjh.credit}")
    protected String SOURCE_PRODUCT_URL_HJH_CREDIT;

    /**
     * 北互金索引文件存放地址
     **/
    @Value("${hyjf.bifa.files.indexfile}")
    protected String HYJF_BIFA_FILES_INDEXFILE;

    /**
     * 北互金索引文件上报地址
     */
    @Value("${hyjf.bifa.indexdata.report.url}")
    protected String HYJF_BIFA_INDEXDATA_REPORT_URL;


    @Autowired
    CsMessageClient csMessageClient;
    /**
     * 调用webservice接口并返回数据
     *
     * @param methodName
     * @param encmsg
     * @return
     */
    @Override
    public String webService(String methodName, String encmsg) {
        // 北互金目前三个方法的参数统一
        String[] params = {"arg0", "arg1", "arg2"};
        return WebServiceUtil.webService(BIFA_END_POINT, BIFA_NAME_SPACE, methodName, params, new Object[]{BIFA_LOGIN_NAME, BIFA_PASS_WORD, encmsg});
    }

    /**
     * 上报数据
     *
     * @param
     * @return
     */
    @Override
    public <T extends BaseHgDataReportEntityVO> T reportData(String methodName, T data) {
        try {
            String encmsg = JSONObject.toJSONString(data);
            String result = this.webService(methodName, encmsg);
            BifaReportResultBean reportResult = JSONObject.parseObject(result, BifaReportResultBean.class);
            data.setErrCode(reportResult.getReCode());
            data.setErrDesc(reportResult.getMessage());

            if (BifaCommonConstants.SUCCESSCODE.equals(reportResult.getReCode())) {
                data.setReportStatus("1");
            } else if (BifaCommonConstants.REPORTEDCODE.equals(reportResult.getReCode())
                    && reportResult.getMessage().indexOf(BifaCommonConstants.EXIST) >= 0 ){
                //已经上报过的状态置为7
                data.setReportStatus("7");
            } else {
                data.setReportStatus("9");
            }

        } catch (Exception e) {
            data.setReportStatus("9");
            data.setErrCode(BifaCommonConstants.ERRCODE);
            data.setErrDesc(BifaCommonConstants.ERRDESC);
            logger.error(logHeader + "北互金上报数据失败！！！", e);
        } finally {
            logger.info("北互金上报结果:"+JSONObject.toJSONString(data));
        }
        return data;
    }

    /**
     * 产品分类转换
     * @param projectType
     * @return
     */
    protected String convertProductMark(Integer projectType) {
        switch (projectType) {
            case 4:
                return "新手标";
            default:
                return "散标";
        }
    }

    /**
     * 获取用户索引信息
     * @return
     */
    @Override
    public BifaUserInfoSHA256EntityVO selectUserIdToSHA256(JSONObject jsonObject) {
        return csMessageClient.selectUserIdToSHA256(jsonObject);
    }

}
