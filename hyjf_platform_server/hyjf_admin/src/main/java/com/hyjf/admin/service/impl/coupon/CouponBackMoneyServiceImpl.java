package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.beans.response.CouponBackMoneyContResponse;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 15:17
 */
@Service
public class CouponBackMoneyServiceImpl implements CouponBackMoneyService {
    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public Integer countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public String queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHztInvestTotal(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAttrbute();
        }
        return null;
    }

    @Override
    public String queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public Integer countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHztJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhDJ(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public String queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHjhInvestTotal(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAttrbute();
        }
        return null;
    }

    @Override
    public String queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getAmountTotal();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhTY(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    @Override
    public Integer countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.countRecordHjhJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getRecordTotal();
        }
        return null;
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderResponse couponTenderResponse = amTradeClient.getRecordListHjhJX(couponBackMoneyCustomize);
        if(null != couponTenderResponse){
            return couponTenderResponse.getBackMoneyCustomizeList();
        }
        return null;
    }

    /**
     * 汇直投-代金券-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse getdjHzt(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = countRecordHztDJ(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());
            List<CouponBackMoneyCustomize>  recordList = getRecordListHztDJ(couponBackMoneyCustomize);
            String recoverInterest = queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 汇直投-加息券-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse getjxHzt(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = countRecordHztJX(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {

            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = getRecordListHztJX(couponBackMoneyCustomize);
            String recoverInterest =queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 汇直投-体验金-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse gettyHzt(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = countRecordHztTY(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = getRecordListHztTY(couponBackMoneyCustomize);
            String recoverInterest = queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 汇计划-体验金-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse gettyHjh(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = countRecordHjhTY(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = getRecordListHjhTY(couponBackMoneyCustomize);
            String recoverInterest = queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 汇计划-代金券-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse getdjHjh(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = countRecordHjhDJ(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = getRecordListHjhDJ(couponBackMoneyCustomize);
            String recoverInterest = queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 汇计划-加息券-回款使用列表
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public CouponBackMoneyContResponse getjxHjh(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = countRecordHjhJX(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = getRecordListHjhJX(couponBackMoneyCustomize);
            String recoverInterest = queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
        return response;
    }

    /**
     * 导出
     * @param couponBackMoneyCustomize
     * @return
     */
    @Override
    public void exportAction(String sheetName, List<CouponBackMoneyCustomize> resultList, String recoverInterest,
                             String investTotal, String fileName, HttpServletResponse response,CouponBackMoneyCustomize couponBackMoneyCustomize) {

        String[] titles = new String[] {"序号", "订单号", "用户名","优惠券id","优惠券类型编号",
                "项目编号","回款期数","应回款（元）","转账订单号","状态" ,"应回款日期" ,"使用时间" ,"投资金额" ,"来源" ,"内容"};
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
                    CouponBackMoneyCustomize pInfo = resultList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    else if (celLength == 1) {
                        cell.setCellValue(pInfo.getNid());
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
                        cell.setCellValue(pInfo.getBorrowNid());
                    }
                    else if (celLength == 6) {
                        cell.setCellValue(pInfo.getRecoverPeriod());
                    }
                    else if (celLength == 7) {
                        cell.setCellValue(pInfo.getRecoverInterest());
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(pInfo.getTransferId());
                    }
                    else if (celLength == 9) {
                        cell.setCellValue(pInfo.getReceivedFlg());
                    }
                    else if (celLength == 10) {
                        cell.setCellValue(pInfo.getRecoverTime());
                    }
                    else if (celLength == 11) {
                        cell.setCellValue(pInfo.getAddTime());
                    }
                    else if (celLength == 12) {
                        cell.setCellValue("￥"+pInfo.getRecoverCapital());
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(pInfo.getCouponSource());
                    }
                    else if (celLength == 14) {
                        cell.setCellValue(pInfo.getCouponContent());
                    }
                }
            }
            rowNum++;
            Row row = sheet.createRow(rowNum);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("合计");
            Cell cell2 = row.createCell(7);
            cell2.setCellValue(recoverInterest);
            Cell cell3 = row.createCell(11);
            cell3.setCellValue("￥"+investTotal);
        }

        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
