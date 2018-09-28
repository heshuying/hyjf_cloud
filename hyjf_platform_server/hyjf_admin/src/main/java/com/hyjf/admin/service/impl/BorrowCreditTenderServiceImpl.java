package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.BorrowCreditRepayInfoResultBean;
import com.hyjf.admin.beans.BorrowCreditRepayResultBean;
import com.hyjf.admin.beans.BorrowCreditTenderResultBean;
import com.hyjf.admin.beans.CreditUserInfoBean;
import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderInfoRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderPDFSignReq;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.BaseClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.FddProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.response.admin.AdminCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowCreditRepayResponse;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.CountResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
public class BorrowCreditTenderServiceImpl implements BorrowCreditTenderService {

    Logger logger = LoggerFactory.getLogger(BorrowCreditTenderServiceImpl.class);

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    AccedeListService accedeListService;

    @Autowired
    FddProducer fddProducer;

    public static final String HZR_PREFIX = "HZR";

    public static final String BASE_URL = "http://AM-ADMIN/am-trade";

    /*还款信息列表*/
    public static final String REPAY_LIST_URL = BASE_URL + "/creditTender/getRepayList";
    /*还款信息count*/
    public static final String REPAY_COUNT_URL = BASE_URL + "/creditTender/getRepayCount";
    /*还款信息合计*/
    public static final String REPAY_SUM_URL = BASE_URL + "/creditTender/getRepaySum";

    /*还款详情count*/
    public static final String REPAY_INFO_COUNT_URL = BASE_URL + "/creditTender/getRepayInfoCount";
    /*还款详情list*/
    public static final String REPAY_INFO_LIST_URL = BASE_URL + "/creditTender/getRepayInfoList";
    /*还款详情合计*/
    public static final String REPAY_INFO_SUM_URL = BASE_URL + "/creditTender/getRepayInfoSum";

    /*承接列表count*/
    public static final String TENDER_COUNT_URL = BASE_URL + "/creditTender/getTenderCount";
    /*承接列表list*/
    public static final String TENDER_LIST_URL = BASE_URL + "/creditTender/getTenderList";
    /*承接列表sum*/
    public static final String TENDER_SUM_URL = BASE_URL + "/creditTender/getTenderSum";

    public static final String BANK_OPEN_ACCOUNT_URL = "http://AM-ADMIN/am-user/borrow_regist_exception/searchbankopenaccount/";

    public static final String BORROW_URL = BASE_URL + "/borrow/getBorrow/";

    public static final String PDFSIGN_CREDIT_TENDER_COUNT_URL = BASE_URL + "/creditTender/getBorrowCreditTender4Admin";
    /**
     * 查询还款信息列表
     *
     * @author zhangyk
     * @date 2018/7/11 14:34
     */
    @Override
    public AdminResult getBorrowCreditRepayList(BorrowCreditRepayRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditRepayResultBean bean = new BorrowCreditRepayResultBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        BorrowCreditTenderResponse response = baseClient.postExe(REPAY_COUNT_URL, req, BorrowCreditTenderResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            response = baseClient.postExe(REPAY_LIST_URL, req, BorrowCreditTenderResponse.class);
            List<BorrowCreditRepayVO> list = response.getResultList();
            bean.setRecordList(list);
            response = baseClient.postExe(REPAY_SUM_URL, req, BorrowCreditTenderResponse.class);
            bean.setSumData(response.getSumData());
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }

    /**
     * 还款信息列表导出
     *
     * @author zhangyk
     * @date 2018/7/11 20:41
     */
    @Override
    public void exportBorrowCreditRepayList(BorrowCreditRepayRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);

        String sheetName = "还款信息列表";
        String[] titles = new String[]{"承接人", "债转编号", "出让人", "项目编号", "订单号", "应收本金", "应收利息", "应收本息", "已收本息", "还款服务费", "还款状态", "债权承接时间", "下次还款时间"};
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;

        // 导出列表不需要分页,扩大数据查询范围，使失效
        BorrowCreditTenderResponse res = baseClient.postExe(REPAY_LIST_URL, req, BorrowCreditTenderResponse.class);
        List<BorrowCreditRepayVO> list = res.getResultList();
        if (CollectionUtils.isNotEmpty(list)) {
            // 特殊处理数据
            for (BorrowCreditRepayVO vo : list) {
                if ("0".equals(vo.getStatus())) {
                    vo.setStatus("还款中");
                } else {
                    vo.setStatus("已还款");
                }
                vo.setCreditNid(HZR_PREFIX + vo.getCreditNid());
            }
        }

        exportExcel(sheetName, fileName, titles, list, response);
    }

