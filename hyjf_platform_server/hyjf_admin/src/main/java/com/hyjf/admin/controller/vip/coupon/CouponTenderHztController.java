package com.hyjf.admin.controller.vip.coupon;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponTenderHjhService;
import com.hyjf.admin.service.coupon.CouponTenderHztService;
import com.hyjf.admin.utils.Page;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/03 16:17
 */
@Api(value = "VIP管理汇直投列表",tags ="VIP管理汇直投列表")
@RestController
@RequestMapping("/hyjf-admin/coupon/tender/hzt")
public class CouponTenderHztController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponTenderHztController.class);

    @Autowired
    private CouponTenderHztService couponTenderHztService;
    @Autowired
    private CouponTenderHjhService couponTenderHjhService;

    @ApiOperation(value = "页面初始化", notes = "汇直投使用列表")
    @PostMapping("/init")
    public AdminResult<ListResult<CouponTenderVo>> couponListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        Integer count = this.couponTenderHztService.countRecord(couponTenderRequest);
        lrs.setCount(count);
        List<CouponTenderCustomize>  recordList = null;
        if (count != null && count > 0) {
            String investTotal=this.couponTenderHztService.queryInvestTotalHzt(couponTenderRequest);
            recordList = this.couponTenderHztService.getRecordList(couponTenderRequest);
            couponTenderHztVo.setInvestTotal(investTotal);
        }
        couponTenderHztVo.setRecordList(recordList==null?new ArrayList():recordList);
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponTenderRequest.getCurrPage(), couponTenderRequest.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    /**
     * 导出汇直投优惠券使用功能
     *
     * @param couponTenderRequest
     */
    @ApiOperation(value = "优惠券使用-汇直投列表", notes = "优惠券使用-汇直投列表")
    @PostMapping("/exportAction")
    public void exportActionByDay(HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) throws Exception {
// 表格sheet名称
        String sheetName = "优惠券使用-汇直投列表";
        //设置默认查询时间
        if(StringUtils.isEmpty(couponTenderRequest.getTimeStartSrch())){
            couponTenderRequest.setTimeStartSrch(GetDate.getDate("yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(couponTenderRequest.getTimeEndSrch())){
            couponTenderRequest.setTimeEndSrch(GetDate.getDate("yyyy-MM-dd"));
        }
        List<CouponTenderCustomize> resultList  = this.couponTenderHztService.exoportRecordList(couponTenderRequest);
        String investTotal=this.couponTenderHztService.queryInvestTotalHzt(couponTenderRequest);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] {"序号", "订单号", "用户名","优惠券id","优惠券类型编号",
                "优惠券类型","面值","来源","内容","项目编号","投资金额","项目期限","年化收益" ,"操作平台" ,"使用时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    CouponTenderCustomize pInfo = resultList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        cell.setCellValue(pInfo.getOrderId());
                    }
                    else if (celLength == 2) {
                        cell.setCellValue(pInfo.getUsername());
                    }
                    else if (celLength == 3) {
                        cell.setCellValue(pInfo.getCouponUserCode());
                    }
                    else if (celLength == 4) {
                        cell.setCellValue(pInfo.getCouponCode());
                    }
                    else if (celLength == 5) {
                        cell.setCellValue(pInfo.getCouponTypeStr());
                    }
                    else if (celLength == 6) {
                        if("1".equals(pInfo.getCouponType())){
                            cell.setCellValue("￥"+pInfo.getCouponQuota());
                        }
                        if("2".equals(pInfo.getCouponType())){
                            cell.setCellValue(pInfo.getCouponQuota()+"%");
                        }
                        if("3".equals(pInfo.getCouponType())){
                            cell.setCellValue("￥"+pInfo.getCouponQuota());
                        }

                    }
                    else if (celLength == 7) {
                        cell.setCellValue(pInfo.getCouponFrom());
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(pInfo.getCouponContent());
                    }
                    else if (celLength == 9) {
                        cell.setCellValue(pInfo.getBorrowNid());
                    }
                    else if (celLength == 10) {
                        cell.setCellValue("￥"+pInfo.getAccount());
                    }
                    else if (celLength == 11) {
                        cell.setCellValue(pInfo.getBorrowPeriod());
                    }
                    else if (celLength == 12) {
                        cell.setCellValue(pInfo.getBorrowApr());
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(pInfo.getOperatingDeck());
                    }
                    else if (celLength == 14) {
                        cell.setCellValue(pInfo.getOrderDate());
                    }
                }
            }
            rowNum++;
            Row row = sheet.createRow(rowNum);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("合计");
            Cell cell2 = row.createCell(10);
            cell2.setCellValue("￥"+investTotal);
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    @ApiOperation(value = "页面详情", notes = "汇直投页面详情")
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
            detail=couponTenderHztService.getCouponTenderDetailCustomize(paramMap);
            //回款列表
            List<CouponRecoverVO> list=
                    couponTenderHztService.getCouponRecoverCustomize(paramMap);
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
}
