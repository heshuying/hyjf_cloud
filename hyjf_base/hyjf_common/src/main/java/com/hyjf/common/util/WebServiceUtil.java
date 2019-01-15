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
     *
     * @param crtPath https协议问题证书存放地址
     * @param endPoint wsdl地址
     * @param nameSpace wsdl返回xml中的namespace
     * @param methodName wsdl中的方法名称
     * @param params 参数集合
     * @param objects 需要传入的参数
     */
     public static String webService(String crtPath,String endPoint,String nameSpace,String methodName,String[] params,Object[] objects){
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

//            // 不需要协议证书的跳过
//            if (StringUtils.isNotBlank(crtPath)) {
//                System.setProperty("javax.net.ssl.trustStore", crtPath);
//            }

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
            e.printStackTrace();
            return jsonMessage("系统异常！", "999999").toString();
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
        String crtPath = "D:\\Cloud\\WebService\\jssecacerts";
        String endPoint = "https://test.bjp2p.com.cn:8443/platformService?wsdl";
        String nameSpace = "http://supervise.service.app.mp.zkbc.net/";
        String[] params = {"arg0", "arg1", "arg2"};
        String methodName = "productRegistration";
        String loginName = "huiyingjingfu@20181101";
        String passWord = "huiyingjinfu@#20181101";
        String encMsg = "{\"loan_type\": \"信用/抵质押 \",\"remark\": \"测试标\",\"product_name\": \"心意贷\",\"collateral_info\": \"3月 \",\"project_source\": \"线上\",\"amount\": \"80000\",\"rate\": \"0.001 \",\"pay_type\": \"1\",\"product_reg_type\": \"01\",\"service_cost\": \"1000 \",\"loan_credit_rating\": \"A-F \",\"amount_limmts\": \"1\",\"product_mark\": \"企业经营贷\",\"amount_limmtl\": \"10000\",\"allow_transfer\": \"0 \",\"close_limmit\": \"12\",\"source_product_code\": \"XMF0011\",\"term_type\": \"年/月/天 \",\"source_code\": \"BJ20170831001\",\"bad_debt_limmit\": \"4月 \",\"term\": \"12 \",\"collateral_desc\": \"押品价值很高 \",\"borrow_sex\": \"女\",\"security_info\": \"4月 \",\"source_product_url\": \"http://www.nmyjd.com/invest/a20151100034.html\",\"security_type\": \"抵押担保 \",\"borrow_name_idcard_digest\": \"9113667bb95e7f109a9df2fef304bd5b223e188fd306f3a20d6e89e6be08424e\",\"overdue_limmit\": \"4月 \",\"risk_margin\": \"4000 \"}\n";
        String result = webService(crtPath, endPoint, nameSpace, methodName, params, new Object[]{loginName, passWord, encMsg});
        System.out.println(result);
    }

}
