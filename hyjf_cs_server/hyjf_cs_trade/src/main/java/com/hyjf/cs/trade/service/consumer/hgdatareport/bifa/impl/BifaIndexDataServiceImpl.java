/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BifaIndexUserInfoBeanVO;
import com.hyjf.am.vo.trade.bifa.BifaIndexUserInfoEntityVO;
import com.hyjf.am.vo.trade.bifa.BifaUserInfoSHA256EntityVO;
import com.hyjf.am.vo.trade.bifa.UserIdAccountSumBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCommonConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaIndexDataService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author jun
 * @version BifaIndexDataServiceImpl, v0.1 2019/1/21 9:25
 */
@Service
public class BifaIndexDataServiceImpl extends BaseHgDateReportServiceImpl implements BifaIndexDataService {

    Logger logger = LoggerFactory.getLogger(BifaIndexDataServiceImpl.class);
    private String thisMessName = "索引数据上报定时任务";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AmTradeClient amTradeClient;

    //保存待上报的文件
    static List<Map<String, String>> reportFiles = new ArrayList<Map<String, String>>();
    //上报文件最大行数
    final int reportFileMaxRows = 1000;

    @Override
    public void report() {
        logger.info(logHeader + "执行上报定时任务！！");
        // 取得当前日期为基准日期
        Integer stdDate = GetDate.getDayStart10(new Date());
        //结束时间为当前时间的前一天的23:59:59

        //生产用 前一天的23：59：59
        //Integer endDate = stdDate - 1;
        //测试用 当天的23：59：59
        Integer endDate = GetDate.getDayEnd10(new Date());

        //查询最近一周的索引数据
        Integer startDate = GetDate.countDate(stdDate, 5, -7);
        //输出上报时间范围
        logger.info(logHeader + "上报时间范围:" + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(startDate) + "~" + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(endDate));
        //准备索引数据
        this.prepareIndexData(startDate, endDate);

    }

    /**
     * 准备索引数据
     * @param startDate
     * @param endDate
     */
    private void prepareIndexData(Integer startDate, Integer endDate) {
        //清空上报文件集合
        reportFiles.clear();
        // 执行已开户用户索引数据上报
        this.prepareBankOpenedAccountReportData(startDate, endDate);
        // 执行出借大于0的用户索引数据上报
        this.prepareLenderGTZeroReportData(startDate, endDate);
        // 执行出借等于0的用户索引数据上报
        this.prepareLenderZeroReportData(startDate, endDate);
        // 执行借贷用户(已放款)索引数据上报
        this.prepareBorrowedUserReportData(startDate, endDate);
        // 执行数据上报操作 测试时关闭北互金索引数据上报
        this.executeDataReport();
    }

    @Override
    public void historyDataReport() {
        try {
            // 启动限制开关 redis.BIFA_HISTORY_OPEN_FLAG = 1 时，执行任务。
            if ( !(RedisUtils.get(RedisConstants.BIFA_HISTORY_OPEN_FLAG) != null
                    && RedisUtils.get(RedisConstants.BIFA_HISTORY_OPEN_FLAG).equals("1")) ) {
                return;
            }

            logger.info(logHeader + "执行历史上报定时任务！！");

            // 取得当前日期为基准日期
            Integer start = GetDate.strYYYYMMDD3Timestamp3(RedisUtils.get(RedisConstants.BIFA_HISTORY_START_YYYYMMDD));
            Integer end = GetDate.strYYYYMMDD3Timestamp3(RedisUtils.get(RedisConstants.BIFA_HISTORY_END_YYYYMMDD));
            if (start.compareTo(end) > 0){
                logger.error(logHeader
                        + "开始>结束 start:" + RedisUtils.get(RedisConstants.BIFA_HISTORY_START_YYYYMMDD)
                        + "  end:" + RedisUtils.get(RedisConstants.BIFA_HISTORY_END_YYYYMMDD));
                throw new Exception("开始>结束");
            }
            Integer endNextMonth = GetDate.countDate(end, 2, 1);
            Integer startDate = 0;
            Integer endDate = 0;
            logger.info(logHeader + "上报时间范围 " + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(start) + "~" + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(GetDate.countDate(end, 5, 1) - 1));
            for (int i=1; GetDate.countDate(start, 2, i) <= endNextMonth; i++){
                if (i==1){
                    if (GetDate.countDate(start, 2, i) >= end){
                        startDate = start;
                        endDate = GetDate.countDate(end, 5, 1) - 1;
                    }else{
                        startDate = start;
                        endDate = GetDate.countDate(start, 2, i) - 1;
                    }
                }else if(GetDate.countDate(start, 2, i) >= end){
                    startDate = endDate + 1;
                    endDate = GetDate.countDate(end, 5, 1) - 1;
                } else{
                    startDate = endDate + 1;
                    endDate = GetDate.countDate(start, 2, i) - 1;
                }
                logger.info(logHeader + "历史上报时间范围 " + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(startDate) + "~" + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(endDate));
                //准备索引数据
                this.prepareIndexData(startDate, endDate);
            }
            logger.info(logHeader + "定时任务处理成功。");
        } catch (Exception e) {
            logger.error(logHeader + "定时任务处理失败！！", e);
        }
    }

