package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.*;
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
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowCreditRepayResponse;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.CountResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.resquest.admin.StartCreditEndRequest;
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
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetOrderIdUtils;
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
    CommonProducer commonProducer;

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

    public static final String BANK_OPEN_ACCOUNT_URL = "http://AM-ADMIN/am-user/borrow_regist_repair/searchbankopenaccount/";

    public static final String BORROW_URL = BASE_URL + "/borrow/getBorrow/";

    public static final String PDFSIGN_CREDIT_TENDER_COUNT_URL = BASE_URL + "/creditTender/getBorrowCreditTender4Admin";

    /*结束债权*/
    public static final String CREDITEND_URL = BASE_URL + "/bankCreditEndController/startCreditEnd";
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
            Map<String, String> sumVo = response.getSumData();
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
            bean.setRecordList(new ArrayList<>());
            if (CollectionUtils.isNotEmpty(list)){
                bean.setRecordList(CommonUtils.convertBeanList(list,BorrowCreditTenderBean.class));
            }
            response = baseClient.postExe(TENDER_SUM_URL, req, AdminCreditTenderResponse.class);
            bean.setSumData(response.getSumData());
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
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
            // 调用江西银行查询单笔出借人投标申请接口
            BankCallBean bankCallBean = bidApplyQuery(userId, assignNid, accountId);
            if (bankCallBean != null) {
                userInfo = CommonUtils.convertBean(bankCallBean, CreditUserInfoBean.class);
            } else {
                logger.error("调用江西银行查询单笔出借人投标申请接口异常");
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
        // 原始出借订单号
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
            accedeListService.updateSaveSignInfo(tenderAgreement, borrowNid, FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT, borrowVO.getInstCode());
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
                commonProducer.messageSendDelay(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean),2);
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
        bean.setAccountId(accountId);// 出借人电子账户号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId0(Integer.parseInt(userId)));
        bean.setLogUserId(userId);
        BankCallBean result = BankCallUtils.callApiBg(bean);
        return result;
    }


    @Override
    public AdminResult pdfPreview(BorrowCreditTenderPDFSignReq req) {
        String nid = req.getNid();
        CheckUtil.check(StringUtils.isNotBlank(nid),MsgEnum.ERR_OBJECT_REQUIRED,"nid");
        // 根据订单号查询用户出借协议记录表
        TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement(nid);
        if (tenderAgreement != null && org.apache.commons.lang3.StringUtils.isNotBlank(tenderAgreement.getImgUrl())) {
            JSONObject responseBean = new JSONObject();
            String imgUrl = tenderAgreement.getImgUrl();
            String[] imgs = new String[]{};
            if (StringUtils.isNotBlank(imgUrl)){
               imgs = imgUrl.split(";");
            }
            List<String> imgList = Arrays.asList(imgs);
            responseBean.put("imgList",imgList);
            // 文件服务器
            String fileDomainUrl = systemConfig.getFtpurl() + systemConfig.getFtpbasepathimg();
            responseBean.put("fileDomainUrl",fileDomainUrl);
            return new AdminResult(responseBean);
        } else {
            return new AdminResult(BaseResult.FAIL, "未查询到用户出借协议");
        }
    }

    @Override
    public int selectBorrowCreditRepayCount(BorrowCreditRepayRequest request) {
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        BorrowCreditRepayResponse borrowCreditRepayResponse = baseClient.postExe(REPAY_INFO_COUNT_URL, req, BorrowCreditRepayResponse.class);
        if (borrowCreditRepayResponse != null) {
            return borrowCreditRepayResponse.getCount();
        }
        return 0;
    }

    @Override
    public int selectBorrowCreditTenderCount(BorrowCreditTenderRequest request) {
        AdminCreditTenderResponse response = baseClient.postExe(TENDER_COUNT_URL, request, AdminCreditTenderResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public AdminCreditTenderResponse getCreditTenderResponse(BorrowCreditTenderRequest request) {
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowCreditRepayAmRequest req = CommonUtils.convertBean(request, BorrowCreditRepayAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        return baseClient.postExe(TENDER_LIST_URL, request, AdminCreditTenderResponse.class);
    }

    /**
     * 结束债权
     * @param orderId
     * @return
     */
    @Override
    public Response doCreditEnd(String orderId){
        StartCreditEndRequest requestBean = new StartCreditEndRequest();
        requestBean.setOrgOrderId(orderId);
        requestBean.setCreditEndType(5);
        requestBean.setStartFrom(2); //散标债转信息

        return baseClient.postExeNoException(CREDITEND_URL, requestBean, IntegerResponse.class);
    }

    /**
     * 承接信息生成excel
     *
     * @author zhangyk
     * @date 2018/7/13 11:10
     */
    private void exportTenderExcel(String isOrganizationView, String sheetName, String fileName, String[] titles, List<BorrowCreditTenderVO> resultList, HttpServletResponse response) {
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
                    BorrowCreditTenderVO borrowCommonCustomize = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (StringUtils.isNotBlank(isOrganizationView)){
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 订单号
                    else if (celLength == 1) {
                        cell.setCellValue(borrowCommonCustomize.getAssignNid());
                    }
                    // 债转编号
                    else if (celLength == 2) {
                        cell.setCellValue(borrowCommonCustomize.getCreditNid());
                    }
                    // 项目编号
                    else if (celLength == 3) {
                        cell.setCellValue(borrowCommonCustomize.getBidNid());
                    }
                    // 出让人用户名
                    else if (celLength == 4) {
                        cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                    }
                    // 出让人推荐人信息---start
                    // 出让人当前
                    else if (celLength == 5) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendNameCredit());
                        }

                    }
                    else if (celLength == 6) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCredit());
                        }

                    }
                    else if (celLength == 7) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRegionNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRegionNameCredit());
                        }

                    }
                    else if (celLength == 8) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getBranchNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getBranchNameCredit());
                        }

                    }
                    else if (celLength == 9) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameCredit());
                        }

                    }

                    //出让人承接
                    else if (celLength == 10) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditName());
                    }
                    else if (celLength == 11) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditAttribute());
                    }
                    else if (celLength == 12) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditRegionName());
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditBranchName());
                    }
                    else if (celLength == 14) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditDepartmentName());
                    }
                    // 出让人推荐人信息---end
                    // 承接人用户名
                    else if (celLength == 15) {
                        cell.setCellValue(borrowCommonCustomize.getUserName());
                    }
                    // 承接人推荐人信息---start
                    // 承接人当前
                    else if (celLength == 16) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendName());
                        }

                    }
                    else if (celLength == 17) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttr());
                        }

                    }
                    else if (celLength == 18) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRegionNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRegionName());
                        }

                    }
                    else if (celLength == 19) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getBranchNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getBranchName());
                        }

                    }
                    else if (celLength == 20) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getDepartmentName());
                        }

                    }

                    // 承接人承接时
                    else if (celLength == 21) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserName());
                    }
                    else if (celLength == 22) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserAttribute());
                    }
                    else if (celLength == 23) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUseRegionname());
                    }
                    else if (celLength == 24) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserBranchname());
                    }
                    else if (celLength == 25) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserDepartmentName());
                    }
                    // 承接人承接时推荐人信息---end
                    // 承接本金
                    else if (celLength == 26) {
                        cell.setCellValue(borrowCommonCustomize.getAssignCapital());
                    }
                    // 折让率
                    else if (celLength == 27) {
                        cell.setCellValue(borrowCommonCustomize.getCreditDiscount());
                    }
                    // 认购价格
                    else if (celLength == 28) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPrice());
                    }
                    // 垫付利息
                    else if (celLength == 29) {
                        cell.setCellValue(borrowCommonCustomize.getAssignInterestAdvance());
                    }
                    // 服务费
                    else if (celLength == 30) {
                        cell.setCellValue(borrowCommonCustomize.getCreditFee());
                    }
                    // 实付金额
                    else if (celLength == 31) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPay());
                    }
                    // 客户端
                    else if (celLength == 32) {
                        if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("0")) {
                            cell.setCellValue("pc");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("1")) {
                            cell.setCellValue("微信");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("2")) {
                            cell.setCellValue("android");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("3")) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 承接时间
                    else if (celLength == 33) {
                        cell.setCellValue(borrowCommonCustomize.getAddTime());
                    }
                    }else {
                        if (celLength == 0) {
                            cell.setCellValue(i + 1);
                        }
                        // 订单号
                        else if (celLength == 1) {
                            cell.setCellValue(borrowCommonCustomize.getAssignNid());
                        }
                        // 债转编号
                        else if (celLength == 2) {
                            cell.setCellValue(borrowCommonCustomize.getCreditNid());
                        }
                        // 项目编号
                        else if (celLength == 3) {
                            cell.setCellValue(borrowCommonCustomize.getBidNid());
                        }
                        // 出让人用户名
                        else if (celLength == 4) {
                            cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                        }
                        // 出让人推荐人信息---start
                        // 出让人当前
                        else if (celLength == 5) {
                            if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                                cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                            }else{
                                cell.setCellValue(borrowCommonCustomize.getRecommendNameCredit());
                            }

                        }
                        else if (celLength == 6) {
                            if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                                cell.setCellValue(borrowCommonCustomize.getRecommendAttrCreditSelf());
                            }else{
                                cell.setCellValue(borrowCommonCustomize.getRecommendAttrCredit());
                            }

                        }
                        //出让人承接
                        else if (celLength == 7) {
                            cell.setCellValue(borrowCommonCustomize.getInviteUserCreditName());
                        }
                        else if (celLength == 8) {
                            cell.setCellValue(borrowCommonCustomize.getInviteUserCreditAttribute());
                        }
                        // 出让人推荐人信息---end
                        // 承接人用户名
                        else if (celLength == 9) {
                            cell.setCellValue(borrowCommonCustomize.getUserName());
                        }
                        // 承接人推荐人信息---start
                        // 承接人当前
                        else if (celLength == 10) {
                            if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                                cell.setCellValue(borrowCommonCustomize.getUserName());
                            }else{
                                cell.setCellValue(borrowCommonCustomize.getRecommendName());
                            }

                        }
                        else if (celLength == 11) {
                            if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                                cell.setCellValue(borrowCommonCustomize.getRecommendAttrSelf());
                            }else{
                                cell.setCellValue(borrowCommonCustomize.getRecommendAttr());
                            }

                        }
                        // 承接人承接时
                        else if (celLength == 12) {
                            cell.setCellValue(borrowCommonCustomize.getInviteUserName());
                        }
                        else if (celLength == 13) {
                            cell.setCellValue(borrowCommonCustomize.getInviteUserAttribute());
                        }
                        // 承接人承接时推荐人信息---end
                        // 承接本金
                        else if (celLength == 14) {
                            cell.setCellValue(borrowCommonCustomize.getAssignCapital());
                        }
                        // 折让率
                        else if (celLength == 15) {
                            cell.setCellValue(borrowCommonCustomize.getCreditDiscount());
                        }
                        // 认购价格
                        else if (celLength == 16) {
                            cell.setCellValue(borrowCommonCustomize.getAssignPrice());
                        }
                        // 垫付利息
                        else if (celLength == 17) {
                            cell.setCellValue(borrowCommonCustomize.getAssignInterestAdvance());
                        }
                        // 服务费
                        else if (celLength == 18) {
                            cell.setCellValue(borrowCommonCustomize.getCreditFee());
                        }
                        // 实付金额
                        else if (celLength == 19) {
                            cell.setCellValue(borrowCommonCustomize.getAssignPay());
                        }
                        // 客户端
                        else if (celLength == 20) {
                            if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                    &&borrowCommonCustomize.getClient().equals("0")) {
                                cell.setCellValue("pc");
                            }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                    &&borrowCommonCustomize.getClient().equals("1")) {
                                cell.setCellValue("微信");
                            }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                    &&borrowCommonCustomize.getClient().equals("2")) {
                                cell.setCellValue("android");
                            }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                    &&borrowCommonCustomize.getClient().equals("3")) {
                                cell.setCellValue("ios");
                            }
                        }
                        // 承接时间
                        else if (celLength == 21) {
                            cell.setCellValue(borrowCommonCustomize.getAddTime());
                        }
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
