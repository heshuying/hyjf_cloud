package com.hyjf.pay.lib.chinapnr.util;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.chinapnr.MerPriv;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.PropUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;

/**
 * <p>
 * ChinapnrUtil
 * </p>
 *
 * @author gogtz-t
 * @version 1.0.0
 */
public class ChinapnrUtil {
    private static Logger log = LoggerFactory.getLogger(ChinapnrUtil.class);

    /** THIS_CLASS */
    private static final String THIS_CLASS = ChinapnrUtil.class.getName();

    /** 跳转的jsp页面 */
    private static final String SEND_JSP = "/chinapnr/chinapnr_send";

    /** 接口路径(页面) */
    private static final String REQUEST_MAPPING_CALLAPI = "/callapi.json";

    /** 接口路径(后台) */
    private static final String REQUEST_MAPPING_CALLAPIBG = "/callapibg.json";

    /**
     * 调用接口(页面)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    public static ModelAndView callApi(ChinapnrBean bean) throws Exception {
        String methodName = "callApi";
        log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");

        // 跳转页面
        ModelAndView modelAndView = new ModelAndView(SEND_JSP);

        try {
            // 取出调用汇付接口的url
            String payurl = PropUtils.getSystem(CustomConstants.HYJF_PAY_URL);
            if (Validator.isNull(payurl)) {
                throw new Exception("接口工程URL不能为空");
            }

            Map<String, String> allParams = bean.getAllParams();
            allParams.put("LogUserId", bean.getLogUserId() == null ? "" : String.valueOf(bean.getLogUserId()));
            allParams.put("LogRemark", bean.getLogRemark() == null ? "" : bean.getLogRemark());
            allParams.put("LogType", bean.getLogType() == null ? "" : bean.getLogType());
            allParams.put("LogBorrowNid", bean.getLogBorrowNid() == null ? "" : bean.getLogBorrowNid());
            allParams.put("LogIp", bean.getLogIp() == null ? "" : bean.getLogIp());
            // 调用汇付接口
            String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPI, allParams);

            // 将返回字符串转换成Map
            if (Validator.isNotNull(result)) {
                Map<String, String> map = JSONObject.parseObject(result, Map.class);
                modelAndView.addAllObjects(map);
            }
        } catch (Exception e) {
           log.error(String.valueOf(e));
            throw e;
        } finally {
            log.debug( "[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }

        return modelAndView;
    }

    /**
     * 调用接口
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public static ChinapnrBean callApiBg(ChinapnrBean bean) {
        String methodName = "callApiBg";
        log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");

        ChinapnrBean ret = null;
        try {
            // bean转换成参数
            bean.convert();
            if (bean.getMerPriv()!=null) {
                if (bean.getMerPriv().contains("{")) {
                    
                }
            }
            // 取出调用汇付接口的url
            String payurl = PropUtils.getSystem(CustomConstants.HYJF_PAY_URL);
            if (Validator.isNull(payurl)) {
                throw new Exception("接口工程URL不能为空");
            }

            // 调用汇付接口
            String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPIBG, bean.getAllParams());
            log.info("请求pay工程返回结果：" + result);
//            System.out.println("请求pay工程返回结果：" + result);
            if (Validator.isNotNull(result)) {
                // 将返回字符串转换成ChinapnrBean
                ret = JSONObject.parseObject(result, ChinapnrBean.class);
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        } finally {
            log.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }

        return ret;
    }

    /**
     * 调用接口(账户余额查询专用)
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public static JSONObject callApiBgForYuE(ChinapnrBean bean) {
        String methodName = "callApiBg";
        log.info("[调用接口开始, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        JSONObject jsonObject = new JSONObject();
        try {
            // bean转换成参数
            bean.convert();

            // 取出调用汇付接口的url
            String payurl = PropUtils.getSystem(CustomConstants.HYJF_PAY_URL);
            if (Validator.isNull(payurl)) {
                throw new Exception("接口工程URL不能为空");
            }

            // 调用汇付接口
            String result = HttpDeal.post(payurl + REQUEST_MAPPING_CALLAPIBG, bean.getAllParams());
            if (StringUtils.isNotEmpty(result)) {
                jsonObject = JSONObject.parseObject(result);
            }

        } catch (Exception e) {
            log.error(String.valueOf(e));
        } finally {
            log.debug("[调用接口结束, 消息类型:" + (bean == null ? "" : bean.getCmdId()) + "]");
        }

        return jsonObject;
    }

    /**
     * 取得页面返回 URL
     */
    public static String getRetUrl() {
        return ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_RETURN_URL);
    }

    /**
     * 企业用户绑定取得页面返回 URL
     */
    public static String getBindRetUrl() {
        return ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_BIND_RETURN_URL);
    }

    
    /**
     * 取得商户后台应答地址
     */
    public static String getBgRetUrl() {
        return ChinaPnrPropUtils.getSystem(ChinaPnrConstant.PROP_CALLBACK_URL);
    }

    /**
     * 设置UUID
     *
     * @param bean
     * @param id 
     */
    public static String setUUID(ChinapnrBean bean, long id) {
        MerPriv merPrivPo = bean.getMerPrivPo();
        if (null == merPrivPo) {
            merPrivPo = new MerPriv();
        }
        merPrivPo.setUuid(String.valueOf(id));
        String merPriv = "";
        try {
            merPriv = URLEncoder.encode(JSON.toJSONString(merPrivPo), CustomConstants.UTF8);
            bean.setMerPriv(merPriv);
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, merPriv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return merPriv;
    }

    /**
     * 设置UUID
     *
     * @param bean
     * @param id 
     */
    public static String setUUID(ChinapnrBean bean, String mongId) {
        MerPriv merPrivPo = bean.getMerPrivPo();
        if (null == merPrivPo) {
            merPrivPo = new MerPriv();
        }
        merPrivPo.setUuid(String.valueOf(mongId));
        String merPriv = "";
        try {
            merPriv = URLEncoder.encode(JSON.toJSONString(merPrivPo), CustomConstants.UTF8);
            bean.setMerPriv(merPriv);
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, merPriv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return merPriv;
    }

    /**
     * 取得UUID
     *
     * @param bean
     * @return
     */
    public static String getUUID(ChinapnrBean bean) {
        String uuid = bean.getMerPriv();
        bean.setMerPriv("");
        bean.set(ChinaPnrConstant.PARAM_MERPRIV, "");
        // 取得商户私有域
        return uuid;
    }

    public static void main(String[] args) {
        ChinapnrBean bean = new ChinapnrBean();
        bean.setVersion("10");
        bean.setCmdId("QueryBalanceBg");
        bean.setUsrCustId("6000060001672694");
        try {
            ChinapnrBean b = callApiBg(bean);
            System.out.println(b.getRespCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUUIDFeeFrom(ChinapnrBean bean) {
        String temp = bean.getMerPriv();
        // 设置商户私有域
        String uuid = bean.getUuid();
        if (!"".equals(temp)) {
            bean.setMerPriv(uuid + "," + temp);
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, uuid + "," + temp);
        } else {
            bean.setMerPriv(uuid);
            bean.set(ChinaPnrConstant.PARAM_MERPRIV, uuid);
        }
    }

}