    /**
     * 还款信息明细
     *
     * @author zhangyk
     * @date 2018/7/12 10:52
     */
    @Override
    public AdminResult getBorrowCreditRepayInfoList(BorrowCreditRepayRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditRepayInfoResultBean bean = new BorrowCreditRepayInfoResultBean();
        CheckUtil.check(StringUtils.isNotBlank(request.getAssignNid()), MsgEnum.ERR_OBJECT_REQUIRED, "订单号");
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        BorrowCreditRepayResponse response = baseClient.postExe(REPAY_INFO_COUNT_URL, req, BorrowCreditRepayResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            req.setLimitStart(page.getOffset());
            req.setLimitEnd(page.getLimit());
            response = baseClient.postExe(REPAY_INFO_LIST_URL, req, BorrowCreditRepayResponse.class);
            List<BorrowCreditRepayInfoVO> list = response.getResultList();
            bean.setRecordList(list);
            response = baseClient.postExe(REPAY_INFO_SUM_URL, req, BorrowCreditRepayResponse.class);
            Map<String, Object> sumVo = response.getSumData();
            bean.setSumData(sumVo);
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }

    /**
     * 承接信息列表
     *
     * @author zhangyk
     * @date 2018/7/12 19:04
     */
    @Override
    public AdminResult getCreditTenderList(BorrowCreditTenderRequest request) {
        AdminResult result = new AdminResult();
        BorrowCreditTenderResultBean bean = new BorrowCreditTenderResultBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        AdminCreditTenderResponse response = baseClient.postExe(TENDER_COUNT_URL, req, AdminCreditTenderResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            req.setLimitStart(page.getOffset());
            req.setLimitEnd(page.getLimit());
            response = baseClient.postExe(TENDER_LIST_URL, req, AdminCreditTenderResponse.class);
            List<BorrowCreditTenderVO> list = response.getResultList();
            bean.setRecordList(list);
            response = baseClient.postExe(TENDER_SUM_URL, req, AdminCreditTenderResponse.class);
            bean.setSumData(response.getSumData());
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }

    /**
     * 承接信息列表导出
     *
     * @author zhangyk
     * @date 2018/7/13 10:49
     */
    @Override
    public void exportCreditTenderList(BorrowCreditTenderRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        AdminCreditTenderResponse res = baseClient.postExe(TENDER_LIST_URL, req, AdminCreditTenderResponse.class);
        List<BorrowCreditTenderVO> list = res.getResultList();
        String sheetName = "汇转让-承接信息";

        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        //String[] titles = new String[] { "序号","订单号","债转编号","项目编号","出让人","出让人当前的推荐人的用户名","出让人当前的推荐人的用户属性","出让人当前的推荐人的分公司","出让人当前的推荐人的部门","出让人当前的推荐人的团队","出让人承接时的推荐人的用户名", "出让人承接时的推荐人的用户属性", "出让人承接时的推荐人的分公司", "出让人承接时的推荐人的部门", "出让人承接时的推荐人的团队", "承接人","承接人当前的推荐人的用户名","承接人当前的推荐人的用户属性","承接人当前的推荐人的分公司","承接人当前的推荐人的部门","承接人当前的推荐人的团队","承接人承接时的推荐人的用户名", "承接人承接时的推荐人的用户属性", "承接人承接时的推荐人的分公司", "承接人承接时的推荐人的部门", "承接人承接时的推荐人的团队", "承接本金","折让率","认购价格","垫付利息", "债转服务费", "实付金额","承接平台", "承接时间" };
        String[] titles = new String[]{"序号", "订单号", "债转编号", "项目编号", "出让人", "承接人", "承接本金", "折让率", "认购价格", "垫付利息", "债转服务费", "实付金额", "承接平台", "承接时间"};
        exportTenderExcel(sheetName, fileName, titles, list, response);
    }


    /**
     * 查看债权人债权信息
     *
     * @author zhangyk
     * @date 2018/7/13 15:21
     */
    @Override
    public AdminResult getCreditUserInfo(BorrowCreditTenderInfoRequest request) {
        AdminResult result = new AdminResult();
        String userId = request.getUserId();
        String assignNid = request.getAssignNid();
        CheckUtil.check(StringUtils.isNotBlank(userId), MsgEnum.ERR_OBJECT_REQUIRED, "用户");
        CheckUtil.check(StringUtils.isNotBlank(assignNid), MsgEnum.ERR_OBJECT_REQUIRED, "承接订单号");
        String url = BANK_OPEN_ACCOUNT_URL + userId;
        BankOpenAccountResponse response = baseClient.getExe(url, BankOpenAccountResponse.class);
        BankOpenAccountVO accountVO = response.getResult();
        CreditUserInfoBean userInfo = new CreditUserInfoBean();
        if (accountVO != null) {
            String accountId = accountVO.getAccount();
            // 调用江西银行查询单笔投资人投标申请接口
            BankCallBean bankCallBean = bidApplyQuery(userId, assignNid, accountId);
            if (bankCallBean != null) {
                userInfo = CommonUtils.convertBean(bankCallBean, CreditUserInfoBean.class);
            } else {
                logger.error("调用江西银行查询单笔投资人投标申请接口异常");
            }
        }
        result.setData(userInfo);
        return result;
    }


    /**
     * pdf签署
     *
     * @author zhangyk
     * @date 2018/8/28 17:00
     */
    @Override
    public AdminResult pdfSign(BorrowCreditTenderPDFSignReq request, AdminSystemVO adminSystemVO) {
        AdminResult result = new AdminResult();
        String userId = request.getUserId();
        // 标的编号
        String borrowNid = request.getBorrowNid();
        // 承接订单号
        String assignNid = request.getAssignNid();
        // 原始投资订单号
        String creditTenderNid = request.getCreditTenderNid();
        // 债转编号
        String creditNid = request.getCreditNid();

        CheckUtil.check(!org.apache.commons.lang3.StringUtils.isAnyBlank(borrowNid, assignNid, creditTenderNid, creditNid,userId), MsgEnum.ERR_OBJECT_REQUIRED, "各项");
        String borrowUrl = BORROW_URL + borrowNid;
        BorrowResponse response = baseClient.getExe(borrowUrl,BorrowResponse.class);
        BorrowAndInfoVO borrowVO = response.getResult();
        if (borrowVO == null) {
            throw new CheckException("原始标的信息不存在");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("borrowNid",borrowNid);
        params.put("assignNid",assignNid);
        params.put("creditTenderNid",creditTenderNid);
        params.put("creditNid",creditNid);
        String tenderUrl= PDFSIGN_CREDIT_TENDER_COUNT_URL ;
        CountResponse countResponse = baseClient.postExe(tenderUrl,params,CountResponse.class);
        int count = countResponse.getCount();
        if (count <= 0 ){
            throw new CheckException("没有对应的承接记录");
        }

        TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement(assignNid);
        if (tenderAgreement != null && tenderAgreement.getStatus() == 2){
            accedeListService.updateSaveSignInfo(tenderAgreement, borrowNid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrowVO.getInstCode());
        } else {
            FddGenerateContractBean bean = new FddGenerateContractBean();
            bean.setOrdid(assignNid);
            bean.setAssignOrderId(assignNid);
            bean.setCreditNid(creditNid);
            bean.setCreditTenderNid(creditTenderNid);
            bean.setTenderUserId(Integer.parseInt(userId));
            bean.setBorrowNid(borrowNid);
            bean.setTransType(3);
            bean.setTenderType(1);
            // 法大大生成合同
            try {
                fddProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(bean)));
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
                return new AdminResult(BaseResult.FAIL, "法大大合同生成MQ发送失败");
            }
        }
        return result;
    }

    private BankCallBean bidApplyQuery(String userId, String orderId, String accountId) {
        BankCallBean bean = new BankCallBean();
        String instCode = systemConfig.getBANK_INSTCODE();
        String bankCode = systemConfig.getBANK_BANKCODE();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        String channel = BankCallConstant.CHANNEL_PC;
        //设置查询需要参数
        bean.setInstCode(instCode);
        bean.setBankCode(bankCode);
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        bean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);
        bean.setAccountId(accountId);// 投资人电子账户号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId0(Integer.parseInt(userId)));
        bean.setLogUserId(userId);
        BankCallBean result = BankCallUtils.callApiBg(bean);
        return result;
    }


