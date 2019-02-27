package com.hyjf.cs.trade.util;

import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.AsteriskProcessUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.BaseBean;
import com.hyjf.cs.trade.bean.BorrowDetailBean;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 标的工具类
 * @author zhangyk
 * @date 2018/7/2 16:37
 */
public class ProjectConstant {

    private static Logger logger = LoggerFactory.getLogger(ProjectConstant.class);


     public static final String REQUEST_HOME = "/hyjf-app";

     public static final String REQUEST_MAPPING = "/project";

     public static final String PROJECT_DETAIL_ACTION = "/getProjectDetail";

     public static final String APP_PAGE = "page";

     public static final String APP_PROJECT_TOTAL = "projectTotal";

     public static final String APP_PROJECT_LIST = "projectList";

     public static final String CREDIT_DETAIL = "/transfer";

     public static final String APP_REQUEST_MAPPING = "/hyjf-app/projectlist";

     public static final String APP_CREDIT_LIST_METHOD = "/creditList";

     public static final String APP_PLAN_LIST_METHOD = "/planList";

     public static final String APP_BORROW_PROJECT_METHOD = "/borrowProjectList";

    /**
     * 加入条件
     */
    public  static  final String PLAN_ADD_CONDITION = "加入金额{0}元起，且以{1}元的倍数递增";

    /**
     * 交易详情类请求地址
     */
    public static final String USER_TRADE_REQUEST_MAPPING = "/user/trade";


    /**
     * 交易详情-交易类型
     */
    public static final String USER_TRADE_TYPES_ACTION = "/getTradeTypes";


    /**
     * 计息时间
     */
    // mod by nxl 智投服务修改计息时间
//    public static String PLAN_ON_ACCRUAL = "计划进入锁定期后开始计息";
    public static String PLAN_ON_ACCRUAL = "从授权出借本金全部放款成功之日开始，至服务回报期限届满。";
    /**
     * 汇计划类型简称
     */
    public static final String PLAN_TYPE_NAME = "HJH";

    /*请求参数 start*/
    /*散标借款参数*/
    public static final String PARAM_BORROW_NID = "borrowNid";

    /*债转参数*/
    public static final String PARAM_CREDIT_NID = "creditNid";

    /*计划参数*/
    public static final String METHOD_PLAN = "/plan/";

    public static final String PARAM_PLAN_NID = "planNid";

    public static final String PARAM_APP_PLAN_NID = "planId";

    public static final String PARAM_USER_ID = "userId";

    public static final String PARAM_BORROW_TYPE = "borrowType";

    public static final String RES_PROJECT_DETAIL = "projectDetail";

    public static final String RES_PROJECT_INFO = "projectInfo";

    /*app首页requestUrl*/
    public static final String APP_HOME_REQUEST_DOMAIN = "/homepage";

    /*app首页请求方法*/
    public static final String APP_HOME_DATA_URL = "/getProjectList";

    /*   --------------------------  web端 开始 --------------------------------*/
    /*顶部统计数据 返回值*/
    public static final String WEB_PLAN_TOTAL_DATA = "totalData";

    public static final String WEB_PLAN_LIST = "resultList";

    /*汇计划接口相关 开始*/
    public static final String HJH_DATA_ACCEDE_ACCOUNT_TOTAL = "accedeAccountTotal";

    public static final String HJH_DATA_INTEREST_TOTAL = "interestTotal";

    public static final String HJH_DATA_ACCEDE_TIMES = "accedeTimes";

    /*展示给app的计划详情链接*/
    public static final String HJH_DETAIL_INFO_URL = "/plan/";
    /*汇计划接口相关 结束*/



    /*   --------------------------  web端 结束 --------------------------------*/

    /*  ----------------------------- api端 开始------------------------------------*/

    public static final String API_METHOD_BORROW_LIST = "getBorrowList";

    public static final String  API_METHOD_INVEST_LIST = "getInvestList";

    public static final String API_METHOD_BORROW_DETAIL = "getBorrowDetail";

    /*  -----------------------------api端 结束------------------------------------*/


