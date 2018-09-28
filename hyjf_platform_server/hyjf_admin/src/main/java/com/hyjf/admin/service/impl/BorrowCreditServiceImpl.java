package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.BaseClient;
import com.hyjf.admin.mq.AppMessageProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.beans.BorrowCreditInfoResultBean;
import com.hyjf.admin.beans.BorrowCreditListResultBean;
import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.BorrowCreditService;
import com.hyjf.am.response.admin.AdminBorrowCreditInfoResponse;
import com.hyjf.am.resquest.admin.BorrowCreditAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
public class BorrowCreditServiceImpl implements BorrowCreditService {


    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private BaseClient baseClient;

    public static final Logger logger = LoggerFactory.getLogger(BorrowCreditServiceImpl.class);

    private static final String BASE_URL = "http://AM-ADMIN/am-trade";

    @Autowired
    private AppMessageProducer appMessageProducer;

    /**
     * 查询汇转让数据列表
     *
     * @author zhangyk
     * @date 2018/7/9 15:12
     */
    @Override
    public AdminResult getBorrowCreditList(BorrowCreditRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditListResultBean bean = new BorrowCreditListResultBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditAmRequest req = CommonUtils.convertBean(request, BorrowCreditAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        Integer count = amTradeClient.getBorrowCreditCount(req);

        if (count != null && count > 0) {
            List<BorrowCreditVO> list = amTradeClient.getBorrowCreditList(req);
            BorrowCreditSumVO sumVO = amTradeClient.getBorrwoCreditTotalSum(req);
            bean.setRecordList(list);
            bean.setSumCredit(sumVO);
            bean.setTotal(count);
        }
        result.setData(bean);
        return result;
    }


    /**
     * 数据导出
     *
     * @author zhangyk
     * @date 2018/7/10 14:09
     */
    @Override
    public void exportBorrowCreditList(BorrowCreditRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        BorrowCreditAmRequest req = CommonUtils.convertBean(request, BorrowCreditAmRequest.class);

        String sheetName = "债权转让列表";
        String[] titles = new String[]{"债转编号", "项目编号", "用户名", "债权本金", "转让本金", "折让率", "转让价格", "已转让金额", "发布时间",
                "还款时间", "转让状态", "发起平台"};
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;
        List<BorrowCreditVO> list = amTradeClient.getBorrowCreditList(req);
        exportExcel(sheetName, fileName, titles, list, response);
    }

    @Override
    public AdminResult getBorrowInfoList(BorrowCreditRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditInfoResultBean bean = new BorrowCreditInfoResultBean();
        String creditNid = request.getCreditNid();
        CheckUtil.check(StringUtils.isNotBlank(creditNid), MsgEnum.ERR_OBJECT_REQUIRED, "债转编号");
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditAmRequest req = new BorrowCreditAmRequest();
        req.setCreditNid(creditNid);
        //Integer count = amBorrowCreditClient.countBorrowCreditInfo(req);
        AdminBorrowCreditInfoResponse response = baseClient.postExe(BASE_URL + "/borrowCredit/countBorrowCreditInfo4admin", request, AdminBorrowCreditInfoResponse.class);
        Integer count = response.getCount();

        if (count == null) {
            logger.error("admin:查询债转详情原子层count异常");
            throw new RuntimeException("admin:查询债转详情原子层count异常");
        }
        if (count > 0) {
            req.setLimitStart(page.getOffset());
            req.setLimitEnd(page.getLimit());
            //List<BorrowCreditInfoVO> list = amBorrowCreditClient.searchBorrowCreditInfoList(req);
            response = baseClient.postExe(BASE_URL + "/borrowCredit/searchBorrowCreditInfo4admin", request, AdminBorrowCreditInfoResponse.class);
            List<BorrowCreditInfoVO> list = response.getResultList();

            CheckUtil.checkNull(list, "admin:查询债转详情原子层list异常");
            BorrowCreditInfoSumVO sumVO = amTradeClient.sumBorrowCreditInfoData(req);
            CheckUtil.checkNull(sumVO, "admin:查询债转详情合计行查询异常");
            bean.setTotal(count);
            bean.setRecordList(list);
            bean.setSumCreditInfo(sumVO);
        }
        result.setData(bean);
        return result;
    }


    // 生成excel
    private void exportExcel(String sheetName, String fileName, String[] titles, List<BorrowCreditVO> resultList, HttpServletResponse response) {
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
                    sheet = ExportExcel
                            .createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    BorrowCreditVO borrowCommonCustomize = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);


                    // 债转编号
                    if (celLength == 0) {
                        cell.setCellValue(borrowCommonCustomize.getCreditNid());
                    }

                    // 项目编号
                    else if (celLength == 1) {
                        cell.setCellValue(borrowCommonCustomize.getBidNid());
                    }
                    // 用户名
                    else if (celLength == 2) {
                        cell.setCellValue(borrowCommonCustomize.getUserName());
                    }

                    // 债权本金
                    else if (celLength == 3) {
                        cell.setCellValue(borrowCommonCustomize.getCreditCapital());
                    }
                    // 转让本金
                    else if (celLength == 4) {
                        cell.setCellValue(borrowCommonCustomize.getCreditCapitalPrice());
                    }
                    // 折让率
                    else if (celLength == 5) {
                        cell.setCellValue(borrowCommonCustomize.getCreditDiscount());
                    }
                    // 转让价格
                    else if (celLength == 6) {
                        cell.setCellValue(borrowCommonCustomize.getCreditPrice());
                    }
                    // 已转让金额
                    else if (celLength == 7) {
                        cell.setCellValue(borrowCommonCustomize.getCreditCapitalAssigned());
                    }
                    // 发布时间
                    else if (celLength == 8) {
                        cell.setCellValue(borrowCommonCustomize.getAddTime());
                    }
                    // 还款时间
                    else if (celLength == 9) {
                        cell.setCellValue(borrowCommonCustomize.getRepayLastTime());
                    }
                    // 转让状态
                    else if (celLength == 10) {
                        cell.setCellValue(borrowCommonCustomize.getCreditStatusName());
                    }
                    // 发布平台
                    else if (celLength == 11) {
                        cell.setCellValue(borrowCommonCustomize.getClient());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }


    /**
     * 取消转让
     *
     * @author zhangyk
     * @date 2018/8/24 16:43
     */
    @Override
    public AdminResult cancelCredit(BorrowCreditRequest request) {
        AdminResult result = new AdminResult();
        String creditNid = request.getCreditNid();
        CheckUtil.check(StringUtils.isNotBlank(creditNid), MsgEnum.ERR_OBJECT_REQUIRED, "债转编号");


        com.hyjf.am.vo.trade.BorrowCreditVO borrowCredit = new com.hyjf.am.vo.trade.BorrowCreditVO();
        borrowCredit.setCreditNid(Integer.valueOf(creditNid));
        borrowCredit.setCreditStatus(1);
        amTradeClient.updateBorrowCredit(borrowCredit);

        BorrowCreditAmRequest req = new BorrowCreditAmRequest();
        req.setCreditNid(creditNid);
        List<BorrowCreditVO> list = amTradeClient.getBorrowCreditList(req);
        BorrowCreditVO credit;
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            credit = list.get(0);
        } else {
            throw new CheckException("没有对应的债转标的");
        }
        String creditUserId = credit.getCreditUserId();
        Map<String, String> params = new HashMap<String, String>();
        // 用户id
        params.put("userId", creditUserId);
        // 债转编号
        params.put("creditNid", creditNid);
        String assignPay = amTradeClient.selectTenderCreditAssignPay(params);
        params = new HashMap<>();
        params.put("val_amount",assignPay);
        params.put("val_profit",borrowCredit.getCreditInterestAssigned() + "");
        AppMsMessage appMsMessage = new AppMsMessage(Integer.valueOf(creditUserId),params,null,MessageConstant.APP_MS_SEND_FOR_USER,CustomConstants.JYTZ_TPL_ZHUANRANGJIESHU);
        try {
            appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,UUID.randomUUID().toString(),JSON.toJSONBytes(appMsMessage)));
        } catch (MQException e) {
            logger.error("apppush消息发送异常:{}",e);
        }
        return result;
    }


    /**
     * 获取转让状态下拉选列表
     * @author zhangyk
     * @date 2018/8/28 13:48
     */
    @Override
    public AdminResult getCreditStatusList() {
        AdminResult result = new AdminResult();
        String key = RedisConstants.CACHE_PARAM_NAME + CustomConstants.CREDIT_STATUS;
        Map<String, String> creditStatusMap = RedisUtils.hgetall(key);
        List<DropDownVO> list =  ConvertUtils.convertParamMapToDropDown(creditStatusMap);
        result.setData(list);
        return result;
    }
}