    private void executeDataReport() {
        for (Map<String, String> fileMap : reportFiles) {
            try {
                JSONObject result = this.report(fileMap.get("filePath"), fileMap.get("dataType"));
                int resultCode= (int) result.get("result");
                if (resultCode == 0){
                    logger.info(logHeader + result.get("message") +",文件:"+fileMap.get("filePath"));
                }else {
                    logger.error(logHeader + result.get("message") +",文件:"+fileMap.get("filePath"));
                }
            } catch (IOException e) {
                logger.error(logHeader + "上报索引文件失败！！！",e);
            }
        }
    }

    /**
     * 上报索引文件
     * @param filePath
     * @param dataType
     * @return
     * @throws IOException
     */
    private JSONObject report(String filePath, String dataType) throws IOException {
        File file = new File(filePath);
        FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
        StringBody stringBody = new StringBody(dataType, ContentType.MULTIPART_FORM_DATA);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addPart("file", fileBody);
        builder.addPart("dataType", stringBody);
        HttpEntity entity = builder.build();
        HttpPost post = new HttpPost(HYJF_BIFA_INDEXDATA_REPORT_URL);
        post.setEntity(entity);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(post);
        String result=EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    private void prepareBorrowedUserReportData(Integer startDate, Integer endDate) {
        try {
            String dataType = BifaCommonConstants.DATATYPE_1000;
            List<BifaIndexUserInfoBeanVO> bifaIndexUserInfoBeans = this.getBorrowUserInfo(startDate, endDate);
            //判空
            if (null == bifaIndexUserInfoBeans) {
                throw new Exception(logHeader + "获取已放款的借贷用户失败!!!");
            }

            logger.info(logHeader + dataType + " DB检出记录数：" + bifaIndexUserInfoBeans.size());
            // 输出记录数合计
            int total = 0;
            // 单次输出数
            int count = 0;
            // 文件数
            int filsNum = 0;

            StringBuffer sb = new StringBuffer();
            List<BifaIndexUserInfoEntityVO> list = new ArrayList<BifaIndexUserInfoEntityVO>();
            for (int i = 0; i < bifaIndexUserInfoBeans.size(); i++) {
                BifaIndexUserInfoBeanVO bean = bifaIndexUserInfoBeans.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId",bean.getUserId());
                jsonObject.put("trueName",bean.getTrueName());
                jsonObject.put("idCard",bean.getIdCard());
                BifaUserInfoSHA256EntityVO sha256Entity = this.selectUserIdToSHA256(jsonObject);
                BifaIndexUserInfoEntityVO resultFromMongo = csMessageClient.getBorrowUserInfoFromMongo(bean.getBorrowNid());
                if (resultFromMongo == null) {
                    //已开户的用户没有上报时 才执行上报操作
                    sb.append(sha256Entity.getSha256() + "," + bean.getBorrowBeginDate() + "," + bean.getBorrowEndDate() + "\r\n");
                    BifaIndexUserInfoEntityVO entity = CommonUtils.convertBean(bean, BifaIndexUserInfoEntityVO.class);
                    list.add(entity);
                    count++;
                    total++;
                }

                // 每reportFileMaxRows条存1次数据 及最后一次有记录
                if (count >= reportFileMaxRows || (i == bifaIndexUserInfoBeans.size() - 1 && count > 0 && count < reportFileMaxRows)) {
                    //往文件里面写
                    filsNum++;
                    this.handleReportIndexData(sb, dataType, filsNum, endDate);
                    logger.info(logHeader + dataType + "_" + filsNum + " 文件生成。生成记录数：" + count);
                    //上报完成后 将借贷用户信息保存至本地mongo
                    this.insertBorrowUserInfoReportData(list);
                    logger.info(logHeader + dataType + "_" + filsNum + " MongoDB保存。" + list.size());
                    sb.setLength(0); // 释放StringBuffer
                    list.clear(); // 释放List
                    count = 0; // 初始单次输出数
                }
            }
            logger.info(logHeader + dataType + " 文件生成。总生成记录数：" + total);
        } catch (Exception e) {
            logger.error(logHeader + "定时任务处理失败！！", e);
        }
    }

    private void insertBorrowUserInfoReportData(List<BifaIndexUserInfoEntityVO> list) {
        try {
            for (BifaIndexUserInfoEntityVO entity : list) {
                BifaIndexUserInfoEntityVO mongoResult = this.getBorrowUserInfoFromMongo(entity.getBorrowNid());
                if (null == mongoResult) {
                    // 等于空的时候再添加
                    csMessageClient.insertBorrowUserInfoReportData(entity);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 从mongo中获取借贷用户索引信息
     * @param borrowNid
     * @return
     */
    private BifaIndexUserInfoEntityVO getBorrowUserInfoFromMongo(String borrowNid) {
       return csMessageClient.getBorrowUserInfoFromMongo(borrowNid);
    }

    /**
     * 获取借款人信息 最近7天的
     * @param startDate
     * @param endDate
     * @return
     */
    private List<BifaIndexUserInfoBeanVO> getBorrowUserInfo(Integer startDate, Integer endDate) throws Exception {
        List<BifaIndexUserInfoBeanVO> result = new ArrayList<BifaIndexUserInfoBeanVO>();
        //获取近七天添加的标的信息
        List<BorrowAndInfoVO> borrowAndInfos = amTradeClient.selectBorrowUserInfo(startDate, endDate);
        //判空
        if (null == borrowAndInfos) {
            throw new Exception(logHeader + "获取借款人信息失败!!!");
        }

        for (BorrowAndInfoVO bean : borrowAndInfos) {
            if ("1".equals(bean.getCompanyOrPersonal()) && StringUtils.isEmpty(bean.getIdCard())) {
                //借款主体为公司时,公司的统一社会信用代码不能为空
                logger.info(logHeader + "索引数据错误！！" + JSONObject.toJSONString(bean));
                continue;
            } else if ("2".equals(bean.getCompanyOrPersonal())
                    && (StringUtils.isEmpty(bean.getTruename()) || StringUtils.isEmpty(bean.getIdCard()))) {
                //借款主体为个人时,个人的真实名称和身份证号不能为空
                logger.info(logHeader + "索引数据错误！！" + JSONObject.toJSONString(bean));
                continue;
            }

            BifaIndexUserInfoBeanVO biuib = this.buildBifaIndexUserInfoBean(bean);
            result.add(biuib);
        }

        return result;
    }

    private BifaIndexUserInfoBeanVO buildBifaIndexUserInfoBean(BorrowAndInfoVO bean) {
        BifaIndexUserInfoBeanVO result = new BifaIndexUserInfoBeanVO();
        result.setTrueName(bean.getTruename());
        result.setIdCard(bean.getIdCard());
        result.setBorrowBeginDate(GetDate.times10toStrYYYYMMDD(bean.getRecoverLastTime()));
        result.setBorrowNid(bean.getBorrowNid());
        result.setBorrowEndDate(GetDate.times10toStrYYYYMMDD(bean.getRepayLastTime()));
        return result;
    }


    private void prepareLenderZeroReportData(Integer startDate, Integer endDate) {
        try {
            String dataType = BifaCommonConstants.DATATYPE_1003;
            //拉取已开户且出借等于0的用户
            String isOpenUp = "1";
            String isLenderZeroUp = "0";
            String isLenderOneUp = "0";
            List<BifaUserInfoSHA256EntityVO> userInfoSHA256EntityList = csMessageClient.getUserInfoSHA256(isOpenUp,isLenderZeroUp,isLenderOneUp);

            String currDate = GetDate.formatDate();

            if (userInfoSHA256EntityList == null) {
                logger.info(logHeader + "未获取已开户且出借等于0的用户!!!" );
                return;
            }

            logger.info(logHeader + dataType + " DB检出记录数：" + userInfoSHA256EntityList.size());
            // 输出记录数合计
            int total = 0;
            // 单次输出数
            int count = 0;
            // 文件数
            int filsNum = 0;
            StringBuffer sb = new StringBuffer();
            List<Integer> userList = new ArrayList<>();

            for (int i = 0; i < userInfoSHA256EntityList.size(); i++) {
                BifaUserInfoSHA256EntityVO entity = userInfoSHA256EntityList.get(i);
                //出借等于0的用户的注册时间
                UserVO users = this.getUserByUserId(entity.getUserId());
                //没有上报
                sb.append(entity.getSha256() + "," + GetDate.dateToString2(users.getRegTime()) + "," + currDate + "\r\n");
                userList.add(entity.getUserId());
                count++;
                total++;

                // 每reportFileMaxRows条存1次数据 及最后一次有记录
                if (count >= reportFileMaxRows || (i == userInfoSHA256EntityList.size() - 1 && count > 0 && count < reportFileMaxRows)) {
                    //往文件里面写
                    filsNum++;
                    this.handleReportIndexData(sb, dataType, filsNum, endDate);
                    logger.info(logHeader + dataType + "_" + filsNum + " 文件生成。生成记录数：" + count);
                    //上报完成后 更新sha256表中用户对应状态
                    this.updateUserIndexReportStatus(userList, dataType);
                    logger.info(logHeader + dataType + "_" + filsNum + " MongoDB保存。" + JSONObject.toJSONString(userList));
                    sb.setLength(0); // 释放StringBuffer
                    userList.clear(); // 释放List
                    count = 0; // 初始单次输出数
                }
            }
            logger.info(logHeader + dataType + " 文件生成。总生成记录数：" + total);
        } catch (Exception e) {
            logger.error(logHeader + "定时任务处理失败！！", e);
        }
    }

    /**
     * 获取user对象
     * @param userId
     * @return
     */
    private UserVO getUserByUserId(Integer userId) {
        return amUserClient.findUserById(userId);
    }

    /**
     * 执行出借大于0或者等于的用户索引数据上报
     * @param startDate
     * @param endDate
     */
    private void prepareLenderGTZeroReportData(Integer startDate, Integer endDate) {
        try {
            String dataType = BifaCommonConstants.DATATYPE_1002;
            //拉取已开户且出借>0的用户
            List<UserIdAccountSumBeanVO> borrowTenders = this.getBorrowTenderAccountSum(startDate, endDate);
            //判空
            if (null == borrowTenders) {
                throw new Exception(logHeader + "获取已开户且出借>0的用户失败!!!");
            }
            logger.info(logHeader + dataType + " DB检出记录数：" + borrowTenders.size());
            // 输出记录数合计
            int total = 0;
            // 单次输出数
            int count = 0;
            // 文件数
            int filsNum = 0;
            StringBuffer sb = new StringBuffer();
            List<Integer> userList = new ArrayList<>();
            for (int i = 0; i < borrowTenders.size(); i++) {
                UserIdAccountSumBeanVO bean = borrowTenders.get(i);
                //获取用户的名称,身份证号,注册时间
                BifaIndexUserInfoBeanVO bifaIndexUserInfoBean = this.getBifaIndexUserInfo(bean.getUserId());
                //获取sha256加密
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId",bean.getUserId());
                jsonObject.put("trueName",bifaIndexUserInfoBean.getTrueName());
                jsonObject.put("idCard",bifaIndexUserInfoBean.getIdCard());
                BifaUserInfoSHA256EntityVO userInfoSHA256Entity = this.selectUserIdToSHA256(jsonObject);
                //出借大于0的用户
                //判断借款大于0的用户是否上报
                if ("0".equals(userInfoSHA256Entity.getIsLenderOneUp())) {
                    //没有上报
                    sb.append(userInfoSHA256Entity.getSha256() + "," + bifaIndexUserInfoBean.getRegDate() + "\r\n");
                    userList.add(bean.getUserId());
                    count++;
                    total++;
                }

                // 每reportFileMaxRows条存1次数据 及最后一次有记录
                if (count >= reportFileMaxRows || (i == borrowTenders.size() - 1 && count > 0 && count < reportFileMaxRows)) {
                    //往文件里面写
                    filsNum++;
                    this.handleReportIndexData(sb, dataType, filsNum, endDate);
                    logger.info(logHeader + dataType + "_" + filsNum + " 文件生成。生成记录数：" + count);
                    //上报完成后 更新sha256表中用户对应状态
                    this.updateUserIndexReportStatus(userList, dataType);
                    logger.info(logHeader + dataType + "_" + filsNum + " MongoDB保存。" + JSONObject.toJSONString(userList));
                    sb.setLength(0); // 释放StringBuffer
                    userList.clear(); // 释放List
                    count = 0; // 初始单次输出数
                }
            }
            logger.info(logHeader + dataType + " 文件生成。总生成记录数：" + total);
        } catch (Exception e) {
            logger.error(logHeader + "定时任务处理失败！！", e);
        }
    }

    /**
     * 获取借款人信息
     * @param userId
     * @return
     */
    private BifaIndexUserInfoBeanVO getBifaIndexUserInfo(Integer userId) {
       return amUserClient.getBifaIndexUserInfo(userId);
    }

    /**
     * 已开户且出借>0的用户
     * @param startDate
     * @param endDate
     * @return
     */
    private List<UserIdAccountSumBeanVO> getBorrowTenderAccountSum(Integer startDate, Integer endDate) {
        return amTradeClient.getBorrowTenderAccountSum(startDate,endDate);
    }

    /**
     * 执行已开户用户索引数据上报
     * @param startDate
     * @param endDate
     */
    private void prepareBankOpenedAccountReportData(Integer startDate, Integer endDate) {
        try {
            String dataType = BifaCommonConstants.DATATYPE_1005;
            //获取最近七天开户的用户
            List<BifaIndexUserInfoBeanVO> bifaIndexUserInfoBeans = this.getBankOpenedAccountUsers(startDate, endDate);
            if (null == bifaIndexUserInfoBeans) {
                throw new Exception(logHeader + "获取开户用户失败!!!");
            }
            logger.info(logHeader + dataType + " DB检出记录数：" + bifaIndexUserInfoBeans.size());
            // 输出记录数合计
            int total = 0;
            // 单次输出数
            int count = 0;
            // 文件数
            int filsNum = 0;

            StringBuffer sb = new StringBuffer();
            List<Integer> userList = new ArrayList<>();
            for (int i = 0; i < bifaIndexUserInfoBeans.size(); i++) {
                BifaIndexUserInfoBeanVO bean = bifaIndexUserInfoBeans.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId",bean.getUserId());
                jsonObject.put("trueName",bean.getTrueName());
                jsonObject.put("idCard",bean.getIdCard());
                BifaUserInfoSHA256EntityVO sha256Entity = this.selectUserIdToSHA256(jsonObject);
                if ("0".equals(sha256Entity.getIsOpenUp())) {
                    //已开户的用户没有上报时 才执行上报操作
                    sb.append(sha256Entity.getSha256() + "," + bean.getRegDate() + "\r\n");
                    userList.add(bean.getUserId());
                    count++;
                    total++;
                }

                // 每reportFileMaxRows条存1次数据 及最后一次有记录
                if (count >= reportFileMaxRows || (i == bifaIndexUserInfoBeans.size() - 1 && count > 0 && count < reportFileMaxRows)) {
                    //往文件里面写
                    filsNum++;
                    this.handleReportIndexData(sb, dataType, filsNum, endDate);
                    logger.info(logHeader + dataType + "_" + filsNum + " 文件生成。生成记录数：" + count);
                    //上报完成后 更新sha256表中用户对应状态
                    this.updateUserIndexReportStatus(userList, dataType);
                    logger.info(logHeader + dataType + "_" + filsNum + " MongoDB保存。" + JSONObject.toJSONString(userList));
                    sb.setLength(0); // 释放StringBuffer
                    userList.clear(); // 释放List
                    count = 0; // 初始单次输出数
                }
            }
            logger.info(logHeader + dataType + " 文件生成。总生成记录数：" + total);
        } catch (Exception e) {
            logger.error(logHeader + "定时任务处理失败！！", e);
        }
    }

    /**
     * 更新sha256表中用户对应状态
     * @param userList
     * @param dataType
     */
    private void updateUserIndexReportStatus(List<Integer> userList, String dataType) {
        for (Integer userId : userList) {
            csMessageClient.updateUserIndexReportStatus(userId,dataType);
        }
    }

    /**
     * 处理上报数据
     * @param sb
     * @param dataType
     * @param fileNum
     * @param endDate
     */
    private void handleReportIndexData(StringBuffer sb, String dataType, int fileNum, Integer endDate) {
        String fileSuffix = "_" + GetDate.getNowTimeYYYYMMDDHHMMSS() + "_" + new DecimalFormat("000").format(fileNum) + ".txt";

        // 历史数据的文件名
        if (endDate.compareTo(GetDate.getDayStart10(new Date())-1) < 0){
            fileSuffix = "_" + GetDate.timestamptoNUMStrYYYYMMDDHHMMSS2(endDate) + "_" + new DecimalFormat("000").format(fileNum) + ".txt";
        }

        if (StringUtils.isEmpty(sb) || StringUtils.isEmpty(dataType)) {
            logger.info(logHeader + "获取待上报数据或者数据类型失败!!!");
        } else {
            //处理出借等于0的用户
            if (StringUtils.isNotEmpty(sb) && BifaCommonConstants.DATATYPE_1003.equals(dataType)) {
                String fileName = BifaCommonConstants.DATATYPE_1003 + fileSuffix;
                String filePath = "";
                if (!HYJF_BIFA_FILES_INDEXFILE.endsWith(File.separator)) {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + File.separator + fileName;
                } else {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + fileName;
                }

                FileUtil.writeFile(filePath, sb.toString().getBytes(), false);
                Map<String, String> filesMap = new HashMap<String, String>();
                filesMap.put("filePath", filePath);
                filesMap.put("dataType", dataType);
                reportFiles.add(filesMap);
            }
            //处理出借大于0的用户
            if (StringUtils.isNotEmpty(sb) && BifaCommonConstants.DATATYPE_1002.equals(dataType)) {
                String fileName = BifaCommonConstants.DATATYPE_1002 + fileSuffix;
                String filePath = "";
                if (!HYJF_BIFA_FILES_INDEXFILE.endsWith(File.separator)) {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + File.separator + fileName;
                } else {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + fileName;
                }

                FileUtil.writeFile(filePath, sb.toString().getBytes(), false);
                Map<String, String> filesMap = new HashMap<String, String>();
                filesMap.put("filePath", filePath);
                filesMap.put("dataType", dataType);
                reportFiles.add(filesMap);
            }
            //处理开户用户
            if (StringUtils.isNotEmpty(sb) && BifaCommonConstants.DATATYPE_1005.equals(dataType)) {
                String fileName = BifaCommonConstants.DATATYPE_1005 + fileSuffix;
                String filePath = "";
                if (!HYJF_BIFA_FILES_INDEXFILE.endsWith(File.separator)) {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + File.separator + fileName;
                } else {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + fileName;
                }

                FileUtil.writeFile(filePath, sb.toString().getBytes(), false);
                Map<String, String> filesMap = new HashMap<String, String>();
                filesMap.put("filePath", filePath);
                filesMap.put("dataType", dataType);
                reportFiles.add(filesMap);
            }
            //处理借贷用户
            if (StringUtils.isNotEmpty(sb) && BifaCommonConstants.DATATYPE_1000.equals(dataType)) {
                String fileName = BifaCommonConstants.DATATYPE_1000 + fileSuffix;
                String filePath = "";
                if (!HYJF_BIFA_FILES_INDEXFILE.endsWith(File.separator)) {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + File.separator + fileName;
                } else {
                    filePath = HYJF_BIFA_FILES_INDEXFILE + fileName;
                }

                FileUtil.writeFile(filePath, sb.toString().getBytes(), false);
                Map<String, String> filesMap = new HashMap<String, String>();
                filesMap.put("filePath", filePath);
                filesMap.put("dataType", dataType);
                reportFiles.add(filesMap);
            }
        }
    }

    /**
     * 获取最近七天开户的用户
     * @param startDate
     * @param endDate
     * @return
     */
    private List<BifaIndexUserInfoBeanVO> getBankOpenedAccountUsers(Integer startDate, Integer endDate) {
        return amUserClient.getBankOpenedAccountUsers(startDate, endDate);
    }

}
