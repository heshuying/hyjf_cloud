/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.util;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.validator.Validator;
import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.io.IOException;


/**
 * @author 迁移 by jijun
 * @version WebServiceUtil, v0.1 2019/1/15
 */
public class WebServiceUtil {

    /**
     * webService调用接口
     * @param endPoint wsdl地址
     * @param nameSpace wsdl返回xml中的namespace
     * @param methodName wsdl中的方法名称
     * @param params 参数集合
     * @param objects 需要传入的参数
     */
     public static String webService(String endPoint,String nameSpace,String methodName,String[] params,Object[] objects){
        try {

            // wsdl地址
            if (StringUtils.isBlank(endPoint)) {
                return jsonMessage("wsdl地址为空！！", "999999").toString();
            }
            // nameSpace
            if (StringUtils.isBlank(nameSpace)) {
                return jsonMessage("nameSpace为空！！", "999999").toString();
            }
            // methodName方法名
            if (StringUtils.isBlank(methodName)) {
                return jsonMessage("methodName方法名为空！！", "999999").toString();
            }
            if (params.length != objects.length){
                return jsonMessage("传入的参数名与参数个数不一致！！", "999999").toString();
            }

            AxisProperties.setProperty("axis.socketSecureFactory","com.hyjf.common.util.MySocketFactory");

            // 直接引用远程的wsdl文件
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setOperationName(new QName(nameSpace, methodName));
            call.setTargetEndpointAddress(endPoint);
            // 接口的参数
            for(String param : params){
                call.addParameter(param, org.apache.axis.encoding.XMLType.XSD_DATE, javax.xml.rpc.ParameterMode.IN);
            }
            // 设置返回类型
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
            String result = (String) call.invoke(objects);
            // 给方法传递参数，并且调用方法
            return result;
        } catch (Exception e) {
            return jsonMessage(e.toString(), "999999").toString();
        }
    }

    /**
     * 拼装返回信息
     *
     * @param message
     * @param reCode
     * @return
     */
    private static JSONObject jsonMessage(String message, String reCode) {
        JSONObject jo = null;
        if (Validator.isNotNull(message)) {
            jo = new JSONObject();
            jo.put("reCode", reCode);
            jo.put("message", message);
        }
        return jo;
    }

    public static void main(String[] args) throws IOException {
        String endPoint = "https://test.bjp2p.com.cn:8443/platformService?wsdl";
        String nameSpace = "http://supervise.service.app.mp.zkbc.net/";
        String[] params = {"arg0", "arg1", "arg2"};
        String methodName = "productRegistration";
        String loginName = "huiyingjingfu@20181101";
        String passWord = "huiyingjinfu@#20181101";
        String encMsg = "{\"allow_transfer\":\"0\",\"amount\":\"50000.00\",\"amount_limmtl\":\"100000000\",\"amount_limmts\":\"100\",\"bad_debt_limmit\":\"3月\",\"borrow_name_idcard_digest\":\"75259f1748aeabd2fbc8c4e072ee9ab9ba9da742f01606891213a3bd4d51af96\",\"borrow_sex\":\"男\",\"close_limmit\":\"0\",\"collateral_desc\":\"\",\"collateral_info\":\"\",\"createTime\":1548409429077,\"loan_credit_rating\":\"AA\",\"loan_type\":\"抵质押\",\"overdue_limmit\":\"到期还款日当天24点未提交还款的标的\",\"pay_type\":\"4\",\"product_mark\":\"散标\",\"product_name\":\"放款异常测试1\",\"product_reg_type\":\"01\",\"project_source\":\"合作机构推荐\",\"rate\":\"0.005833\",\"risk_margin\":\"0\",\"security_info\":\"\",\"security_type\":\"抵押\",\"service_cost\":\"2300.00\",\"source_code\":\"BJ20181101001\",\"source_product_code\":\"HCD19010005\",\"source_product_url\":\"https://www.hyjf.com/bank/web/borrow/getBorrowDetail.do?borrowNid=HCD19010005\",\"term\":\"4\",\"term_type\":\"月\",\"updateTime\":1548409429077}";
        String result = webService(endPoint, nameSpace, methodName, params, new Object[]{loginName, passWord, encMsg});
        System.out.println(result);
    }

}