    /**
     * 验证外部请求签名
     *
     * @param paramBean
     * @return
     */
    public static boolean verifyRequestSign(BaseBean paramBean, String methodName) {

        String sign = org.apache.commons.lang3.StringUtils.EMPTY;

        // 机构编号必须参数
        String instCode = paramBean.getInstCode();
        if (org.apache.commons.lang.StringUtils.isEmpty(instCode)) {
            return false;
        }
        if (API_METHOD_BORROW_LIST.equals(methodName)){
            ApiBorrowReqBean bean = (ApiBorrowReqBean) paramBean;
            sign = bean.getInstCode() + bean.getBorrowStatus() + bean.getTimestamp();
        }else if(API_METHOD_BORROW_DETAIL.equals(methodName)){
            ApiBorrowReqBean bean = (ApiBorrowReqBean) paramBean;
            sign = bean.getInstCode() + bean.getBorrowNid() + bean.getTimestamp();
        }else if (API_METHOD_INVEST_LIST.equals(methodName)){
            ApiInvestListReqBean bean = (ApiInvestListReqBean) paramBean;
            sign =  bean.getInstCode() + bean.getStartTime() + bean.getEndTime() + bean.getTimestamp();

        }

        return ApiSignUtil.verifyByRSA(instCode, paramBean.getChkValue(), sign);

    }






    /*请求参数 end*/

    /**
     * 计算更新时间
     *
     * @param timeLoan
     * @param timeRepay
     * @return
     */
    public static String getUpdateTime(Integer timeLoan, Integer timeRepay) {
        if (timeLoan == null) {
            return "";
        }

        Integer timeCurr = GetDate.getNowTime10();
        if (timeRepay != null && timeCurr > timeRepay) {
            timeCurr = timeRepay;
        }

        Integer timeDiff = timeCurr - timeLoan;
        Integer timeDiffMonth = timeDiff / (60 * 60 * 24 * 31);

        Calendar timeLoanCal = Calendar.getInstance();
        timeLoanCal.setTimeInMillis(timeLoan * 1000L);

        if (timeDiffMonth >= 1) {
            timeLoanCal.add(Calendar.MONTH, timeDiffMonth);
        }

        return GetDate.formatDate(timeLoanCal);
    }