    /**
     * 承接信息生成excel
     *
     * @author zhangyk
     * @date 2018/7/13 11:10
     */
    private void exportTenderExcel(String sheetName, String fileName, String[] titles, List<BorrowCreditTenderVO> resultList, HttpServletResponse response) {
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
                    BorrowCreditTenderVO creditTenderVo = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 订单号
                    else if (celLength == 1) {
                        cell.setCellValue(creditTenderVo.getAssignNid());
                    }
                    // 债转编号
                    else if (celLength == 2) {
                        cell.setCellValue(creditTenderVo.getCreditNid());
                    }
                    // 项目编号
                    else if (celLength == 3) {
                        cell.setCellValue(creditTenderVo.getBidNid());
                    }
                    // 出让人用户名
                    else if (celLength == 4) {
                        cell.setCellValue(creditTenderVo.getCreditUserName());
                    }
                    // 承接人
                    else if (celLength == 5) {
                        cell.setCellValue(creditTenderVo.getUserName());
                    }

                    // 承接本金
                    else if (celLength == 6) {
                        cell.setCellValue(creditTenderVo.getAssignCapital());
                    }
                    // 折让率
                    else if (celLength == 7) {
                        cell.setCellValue(creditTenderVo.getCreditDiscount());
                    }
                    // 认购价格
                    else if (celLength == 8) {
                        cell.setCellValue(creditTenderVo.getAssignPrice());
                    }
                    // 垫付利息
                    else if (celLength == 9) {
                        cell.setCellValue(creditTenderVo.getAssignInterestAdvance());
                    }
                    // 服务费
                    else if (celLength == 10) {
                        cell.setCellValue(creditTenderVo.getCreditFee());
                    }
                    // 实付金额
                    else if (celLength == 11) {
                        cell.setCellValue(creditTenderVo.getAssignPay());
                    }
                    // 客户端
                    else if (celLength == 12) {
                        if (creditTenderVo.getClient() != null && !"".equals(creditTenderVo.getClient())
                                && "0".equals(creditTenderVo.getClient())) {
                            cell.setCellValue("pc");
                        } else if (creditTenderVo.getClient() != null && !"".equals(creditTenderVo.getClient())
                                && "1".equals(creditTenderVo.getClient())) {
                            cell.setCellValue("微信");
                        } else if (creditTenderVo.getClient() != null && !"".equals(creditTenderVo.getClient())
                                && "2".equals(creditTenderVo.getClient())) {
                            cell.setCellValue("android");
                        } else if (creditTenderVo.getClient() != null && !"".equals(creditTenderVo.getClient())
                                && "3".equals(creditTenderVo.getClient())) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 承接时间
                    else if (celLength == 13) {
                        cell.setCellValue(creditTenderVo.getAddTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }


    // 还款信息生成excel
    private void exportExcel(String sheetName, String fileName, String[] titles, List<BorrowCreditRepayVO> resultList, HttpServletResponse response) {
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
                    BorrowCreditRepayVO creditRepay = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);


                    // 承接人
                    if (celLength == 0) {
                        cell.setCellValue(creditRepay.getUserName());
                    }

                    // 债转编号
                    else if (celLength == 1) {
                        cell.setCellValue(creditRepay.getCreditNid());
                    }
                    // 出让人
                    else if (celLength == 2) {
                        cell.setCellValue(creditRepay.getCreditUserName());
                    }

                    // 项目编号
                    else if (celLength == 3) {
                        cell.setCellValue(creditRepay.getBidNid());
                    }
                    // 订单号
                    else if (celLength == 4) {
                        cell.setCellValue(creditRepay.getAssignNid());
                    }
                    // 应收本金
                    else if (celLength == 5) {
                        cell.setCellValue(creditRepay.getAssignCapital());
                    }
                    // 应收利息
                    else if (celLength == 6) {
                        cell.setCellValue(creditRepay.getAssignInterest());
                    }
                    // 应收本息
                    else if (celLength == 7) {
                        cell.setCellValue(creditRepay.getAssignAccount());
                    }
                    // 已收本息
                    else if (celLength == 8) {
                        cell.setCellValue(creditRepay.getAssignRepayAccount());
                    }
                    // 还款服务费
                    else if (celLength == 9) {
                        cell.setCellValue(creditRepay.getCreditFee());
                    }
                    // 还款状态
                    else if (celLength == 10) {
                        cell.setCellValue(creditRepay.getStatus());
                    }
                    // 债权承接时间
                    else if (celLength == 11) {
                        cell.setCellValue(creditRepay.getAddTime());
                    }
                    // 下次还款时间
                    else if (celLength == 12) {
                        cell.setCellValue(creditRepay.getAssignRepayNextTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }


}
