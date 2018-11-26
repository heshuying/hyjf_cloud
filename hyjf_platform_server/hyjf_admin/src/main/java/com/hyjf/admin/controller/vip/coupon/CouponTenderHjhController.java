package com.hyjf.admin.controller.vip.coupon;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponTenderHjhService;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/03 16:17
 */
@Api(value = "VIP管理汇计划列表",tags ="VIP管理汇计划列表")
@RestController
@RequestMapping("/hyjf-admin/coupon/tender/hjh")
public class CouponTenderHjhController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponTenderHjhController.class);
    @Autowired
    private CouponTenderHjhService couponTenderHjhService;

    @ApiOperation(value = "页面初始化", notes = "汇计划使用列表")
    @PostMapping("/init")
    public AdminResult<ListResult<CouponTenderVo>> couponListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        Integer count = this.couponTenderHjhService.countRecord(couponTenderRequest);
        lrs.setCount(count);
        List<CouponTenderCustomize> recordList = null;
        if (count != null && count > 0) {
            String investTotal=this.couponTenderHjhService.queryInvestTotalHjh(couponTenderRequest);
            recordList = this.couponTenderHjhService.getRecordList(couponTenderRequest);
            couponTenderHztVo.setInvestTotal(investTotal);
        }
        couponTenderHztVo.setRecordList(recordList==null?new ArrayList():recordList);
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponTenderRequest.getCurrPage(), couponTenderRequest.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面详情", notes = "汇计划页面详情")
    @PostMapping("/coupondetail")
    public AdminResult<ListResult<CouponTenderVo>> couponDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderHRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        if(null != couponTenderHRequest.getCouponUserId()){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            // 优惠券发放编号
            paramMap.put("couponUserId", couponTenderHRequest.getCouponUserId());
            paramMap.put("userFlag", 1);

            //详情
            CouponTenderDetailVo detail=new CouponTenderDetailVo();
            detail=couponTenderHjhService.getCouponTenderDetailCustomize(paramMap);
            //回款列表
            List<CouponRecoverVO> list=
                    couponTenderHjhService.getCouponRecoverCustomize(paramMap);
            //操作平台
            Map<String, String> map =  CacheUtil.getParamNameMap("CLIENT");
            for (String key : map.keySet()) {
                System.out.println("key= "+ key + " and value= " + map.get(key));
            }

            //处理优惠券使用平台，使用项目
            detail = couponTenderHjhService.dealDetail(detail,map);

            couponTenderHztVo.setDetail(detail);
            couponTenderHztVo.setCouponRecoverlist(list);
            lrs.setData(couponTenderHztVo);
            return new AdminResult<>(lrs);
        }else{
            return new AdminResult(BaseResult.FAIL,"请选择用户优惠券");
        }
    }

    /**
     * 导出excel
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "优惠券使用-汇计划列表", notes = "优惠券使用-汇计划列表")
    @PostMapping("/exportAction")
    public void exportActionByDay(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) throws Exception {
        // 封装查询条件
        //设置默认查询时间
        if(StringUtils.isEmpty(couponTenderRequest.getTimeStartSrch())){
            couponTenderRequest.setTimeStartSrch(GetDate.getDate("yyyy-MM-dd") + " 00:00:00");
        }
        if(StringUtils.isEmpty(couponTenderRequest.getTimeEndSrch())){
            couponTenderRequest.setTimeEndSrch(GetDate.getDate("yyyy-MM-dd") + " 23:59:59");
        }
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "优惠券使用-汇计划列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        couponTenderRequest.setLimitFlg(true);
        //请求第一页5000条
        couponTenderRequest.setPageSize(defaultRowMaxCount);
        couponTenderRequest.setCurrPage(1);
        // 需要输出的结果列表
        CouponTenderResponse recordList = this.couponTenderHjhService.getRecordExport(couponTenderRequest);
        Integer totalCount = recordList.getResultList().size();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();

        String investTotal=this.couponTenderHjhService.queryInvestTotalHjh(couponTenderRequest);
        //总条数
        String[] sumSmsCount = new String[]{"合计", "","","", "", "", "","","", "", "￥"+ (investTotal == null ? "" : investTotal), "","",""};
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList(),sumSmsCount);
        }else{
            //实现多个参数判断返回问题
            for(CouponTenderCustomize couponTenderCustomize : recordList.getResultList()){
                if("1".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota("￥"+couponTenderCustomize.getCouponQuota());
                }
                if("2".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota(couponTenderCustomize.getCouponQuota()+"%");
                }
                if("3".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota("￥"+couponTenderCustomize.getCouponQuota());
                }
            }
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.getResultList(),sumSmsCount);
        }
        for (int i = 1; i < sheetCount; i++) {
            couponTenderRequest.setPageSize(defaultRowMaxCount);
            couponTenderRequest.setCurrPage(i+1);
            CouponTenderResponse recordList2 = this.couponTenderHjhService.getRecordExport(couponTenderRequest);
            //实现多个参数判断返回问题
            for(CouponTenderCustomize couponTenderCustomize : recordList2.getResultList()){
                if("1".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota("￥"+couponTenderCustomize.getCouponQuota());
                }
                if("2".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota(couponTenderCustomize.getCouponQuota()+"%");
                }
                if("3".equals(couponTenderCustomize.getCouponType())){
                    couponTenderCustomize.setCouponQuota("￥"+couponTenderCustomize.getCouponQuota());
                }
            }
            if (recordList2 != null && recordList2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                if(i==sheetCount-1){
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2.getResultList(),sumSmsCount);
                }else {
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList2.getResultList());
                }
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, URLEncoder.encode(fileName, CustomConstants.UTF8), workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("orderId", "订单号");
        map.put("username", "用户名");
        map.put("couponUserCode", "优惠券id");
        map.put("couponCode", "优惠券类型编号");
        map.put("couponTypeStr", "优惠券类型");
        map.put("couponQuota", "面值");
        map.put("couponSource", "来源");
        map.put("couponContent", "内容");
        map.put("borrowNid", "智投编号");
        map.put("account", "授权服务金额");
        map.put("borrowPeriod", "项目期限");
        map.put("borrowApr", "年化收益");
        map.put("operatingDeck", "操作平台");
        map.put("orderDate", "使用时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter operatingDeck = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String operatingDeck = (String) object;
                //操作平台
                Map<String, String> map =  CacheUtil.getParamNameMap("CLIENT");
                for (String key : map.keySet()) {
                    //System.out.println("key= "+ key + " and value= " + map.get(key));
                    if(operatingDeck.equals(key)){
                        operatingDeck = map.get(key);
                    }
                }
                return operatingDeck;
            }
        };
        mapAdapter.put("operatingDeck", operatingDeck);
        return mapAdapter;
    }
}
