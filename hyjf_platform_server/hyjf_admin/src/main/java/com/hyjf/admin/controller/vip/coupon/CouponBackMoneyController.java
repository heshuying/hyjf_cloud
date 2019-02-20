package com.hyjf.admin.controller.vip.coupon;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.response.CouponBackMoneyContResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yinhui
 * @version UtmController, v0.1 2018/7/06 15:17
 */
@Api(value = "VIP管理汇-直投/汇计划回款",tags ="VIP管理-汇直投/汇计划回款")
@RestController
@RequestMapping("/hyjf-admin/coupon")
public class CouponBackMoneyController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponBackMoneyController.class);
    @Autowired
    private CouponBackMoneyService couponBackMoneyService;
    /** 汇计划体验金查看权限 */
    public static final String HJHPERMISSIONSTY = "HJHBACKMONEYTY";
    /** 汇计划加息券查看权限 */
    public static final String HJHPERMISSIONSJX = "HJHBACKMONEYJX";
    /** 汇计划代金券查看权限 */
    public static final String HJHPERMISSIONSDJ = "HJHBACKMONEYDJ";
    /** 汇直投体验金查看权限 */
    public static final String HZTPERMISSIONSTY = "HZTBACKMONEYTY";
    /** 汇直投加息券查看权限 */
    public static final String HZTPERMISSIONSJX = "HZTBACKMONEYJX";
    /** 汇直投代金券查看权限 */
    public static final String HZTPERMISSIONSDJ = "HZTBACKMONEYDJ";


    @ApiOperation(value = "汇直投-代金券-回款使用列表", notes = "汇直投-代金券-回款使用列表")
    @PostMapping("/backmoneydj/hzt/init")
    @AuthorityAnnotation(key = HZTPERMISSIONSDJ, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult coupontDjListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {

        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        response = couponBackMoneyService.getdjHzt(couponBackMoneyCustomize);

        return new AdminResult(response);
    }

    @ApiOperation(value = "汇直投-加息券-回款使用列表", notes = "汇直投-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hzt/init")
    @AuthorityAnnotation(key = HZTPERMISSIONSTY, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponTenderVo>> couponJxListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {

        CouponBackMoneyContResponse responsehzt = new CouponBackMoneyContResponse();

        responsehzt = couponBackMoneyService.getjxHzt(couponBackMoneyCustomize);

        return new AdminResult(responsehzt);
    }

    @ApiOperation(value = "汇直投-体验金-回款使用列表", notes = "汇直投-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hzt/init")
    @AuthorityAnnotation(key = HZTPERMISSIONSJX, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponTenderVo>> couponTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.gettyHzt(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "汇计划-体验金-回款使用列表", notes = "汇计划-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hjh/init")
    @AuthorityAnnotation(key = HJHPERMISSIONSTY, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponTenderVo>> couponHjhTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.gettyHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "汇计划-代金券-回款使用列表", notes = "汇计划-代金券-回款使用列表")
    @PostMapping("/backmoneydj/hjh/init")
    @AuthorityAnnotation(key = HJHPERMISSIONSDJ, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponTenderVo>> coupontHjhDjListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.getdjHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "汇计划-加息券-回款使用列表", notes = "汇计划-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hjh/init")
    @AuthorityAnnotation(key = HJHPERMISSIONSJX, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CouponTenderVo>> couponHjhJxListInit(@RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.getjxHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    /**
     * 导出功能
     *
     */
    @PostMapping("/exportAction")
    public void exportAction(HttpServletRequest request,HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) throws Exception {
        String father = couponBackMoneyCustomize.getFather();
        String son = couponBackMoneyCustomize.getSon();
        if(StringUtils.isEmpty(father) || StringUtils.isEmpty(son)){
            return ;
        }
        //sheet默认最大行数
        int defaultRowMaxCount =Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        Map map=getCouponResult(father,son,couponBackMoneyCustomize);
        // 表格sheet名称
        String sheetName = (String)map.get("sheetName");
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        //查询总条数
        Integer totalCount =(Integer)map.get("totalCount");
        //workbook页数
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap(father,son);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter(son);
        String recoverInterest = (String)map.get("recoverInterest");
        String investTotal = (String)map.get("investTotal");
        //总条数
        String[] sumSmsCount = new String[]{"合计", "","","", "", "", "",recoverInterest,"", "", "", "",investTotal,"", ""};
        if("tyj".equals(son)){
            sumSmsCount = new String[]{"合计", "","","", "", "", "","", "", "",recoverInterest,"", "", "", "","",investTotal,"", "", "", "",""};
        }

        String sheetNameTmp = sheetName + "_第1页";

        //请求第一页5000条
        couponBackMoneyCustomize.setPageSize(defaultRowMaxCount);
        couponBackMoneyCustomize.setCurrPage(1);
        List<CouponBackMoneyCustomize> resultList = getCouponResultList(father,son,couponBackMoneyCustomize,totalCount);
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList(),sumSmsCount);
        }else{
            if(sheetCount == 1){
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList,sumSmsCount);
            }else{
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
            }
        }
        for (int i = 1; i < sheetCount; i++) {
            couponBackMoneyCustomize.setPageSize(defaultRowMaxCount);
            couponBackMoneyCustomize.setCurrPage(i+1);
            List<CouponBackMoneyCustomize> resultList2 = getCouponResultList(father,son,couponBackMoneyCustomize,totalCount);
            //实现多个参数判断返回问题
            if (!CollectionUtils.isEmpty(resultList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                if(i==sheetCount-1){
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2,sumSmsCount);
                }else {
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList2);
                }
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    public Map getCouponResult(String father,String son,CouponBackMoneyCustomize couponBackMoneyCustomize){
        Map map =new HashedMap();
        Integer count=0;
        String sheetName="";
        String recoverInterest ="";
        String investTotal = "";
        if("hzt".equals(father)){
            //体验金
            if("tyj".equals(son)){
                sheetName = "体验金回款列表";
                couponBackMoneyCustomize.setCouponType("1");
                count = couponBackMoneyService.countRecordHztTY(couponBackMoneyCustomize);
            }
            //加息券
            else if("jxq".equals(son)){
                sheetName = "加息券回款列表";
                couponBackMoneyCustomize.setCouponType("2");
                count = couponBackMoneyService.countRecordHztJX(couponBackMoneyCustomize);
            }

            //代金券
            else if("djq".equals(son)){
                sheetName = "代金券回款列表";
                couponBackMoneyCustomize.setCouponType("3");
                count =couponBackMoneyService.countRecordHztDJ(couponBackMoneyCustomize);
            }
            recoverInterest=couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            investTotal=couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);

        }else if("hjh".equals(father)){
            //体验金
            if("tyj".equals(son)){
                sheetName = "体验金回款列表";
                couponBackMoneyCustomize.setCouponType("1");
                count = couponBackMoneyService.countRecordHjhTY(couponBackMoneyCustomize);
            }
            //加息券
            else if("jxq".equals(son)){
                sheetName = "加息券回款列表";
                couponBackMoneyCustomize.setCouponType("2");
                count = couponBackMoneyService.countRecordHjhJX(couponBackMoneyCustomize);
            }
            //代金券
            else if("djq".equals(son)){
                sheetName = "代金券回款列表";
                couponBackMoneyCustomize.setCouponType("3");
                count = couponBackMoneyService.countRecordHjhDJ(couponBackMoneyCustomize);
            }
            recoverInterest = couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            investTotal = couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
        }
        map.put("totalCount",count);
        map.put("sheetName",sheetName);
        map.put("recoverInterest",StringUtils.isBlank(recoverInterest)?"￥0.00":"￥"+recoverInterest);
        map.put("investTotal",StringUtils.isBlank(investTotal)?"￥0.00":"￥"+investTotal);
        return map;

    }
    public List<CouponBackMoneyCustomize> getCouponResultList(String father,String son,CouponBackMoneyCustomize couponBackMoneyCustomize,int count){
        Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());
        couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
        couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());
        List<CouponBackMoneyCustomize> result=new ArrayList<>();
        if("hzt".equals(father)){
            //体验金
            if("tyj".equals(son)){
                result=couponBackMoneyService.getRecordListHztTY(couponBackMoneyCustomize);
            }
            //加息券
            else if("jxq".equals(son)){
                result=couponBackMoneyService.getRecordListHztJX(couponBackMoneyCustomize);
            }

            //代金券
            else if("djq".equals(son)){
                result=couponBackMoneyService.getRecordListHztDJ(couponBackMoneyCustomize);
            }

        }else if("hjh".equals(father)){
            //体验金
            if("tyj".equals(son)){
                result=couponBackMoneyService.getRecordListHjhTY(couponBackMoneyCustomize);
            }
            //加息券
            else if("jxq".equals(son)){
                result=couponBackMoneyService.getRecordListHjhJX(couponBackMoneyCustomize);
            }
            //代金券
            else if("djq".equals(son)){
                result=couponBackMoneyService.getRecordListHjhDJ(couponBackMoneyCustomize);
            }
        }
        return result;
    }

    private Map<String, String> buildMap(String father,String son) {
        Map<String, String> map = Maps.newLinkedHashMap();
        if("tyj".equals(son)){
            map.put("username", "用户名");
            map.put("truename", "姓名");
            map.put("mobile", "手机号");
            map.put("chinapnrUsrcustid", "客户号");
            map.put("nid", "投资订单号");
            map.put("borrowName", "项目名称");
            map.put("borrowPeriod", "项目期限");
            map.put("borrowApr", "项目年化");
            if("hzt".equals(father)){
                map.put("borrowNid", "项目编号");
            }else{
                map.put("borrowNid", "智投编号");
            }
            map.put("couponQuota", "体验金面值");
            map.put("couponProfitTime", "体验金收益期限");
            map.put("couponUserCode", "体验金编号");
            map.put("addTime", "出借时间");
            map.put("hcrAddTime", "放款时间");
            map.put("recoverTime", "应回款时间");
            map.put("recoverInterest", "回款金额");
            map.put("receivedFlg", "回款状态");
            map.put("transferId", "转载订单号");
            map.put("recoverYestime", "实际回款时间");
        }else{
            map.put("nid", "订单号");
            map.put("username", "用户名");
            map.put("couponUserCode", "优惠券id");
            map.put("couponCode", "优惠券类型编号");
            if("hzt".equals(father)){
                map.put("borrowNid", "项目编号");
            }else{
                map.put("borrowNid", "智投编号");
            }
            map.put("recoverPeriod", "回款期数");
            map.put("recoverInterest", "应回款（元）");
            map.put("transferId", "转账订单号");
            map.put("receivedFlgString", "状态");
            map.put("recoverTime", "应回款日期");
            map.put("addTime", "使用时间");
            map.put("recoverCapital", "授权服务金额");
            map.put("couponSource", "来源");
            map.put("couponContent", "内容");
        }
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter(String son) {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter dateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String)object;
                return StringUtils.isEmpty(value) ? StringUtils.EMPTY : value;
            }
        };
        IValueFormatter dateAdapter2 = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String)object;
                return StringUtils.isEmpty(value) ? "￥0.00": "￥"+value;
            }
        };
        IValueFormatter dateAdapter3 = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String)object;
                return StringUtils.isEmpty(value) ? StringUtils.EMPTY : value+"个月";
            }
        };
        IValueFormatter dateAdapter4 = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String)object;
                return StringUtils.isEmpty(value) ? StringUtils.EMPTY : value+"天";
            }
        };
        IValueFormatter dateAdapter5 = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String)object;
                if(value == null||value==""){
                    return "";
                }
                if("1".equals(value)){
                    value= "未回款";
                }
                if("2".equals(value)){
                    value="未领取";
                }
                if("3".equals(value)){
                    value= "转账中";
                }
                if("4".equals(value)){
                    value= "转账失败";
                }
                if("5".equals(value)){
                    value= "已领取";
                }
                if("6".equals(value)){
                    value= "已过期";
                }
                return value;
            }
        };
        if("tyj".equals(son)){
            mapAdapter.put("couponQuota", dateAdapter2);
            mapAdapter.put("recoverInterest", dateAdapter2);
            mapAdapter.put("borrowPeriod", dateAdapter3);
            mapAdapter.put("couponProfitTime", dateAdapter3);
            mapAdapter.put("receivedFlg", dateAdapter5);
        }else{
            mapAdapter.put("recoverCapital", dateAdapter2);
            mapAdapter.put("recoverInterest", dateAdapter2);
        }
        return mapAdapter;
    }
}
