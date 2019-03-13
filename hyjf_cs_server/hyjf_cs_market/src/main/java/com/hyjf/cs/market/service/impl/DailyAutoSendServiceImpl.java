/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmConfigClient;
import com.hyjf.cs.market.client.AmMarketClient;
import com.hyjf.cs.market.mq.base.CommonProducer;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.service.DailyAutoSendService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author yaoyong
 * @version DailyAutoSendServiceImpl, v0.1 2018/11/16 9:29
 */
@Service
public class DailyAutoSendServiceImpl implements DailyAutoSendService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AmMarketClient amMarketClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private CommonProducer commonProducer;

    @Override
    public List<SellDailyDistributionVO> listSellDailyDistribution() {
        return amMarketClient.selectSellDailyDistribution();
    }

    @Override
    public boolean isWorkdateOnSomeDay() {
        return amConfigClient.queryWorkdateOnSomeday();
    }

    @Override
    public boolean isTodayFirstWorkdayOnMonth() {
        return amConfigClient.selectFirstWorkdayOnMonth();
    }

    @Override
    public void sendMail(SellDailyDistributionVO sellDailyDistribution) {
        beforeSend();

        String[] toEmail = sellDailyDistribution.getEmail().split(";");
        if (toEmail == null || toEmail.length == 0) {
            return;
        }
        String dateStr = GetDate.getFormatDateStr();
        String fileName = "销售日报-" + dateStr + ".xls";
        String[] fileNames = {fileName};
        // 将excel作为附件
        byte[] is = this.drawExcel(dateStr);

        logger.info("开始发送销售日报邮件>>>>>>>>>>>>>>>>>>>>>toEmail:" + JSONObject.toJSONString(toEmail) + "");
        // 邮件内容
        String subject = "【销售日报】";
        MailMessage mailMessage = new MailMessage(null, null, subject, null, fileNames, toEmail, null, MessageConstant.MAIL_SEND_FRO_SELL_DAILY, is);
        try {
            // 包含附件
            commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, "sell_daily_excel", mailMessage));
            logger.info("发送销售日报成功>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            logger.error("发送销售日报失败>>>>>>>>>>>>>>>>>>>>> 失败原因：{}", e);
        }
    }

    private void beforeSend() {
        // 由于生成拆分成消息队列， 不能保证更新顺序，对某些字段加工挪在这
        // 统一计算第四、六、十列速率,第十六列净资金流
        // 第四列: 计算环比增速
        // 第六列: 计算提现占比
        // 第十列: 计算环比增速
        // 第十六列: 计算昨日净资金流（充值-提现） 不能保证更新时间， 调整到在发送邮件前更新
        amMarketClient.calculateRate();
    }

    /**
     * 填充数据
     *
     * @param dateStr
     * @return
     */
    private byte[] drawExcel(String dateStr) {
        // 表格sheet名称
        String sheetName = "销售数据日报表";
        String[] titles = new String[]{"序号", "一级分部", "二级分部", "门店数量", "本月累计规模业绩", "本月累计已还款", "上月对应累计规模业绩", "环比增速",
                "本月累计提现", "提现占比", "本月累计充值", "本月累计年化业绩", "上月累计年化业绩", "环比增速", "昨日规模业绩", "昨日还款", "昨日年化业绩", "昨日提现", "昨日充值",
                "昨日净资金流（充值-提现）", "当日待还", "昨日注册数", "其中充值≥3000人数", "其中出借≥3000人数", "本月累计出借3000以上新客户数"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        Map<Integer, List<SellDailyVO>> resultMap = this.selectDrawExcelSourceData(dateStr);

        if (!CollectionUtils.isEmpty(resultMap)) {
            int rowNum = 1;
            // 序号列
            int orderNum = 0;

            for (int drawOrder : resultMap.keySet()) {
                List<SellDailyVO> sellDailies = resultMap.get(drawOrder);
                HSSFCellStyle cellStyle = this.createNomalCellStyle(workbook);
                for (int i = 0; i < sellDailies.size(); i++) {
                    SellDailyVO sellDaily = sellDailies.get(i);

                    // 本月累计规模业绩和上月对应累计规模业绩为0的数据不显示
                    if (BigDecimal.ZERO.compareTo(sellDaily.getInvestTotalMonth()) >= 0
                            && BigDecimal.ZERO.compareTo(sellDaily.getInvestTotalPreviousMonth()) >= 0
                            && BigDecimal.ZERO.compareTo(sellDaily.getNonRepaymentToday()) >= 0
                            && BigDecimal.ZERO.compareTo(sellDaily.getRepaymentTotalMonth()) >= 0
                            && BigDecimal.ZERO.compareTo(sellDaily.getWithdrawTotalMonth()) >= 0) {
                        logger.info("{},{}机构无销售业务，疑似关停，不显示。", sellDaily.getPrimaryDivision(),
                                sellDaily.getTwoDivision());
                        continue;
                    }


                    // 1. 插入运营中心汇总行
                    if (drawOrder == 2 && "惠众".equals(sellDaily.getPrimaryDivision())) {
                        Row row = sheet.createRow(++rowNum);
                        HSSFCellStyle sumOCCellStyle = createTotalCellStyle(row, sheet, workbook, "运营中心汇总", rowNum,
                                IndexedColors.GOLD.index);
                        SellDailyVO sumOCSellDaily = getSumSellDaily(1);
                        for (int celLength = 3; celLength < titles.length; celLength++) {
                            // 创建相应的单元格
                            Cell cell = row.createCell(celLength);
                            cell.setCellStyle(sumOCCellStyle);
                            setCellValue(workbook, cell, celLength, sumOCSellDaily);
                        }

                    }

                    // 2. 插入普通行
                    Row row = sheet.createRow(++rowNum);
                    orderNum++;
                    for (int celLength = 0; celLength < titles.length; celLength++) {
                        Cell cell = row.createCell(celLength);
                        cell.setCellStyle(cellStyle);
                        setCellValue(cell, celLength, sellDaily, orderNum);
                    }
                }

                // 3. 插入一级分部合计行
                Row row = sheet.createRow(++rowNum);
                HSSFCellStyle sumPrimaryDivisionCellStyle = createTotalCellStyle(row, sheet, workbook,
                        concatPrimaryDivisionTotalTitle(drawOrder), rowNum, IndexedColors.LIGHT_TURQUOISE.index);
                SellDailyVO sumPrimaryDivisionSellDaily = getSumSellDaily(2, drawOrder);
                // 第三列到第24列计算合计数据
                for (int i = 3; i < titles.length; i++) {
                    Cell totalCell = row.createCell(i);
                    totalCell.setCellStyle(sumPrimaryDivisionCellStyle);
                    setCellValue(workbook, totalCell, i, sumPrimaryDivisionSellDaily);
                }
            }

            // 4. 插入总合计行
            Row row = sheet.createRow(++rowNum);
            HSSFCellStyle allTotalCellStyle = createTotalCellStyle(row, sheet, workbook, "合计", rowNum,
                    IndexedColors.YELLOW.index);
            SellDailyVO allTotalSellDaily = getSumSellDaily(3);
            for (int i = 3; i < titles.length; i++) {
                Cell totalCell = row.createCell(i);
                totalCell.setCellStyle(allTotalCellStyle);
                setCellValue(workbook, totalCell, i, allTotalSellDaily);
            }
        }

        InputStreamSource is = null;
        ByteArrayOutputStream os =null;
        try {
            os = new ByteArrayOutputStream(1024);
            workbook.write(os);
            is = new ByteArrayResource(os.toByteArray());
            os.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return os.toByteArray();
    }

    /**
     * 创建excel基本样式
     *
     * @param workbook
     * @param titles
     * @param sheetName
     * @return
     */
    private static HSSFSheet createHSSFWorkbookTitle(HSSFWorkbook workbook, String[] titles, String sheetName) {

        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);

        // ----------------标题样式---------------------
        // 标题样式
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont ztFont = workbook.createFont();
        // 字体设置
        ztFont.setColor(Font.COLOR_NORMAL);
        // 将字体大小设置为18px
        ztFont.setFontHeightInPoints((short) 18);
        // 将“宋体”字体应用到当前单元格上
        ztFont.setFontName("宋体");
        // 加粗
        ztFont.setBold(true);
        titleStyle.setFont(ztFont);

        // 设置前景填充样式
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 淡黄色 https://www.cnblogs.com/caiyun/archive/2012/08/22/2650239.html 颜色对比
        titleStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.index);

        // ----------------单元格样式----------------------------------
        // 表格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        Font cellFont = workbook.createFont();
        // 字体设置
        cellFont.setColor(Font.COLOR_NORMAL);
        // 将字体大小设置为18px
        cellFont.setFontHeightInPoints((short) 10);
        // 字体应用到当前单元格上
        cellFont.setFontName("宋体");
        cellFont.setBold(true);
        cellStyle.setFont(cellFont);
        // 设置自动换行
        cellStyle.setWrapText(true);
        // 设置前景填充样式
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);

        // ----------------------创建第一行---------------
        // 在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        Row row = sheet.createRow(0);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        Cell cell = row.createCell(0);
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24));
        // 设置单元格内容

        cell.setCellValue("销售数据日报表-- " + GetDate.getFormatDateStr() + "                    单位：万元");
        cell.setCellStyle(titleStyle);

        row = sheet.createRow(1);
        for (int celLength = 0; celLength < titles.length; celLength++) {
            // 创建相应的单元格
            cell = row.createCell(celLength);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(titles[celLength]);
        }

        return sheet;
    }


    /**
     * 查询excel填充数据源
     *
     * @param dateStr
     * @return
     */
    private Map<Integer, List<SellDailyVO>> selectDrawExcelSourceData(String dateStr) {
        List<SellDailyVO> list = amMarketClient.selectSellDailyByDateStr(dateStr);
        Assert.notNull(list, "sell daily must be not null...");
        return this.slicingDepartmentData(list);
    }

    /**
     * 根据类型查询合计
     *
     * @param type
     * @param drawOrder
     * @return
     */
    private SellDailyVO getSumSellDaily(int type, int drawOrder) {
        SellDailyVO sumSellDaily = null;
        switch (type) {
            case 1:
                sumSellDaily = amMarketClient.selectOCSum(GetDate.getFormatDateStr());
                break;
            case 2:
                sumSellDaily = amMarketClient.selectPrimaryDivisionSum(GetDate.getFormatDateStr(), drawOrder);
                break;
            case 3:
                sumSellDaily = amMarketClient.selectAllSum(GetDate.getFormatDateStr());
                break;
            default:
                throw new RuntimeException("错误的传参数,type is : " + type);
        }

        Assert.notNull(sumSellDaily, "数据缺失");
        return sumSellDaily;
    }

    /**
     * 根据类型查询合计-重载
     *
     * @param type
     * @return
     */
    private SellDailyVO getSumSellDaily(int type) {
        return getSumSellDaily(type, 0);
    }

    /**
     * 拼接合计标题
     *
     * @param drawOrder
     * @return
     */
    private String concatPrimaryDivisionTotalTitle(int drawOrder) {
        String totalTitleName = "合计";
        switch (drawOrder) {
            case 1:
                totalTitleName = "纳觅".concat(totalTitleName);
                break;
            case 2:
                totalTitleName = "惠众".concat(totalTitleName);
                break;
            case 3:
                totalTitleName = "裕峰瑞".concat(totalTitleName);
                break;
            case 4:
                totalTitleName = "其它".concat(totalTitleName);
                break;
        }
        return totalTitleName;
    }

    /**
     * 创建普通行样式
     *
     * @param workbook
     * @return
     */
    private HSSFCellStyle createNomalCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * 创建汇总行样式
     *
     * @param row
     * @param sheet
     * @param workbook
     * @param cellValue
     * @param rowNum
     * @param color     合计行的颜色
     * @return
     */
    private HSSFCellStyle createTotalCellStyle(Row row, HSSFSheet sheet, HSSFWorkbook workbook, String cellValue,
                                               int rowNum, short color) {
        Cell cell = row.createCell(0);
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
        cell.setCellValue(cellValue);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置前景填充样式
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 淡黄色 https://www.cnblogs.com/caiyun/archive/2012/08/22/2650239.html 颜色对比
        cellStyle.setFillForegroundColor(color);

        Font cellFont = workbook.createFont();
        // 字体设置
        cellFont.setColor(Font.COLOR_NORMAL);
        // 将字体大小设置为18px
        cellFont.setFontHeightInPoints((short) 10);
        // 字体应用到当前单元格上
        cellFont.setFontName("Calibri");
        cellStyle.setFont(cellFont);

        cell.setCellStyle(cellStyle);
        return cellStyle;
    }

    /**
     * 填充cell值 -重载方法
     *
     * @param workbook
     * @param cell
     * @param celLength
     * @param sellDaily
     * @return
     */
    private Cell setCellValue(HSSFWorkbook workbook, Cell cell, int celLength, SellDailyVO sellDaily) {
        return setCellValue(cell, celLength, sellDaily, 0);
    }

    /**
     * 填充cell值
     *
     * @param sortNo
     * @param cell
     * @param celLength
     * @param sellDaily
     * @return
     */
    private Cell setCellValue(Cell cell, int celLength, SellDailyVO sellDaily, int sortNo) {
        switch (celLength) {
            case 0:
                cell.setCellValue(sortNo);
                break;
            case 1:
                cell.setCellValue(sellDaily.getPrimaryDivision());
                break;
            case 2:
                cell.setCellValue(sellDaily.getTwoDivision());
                break;
            case 3:
                Integer cellStoreNum = sellDaily.getStoreNum();
                cell.setCellValue(cellStoreNum);
                break;
            case 4:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalMonth()));
                break;
            case 5:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRepaymentTotalMonth()));
                break;
            case 6:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalPreviousMonth()));
                break;
            case 7:
                String investRatioGrowth = sellDaily.getInvestRatioGrowth();
                cell.setCellValue(investRatioGrowth);
                break;
            case 8:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getWithdrawTotalMonth()));
                break;
            case 9:
                String withdrawRate = sellDaily.getWithdrawRate();
                cell.setCellValue(withdrawRate);
                break;
            case 10:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRechargeTotalMonth()));
                break;
            case 11:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalMonth()));
                break;
            case 12:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalPreviousMonth()));
                break;
            case 13:
                String investAnnularRatioGrowth = sellDaily.getInvestAnnularRatioGrowth();
                cell.setCellValue(investAnnularRatioGrowth);
                break;
            case 14:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestTotalYesterday()));
                break;
            case 15:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRepaymentTotalYesterday()));
                break;
            case 16:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getInvestAnnualTotalYesterday()));
                break;
            case 17:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getWithdrawTotalYesterday()));
                break;
            case 18:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getRechargeTotalYesterday()));
                break;
            case 19:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getNetCapitalInflowYesterday()));
                break;
            case 20:
                cell.setCellValue(this.cutIntegerFromBigDecimal(sellDaily.getNonRepaymentToday()));
                break;
            case 21:
                Integer registerTotalYesterday = sellDaily.getRegisterTotalYesterday();
                cell.setCellValue(registerTotalYesterday.toString());
                break;
            case 22:
                Integer rechargeGt3000UserNum = sellDaily.getRechargeGt3000UserNum();
                cell.setCellValue(rechargeGt3000UserNum.toString());
                break;
            case 23:
                Integer investGt3000UserNum = sellDaily.getInvestGt3000UserNum();
                cell.setCellValue(investGt3000UserNum.toString());
                break;
            case 24:
                Integer investGt3000MonthUserNum = sellDaily.getInvestGt3000MonthUserNum();
                cell.setCellValue(investGt3000MonthUserNum.toString());
                break;
            default:
        }
        return cell;
    }

    /**
     * 四舍五入从 BigDecimal 取出整数 ，转成字符串
     *
     * @param bigDecimal
     * @return
     */
    private String cutIntegerFromBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 切割部门
     *
     * @param list
     * @return
     */
    private Map<Integer, List<SellDailyVO>> slicingDepartmentData(List<SellDailyVO> list) {
        Map<Integer, List<SellDailyVO>> map = new TreeMap<>();

        for (SellDailyVO bean : list) {
            List<SellDailyVO> sellDailies = null;
            if (map.containsKey(bean.getDrawOrder())) {
                sellDailies = map.get(bean.getDrawOrder());
            } else {
                sellDailies = new ArrayList<>();
            }
            sellDailies.add(bean);
            map.put(bean.getDrawOrder(), sellDailies);
        }
        return map;
    }
}