    /**
     * 封装项目详情页
     *
     * @param objBean
     * @param type        1 基础信息 2资产信息 3项目介绍 4信用状况 5审核状况
     * @param borrowType  1借款人 2企业借款
     * @param borrowLevel 信用评级
     * @return
     */
    public static  List<BorrowDetailBean> packDetail(Object objBean, int type, int borrowType, String borrowLevel) {
        List<BorrowDetailBean> detailBeanList = new ArrayList<BorrowDetailBean>();
        String currencyName = "元";
        // 得到对象
        Class c = objBean.getClass();
        // 得到方法
        Field fieldlist[] = c.getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++) {
            // 获取类属性
            Field f = fieldlist[i];
            // 得到方法名
            String fName = f.getName();
            try {
                // 参数方法获取
                String paramName = fName.substring(0, 1).toUpperCase() + fName.substring(1, fName.length());
                // 取得结果
                Method getMethod = c.getMethod(BankCallConstant.GET + paramName);
                if (getMethod != null) {
                    Object result = getMethod.invoke(objBean);
                    // 结果不为空时
                    if (Validator.isNotNull(result)) {
                        //封装bean
                        BorrowDetailBean detailBean = new BorrowDetailBean();
                        detailBean.setId(fName);
                        detailBean.setVal(result.toString());
                        if (type == 1) {
                            if (borrowType == 2) {//个人借款
                                switch (fName) {
                                    case "name":
                                        detailBean.setKey("姓名");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "cardNo":
                                        detailBean.setKey("身份证号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "sex":
                                        detailBean.setKey("性别");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("男");
                                        } else {
                                            detailBean.setVal("女");
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "old":
                                        if (!"0".equals(detailBean.getVal())) {
                                            detailBean.setKey("年龄");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "merry":
                                        if (!("0".equals(result.toString()) || result.toString() == null)) {
                                            detailBean.setKey("婚姻状况");
                                            if ("1".equals(result.toString())) {
                                                detailBean.setVal("已婚");
                                            } else if ("2".equals(result.toString())) {
                                                detailBean.setVal("未婚");
                                            } else if ("3".equals(result.toString())) {
                                                detailBean.setVal("离异");
                                            } else if ("4".equals(result.toString())) {
                                                detailBean.setVal("丧偶");
                                            }
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "city":
                                        detailBean.setKey("工作城市");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "domicile":
                                        detailBean.setKey("户籍地");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "position":
                                        detailBean.setKey("职业类型");
                                        if ("10000".equals(result.toString())) {
                                            detailBean.setVal("机关企事业单位负责人");
                                        } else if ("20000".equals(result.toString())) {
                                            detailBean.setVal("专业技术人员");
                                        } else if ("30000".equals(result.toString())) {
                                            detailBean.setVal("办事人员和有关人员");
                                        } else if ("40000".equals(result.toString())) {
                                            detailBean.setVal("会生产服务和生活服务人员");
                                        } else if ("50000".equals(result.toString())) {
                                            detailBean.setVal("农、林、牧、渔业生产及辅助人员");
                                        } else if ("60000".equals(result.toString())) {
                                            detailBean.setVal("生产制造及有关人员");
                                        } else if ("70000".equals(result.toString())) {
                                            detailBean.setVal("军人");
                                        } else if ("80000".equals(result.toString())) {
                                            detailBean.setVal("不便分类的其他从业人员");
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "annualIncome":
                                        detailBean.setKey("年收入");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            } else {//企业借款

                                switch (fName) {
                                    case "currencyName":
                                        currencyName = detailBean.getVal();
                                        break;
                                    case "username":
                                        detailBean.setKey("借款主体");
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), detailBean.getVal().length() - 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "city":
                                        detailBean.setKey("注册地区");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "regCaptial":
                                        detailBean.setKey("注册资本");
                                        if (StringUtils.isNotBlank(detailBean.getVal())) {
                                            detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + currencyName);
                                        }
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "comRegTime":
                                        detailBean.setKey("注册时间");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "socialCreditCode":
                                        detailBean.setKey("统一社会信用代码");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "registCode":
                                        detailBean.setKey("注册号");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 10));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "legalPerson":
                                        detailBean.setKey("法定代表人");
                                        //数据脱敏
                                        detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 1, 2));
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "industry":
                                        detailBean.setKey("所属行业");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "mainBusiness":
                                        detailBean.setKey("主营业务");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "overdueReport":
                                        detailBean.setKey("征信报告逾期情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "debtSituation":
                                        detailBean.setKey("重大负债状况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    case "otherBorrowed":
                                        detailBean.setKey("其他平台借款情况");
                                        detailBeanList.add(detailBean);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 2) {
                            switch (fName) {
                                case "housesType":
                                    detailBean.setKey("资产类型");
                                    //String houseType = this.borrowService.getParamName("HOUSES_TYPE", detailBean.getVal());
                                    String key = "hyjf_param_name:HOUSES_TYPE";
                                    Map<String, String> houseTypeMap = RedisUtils.hgetall(key);
                                    if (!CollectionUtils.isEmpty(houseTypeMap)) {
                                        detailBean.setVal(houseTypeMap.get(detailBean.getVal()));
                                    } else {
                                        detailBean.setVal("住宅");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesArea":
                                    detailBean.setKey("资产面积");
                                    detailBean.setVal(detailBean.getVal() + "m<sup>2</sup>");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesCnt":
                                    detailBean.setKey("资产数量");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesToprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "housesBelong":
                                    detailBean.setKey("资产所属");
                                    detailBeanList.add(detailBean);
                                    break;
                                //车辆
                                case "brand":
                                    BorrowDetailBean carBean = new BorrowDetailBean();
                                    carBean.setId("carType");
                                    carBean.setKey("资产类型");
                                    carBean.setVal("车辆");
                                    detailBeanList.add(carBean);
                                    detailBean.setKey("品牌");
                                    detailBeanList.add(detailBean);
                                    break;

                                case "model":
                                    detailBean.setKey("型号");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "place":
                                    detailBean.setKey("产地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "price":
                                    detailBean.setKey("购买价格");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "toprice":
                                    detailBean.setKey("评估价值");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "number":
                                    detailBean.setKey("车牌号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 2, 4));
                                    detailBeanList.add(detailBean);
                                    break;
                                case "registration":
                                    detailBean.setKey("车辆登记地");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "vin":
                                    detailBean.setKey("车架号");
                                    //数据脱敏
                                    detailBean.setVal(AsteriskProcessUtil.getAsteriskedValue(detailBean.getVal(), 4, 5));
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }

                        } else if (type == 3) {
                            switch (fName) {
                                case "borrowContents":
                                    detailBean.setKey("项目信息");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "fianceCondition":
                                    detailBean.setKey("财务状况 ");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "financePurpose":
                                    detailBean.setKey("借款用途");
                                    if ("01".equals(result.toString())) {
                                        detailBean.setVal("个人消费");
                                    } else if ("02".equals(result.toString())) {
                                        detailBean.setVal("个人经营");
                                    } else if ("03".equals(result.toString())) {
                                        detailBean.setVal("个人资金周转");
                                    } else if ("04".equals(result.toString())) {
                                        detailBean.setVal("房贷");
                                    } else if ("05".equals(result.toString())) {
                                        detailBean.setVal("企业经营");
                                    } else if ("06".equals(result.toString())) {
                                        detailBean.setVal("企业周转");
                                    } else if ("99".equals(result.toString())) {
                                        detailBean.setVal("其他");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "monthlyIncome":
                                    detailBean.setKey("月薪收入");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(detailBean.getVal());
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "payment":
                                    detailBean.setKey("还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "firstPayment":
                                    detailBean.setKey("第一还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "secondPayment"://还没有
                                    detailBean.setKey("第二还款来源");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "costIntrodution":
                                    detailBean.setKey("费用说明");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 4) {
                            switch (fName) {
                                case "overdueTimes":
                                    detailBean.setKey("在平台逾期次数");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "overdueAmount":
                                    detailBean.setKey("在平台逾期金额");
                                    if (StringUtils.isNotBlank(detailBean.getVal())) {
                                        detailBean.setVal(CustomConstants.DF_FOR_VIEW.format(new BigDecimal(detailBean.getVal())) + "元");
                                    }
                                    detailBeanList.add(detailBean);
                                    break;
                                case "litigation":
                                    detailBean.setKey("涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        } else if (type == 5) {
                            if (borrowType == 2) {
                                switch (fName) {
                                    case "isCard":
                                        detailBean.setKey("身份证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isIncome":
                                        detailBean.setKey("收入状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isCredit":
                                        detailBean.setKey("信用状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicle":
                                        detailBean.setKey("车辆状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isDrivingLicense":
                                        detailBean.setKey("行驶证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isVehicleRegistration":
                                        detailBean.setKey("车辆登记证");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isMerry":
                                        detailBean.setKey("婚姻状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isWork":
                                        detailBean.setKey("工作状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAccountBook":
                                        detailBean.setKey("户口本");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                switch (fName) {
                                    case "isCertificate":
                                        detailBean.setKey("企业证件");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isOperation":
                                        detailBean.setKey("经营状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isFinance":
                                        detailBean.setKey("财务状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isEnterpriseCreidt":
                                        detailBean.setKey("企业信用");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isLegalPerson":
                                        detailBean.setKey("法人信息");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isAsset":
                                        detailBean.setKey("资产状况");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isPurchaseContract":
                                        detailBean.setKey("购销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    case "isSupplyContract":
                                        detailBean.setKey("供销合同");
                                        if ("1".equals(result.toString())) {
                                            detailBean.setVal("已审核");
                                            detailBeanList.add(detailBean);
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                        } else if (type == 6) {
                            switch (fName) {
                                case "isFunds":
                                    detailBean.setKey("借款资金运用情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isManaged":
                                    detailBean.setKey("借款人经营状况及财务状况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isAbility":
                                    detailBean.setKey("借款人还款能力变化情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isOverdue":
                                    detailBean.setKey("借款人逾期情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isComplaint":
                                    detailBean.setKey("借款人涉诉情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                case "isPunished":
                                    detailBean.setKey("借款人受行政处罚情况");
                                    detailBeanList.add(detailBean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                continue;
            }
        }
        if (type == 1 || type == 4) {
            //信用评级单独封装
            BorrowDetailBean detailBean = new BorrowDetailBean();
            detailBean.setId("borrowLevel");
            detailBean.setKey("信用评级");
            detailBean.setVal(borrowLevel);
            detailBeanList.add(detailBean);
        }
        return detailBeanList;
    }

}
